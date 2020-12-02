import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * 
 * @author Gabriella Hafdell, Hanna Eriksson, Philip Larsson
 *
 */
public class Lab2 {
	//levers
	int arraySizeK = 4;
	boolean useRandomSeed = false;
	int fixedSeed = 55;
	
	//useful globals
	Random r;
	
	//analysis variables
	int incrementalAlgCount;
	int divideAndConquerAlgCount;

	public Lab2() {
		// TODO Auto-generated constructor stub
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
		
		
		int[] intArr = new int[(int) (3 * Math.pow(2, arraySizeK-1))];
		
		if(useRandomSeed) {
			this.r = new Random(System.currentTimeMillis());
		}else {
			this.r = new Random(fixedSeed);
		}
		
		
		while(true) {
			int choice = r.nextInt(intArr.length);
			if(intArr[choice] == 0 && xNotPlaced) {
				intArr[choice] = x; 
				xNotPlaced = false;
			}else if(intArr[choice] == 0 && yNotPlaced) {
				intArr[choice] = y; 
				yNotPlaced = false;
			}else if(intArr[choice] == 0 && zNotPlaced) {
				intArr[choice] = z; 
				zNotPlaced = false;
			}else if (!xNotPlaced && !yNotPlaced && !zNotPlaced) {
				break;
			}
		}
		
		for(int i = 0; i < intArr.length; i++) {
			if(intArr[i] != 0) {
				continue;
			}else {
				do {
				nextInsertion = 70 + r.nextInt(40000);
				}while(selectedIntegers.contains(nextInsertion));
				intArr[i] = nextInsertion;
				selectedIntegers.add(nextInsertion);
			}
			
		}
		
		
//		for(int i = 0; i < intArr.length; i++) {
//			System.out.println("Element "+i+": "+intArr[i]);
//		} //debug
		
		return intArr;
	}
	
//	=== Part 1-1 algorithms ===
	
	int[] incrementalAlg(int[] input) {
		int x = max_value(input);
		int y = x;
		int z = x;
		
		for(int i : input) {
//			incrementalAlgCount++;
			if(boolCounter(i, x)) {
				z = y;
				y = x;
				x = i;
			}else if((boolCounter(i, y))) {
				z = y;
				y = i;
			}else if(boolCounter(i, z)) {
				z = i;
			}
		}
		
		return new int[] {x, y, z};
	}
	
	/**
	 * Hjälpmetod
	 * Räknar upp antalet jämförelser för inkrementella algoritmen
	 * @param a Tal som ska jämföras
	 * @param b Tal som ska jämföras
	 * @return true om a < b, false annars
	 */
	boolean boolCounter(int a, int b) {
		incrementalAlgCount++;
		if(a < b) {
			return true;
		}else {
			return false;
		}
	}
	
	int[] incrementalAlgv2(int[] input) {
		int x = max_value(input);
		int y = x;
		int z = x;
		
		for(int i : input) {
//			incrementalAlgCount++;
			if(boolCounter(i, z)) {
				if(boolCounter(i, x)) {
					z = y;
					y = x;
					x = i;
				}else if(boolCounter(i, y)) {
					z = y;
					y = i;
				}else {
					z = i;
				}
			}
		}
		return new int[] {x, y, z};
	}
	
	public int[] divideAndConquer(int[] arr) {
        int[] finalArr = divideConquer(arr);
        divideAndConquerAlgCount++;
        return finalArr;
        //return new int[]{finalArr[0], finalArr[1], finalArr[2]};
    }
    private int[] divideConquer(int[] arr) {
        if(arr.length == 3) {
            divideAndConquerAlgCount++;
            return sort(arr);
        }
        else {
            int[] arr1 = new int[arr.length/2];
            int[] arr2 = new int[arr.length/2];
            System.arraycopy(arr, 0, arr1, 0, arr.length/2);
            System.arraycopy(arr, arr.length/2, arr2, 0, arr.length/2);
            arr1 = divideConquer(arr1);
            divideAndConquerAlgCount++;
            arr2 = divideConquer(arr2);
            divideAndConquerAlgCount++;
            int[] finalArr = new int[arr1.length + arr2.length];
            System.arraycopy(arr1, 0, finalArr, 0, arr1.length);
            System.arraycopy(arr2, 0, finalArr, arr1.length, arr2.length);
            divideAndConquerAlgCount++;
            return finalArr;
        }
    }

    private int[] sort(int[] arr) {
    	int max = max_value(arr);
        int n = arr.length; 

        int output[] = new int[n]; 

        int count[] = new int[max+1];

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
     * @param nums Array med heltal
     * @return Det största heltalet i arrayen
     */
    int max_value(int[] nums) 
    {
        int max_value = 0;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] > max_value)
                max_value = nums[i];
//        System.out.println("Max value found: "+max_value);
        return max_value;
    } 
	
	
	
	
	public static void main(String[] args) {
		Lab2 asdf = new Lab2();
		
		
		System.out.println("\nTesting Incremental Algorithm: ");
		asdf.testIncrementalAlg();
		System.out.print("\n");
		
		System.out.println("\nTesting Incremental Algorithm v2: ");
		asdf.testIncrementalAlgv2();
		System.out.print("\n");
		
		System.out.println("\nTesting Divide and Conquer Algorithm: ");
		asdf.testDivideAndConquerAlg();
		System.out.print("\n");
	}
	
	void testIncrementalAlgv2() {
		int[] test = generateDataSet();
		
		int[] result = incrementalAlgv2(test);
		
		for(int i : result) {
			System.out.print(i+", ");
		}
		System.out.println("\nNumber of elements: "+test.length);
		System.out.println("Executed in "+incrementalAlgCount);
		
		incrementalAlgCount = 0;
		
	}
	
	void testIncrementalAlg() {
		int[] test = generateDataSet();
		
		int[] result = incrementalAlg(test);
		
		for(int i : result) {
			System.out.print(i+", ");
		}
		System.out.println("\nNumber of elements: "+test.length);
		System.out.println("Executed in "+incrementalAlgCount);
		
		incrementalAlgCount = 0;
	}
	
	void testDivideAndConquerAlg() {
		int[] test = generateDataSet();
		
		int[] result = divideAndConquer(test);
		
		for(int i : result) {
			System.out.print(i+", ");
		}
		System.out.println("\nNumber of elements: "+test.length);
		System.out.println("Executed in "+divideAndConquerAlgCount);
	}

}
