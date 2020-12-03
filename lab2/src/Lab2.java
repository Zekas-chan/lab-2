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
	int fixedSeed = 12;

	// levers part1-2
	int arrayK = 1; // min 1

	// useful globals
	Random r;

	// analysis variables
	int incrementalAlgCount;
	int divideAndConquerAlgCount;
	int divideAndConquerNlogN;
	int divideAndConquerLinearCount;

	// prototyping
	int divideAndConquerLinearIterativeCount;

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

		int knownLeft = 1;
		int knownRight = 50;

		boolean knownLeftPlaced = true;
		boolean knownRightPlaced = true;

		int[] data = new int[(int) Math.pow(2, arrayK)];

//		for(int i = 0; i < data.length; i++) {
//			data[i] = n;
//			n++;
//		}

//		while (true) {
//			int choice = r.nextInt(data.length);
//			if (data[choice] == 0 && knownLeftPlaced) {
//				data[choice] = knownLeft;
//				knownLeftPlaced = false;
//			} else if (data[choice] == 0 && knownRightPlaced) {
//				data[choice] = knownRight;
//				knownRightPlaced = false;
//			}else if (!knownRightPlaced && !knownLeftPlaced) {
//				break;
//			}
//		}

		for (int i = 0; i < data.length; i++) {
//			if (data[i] != 0) {
//				continue;
//			}
			data[i] = 1 + r.nextInt(50);
		}

		return data;
	}

