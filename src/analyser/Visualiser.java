package analyser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import trame.*;


public class Visualiser {

	public static List<String> execute() throws IOException {
		String file = "data/test6.txt";
		try {
			int cpt = -1;
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			StringBuilder trames = new StringBuilder();
			//On met dans un String le contenu du fichier avec 16 octets par ligne
			while ((line = br.readLine())!=null){
				if (cpt%16==0) {
					cpt = -1;
					trames.append("\n");
				}
				cpt++;
				trames.append(line);
			}
			br.close();
			
			//On sépare les différentes trames
			//stringTrames ne sert qu'à passer de StringBuilder à String
			String stringTrames = trames.toString();
			//On sépare les différentes trames pour les différencier
			String[] splitedTrames = stringTrames.split("0000   ");
			
			//Pour chaque trame, on va supprimer l'offset et garder les données utiles
			//On crée une liste de trames que l'on va traiter
			List<String> tramesTotale = new ArrayList<>();
			
			//Pour chaque dirty trame (càd avec offset), on va la clean
			for (int i = 1; i < splitedTrames.length ;i++) {
				// tableau contenant tout les éléments d'une trame (càd offset et donnée)
				String[] tmp = splitedTrames[i].split("   |\\\n"); //On va séparer la trame entre "___" et "\n"
				//On stocke dans trameFinale le contenu utile
				StringBuilder tmp2 = new StringBuilder();

				//Pour chaque élément de tmp utile, on va la rajouter à tmp2
				for(int j = 0; j < tmp.length; j+=2) {
					tmp2.append(tmp[j]+"\n");
				}
				//conversion StringBuilder to String
				String tmp2bis = ""+tmp2.toString();
				//On ajoute la trame épurée finale à notre liste de trames à traiter
				tramesTotale.add(tmp2bis);
			}

			
			//On traite toutes les trames valides
			List<Entry<String,String>> pairs = new ArrayList<>();
			AbstractMap.SimpleEntry<String, String> curPair;
			AbstractMap.SimpleEntry<String, String> curPair2;
			/**
			 * On fait tout les couples d'adresses à traiter
			 */
			for (String trame : tramesTotale) {
				Ethernet e = new Ethernet(trame);
				IPv4 ip = e.getIpv4();
				if (ip == null) {
					continue;
				}
				curPair = new AbstractMap.SimpleEntry<>(ip.getSourceAdress(), ip.getDestinationAdress());
				curPair2 = new AbstractMap.SimpleEntry<>(ip.getDestinationAdress(), ip.getSourceAdress());

				if (!pairs.contains(curPair) && !pairs.contains(curPair2)) pairs.add(curPair);
			}
//			/**
//			 * AFFICHAGE TEST
//			 */
//			for (Entry<String, String> couple : pairs)
//			System.out.println("LES PAIRES SONT : "+Tools.hexToAddressIP(couple.getKey())+" ET "
//			+Tools.hexToAddressIP(couple.getValue()));
			
			//Les adresses courantes
			String curSourceIP="";
			String curDestinationIP="";
			
			//Les trames initialisées qu'on va utiliser 
			Ethernet e;
			IPv4 ip;
			TCP tcp;
			HTTP http;
			
			//La chaine finale qu'on affiche pour chaque paire d'IP qui communiquent
			String res = "";
			
			//Le port qu'on va afficher à gauche
			String leftPort = "";
			
			List<String> list = new ArrayList<>();
			
			
			//TANT QU'ON A DES PAIRES A TRAITER
//			System.out.println("PAIRS ARE : "+pairs+"\n\n\n");
			int i = 1;
			while (!pairs.isEmpty()) {
				boolean traffic = false;
				res ="";
				leftPort = "";
				curSourceIP = pairs.get(0).getKey();
				curDestinationIP = pairs.get(0).getValue();
				res = ""+curSourceIP+"							"+curDestinationIP+"\n";
				for (String trame : tramesTotale) {
					//On crée l'ethernet et l'ip pour avoir les adresses IP qui communiquent
					res = "";
					e = new Ethernet(trame);
					ip = e.getIpv4();
					if (ip == null) continue;
					
//					System.out.println("CUR SOURCE IP :"+curSourceIP);
//					System.out.println("CUR DEST IP : "+curDestinationIP);
//					System.out.println("SOURCE IP : "+ip.getSourceAdress());
//					System.out.println("DESTINATION IP : "+ip.getDestinationAdress());
					
					if ((ip.getSourceAdress().equals(curSourceIP) && ip.getDestinationAdress().equals(curDestinationIP))
					|| (ip.getSourceAdress().equals(curDestinationIP) && ip.getDestinationAdress().equals(curSourceIP))){
//						System.out.println("ON EST RENTRE");
						tcp = ip.getTCP();
						if (tcp == null) continue;
						traffic = true;
						if (leftPort.equals(""))leftPort = tcp.getSourcePort();
						res+="	     "+leftPort+" -> "+tcp.getDestinationPort()+" ";
						if (tcp.getUrg().equals("1")) res+="[URG] ";
						if (tcp.getAck().equals("1")) res+="[ACK] ";
						if (tcp.getPsh().equals("1")) res+="[PSH] ";
						if (tcp.getRst().equals("1")) res+="[RST] ";
						if (tcp.getSyn().equals("1")) res+="[SYN] ";
						if (tcp.getFin().equals("1")) res+="[FIN] ";
						res+="Seq="+tcp.getSequenceNumber()+" ";
						res+="Ack="+tcp.getAcknowNumber()+" ";
						res+="Win="+tcp.getWindow()+"\n";
						if (tcp.getSourcePort().equals(leftPort)) {
							res+=""+leftPort+" | ----------------------------------------------------------------> | "+tcp.getDestinationPort()+"\n";	
						} else {
							res+=""+tcp.getDestinationPort()+"| <---------------------------------------------------------------- | "+tcp.getSourcePort()+"\n";
						}
						http = tcp.getHttp();
						if (http == null) {
							res+="\n\n\n";
	//						System.out.print(res);
						} else {
						res+="			"+http.toString();
						res+="\n\n\n";
//						System.out.print(res);
						}
					}
					list.add(res);
					res="";
				}
				if (!traffic) res+=""+"			Pas de traffic reconnaissable";
				System.out.print(res);
				pairs.remove(0);
			}
			return list;

//			String firstPort = "";
//			e = new Ethernet(tramesTotale.get(0));
//			ip = e.getIpv4();
//			if (!(ip == null)) tcp = ip.getTCP();
//			else tcp = null;
//			if (!(tcp == null)) firstPort = tcp.getSourcePort();
//			res = ""+ip.getSourceAdress()+"					             "+ip.getDestinationAdress()+"\n";
//			System.out.println(res);
//			for (String trame : tramesTotale) {
//				e = new Ethernet(trame);
//				ip = e.getIpv4();
//				if (ip == null) continue;
//				tcp = ip.getTCP();
//				if (tcp == null) continue;
//				if (tcp.getSourcePort().equals(firstPort))
//					res="	"+tcp.getSourcePort()+" -> "+tcp.getDestinationPort()+" ";
//				else 
//					res="	"+tcp.getDestinationPort()+" -> "+tcp.getSourcePort()+" ";
//				if (tcp.getUrg().equals("1")) res+="[URG] ";
//				if (tcp.getAck().equals("1")) res+="[ACK] ";
//				if (tcp.getPsh().equals("1")) res+="[PSH] ";
//				if (tcp.getRst().equals("1")) res+="[RST] ";
//				if (tcp.getSyn().equals("1")) res+="[SYN] ";
//				if (tcp.getFin().equals("1")) res+="[FIN] ";
//				res+="Seq="+tcp.getSequenceNumber()+" ";
//				res+="Ack="+tcp.getAcknowNumber()+" ";
//				res+="Win="+tcp.getWindow()+"\n";
//				if (tcp.getSourcePort().equals(firstPort)) {
//					res+=""+firstPort+" | ----------------------------------------------------------------> | "+tcp.getDestinationPort()+"\n";
//				} else {
//					res+=""+tcp.getDestinationPort()+"| <---------------------------------------------------------------- | "+tcp.getSourcePort()+"\n";
//
//				}
//				http = tcp.getHttp();
//				if (http == null) {
//					res+="\n\n";
//					System.out.print(res);
//				} else {
//					res+="			"+http.toString();
//					res+="\n\n";
//					System.out.print(res);
//				}
//			}
		} catch (FileNotFoundException e) {
			System.out.println("Erreur lors de l'ouverture du fichier");
			e.getMessage();
		}
		return null;
	}
}
