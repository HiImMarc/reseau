package trame;

import analyser.Tools;

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
	private String data="";
	private HTTP http;
	private int tailleTotale;
	
	public TCP(String trame) {
		tailleTotale = trame.length();
		sourcePort+="" + trame.charAt(0)+trame.charAt(1);
		sourcePort+="" + trame.charAt(2)+trame.charAt(3);
		
		destinationPort+="" + trame.charAt(4)+trame.charAt(5);
		destinationPort+="" + trame.charAt(6)+trame.charAt(7);

		sequenceNumber+="" + trame.charAt(8)+trame.charAt(9);
		sequenceNumber+="" + trame.charAt(10)+trame.charAt(11);
		sequenceNumber+="" + trame.charAt(12)+trame.charAt(13);
		sequenceNumber+="" + trame.charAt(14)+trame.charAt(15);
		
		acknowNumber+="" + trame.charAt(16)+trame.charAt(17);
		acknowNumber+="" + trame.charAt(18)+trame.charAt(19);
		acknowNumber+="" + trame.charAt(20)+trame.charAt(21);
		acknowNumber+="" + trame.charAt(22)+trame.charAt(23);
		
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
		
		int dataLength = getDataLength();
		int debutData = 40 + optionLength()*8;
		System.out.print("PAAAA"+dataLength);
		for (int i = debutData; i < dataLength - 1; i+=1){
			data +="" + trame.charAt(i);
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
		http = new HTTP(data);
	}
	
	public HTTP getHTTP() {
		return http;
	}
	
	public int optionLength() {
		return 5-Integer.parseInt(thl,16);
	}
	
	public int getDataLength() {
		int nbthl = Integer.parseInt(thl, 16);
		return tailleTotale - nbthl;
	}
	
	
	
	
	
	
	
	
	
}
