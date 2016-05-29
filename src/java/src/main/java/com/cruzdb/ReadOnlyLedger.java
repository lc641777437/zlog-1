
package com.cruzdb;

import java.util.ArrayList;

public class ReadOnlyLedger extends Ledger{
	private long lastAddConfirmed;
	//initializes a Ledger object
	
	ReadOnlyLedger(Bookkeeper bk,String name) throws LogException{
		super(bk,name);
	}
	
	
	//trying to add to a read only log
	@Override
	public long addEntry(final byte[] data) throws LogException{
		//throw exception
		throw new LogException("Can not right to a read only log.");
	}
}
