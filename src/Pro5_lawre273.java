import java.io.*;
import java.util.ArrayList;

public class Pro5_lawre273 {

	// Object to read user input at command line
	static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

	// Main function to run menu loop and call other functions
	public static void main(String[] args) throws Exception {

		// Class objects, string input declared
		Polynomial Polynomial = new Polynomial();
		SteepestDescent SteepestDescent = new SteepestDescent();
		ArrayList<Polynomial> Polylist = new ArrayList<Polynomial>();
		SDFixed SDF = new SDFixed();
		SDArmijo SDA = new SDArmijo();
		SDGSS SDG = new SDGSS();
		String input = "";

		// Booleans declared for use for functions
		boolean loop = true;
		boolean errorcatch = true;
		boolean newpolynomial = true;
		boolean resultsset = false;
		boolean polyset = false;
		SteepestDescent.setHasResults(resultsset);

		// Main menu loop
		while (loop == true) {
			displayMenu();
			while (errorcatch == true) {
				try {
					input = cin.readLine();
					errorcatch = false;
				} catch (Exception e) {
					System.out.println("");
					System.out.println("ERROR: Invalid menu choice!\n");
					displayMenu();
				}
			}
			System.out.println("");
			errorcatch = true;
			if (input.equals("L") || input.equals("l")) {
				newpolynomial = loadPolynomialFile(Polylist);
				SteepestDescent.init(Polylist);

				if (newpolynomial == true) {
					SteepestDescent.setHasResults(false);
					polyset = true;

				}
			}

			else if (input.equals("F") || input.equals("f")) {

				// Check if poly ArrayList is empty, return error if true
				if (Polylist.isEmpty()) {
					System.out.println("ERROR: No polynomial functions are loaded!\n");

				} else {
					printPolynomials(Polylist);
				}
			}

			else if (input.equals("C") | input.equals("c")) {
				Polylist.clear();
				System.out.println("All polynomials cleared.\n");
				SteepestDescent.setHasResults(false);

			}

			else if (input.equals("S") || input.equals("s")) {
				getAllParams(SDF, SDA, SDG);

			}

			else if (input.equals("P") || input.equals("p")) {
				printAllParams(SDF, SDA, SDG);
			}

			else if (input.equals("R") || input.equals("r")) {

				// Check if poly ArrayList is empty, return error if true
				if (Polylist.isEmpty()) {
					System.out.println("ERROR: No polynomial functions are loaded!\n");
				}

				else {
					runAll(SDF, SDA, SDG, Polylist);
					SteepestDescent.setHasResults(true);
				}
			}

			else if (input.equals("D") || input.equals("d")) {

				// Check if results exists, return error if it doesn't
				boolean resultisset = SteepestDescent.hasResults();
				if (resultisset == false) {
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println("");
				} else {
					printAllResults(SDF, SDA, SDG, Polylist);
				}
			} else if (input.equals("X") || input.equals("x")) {
				boolean resultisset = SteepestDescent.hasResults();
				if (resultisset == false) {
					System.out.println("ERROR: Results do not exist for all line searches!");
					System.out.println("");
				} else {
					compare(SDF, SDA, SDG);
				}
			} else if (input.equals("Q") || input.equals("q")) {
				loop = false;
				System.out.println("Arrivederci.");
			} else {
				System.out.println("ERROR: Invalid menu choice!\n");
			}
		}

	}

	// Call the menu function
	public static void displayMenu() {
		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		System.out.println("L - Load polynomials from file");
		System.out.println("F - View polynomial functions");
		System.out.println("C - Clear polynomial functions");
		System.out.println("S - Set steepest descent parameters");
		System.out.println("P - View steepest descent parameters");
		System.out.println("R - Run steepest descent algorithms");
		System.out.println("D - Display algorithm performance");
		System.out.println("X - Compare average algorithm performance");
		System.out.println("Q - Quit");
		System.out.println("");
		System.out.print("Enter choice: ");
	}

