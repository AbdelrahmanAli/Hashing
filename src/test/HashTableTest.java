package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hashTable.Bucketing;
import hashTable.ConstantOverFlowBucketing;
import hashTable.DoubleHashing;
import hashTable.HashTable;
import hashTable.LinearProbing;
import hashTable.PseudoRandomProbing;
import hashTable.QuadraticProbing;
import hashTable.SeparateChaining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;

import org.junit.Ignore;
import org.junit.Test;

public class HashTableTest {

	protected HashTable<Integer,String> ht;
	protected int hashSize;
	
	@Ignore
	@Test
	public void separateChaining() 
	{
		System.out.println("========================================");
		System.out.println("\n -Separate Chaining Test:");
		ht = new SeparateChaining<Integer,String>();
		System.out.println(" -Initial Test:");
		System.out.println("  -Checking Hash Table if empty.");
		assertEquals(ht.isEmpty(),true);
		System.out.println("  -Checking Hash Table size.");
		assertTrue( ht.tableLength() >= 0 );
		hashSize = ht.tableLength();
		System.out.println("  -Hash Table size is "+hashSize+".");
		
		System.out.println("\n -Insertion and Search Test:");
		System.out.println("  -Inserting 10 entries without collision.");
		for (int i = 0; i < 10; i++) {	ht.put((Integer)i, i+"");	}
		System.out.println("  -Checking size again.");
		assertEquals(ht.isEmpty(),false);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 10);  
		System.out.println("  -Making an iterable object.");
		Iterable<Integer> iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue(Integer.parseInt(ht.get(x))==x);	}
		System.out.println("  -Insert 10 entries that causes collisions.");
		for (int i = 10; i < 20; i++) {	ht.put((Integer)i, i+"");	}
		System.out.println("  -Checking if the size is doubled.");
		assertTrue(ht.tableLength()<2*hashSize);
		System.out.println("  -Size not doubled as expected.");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 20); 
		System.out.println("  -Insert entries that causes collisions and cause size to be doubled.");
		for (int i = 20; i < 30; i++) {	ht.put((Integer)i, i+"");	}
		assertTrue(ht.tableLength()>=2*hashSize);
		hashSize = ht.tableLength();
		System.out.println("  -The new hash table size = "+hashSize+".");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 30); 
		System.out.println("  -Regenerate the iterable object.");
		iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue(Integer.parseInt(ht.get(x))==x);	}
		System.out.println("  -Insert dublicates.");
		for (int i = 0; i < 30; i++) {	ht.put((Integer)i, i+"");	}
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 30); 
		
		System.out.println("\n -Deletion and Search Test:");
		System.out.println("  -Deleting entries from 10 to 19.");
		for (int i = 10; i < 20; i++) {	ht.delete((Integer)i);	}
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 20); 
		System.out.println("  -Get entries from 10 to 19.");
		for (int i = 10; i < 20; i++) {	assertTrue(ht.get((Integer)i)==null);	}
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Trying to get the rest of the entries.");
		for (int i = 0; i < 10; i++) {	assertTrue(ht.get((Integer)i).equals(i+""));	}
		for (int i = 20; i < 30; i++) {	assertTrue(ht.get((Integer)i).equals(i+""));	}
		System.out.println("  -Successfully access entries.");
		System.out.println("  -Checking if contains entries from 10 to 19.");
		for (int i = 10; i < 20; i++) {	assertTrue(!ht.contains((Integer)i));	}
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Checking if contains the rest of the entries.");
		for (int i = 0; i < 10; i++) {	assertTrue(ht.contains((Integer)i));	}
		for (int i = 20; i < 30; i++) {	assertTrue(ht.contains((Integer)i));	}
		System.out.println("  -Successfully found entries.");
		System.out.println("  -Checking if contains entries out of range.");
		assertTrue(!ht.contains((Integer)100));
		assertTrue(!ht.contains((Integer)1000));
		assertTrue(!ht.contains((Integer)10000));
		assertTrue(!ht.contains((Integer)100000));
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Deleting all entries.");
		for (int i = 0; i < 10; i++) {	ht.delete((Integer)i);	}
		for (int i = 20; i < 30; i++) {	ht.delete((Integer)i);	}
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 0);
		System.out.println("  -Checking hash table if empty.");
		assertEquals(ht.isEmpty(),true);
		System.out.println("  -Trying to get any of the deleted elements.");
		for (int i = 0; i < 30; i++) {	assertTrue(ht.get((Integer)i)==null);	}
		System.out.println("  -Checking if contains the deleted entries.");
		for (int i = 0; i < 30; i++) {	assertTrue(!ht.contains((Integer)i));	}
		
		System.out.println("\n Separate Chaining Test Completed successfully.");
	}
	
	@Ignore
	@Test
	public void bucketing() 
	{
		System.out.println("========================================");
		System.out.println("\n -Bucketing Test:");
		ht = new Bucketing<Integer,String>();
		//ht = new ConstantOverFlowBucketing<Integer,String>();
		System.out.println(" -Initial Test:");
		System.out.println("  -Checking Hash Table if empty.");
		assertEquals(ht.isEmpty(),true);
		System.out.println("  -Checking Hash Table size.");
		assertTrue( ht.tableLength() >= 0 );
		hashSize = ht.tableLength();
		System.out.println("  -Hash Table size is "+hashSize+".");
		
		System.out.println("\n -Insertion and Search Test:");
		System.out.println("  -Inserting 5 entries without collision.");
		for (int i = 0; i < 5; i++) {	ht.put((Integer)i, i+"");	}
		System.out.println("  -Checking size again.");
		assertEquals(ht.isEmpty(),false);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 5);
		System.out.println("  -Making an iterable object.");
		Iterable<Integer> iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue((Integer.parseInt(ht.get(x))==x));	}
		System.out.println("  -Insert 2 entries that causes collisions.");
		ht.put((Integer)11, 11+"");
		ht.put((Integer)13, 13+"");
		System.out.println("  -Checking if the size is doubled.");
		assertTrue(ht.tableLength()<2*hashSize);
		System.out.println("  -Size not doubled as expected.");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 7); 
		System.out.println("  -Insert entry that causes collisions and cause size to be doubled.");
		ht.put((Integer)21, 21+"");
		assertTrue(ht.tableLength()>=2*hashSize);
		hashSize = ht.tableLength();
		System.out.println("  -The new hash table size = "+hashSize+".");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 8); 
		System.out.println("  -Regenerate the iterable object.");
		iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue(Integer.parseInt(ht.get(x))==x);	}
		System.out.println("  -Insert dublicates.");
		for (int i = 0; i < 5; i++) {	ht.put((Integer)i, i+"");	}
		ht.put((Integer)11, 11+"");
		ht.put((Integer)13, 13+"");
		ht.put((Integer)21, 21+"");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 8); 
		
		System.out.println("\n -Deletion and Search Test:");
		System.out.println("  -Deleting some entries."); //11,3
		ht.delete((Integer)11);
		ht.delete((Integer)3);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 6); 
		
		System.out.println("  -Get the deleted entries.");
		assertTrue(ht.get((Integer)11)==null);
		assertTrue(ht.get((Integer)3)==null);
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Trying to get the rest of the entries.");
		for (int i = 0; i < 3; i++) {	assertTrue(ht.get((Integer)i).equals(i+""));	}
		assertTrue(ht.get((Integer)4).equals(4+""));
		assertTrue(ht.get((Integer)13).equals(13+""));
		assertTrue(ht.get((Integer)21).equals(21+""));
		System.out.println("  -Successfully access entries.");
		System.out.println("  -Checking if contains the deleted entries.");
		assertTrue(!ht.contains((Integer)11));
		assertTrue(!ht.contains((Integer)3));
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Checking if contains the rest of the entries.");
		for (int i = 0; i < 3; i++) {	assertTrue(ht.contains((Integer)i)==true);	}
		assertTrue(ht.contains((Integer)4)==true);
		assertTrue(ht.contains((Integer)13)==true);
		assertTrue(ht.contains((Integer)21)==true);
		System.out.println("  -Successfully found entries.");
		System.out.println("  -Checking if contains entries out of range.");
		assertTrue(!ht.contains((Integer)100));
		assertTrue(!ht.contains((Integer)1000));
		assertTrue(!ht.contains((Integer)10000));
		assertTrue(!ht.contains((Integer)100000));
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Deleting all entries.");
		for (int i = 0; i < 3; i++) {	ht.delete((Integer)i);	}
		ht.delete((Integer)4);
		ht.delete((Integer)21);
		ht.delete((Integer)13);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 0);
		System.out.println("  -Checking hash table if empty.");
		assertEquals(ht.isEmpty(),true);
		System.out.println("  -Trying to get any of the deleted elements.");
		for (int i = 0; i < 3; i++) {	assertTrue(ht.get((Integer)i)==null);	}
		assertTrue(ht.get((Integer)4)==null);
		assertTrue(ht.get((Integer)21)==null);
		assertTrue(ht.get((Integer)13)==null);
		assertTrue(ht.get((Integer)11)==null);
		assertTrue(ht.get((Integer)3)==null);
		
		System.out.println("  -Checking if contains the deleted entries.");
		for (int i = 0; i < 3; i++) {	assertTrue(!ht.contains((Integer)i));	}
		assertTrue(!ht.contains((Integer)4));
		assertTrue(!ht.contains((Integer)21));
		assertTrue(!ht.contains((Integer)13));
		assertTrue(!ht.contains((Integer)11));
		assertTrue(!ht.contains((Integer)3));
		
		System.out.println("\n -Full Bucket Test:");
		System.out.println("  -Checking hash table if empty.");
		assertTrue(ht.isEmpty());
		System.out.println("  -Hash size = "+hashSize+".");
		System.out.println("  -Inserting 14 entries in the same bucket.");
		for (int i = 20; i <= 14*20; i+=20) ht.put((Integer)i, i+"");	
		System.out.println("  -Checking if the size is doubled.");
		assertTrue(ht.tableLength()<2*hashSize);
		System.out.println("  -Size not doubled as expected.");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 14); 
		System.out.println("  -One Bucket is full now and the overflow has 1 slots.");
		System.out.println("  -Inserting another entry in the same bucket.");
		ht.put((Integer)15*20, (15*20)+"");
		assertTrue(ht.tableLength()>=2*hashSize);
		hashSize = ht.tableLength();
		System.out.println("  -Hash table size doubled as expected.");
		System.out.println("  -The new hash table size = "+hashSize+".");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 15); 
		System.out.println("  -Regenerate the iterable object.");
		iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator)	assertTrue(Integer.parseInt(ht.get(x))==x);	
		
		System.out.println("\n Bucketing Test Completed successfully.");
	}
	
	@Ignore
	@Test
	public void LinearProbing() 
	{
		System.out.println("========================================");
		System.out.println("\n -Linear Probing Test:");
		ht = new LinearProbing<Integer,String>();
		System.out.println(" -Initial Test:");
		System.out.println("  -Checking Hash Table if empty.");
		assertEquals(ht.isEmpty(),true);
		System.out.println("  -Checking Hash Table size.");
		assertTrue( ht.tableLength() >= 0 );
		hashSize = ht.tableLength();
		System.out.println("  -Hash Table size is "+hashSize+".");
		
		System.out.println("\n -Insertion and Search Test:");
		System.out.println("  -Inserting 5 entries without collision.");
		for (int i = 0; i < 5; i++) {	ht.put((Integer)i, i+"");	}
		System.out.println("  -Checking size again.");
		assertEquals(ht.isEmpty(),false);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 5);  
		System.out.println("  -Making an iterable object.");
		Iterable<Integer> iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue(Integer.parseInt(ht.get(x))==x);	}
		System.out.println("  -Insert 2 entries that causes collisions.");
		ht.put((Integer)11, 11+"");
		ht.put((Integer)13, 13+"");
		System.out.println("  -Checking if the size is doubled.");
		assertTrue(ht.tableLength()<2*hashSize);
		System.out.println("  -Size not doubled as expected.");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 7); 
		System.out.println("  -Insert entry that causes collisions and cause size to be doubled.");
		ht.put((Integer)21, 21+"");
		assertTrue(ht.tableLength()>=2*hashSize);
		hashSize = ht.tableLength();
		System.out.println("  -The new hash table size = "+hashSize+".");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 8); 
		System.out.println("  -Regenerate the iterable object.");
		iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue(Integer.parseInt(ht.get(x))==x);	}
		
		System.out.println("  -Insert dublicates.");
		for (int i = 0; i < 5; i++) {	ht.put((Integer)i, i+"");	}
		ht.put((Integer)11, 11+"");
		ht.put((Integer)13, 13+"");
		ht.put((Integer)21, 21+"");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 8); 
		
		System.out.println("  -Insert 6 entries that causes collisions.");
		for(int i = 20 ; i<7*20 ; i+=20) 	ht.put((Integer)i, i+"");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 14);
		
		System.out.println("\n -Deletion and Search Test:");
		System.out.println("  -Deleting some entries."); 
		for(int i = 2*20 ; i<6*20 ; i+=20) ht.delete((Integer)i);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 10);
		System.out.println("  -Get the deleted entries.");
		for(int i = 2*20 ; i<6*20 ; i+=20) assertTrue(ht.get((Integer)i)==null);
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Trying to get the rest of the entries (Passing by tombstones).");
		for (int i = 0; i < 5; i++) {	assertTrue(ht.get((Integer)i).equals(i+""));	}
		assertTrue(ht.get((Integer)21).equals(21+""));
		assertTrue(ht.get((Integer)20).equals(20+""));
		assertTrue(ht.get((Integer)11).equals(11+""));
		assertTrue(ht.get(13).equals(13+""));
		assertTrue(ht.get((Integer)120).equals(120+""));
		System.out.println("  -Successfully access entries.");
		System.out.println(" -Insert element in a tombstone slot.");
		ht.put((Integer)60, 60+"");
		System.out.println(" -Get all elements in the sequence that contains the tombstone.");
		assertTrue(ht.get((Integer)0).equals(0+""));
		assertTrue(ht.get((Integer)20).equals(20+""));
		assertTrue(ht.get((Integer)120).equals(120+""));
		assertTrue(ht.get(60).equals(60+""));
		System.out.println("  -Checking if contains the deleted entries.");
		for(int i = 2*20 ; i<3*20 ; i+=20) assertTrue(!ht.contains((Integer)i));
		for(int i = 4*20 ; i<6*20 ; i+=20) assertTrue(!ht.contains((Integer)i));
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Checking if contains the rest of the entries.");
		for (int i = 0; i < 5; i++) {	assertTrue(ht.contains((Integer)i));	}
		assertTrue(ht.contains((Integer)21));
		assertTrue(ht.contains(20));
		assertTrue(ht.contains((Integer)11));
		assertTrue(ht.contains(13));
		assertTrue(ht.contains((Integer)120));
		assertTrue(ht.contains((Integer)60));
		System.out.println("  -Successfully found entries.");
		System.out.println("  -Checking if contains entries out of range.");
		assertTrue(!ht.contains((Integer)100));
		assertTrue(!ht.contains((Integer)1000));
		assertTrue(!ht.contains((Integer)10000));
		assertTrue(!ht.contains((Integer)100000));
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Deleting all entries.");
		for (int i = 0; i < 5; i++) {	ht.delete((Integer)i);	}
		ht.delete((Integer)21);
		ht.delete((Integer)20);
		ht.delete((Integer)11);
		ht.delete(13);
		ht.delete((Integer)120);
		ht.delete((Integer)60);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 0);
		System.out.println("  -Checking hash table if empty.");
		assertEquals(ht.isEmpty(),true);
		
		System.out.println("  -Trying to get any of the deleted elements.");
		for (int i = 0; i < 5; i++) {	assertTrue(ht.get((Integer)i)==null);	}
		assertTrue(ht.get((Integer)20)==null);
		assertTrue(ht.get((Integer)21)==null);
		assertTrue(ht.get((Integer)13)==null);
		assertTrue(ht.get((Integer)11)==null);
		assertTrue(ht.get((Integer)120)==null);
		assertTrue(ht.get((Integer)60)==null);
		
		System.out.println("  -Checking if contains the deleted entries.");
		for (int i = 0; i < 5; i++) {	assertTrue(!ht.contains((Integer)i));	}
		assertTrue(!ht.contains((Integer)20));
		assertTrue(!ht.contains((Integer)21));
		assertTrue(!ht.contains((Integer)13));
		assertTrue(!ht.contains((Integer)11));
		assertTrue(!ht.contains((Integer)120));
		assertTrue(!ht.contains((Integer)60));
		
		System.out.println("\n Linear Probing Completed successfully.");
	}
	
	@Ignore
	@Test
	public void QuadraticProbing() 
	{
		System.out.println("========================================");
		System.out.println("\n -Quadratic Probing Test:");
		ht = new QuadraticProbing<Integer,String>();
		System.out.println(" -Initial Test:");
		System.out.println("  -Checking Hash Table if empty.");
		assertEquals(ht.isEmpty(),true);
		System.out.println("  -Checking Hash Table size.");
		assertTrue( ht.tableLength() >= 0 );
		hashSize = ht.tableLength();
		System.out.println("  -Hash Table size is "+hashSize+".");
		
		System.out.println("\n -Insertion and Search Test:");
		System.out.println("  -Inserting 8 entries without collision.");
		for (int i = 0; i < 8; i++) {	ht.put((Integer)i, i+"");	}
		System.out.println("  -Checking size again.");
		assertEquals(ht.isEmpty(),false);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 8);  
		
		System.out.println("  -Making an iterable object.");
		Iterable<Integer> iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue(Integer.parseInt(ht.get(x))==x);	}
		System.out.println("  -Insert 3 entries that causes collisions.");
		ht.put((Integer)11, 11+"");
		ht.put((Integer)12, 12+"");
		ht.put((Integer)13, 13+"");
		System.out.println("  -Checking if the size is doubled.");
		assertTrue(ht.tableLength()<2*hashSize);
		System.out.println("  -Size not doubled as expected.");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 11); 
		System.out.println("  -Insert 1 entry that causes collisions and cause size to be doubled.");
		ht.put((Integer)21, 21+"");
		assertTrue(ht.tableLength()>=2*hashSize);
		hashSize = ht.tableLength();
		System.out.println("  -The new hash table size = "+hashSize+".");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 12);
		System.out.println("  -Regenerate the iterable object.");
		iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue(Integer.parseInt(ht.get(x))==x);	}
		
		System.out.println("  -Insert dublicates.");
		for (int i = 0; i < 8; i++) {	ht.put((Integer)i, i+"");	}
		ht.put((Integer)11, 11+"");
		ht.put((Integer)12, 12+"");
		ht.put((Integer)13, 13+"");
		ht.put((Integer)21, 21+"");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 12); 
		
		System.out.println("  -Insert 7 entries that causes collisions.");
		for(int i = 32 ; i<8*32 ; i+=32) ht.put((Integer)i, i+"");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 19);
		
		
		System.out.println("\n -Deletion and Search Test:");
		System.out.println("  -Deleting some entries."); 
		for(int i = 2*32 ; i<7*32 ; i+=32) ht.delete((Integer)i);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 14); 
		System.out.println("  -Get the deleted entries.");
		for(int i = 2*32 ; i<7*32 ; i+=32) assertTrue(ht.get((Integer)i)==null);
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Trying to get the rest of the entries (Passing by tombstones).");
		for (int i = 0; i < 8; i++) {	assertTrue(ht.get((Integer)i).equals(i+""));	}
		for (int i = 11; i < 14; i++) {	assertTrue(ht.get((Integer)i).equals(i+""));	}
		assertTrue(ht.get((Integer)21).equals(21+""));
		assertTrue(ht.get((Integer)32).equals(32+""));
		assertTrue(ht.get(224).equals(224+""));
		System.out.println("  -Successfully access entries.");
		System.out.println(" -Insert element in a tombstone slot.");
		ht.put((Integer)128, 128+"");
		System.out.println(" -Get all elements in the sequence that contains the tombstone.");
		assertTrue(ht.get((Integer)0).equals(0+""));
		assertTrue(ht.get((Integer)32).equals(32+""));
		assertTrue(ht.get((Integer)128).equals(128+""));
		assertTrue(ht.get(224).equals(224+""));
		
		System.out.println("  -Checking if contains the deleted entries.");
		for(int i = 2*32 ; i<4*32 ; i+=32) {	assertTrue(!ht.contains((Integer)i));	}
		for(int i = 5*32 ; i<7*32 ; i+=32) {	assertTrue(!ht.contains((Integer)i));	}
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Checking if contains the rest of the entries.");
		for (int i = 0; i < 8; i++) {	assertTrue(ht.contains((Integer)i));	}
		for (int i = 11; i < 14; i++) {	assertTrue(ht.contains((Integer)i));	}
		assertTrue(ht.contains(21));
		assertTrue(ht.contains(32));
		assertTrue(ht.contains(128));
		assertTrue(ht.contains(224));
		System.out.println("  -Successfully found entries.");
		System.out.println("  -Checking if contains entries out of range.");
		assertTrue(!ht.contains((Integer)100));
		assertTrue(!ht.contains((Integer)1000));
		assertTrue(!ht.contains((Integer)10000));
		assertTrue(!ht.contains((Integer)100000));
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Deleting all entries.");
		for (int i = 0; i < 8; i++) {	ht.delete((Integer)i);	}
		for (int i = 11; i < 14; i++) {	ht.delete((Integer)i);	}
		ht.delete(21);
		ht.delete(32);
		ht.delete(128);
		ht.delete(224);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 0);
		System.out.println("  -Checking hash table if empty.");
		assertEquals(ht.isEmpty(),true);
		System.out.println("  -Trying to get any of the deleted elements.");
		for(int i = 2*32 ; i<4*32 ; i+=32) {	assertTrue(ht.get((Integer)i)==null);	}
		for(int i = 5*32 ; i<7*32 ; i+=32) {	assertTrue(ht.get((Integer)i)==null);	}
		
		System.out.println("\n Quadratic Probing Completed successfully.");
	}

	@Ignore
	@Test
	public void PseudoRandomProbing() 
	{
		System.out.println("========================================");
		System.out.println("\n -Pseudo Random Probing Test:");
		ht = new PseudoRandomProbing<Integer,String>();
		System.out.println(" -Initial Test:");
		System.out.println("  -Checking Hash Table if empty.");
		assertEquals(ht.isEmpty(),true);
		System.out.println("  -Checking Hash Table size.");
		assertTrue( ht.tableLength() >= 0 );
		hashSize = ht.tableLength();
		System.out.println("  -Hash Table size is "+hashSize+".");
		
		System.out.println("\n -Insertion and Search Test:");
		System.out.println("  -Inserting 5 entries without collision.");
		for (int i = 0; i < 5; i++) {	ht.put((Integer)i, i+"");	}
		System.out.println("  -Checking size again.");
		assertEquals(ht.isEmpty(),false);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 5);  
		System.out.println("  -Making an iterable object.");
		Iterable<Integer> iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue(Integer.parseInt(ht.get(x))==x);	}
		System.out.println("  -Insert 2 entries that causes collisions.");
		ht.put((Integer)11, 11+"");
		ht.put((Integer)13, 13+"");
		System.out.println("  -Checking if the size is doubled.");
		assertTrue(ht.tableLength()<2*hashSize);
		System.out.println("  -Size not doubled as expected.");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 7); 
		System.out.println("  -Insert entry that causes collisions and cause size to be doubled.");
		ht.put((Integer)21, 21+"");
		assertTrue(ht.tableLength()>=2*hashSize);
		hashSize = ht.tableLength();
		System.out.println("  -The new hash table size = "+hashSize+".");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 8); 
		System.out.println("  -Regenerate the iterable object.");
		iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue(Integer.parseInt(ht.get(x))==x);	}
		
		System.out.println("  -Insert dublicates.");
		for (int i = 0; i < 5; i++) {	ht.put((Integer)i, i+"");	}
		ht.put((Integer)11, 11+"");
		ht.put((Integer)13, 13+"");
		ht.put((Integer)21, 21+"");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 8); 
		
		System.out.println("  -Insert 6 entries that causes collisions.");
		for(int i = 20 ; i<7*20 ; i+=20) 	ht.put((Integer)i, i+"");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 14);
		
		System.out.println("\n -Deletion and Search Test:");
		System.out.println("  -Deleting some entries."); 
		for(int i = 2*20 ; i<6*20 ; i+=20) ht.delete((Integer)i);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 10);
		System.out.println("  -Get the deleted entries.");
		for(int i = 2*20 ; i<6*20 ; i+=20) assertTrue(ht.get((Integer)i)==null);
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Trying to get the rest of the entries (Passing by tombstones).");
		for (int i = 0; i < 5; i++) {	assertTrue(ht.get((Integer)i).equals(i+""));	}
		assertTrue(ht.get((Integer)21).equals(21+""));
		assertTrue(ht.get((Integer)20).equals(20+""));
		assertTrue(ht.get((Integer)11).equals(11+""));
		assertTrue(ht.get(13).equals(13+""));
		assertTrue(ht.get((Integer)120).equals(120+""));
		System.out.println("  -Successfully access entries.");
		System.out.println(" -Insert element in a tombstone slot.");
		ht.put((Integer)60, 60+"");
		System.out.println(" -Get all elements in the sequence that contains the tombstone.");
		assertTrue(ht.get((Integer)0).equals(0+""));
		assertTrue(ht.get((Integer)20).equals(20+""));
		assertTrue(ht.get((Integer)120).equals(120+""));
		assertTrue(ht.get(60).equals(60+""));
		System.out.println("  -Checking if contains the deleted entries.");
		for(int i = 2*20 ; i<3*20 ; i+=20) assertTrue(!ht.contains((Integer)i));
		for(int i = 4*20 ; i<6*20 ; i+=20) assertTrue(!ht.contains((Integer)i));
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Checking if contains the rest of the entries.");
		for (int i = 0; i < 5; i++) {	assertTrue(ht.contains((Integer)i));	}
		assertTrue(ht.contains((Integer)21));
		assertTrue(ht.contains(20));
		assertTrue(ht.contains((Integer)11));
		assertTrue(ht.contains(13));
		assertTrue(ht.contains((Integer)120));
		assertTrue(ht.contains((Integer)60));
		System.out.println("  -Successfully found entries.");
		System.out.println("  -Checking if contains entries out of range.");
		assertTrue(!ht.contains((Integer)100));
		assertTrue(!ht.contains((Integer)1000));
		assertTrue(!ht.contains((Integer)10000));
		assertTrue(!ht.contains((Integer)100000));
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Deleting all entries.");
		for (int i = 0; i < 5; i++) {	ht.delete((Integer)i);	}
		ht.delete((Integer)21);
		ht.delete((Integer)20);
		ht.delete((Integer)11);
		ht.delete(13);
		ht.delete((Integer)120);
		ht.delete((Integer)60);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 0);
		System.out.println("  -Checking hash table if empty.");
		assertEquals(ht.isEmpty(),true);
		
		System.out.println("  -Trying to get any of the deleted elements.");
		for (int i = 0; i < 5; i++) {	assertTrue(ht.get((Integer)i)==null);	}
		assertTrue(ht.get((Integer)20)==null);
		assertTrue(ht.get((Integer)21)==null);
		assertTrue(ht.get((Integer)13)==null);
		assertTrue(ht.get((Integer)11)==null);
		assertTrue(ht.get((Integer)120)==null);
		assertTrue(ht.get((Integer)60)==null);
		
		System.out.println("  -Checking if contains the deleted entries.");
		for (int i = 0; i < 5; i++) {	assertTrue(!ht.contains((Integer)i));	}
		assertTrue(!ht.contains((Integer)20));
		assertTrue(!ht.contains((Integer)21));
		assertTrue(!ht.contains((Integer)13));
		assertTrue(!ht.contains((Integer)11));
		assertTrue(!ht.contains((Integer)120));
		assertTrue(!ht.contains((Integer)60));

		System.out.println("\n Pseudo Random Probing Completed successfully.");
	}
	
	@Ignore
	@Test
	public void DoubleHashing() 
	{
		System.out.println("========================================");
		System.out.println("\n -Double Hashing Test:");
		ht = new DoubleHashing<Integer,String>();
		System.out.println(" -Initial Test:");
		System.out.println("  -Checking Hash Table if empty.");
		assertEquals(ht.isEmpty(),true);
		System.out.println("  -Checking Hash Table size.");
		assertTrue( ht.tableLength() >= 0 );
		hashSize = ht.tableLength();
		System.out.println("  -Hash Table size is "+hashSize+".");
		
		System.out.println("\n -Insertion and Search Test:");
		System.out.println("  -Inserting 8 entries without collision.");
		for (int i = 0; i < 8; i++) {	ht.put((Integer)i, i+"");	}
		System.out.println("  -Checking size again.");
		assertEquals(ht.isEmpty(),false);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 8);  
		
		System.out.println("  -Making an iterable object.");
		Iterable<Integer> iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue(Integer.parseInt(ht.get(x))==x);	}
		System.out.println("  -Insert 3 entries that causes collisions.");
		ht.put((Integer)11, 11+"");
		ht.put((Integer)12, 12+"");
		ht.put((Integer)13, 13+"");
		System.out.println("  -Checking if the size is doubled.");
		assertTrue(ht.tableLength()<2*hashSize);
		System.out.println("  -Size not doubled as expected.");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 11); 
		System.out.println("  -Insert 1 entry that causes collisions and cause size to be doubled.");
		ht.put((Integer)21, 21+"");
		assertTrue(ht.tableLength()>=2*hashSize);
		hashSize = ht.tableLength();
		System.out.println("  -The new hash table size = "+hashSize+".");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 12);
		System.out.println("  -Regenerate the iterable object.");
		iterator = ht.keys();
		System.out.println("  -Checking the previously inserted elements using the iterable Object.");
		for (Integer x : iterator) 
		{	assertTrue(Integer.parseInt(ht.get(x))==x);	}
		
		System.out.println("  -Insert dublicates.");
		for (int i = 0; i < 8; i++) {	ht.put((Integer)i, i+"");	}
		ht.put((Integer)11, 11+"");
		ht.put((Integer)12, 12+"");
		ht.put((Integer)13, 13+"");
		ht.put((Integer)21, 21+"");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 12); 
		
		System.out.println("  -Insert 7 entries that causes collisions.");
		for(int i = 32 ; i<8*32 ; i+=32) ht.put((Integer)i, i+"");
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 19);
		
		
		System.out.println("\n -Deletion and Search Test:");
		System.out.println("  -Deleting some entries."); 
		for(int i = 2*32 ; i<7*32 ; i+=32) ht.delete((Integer)i);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 14); 
		System.out.println("  -Get the deleted entries.");
		for(int i = 2*32 ; i<7*32 ; i+=32) assertTrue(ht.get((Integer)i)==null);
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Trying to get the rest of the entries (Passing by tombstones).");
		for (int i = 0; i < 8; i++) {	assertTrue(ht.get((Integer)i).equals(i+""));	}
		for (int i = 11; i < 14; i++) {	assertTrue(ht.get((Integer)i).equals(i+""));	}
		assertTrue(ht.get((Integer)21).equals(21+""));
		assertTrue(ht.get((Integer)32).equals(32+""));
		assertTrue(ht.get(224).equals(224+""));
		System.out.println("  -Successfully access entries.");
		System.out.println(" -Insert element in a tombstone slot.");
		ht.put((Integer)128, 128+"");
		System.out.println(" -Get all elements in the sequence that contains the tombstone.");
		assertTrue(ht.get((Integer)0).equals(0+""));
		assertTrue(ht.get((Integer)32).equals(32+""));
		assertTrue(ht.get((Integer)128).equals(128+""));
		assertTrue(ht.get(224).equals(224+""));
		
		System.out.println("  -Checking if contains the deleted entries.");
		for(int i = 2*32 ; i<4*32 ; i+=32) {	assertTrue(!ht.contains((Integer)i));	}
		for(int i = 5*32 ; i<7*32 ; i+=32) {	assertTrue(!ht.contains((Integer)i));	}
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Checking if contains the rest of the entries.");
		for (int i = 0; i < 8; i++) {	assertTrue(ht.contains((Integer)i));	}
		for (int i = 11; i < 14; i++) {	assertTrue(ht.contains((Integer)i));	}
		assertTrue(ht.contains(21));
		assertTrue(ht.contains(32));
		assertTrue(ht.contains(128));
		assertTrue(ht.contains(224));
		System.out.println("  -Successfully found entries.");
		System.out.println("  -Checking if contains entries out of range.");
		assertTrue(!ht.contains((Integer)100));
		assertTrue(!ht.contains((Integer)1000));
		assertTrue(!ht.contains((Integer)10000));
		assertTrue(!ht.contains((Integer)100000));
		System.out.println("  -Entries not found as expected.");
		System.out.println("  -Deleting all entries.");
		for (int i = 0; i < 8; i++) {	ht.delete((Integer)i);	}
		for (int i = 11; i < 14; i++) {	ht.delete((Integer)i);	}
		ht.delete(21);
		ht.delete(32);
		ht.delete(128);
		ht.delete(224);
		System.out.println("  -Checking number of entries.");
		assertTrue(ht.size() == 0);
		System.out.println("  -Checking hash table if empty.");
		assertEquals(ht.isEmpty(),true);
		System.out.println("  -Trying to get any of the deleted elements.");
		for(int i = 2*32 ; i<4*32 ; i+=32) {	assertTrue(ht.get((Integer)i)==null);	}
		for(int i = 5*32 ; i<7*32 ; i+=32) {	assertTrue(ht.get((Integer)i)==null);	}
		
		System.out.println("\n Double Hashing Completed successfully.");
	}
	

	
	
	private static int insertionEntries = 10000;
	//@Ignore
	@Test
	public void generalTest()
	{
		
		ArrayList<Integer> collisions = new ArrayList<Integer>();
		ArrayList<Integer> memoryUsed = new ArrayList<Integer>();
		Integer[] shuffledArray = new Integer[insertionEntries];
		shuffleArray(shuffledArray);

		System.out.println("separateChaining");
		ht = new SeparateChaining<Integer,String>();
		for(int i = 0 ; i < insertionEntries ; i++)
		{
			ht.put(shuffledArray[i], shuffledArray[i]+"");
			collisions.add(ht.getNumberOfCollisions());
			memoryUsed.add(ht.getMemoryUsed());
		}
		
		System.out.println("memory: "+ht.getMemoryUsed()+" ,collisions: "+ht.getTotalCollison());
		saveToFile(collisions,"SeparateChaining_collisions");
		saveToFile(memoryUsed,"SeparateChaining_memoryUsed");
		
System.out.println("bucketing'");

		collisions = new ArrayList<Integer>();
		memoryUsed = new ArrayList<Integer>();
		ht = new Bucketing<Integer,String>();

		for(int i = 0 ; i < insertionEntries; i++)
		{
			ht.put(shuffledArray[i], shuffledArray[i]+"");
			collisions.add(ht.getNumberOfCollisions());
			memoryUsed.add(ht.getMemoryUsed());
		}
		System.out.println("memory: "+ht.getMemoryUsed()+" ,collisions: "+ht.getTotalCollison());
		saveToFile(collisions,"bucketing_collisions");
		saveToFile(memoryUsed,"bucketing_memoryUsed");
		
	System.out.println("Constant over flow bucketing");
	ht = new ConstantOverFlowBucketing<Integer,String>();
	for(int i = 0 ; i < insertionEntries; i++)
	{
		ht.put(shuffledArray[i], shuffledArray[i]+"");
		collisions.add(ht.getNumberOfCollisions());
		memoryUsed.add(ht.getMemoryUsed());
	}
	System.out.println("memory: "+ht.getMemoryUsed()+" ,collisions: "+ht.getTotalCollison());
	saveToFile(collisions,"constant_bucketing_collisions");
	saveToFile(memoryUsed,"constant_bucketing_memoryUsed");

System.out.println("LinearProbing");

		collisions = new ArrayList<Integer>();
		memoryUsed = new ArrayList<Integer>();
		ht = new LinearProbing<Integer,String>();
		for(int i = 0 ; i < insertionEntries ; i++)
		{
			ht.put(shuffledArray[i], shuffledArray[i]+"");
			collisions.add(ht.getNumberOfCollisions());
			memoryUsed.add(ht.getMemoryUsed());
		}
		System.out.println("memory: "+ht.getMemoryUsed()+" ,collisions: "+ht.getTotalCollison());
		saveToFile(collisions,"LinearProbing_collisions");
		saveToFile(memoryUsed,"LinearProbing_memoryUsed");

System.out.println("QuadraticProbing");

		collisions = new ArrayList<Integer>();
		memoryUsed = new ArrayList<Integer>();
		ht = new QuadraticProbing<Integer,String>();
		for(int i = 0 ; i < insertionEntries ; i++)
		{
			ht.put(shuffledArray[i], shuffledArray[i]+"");
			collisions.add(ht.getNumberOfCollisions());
			memoryUsed.add(ht.getMemoryUsed());
		}
		System.out.println("memory: "+ht.getMemoryUsed()+" ,collisions: "+ht.getTotalCollison());
		saveToFile(collisions,"QuadraticProbing_collisions");
		saveToFile(memoryUsed,"QuadraticProbing_memoryUsed");

		System.out.println("PseudoRandomProbing");
		
		collisions = new ArrayList<Integer>();
		memoryUsed = new ArrayList<Integer>();
		ht = new PseudoRandomProbing<Integer,String>();
		for(int i = 0 ; i < insertionEntries ; i++)
		{
			ht.put(shuffledArray[i], shuffledArray[i]+"");
			collisions.add(ht.getNumberOfCollisions());
			memoryUsed.add(ht.getMemoryUsed());
		}
		System.out.println("memory: "+ht.getMemoryUsed()+" ,collisions: "+ht.getTotalCollison());
		saveToFile(collisions,"PseudoRandomProbing_collisions");
		saveToFile(memoryUsed,"PseudoRandomProbing_memoryUsed");

System.out.println("DoubleHashing");
		
		collisions = new ArrayList<Integer>();
		memoryUsed = new ArrayList<Integer>();
		ht = new DoubleHashing<Integer,String>();
		for(int i = 0 ; i < insertionEntries ; i++)
		{
			ht.put(shuffledArray[i], shuffledArray[i]+"");
			collisions.add(ht.getNumberOfCollisions());
			memoryUsed.add(ht.getMemoryUsed());
		}
		System.out.println("memory: "+ht.getMemoryUsed()+" ,collisions: "+ht.getTotalCollison());
		saveToFile(collisions,"DoubleHashing_collisions");
		saveToFile(memoryUsed,"DoubleHashing_memoryUsed");

	}
	
	public void shuffleArray(Integer array[])
	{
		for(int i = 0 ; i < array.length ; i++)
		{
			array[i] = i;
		}
	    Collections.shuffle(Arrays.asList(array)); //shuffle array
	}

	
	public void saveToFile(ArrayList<Integer> theList,String name)
	{
		/// opening file
		Formatter z = null ;
		try{
			z=new Formatter("C:\\Users\\HP\\Desktop\\"+name+".txt");
		}
		catch(Exception e){
			System.out.println("There is an Error" + e);
		}
		
		// add theList to the File
		for (int i = 0; i <theList.size(); i++) {
			z.format("%s\t", theList.get(i)+"");
		}
		// save and exit
		z.close();	
	}
}
