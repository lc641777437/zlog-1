package com.cruzdb;

import java.util.ArrayList;
import java.util.Random;
import java.util.Enumeration;
import static org.junit.Assert.*;
import org.junit.*;

public class TestBkClientClosed{
	
	@Test(expected=BKException.class)
	public void openLedgerThrows() throws LogException,BKException{
	
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		Random r = new Random();
		String n = "" + r.nextInt();
		LedgerHandle l = bk.createLedger(String.valueOf(n));
		bk.close();
		LedgerHandle l1 = bk.openLedger(String.valueOf(n));
		assertEquals(n,l1.getId());
	}

	
	@Test(expected=BKException.class)
	public void createLedgerthrows() throws LogException,BKException{
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		bk.close();
		Random r = new Random();
		String n = "" + r.nextInt();
		LedgerHandle l = bk.createLedger(String.valueOf(n));	
		assertEquals(n,l.getId());
		
	}

	@Test(expected=BKException.class)
	public void addEntryThrows() throws LogException,BKException{
		
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		LedgerHandle l = bk.createLedger(String.valueOf(n));	
		bk.close();
		long pos = l.addEntry("abc".getBytes());	
	}

	
	@Test(expected=BKException.class)
	public void readEntryThrows() throws LogException,BKException{
		
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		LedgerHandle l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());	
		bk.close();
		Enumeration<LedgerEntry> e = l.readEntries(pos,pos);
		String entry = new String(e.nextElement().getEntry());
		assertEquals("abc",entry);	
	}

	@Test(expected=BKException.class)
	public void getIdThrows() throws LogException,BKException{
	
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		
		LedgerHandle l = bk.createLedger(String.valueOf(n));	
		bk.close();
		String lid = l.getId();	
		assertEquals(n,lid);
	}
	

	
	@Test(expected=BKException.class)
	public void readLastAddConfirmedThrows() throws LogException,BKException{
	
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		LedgerHandle l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());	
		bk.close();
		long lac = l.readLastAddConfirmed();	
		assertEquals(pos,lac);
	}

	@Test(expected=BKException.class)
	public void isClosedThrows() throws LogException,BKException{
	
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		
		LedgerHandle l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());
		l.close();
		bk.close();
		boolean isClosed = l.isClosed();
		assertEquals(true,isClosed);
	}

	
	@Test(expected=BKException.class)
	public void CloseThrows() throws LogException,BKException{
	
		BookKeeper bk = new BookKeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		
		LedgerHandle l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());
		bk.close();
		l.close();
		boolean isClosed = l.isClosed();
		assertEquals(true,isClosed);
	}


}