	// Obtain Polynomial Parameters (variable, degree, coefficient)
	public static boolean getPolynomialDetails(Polynomial P) {

		// Initialize variables to use in for-loops
		int vcounter;
		int dcounter;
		double coefficient;
		int n;
		int degree;

		// Get variable input
		System.out.print("Enter number of variables (0 to cancel): ");
		n = getInteger("Enter number of variables (0 to cancel): ", 0, Integer.MAX_VALUE);

		// if n = 0, cancel process
		if (n == 0) {
			System.out.println("");
			System.out.println("Process canceled. No changes made to polynomial function.");
			System.out.println("");
			return false;
		}

		// Get degree input
		System.out.print("Enter polynomial degree (0 to cancel): ");
		degree = (getInteger("Enter polynomial degree (0 to cancel): ", 0, Integer.MAX_VALUE));

		// if degree = 0, cancel process
		if (degree == 0) {
			System.out.println("");
			System.out.println("Process canceled. No changes made to polynomial function.");
			System.out.println("");
			return false;
		}

		// Initialize Polynomial, Obtain coefficient inputs
		P.setN(n);
		P.setDegree(degree);
		P.init();
		for (vcounter = 0; vcounter < P.getN(); vcounter++) {
			System.out.println("Enter coefficients for variable x" + (vcounter + 1) + ": ");
			for (dcounter = 0; dcounter < (P.getDegree() + 1); dcounter++) {
				System.out.print("   Coefficient " + (dcounter + 1) + ": ");
				coefficient = getDouble("   Coefficient " + (dcounter + 1) + ": ", Double.NEGATIVE_INFINITY,
						Double.POSITIVE_INFINITY);
				P.setCoefs(vcounter, dcounter, coefficient);
			}
		}
		System.out.println("");
		System.out.println("Polynomial complete!");
		System.out.println("");
		return true;
	}

	// Loads polynomials from data file
	public static boolean loadPolynomialFile(ArrayList<Polynomial> P) throws Exception {

		// Initialize variables
		String polyline;
		boolean loop = true;
		int counter = 0;
		int polycounter = 0;
		int loadedpoly = 0;
		ArrayList<double[]> CoefsList = new ArrayList<double[]>();
		double[] polycoefs;

		// User enters file name input
		System.out.print("Enter file name (0 to cancel): ");
		String input = cin.readLine();
		System.out.println("");

		// If 0 is inputed cancel file loading
		if (input.equals("0")) {
			System.out.println("File loading process canceled.\n");
			return false;
		}

		// Initialize file variable, check if file exists
		File file = new File(input);
		boolean fileexists = file.exists();
		if (fileexists == false) {
			System.out.println("ERROR: File not found!\n");
			return false;
		}

		// If file exists, open file reader and iterate through it
		BufferedReader fin = new BufferedReader(new FileReader(input));
		while (loop = true) {
			Polynomial poly = new Polynomial();
			polyline = fin.readLine();

			// While the string contains coefficients (not "*")
			do {

				// Split each line by ",", then convert to a double array
				String polysplit[] = polyline.split(",");
				polycoefs = new double[polysplit.length];
				for (counter = 0; counter < polysplit.length; counter++) {
					polycoefs[counter] = Double.parseDouble(polysplit[counter]);
				}
				CoefsList.add(polycoefs);

				// If file ends, break
				polyline = fin.readLine();
				if (polyline == null) {
					loop = false;
					break;
				}
			} while (!polyline.contains("*"));
			polycounter += 1;

			// Check if degrees between polynomials are consistent
			for (counter = 0; counter < CoefsList.size(); counter++) {
				if (CoefsList.get(counter).length != CoefsList.get(0).length) {
					System.out.println("ERROR: Inconsistent dimensions in polynomial " + polycounter + "!\n");
					CoefsList.clear();
					break;
				}
			}

			// Initialize polynomial
			if (!CoefsList.isEmpty()) {
				poly.setDegree(CoefsList.get(0).length - 1);
				poly.setN(CoefsList.size());
				poly.init();
				for (counter = 0; counter < (poly.getN()); counter++) {
					for (int counter2 = 0; counter2 < (poly.getDegree()) + 1; counter2++) {
						poly.setCoefs(counter, counter2, CoefsList.get(counter)[counter2]);
					}
				}
				P.add(poly);
				poly = null;
				loadedpoly += 1;
			}
			CoefsList.clear();

			// If file ends
			if (polyline == null) {
				System.out.println(loadedpoly + " polynomials loaded!\n");
				fin.close();
				loop = false;
				break;
			}
		}

		// Close file
		fin.close();
		return true;
	}

	// Prints each polynomial from ArrayList from loaded data files
	public static void printPolynomials(ArrayList<Polynomial> P) {
		int counter = 0;
		System.out.println("---------------------------------------------------------");
		System.out.println("Poly No.  Degree   # vars   Function");
		System.out.println("---------------------------------------------------------");

		// Prints each individual polynomial from Poly ArrayList
		for (counter = 0; counter < P.size(); counter++) {
			System.out.format("%8d%8d%9d", (counter + 1), P.get(counter).getDegree(), P.get(counter).getN());
			System.out.print("   ");
			P.get(counter).print();
		}
		System.out.println("");
	}

