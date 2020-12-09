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
	int arrayK = 4; // min 1

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
		
		if(useRandomSeed) {
			r = new Random(System.currentTimeMillis());
		}else {
			r = new Random(fixedSeed);
		}
		
		
		for(int i = 0; i < arr.length; i++) {
			do {
			arr[i] = 1 + r.nextInt(50);
			if(negativeNumbersAllowed) {
				arr[i] -= 25;
			}
			}while(arr[i] == 0);
		}
		
		return arr;
	}
		
	

	public static void main(String[] args) {
		Lab2 asdf = new Lab2();
		
		double[] test = asdf.generateTestArr(true);
		
		for(double i : test) {
			System.out.print(asdf.df.format(i)+", ");
		}

		System.out.println("\n======PART 2=====");
		

	}

	void testNlogNalg() {
		System.out.println("\nTesting n log n: ");
		double[] test = generateTestArr(true);
		
		//print testarr
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
		
		//display result
		int[] result = algName(test);
		
		System.out.println("Output:");
		for (int i : result) {
			System.out.print(i + ", ");
		}
		System.out.println("\nNumber of elements: " + test.length);
		System.out.println("Executed in " + divideAndConquerNlogN);
		divideAndConquerNlogN = 0;
		

	}
	
	void testOnAlg() {
		System.out.println("\nTesting O(n): ");
		double[] test = generateTestArr(false);
		
		//print testarr
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
		
		//display result
		int[] result = algName(test);
		
		System.out.println("Output:");
		for (int i : result) {
			System.out.print(i + ", ");
		}
		System.out.println("\nNumber of elements: " + test.length);
		System.out.println("Executed in " + divideAndConquerLinearCount);
		divideAndConquerLinearCount = 0;
	}
	
}
