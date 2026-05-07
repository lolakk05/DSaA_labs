package List5;

import java.util.*;

public class Document{
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> links;
	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		links=new TwoWayCycledOrderedListWithSentinel<Link>();
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
                        this.links.add(link);
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

        for (Link link : this.links) {
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
        ListIterator<Link> iter = links.listIterator();

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
	public int[] getWeights() {
        int[] array = new int[links.size];
        int index = 0;
        for(Link link: links) {
            array[index] = link.weight;
            index++;
        }
        return array;
	}

	public static void showArray(int[] arr) {
        if (arr == null || arr.length == 0) return;

		for(int i = 0; i < arr.length-1; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println(arr[arr.length - 1]);
	}

	void bubbleSort(int[] arr) {
		showArray(arr);
        int inversions = countInversions(arr);
        System.out.println("Inwersje: " + inversions);
        int lastInversions;
		for(int i = 0; i < arr.length - 1; i++) {
            lastInversions = inversions;
            for(int j = arr.length - 1; j > 0; j--) {
                if(arr[j] < arr[j-1]) {
                    swap(arr, arr[j], j, j-1);
                }
            }
            inversions = countInversions(arr);
            countInversionsRemovedInLastStepAndDisplayArray(inversions, lastInversions, arr);
        }
	}

	public void insertSort(int[] arr) {
		showArray(arr);
        int inversions = countInversions(arr);
        int lastInversions;
        System.out.println("Inwersje: " + inversions);
        for(int i = arr.length - 2; i >= 0; i--) {
            lastInversions = inversions;
            int temp = arr[i];
            int j = i + 1;
            while(j < arr.length && temp > arr[j]) {
                arr[j-1] = arr[j];
                j++;
            }
            arr[j-1] = temp;
            inversions = countInversions(arr);
            countInversionsRemovedInLastStepAndDisplayArray(inversions, lastInversions, arr);
        }
	}
	public void selectSort(int[] arr) {
		showArray(arr);
        int inversions = countInversions(arr);
        int lastInversions;
		for(int i = arr.length - 1; i > 0; i--) {
            int max = i;
            lastInversions = inversions;
            for(int j = i; j >= 0; j--) {
                if(arr[j] > arr[max]) {
                    max = j;
                }
            }
            swap(arr, arr[i], i, max);
            inversions = countInversions(arr);
            countInversionsRemovedInLastStepAndDisplayArray(inversions, lastInversions, arr);
        }
	}

    public void swap(int[] arr, int temp, int index1, int index2) {
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    public int countInversions(int[] arr) {
        int inversions = 0;
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr.length; j++) {
                if (arr[i] > arr[j] && i < j) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    public void countInversionsRemovedInLastStepAndDisplayArray(int inversions, int lastInversions, int[] arr) {
        showArray(arr);
        System.out.println("Inwersje: " + inversions);
        System.out.println("O ile mniej: " + (lastInversions - inversions));
    }

    //lista 6

    public int getMax(int[] array) {
        int max = 0;
        for (int j : array) {
            if (j > max) {
                max = j;
            }
        }
        return max;
    }

    public void radixSort(int[] array) {
        if (array.length <= 1) return;
        int max = getMax(array);
        showArray(array);
        for(int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(array, exp);
            showArray(array);
        }
    }

    public void countingSort(int[] array, int exp) {
        int[] countArray = countArray(array, exp);
        sumCountArray(countArray);
        int[] resultArray = sortArray(array, exp, countArray);
        writeArray(array, resultArray);
    }

    public int[] countArray(int[] array, int exp) {
        int[] countArray = new int[10];
        for (int j : array) {
            int number = (j / exp) % 10;
            countArray[number] += 1;
        }
        return countArray;
    }

    public void sumCountArray(int[] array) {
        for(int i = 1; i < array.length; i++) {
            array[i] += array[i-1];
        }
    }

    public int[] sortArray(int[] array, int exp, int[] countArray) {
        int[] resultArray = new int[array.length];
        for(int i = array.length-1; i >= 0; i--) {
            int number = (array[i] / exp) % 10;
            resultArray[countArray[number] - 1] = array[i];
            countArray[number]--;
        }
        return resultArray;
    }

    public void writeArray(int[] array, int[] resultArray) {
        for(int i = 0; i < array.length; i++) {
            array[i] = resultArray[i];
        }
    }

    //merge sort 1

    public void iterativeMergeSort(int[] arr) {
        if (arr.length <= 1) return;
        showArray(arr);
        for(int width = 1; width < arr.length; width *= 2) {
            mergeWidth(width, arr);
        }
    }

    public void mergeWidth(int width, int[] arr) {
        for(int left = 0; left < arr.length; left += 2 * width) {
            int mid = Math.min(left + width, arr.length);
            int right = Math.min(left + 2 * width, arr.length);

            merge(arr, left, mid, right);
        }
        showArray(arr);
    }

    public void merge(int[] array, int left, int mid, int right) {
        int leftSize = mid - left;
        int rightSize = right - mid;

        int[] leftArray = fillSubarray(left, leftSize, array);
        int[] rightArray = fillSubarray(mid, rightSize, array);

        int leftIndex = 0;
        int rightIndex = 0;
        int currentArrayIndex = left;

        while(elementsAvailableToMerge(leftIndex, leftSize, rightIndex, rightSize)) {
            if(compareSubarrayElements(leftArray, rightArray, leftIndex, rightIndex)) {
                array[currentArrayIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                array[currentArrayIndex] = rightArray[rightIndex];
                rightIndex++;
            }
            currentArrayIndex++;
        }

        fillRestOfSubarrayElements(leftIndex, leftArray, array, currentArrayIndex);
        fillRestOfSubarrayElements(rightIndex, rightArray, array, currentArrayIndex);
    }

    public boolean elementsAvailableToMerge(int leftIndex, int leftSize, int rightIndex, int rightSize) {
        return leftIndex < leftSize && rightIndex < rightSize;
    }

    public boolean compareSubarrayElements(int[] leftArray, int[] rightArray, int leftIndex, int rightIndex) {
        return leftArray[leftIndex] <= rightArray[rightIndex];
    }

    public int[] fillSubarray(int start, int size, int[] array) {
        int[] arr = new int[size];
        for(int i = 0; i < size; i++) {
            arr[i] = array[start];
            start++;
        }
        return arr;
    }

    public void fillRestOfSubarrayElements(int index, int[] fillArray, int[] array, int currentIndex) {
        while(index < fillArray.length) {
            array[currentIndex] = fillArray[index];
            index++;
            currentIndex++;
        }
    }

    //merge sort 2

    //    public void iterativeMergeSort(int[] arr) {
//        if (arr.length == 0) return;
//        showArray(arr);
//        Queue<int[]> queue = new ArrayDeque<>();
//        for (int j : arr) {
//            queue.add(new int[]{j});
//        }
//        int width = 1;
//        while(width != arr.length) {
//            int[] leftArray = queue.poll();
//            int[] rightArray = queue.poll();
//
//            int[] result = merge(leftArray, rightArray);
//            showArray(result);
//            queue.add(result);
//            width = result.length;
//        }
//    }
//
//    public int[] merge(int[] leftArray, int[] rightArray) {
//        int leftSize = leftArray.length;
//        int rightSize = rightArray.length;
//
//        int[] result = new int[leftSize + rightSize];
//
//        int leftIndex = 0;
//        int rightIndex = 0;
//        int currentArrayIndex = 0;
//
//        while(elementsToMerge(leftIndex, leftSize, rightIndex, rightSize)) {
//            if(compareElements(leftArray, rightArray, leftIndex, rightIndex)) {
//                result[currentArrayIndex] = leftArray[leftIndex];
//                leftIndex++;
//            } else {
//                result[currentArrayIndex] = rightArray[rightIndex];
//                rightIndex++;
//            }
//            currentArrayIndex++;
//        }
//
//        fillWhatsLeft(leftIndex, leftArray, result, currentArrayIndex);
//        fillWhatsLeft(rightIndex, rightArray, result, currentArrayIndex);
//
//        return result;
//    }
//
//    public boolean elementsToMerge(int leftIndex, int leftSize, int rightIndex, int rightSize) {
//        return leftIndex < leftSize && rightIndex < rightSize;
//    }
//
//    public boolean compareElements(int[] leftArray, int[] rightArray, int leftIndex, int rightIndex) {
//        return leftArray[leftIndex] <= rightArray[rightIndex];
//    }
//
//    public void fillWhatsLeft(int index, int[] fillArray, int[] array, int currentIndex) {
//        while(index < fillArray.length) {
//            array[currentIndex] = fillArray[index];
//            index++;
//            currentIndex++;
//        }
//    }
}