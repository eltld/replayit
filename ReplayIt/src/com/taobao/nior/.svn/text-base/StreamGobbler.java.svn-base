package com.taobao.nior;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamGobbler extends Thread {
	private InputStream is;
	private IShellOutputReceiver rcvr;

	StreamGobbler(InputStream is) {
		this(is, null);
	}

	StreamGobbler(InputStream is, IShellOutputReceiver rcvr) {
		this.is = is;
		this.rcvr = rcvr;
	}

	public void run() {

		try {
			is = new BufferedInputStream(is);

			int num = 0;
			byte[] bs = new byte[1024];

			while ((num = is.read(bs)) != -1) {
				if (rcvr != null) {
					rcvr.addOutput(bs, 0, num);
				}
			}

			if (rcvr != null)
				rcvr.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
