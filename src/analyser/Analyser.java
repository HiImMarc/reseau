package analyser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import trame.*;

public class Analyser {
	
	public static Boolean isAlphaNumeric(String s) {
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (!(c >= 'A' && c <= 'Z') &&
                    !(c >= 'a' && c <= 'z') &&
                    !(c >= '0' && c <= '9')) {
            	return false;
            }
        }
        return true;
    }
	public static Boolean isAlpha(char c) {
	   return (!(c >= 'A' && c <= 'Z') &&
	            !(c >= 'a' && c <= 'z'));
    }
 

	public boolean DecodableOffset(String offset) {
		if (offset.length() < 2 || ! Analyser.isAlphaNumeric(offset)) {
			return false;
		}
		boolean bool = false;

		for (int i = 0; i < offset.length(); i++) {
			if (Analyser.isAlpha(offset.charAt(i))) {
				char[] tab = {'a','b','c','d','e','f'};
				for (int j = 0; j < 6; j++) {
					if (Character.toLowerCase(offset.charAt(i)) == tab[j] ) {
						bool = true;
					}
				}
			}
		}
		return bool;
	}

	public static void main(String args[]) throws IOException {
		String file = "data/test5.txt";
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
				//On ajoute la trame propre finale à notre liste de trames à traiter
				trameFinale.add(tmp2bis);
			}

			for (String trame : trameFinale) {
				Ethernet e = new Ethernet(trame);
				IPv4 ip = e.getIpv4();
				TCP tcp = ip.getTCP();
				HTTP http = tcp.getHTTP();
				System.out.print(e);
				System.out.print(ip);
				System.out.println(tcp);
				System.out.println(http);
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR");
			e.getCause();
			
		}		
	}
}
