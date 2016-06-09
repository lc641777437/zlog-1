package com.cruzdb;

import java.util.ArrayList;
import java.util.Random;
import java.util.Enumeration;
import static org.junit.Assert.*;
import org.junit.*;

public class TestOpenCreateLedger{
	
	@Test(expected=LogException.class)
	public void openLedgerThrows() throws LogException,BKException{
	
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		
		System.out.println("Opening ledger\n");
		Random r = new Random();
		String n = "" + r.nextInt();
		LedgerHandle l = bk.openLedger(String.valueOf(n));	
		assertEquals(n,l.getId());
	}

	@Test
	public void createLedger() throws LogException,BKException{
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		LedgerHandle l = bk.createLedger(String.valueOf(n));	
		assertEquals(n,l.getId());
		
	}

	@Test(expected=LogException.class)
	public void createLedgerThrows() throws LogException,BKException{
		
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		LedgerHandle l = bk.createLedger(String.valueOf(n));	
		
		LedgerHandle l1 = bk.createLedger(String.valueOf(l.getId()));
		assertEquals(n,l1.getId());
	}

	@Test
	public void openLedger() throws LogException,BKException{
	
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		
		System.out.println("Creating a ledger\n");
		LedgerHandle l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());
		
		LedgerHandle l1 = bk.openLedger(String.valueOf(l.getId()));	
		assertEquals(n,l1.getId());
		Enumeration<LedgerEntry> e = l1.readEntries(pos,pos);
		assertEquals("abc",new String(e.nextElement().getEntry()));
	}
}

