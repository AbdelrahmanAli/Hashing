package hashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PseudoRandomProbing<K,V> implements HashTable<K,V> 
{
	private ArrayList<Entry<K,V>> hashTable;
	private Integer[] perm;
	private ArrayList<K> keys;
	private int numberOfEntries,tableSize;
	private double loadFactor;
	private int numberOfCollisions,totalCollisions;
	private boolean rehashing;
	
	public PseudoRandomProbing() 
	{
		rehashing = false;
		numberOfCollisions = 0;
		totalCollisions = 0;
		numberOfEntries = 0;
		tableSize = 10; 
		loadFactor = 0;
		hashTable = new ArrayList<Entry<K,V>>();
		for (int i = 0; i < tableSize; i++) 	hashTable.add(null);
		generatePermutation();
	}
	
	private void generatePermutation()
	{
		perm = new Integer[tableSize];
		for (int i = 0; i < tableSize; i++) perm[i] = i;
	    Collections.shuffle(Arrays.asList(perm)); //shuffle array
	    for (int i = 0; i < tableSize; i++) 
	    {
	    	if(perm[i]==0)
	    	{
	    		perm[i] = perm[0];
	    		perm[0]=0;
	    		return;
	    	}
	    }
	}
	
	public int searchForEntry(K key)
	{
		// return -1 if not found else return position of the entry
		int index;
		int homePosition = key.hashCode()%tableSize; 
		for (int i = 0; i < tableSize; i++) 
		{
			index = (homePosition+ perm[i])%tableSize;
			if(hashTable.get(index) == null) return -1;
			else if(hashTable.get(index).isTompStone()) continue;
			else if(key.hashCode() == hashTable.get(index).getKey().hashCode())	return index;
		}
		return -1;
	}
	
	@Override
	public void put(K key, V value) 
	{
		int positionInfo = searchForEntry(key);
		// doesn't exist before
		if(positionInfo==-1)
		{
			//get home position
			int homePosition = key.hashCode()%tableSize;
			// find empty position in the probe sequence and insert (starting from homePosition)
			int index;
			for (int i = 0; i < tableSize; i++) 
			{
				index = (homePosition+ perm[i] )%tableSize;
				if(hashTable.get(index)==null || hashTable.get(index).isTompStone())
				{
					hashTable.set(index, (new Entry<K,V>(key,value)));
					if(!rehashing && index!=homePosition) numberOfCollisions++;
					numberOfEntries++;
					checkForRehashing();
					return;
				}
			}
			System.out.println("Unable to put entry. Memory Full.");
		}
		// exist before
		else	hashTable.get(positionInfo).setValue(value);
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
		
		numberOfEntries = 0;
		tableSize*=2;
		loadFactor = 0;
		hashTable = new ArrayList<Entry<K,V>>();
		for (int i = 0; i < tableSize; i++) 	hashTable.add(null);
		generatePermutation();
		
		for (int i = 0; i < prevHashTable.size(); i++) 
		{
			if(prevHashTable.get(i)!=null && !prevHashTable.get(i).isTompStone())
				put(prevHashTable.get(i).getKey(),prevHashTable.get(i).getValue());
		}
		rehashing = false;
	}
	
	@Override
	public V get(K key) 
	{
		int positionInfo = searchForEntry(key);
		if(positionInfo!=-1)	return hashTable.get(positionInfo).getValue();
		//if not found
		return null;
	}
	@Override
	public void delete(K key) 
	{
		int positionInfo = searchForEntry(key);
		if(positionInfo!=-1)
		{
			hashTable.get(positionInfo).setTompStone(true);
			numberOfEntries--;
		}
	}
	@Override
	public boolean contains(K key) 
	{
		int positionInfo = searchForEntry(key);
		if(positionInfo!=-1)	return true;
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
		{
			if(hashTable.get(i)!=null && !hashTable.get(i).isTompStone())	keys.add(hashTable.get(i).getKey());
		}
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
		return tableSize;
	}
	@Override
	public int getTotalCollison()
	{
		return totalCollisions;
	}
}
