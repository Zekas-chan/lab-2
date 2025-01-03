import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * 
 * @author Gabriella Hafdell, Hanna Eriksson, Philip Larsson
 *
 */
public class Lab2 {
	// levers
	int arraySizeK = 1; // min 1
	boolean useRandomSeed = true;
	int fixedSeed = 8976321;

	// levers part1-2
	int arrayK = 4; // min 1

	// useful globals
	Random r;

	// analysis variables
	int incrementalAlgCount;
	int divideAndConquerAlgCount;
	int divideAndConquerNlogN;
	int divideAndConquerLinearCount;

	public Lab2() {
	}

	/*
	 * 
	 */
	int[] generateDataSet() {
		int x = 13;
		int y = 42;
		int z = 69;

		int nextInsertion;

		boolean xNotPlaced = true;
		boolean yNotPlaced = true;
		boolean zNotPlaced = true;

		LinkedList<Integer> selectedIntegers = new LinkedList<Integer>();

		int[] intArr = new int[(int) (3 * Math.pow(2, arraySizeK - 1))];

		if (useRandomSeed) {
			this.r = new Random(System.currentTimeMillis());
		} else {
			this.r = new Random(fixedSeed);
		}

		while (true) {
			int choice = r.nextInt(intArr.length);
			if (intArr[choice] == 0 && xNotPlaced) {
				intArr[choice] = x;
				xNotPlaced = false;
			} else if (intArr[choice] == 0 && yNotPlaced) {
				intArr[choice] = y;
				yNotPlaced = false;
			} else if (intArr[choice] == 0 && zNotPlaced) {
				intArr[choice] = z;
				zNotPlaced = false;
			} else if (!xNotPlaced && !yNotPlaced && !zNotPlaced) {
				break;
			}
		}

		for (int i = 0; i < intArr.length; i++) {
			if (intArr[i] != 0) {
				continue;
			} else {
				do {
					nextInsertion = 70 + r.nextInt(400000);
				} while (selectedIntegers.contains(nextInsertion));
				intArr[i] = nextInsertion;
				selectedIntegers.add(nextInsertion);
			}

		}

//		for(int i = 0; i < intArr.length; i++) {
//			System.out.println("Element "+i+": "+intArr[i]);
//		} //debug

		return intArr;
	}

	int[] generateDataSetPart1_2() {
		if (useRandomSeed) {
			this.r = new Random(System.currentTimeMillis());
		} else {
			this.r = new Random(fixedSeed);
		}

		int[] data = new int[(int) Math.pow(2, arrayK)];

		for (int i = 0; i < data.length; i++) {
			data[i] = 1 + r.nextInt(500);
		}

		return data;
	}

//  === Part 1-1 algorithms ===

	int[] incrementalAlgv3(int[] input) {
		int x;
		int y;
		int z;
		incrementalAlgCount++;
		if (ifcounter(2) && input.length == 3) {
			incrementalAlgCount += 5;
			if (ifcounter(2) && ifcounter(2) && input[0] < input[1] && input[0] < input[2]) {
				x = input[0];
				if (ifcounter(2) && input[1] < input[2]) {
					y = input[1];
					z = input[2];
				} else {
					y = input[2];
					z = input[1];
				}
			}

			else if (ifcounter(2) && ifcounter(2) && input[1] < input[0] && input[1] < input[2]) {
				x = input[1];
				if (ifcounter(2) && input[0] < input[2]) {
					y = input[0];
					z = input[2];
				} else {
					y = input[2];
					z = input[0];
				}

			} else {
				x = input[2];
				if (ifcounter(2) && input[0] < input[1]) {
					y = input[0];
					z = input[1];
				} else {
					y = input[1];
					z = input[0];
				}

			}
			return new int[] { x, y, z };
		} // end base case

		int[] inputCopy = new int[input.length - 1];
		System.arraycopy(input, 1, inputCopy, 0, input.length - 1);
		int[] arr = incrementalAlgv3(inputCopy);

		incrementalAlgCount++;
		x = arr[0];
		y = arr[1];
		z = arr[2];

		incrementalAlgCount += 4;
		if (boolCounter(input[0], z)) {
			if (boolCounter(input[0], x)) {
				z = y;
				y = x;
				x = input[0];
			} else if (boolCounter(input[0], y)) {
				z = y;
				y = input[0];
			} else {
				z = input[0];
			}
		}

		incrementalAlgCount++;
		return new int[] { x, y, z };
	}

