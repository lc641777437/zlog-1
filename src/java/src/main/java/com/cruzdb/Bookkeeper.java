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
	
	public Ledger createLedger(String name) throws LogException{
		return new Ledger(this.poolname,this.seqr_server,this.seqr_port,name);
	}

	/*public Ledger openLedger(String name){
		Ledger ledger = new Ledger(this.poolname,this.seqr_server,this.seqr_port,name);
		ledger.close();
		return ledger;
	}

	public Ledger openReadOnlyLedger(String name){
		return new ReadOnlyLedger(this.poolname,this.seqr_server,this.seqr_port,name);
	}

	public void deleteLedger(){

	}

	public Boolean isClosed(String name){
		return new ReadOnlyLedger
	}*/
	
}
