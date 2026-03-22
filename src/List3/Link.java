package List3;

public class Link{
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
}