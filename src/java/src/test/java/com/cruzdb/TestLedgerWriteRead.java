package com.cruzdb;

import java.util.Random;
import java.util.ArrayList;
import java.util.Enumeration;
import static org.junit.Assert.*;
import org.junit.*;

public class TestLedgerWriteRead{

	@Test
	public void readWrite() throws LogException,BKException{
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
	        Random r = new Random();
                String n = "" + r.nextInt();
                LedgerHandle l = bk.createLedger(String.valueOf(n));	
		long entryId = l.addEntry("Dummy Text".getBytes());
		
		Enumeration<byte[]> e = l.readEntries(entryId,entryId);
		assertEquals("Dummy Text",new String(e.nextElement()));
	}

	
	@Test
	public void readWriteMultiple() throws LogException,BKException{
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
	        Random r = new Random();
                String n = "" + r.nextInt();
                LedgerHandle l = bk.createLedger(String.valueOf(n));

		ArrayList<String> input = new ArrayList<String>();

		for(int i = 0 ;i<5;i++){
			input.add("Dummy Text" + String.valueOf(i));
		}

		long firstId = l.addEntry(input.get(0).getBytes());
		long lastId = l.readLastAddConfirmed();

		for(int i=1;i<5;i++){
			lastId = l.addEntry(input.get(i).getBytes());
		}
		
		Enumeration<byte[]> readEntries = l.readEntries(firstId,lastId);

		for(int i=0;i<5;i++){	
			assertEquals(input.get(i),new String(readEntries.nextElement()));
		}
	}


	
	@Test
	public void readLastAddConfirmed() throws LogException,BKException{
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
	        Random r = new Random();
                String n = "" + r.nextInt();
                LedgerHandle l = bk.createLedger(String.valueOf(n));
	
		long entryId=-1;	
		for(int i=0;i<5;i++){
			entryId = l.addEntry(("Dummy Text" + String.valueOf(i)).getBytes());
		}	
		assertEquals(entryId,l.readLastAddConfirmed());
	}


	@Test(expected=LogException.class)
	public void writeReadOnlyThrows() throws LogException,BKException{
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
	        Random r = new Random();
                String n = "" + r.nextInt();
                LedgerHandle l = bk.createLedger(String.valueOf(n));	
		System.out.println("Ledger created " + l.getId());

		LedgerHandle l1 = bk.openLedger(n);
		long entryId = l1.addEntry("Dummy Text".getBytes());
	}


	@Test
	public void readReadOnlyLedger() throws LogException,BKException{
		
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
	        Random r = new Random();
                String n = "" + r.nextInt();
                LedgerHandle l = bk.createLedger(String.valueOf(n));	
		System.out.println("Ledger created " + l.getId());

		long entryId = l.addEntry("Dummy Text".getBytes());

		LedgerHandle l1 = bk.openLedger(n);
		Enumeration<byte[]> e = l1.readEntries(entryId,entryId);
		assertEquals("Dummy Text",new String(e.nextElement()));
	}
}
