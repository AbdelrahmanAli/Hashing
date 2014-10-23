package hashTable;

public class Entry<K,V>
{
	private K key;
	private V value;
	private boolean tompStone;

	public Entry(K key, V value) 
	{
		this.key = key;
		this.value = value;
		tompStone = false;
	}

	// getters
	public K getKey() 			 {	return key;			}
	public V getValue() 		 {	return value;		}
	public boolean isTompStone() {	return tompStone;	}
	//setters
	public void setKey(K key)	 				{	this.key = key;				}
	public void setValue(V value) 				{	this.value = value;			}
	public void setTompStone(boolean tompStone) {	this.tompStone = tompStone;	}
	
	
}
