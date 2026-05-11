package List8;

import java.util.Scanner;

public class Document implements IWithName{
	public String name;
	public BST<Link> link;
    public final int MOD_VALUE = 100000000;
	public Document(String name) {
		this.name=name.toLowerCase();
		link=new BST<Link>();
	}

	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new BST<Link>();
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
	static Link createLink(String link) {
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
		String retStr="Document: "+name+"\n";
		retStr+=link.toStringInOrder();		
		return retStr;
	}

	public String toStringPreOrder() {
		String retStr="Document: "+name+"\n";
		retStr+=link.toStringPreOrder();
		return retStr;
	}

	public String toStringPostOrder() {
		String retStr="Document: "+name+"\n";
		retStr+=link.toStringPostOrder();
		return retStr;
	}
	
	@Override
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

    public int getSize() {
        return this.name.length();
    }

	@Override
	public String getName() {
		return name;
	}
}
