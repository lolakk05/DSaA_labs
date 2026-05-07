package List3;

public class Link implements Comparable<Link>{
    public String ref;
    public Link(String ref) {
        this.ref=ref;
    }
    // in the future there will be more fields

    @Override
    public String toString() {
        return ref;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Link) {
            return ref.equals(((Link) obj).ref);
        }
        return false;
    }

    @Override
    public int compareTo(Link o) {
        return this.getSize() - o.getSize();
    }

    public int getSize() {
        return this.ref.length();
    }


}