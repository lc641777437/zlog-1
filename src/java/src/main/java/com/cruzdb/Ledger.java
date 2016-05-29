package com.cruzdb;

import java.util.ArrayList;

public class Ledger{
	protected String ledgerId;
	protected Bookkeeper bk;
	protected Log log;
	protected Boolean closed;
	protected long lastAddConfirmed;
	
	//initializes a Ledger object
	Ledger(Bookkeeper bk,String name) throws LogException{
		this.ledgerId = name;
		this.bk = bk;
	}
	
	//initializes the log object with an already exisiting ledger
	public void open() throws LogException,BkException{	
		if(bk.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		this.log = Log.openIfExists(bk.getPoolName(),bk.getSeqrServer(),bk.getSeqrPort(),this.ledgerId);
		this.closed = false;
	 }

	//creates a new ledger
	public void create() throws LogException,BkException{	
		if(bk.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		this.log = Log.create(bk.getPoolName(),bk.getSeqrServer(),bk.getSeqrPort(),this.ledgerId);
		this.closed = false;
	}


	//returns name of the ledger
	public String getLedgerId() throws LogException,BkException{
		if(bk.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		return this.ledgerId;
	}

	//add an entry to the ledger
	public long addEntry(final byte[] data) throws LogException,BkException{
		if(bk.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		if(this.isClosed()){
			throw new LogException("Can not write to a closed ledger.\n"); 
		}
		this.closed=false;
		return log.append(data);
	}

	//returns a single entry
	public byte[] readEntry(long position) throws LogException,BkException{
		if(bk.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		return log.read(position);
	}

	//returns a list of entries
	public ArrayList<byte[]> readEntries(long start,long end)throws LogException,BkException{
		if(bk.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		long position;
		ArrayList<byte[]> entries = new ArrayList<byte[]>();
		
		for(position=start;position<=end;position++){
			entries.add(readEntry(position));
		}
		return entries;
	}

	//returns tail of the log, that is the position where new entry should be inserted. To get last entry subtract one position from this...
	public long readLastAddConfirmed() throws LogException,BkException{
		if(bk.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		return this.log.tail();
	}

	public Boolean isClosed() throws LogException,BkException{
		if(bk.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		return closed;
	}

	public void close() throws LogException,BkException{
		if(bk.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		//later on we need to make some changes to zlog object, to close or and make it readonly. This method won't give the correct value when another client invokes it parallely. 
		if(this.closed){
			throw new LogException("Ledger already closed.");
		}
		this.closed=true;
		return;
	}
	
}

