package analyser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import exception.*;

public abstract class Tools {
	
	//Convertie un nombre hexa en binaire et renvoie le binaire en string
    public static String hexToBin(String hex){

        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }
	
	public static String hexToAddressIP(String s) {

		String c1=""+s.charAt(0);
		c1+=""+s.charAt(1);
		int d1 =Integer.parseInt(c1,16);
		
		String c2=""+s.charAt(2);
		c2+=""+s.charAt(3);
		int d2 = Integer.parseInt(c2,16);
		
		String c3=""+s.charAt(4);
		c3+=""+s.charAt(5);
		int d3 = Integer.parseInt(c3,16);
		
		String c4=""+s.charAt(6);
		c4+=""+s.charAt(7);
		int d4 = Integer.parseInt(c4,16);
		
		String res=""+ d1+"."+d2+"."+d3+"."+d4;
		return res;
	}
	
	public static String hexToAscii(String hexStr) {

		
	    StringBuilder output = new StringBuilder("");
	    
	    for (int i = 0; i < hexStr.length(); i += 2) {
	        String str = hexStr.substring(i, i + 2);
	        output.append((char) Integer.parseInt(str, 16));
	    }
	    
	    return output.toString();
	}
	
	public static Boolean isAlphaNumeric(String s) {
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (!(c >= 'A' && c <= 'F') &&
                    !(c >= 'a' && c <= 'f') &&
                    !(c >= '0' && c <= '9')) {
            	return false;
            }
        }
        return true;
    }	
	
	public static Boolean isAlphaNumericSpace(String s) {
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (!(c >= 'A' && c <= 'F') &&
                    !(c >= 'a' && c <= 'f') &&
                    !(c >= '0' && c <= '9') &&
            		!(c == ' ')){
            	return false;
            }
        }
        return true;
    }	
	
	
	public static boolean decodableOffset(String offset) {
		if (offset.length() != 4 || ! isAlphaNumeric(offset)) {
			return false;
		}
		return true;
	}
	
	public static void ecrire(String trame) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("Resultat.txt", "UTF-8");
		writer.println(trame);
		writer.close();
	}
	
	public static List<String> getCleanFrame(String filePath){
		try {
			int cpt = -1;
			BufferedReader br = new BufferedReader(new FileReader(filePath));
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
				try {
					for(int j = 0; j < tmp.length; j+=2) {
						if (j+1 >= tmp.length) {
							break;
						}
						if (!decodableOffset(tmp[j+1])) {
							throw new IllegalOffsetException("Mauvais offset");
						}
						tmp2.append(tmp[j]+"\n");
					}
				} catch(IllegalOffsetException o) {
					System.out.println(o.getMessage());
					System.exit(-1);
				}	
				
				//conversion StringBuilder to String
				String tmp2bis = ""+tmp2.toString();
				//On ajoute la trame épurée finale à notre liste de trames à traiter
				trameFinale.add(tmp2bis);
			}
			return trameFinale;

		} catch (Exception e) {
	
		}
		return null;
	}
}
