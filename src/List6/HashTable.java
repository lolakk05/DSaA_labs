package List6;

import java.util.LinkedList;

public class HashTable {
    LinkedList arr[]; // use pure array
    private final static int defaultInitSize = 4;
    private final static double defaultMaxLoadFactor = 0.7;
    private int size;
    private final double maxLoadFactor;

    public HashTable() {
        this(defaultInitSize);
    }

    public HashTable(int size) {
        this(size, defaultMaxLoadFactor);
    }


    public HashTable(int initCapacity, double maxLF) {
        this.arr = new LinkedList[initCapacity];
        fillEmptyArrayWithLists();
        this.maxLoadFactor = maxLF;
    }

    public boolean add(Object elem) {
        int index = elem.hashCode() % this.arr.length;
        if (isAlreadyInHashTable(index, elem)) {
            return false;
        }
        this.arr[index].add(elem);
        this.size++;

        return true;
    }

    public void reHashToPrimeSize() {
        LinkedList[] old = this.arr;
        int newSize = old.length * 2;
        while (!isPrime(newSize)) {
            newSize++;
        }
        this.arr = new LinkedList[newSize];
        this.size = 0;
        fillEmptyArrayWithLists();
        for(LinkedList list: old) {
            for(Object element: list) {
                int index = element.hashCode() % this.arr.length;
                this.arr[index].add(element);
                this.size += 1;
            }
        }
    }

    public void optimize() {
        int[] maxBucketValues = getLongestBucket();
        int maxSize = maxBucketValues[1];
        int sumOfBucketSize = 0;
        for (LinkedList list : this.arr) {
            sumOfBucketSize += list.size();
        }
        double averageBucketSize = (double) sumOfBucketSize / this.arr.length;
        System.out.println("srednia: " + averageBucketSize + " suma: " + sumOfBucketSize + " rozmiar: " + this.arr.length);
        if(averageBucketSize * 2< maxSize) {
            System.out.println("srednia * 2: " + averageBucketSize * 2 + " max: " + maxSize);
            reHashToPrimeSize();
        }
    }

    public boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for(int i = 2; i < n; i++) {
            if(n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public Object get(Object toFind) {
        int index = toFind.hashCode() % this.arr.length;
        LinkedList list = this.arr[index];
        return getElement(list, toFind);
    }

    public Object getElement(LinkedList list, Object toFind) {
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

    public boolean isAlreadyInHashTable(int index, Object elem) {
        return this.arr[index].contains(elem);
    }

    public int[] getLongestBucket() {
        int index = -1;
        int size = 0;
        int maxIndex = 0;
        for (LinkedList list : this.arr) {
            index++;
            if (list.size() > size) {
                size = list.size();
                maxIndex = index;
            }
        }
        return new int[]{maxIndex, size};
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
}