	/*
	 * 
	 */
	public int[] divideAndConquer(int[] arr) {
		divideAndConquerAlgCount++;
		if (arr.length == 3) {
			divideAndConquerAlgCount += 5;
			return scuffedSort(arr);
		} else {
			// create array objects (not an event)
			int[] arr1 = new int[arr.length / 2];
			int[] arr2 = new int[arr.length / 2];

			// split into two arrays (event)
			divideAndConquerAlgCount++;
			System.arraycopy(arr, 0, arr1, 0, arr.length / 2);
			System.arraycopy(arr, arr.length / 2, arr2, 0, arr.length / 2);

			// input array was >3, recurse (event)
//			divideAndConquerAlgCount++;
//			divideAndConquerAlgCount++;
			int[] arr_1 = divideAndConquer(arr1);
			int[] arr_2 = divideAndConquer(arr2);

			// put lowest of left and right into finalarr (event)
//			int[] finalArr = new int[] { arr_1[0], arr_1[1], arr_1[2], arr_2[0], arr_2[1], arr_2[2] };

			// sort final array
			divideAndConquerAlgCount += 6;
			int[] finalArr = findSmallestTriple(new int[] { arr_1[0], arr_1[1], arr_1[2] },
					new int[] { arr_2[0], arr_2[1], arr_2[2] });

//			divideAndConquerAlgCount++;

			// return (event)
			divideAndConquerAlgCount++;
			return new int[] { finalArr[0], finalArr[1], finalArr[2] };
		}
	}

	int[] scuffedSort(int[] arr) {
		int x;
		int y;
		int z;
		if (ifcounter(2) && ifcounter(2) && arr[0] < arr[1] && arr[0] < arr[2]) {
			x = arr[0];
			if (ifcounter(2) && arr[1] < arr[2]) {
				y = arr[1];
				z = arr[2];
			} else {
				y = arr[2];
				z = arr[1];
			}
		}

		else if (ifcounter(2) && ifcounter(2) && arr[1] < arr[0] && arr[1] < arr[2]) {
			x = arr[1];
			if (ifcounter(2) && arr[0] < arr[2]) {
				y = arr[0];
				z = arr[2];
			} else {
				y = arr[2];
				z = arr[0];
			}

		} else {
			x = arr[2];
			if (ifcounter(2) && arr[0] < arr[1]) {
				y = arr[0];
				z = arr[1];
			} else {
				y = arr[1];
				z = arr[0];
			}

		}
		return new int[] { x, y, z };
	}

	int[] findSmallestTriple(int[] arrA, int[] arrB) {
		int[] result = new int[3];
		int minA;
		int minB;
		int A = 0;
		int B = 0;

		for (int i = 0; i < 3; i++) {
			minA = arrA[A];
			minB = arrB[B];
			if (minA < minB) {
				result[i] = minA;
				A++;
			} else {
				result[i] = minB;
				B++;
			}
		}

		return result;
	}

	public int divideAndConquerNlogN(int[] arr) {

		// base case return (event)
		divideAndConquerNlogN++; // belongs to the if
		if (arr.length == 2) {
			return arr[1] / arr[0];
		}

		// implementation data init (not an event)
		int half = arr.length / 2;
		int[] left = new int[half];
		int[] right = new int[half];

		// split arrays (event)
		System.arraycopy(arr, 0, left, 0, half);
		System.arraycopy(arr, half, right, 0, half);
		divideAndConquerNlogN++;

		//
		int leftDivide = divideAndConquerNlogN(left);
		int rightDivide = divideAndConquerNlogN(right);
		divideAndConquerNlogN++;
		divideAndConquerNlogN++;

		int leftMin = sort(left)[0];
		int rightMax = sort(right)[right.length - 1];
		divideAndConquerNlogN += arr.length;
		int maxDivide = sort(new int[] { leftDivide, rightDivide, rightMax / leftMin })[2];
		divideAndConquerNlogN++;
		return maxDivide;
	}

