import java.util.ArrayList;

public class SteepestDescent {

	// Private variables
	private double eps;
	private int maxIter;
	private double stepSize;
	private double x0;
	private ArrayList<double[]> bestPoint;
	private double[] bestObjVal;
	private double[] bestGradNorm;
	private long[] compTime;
	private int[] nIter;
	private boolean resultsExist;

	// Constructors to initialize variables
	public SteepestDescent() {
		this.eps = 0.001;
		this.maxIter = 100;
		this.stepSize = 0.05;
		this.x0 = 1;
		this.resultsExist = false;

	}

	public SteepestDescent(double eps, int maxIter, double stepSize, double[] x0) {

	}

	// Getters to return private variables if called
	public double getEps() {
		return this.eps;
	}

	public int getMaxIter() {
		return this.maxIter;
	}

	public double getStepSize() {
		return this.stepSize;
	}

	public double getX0() {
		return this.x0;
	}

	public double[] getBestObjVal() {
		return this.bestObjVal;
	}

	public double[] getBestGradNorm() {
		return this.bestGradNorm;
	}

	public ArrayList<double[]> getBestPoint() {
		return bestPoint;
	}

	public int[] getNIter() {
		return nIter;
	}

	public long[] getCompTime() {
		return compTime;
	}

	public boolean hasResults() {
		return resultsExist;
	}

	// Setters to assign values to private variables
	public void setEps(double a) {
		this.eps = a;
	}

	public void setMaxIter(int a) {
		this.maxIter = a;
	}

	public void setStepSize(double a) {
		this.stepSize = a;
	}

	public void setX0(double a) {
		this.x0 = a;
	}

	public void setBestObjVal(int i, double a) {
		this.bestObjVal[i] = a;
	}

	public void setBestGradNorm(int i, double a) {
		this.bestGradNorm[i] = a;
	}

	public void setBestPoint(int i, double[] a) {
		this.bestPoint.set(i, a);
	}

	public void setCompTime(int i, long a) {
		this.compTime[i] = a;
	}

	public void setNIter(int i, int a) {
		this.nIter[i] = a;
	}

	public void setHasResults(boolean a) {
		this.resultsExist = a;
	}

	// Initialize the starting value array
	public void init(ArrayList<Polynomial> P) {
		this.bestPoint = new ArrayList<double[]>();
		for (int counter = 0; counter < P.size(); counter++) {
			this.bestGradNorm = new double[P.size()];
			this.compTime = new long[P.size()];
			this.nIter = new int[P.size()];
			this.bestObjVal = new double[P.size()];
			this.bestPoint.add(new double[P.get(counter).getN()]);

		}
	}

	// Run one polynomial function
	public void run(int i, Polynomial P) {

		// initialize/reset variables to run algorithm
		int counter = 0;
		double converge;
		int counter1;
		int counter2;
		boolean loop = true;
		boolean loop1 = true;
		boolean Pset;
		long comptime = 0;
		this.bestGradNorm[i] = 0;
		this.bestObjVal[i] = 0;
		this.bestGradNorm[i] = 0;
		this.nIter[i] = 0;
		long start = System.currentTimeMillis();

		// if polynomial is not set return error
		while (loop1 == true) {
			this.bestPoint.set(i, new double[P.getN()]);

			// Main algorithm loop
			while (loop == true) {
				// If iteration is 0, equal best points to starting points
				if (this.nIter[i] == 0) {
					for (counter1 = 0; counter1 < bestPoint.get(i).length; counter1++) {
						this.bestPoint.get(i)[counter1] = this.x0;
						comptime += System.currentTimeMillis();
					}
					this.compTime[i] = comptime;
					this.bestGradNorm[i] = P.gradientNorm(this.bestPoint.get(i));
					this.bestObjVal[i] = P.f(this.bestPoint.get(i));
					this.compTime[i] = System.currentTimeMillis() - start;
				}

				// Else run algorithm using iterative process
				else {
					
					// Check if Armijo Line Search did not converge
					converge = this.lineSearch(P, this.bestPoint.get(i));
					if (converge == -393) {
						System.out.println("   Armijo line search did not converge!");
						
						// Reset values and stop steepest descent algorithm if true
						for (counter1 = 0; counter1 < bestPoint.get(i).length; counter1++) {
							this.bestPoint.get(i)[counter1] = this.x0;
						}
						this.bestGradNorm[i] = P.gradientNorm(this.bestPoint.get(i));
						this.bestObjVal[i] = P.f(this.bestPoint.get(i));
						this.nIter[i] = 1;
						break;
							}
					for (counter2 = 0; counter2 < this.bestPoint.get(i).length; counter2++) {
						this.bestPoint.get(i)[counter2] = this.bestPoint.get(i)[counter2]
								+ this.direction(P, bestPoint.get(i))[counter2] * converge;
						comptime = System.currentTimeMillis();
					}
					this.bestGradNorm[i] = P.gradientNorm(this.bestPoint.get(i));
					this.bestObjVal[i] = P.f(this.bestPoint.get(i));
					this.compTime[i] = System.currentTimeMillis() - start;
				}
				if (this.bestGradNorm[i] <= this.eps) {
					break;
				}

				if (this.nIter[i] == this.maxIter) {
					break;
				}
				this.nIter[i] += 1;
			}
			this.resultsExist = true;
			System.out.println("Polynomial " + (i + 1) + " done in " + this.compTime[i] + "ms.");
			break;
		}
	}

