package List4;

import java.util.ListIterator;
import java.util.Scanner;

public class Document{
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;
	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new TwoWayCycledOrderedListWithSentinel<Link>();
		load(scan);
	}
	public void load(Scanner scan) {
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            if(line.equals("eod")) {
                break;
            }
            String[] words = line.split(" ");

            for(String word: words) {
                word = word.toLowerCase();
                if(word.startsWith("link=")) {
                    String link = word.substring(5);
                    Link parsed = createLink(link);

                    if(parsed != null) {
                        this.link.add(parsed);
                    }
                }
            }
        }
	}

	public static boolean isCorrectId(String id) {
		if(id == null || id.length() == 0 || !Character.isLetter(id.charAt(0))) {
            return false;
        }
        for(int i = 1; i < id.length(); i++) {
            if(!Character.isLetterOrDigit(id.charAt(i)) && id.charAt(i) != '_') {
                return false;
            }
        }
		return true;
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	public static Link createLink(String link) {
		int left = link.indexOf('(');

        if(left == -1) {
            if(isCorrectId(link)) {
                return new Link(link);
            }
        } else {
            String linkId = link.substring(0, left);
            int right = link.indexOf(')', left);

            if(right != -1 && isCorrectId(linkId)) {
                String weightText = link.substring(left + 1, right);

                try {
                    int weight = Integer.parseInt(weightText);
                    if(weight > 0) {
                        return new Link(linkId, weight);
                    }
                } catch(NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
	}

	@Override
	public String toString() {
		StringBuilder retStr= new StringBuilder("Document: " + name);

        int count = 0;

        for(Link link: link) {
            if(count % 10 == 0) {
                retStr.append("\n");
            } else {
                retStr.append(" ");
            }

            retStr.append(link.toString());
            count++;
        }

		return retStr.toString();
	}

	public String toStringReverse() {
		StringBuilder retStr= new StringBuilder("Document: " + name);
		ListIterator<Link> iter=link.listIterator();
		while(iter.hasNext())
			iter.next();

        int count = 0;

		while(iter.hasPrevious()){
            if(count % 10 == 0) {
                retStr.append("\n");
            } else {
                retStr.append(" ");
            }
            retStr.append(iter.previous().toString());
            count++;
		}
		return retStr.toString();
	}
}