	public int[] divideAndConquerLinear(int[] arr) {
		int max;
		int min;
		int leftMin;
		int leftMax;
		int rightMin;
		int rightMax;
		int maxDivide;
		divideAndConquerLinearCount++; // belongs to the if
		if (arr.length == 2) {
			return new int[] { arr[1] / arr[0], arr[0], arr[1] };
		}
		divideAndConquerLinearCount++;
		if (arr.length % 4 == 0 && arr.length > 4) {
			int fourth = arr.length / 4;
			int[] leftleft = new int[fourth];
			int[] leftright = new int[fourth];
			int[] rightleft = new int[fourth];
			int[] rightright = new int[fourth];
			System.arraycopy(arr, 0, leftleft, 0, fourth);
			System.arraycopy(arr, fourth, leftright, 0, fourth);
			System.arraycopy(arr, 2 * fourth, rightleft, 0, fourth);
			System.arraycopy(arr, 3 * fourth, rightright, 0, fourth);
			divideAndConquerLinearCount++;
			int[] leftleftDivide = divideAndConquerLinear(leftleft);
			divideAndConquerLinearCount++;
			int[] leftrightDivide = divideAndConquerLinear(leftright);
			divideAndConquerLinearCount++;
			int[] rightleftDivide = divideAndConquerLinear(rightleft);
			divideAndConquerLinearCount++;
			int[] rightrightDivide = divideAndConquerLinear(rightright);
			divideAndConquerLinearCount++;
			int leftleftMin = leftleftDivide[1] < leftleftDivide[2] ? leftleftDivide[1] : leftleftDivide[2];
			int leftleftMax = leftleftDivide[1] > leftleftDivide[2] ? leftleftDivide[1] : leftleftDivide[2];
			int rightleftMin = rightleftDivide[1] < rightleftDivide[2] ? rightleftDivide[1] : rightleftDivide[2];
			int rightleftMax = rightleftDivide[1] > rightleftDivide[2] ? rightleftDivide[1] : rightleftDivide[2];
			int leftrightMin = leftrightDivide[1] < leftrightDivide[2] ? leftrightDivide[1] : leftrightDivide[2];
			int leftrightMax = leftrightDivide[1] > leftrightDivide[2] ? leftrightDivide[1] : leftrightDivide[2];
			int rightrightMin = rightrightDivide[1] < rightrightDivide[2] ? rightrightDivide[1] : rightrightDivide[2];
			int rightrightMax = rightrightDivide[1] > rightrightDivide[2] ? rightrightDivide[1] : rightrightDivide[2];
			leftMin = leftleftMin < leftrightMin ? leftleftMin : leftrightMin;
			leftMax = leftleftMax > leftrightMax ? leftleftMax : leftrightMax;
			rightMin = rightleftMin < rightrightMin ? rightleftMin : rightrightMin;
			rightMax = rightleftMax > rightrightMax ? rightleftMax : rightrightMax;
			divideAndConquerLinearCount++;
			min = leftMin < rightMin ? leftMin : rightMin;
			max = leftMax > rightMax ? leftMax : rightMax;
			divideAndConquerLinearCount++;
			maxDivide = sort(new int[] { leftleftDivide[0], leftrightDivide[0], rightleftDivide[0], rightrightDivide[0],
					rightrightMax / rightleftMin, leftrightMax / leftleftMin, rightMax / leftMin })[6];
		} else {
			int half = arr.length / 2;
			int[] left = new int[half];
			int[] right = new int[half];
			System.arraycopy(arr, 0, left, 0, half);
			System.arraycopy(arr, half, right, 0, half);
			divideAndConquerLinearCount++;
			int[] leftDivide = divideAndConquerLinear(left);
			int[] rightDivide = divideAndConquerLinear(right);
			divideAndConquerLinearCount++;
			divideAndConquerLinearCount++;
			leftMin = leftDivide[1] < leftDivide[2] ? leftDivide[1] : leftDivide[2];
			leftMax = leftDivide[1] > leftDivide[2] ? leftDivide[1] : leftDivide[2];
			rightMin = rightDivide[1] < rightDivide[2] ? rightDivide[1] : rightDivide[2];
			rightMax = rightDivide[1] > rightDivide[2] ? rightDivide[1] : rightDivide[2];
			divideAndConquerLinearCount++;
			min = leftMin < rightMin ? leftMin : rightMin;
			max = leftMax > rightMax ? leftMax : rightMax;
			divideAndConquerLinearCount++;
			maxDivide = sort(new int[] { leftDivide[0], rightDivide[0], rightMax / leftMin })[2];
		}
		divideAndConquerLinearCount++;
		return new int[] { maxDivide, max, min };
	}

