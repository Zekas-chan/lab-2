import java.util.Random;

/**
 * 
 * @author Gabriella Hafdell, Hanna Eriksson, Philip Larsson
 *
 */
public class Lab2 {
	//levers
	int arraySizeK = 5;
	boolean useRandomSeed = true;
	int fixedSeed = 0;
	
	//useful globals
	Random r;
	
	//analysis variables
	int incrementalAlgCount;
	int divideAndConquerAlgCount;

	public Lab2() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Tillåter i nuläget identiska element: FIXA!
	 */
	int[] generateDataSet() {
		int x = 13;
		int y = 42;
		int z = 69;
		
		boolean xNotPlaced = true;
		boolean yNotPlaced = true;
		boolean zNotPlaced = true;
		
		
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
				intArr[i] = 70 + r.nextInt(4000);
			}
			
		}
		
		
//		for(int i = 0; i < intArr.length; i++) {
//			System.out.println("Element "+i+": "+intArr[i]);
//		} //debug
		
		return intArr;
	}
	
//	=== Part 1-1 algorithms ===
	
	int[] incrementalAlg(int[] input) {
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;
		int z = Integer.MAX_VALUE;
		
		for(int i : input) {
			incrementalAlgCount++;
			if(i < x) {
				incrementalAlgCount++;
				z = y;
				y = x;
				x = i;
			}else if(i < y) {
				incrementalAlgCount++;
				z = y;
				y = i;
			}else if(i < z) {
				incrementalAlgCount++;
				z = i;
			}
		}
		
		return new int[] {x, y, z};
	}
	
	public int[] divideAndConquer(int[] arr) {

        if(arr.length == 3) {
            return arr;
        }
        else {
            int[] arr1 = new int[arr.length/2];
            int[] arr2 = new int[arr.length/2];
            for(int i = 0; i < arr1.length; i++) {
                arr1[i] = arr[i];
                arr2[i] = arr[arr2.length + i];
            }
            int[] arr3 = divideAndConquer(sort(arr1));
            int[] arr4 = divideAndConquer(sort(arr2));
            int[] finalArr = new int[arr3.length + arr4.length];
            for(int i = 0; i < finalArr.length; i++) {
                if(i < arr3.length) {
                    finalArr[i] = arr3[i];
                }
                else {
                    finalArr[i] = arr4[i - arr4.length];
                }
            }
            return sort(finalArr);
        }
    }

    private int[] sort(int[] arr) {
    	int max = max_value(arr);
        int n = arr.length; 

        int output[] = new int[n]; 

        int count[] = new int[4070];

        for (int i = 0; i < n; ++i) 
            ++count[arr[i]]; 

        for (int i = 1; i <= 4070-1; ++i) 
            count[i] += count[i - 1]; 
 
        for (int i = n - 1; i >= 0; i--) { 
            output[count[arr[i]] - 1] = arr[i]; 
            --count[arr[i]]; 
        }
        return output;
    }
    
    int max_value(int[] nums) 
    {
        int max_value = 0;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] > max_value)
                max_value = nums[i];
        return max_value;
    } 
	
	
	
	
	public static void main(String[] args) {
		Lab2 asdf = new Lab2();
		
		asdf.testIncrementalAlg();
		System.out.print("\n");
		asdf.testDivideAndConquerAlg();
		
	}
	
	void testIncrementalAlg() {
		int[] test = generateDataSet();
		
		int[] result = incrementalAlg(test);
		
		for(int i : result) {
			System.out.print(i+", ");
		}
		System.out.println("\nNumber of elements: "+test.length);
		System.out.println("Executed in "+incrementalAlgCount);
		
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
