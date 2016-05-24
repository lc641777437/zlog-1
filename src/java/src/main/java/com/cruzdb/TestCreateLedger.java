package com.cruzdb;

import java.util.ArrayList;
import java.util.Random;


public class TestCreateLedger{

	public static void main(String args[]) throws LogException{
	
		Bookkeeper bk = new Bookkeeper("rbd","127.0.0.1",5678);
		System.out.println("Creating multiple ledgers\n");
		ArrayList<Ledger> ledgerList = new ArrayList<Ledger>();		
		
		for(int i=0;i<10;i++){
			ledgerList.add(bk.createLedger(String.valueOf(i)));	
		}

		System.out.println("\nAccessing LedgerIds\n");
		for(int i=0;i<10;i++){
			Ledger l = ledgerList.get(i);	
			System.out.println("The ledgerId is " + l.getLedgerId());
		}
	}
}
