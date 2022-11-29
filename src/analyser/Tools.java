package analyser;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
	
	public static boolean DecodableOffset(String offset) {
		if (offset.length() < 2 || ! isAlphaNumeric(offset)) {
			return false;
		}
		boolean bool = false;

		for (int i = 0; i < offset.length(); i++) {
			if (isAlpha(offset.charAt(i))) {
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
	
	public static void ecrire(String trame) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("data/Resultat.txt", "UTF-8");
		writer.println(trame);
		writer.close();
	}

	
}
