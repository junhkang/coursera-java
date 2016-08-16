import java.util.Random;


public class Project2 {

	private static int[] arr;
	private static int[] arrCopy;

	private static Random randomGenerator;

	private static void printArray(int[] arr) {
		System.out.print(" [" + arr[0]);
		for (int i = 1; i < 25; i++) {
			System.out.print(", " + arr[i]);
		}
		System.out.println("]");
	}

	private static void printData(int totalTrials, int range, int size, long duration, long comparisons) {
		System.out.println("Trial " + totalTrials + " Completed: Range(" + range + ") " + "Size(" + size
				+ ") = Duration(" + duration + ") Comparisons(" + comparisons + ")");
	}

	private static int bottomUpMergeSort(int[] a, int iLeft, int iMiddle, int iRight, int[] tmp) {
		int comparisons = 0;
		int i, j, k;
		i = iLeft;
		j = iMiddle;
		k = iLeft;

		while (i < iMiddle || j < iRight) {
			if (i < iMiddle && j < iRight) { 
				comparisons++;
				if (a[i] < a[j])
					tmp[k++] = a[i++];
				else
					tmp[k++] = a[j++];
			} 
                        else if (i == iMiddle)
				tmp[k++] = a[j++]; 
			else if (j == iRight)
				tmp[k++] = a[i++]; 
		}

		return comparisons;
	}

	private static int mergesort(int low, int high) {
		int comparisons = 0; // set comparisons as "0"
		// Check if low is smaller then high, if not then the array is sorted
		if (low < high) {
			// Get the index of the element which is in the middle
			int middle = low + (high - low) / 2;
			// Sort the left side of the array
			comparisons += mergesort(low, middle);
			// Sort the right side of the array
			comparisons += mergesort(middle + 1, high);
			// Combine them both
			comparisons += merge(low, middle, high);
		}

		return comparisons;
	}

	private static int merge(int low, int middle, int high) {
		int comparisons = 0; // set comparisons as "0"

		// Copy first part into the arrCopy array
		for (int i = low; i <= middle; i++) {
			arrCopy[i] = arr[i];
		}

		int i = low;
		int j = middle + 1;
		int k = low;

		while (i <= middle && j <= high) {
			comparisons++;
			if (arrCopy[i] <= arr[j]) {
				arr[k] = arrCopy[i];
				i++;
			} else {
				arr[k] = arr[j];
				j++;
			}
			k++;
		}

		// Copy the rest of the left part of the array into the target array
		while (i <= middle) {
			arr[k] = arrCopy[i];
			k++;
			i++;
		}

		return comparisons;
	}

	public static void main(String[] args) {
		randomGenerator = new Random();

		long start;
		long finish;
		int size;
		int range;
		int numTrials = 5;
		long totalDuration = 0;
		int totalTrials = 0;
		long totalComparisons = 0;

		// Do merge sort for different ranges of integer values as instructions
		for (int currIntRange = 0; currIntRange < 2; currIntRange++) {
			if (currIntRange == 0) {
				range = 1000; // case 1
			} else {
				range = 1000000; // case 2
			}

			// Do merge sort for different sizes of integer values as instructions
			for (int currIntSize = 0; currIntSize < 3; currIntSize++) {
				if (currIntSize == 0) {
					size = 1000000; // case 1
				} else if (currIntSize == 1) {
					size = 2000000; // case 2
				} else {
					size = 4000000; // case 3
				}

				// Do merge sort with current size and current range of integer with 100 trials 
				for (int currTrial = 0; currTrial < numTrials; currTrial++) {
					arr = new int[size];
					arrCopy = new int[size];

					// Fill the array with 'size' amount of random numbers
					for (int i = 0; i < size; i++) {
						arr[i] = arrCopy[i] = randomGenerator.nextInt(range) + 1;
					}

					// get starting time
					start = System.currentTimeMillis();
					// perform sort
					int comparisons = mergesort(0, size - 1);
					// get ending time
					finish = System.currentTimeMillis();

					long duration = finish - start;
					totalDuration += duration;
					totalTrials++;
					totalComparisons += comparisons;
                                        // if we want to see detail processes in each trials, activate printData or erase it.
					printData(totalTrials, range, size, duration, comparisons);
				}
			}
		}

		long avgDuration = totalDuration / totalTrials;
		long avgComparisons = totalComparisons / totalTrials;
		System.out.println(
				"mergeSort() Average duration(" + avgDuration + ") Average comparisons(" + avgComparisons + ")");

		totalTrials = 0;
		totalDuration = 0;
		totalComparisons = 0;   // reset for other sort

                // same with previous process
		for (int currIntRange = 0; currIntRange < 2; currIntRange++) {
			if (currIntRange == 0) {
				range = 1000;
			} else {
				range = 1000000;
			}

			for (int currIntSize = 0; currIntSize < 3; currIntSize++) {
				if (currIntSize == 0) {
					size = 1000000;
				} else if (currIntSize == 1) {
					size = 2000000;
				} else {
					size = 4000000;
				}

				// Do merge sort with current size and current range of integer valuse 100 times
				for (int currTrial = 0; currTrial < numTrials; currTrial++) {
					arr = new int[size];
					arrCopy = new int[size];

					// Fill the array with 'size' amount of random numbers
					for (int i = 0; i < size; i++) {
						arr[i] = arrCopy[i] = randomGenerator.nextInt(range) + 1;
					}

					// get starting time
					start = System.currentTimeMillis();
					// perform sort
					int middle = (size - 1) / 2;
					int comparisons = bottomUpMergeSort(arr, 0, middle, size - 1, arrCopy);
					// get ending time
					finish = System.currentTimeMillis();

					long duration = finish - start;
					totalDuration += duration;
					totalTrials++;
					totalComparisons += comparisons;
                                        // if we want to see detail processes in each trials, activate printData or erase it.
					printData(totalTrials, range, size, duration, comparisons);
				}
			}
		}

		avgDuration = totalDuration / totalTrials;
		avgComparisons = totalComparisons / totalTrials;
		System.out.println("bottomUpMergeSort() Average duration(" + avgDuration + ") Average comparisons("
				+ avgComparisons + ")");
	}
}
// conclusion: bottomup merge sort has much less running time and much less comparisons.
// also, in bottom up merge sort, as size increases double, comparisons also double.
//       as range <- 1000*range, duration = duration + x (couldnt find relation ship on x, but it increases as range increases
// also, in merge sort, as size increases double, duration increases to double
//       as range <- 1000*range, duration = duration + x (couldnt find relation ship on x, but it increases as range increases