package com.cruzdb;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.*;

public class TestOpenCreateLedger{
	
	@Test(expected=NullPointerException.class)
	public void openLedgerThrows() throws NullPointerException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		System.out.println("Opening ledger\n");
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.openLedger(String.valueOf(n));	

		System.out.println("The ledgerId is " + l.getLedgerId()+"\n");
	}

	@Test
	public void createLedger() throws NullPointerException,LogException{
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.createLedger(String.valueOf(n));	
		System.out.println("The ledgerId is " + l.getLedgerId()+"\n");
		
	}

	@Test(expected=NullPointerException.class)
	public void createLedgerThrows() throws NullPointerException,LogException{
		
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.createLedger(String.valueOf(n));	
		
		Ledger l1 = bk.createLedger(String.valueOf(l.getLedgerId()));	
		System.out.println("The ledgerId is " + l1.getLedgerId()+"\n");
	}

	@Test
	public void openLedger() throws LogException, NullPointerException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		
		System.out.println("Creating a ledger\n");
		Ledger l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());
		
		Ledger l1 = bk.openLedger(String.valueOf(l.getLedgerId()));	

		System.out.println("The ledgerId is " + l1.getLedgerId() + " and the first entry is " + new String(l1.readEntry(pos)) + "\n");
	}
	

	
	@Test(expected=NullPointerException.class)
	public void openReadOnlyLedgerThrows() throws NullPointerException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		System.out.println("Opening ledger\n");
		Random r = new Random();
		String n = "" + r.nextInt();
		Ledger l = bk.openReadOnlyLedger(String.valueOf(n));	

		System.out.println("The ledgerId is " + l.getLedgerId()+"\n");
	}

	@Test
	public void openReadOnlyLedger() throws LogException, NullPointerException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		Random r = new Random();
		String n = "" + r.nextInt();
		
		System.out.println("Creating a ledger\n");
		Ledger l = bk.createLedger(String.valueOf(n));	
		long pos = l.addEntry("abc".getBytes());
		
		Ledger l1 = bk.openReadOnlyLedger(String.valueOf(l.getLedgerId()));	

		System.out.println("The ledgerId is " + l1.getLedgerId() + " and the first entry is " + new String(l1.readEntry(pos)) + "\n");
	}

}

