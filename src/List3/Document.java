package List3;
import java.util.Scanner;

public class Document{
	public String name;
	public TwoWayUnorderedListWithHeadAndTail<Link> link;
	public Document(String name, Scanner scan) {
		this.name=name;
		link=new TwoWayUnorderedListWithHeadAndTail<Link>();
		load(scan);
	}
    public void load(Scanner scan) {
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            if(line.equals("eod")) {
                break;
            }
            String[] words = line.split(" ");
            for(String word : words) {
                if(correctLink(word)) {
                    link.add(new Link(word.substring(5).toLowerCase()));
                }
            }
        }
    }
    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    private static boolean correctLink(String link) {
        //return List1.Document.correctLink(link);
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Document: " + name);
        for (Link link : link) {
            result.append("\n").append(link.toString());
        }

        return result.toString();
    }
	
	public String toStringReverse() {
		String retStr="Document: "+name;
		return retStr+link.toStringReverse();
	}

}
