package com.cruzdb;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.*;

public class TestOpenCreateLedger{
	
	@Test(expected=LogException.class)
	public void openLedgerThrows() throws LogException,BkException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		System.out.println("Opening ledger\n");
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.openLedger(String.valueOf(n));	
		assertEquals(n,l.getLedgerId());
	}

	@Test
	public void createLedger() throws LogException,BkException{
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.createLedger(String.valueOf(n));	
		assertEquals(n,l.getLedgerId());
		
	}

	@Test(expected=LogException.class)
	public void createLedgerThrows() throws LogException,BkException{
		
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.createLedger(String.valueOf(n));	
		
		Ledger l1 = bk.createLedger(String.valueOf(l.getLedgerId()));
		assertEquals(n,l1.getLedgerId());
	}

	@Test
	public void openLedger() throws LogException,BkException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		
		System.out.println("Creating a ledger\n");
		Ledger l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());
		
		Ledger l1 = bk.openLedger(String.valueOf(l.getLedgerId()));	
		assertEquals(n,l1.getLedgerId());
	}
	

	
	@Test(expected=LogException.class)
	public void openReadOnlyLedgerThrows() throws LogException,BkException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		System.out.println("Opening ledger\n");
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.openReadOnlyLedger(String.valueOf(n));	
		assertEquals(n,l.getLedgerId());
	}

	@Test
	public void openReadOnlyLedger() throws LogException,BkException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		
		System.out.println("Creating a ledger\n");
		Ledger l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());
		
		Ledger l1 = bk.openReadOnlyLedger(String.valueOf(l.getLedgerId()));	
		assertEquals("abc",new String(l1.readEntry(pos)));
	}

}

