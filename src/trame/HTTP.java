package trame;

import java.util.ArrayList;
import java.util.List;

import analyser.*;
public class HTTP {
	private String data ="";
	private String toShow="";

	public HTTP(String trame) {
		data = trame;
	}
	
//	public String toString() {
//		String res ="HTTP :\n";
//		res += ""+ Tools.hexToAscii(data);
//		res += ""+"----------------------------------------------------\n";
//		return res;
//	}
	
	public String toString() {
		String[] res =Tools.hexToAscii(data).split("\n");
		if (res[0].length() > 30) return res[0].substring(0, 30)+".....";
		return res[0];
	}
	
	public String getHTTP() {
		return data;
	}
}
