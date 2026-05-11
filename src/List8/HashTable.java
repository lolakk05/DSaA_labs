package List8;

import java.util.LinkedList;

public class HashTable<T> {
	LinkedList<T> arr[];
	private final static int defaultInitSize=8;
	private final static double defaultMaxLoadFactor=0.7;
	private int size;	
	private final double maxLoadFactor;
	public HashTable() {
		this(defaultInitSize);
	}
	public HashTable(int size) {
		this(size,defaultMaxLoadFactor);
	}

    @SuppressWarnings("unchecked")
	public HashTable(int initCapacity, double maxLF) {
		if(initCapacity<2)
			initCapacity=2;
		arr=(LinkedList<T>[]) new LinkedList[initCapacity];
        fillEmptyArrayWithLists();
		this.maxLoadFactor = maxLF;
	}

    public boolean add(T elem) {
        int index = elem.hashCode() % this.arr.length;
        if (isAlreadyInHashTable(index, elem)) {
            return false;
        }
        this.arr[index].add(elem);
        this.size++;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.arr.length; i++) {
            sb.append(i);
            sb.append(":");
            if (!this.arr[i].isEmpty()) {
                sb.append(" ");
            }
            boolean isFirst = true;

            for (Object element : this.arr[i]) {
                if (!isFirst) {
                    sb.append(", ");
                }

                IWithName named = (IWithName) element;
                sb.append(named.getName());
                isFirst = false;
            }

            sb.append("\n");
        }
        return sb.toString();
    }

    public Object get(T toFind) {
        int index = toFind.hashCode() % this.arr.length;
        LinkedList<T> list = this.arr[index];
        return getElement(list, toFind);
    }

    public Object getElement(LinkedList<T> list, T toFind) {
        for (Object element : list) {
            if (element.equals(toFind)) {
                return element;
            }
        }
        return null;
    }

    public void fillEmptyArrayWithLists() {
        for (int i = 0; i < this.arr.length; i++) {
            this.arr[i] = new LinkedList<>();
        }
    }

    public boolean isAlreadyInHashTable(int index, T elem) {
        return this.arr[index].contains(elem);
    }
	
}

