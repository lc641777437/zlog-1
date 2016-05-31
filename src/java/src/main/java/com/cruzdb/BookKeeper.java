package com.cruzdb;

public class BookKeeper{
	private String poolname;
	private String seqr_server;
	private int seqr_port;
	private boolean closed = false;

	/*
	* BookKeeper constructor.Takes zlog poolname, sequencer server and sequencer port as arguments
	*/
	public BookKeeper(String poolname,String seqr_server, int seqr_port){
		this.poolname = poolname;
		this.seqr_server = seqr_server;
		this.seqr_port = seqr_port;
	}

	//Returns a safe Ledger Handle. Safe means it can not be edited.
	//Originally in bookkeeper this method also closes the Ledger and prevents all clients from writing to it.
	//Currently only the client using this method to open a ledger cannot edit the Ledger.

	//TO-DO : implement closing ledger when using this method		
	public LedgerHandle openLedger(String name) throws LogException,BKException{
		if(this.isBKClosed())
			throw new BKException("Bookkeeper client is closed\n");
		LedgerHandle l = new ReadOnlyLedgerHandle(this,name);
		return l;
	}

	//In BookKeeper this method allows user to read the ledger without closing it. Its unsafe because any other client maybe writing to the ledger and the reads might not be consistent.
	public LedgerHandle openLedgerNoRecovery(String name) throws LogException,BKException{
		if(this.isBKClosed())
			throw new BKException("Bookkeeper client is closed\n");
		LedgerHandle l = new ReadOnlyLedgerHandle(this,name);
		return l;
	}

	//creates a ledger
	public LedgerHandle createLedger(String name) throws LogException,BKException{
		if(this.isBKClosed())
			throw new BKException("Bookkeeper client is closed\n");
		LedgerHandle l = new LedgerHandle(this,name);
		return l;
	}

	
	public String getPoolName(){
		return this.poolname;
	}

	public String getSeqrServer(){
		return this.seqr_server;
	}

	public int getSeqrPort(){
		return this.seqr_port;
	}

	//closes the bookkeeper client
	public void close(){
		this.closed = true;
	}

	//checks if ledger with lid is closed
	//TO-DO: implement closing a ledger and check its state
	public boolean isClosed(String lid){
		return false;	
	}

	//checks if a client is closed.
	public boolean isBKClosed(){
		return this.closed;
	}

	//deletes a ledger
	//TO-DO : implement deleting a ledger functionality	
	public void deleteLedger(String name){

	}

}
