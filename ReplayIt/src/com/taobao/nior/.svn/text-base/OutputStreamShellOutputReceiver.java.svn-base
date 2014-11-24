package com.taobao.nior;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamShellOutputReceiver implements IShellOutputReceiver {

	OutputStream os;
	
	public OutputStreamShellOutputReceiver(OutputStream os) {
		this.os = os;
	}
	
	public void flush() {
	}
	
	public void addOutput(byte[] buf, int off, int len) {
		try {
			os.write(buf,off,len);
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}