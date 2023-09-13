public class SDFixed extends SteepestDescent {

	// Initialize private variable
	private double alpha;

	// Constructors
	public SDFixed() {
		this.alpha = 0.01;
	}

	public SDFixed(double alpha) {
	}

	// Getters
	public double getAlpha() {
		return this.alpha;

	}

	// Setters
	public void setAlpha(double a) {
		this.alpha = a;
	}

	// Calculates line search for the Steepest Descent Run function, return fixed
	// alpha
	public double lineSearch(Polynomial P, double[] x) {
		return this.alpha;

	}

	// Get Fixed line search parameters
	public boolean getParamsUser() {
		double inputAlpha;
		System.out.println("Set parameters for SD with a fixed line search: ");

		// Get step size input
		System.out.print("Enter fixed step size (0 to cancel): ");
		inputAlpha = Pro5_lawre273.getDouble("Enter fixed step size (0 to cancel): ", 0, Double.POSITIVE_INFINITY);

		// If input is 0, end process
		if (inputAlpha == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		// Get Steepest Descent params
		super.getParamsUser();

		// Initialize params
		this.setAlpha(inputAlpha);
		return true;
	}

	// Print fixed line search parameters
	public void print() {
		System.out.println("SD with a fixed line search:");
		super.print();
		System.out.println("Fixed step size (alpha): " + this.alpha);
	}
}