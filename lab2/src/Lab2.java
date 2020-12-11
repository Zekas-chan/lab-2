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
	int incrementalLinearCount;

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

		if (negativeNumbersAllowed) {
			for (int i = 0; i < arr.length; i++) {
				do {
					arr[i] = r.nextInt(10) + r.nextDouble();
					arr[i] -= 5;
				} while (arr[i] == 0);
			}
		} else {
			for (int i = 0; i < arr.length; i++) {
				do {
					arr[i] = r.nextInt(5) + r.nextDouble();
				} while (arr[i] == 0);
			}
		}
		return arr;
	}

	/*
	 * Frågeställning: Om alla tal är <1, bör en produkt fortfarande returneras?
	 * 
	 * Får en inkrementell lösning se ut som productFinderLinear (om den konverteras
	 * till ett rekursiv version)?
	 * 
	 * 
	 */
	public double divideAndConquer(double arr[]) {
		if (arr.length == 1 && ifcounter(1)) {
			return arr[0];
		}//base case
		
		
		divideAndConquerNlogN++;
		int half = arr.length / 2;
		double[] left = new double[half];
		double[] right = new double[half];
		System.arraycopy(arr, 0, left, 0, half);
		System.arraycopy(arr, half, right, 0, half);
		//divide
		
		
		double leftProduct = divideAndConquer(left);
		divideAndConquerNlogN++;
		double rightProduct = divideAndConquer(right);
		divideAndConquerNlogN++;
		double crossingProduct = crossingProduct(arr);
		divideAndConquerNlogN += arr.length;
		
		divideAndConquerNlogN++;
		if (leftProduct >= rightProduct && leftProduct >= crossingProduct) {
			return leftProduct;
		} else if (rightProduct >= leftProduct && rightProduct >= crossingProduct) {
			return rightProduct;
		} else {
			return crossingProduct;
		}
	}

	private double crossingProduct(double arr[]) {
		double maxProduct = 1;
		double leftNegativeProduct = 0;
		double leftPositiveProduct = 0;
		double rightNegativeProduct = 0;
		double rightPositiveProduct = 0;
		double productL = 1;
		double productR = 1;
		for (int i = 0; i < arr.length / 2; i++) {
			productL = productL * arr[((arr.length / 2) - 1) - i];
			if (productL > leftPositiveProduct) {
				leftPositiveProduct = productL;
			} else if (productL < leftNegativeProduct) {
				leftNegativeProduct = productL;
			}

			productR = productR * arr[i + ((arr.length / 2))];
			if (productR > rightPositiveProduct) {
				rightPositiveProduct = productR;
			} else if (productR < rightNegativeProduct) {
				rightNegativeProduct = productR;
			}
		}

		maxProduct = leftNegativeProduct * rightNegativeProduct > leftPositiveProduct * rightPositiveProduct
				? leftNegativeProduct * rightNegativeProduct
				: leftPositiveProduct * rightPositiveProduct;

		return maxProduct;

	}

	double[] productFinderAlgLinear(double[] arr) {
		if (arr.length == 1 && ifcounter(2)) {
			return new double[] { arr[0], arr[0], arr[0] };
		} // base case
		
		incrementalLinearCount++;
		double[] inputCopy = new double[arr.length - 1];
		System.arraycopy(arr, 1, inputCopy, 0, arr.length - 1);
		
		incrementalLinearCount++;
		double[] arrd = productFinderAlgLinear(inputCopy);
		
		incrementalLinearCount++;
		double curProduct = arr[0] > (arr[0] * arrd[1]) ? arr[0] : arr[0] * arrd[1];
		double maxProduct = arrd[2];
		
		if (curProduct > maxProduct && ifcounter(2)) {
			maxProduct = curProduct;
		}
		
		incrementalLinearCount++;
		return new double[] { inputCopy[0], curProduct, maxProduct };
	}

	/**
	 * Hittar maximala subarray-produkten i en array med doubles (iterativt).
	 * 
	 * @param arr
	 * @return
	 */
	double productFinderCheck(double[] arr) {
		double maxProduct = arr[0];
		double imax = arr[0];
		double imin = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < 0) {
				double swap = imin;
				imin = imax;
				imax = swap;
			}
			imax = arr[i] > (imax * arr[i]) ? arr[i] : (imax * arr[i]);
			imin = arr[i] < (imin * arr[i]) ? arr[i] : (imin * arr[i]);
			maxProduct = maxProduct > imax ? maxProduct : imax;
		}

		return maxProduct;
	}
	
	boolean ifcounter(int mode) {
		switch(mode) {
		case 1:
			divideAndConquerNlogN++;
			break;
			
		case 2:
			incrementalLinearCount++;
			break;
		}
		return true;
	}

	public static void main(String[] args) {
		Lab2 asdf = new Lab2();

		System.out.println("\n======PART 2======");
//		double[] test = asdf.generateTestArr(false);
//		System.out.println("Input:");
//		System.out.print("[");
//		if (test.length > 32) {
//			System.out.print("Input is too large to display.");
//		} else {
//			for (int i = 0; i < test.length; i++) {
//				System.out.print(asdf.df.format(test[i]));
//				if (i < test.length - 1) {
//					System.out.print(", ");
//				}
//			}
//		}
//		System.out.println("]");
//		
//		asdf.productFinderAlgLinear(test);
		for (int i = 0; i < 10; i++) {
			asdf.testOnAlg();
			asdf.arrayK++;
		}

	}

	void testNlogNalg() {
		System.out.println("\nTesting n log n: ");
		double[] test = generateTestArr(true);

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
		double[] result = { divideAndConquer(test) };
		double verify = productFinderCheck(test);

		System.out.println("Output:");
		for (double i : result) {
			System.out.print(df.format(i) + ", ");
		}
		System.out.println("\nNumber of elements: " + test.length);
		System.out.println("Executed in " + divideAndConquerNlogN);
		divideAndConquerNlogN = 0;

		if (result[0] != verify) {
			System.out.println("Mismatch: Algorithm returned " + df.format(result[0]) + ", but the check returned "
					+ df.format(verify));
		}

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
		double[] result = { productFinderAlgLinear(test)[2] };

		double check = productFinderCheck(test);

		System.out.println("Output:");
		for (double i : result) {
			System.out.print(df.format(i) + ", ");
		}
		System.out.println("\nNumber of elements: " + test.length);
		System.out.println("Executed in " + incrementalLinearCount);
		incrementalLinearCount = 0;

		if (check != result[0]) {
			System.out.println("Mismatch: Algorithm returned " + df.format(result[0]) + ", but linear version found "
					+ df.format(check));
		}
	}

}
