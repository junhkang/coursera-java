import java.util.Random;

public class Project2problem2 {

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
			} else if (i == iMiddle)
				tmp[k++] = a[j++]; 
			else if (j == iRight)
				tmp[k++] = a[i++]; 
		}

		return comparisons;
	}

	public static int insertSort(int left, int right) {
		int comparisons = 0;
		// insertSort the subarray arr[left, right]
		int i, j;
		for (i = left + 1; i <= right; i++) {
			int temp = arr[i]; // store a[i] in temp
			j = i; // start shifts at i
			// until one is smaller,
			while (j > left && arr[j - 1] >= temp) {
				comparisons++;
                                
				arr[j] = arr[j - 1]; // shift item to right
				--j; // go left one position
			}
			arr[j] = temp; // insert stored item
		} // end for

		return comparisons;
	} // end insertSort()

	private static int mergesort(int low, int high, boolean smartSort, boolean insertSort) {
		int comparisons = 0;

		// Check if low is smaller then high, if not then the array is sorted
		if (low < high) {
			// Get the index of the element which is in the middle
			int middle = low + (high - low) / 2;

			// Sort the left side of the array
			if ((smartSort && !isSorted(low, middle)) || !smartSort) {
				// If subarray is 100 elements or greater call merge sort
				if ((insertSort && middle - low >= 100) || !insertSort) {
					comparisons += mergesort(low, middle, smartSort, insertSort);
				}
				// If subarray is less than 100 elements call insert sort
				else {
					comparisons += insertSort(low, middle);
				}
			}

			// Sort the right side of the array
			if ((smartSort && !isSorted(middle + 1, high)) || !smartSort) {
				// If subarray is 100 elements or greater call merge sort
				if ((insertSort && high - middle + 1 >= 100) || !insertSort) {
					comparisons += mergesort(middle + 1, high, smartSort, insertSort);
				}
				// If subarray is less than 100 elements call insert sort
				else {
					comparisons += insertSort(low, middle);
				}
			}

			// Combine them both
			comparisons += merge(low, middle, high);
		}

		return comparisons;
	}

	private static int merge(int low, int middle, int high) {
		int comparisons = 0;

		// Copy first part into the arrCopy array
		for (int i = low; i <= middle; i++) {
			arrCopy[i] = arr[i];
		}

		int i = low;
		int j = middle + 1;
		int k = low;

		// Copy the smallest values from either the left or the right side back
		// to the original array
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
        // gives 2 conditions to turn it off/on
	private static int quicksort(int low, int high, boolean smartSort, boolean insertSort) {
		int comparisons = 0;

		int i = low, j = high;

		// Get the pivot element from the middle of the list
		int pivot = arr[(high + low) / 2];

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (arr[i] < pivot) {
				comparisons++;
				i++;
			}

			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (arr[j] > pivot) {
				comparisons++;
				j--;
			}

			// If we have found a value in the left list which is larger than
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i < j) {
				exchange(i, j);
				i++;
				j--;
			} else if (i == j) {
				i++;
				j--;
			}
		}

		// Recursion
		if (low < j) {
			if ((smartSort && !isSorted(low, j)) || !smartSort) {
				// If subarray is 100 elements or greater call quick sort
				if ((insertSort && j - low >= 100) || !insertSort) {
					comparisons += quicksort(low, j, smartSort, insertSort);
				}
				// If subarray is less than 100 elements call insert sort
				else {
					comparisons += insertSort(low, j);
				}
			}
		}
		if (i < high) {
			if ((smartSort && !isSorted(i, high)) || !smartSort) {
				// If subarray is 100 elements or greater call quick sort
				if ((insertSort && high - i >= 100) || !insertSort) {
					comparisons += quicksort(i, high, smartSort, insertSort);
				}
				// If subarray is less than 100 elements call insert sort
				else {
					comparisons += insertSort(i, high);
				}
			}
		}

		return comparisons;
	}

	public static void exchange(int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}
        // check whether the array is sorted or not by using isSorted
	private static boolean isSorted(int low, int high) {
		for (int x = low; x < high - 1; x++) {
			if (arr[x] >= arr[x + 1]) {
				return false;
			}
		}

		return true;
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

			
			for (int currIntSize = 0; currIntSize < 3; currIntSize++) {
				if (currIntSize == 0) {
					size = 1000000;
				} else if (currIntSize == 1) {
					size = 2000000;
				} else {
					size = 4000000;
				}

				
				for (int currTrial = 0; currTrial < numTrials; currTrial++) {
					arr = new int[size];
					arrCopy = new int[size];

					// Fill the array with 'size' amount of random numbers
					for (int i = 0; i < size; i++) {
						arr[i] = arrCopy[i] = randomGenerator.nextInt(range) + 1;
					}

					// get starting time
					start = System.currentTimeMillis();
					// perform sort with changing the bolean (turn off/ on the condition)
					int comparisons = mergesort(0, size - 1, true, true);
					// get ending time
					finish = System.currentTimeMillis();

					long duration = finish - start;
					totalDuration += duration;
					totalTrials++;
					totalComparisons += comparisons;

				}
			}
		}

		long avgDuration = totalDuration / totalTrials;
		long avgComparisons = totalComparisons / totalTrials;
		System.out.println(
				"mergeSort() Average duration(" + avgDuration + ") Average comparisons(" + avgComparisons + ")");

		totalTrials = 0;
		totalDuration = 0;
		totalComparisons = 0;

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


				// Do merge sort with current size and current range of integer
				// values 100 times
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
                                        // changing boleans to turn on/off the condition
					int comparisons = quicksort(0, size - 1, false, true);
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
		System.out.println("quickSort() Average duration(" + avgDuration + ") Average comparisons("
				+ avgComparisons + ")");
	}
}

// we can easily check the results depending on using smart sort and insert sort. I tried to turn it off / on by changing codes and checked difference.
// (a) use both sorts
//mergeSort() Average duration(116) Average comparisons(52575595)
//quickSort() Average duration(166) Average comparisons(87535053)
// (b) using only smart sort
//mergeSort() Average duration(124) Average comparisons(52581761)
//quickSort() Average duration(161) Average comparisons(87828582)
// (c) using only insert sort
//mergeSort() Average duration(222) Average comparisons(44925068)
//quickSort() Average duration(154) Average comparisons(30354552)
// (d) use none
//mergeSort() Average duration(220) Average comparisons(46901313)
//quickSort() Average duration(157) Average comparisons(30490848)
// as we can see, if we use both methods, both comparisons and durations are ruduced dramatically

