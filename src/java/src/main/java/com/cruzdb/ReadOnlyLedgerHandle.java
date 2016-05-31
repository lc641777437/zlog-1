
package com.cruzdb;

import java.util.ArrayList;

public class ReadOnlyLedgerHandle extends LedgerHandle{
	//initializes a Read Only Ledger object	
	ReadOnlyLedgerHandle(BookKeeper bk,String name) throws LogException,BKException{
		
		super();
		this.ledgerId = name;
                this.bk = bk;

                if(bk.isBKClosed())
                        throw new BKException("Bookkeeper client is closed\n");
                this.log = Log.openIfExists(bk.getPoolName(),bk.getSeqrServer(),bk.getSeqrPort(),this.ledgerId);
                this.closed = true;
	}
	
	
	//trying to add to a read only ledger
	@Override
	public long addEntry(final byte[] data) throws LogException,BKException{
		//throw exception
		if(bk.isBKClosed())
			throw new BKException("Bookkeeper Client is closed\n");
		throw new LogException("Can not right to a read only log.");
	}
}
