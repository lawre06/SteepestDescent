public class SDArmijo extends SteepestDescent {

	// Initialize Private variables
	private double maxStep;
	private double beta;
	private double tau;
	private int K;

	// Constructors
	public SDArmijo() {
		this.maxStep = 1;
		this.tau = 0.5;
		this.beta = 0.0001;
		this.K = 10;
	}

	public SDArmijo(double maxStep, double beta, double tau, int K) {

	}

	// Getters
	public double getMaxStep() {
		return maxStep;
	}

	public double getBeta() {
		return beta;
	}

	public double getTau() {
		return tau;
	}

	public int getK() {
		return K;
	}

	// Setters
	public void setMaxStep(double a) {
		this.maxStep = a;
	}

	public void setBeta(double a) {
		this.beta = a;
	}

	public void setTau(double a) {
		this.tau = a;
	}

	public void setK(int a) {
		this.K = a;
	}

	// Calculates line search for the Steepest Descent Run function using the Armijo
	// Line Search Process
	public double lineSearch(Polynomial P, double[] x) {
		double alpha;
		int counter = 0;
		int counter1;
		alpha = this.maxStep;
		double[] armijoDecr = new double[x.length];

		// While iterations are less then K run line search
		for (counter = 0; counter <= this.K; counter++) {

			// Calculate LHS of Armijo condition
			for (counter1 = 0; counter1 < x.length; counter1++) {
				armijoDecr[counter1] = x[counter1] - alpha * P.gradient(x)[counter1];

			}

			// If LHS <= RHS, return alpha, or multiply alpha by tau
			if (P.f(armijoDecr) <= (P.f(x) - (alpha * this.beta * Math.pow(P.gradientNorm(x), 2)))) {
				return alpha;
			} else {
				alpha = alpha * this.tau;
			}

		}
		// Cancel line search process if K iterations is reached
		if (counter > this.K) {
			return -393;
		}
		return -393;
	}

	// Get Armijo parameters
	public boolean getParamsUser() {
		double inputMaxStep;
		double inputBeta;
		double inputTau;
		int inputK;

		System.out.println("Set parameters for SD with an Armijo line search:");

		// Get max step size input
		System.out.print("Enter Armijo max step size (0 to cancel): ");
		inputMaxStep = Pro5_lawre273.getDouble("Enter Armijo max step size (0 to cancel): ", 0,
				Double.POSITIVE_INFINITY);

		// If input is 0, end process
		if (inputMaxStep == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		// Get beta input
		System.out.print("Enter Armijo beta (0 to cancel): ");
		inputBeta = Pro5_lawre273.getDouble("Enter Armijo beta (0 to cancel): ", 0, 1);

		// If input is 0, end process
		if (inputBeta == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		// Get tau input
		System.out.print("Enter Armijo tau (0 to cancel): ");
		inputTau = Pro5_lawre273.getDouble("Enter Armijo tau (0 to cancel): ", 0, 1);

		// If input is 0, end process
		if (inputTau == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		// Get K input
		System.out.print("Enter Armijo K (0 to cancel): ");
		inputK = Pro5_lawre273.getInteger("Enter Armijo K (0 to cancel): ", 0, Integer.MAX_VALUE);

		// If input is 0, end process
		if (inputK == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		// Get original Steepest Descent parameters
		super.getParamsUser();

		// Initialize variables
		this.maxStep = inputMaxStep;
		this.beta = inputBeta;
		this.tau = inputTau;
		this.K = inputK;
		return true;
	}

	// Print Armijo line search parameters
	public void print() {
		System.out.println("\nSD with a Armijo line search:");

		// Print Steepest Descent parameters
		super.print();

		// Print Armijo parameters
		System.out.println("Armijo maximum step size: " + this.maxStep);
		System.out.println("Armijo beta: " + this.beta);
		System.out.println("Armijo tau: " + this.tau);
		System.out.println("Armijo maximum iterations: " + this.K);

	}
}
