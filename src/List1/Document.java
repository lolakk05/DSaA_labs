package List1;

import java.util.Scanner;

public class Document {
	public static void loadDocument(String name, Scanner scan) {
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            if(line.equals("eod")) {
                break;
            }
            String[] words = line.split(" ");
            for(String word : words) {
                if(correctLink(word)) {
                    System.out.println(word.substring(5).toLowerCase());
                }
            }
        }
	}
	
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	public static boolean correctLink(String link) {
        if(link.length() < 6) {
            return false;
        }

        String loweredLink = link.toLowerCase();

        if(!loweredLink.startsWith("link=") || !Character.isLetter(loweredLink.charAt(5))) {
            return false;
        }

        for (int i = 6; i < link.length(); i++) {
            char c = link.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }

        return true;
	}

}
