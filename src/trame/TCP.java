package trame;

import analyser.*;

public class TCP {
	private String sourcePort="";
	private String destinationPort="";
	private String sequenceNumber="";
	private String acknowNumber="";
	private String thl="";
	
	private String reservedToFin="";
	private String reserved="";
	private String urg="";
	private String ack="";
	private String psh="";
	private String rst="";
	private String syn="";
	private String fin="";
	
	private String window="";
	private String checkSum="";
	private String urgentPointer="";

	private int tailleTotale;
	private String optionList="";

	private String data="";
	private HTTP http;
	
	public TCP(String trame) {
		tailleTotale = trame.length();
		
		sourcePort+="" + trame.charAt(0)+trame.charAt(1);
		sourcePort+="" + trame.charAt(2)+trame.charAt(3);
		sourcePort = ""+Integer.parseInt(sourcePort, 16);
		
		destinationPort+="" + trame.charAt(4)+trame.charAt(5);
		destinationPort+="" + trame.charAt(6)+trame.charAt(7);
		destinationPort = ""+Integer.parseInt(destinationPort, 16);

		sequenceNumber+="" + trame.charAt(8)+trame.charAt(9);
		sequenceNumber+="" + trame.charAt(10)+trame.charAt(11);
		sequenceNumber+="" + trame.charAt(12)+trame.charAt(13);
		sequenceNumber+="" + trame.charAt(14)+trame.charAt(15);
		sequenceNumber = "" + Long.parseLong(sequenceNumber, 16);

		acknowNumber+="" + trame.charAt(16)+trame.charAt(17);
		acknowNumber+="" + trame.charAt(18)+trame.charAt(19);
		acknowNumber+="" + trame.charAt(20)+trame.charAt(21);
		acknowNumber+="" + trame.charAt(22)+trame.charAt(23);
		acknowNumber = "" + Long.parseLong(acknowNumber, 16);

		thl+="" + trame.charAt(24);
		
		reservedToFin+=""+trame.charAt(25)+trame.charAt(26);
		reservedToFin+=""+trame.charAt(27);
		
		String chaine =""+ Tools.hexToBin(reservedToFin);
		
		//On remplit reserved en bits
		for (int i = 0; i <6 ;i++) {
			reserved += ""+chaine.charAt(i);
		}
		
		urg+=""+chaine.charAt(6);
		ack+=""+chaine.charAt(7);
		psh+=""+chaine.charAt(8);
		rst+=""+chaine.charAt(9);
		syn+=""+chaine.charAt(10);
		fin+=""+chaine.charAt(11);
		
		window+="" + trame.charAt(28)+trame.charAt(29);
		window+="" + trame.charAt(30)+trame.charAt(31);
		
		checkSum+=""+trame.charAt(32)+trame.charAt(33);
		checkSum+=""+trame.charAt(34)+trame.charAt(35);
		
		urgentPointer+=""+trame.charAt(36)+trame.charAt(37);
		urgentPointer+=""+trame.charAt(38)+trame.charAt(39);
		
		int debutData = 40;
		int nbOptions = 0;
		if (hasOptions()) {
			nbOptions = getNbOptions();
			debutData = 40 + nbOptions*8;
		}

		for (int j = debutData; j < trame.length(); j++){
			data +="" + trame.charAt(j);
		}
		doHTTP();
	}

	public String toString() {
		String res ="";
		res +="TCP\n";
		res += "	   Source Port : "+sourcePort+"\n";
		res += "	   Destination Port : "+destinationPort+"\n";
		res += "	   Sequence Number : "+"0x"+sequenceNumber+"\n";
		res += "	   Acknowledgment Number : "+acknowNumber+"\n";
		res += "	   Transport Header Length : "+"0x"+thl+"\n";
		res += "	   Reserved : "+"0x"+reserved+"\n";
		res += "	   Flags TCP :\n";
		res += "	     Urgent Data : "+Integer.parseInt(urg, 16)+"\n";
		res += "	     Acknowledgment : "+Integer.parseInt(ack,16)+"\n";
		res += "	     Push : "+Integer.parseInt(psh, 16)+"\n";
		res += "	     Reset : "+Integer.parseInt(rst, 16)+"\n";
		res += "	     Synchronisation : "+Integer.parseInt(syn, 16)+"\n";
		res += "	     Fin : "+Integer.parseInt(fin, 16)+"\n";
		res += "	   Window : "+"0x"+window+"\n";
		res += "	   Checksum : "+checkSum+"\n";
		res += "	   Urgent Pointer : "+urgentPointer+"\n";
		return res;
	}
	
	public void doHTTP() {
		if (hasHTTP()) http = new HTTP(data);
	}
	
	public HTTP getHTTP() {
		return http;
	}
	
	public boolean hasHTTP() {
		return ( destinationPort.equals("80")||sourcePort.equals("80")) && data.length() > 10;
	}
	
	public int getDataLength() {
		int tailleEntete = Integer.parseInt(thl, 16);
		return tailleTotale - tailleEntete;
	}
	public boolean hasOptions() {

		return Integer.parseInt(thl, 16) - 5 > 0;
	}
	
	public int getNbOptions() {
		return Integer.parseInt(thl,16) - 5;
	}
	
	public int optionLength() {
		return 5 -Integer.parseInt(thl,16);
	}
	
	public String getSourcePort() {
		return sourcePort;
	}

	public String getDestinationPort() {
		return destinationPort;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public String getAcknowNumber() {
		return acknowNumber;
	}

	public String getThl() {
		return thl;
	}

	public String getReservedToFin() {
		return reservedToFin;
	}

	public String getReserved() {
		return reserved;
	}

	public String getUrg() {
		return urg;
	}

	public String getAck() {
		return ack;
	}

	public String getPsh() {
		return psh;
	}

	public String getRst() {
		return rst;
	}

	public String getSyn() {
		return syn;
	}

	public String getFin() {
		return fin;
	}

	public String getWindow() {
		return window;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public String getUrgentPointer() {
		return urgentPointer;
	}

	public String getData() {
		return data;
	}

	public HTTP getHttp() {
		return http;
	}

	public int getTailleTotale() {
		return tailleTotale;
	}

}
