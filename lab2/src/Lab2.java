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
	int arrayK = 1; // min 1

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
			data[i] = 1 + r.nextInt(500);
		}

		return data;
	}

//	=== Part 1-1 algorithms ===

	int[] incrementalAlgv2(int[] input) {
		int x = max_value(input)[0];
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
	
	public int[] divideAndConquerLinear(int[] arr) {
		int max;
		int min;
		int leftMin;
		int leftMax;
		int rightMin;
		int rightMax;
		int maxDivide;
		divideAndConquerLinearCount++; //belongs to the if
		if(arr.length == 2) {
			return new int[] {arr[1]/arr[0], arr[0], arr[1]};
		}
		divideAndConquerLinearCount++;
		if(arr.length % 4 == 0 && arr.length > 4) {
			int fourth = arr.length/4;
	        int[] leftleft = new int[fourth];
	        int[] leftright = new int[fourth];
	        int[] rightleft = new int[fourth];
	        int[] rightright = new int[fourth];
	        System.arraycopy(arr, 0, leftleft, 0, fourth);
	        System.arraycopy(arr, fourth, leftright, 0, fourth);
	        System.arraycopy(arr, 2*fourth, rightleft, 0, fourth);
	        System.arraycopy(arr, 3*fourth, rightright, 0, fourth);
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
//	        System.out.println("\nleftleftMin "+leftleftMin+", leftleftMax "+leftleftMax);
//	        System.out.println("\nleftrightMin "+leftrightMin+", leftrightMax "+leftrightMax);
//	        System.out.println("\nrightleftMin "+rightleftMin+", rightleftMax "+rightleftMax);
//	        System.out.println("\nrightrightMin "+rightrightMin+", rightrightMax "+rightrightMax);
//	        System.out.println("\nleftMin "+leftMin+", leftaMax "+leftMax);
//	        System.out.println("\nrightMin "+rightMin+", rightMax "+rightMax);
	        min = leftMin < rightMin ? leftMin : rightMin;
	        max = leftMax > rightMax ? leftMax : rightMax;
	        divideAndConquerLinearCount++;
//	        System.out.println("\nMin "+min+" Max "+max);
	        maxDivide = sort(new int[] {leftleftDivide[0], leftrightDivide[0],rightleftDivide[0], rightrightDivide[0], rightrightMax/rightleftMin, leftrightMax/leftleftMin, rightMax/leftMin})[6];
		} else {
			int half = arr.length/2;
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
	        maxDivide = sort(new int[] {leftDivide[0], rightDivide[0], rightMax/leftMin})[2];
		}
        divideAndConquerLinearCount++;
        return new int[] {maxDivide, max, min};		
	}
	
	
	
	/*
	 * Bellas möjligtvis användbara metod
	 */
	int[] divide(int[] arr) {
        // Check for if only one element
        if(arr.length == 1) {
            return new int[] {arr[0], arr[0], 1};
        }
        
        // Separate the array into two
        int half = arr.length/2;
        int[] left = new int[half];
        int[] right = new int[half];
        System.arraycopy(arr, 0, left, 0, half);
        System.arraycopy(arr, half, right, 0, half );
        
        // The recursive part
        int[] newLeft = divide(left);
        int[] newRight = divide(right);
        
        //Create an array that is to be returned
        int[] array= new int[3];
        
        // Check for min
        if(newLeft[0] < newRight[0]) {
            array[0] = newLeft[0]; 
        } else {
            array[0] = newRight[0]; 
        }
        
        // Check for max
        if(newLeft[1] > newRight[1]) {
            array[1] = newLeft[1]; 
        } else {
            array[1] = newRight[1]; 
        }
        
        // input max/min
        array[2] = array[1]/array[0];
        
        // Test to see, delete later ~v
        System.out.print("\nMin:\t"     + array[0]);
        System.out.print("\nMax:\t"     + array[1]);
        System.out.println("\nKvot:\t"     + array[2]);
        return array;
    }
	
	int[] divide2(int[] arr) {
        // Check for if only one element
        if(arr.length == 1) {
            // Return end of orgin Array
            return new int[] {arr[0], arr[0], 1};
        }

        // Create a array copy without first value of array
        int[] arr1 = new int[arr.length-1];
        System.arraycopy(arr, 1, arr1, 0, arr.length-1);

        // The recursive part
        int[] arrReturn = divide(arr1);

        // For loop to check each division
        // if better max is found then it will be returned
        // else, old value will be returned
        for(int i : arr1) {
            if(i/arr[0] > arrReturn[2]) {
                arrReturn[0] = arr[0];
                arrReturn[1] = i;
                arrReturn[2] = i/arr[0];
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
		return new int[] {max_value, index, execTime};
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
		return new int[] {min_value, index, execTime};
	}
	

	public static void main(String[] args) {
		Lab2 asdf = new Lab2();
		
		
		for (int i = 0; i < 15; i++) {
//			asdf.testDivideAndConquerAlgLinear();
//			System.out.print("\n");
			asdf.testDivideAndConquerAlg();
			System.out.print("\n");
			asdf.arraySizeK++;
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
		System.out.println("Testing Divide and Conquer (n log n version)");
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
			for (int i = 0; i < test.length; i++) {
				System.out.print(test[i]);
				if(i < test.length-1) {
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
		if(result != verify) {
			System.out.println("\n!!!:\nMismatch: Result was "+result+" but N log N calculated it as: "+verify);
		}
	}

}
