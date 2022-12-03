package analyser;
import trame.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Analyser {

	public static List<String> execute(String filePath) throws IOException {
		
		List<String> trameFinale = Tools.getCleanFrame(filePath);
		String res = "";
		List<String> list = new ArrayList<>();

		//On traite toutes les trames valides
		StringBuilder textBuilder = new StringBuilder();
		for (String trame : trameFinale) {
			res="";
			Ethernet e = new Ethernet(trame);
//				System.out.print(e);
			res+=""+e.toString();
			textBuilder.append(e.toString());
			IPv4 ip = e.getIpv4();
			if (ip == null) {
				System.out.println("Pas de IPv4");
				res +="Pas de IPv4\n";
				textBuilder.append("Pas de IPv4\n");
				list.add(res);
				continue;
			}
//				System.out.print(ip);
			textBuilder.append(ip.toString());
			res+=""+ip.toString();
			TCP tcp = ip.getTCP();
			if (tcp == null) {
//					System.out.println("Pas de TCP");
				textBuilder.append("Pas de TCP\n");
				list.add(res);
				continue;
			}
//				System.out.println(tcp);
			textBuilder.append(tcp.toString());
			res+=""+tcp.toString();
			HTTP http = tcp.getHTTP();
			if (http == null) {
//				System.out.println("Pas de HTTP");
				textBuilder.append("Pas de HTTP\n");
				list.add(res);
				continue;
			}
//				System.out.println(http);
			res+=""+http.toString(false);
			textBuilder.append(http.toString());
			list.add(res);
		}
		String text = textBuilder.toString();
		Tools.ecrire(text);
		return list;

	}
}
