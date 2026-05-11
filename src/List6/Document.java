package List6;

import javax.print.Doc;
import java.util.ListIterator;
import java.util.Scanner;

public class Document implements IWithName{
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;
    public final int MOD_VALUE= 100000000;
	public Document(String name) {
		this.name = name;
	}

	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new TwoWayCycledOrderedListWithSentinel<Link>();
		load(scan);
	}
	public void load(Scanner scan) {
        while(scan.hasNextLine()) {
            String line = scan.nextLine();

            if(line.equals("eod")){
                break;
            }

            String[] words = line.split("\\s+");

            for (String word : words) {
                word = word.toLowerCase();
                if(word.startsWith("link=")) {
                    Link link = createLink(word.substring(5));
                    if(link != null) {
                        this.link.add(link);
                    }
                }
            }
        }
	}
	
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	

	public static boolean isCorrectId(String id) {
        if(id == null || id.isEmpty()){
            return false;
        }

        if(!Character.isLetter(id.charAt(0))){
            return false;
        }

        for(int i=1;i<id.length();i++){
            if(!Character.isLetterOrDigit(id.charAt(i)) && id.charAt(i) != '_'){
                return false;
            }
        }

        return true;
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	public static Link createLink(String link) {
        int openBracket = link.indexOf('(');
        int closeBracket = link.indexOf(')');

        if(openBracket == -1) {
            if(isCorrectId(link)){
                return new Link(link);
            }
            return null;
        }

        if(closeBracket > openBracket) {
            String id = link.substring(0, openBracket);
            String weightStr = link.substring(openBracket + 1, closeBracket);

            if(isCorrectId(id)){
                try{
                    int weight = Integer.parseInt(weightStr);
                    if(weight > 0) {
                        return new Link(id, weight);
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
        String retStr="Document: "+name;

        int count = 10;

        for (Link link : this.link) {
            if(count < 10) {
                retStr += link.toString() + " ";
            } else {
                retStr = retStr.trim();
                retStr += "\n" + link.toString() + " ";
                count = 0;
            }
            count++;
        }

        retStr = retStr.trim();
        return retStr;
	}

	public String toStringReverse() {
        String retStr = "Document: " + name;
        ListIterator<Link> iter = link.listIterator();

        while (iter.hasNext())
            iter.next();

        int count = 10;

        while (iter.hasPrevious()) {
            Link link = iter.previous();

            if (count < 10) {
                retStr += link.toString() + " ";
            } else {
                retStr = retStr.trim();
                retStr += "\n" + link.toString() + " ";
                count = 0;
            }
            count++;
        }

        retStr = retStr.trim();
        return retStr;
	}

	@Override
	public String getName() {
		return this.name;
	}

    public int getSize() {
        return this.name.length();
    }

    public int hashCode() {
        int[] data = {7, 11, 13, 17, 19};
        char[] name = this.getName().toCharArray();
        int sum = name[0];
        for(int i = 1; i < this.getSize(); i++) {
            int number = data[(i - 1) % data.length];
            sum = (sum * number + name[i]);
            sum %= MOD_VALUE;
        }
        return sum;
    }

    @Override
    public boolean equals(Object element) {
        if(element instanceof Document) {
            return this.name.equals(((Document) element).name);
        }
        return false;
    }
}

