package com.cruzdb;

public class LedgerEntry{
	String ledgerId;
	long entryId;
	byte[] entry;
	long length;

	LedgerEntry(String lid,long eid){
		this.ledgerId = lid;
		this.entryId = eid;
	}

	public long getEntryId(){
		return this.entryId;
	}

	public byte[] getEntry(){
		return this.entry;
	}

	public String getLedgerId(){
		return this.ledgerId;
	}

	public long length(){
		return this.length;
	}
}
