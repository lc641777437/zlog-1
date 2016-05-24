
package com.cruzdb;

import java.util.ArrayList;

public class ReadOnlyLedger extends Ledger{
	private long lastAddConfirmed;
	//initializes a Ledger object
	
	ReadOnlyLedger(String poolname, String seqr_server,int seqr_port,String name) throws LogException{
		super(poolname,seqr_server,seqr_port,name);
	}
	
	
	//trying to add to a read only log
	@Override
	public long addEntry(final byte[] data) throws LogException{
		//throw exception
		throw new LogException("Can not right to a read only log.");
	}

	//returns a single entry
	@Override
	public byte[] readEntry(long position) throws LogException{
		return this.log.read(position);
	} 

	//returns a list of entries
	@Override
	public ArrayList<byte[]> readEntries(long start,long end)throws LogException{
		long position;
		ArrayList<byte[]> entries = new ArrayList<byte[]>();
		
		for(position=start;position<=end;position++){
			entries.add(readEntry(position));
		}
		return entries;
	}
}