	/*
	 * Bellas möjligtvis användbara metod
	 */
	int[] divide(int[] arr) {
		// Check for if only one element
		if (arr.length == 1) {
			return new int[] { arr[0], arr[0], 1 };
		}

		// Separate the array into two
		int half = arr.length / 2;
		int[] left = new int[half];
		int[] right = new int[half];
		System.arraycopy(arr, 0, left, 0, half);
		System.arraycopy(arr, half, right, 0, half);

		// The recursive part
		int[] newLeft = divide(left);
		int[] newRight = divide(right);

		// Create an array that is to be returned
		int[] array = new int[3];

		// Check for min
		if (newLeft[0] < newRight[0]) {
			array[0] = newLeft[0];
		} else {
			array[0] = newRight[0];
		}

		// Check for max
		if (newLeft[1] > newRight[1]) {
			array[1] = newLeft[1];
		} else {
			array[1] = newRight[1];
		}

		// input max/min
		array[2] = array[1] / array[0];

		// Test to see, delete later ~v
		System.out.print("\nMin:\t" + array[0]);
		System.out.print("\nMax:\t" + array[1]);
		System.out.println("\nKvot:\t" + array[2]);
		return array;
	}

	int[] divide2(int[] arr) {
		// Check for if only one element
		if (arr.length == 1) {
			// Return end of orgin Array
			return new int[] { arr[0], arr[0], 1 };
		}

		// Create a array copy without first value of array
		int[] arr1 = new int[arr.length - 1];
		System.arraycopy(arr, 1, arr1, 0, arr.length - 1);

		// The recursive part
		int[] arrReturn = divide(arr1);

		// For loop to check each division
		// if better max is found then it will be returned
		// else, old value will be returned
		for (int i : arr1) {
			if (i / arr[0] > arrReturn[2]) {
				arrReturn[0] = arr[0];
				arrReturn[1] = i;
				arrReturn[2] = i / arr[0];
			}
		}
		return arrReturn;
	}

	private int[] sort(int[] arr) {
		int max = max_value(arr)[0];
		int n = arr.length;

		int output[] = new int[n];

		int count[] = new int[max + 1];

		for (int i = 0; i < n; ++i)
			++count[arr[i]];

		for (int i = 1; i <= max; ++i)
			count[i] += count[i - 1];

		for (int i = n - 1; i >= 0; i--) {
			output[count[arr[i]] - 1] = arr[i];
			--count[arr[i]];
		}
		return output;
	}

	boolean ifcounter(int mode) {
		switch (mode) {
		case 1:
			divideAndConquerNlogN++;
			break;

		case 2:
//			incrementalAlgCount++;
			break;
		}
		return true;
	}

