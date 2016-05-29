package com.cruzdb;

import java.util.Random;

import static org.junit.Assert.*;
import org.junit.*;

public class TestLedgerWriteRead{

	@Test
	public void readWrite() throws LogException,BkException{
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
	        Random r = new Random();
                String n = "" + r.nextInt();
                Ledger l = bk.createLedger(String.valueOf(n));	
		System.out.println("Ledger created " + l.getLedgerId());
		long entryId = l.addEntry("Dummy Text".getBytes());
		assertEquals("Dummy Text",new String(l.readEntry(entryId)));
	}


	@Test(expected=LogException.class)
	public void writeReadOnlyThrows() throws LogException,BkException{
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
	        Random r = new Random();
                String n = "" + r.nextInt();
                Ledger l = bk.createLedger(String.valueOf(n));	
		System.out.println("Ledger created " + l.getLedgerId());

		Ledger l1 = bk.openReadOnlyLedger(n);
		long entryId = l1.addEntry("Dummy Text".getBytes());
	}


	@Test
	public void readReadOnlyLedger() throws LogException,BkException{
		
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
	        Random r = new Random();
                String n = "" + r.nextInt();
                Ledger l = bk.createLedger(String.valueOf(n));	
		System.out.println("Ledger created " + l.getLedgerId());

		long entryId = l.addEntry("Dummy Text".getBytes());

		Ledger l1 = bk.openReadOnlyLedger(n);
		assertEquals("Dummy Text",new String(l1.readEntry(entryId)));
	}
}