	// Print the results of one algorithm iteration for one polynomial
	public void printSingleResults(int i, boolean rowOnly) {
		String bestpoints = "";
		String point;
		int counter;

		// Format best points into string
		for (counter = 0; counter < this.bestPoint.get(i).length; counter++) {
			if (counter == this.bestPoint.get(i).length - 1) {
				point = String.format("%.4f", this.bestPoint.get(i)[counter]);
				bestpoints += point;
			} else {
				point = String.format("%.4f", this.bestPoint.get(i)[counter]);
				if (counter == 0) {
					bestpoints += (point + ", ");
				} else {
					bestpoints += (point + ", ");
				}
			}
		}

		// Print formatted string
		System.out.format("%8d%13f%13.6f%9d%17d", (i + 1), this.bestObjVal[i], this.bestGradNorm[i], this.nIter[i],
				this.compTime[i]);
		System.out.print("   " + bestpoints);
		System.out.println("");
	}

	// Prints all SteepestDescent parameters
	public void print() {
		System.out.println("Tolerance (epsilon): " + this.eps);
		System.out.println("Maximum iterations: " + this.maxIter);
		System.out.println("Starting point (x0): " + this.x0);
	}
	
	// Prints Statistics from gradnorm, iteration, and comp time 
	public void printStats() {
		// Averages for each variable
		double averageNormGrad = Stats.averageDouble(this.bestGradNorm);
		double averageIter = Stats.averageInt(this.nIter);
		double averageCompTime = Stats.averageLong(this.compTime);

		// Standard Deviation for each variable
		double stdevNormGrad = Stats.stdevDouble(this.bestGradNorm);
		double stdevIter = Stats.stdevInt(this.nIter);
		double stdevCompTime = Stats.stdevLong(this.compTime);

		// Minimum for each variable
		double minNormGrad = Stats.minDouble(this.bestGradNorm);
		int minIter = Stats.minInt(this.nIter);
		long minCompTime = Stats.minLong(this.compTime);

		// Maximum for each variable
		double maxNormGrad = Stats.maxDouble(bestGradNorm);
		int maxIter = Stats.maxInt(this.nIter);
		long maxCompTime = Stats.maxLong(this.compTime);

		// Print Average for each variable
		System.out.printf("Average%13.3f%13.3f%18.3f", averageNormGrad, averageIter, averageCompTime);
		System.out.println("");

		// Print St Dev for each variable
		System.out.printf("St Dev %13.3f%13.3f%18.3f", stdevNormGrad, stdevIter, stdevCompTime);
		System.out.println("");

		// Print Minimum for each variable
		System.out.printf("Min    %13.3f%13d%18d", minNormGrad, minIter, minCompTime);
		System.out.println("");

		// Print Maximum for each variable
		System.out.printf("Max    %13.3f%13d%18d\n", maxNormGrad, maxIter, maxCompTime);
		System.out.println("");
	}

	
	// Prints only the averages of a single polynomial
	public void printAverages() {
		// Print results for one polynomial
		for (int counter = 0; counter < this.bestPoint.size(); counter++) {
			this.printSingleResults(counter, resultsExist);
		}
		System.out.println("");
	}
	// Print results for all polynomials and statistics
	public void printAll() {

		System.out.println("Detailed results:");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
		System.out.println("-------------------------------------------------------------------------");
		
		// Print results for one polynomial
		for (int counter = 0; counter < this.bestPoint.size(); counter++) {
			this.printSingleResults(counter, resultsExist);
		}
		System.out.println("");
		
		// Print all stats
		printStats();

	}

	// Finds the next step size (return step size)
	public double lineSearch(Polynomial P, double[] x) {
		return this.stepSize;
	}

	// Finds the next direction using negative gradient
	public double[] direction(Polynomial P, double[] x) {
		int counter;
		double gradient[] = P.gradient(x);
		double direction[] = new double[gradient.length];
		for (counter = 0; counter < gradient.length; counter++) {
			direction[counter] = (gradient[counter] * -1);
		}
		return direction;
	}

	// Gets the parameters for the algorithm
	public boolean getParamsUser() {

		// Initialize variables
		int counter;
		double value;
		boolean loop = true;
		double input_eps;
		int input_maxiter;
		double input_stepsize;

		// Loop to obtain parameters
		while (loop == true) {

			// Get epsilon input
			System.out.print("Enter tolerance epsilon (0 to cancel): ");
			input_eps = Pro5_lawre273.getDouble("Enter tolerance epsilon (0 to cancel): ", 0.00,
					Double.POSITIVE_INFINITY);

			// If input is 0, end process
			if (input_eps == 0) {
				System.out.println("");
				System.out.println("Process canceled. No changes made to algorithm parameters.");
				System.out.println("");
				break;
			}

			// Get max iterations input
			System.out.print("Enter maximum number of iterations (0 to cancel): ");
			input_maxiter = Pro5_lawre273.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000);

			// If input is 0, end process
			if (input_maxiter == 0) {
				System.out.println("");
				System.out.println("Process canceled. No changes made to algorithm parameters.");
				System.out.println("");
				break;
			}

			// Set all parameters
			this.setEps(input_eps);
			this.setMaxIter(input_maxiter);

			// Obtain starting point input
			System.out.print("Enter value for starting point (0 to cancel): ");
			value = Pro5_lawre273.getDouble("Enter value for starting point (0 to cancel): ", Double.NEGATIVE_INFINITY,
					Double.POSITIVE_INFINITY);
			this.setX0(value);

			System.out.println("");
			System.out.println("Algorithm parameters set!");
			System.out.println("");
			this.setHasResults(false);
			loop = false;
		}
		return true;
	}
}
