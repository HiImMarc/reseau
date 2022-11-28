package trame;

import analyser.*;
public class HTTP {
	private String data ="";
	
	public HTTP(String trame) {
		data = trame;
	}
	
	public String toString() {
		String res ="";
		res += ""+ Tools.hexToAscii(data);
		res += ""+"----------------------------------------------------\n";
		return res;
	}
	
	public String getHTTP() {
		return data;
	}
	
}
