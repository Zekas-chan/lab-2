import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
	boolean useRandomSeed = true;
	int fixedSeed = 1;

	// levers part1-2
	int arrayK = 1; // min 1

	// useful globals
	Random r;
	DecimalFormat df;

	// analysis variables
	int divideAndConquerNlogN;
	int divideAndConquerLinearCount;

	public Lab2() {
		String pattern = "##0.##";

		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(' ');

		this.df = new DecimalFormat(pattern, symbols);
		df.setGroupingSize(3);
	}

	double[] generateTestArr(boolean negativeNumbersAllowed) {
		double[] arr = new double[(int) Math.pow(2, arrayK)];

		if (useRandomSeed) {
			r = new Random(System.currentTimeMillis());
		} else {
			r = new Random(fixedSeed);
		}

		for (int i = 0; i < arr.length; i++) {
			do {
				arr[i] = r.nextInt(5) + r.nextDouble();
				if (negativeNumbersAllowed) {
					arr[i] -= 2.5;
				}
			} while (arr[i] == 0);
		}

		return arr;
	}

	/*
	 * Frågeställning: Om alla tal är <1, bör en produkt fortfarande returneras?
	 * 
	 * Får en inkrementell lösning se ut som productFinderLinear (om den konverteras
	 * till ett rekursiv version)?
	 */
	public double[] divideAndConquer(double[] arr) {
        if (arr.length == 2) {
            return new double[] { arr[0] * arr[1], arr[0], arr[1] };
        }
        int half = arr.length / 2;
        double[] left = new double[half];
        double[] right = new double[half];
        System.arraycopy(arr, 0, left, 0, half);
        System.arraycopy(arr, half, right, 0, half);
        System.out.println("\nLeft");
        for(double i:left) {
            System.out.print(i+", ");
        }
        System.out.println("\nRight");
        for(double i:right) {
            System.out.print(i+", ");
        }
        double[] leftProduct = divideAndConquer(left);
        double[] rightProduct = divideAndConquer(right);
        System.out.println("\nLeftDivide\n"+leftProduct[0]+" , "+leftProduct[1]+" , "+leftProduct[2]);
        System.out.println("\nRightDivide\n"+rightProduct[0]+" , "+rightProduct[1]+" , "+rightProduct[2]);
        if (leftProduct[0] < 0 && rightProduct[0] > 0) {
            double element = leftProduct[0] < 0 ? leftProduct[1] : leftProduct[0];
            return new double[] {rightProduct[0] * element, leftProduct[1], rightProduct[2]};
        }
        if(rightProduct[0] < 0 && leftProduct[0] > 0) {
            double element = rightProduct[0] < 0 ? rightProduct[1] : rightProduct[0];
            return new double[] {leftProduct[0] * element, leftProduct[1], rightProduct[2]};
        }
        return new double[] {rightProduct[0]*leftProduct[0], leftProduct[1], rightProduct[2]};

    }

	/*
	 * Must be converted to a recursive version.
	 */
	double productFinderLinear(double[] arr) {
		double maxProduct = 1;
		double curProduct = 1;

		for (double d : arr) {
			divideAndConquerLinearCount++;
			if (d < 1) {
				divideAndConquerLinearCount++;
				if (curProduct > maxProduct) {
					maxProduct = curProduct;
				}
				curProduct = 1;
				continue;
			}
			curProduct *= d;
			divideAndConquerLinearCount++;
		}

		double finalProduct = curProduct > maxProduct ? curProduct : maxProduct;
		return finalProduct;
	}

	double productFinderLinearRecursive(double[] arr) {
		if (arr.length == 2) {
			if (arr[0] > 1 && arr[1] > 1) {
				return arr[0] * arr[1];
			} else if (arr[0] > 1) {
				return arr[0];
			} else if (arr[1] > 1) {
				return arr[1];
			} else {
				double pls = arr[0] > arr[1] ? arr[0] : arr[1];
				return pls;
			}
		} // fulaste basfallet i historien
		
		double[] arr1 = new double[arr.length/2];
 		double[] arr2 = new double[arr.length/2];
		
 		System.arraycopy(arr, 0, arr1, 0, arr.length / 2);
		System.arraycopy(arr, arr.length / 2, arr2, 0, arr.length / 2);
		
		double[] arr_1 = productFinderLinearRecursive(arr1);
		double[] arr_2 = productFinderLinearRecursive(arr1);
		
	}

	public static void main(String[] args) {
		Lab2 asdf = new Lab2();

		System.out.println("\n======PART 2======");

		for (int i = 0; i < 5; i++) {
			asdf.testOnAlg();
			asdf.arrayK++;
		}

	}

	void testNlogNalg() {
		System.out.println("\nTesting n log n: ");
		double[] test = generateTestArr(true);

		// print testarr
		System.out.println("Input:");
		System.out.println("[");
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

		// display result
		double[] result = { algName(test) };

		System.out.println("Output:");
		for (double i : result) {
			System.out.print(i + ", ");
		}
		System.out.println("\nNumber of elements: " + test.length);
		System.out.println("Executed in " + divideAndConquerNlogN);
		divideAndConquerNlogN = 0;

	}

	void testOnAlg() {
		System.out.println("\nTesting O(n): ");
		double[] test = generateTestArr(false);

		// print testarr
		System.out.println("Input:");
		System.out.print("[");
		if (test.length > 32) {
			System.out.print("Input is too large to display.");
		} else {
			for (int i = 0; i < test.length; i++) {
				System.out.print(df.format(test[i]));
				if (i < test.length - 1) {
					System.out.print(", ");
				}
			}
		}
		System.out.println("]");

		// display result
		double[] result = { productFinderLinear(test) };

		System.out.println("Output:");
		for (double i : result) {
			System.out.print(df.format(i) + ", ");
		}
		System.out.println("\nNumber of elements: " + test.length);
		System.out.println("Executed in " + divideAndConquerLinearCount);
		divideAndConquerLinearCount = 0;
	}

}
