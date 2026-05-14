package List8;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        Random rand = new Random();
        double average;
        int sum = 0;
        int lastSum = 0;

        int lastSumSorted = 0;
        int sumSorted = 0;
        double averageSorted;

        int n = 1000;

        for(int j = 0; j < 100; j++) {
            List<Integer> list = new ArrayList<>();

            for(int i = 0; i < n; i++) {
                int number = rand.nextInt(n);
                while(list.contains(number)) {
                    number = rand.nextInt(n);
                }
                list.add(number);
            }

            BST<Link> bst = new BST<>();

            for(int i = 0; i < n; i++) {
                bst.add(new Link(""+list.get(i), i));
            }

            Collections.sort(list);

            BST<Link> sortedBst = new BST<>();

            for(int i = 0; i < n; i++) {
                sortedBst.add(new Link(""+list.get(i), i));
            }

            sum += bst.getAddComparasions();
            lastSum = bst.getAddComparasions();
            sumSorted += sortedBst.getAddComparasions();
            lastSumSorted = sortedBst.getAddComparasions();
        }

        average = (double) sum / 100;

        averageSorted = (double) sumSorted / 100;

        System.out.println(lastSum);
        System.out.println(average);
        System.out.println();
        System.out.println(lastSumSorted);
        System.out.println(averageSorted);
    }
}
