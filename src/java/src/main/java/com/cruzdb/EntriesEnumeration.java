package com.cruzdb;

import java.util.*;

public class EntriesEnumeration implements Enumeration<LedgerEntry>{
	private long firstId;
	private long lastId;
	private long currentId;
	private LedgerHandle lh;
	EntriesEnumeration(long first,long last,LedgerHandle l){
		this.firstId = first;
		this.lastId = last;
		this.currentId = first;
		this.lh = l;
	}

	public boolean hasMoreElements(){
		if(currentId<=lastId & currentId>=firstId){
			return true;
		}
		else{
			return false;
		}
	}

	public LedgerEntry nextElement() throws NoSuchElementException{
		try{
			byte[] data = lh.log.read(currentId);
			LedgerEntry entry = new LedgerEntry(lh.getId(),currentId);
			entry.entry = data;
			entry.length = data.length;	
			currentId++;
			return entry;
		}catch(Exception e){
			e.printStackTrace();
			throw new NoSuchElementException();
		}
	}
}
