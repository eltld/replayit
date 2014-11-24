package com.taobao.nior;

import java.util.ArrayList;
import java.util.List;

public class MultiLineShellReceiver extends MultiLineReceiver {
	private List<String> output = new ArrayList<String>();

	@Override
	public void processNewLines(String[] lines) {
		for (String line : lines) {
			output.add(line);
		}
	}

	public List<String> getOutput() {
		return output;
	}

	public void setOutput(List<String> output) {
		this.output = output;
	}

}