	// Get Algorithm Parameters
	public static void getAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
		SDF.getParamsUser();
		SDA.getParamsUser();
		SDG.getParamsUser();
	}

	public static void printAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
		SDF.print();
		SDA.print();
		SDG.print();
	}

	public static void runAll(SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial> P) {
		// Check if poly ArrayList is empty, return error if true
		if (P.isEmpty()) {
			System.out.println("ERROR: No polynomial functions are loaded!\n");
		}

		else {

			// Initialize line search objects
			SDF.init(P);
			SDA.init(P);
			SDG.init(P);

			// Run fixed line search process
			System.out.println("Running SD with a fixed line search:");
			for (int counter = 0; counter < P.size(); counter++) {
				SDF.run(counter, P.get(counter));
			}
			System.out.println("");

			// Run Armijo line search process
			System.out.println("Running SD with an Armijo line search:");
			for (int counter = 0; counter < P.size(); counter++) {
				SDA.run(counter, P.get(counter));
			}
			System.out.println("");

			// Run Golden Section search process
			System.out.println("Running SD with a golden section line search:");
			for (int counter = 0; counter < P.size(); counter++) {
				SDG.run(counter, P.get(counter));
			}
			System.out.println("");
			System.out.println("All polynomials done.");

			System.out.println("");
			SDF.setHasResults(true);
		}
	}

	public static void printAllResults(SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial> P) {

		// Print Results and Stats for fixed line search
		System.out.println("Detailed results for SD with a fixed line search:");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
		System.out.println("-------------------------------------------------------------------------");
		SDF.printAverages();

		System.out.println("Statistical summary for SD with a fixed line search:");
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");
		SDF.printStats();
		System.out.println("");

		// Print Results and Stats for Armijo line search
		System.out.println("Detailed results for SD with an Armijo line search:");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
		System.out.println("-------------------------------------------------------------------------");
		SDA.printAverages();

		System.out.println("Statistical summary for SD with an Armijo line search:");
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");
		SDA.printStats();

		// Print Results and Stats for Golden Section line search
		System.out.println("");
		System.out.println("Detailed results for SD with a golden section line search:");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
		System.out.println("-------------------------------------------------------------------------");
		SDG.printAverages();

		System.out.println("Statistical summary for SD with a golden section line search:");
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");
		SDG.printStats();

	}

	public static void compare(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {

		// Initialize line search counters
		int armijoCounter = 0;
		int fixedCounter = 0;
		int gssCounter = 0;
		int bestGradcounter = 0;
		int bestItercounter = 0;
		int bestTimecounter = 0;
		String GradWinner = "";
		String IterWinner = "";
		String TimeWinner = "";
		String OverallWinner = "";

		// Initialize Stat variables
		double fixedGradNorm = Stats.averageDouble(SDF.getBestGradNorm());
		double fixedIter = Stats.averageInt(SDF.getNIter());
		double fixedTime = Stats.averageLong(SDF.getCompTime());
		double armijoGradNorm = Stats.averageDouble(SDA.getBestGradNorm());
		double armijoIter = Stats.averageInt(SDA.getNIter());
		double armijoTime = Stats.averageLong(SDA.getCompTime());
		double gssGradNorm = Stats.averageDouble(SDG.getBestGradNorm());
		double gssIter = Stats.averageInt(SDG.getNIter());
		double gssTime = Stats.averageLong(SDG.getCompTime());
		double bestGradNorm = fixedGradNorm;
		double bestIter = fixedIter;
		double bestTime = fixedTime;

		// Check which average gradnorm is lowest
		if (armijoGradNorm < bestGradNorm) {
			bestGradNorm = armijoGradNorm;
			bestGradcounter = 1;
		} else if (gssGradNorm < bestGradNorm) {
			bestGradNorm = gssGradNorm;
			bestGradcounter = 2;
		}
		if (bestGradcounter == 0) {
			fixedCounter += 1;
			GradWinner = "Fixed";
		}
		if (bestGradcounter == 1) {
			armijoCounter += 1;
			GradWinner = "Armijo";
		}
		if (bestGradcounter == 2) {
			gssCounter += 1;
			GradWinner = "GSS";
		}

		// Check which average iteration is lowest
		if (armijoIter < bestIter) {
			bestIter = armijoIter;
			bestItercounter = 1;
		} else if (gssIter < bestIter) {
			bestIter = gssIter;
			bestItercounter = 2;
		}
		if (bestItercounter == 0) {
			fixedCounter += 1;
			IterWinner = "Fixed";
		}
		if (bestItercounter == 1) {
			armijoCounter += 1;
			IterWinner = "Armijo";
		}
		if (bestItercounter == 2) {
			gssCounter += 1;
			IterWinner = "GSS";
		}

		// Check which average comp time is lowest
		if (armijoTime < bestTime) {
			bestTime = armijoTime;
			bestTimecounter = 1;
		}
		if (gssTime < bestTime) {
			bestTime = gssTime;
			bestTimecounter = 2;
		}
		if (bestTimecounter == 0) {
			fixedCounter += 1;
			TimeWinner = "Fixed";
		}
		if (bestTimecounter == 1) {
			armijoCounter += 1;
			TimeWinner = "Armijo";
		}
		if (bestTimecounter == 2) {
			gssCounter += 1;
			TimeWinner = "GSS";
		}

		// Check which line search is the winner, if not unanimous return unclear
		if (fixedCounter == 3) {
			OverallWinner = "Fixed";
		} else if (armijoCounter == 3) {
			OverallWinner = "Armijo";
		} else if (gssCounter == 3) {
			OverallWinner = "GSS";
		} else if (fixedCounter != 3 && armijoCounter != 3 && gssCounter != 3) {
			OverallWinner = "Unclear";
		}

		// Print comparison table and overall winner
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");
		System.out.printf("Fixed  %13.3f%13.3f%18.3f\n", fixedGradNorm, fixedIter, fixedTime);
		System.out.printf("Armijo %13.3f%13.3f%18.3f\n", armijoGradNorm, armijoIter, armijoTime);
		System.out.printf("GSS    %13.3f%13.3f%18.3f\n", gssGradNorm, gssIter, gssTime);
		System.out.println("---------------------------------------------------");
		System.out.printf("Winner%14s%13s%18s\n", GradWinner, IterWinner, TimeWinner);
		System.out.println("---------------------------------------------------");
		System.out.print("Overall winner: ");
		System.out.println(OverallWinner);
		System.out.println("");

	}

	// Get Integer function used in to check integer input for errors
	public static int getInteger(String prompt, int LB, int UB) {
		boolean getintloop = true;
		int input1 = 1;
		while (getintloop == true) {
			String s;
			if (LB == Integer.MAX_VALUE * -1) {
				s = "ERROR: Input must be an integer in [-infinity, " + UB + "]!";
			} else if (UB == Integer.MAX_VALUE) {
				s = "ERROR: Input must be an integer in [" + LB + ", infinity]!";
			} else if (UB == Integer.MAX_VALUE && LB == Integer.MAX_VALUE * -1) {
				s = "ERROR: Input must be an integer in [-infinity, infinity]!";
			} else {
				s = "ERROR: Input must be an integer in [" + LB + ", " + UB + "]!";
			}
			// Try catch loop to check for errors
			try {
				input1 = Integer.parseInt(cin.readLine());
				getintloop = false;
			} catch (NumberFormatException e1) {
				System.out.println(s);
				System.out.println("");
				System.out.print(prompt);
				input1 = 1;
			} catch (IOException e1) {
				System.out.println(s);
				System.out.println("");
				System.out.print(prompt);
				input1 = 1;
			}

			// If loop if input is past number bounds, accounts for LB, UB = infinity
			if (input1 < LB | input1 > UB) {
				System.out.println(s);
				System.out.println("");
				getintloop = true;
				System.out.print(prompt);
			}
		}
		return input1;

	}

	// Get Double function used in to check integer double for errors
	public static double getDouble(String prompt, double LB, double UB) {
		boolean getdoubleloop = true;
		double input1 = 1;
		while (getdoubleloop == true) {
			String s;

			// Change string output if either bound is infinity
			if (LB == Double.NEGATIVE_INFINITY && UB != Double.POSITIVE_INFINITY) {
				s = (String.format("ERROR: Input must be a real number in [-infinity , %.2f", UB) + "]!\n");
			} else if (UB == Double.POSITIVE_INFINITY && LB != Double.NEGATIVE_INFINITY) {
				s = (String.format("ERROR: Input must be a real number in [%.2f", LB) + ", infinity]!\n");
			} else if (UB == Double.POSITIVE_INFINITY && LB == Double.NEGATIVE_INFINITY) {
				s = "ERROR: Input must be a real number in [-infinity, infinity]!\n";
			} else {
				s = (String.format("ERROR: Input must be a real number in [%.2f", LB) + String.format(", %.2f", UB)
						+ "]!\n");
			}

			// Try catch loop to check for errors
			try {
				input1 = Double.parseDouble(cin.readLine());
				getdoubleloop = false;
			} catch (NumberFormatException e1) {
				System.out.println(s);
				System.out.print(prompt);
				input1 = 1;
			} catch (IOException e1) {
				System.out.println(s);

				System.out.print(prompt);
				input1 = 1;
			}

			// If input is out of bounds
			if (input1 < LB | input1 > UB) {
				System.out.println(s);
				getdoubleloop = true;
				System.out.print(prompt);
			}
		}
		return input1;
	}
}