//	=== Part 1-1 algorithms ===

	int[] incrementalAlgv2(int[] input) {
		int x = max_value(input);
		int y = x;
		int z = x;

		for (int i : input) {
//			incrementalAlgCount++;
			if (boolCounter(i, z)) {
				if (boolCounter(i, x)) {
					z = y;
					y = x;
					x = i;
				} else if (boolCounter(i, y)) {
					z = y;
					y = i;
				} else {
					z = i;
				}
			}
		}
		return new int[] { x, y, z };
	}

	/**
	 * Hjälpmetod Räknar upp antalet jämförelser för inkrementella algoritmen
	 * 
	 * @param a Tal som ska jämföras
	 * @param b Tal som ska jämföras
	 * @return true om a < b, false annars
	 */
	boolean boolCounter(int a, int b) {
		incrementalAlgCount++;
		if (a < b) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 
	 */
	public int[] divideAndConquer(int[] arr) {
		divideAndConquerAlgCount++;
		if (arr.length == 3) {
			return sort(arr);
		} else {
			//create array objects (not an event)
			int[] arr1 = new int[arr.length / 2];
			int[] arr2 = new int[arr.length / 2];
			
			//split into two arrays (event)
			divideAndConquerAlgCount++;
			System.arraycopy(arr, 0, arr1, 0, arr.length / 2);
			System.arraycopy(arr, arr.length / 2, arr2, 0, arr.length / 2);
			
			//input array was >2, recurse (event)
			divideAndConquerAlgCount++;
			divideAndConquerAlgCount++;
			int[] arr_1 = divideAndConquer(arr1);
			int[] arr_2 = divideAndConquer(arr2);
			
			//put lowest of left and right into finalarr (event)
			int[] finalArr = new int[] { arr_1[0], arr_1[1], arr_1[2], arr_2[0], arr_2[1], arr_2[2] };
			divideAndConquerAlgCount++;
			
			//sort final array (not an event)
			finalArr = sort(finalArr);
//			divideAndConquerAlgCount++;
			
			//return (event)
			divideAndConquerAlgCount++;
			return new int[] { finalArr[0], finalArr[1], finalArr[2] };
		}
	}

	public int divideAndConquerNlogN(int[] arr) {
		
		//base case return (event)
		divideAndConquerNlogN++; // belongs to the if
		if (arr.length == 2) {
			return arr[1] / arr[0];
		}
		
		//implementation data init (not an event)
		int half = arr.length / 2;
		int[] left = new int[half];
		int[] right = new int[half];
		
		//split arrays (event)
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

	public int divideAndConquerLinear(int[] arr) {
        divideAndConquerLinearCount++; //belongs to the if
        if (arr.length == 2) {
            return arr[1]/arr[0];
        }
        int half = arr.length/2;
        int[] left = new int[half];
        int[] right = new int[half];
        System.arraycopy(arr, 0, left, 0, half);
        System.arraycopy(arr, half, right, 0, half);
        divideAndConquerLinearCount++;
        int[] sortedLeft = sort(left);
        int[]sortedRight = sort(right);
        int leftMin = sortedLeft[0];
        int rightMin = sortedRight[0];
        int leftMax = sortedLeft[left.length-1];
        int rightMax = sortedRight[right.length-1];
        divideAndConquerLinearCount++; //belongs to the if
        if(leftMin < rightMin && leftMax < rightMax) {
            int max = leftMin > rightMax ? leftMin : rightMax;
            return rightMax/leftMin;
        }
        int leftDivide = divideAndConquerLinear(left); 
        int rightDivide = divideAndConquerLinear(right);
        divideAndConquerLinearCount++;
        int maxDivide = sort(new int[] {leftDivide, rightDivide})[1];
        divideAndConquerLinearCount++;
        return maxDivide;
    }

	public int divideAndConquerLinearIterative(int[] arr) {
		int min = Integer.MAX_VALUE;
		int max = 0;
		int indexOfMax = 0;

		for (int i = 1; i < arr.length; i++) {
			divideAndConquerLinearIterativeCount++;
			divideAndConquerLinearIterativeCount++;
			if (max < arr[i]) {
				max = arr[i];
				indexOfMax = i;
			}
		}

		for (int i = 0; i < indexOfMax; i++) {
			divideAndConquerLinearIterativeCount++;
			divideAndConquerLinearIterativeCount++;
			if (min > arr[i]) {
				min = arr[i];
			}
		}

		return max / min;
	}

	private int[] sort(int[] arr) {
		int max = max_value(arr);
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

	/**
	 * Hittar det största heltalet i en array och returnerar det
	 * 
	 * @param nums Array med heltal
	 * @return Det största heltalet i arrayen
	 */
	int max_value(int[] nums) {
		int max_value = 0;
		for (int i = 0; i < nums.length; i++)
			if (nums[i] > max_value)
				max_value = nums[i];
//        System.out.println("Max value found: "+max_value);
		return max_value;
	}

	public static void main(String[] args) {
		Lab2 asdf = new Lab2();

		for (int i = 0; i < 15; i++) {
			asdf.testDivideAndConquerAlgNlogN();
			System.out.print("\n");
			asdf.arrayK++;
		}

//		asdf.testIncrementalAlgv2();
//		System.out.print("\n");
//		
//		asdf.testDivideAndConquerAlg();
//		System.out.print("\n");
//		
//		asdf.testDivideAndConquerAlgNlogN();
//		System.out.print("\n");
//
//		asdf.testDivideAndConquerAlgLinear();
//		System.out.print("\n");

	}

	void testIncrementalAlgv2() {
		System.out.println("\nTesting Incremental Algorithm v2: ");
		int[] test = generateDataSet();
		int[] result = incrementalAlgv2(test);

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

		int[] result = divideAndConquer(test);

		for (int i : result) {
			System.out.print(i + ", ");
		}
		System.out.println("\nNumber of elements: " + test.length);
		System.out.println("Executed in " + divideAndConquerAlgCount);
		divideAndConquerAlgCount = 0;
	}

	void testDivideAndConquerAlgNlogN() {
		int[] test = generateDataSetPart1_2();
		System.out.println("Testing Divide and Conquer (N log n version)");
		System.out.println("Number of elements: " + test.length);
		System.out.println("Input: ");
		
		if (test.length > 32) {
			System.out.print("Input is too large to display.");
		} else {
			for (int i : test) {
				System.out.print(i + ", ");
			}
		}
//		System.out.println("Input used to be here.");

		int result = divideAndConquerNlogN(test);

		System.out.println("\nFunction returned: " + result);
		System.out.println("Executed in " + divideAndConquerNlogN);
		divideAndConquerNlogN = 0;
	}

	void testDivideAndConquerAlgLinear() {
		int[] test = generateDataSetPart1_2();
		System.out.println("Testing Divide and Conquer (Linear version)");
		System.out.println("Number of elements: " + test.length);
		System.out.print("Input: \n[");
		
		if (test.length > 32) {
			System.out.print("Input is too large to display.");
		} else {
			for (int i : test) {
				System.out.print(i + ", ");
			}
		}
		System.out.println("]");

		int result = divideAndConquerLinear(test);

		System.out.println("\nFunction returned: " + result);
		
		System.out.println("Executed in " + divideAndConquerLinearCount);
		divideAndConquerLinearCount = 0;
	}

	void testDivideAndConquerAlgLinearIterative() {
		int[] test = generateDataSetPart1_2();
		System.out.println("Testing Divide and Conquer (Linear iterative version)");

		System.out.println("Input: ");
		
		for (int i : test) {
			System.out.print(i + ", ");
		}

		int result = divideAndConquerLinearIterative(test);

		System.out.print("\nFunction returned: " + result);
		System.out.println("\nNumber of elements: " + test.length);
		System.out.println("Executed in " + divideAndConquerLinearIterativeCount);
		divideAndConquerLinearIterativeCount = 0;
	}

}
