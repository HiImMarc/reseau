package analyser;
import trame.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Analyser {

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
			List<String> trameFinale = new ArrayList<>();
			
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
				trameFinale.add(tmp2bis);
			}

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
//					System.out.println("Pas de IPv4");
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
				res+=""+http.toString();
				textBuilder.append(http.toString());
				list.add(res);
				System.out.println("TAILE DE LA LISTE :"+list.size());
			}
			String text = textBuilder.toString();
			Tools.ecrire(text);
			return list;

		} catch (FileNotFoundException e) {
			System.out.println("Erreur lors de l'ouverture du fichier");
			e.getMessage();
		}
		return null;
	}
}
