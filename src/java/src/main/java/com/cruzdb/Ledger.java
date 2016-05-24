package com.cruzdb;

import java.util.ArrayList;

public class Ledger{
	protected String ledgerId;
	protected Log log;
	protected Boolean closed;
	protected long lastAddConfirmed;
	//initializes a Ledger object
	
	Ledger(String poolname,String seqr_server,int seqr_port,String name) throws LogException{
		this.ledgerId = name;
		this.log = Log.open(poolname,seqr_server,seqr_port,name);
		//this.closed = false;
	}
	

	public String getLedgerId(){
		return this.ledgerId;
	}

	//add an entry to the log
	public long addEntry(final byte[] data) throws LogException{
		//if(this.isClosed()){
			//throw some exception
		//} 
		updateLastAddConfirmed(log.append(data));
		return getLastAddConfirmed();
	}

	//returns a single entry
	public byte[] readEntry(long position) throws LogException{
		return log.read(position);
	} 

	//returns a list of entries
	public ArrayList<byte[]> readEntries(long start,long end)throws LogException{
		long position;
		ArrayList<byte[]> entries = new ArrayList<byte[]>();
		
		for(position=start;position<=end;position++){
			entries.add(readEntry(position));
		}
		return entries;
	}

	//returns tail of the log, that is the position where new entry should be inserted. To get last entry subtract one position from this...
	public long readLastAddConfirmed() throws LogException{
		return this.log.tail();
	}

	public long getLastAddConfirmed(){
		return lastAddConfirmed;
	}

	protected void updateLastAddConfirmed(long entryID){
		lastAddConfirmed = entryID;
	}

	/*public Boolean isClosed(){
		return closed;
	}*/

	/*public void close(){
		if(this.closed){
			//some exception about log already closed.
			return;
		}
		this.closed=true;
		return;
	}*/

	
	
}

