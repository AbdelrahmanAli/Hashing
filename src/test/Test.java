package test;

import hashTable.DoubleHashing;
import hashTable.HashTable;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Random;


public class Test {

	public static void main(String[] args) {
		//init hashtable
		HashTable<Integer, String> map = new DoubleHashing<Integer, String>();
		
		int grade = 0;
		
		//Empty hash table test
		System.out.println("Testing Empty HashTable");
		
		if (map.isEmpty()) {
			grade += 1;
			System.out.println("Testing isEmpty (1) ... SUCCESS");
		} else {
			System.out.println("Testing isEmpty (1) ... FAIL");
		}
		
		if (map.size() == 0) {
			grade += 1;
			System.out.println("Testing size (1) ... SUCCESS");
		} else {
			System.out.println("Testing size (1) ... FAIL");
		}
		
		if (map.get(1) == null) {
			grade += 1;
			System.out.println("Testing get (1) ... SUCCESS");
		} else {
			System.out.println("Testing get (1) ... FAIL");
		}
		
		if (map.contains(1) == false) {
			grade += 1;
			System.out.println("Testing contains (1) ... SUCCESS");
		} else {
			System.out.println("Testing contains (1) ... FAIL");
		}
		
		if (map.keys().iterator().hasNext() == false) {
			grade += 1;
			System.out.println("Testing keys (1) ... SUCCESS");
		} else {
			System.out.println("Testing keys (1) ... FAIL");
		}
		
		// Adding data to the HashTable
		System.out.println("Adding Data to HashTable");
		BitSet keysMap = new BitSet(1000000);
		Random r = new Random();
		int unique = 0;
		for (int i = 0; i < 500000; i++) {
			int k = r.nextInt(1000000);
			if (! keysMap.get(k)) {
				unique++;
			}
			map.put(k, k+"");
			keysMap.set(k);
		}
		
		if (! map.isEmpty()) {
			grade += 1;
			System.out.println("Testing isEmpty (1) ... SUCCESS");
		} else {
			System.out.println("Testing isEmpty (1) ... FAIL");
		}
		
		if (map.size() == unique) {
			grade += 1;
			System.out.println("Testing size (1) ... SUCCESS");
		} else {
			System.out.println("Testing size (1) ... FAIL");
		}
		//Testing get
		int k = -1;
		while (k == -1) {
			int rand = r.nextInt(1000000);
			k = keysMap.nextSetBit(rand);
		}
		if (map.get(k) != null && map.get(k).equals(k+"")) {
			grade += 1;
			System.out.println("Testing get (1) ... SUCCESS");
		} else {
			System.out.println("Testing get (1) ... FAIL");
		}
		
		k = -1;
		while (k == -1) {
			int rand = r.nextInt(1000000);
			k = keysMap.nextClearBit(rand);
		}
		if (map.get(k) == null) {
			grade += 1;
			System.out.println("Testing get (1) ... SUCCESS");
		} else {
			System.out.println("Testing get (1) ... FAIL");
		}
		//Testing contains
		k = -1;
		while (k == -1) {
			int rand = r.nextInt(1000000);
			k = keysMap.nextSetBit(rand);
		}
		if (map.contains(k)) {
			grade += 1;
			System.out.println("Testing contains (1) ... SUCCESS");
		} else {
			System.out.println("Testing contains (1) ... FAIL");
		}
		
		k = -1;
		while (k == -1) {
			int rand = r.nextInt(1000000);
			k = keysMap.nextClearBit(rand);
		}
		if (map.contains(k) == false) {
			grade += 1;
			System.out.println("Testing contains (1) ... SUCCESS");
		} else {
			System.out.println("Testing contains (1) ... FAIL");
		}
		
		//Testing Delete
		int size = map.size();
		k = -1;
		while (k == -1) {
			int rand = r.nextInt(1000000);
			k = keysMap.nextSetBit(rand);
		}
		if (keysMap.get(k)) {
			map.delete(k);
			if (map.get(k) == null && map.size() == size-1) {
				grade += 2;
				keysMap.clear(k);
				System.out.println("Testing delete (1) ... SUCCESS");
			} else {
				System.out.println("Testing delete (1) ... FAIL");
			}
		} else {
			System.out.println("Testing delete (1) ... ERROR");
		}
		
		//Testing Keys
		Iterator<Integer> it = map.keys().iterator();
		while (it.hasNext()) {
			int w = it.next();
			keysMap.clear(w);
		}
		if (keysMap.cardinality() == 0) {
			grade += 2;
			System.out.println("Testing keys (1) ... SUCCESS");
		} else {
			System.out.println("Testing keys (1) ... FAIL");
		}
		
		System.out.println("Grade = " + grade + " / 15");
	}

}
