package hashTable;

import java.util.ArrayList;

public class ConstantOverFlowBucketing<K,V> implements HashTable<K,V> 
{

	// array of linked lists
	private ArrayList<Entry<K,V>> hashTable,overFlowBucket;
	private ArrayList<K> keys;
	private int numberOfEntries,tableSize,bucketSize,numberOfBuckets;
	private double loadFactor;
	private int numberOfCollisions,totalCollisions;
	private boolean rehashing;
	
	public ConstantOverFlowBucketing() 
	{
		rehashing = false;
		numberOfCollisions = 0;
		totalCollisions = 0;
		numberOfEntries = 0;
		tableSize = 10;
		loadFactor = 0;
		bucketSize = 5;
		numberOfBuckets = tableSize/bucketSize;
		hashTable = new ArrayList<Entry<K,V>>();
		for (int i = 0; i < tableSize; i++) 	hashTable.add(null);
		overFlowBucket = new ArrayList<Entry<K,V>>();
		for (int i = 0; i < bucketSize*2; i++) 	overFlowBucket.add(null);
	}
	
	public int getOverflowFirstEmptySlot()
	{
		for (int i = 0; i < overFlowBucket.size(); i++) 	{	if(overFlowBucket.get(i)==null) return i;	}
		return -1;
	}
	
	@Override
	public void put(K key, V value) 
	{
		int[] positionInfo = searchForEntry(key);
		//doesn't exist before
		if(positionInfo==null)
		{
			//get position
			int homePosition = key.hashCode()%tableSize;
			
			//if the position is empty (null) then insert
			if( hashTable.get(homePosition) == null)	hashTable.set(homePosition, (new Entry<K,V>(key,value)));
			else
			{
				//get bucket number
				int bucketNumber = (homePosition/bucketSize) +1;
				// get bucket start
				int bucketStart = tableSize - ( bucketSize*(numberOfBuckets- (bucketNumber-1)) );
				//find empty position to insert into
				boolean emptyPositionNotFound = true;
				for (int i = bucketStart; i < bucketStart+bucketSize && emptyPositionNotFound && i<hashTable.size(); i++) 
				{
					if( hashTable.get(i) == null )
					{
						if(!rehashing) numberOfCollisions++;
						hashTable.set(i, (new Entry<K,V>(key,value)));
						emptyPositionNotFound=false;
					}
				}
				
				//if bucket is full, insert in the overflow bucket
				if(emptyPositionNotFound)
				{
					int firstEmptyOverflowSlot = getOverflowFirstEmptySlot();
					if(firstEmptyOverflowSlot != -1)
					{
						if(!rehashing) numberOfCollisions++;
						overFlowBucket.set(firstEmptyOverflowSlot, (new Entry<K,V>(key,value)) );	
					}
					else
					{
						System.out.println("Unable to put entry. No space.");
						return;
					}
				}
			}
			
			// increment number of entries
			numberOfEntries++;
			// check load factor
			checkForRehashing();
		}
		//exist before then override value
		else 
		{
			int bucketNumber = positionInfo[0];
			int position = positionInfo[1];
			// in over flow bucket
			if(bucketNumber==(numberOfBuckets+1)) overFlowBucket.get(position).setValue(value);
			//in hashTable
			else hashTable.get(position).setValue(value);
		}
	}
	
	private void checkForRehashing()
	{
		//calculate load factor
		loadFactor =  (double) numberOfEntries/tableSize;
		if(loadFactor >= 0.75)	rehash();		
	}
	
	
	private void rehash()
	{
		totalCollisions+=numberOfCollisions;
		numberOfCollisions = 0;
		rehashing = true;
		
		ArrayList<Entry<K,V>> prevHashTable = hashTable;
		ArrayList<Entry<K,V>> prevOverFlowBucket = overFlowBucket;
		
		tableSize*=2;
		numberOfEntries = 0;
		loadFactor = 0; 
		numberOfBuckets = tableSize/bucketSize;
		
		hashTable = new ArrayList<Entry<K,V>>();
		for (int i = 0; i < tableSize; i++) 	hashTable.add(null);
		overFlowBucket = new ArrayList<Entry<K,V>>();
		for (int i = 0; i < prevOverFlowBucket.size(); i++) 	overFlowBucket.add(null);
		
		for (int i = 0; i < prevHashTable.size(); i++) 
		{ 
			if(prevHashTable.get(i)!=null) 
			put(prevHashTable.get(i).getKey(),prevHashTable.get(i).getValue());	
		}
		
		for (int i = 0; i < prevOverFlowBucket.size(); i++)
		{	
			if(prevOverFlowBucket.get(i)!=null) 
			put(prevOverFlowBucket.get(i).getKey(),prevOverFlowBucket.get(i).getValue());
		}
		rehashing = false; 
	}
	
