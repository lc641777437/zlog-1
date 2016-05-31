package com.cruzdb;

import java.util.ArrayList;
import java.util.*;

public class LedgerHandle{
	protected String ledgerId;
	protected BookKeeper bk;
	protected Log log;
	protected Boolean closed;
	protected long lastAddConfirmed;

	LedgerHandle(){
	}
		
	//creates a ledger object with the given name. If one already exists it throws an exception
	LedgerHandle(BookKeeper bk,String name) throws LogException,BKException{
		this.ledgerId = name;
		this.bk = bk;
	
		if(bk.isBKClosed())
			throw new BKException("Bookkeeper client is closed\n");
		this.log = Log.create(bk.getPoolName(),bk.getSeqrServer(),bk.getSeqrPort(),this.ledgerId);
		this.closed = false;
	}
	
	//returns name of the ledger
	public String getId() throws LogException,BKException{
		if(bk.isBKClosed())
			throw new BKException("Bookkeeper client is closed\n");
		return this.ledgerId;
	}

	//add an entry to the ledger
	public long addEntry(final byte[] data) throws LogException,BKException{
		if(bk.isBKClosed())
			throw new BKException("Bookkeeper client is closed\n");
		if(this.isClosed()){
			throw new LogException("Can not write to a closed ledger.\n"); 
		}
		long eid =  log.append(data);
		updateLastConfirmed(eid);
		return eid;
	}

	//returns a single entry
	private byte[] readEntry(long position) throws LogException,BKException{
		if(bk.isBKClosed())
			throw new BKException("Bookkeeper client is closed\n");
		return log.read(position);
	}

	//returns a list of entries
	public Enumeration<byte[]> readEntries(long start,long end)throws LogException,BKException{
		if(bk.isBKClosed())
			throw new BKException("Bookkeeper client is closed\n");
		long position;
		ArrayList<byte[]> entries = new ArrayList<byte[]>();
		
		for(position=start;position<=end;position++){
			entries.add(readEntry(position));
		}
		Enumeration e = Collections.enumeration(entries);
		return e;
	}

	//returns tail of the ledger, that is the position where new entry should be inserted. To get last entry subtract one position from it.
	public long readLastAddConfirmed() throws LogException,BKException{
		if(bk.isBKClosed())
			throw new BKException("Bookkeeper client is closed\n");
		return this.log.tail()-1;
	}
	
	//returns the local state of the ledgerhandle. Wont return the correct value if another client invokes this.
	public Boolean isClosed() throws LogException,BKException{
		if(bk.isBKClosed())
			throw new BKException("Bookkeeper client is closed\n");
		return closed;
	}
	
	//currently it changes the state of a ledger locally
	//TO-DO : implement a feature to close ledger globally. Might involve making changes to zlog.
	public void close() throws LogException,BKException{
		if(bk.isBKClosed())
			throw new BKException("Bookkeeper client is closed\n");
		//later on we need to make some changes to zlog object, to close or and make it readonly. This method won't give the correct value when another client invokes it parallely. 
		if(this.closed){
			throw new LogException("Ledger already closed.");
		}
		this.closed=true;
		return;
	}


	//updates the last add confirmed locally
	private void updateLastConfirmed(long eid){
		this.lastAddConfirmed = eid;
	}

	
	//Originally in bookkeeper it return the local value of the last add confirmed in the LedgerHandle. Need more clarification 
	//TO-DO
	/*public long getLastAddConfirmed(){
		return this.lastConfirmed;
	}*/

		
	//TO-DO : find length of the ledger. Might involve changing zlog.
	/*public long getLength(){
		return this.log.getLength();	
	}*/

	//Get the entry id of the last entry that has been enqueued for addition (but may not have possibly been persited to the ledger)
	//TO-DO
	/*public long getLastPushed(){
		
	}*/
}

