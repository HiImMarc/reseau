package trame;
import analyser.*;

public class HTTP {
	private String data ="";

	public HTTP(String trame) {
		data = trame;
	}

	public String toString(boolean b) {
		if (b) {
		String[] res =Tools.hexToAscii(data).split("\n");
		if (res[0].length() > 30) return res[0].substring(0, 30)+".....\n";
		return "			 "+res[0];
		} else { 
			return Tools.hexToAscii(data);
		}
	}
	
	public String getHTTP() {
		return data;
	}
}
