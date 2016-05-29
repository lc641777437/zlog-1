package com.cruzdb;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.*;

public class TestBkClientClosed{
	
	@Test(expected=BkException.class)
	public void openLedgerThrows() throws LogException,BkException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.createLedger(String.valueOf(n));
		bk.close();
		Ledger l1 = bk.openLedger(String.valueOf(n));
		assertEquals(n,l1.getLedgerId());
	}

	
	@Test(expected=BkException.class)
	public void openReadOnlyLedgerThrows() throws LogException,BkException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.createLedger(String.valueOf(n));
		bk.close();
		Ledger l1 = bk.openReadOnlyLedger(String.valueOf(n));
		assertEquals(n,l1.getLedgerId());
	}

	@Test(expected=BkException.class)
	public void createLedgerthrows() throws LogException,BkException{
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		bk.close();
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.createLedger(String.valueOf(n));	
		assertEquals(n,l.getLedgerId());
		
	}

	@Test(expected=BkException.class)
	public void addEntryThrows() throws LogException,BkException{
		
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.createLedger(String.valueOf(n));	
		bk.close();
		long pos = l.addEntry("abc".getBytes());	
		assertEquals("abc",new String(l.readEntry(pos)));	
	}

	
	@Test(expected=BkException.class)
	public void addEntryReadOnlyThrows() throws LogException,BkException{
		
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.createLedger(String.valueOf(n));
		Ledger l1 = bk.openReadOnlyLedger(String.valueOf(n));
		bk.close();
		long pos = l1.addEntry("abc".getBytes());	
		assertEquals("abc",new String(l1.readEntry(pos)));	
	}

	@Test(expected=BkException.class)
	public void readEntryThrows() throws LogException,BkException{
		
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());	
		bk.close();
		String entry = new String(l.readEntry(pos));
		assertEquals("abc",entry);	
	}

	@Test(expected=BkException.class)
	public void getLedgerIdThrows() throws LogException,BkException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		
		Ledger l = bk.createLedger(String.valueOf(n));	
		bk.close();
		String lid = l.getLedgerId();	
		assertEquals(n,lid);
	}
	

	
	@Test(expected=BkException.class)
	public void readLastAddConfirmedThrows() throws LogException,BkException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());	
		bk.close();
		long lac = l.readLastAddConfirmed();	
		assertEquals(pos,lac);
	}

	@Test(expected=BkException.class)
	public void isClosedThrows() throws LogException,BkException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		
		Ledger l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());
		l.close();
		bk.close();
		boolean isClosed = l.isClosed();
		assertEquals(true,isClosed);
	}

	
	@Test(expected=BkException.class)
	public void CloseThrows() throws LogException,BkException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		
		Ledger l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());
		bk.close();
		l.close();
		boolean isClosed = l.isClosed();
		assertEquals(true,isClosed);
	}


}

