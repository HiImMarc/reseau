package trame;

import analyser.*;
public class Ethernet {
	private static int cpt = 0;
	private final int id;
	private String destination = "";
	private String source = "";
	private String type = "";
	private String data = "";
	private IPv4 ipv4;


	public Ethernet(String trame) {
		cpt++;
		id = cpt;
		destination=""+trame.charAt(0)+trame.charAt(1);
		destination+=""+trame.charAt(3)+trame.charAt(4);
		destination+=""+trame.charAt(6)+trame.charAt(7);
		destination+=""+trame.charAt(9)+trame.charAt(10);
		destination+=""+trame.charAt(12)+trame.charAt(13);
		destination+=""+trame.charAt(15)+trame.charAt(16);
		source=""+trame.charAt(18)+trame.charAt(19);
		source+=""+trame.charAt(21)+trame.charAt(22);
		source+=""+trame.charAt(24)+trame.charAt(25);
		source+=""+trame.charAt(27)+trame.charAt(28);
		source+=""+trame.charAt(30)+trame.charAt(31);
		source+=""+trame.charAt(33)+trame.charAt(34);
		type =""+ trame.charAt(36)+trame.charAt(37);
		type+=""+trame.charAt(39)+trame.charAt(40);
		for (int i = 42; i < trame.length()-1; i+=3){
			data +=""+ trame.charAt(i)+ trame.charAt(i+1);
		}
		doIPv4();
	}
	
	public String toString() {
		String res = "";
		res += "Trame numéro : "+id+"\n";
		res += "Ethernet \n";
		res += "	 Adresse de destination : "
				+destination.charAt(0)+destination.charAt(1)+":"
				+destination.charAt(2)+destination.charAt(3)+":"
				+destination.charAt(4)+destination.charAt(5)+":"
				+destination.charAt(6)+destination.charAt(7)+":"
				+destination.charAt(8)+destination.charAt(9)+":"
				+destination.charAt(10)+destination.charAt(11)+"\n";
		res += "	 Adresse de source : "
				+source.charAt(0)+source.charAt(1)+":"
				+source.charAt(2)+source.charAt(3)+":"
				+source.charAt(4)+source.charAt(5)+":"
				+source.charAt(6)+source.charAt(7)+":"
				+source.charAt(8)+source.charAt(9)+":"
				+source.charAt(10)+source.charAt(11)+"\n";
		if (hasIP()) res += "	 Type : IP Datagram("+type+")\n";
		return res;
	}
	
	public boolean hasIP() {
		return type.equals("0800");
	}
	
	public void doIPv4() {
		if (hasIP()) ipv4=new IPv4(data);
	}

	public String getDestination() {
		return destination;
	}

	public String getSource() {
		return source;
	}

	public String getType() {
		return type;
	}

	public String getData() {
		return data;
	}
	
	public static int getCpt() {
		return cpt;
	}

	public int getId() {
		return id;
	}

	public IPv4 getIpv4() {
		return ipv4;
	}
}