	/**
	 * Hjälpmetod Räknar upp antalet jämförelser för inkrementella algoritmen
	 * 
	 * @param a Tal som ska jämföras
	 * @param b Tal som ska jämföras
	 * @return true om a < b, false annars
	 */
	boolean boolCounter(int a, int b) {
//		incrementalAlgCount++;
		if (a < b) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Hittar det största heltalet i en array och returnerar det
	 * 
	 * @param nums Array med heltal
	 * @return Det största heltalet i arrayen
	 */
	int[] max_value(int[] nums) {
		int max_value = 0;
		int index = 0;
		int execTime = 0;
		for (int i = 0; i < nums.length; i++) {
			execTime++;
			if (nums[i] > max_value) {
				max_value = nums[i];
				index = i;
			}
		}
		return new int[] { max_value, index, execTime };
	}

	int[] min_value(int[] nums) {
		int min_value = Integer.MAX_VALUE;
		int index = 0;
		int execTime = 0;
		for (int i = 0; i < nums.length; i++) {
			execTime++;
			if (nums[i] < min_value) {
				min_value = nums[i];
				index = i;
			}
		}
		return new int[] { min_value, index, execTime };
	}

	public static void main(String[] args) {
		Lab2 asdf = new Lab2();

		for (int i = 0; i < 10; i++) {
			asdf.testDivideAndConquerAlg();
			System.out.print("\n");
			asdf.arraySizeK++;
		}

//		System.out.println("======PART 1-1=====");
//		asdf.testIncrementalAlg();
//		System.out.print("\n");
//		
//		asdf.testDivideAndConquerAlg();
//		System.out.print("\n");
//		
//		System.out.println("\n======PART 1-2=====\n");
//		
//		asdf.testDivideAndConquerAlgNlogN();
//		System.out.print("\n");
//
//		asdf.testDivideAndConquerAlgLinear();
//		System.out.print("\n");

	}

	void testIncrementalAlg() {
		System.out.println("\nTesting Incremental Algorithm: ");
		int[] test = generateDataSet();
		// print input
		System.out.print("Input: \n[");
		if (test.length > 32) {
			System.out.print("Input is too large to display.");
		} else {
			for (int i = 0; i < test.length; i++) {
				System.out.print(test[i]);
				if (i < test.length - 1) {
					System.out.print(", ");
				}
			}
		}
		System.out.println("]");

		int[] result = incrementalAlgv3(test);

		System.out.println("Output:");
		for (int i : result) {
			System.out.print(i + ", ");
		}
		System.out.println("\nNumber of elements: " + test.length);
		System.out.println("Executed in " + incrementalAlgCount);
		incrementalAlgCount = 0;

	}

	void testDivideAndConquerAlg() {
		System.out.println("\nTesting Divide and Conquer Algorithm: ");
		int[] test = generateDataSet();
		// print input
		System.out.print("Input: \n[");
		if (test.length > 32) {
			System.out.print("Input is too large to display.");
		} else {
			for (int i = 0; i < test.length; i++) {
				System.out.print(test[i]);
				if (i < test.length - 1) {
					System.out.print(", ");
				}
			}
		}
		System.out.println("]");

		int[] result = divideAndConquer(test);
		System.out.println("Output:");
		for (int i : result) {
			System.out.print(i + ", ");
		}
		System.out.println("\nNumber of elements: " + test.length);
		System.out.println("Executed in " + divideAndConquerAlgCount);
		divideAndConquerAlgCount = 0;
	}

	void testDivideAndConquerAlgNlogN() {
		int[] test = generateDataSetPart1_2();
		System.out.println("Testing Divide and Conquer (n log n version)");
		System.out.println("Number of elements: " + test.length);

		// print input
		System.out.print("Input: \n[");
		if (test.length > 32) {
			System.out.print("Input is too large to display.");
		} else {
			for (int i = 0; i < test.length; i++) {
				System.out.print(test[i]);
				if (i < test.length - 1) {
					System.out.print(", ");
				}
			}
		}
		System.out.println("]");

		int result = divideAndConquerNlogN(test);

		System.out.println("Function returned: " + result);
		System.out.println("Executed in " + divideAndConquerNlogN);
		divideAndConquerNlogN = 0;
	}

	void testDivideAndConquerAlgLinear() {
		int[] test = generateDataSetPart1_2();
		System.out.println("Testing Divide and Conquer (Linear version)");
		System.out.println("Number of elements: " + test.length);

		// print input
		System.out.print("Input: \n[");
		if (test.length > 32) {
			System.out.print("Input is too large to display.");
		} else {
			for (int i = 0; i < test.length; i++) {
				System.out.print(test[i]);
				if (i < test.length - 1) {
					System.out.print(", ");
				}
			}
		}
		System.out.println("]");

		int result = divideAndConquerLinear(test)[0];

		int verify = divideAndConquerNlogN(test);

		System.out.println("Function returned: " + result);
		System.out.println("Executed in " + divideAndConquerLinearCount);
		divideAndConquerLinearCount = 0;
		if (result != verify) {
			System.out.println("\n!!!:\nMismatch: Result was " + result + " but N log N calculated it as: " + verify);
		}
	}

}
