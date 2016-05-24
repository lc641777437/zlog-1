package com.cruzdb;

public class TestLedgerWriteRead{

	public static void main(String[] args) throws LogException{
		System.out.println("Creating ledger and adding an entry\n");
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		
		String s = "Test Ledger";
		Ledger l = bk.createLedger(s);
		System.out.println("Ledger created " + l.getLedgerId());
		long entryId = l.addEntry("Dummy Text".getBytes());

		System.out.format("The entry with id %d  is " + new String(l.readEntry(entryId))+ "\n",entryId);
	}
}