	private int[] searchForEntry(K key)
	{
		//if found then return positionInfo array
		//(no. of bucket (bucketsNumber+1 if overflow bucket) , position)
		//else return null
		int[] positionInfo = new int[2];
		
		//get position
		int homePosition = key.hashCode()%tableSize;
		//get bucket number
		int bucketNumber = (homePosition/bucketSize) +1;
		
	//if in position then return it
		if(hashTable.get(homePosition)!=null && hashTable.get(homePosition).getKey().hashCode() == key.hashCode())
		{
			positionInfo[0] = bucketNumber;
			positionInfo[1] = homePosition;
			return positionInfo;
		}
			
		
	//if not in position then search the bucket
		// get bucket start
		int bucketStart = tableSize - ( bucketSize*(numberOfBuckets- (bucketNumber-1)) );
		// search for it
		for (int i = bucketStart; i < bucketStart+bucketSize && i<hashTable.size(); i++) 
		{
			if(hashTable.get(i)!=null && hashTable.get(i).getKey().hashCode() == key.hashCode())
			{
				positionInfo[0] = bucketNumber;
				positionInfo[1] = i;
				return positionInfo;
			}
		}
	
	//if not in the bucket then search the overflow bucket
		for (int i = 0; i < overFlowBucket.size(); i++) 
		{
			if(overFlowBucket.get(i)!=null && overFlowBucket.get(i).getKey().hashCode() == key.hashCode())
			{
				positionInfo[0] = numberOfBuckets+1;
				positionInfo[1] = i;
				return positionInfo;
			}
		}
	
	//if not found return null
		return null;
	}

	@Override
	public V get(K key) 
	{
		int[] positionInfo = searchForEntry(key);
		
		if(positionInfo!=null)
		{
			int bucketNumber = positionInfo[0];
			int position = positionInfo[1];
			// in over flow bucket
			if(bucketNumber==(numberOfBuckets+1)) return overFlowBucket.get(position).getValue();
			//in hashTable
			else return hashTable.get(position).getValue();
		}
		//if not found
		return null;
	}

	@Override
	public void delete(K key) 
	{
		int[] positionInfo = searchForEntry(key);
		
		if(positionInfo!=null)
		{
			int bucketNumber = positionInfo[0];
			int position = positionInfo[1];
			// in over flow bucket
			if(bucketNumber==(numberOfBuckets+1))  overFlowBucket.set(position,null);
			//in hashTable
			else hashTable.set(position,null);
			//decrement number of entries
			numberOfEntries--;
			return;
		}

		// if not found
	}

	@Override
	public boolean contains(K key) 
	{
		int[] positionInfo = searchForEntry(key);
		// found
		if(positionInfo!=null)	return true;
		
		//if not found
		return false;
	}

	@Override
	public boolean isEmpty() 
	{
		return (numberOfEntries==0);
	}



	@Override
	public Iterable<K> keys() 
	{
		keys = new ArrayList<K>();
		
		for (int i = 0; i < tableSize; i++)
		{	if(hashTable.get(i)!=null) keys.add(hashTable.get(i).getKey());	}
		
		for (int i = 0; i < overFlowBucket.size(); i++) 	
		{	if(overFlowBucket.get(i)!=null) keys.add(overFlowBucket.get(i).getKey());	}

		return keys;
	}
	@Override
	public int size() 
	{
		return numberOfEntries;
	}
	
	@Override
	public int tableLength() 
	{
		return tableSize;
	}
	
	@Override
	public int getNumberOfCollisions() 
	{
		return numberOfCollisions;
	}
	
	@Override
	public int getMemoryUsed()
	{
		int totalMemory =tableSize+overFlowBucket.size();
		return totalMemory;
	}
	@Override
	public int getTotalCollison()
	{
		return totalCollisions;
	}
	
}
