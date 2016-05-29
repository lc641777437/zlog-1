package com.cruzdb;

public class Bookkeeper{
	
	private String poolname;
	private String seqr_server;
	private int seqr_port;
	private boolean closed = false;

	
	public Bookkeeper(String poolname,String seqr_server, int seqr_port){
		this.poolname = poolname;
		this.seqr_server = seqr_server;
		this.seqr_port = seqr_port;
	}
	
	public Ledger openLedger(String name) throws LogException,BkException{
		if(this.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		Ledger l = new Ledger(this,name);
		l.open();
		return l;
	}


	public Ledger openReadOnlyLedger(String name) throws LogException,BkException{
		if(this.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		Ledger l = new ReadOnlyLedger(this,name);
		l.open();
		return l;
	}

	public Ledger createLedger(String name) throws LogException,BkException{
		if(this.isClosed())
			throw new BkException("Bookkeeper client is closed\n");
		Ledger l = new Ledger(this,name);
		l.create();
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

	public void close(){
		this.closed = true;
	}

	public void open(){
		this.closed = false;
	}

	public boolean isClosed(){
		return this.closed;
	}

	/*
	public void deleteLedger(){

	}
	*/
	
}
