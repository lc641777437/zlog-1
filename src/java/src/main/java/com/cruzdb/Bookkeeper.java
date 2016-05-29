package com.cruzdb;

public class Bookkeeper{
	
	private String poolname;
	private String seqr_server;
	private int seqr_port;
	
	public Bookkeeper(String poolname,String seqr_server, int seqr_port){
		this.poolname = poolname;
		this.seqr_server = seqr_server;
		this.seqr_port = seqr_port;
	}
	
	public Ledger openLedger(String name) throws NullPointerException{
		try{
			Ledger l = new Ledger(this,name);
			l.open();
			return l;
		}catch(LogException e){
			System.out.format("Ledger with name %s does not exist\n",name);
			return null;
		}
	}



	public Ledger openReadOnlyLedger(String name) throws NullPointerException{
		try{
			Ledger l = new ReadOnlyLedger(this,name);
			l.open();
			return l;
		}catch(LogException e){
			System.out.format("Ledger with name %s does not exist\n",name);
			return null;
		}
	}

	public Ledger createLedger(String name) throws NullPointerException{
		try{
			Ledger l = new Ledger(this,name);
			l.create();
			return l;
		}catch(LogException e){
			System.out.format("Ledger with name %s already exists\n\n",name);
			return null;
		}
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


	/*
	public void deleteLedger(){

	}
	*/
	
}
