import java.util.List;

public class SDGSS extends SteepestDescent {
	private final double _PHI_ = (1 + Math.sqrt(5)) / 2;
	private double maxStep;
	private double minStep;
	private double delta;

	public SDGSS() {
		this.maxStep = 1;
		this.minStep = 0.001;
		this.delta = 0.001;
	}

	public SDGSS(double maxStep, double minStep, double delta) {

	}

	public double getMaxStep() {
		return maxStep;
	}

	public double getMinStep() {
		return minStep;
	}

	public double getDelta() {
		return delta;
	}

	public void setMaxStep(double a) {
		this.maxStep = a;
	}

	public void setMinStep(double a) {
		this.minStep = a;
	}

	public void setDelta(double a) {
		this.delta = a;
	}

	// Calculates line search for the Steepest Descent Run function using the Golden
	// Section Line Search Process
	public double lineSearch(Polynomial P, double[] x) {
		double c;
		c = this.minStep + ((this.maxStep - this.minStep) / this._PHI_);

		// Calls GSS function to get the next step
		double nextstep = this.GSS(minStep, maxStep, c, x, x, P);
		return nextstep;
	}

	public boolean getParamsUser() {
		double inputMaxStep;
		double inputMinStep;
		double inputDelta;

		System.out.println("Set parameters for SD with an golden section line search:");

		// Get max step size input
		System.out.print("Enter GSS maximum step size (0 to cancel): ");
		inputMaxStep = Pro5_lawre273.getDouble("Enter GSS maximum step size (0 to cancel): ", 0,
				Double.POSITIVE_INFINITY);

		// If input is 0, end process
		if (inputMaxStep == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		// Get min step size input
		System.out.print("Enter GSS minimum step size (0 to cancel): ");
		inputMinStep = Pro5_lawre273.getDouble("Enter GSS minimum step size (0 to cancel): ", 0, 1);

		// If input is 0, end process
		if (inputMinStep == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		// Get delta input
		System.out.print("Enter GSS delta (0 to cancel): ");
		inputDelta = Pro5_lawre273.getDouble("Enter GSS delta (0 to cancel): ", 0, Double.POSITIVE_INFINITY);

		// If input is 0, end process
		if (inputDelta == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}
		// Get Steepest Descent params
		super.getParamsUser();

		// Initialize variables
		this.maxStep = inputMaxStep;
		this.minStep = inputMinStep;
		this.delta = inputDelta;
		return true;
	}

	// Print GSS parameters
	public void print() {
		System.out.println("\nSD with a golden section line search:");
		super.print();
		System.out.println("GSS maximum step size: " + this.maxStep);
		System.out.println("GSS minimum step size: " + this.minStep);
		System.out.println("GSS delta: " + this.delta);
		System.out.println("");
	}

	// Calculates the GSS process finding the midpoint
	private double GSS(double a, double b, double c, double[] x, double[] dir, Polynomial P) {
		double y;
		double midpoint = 0;
		double d = b - a;
		double fa;
		double fb;
		double fc;

		// Calculates the minimum using fx method
		fa = this.fx(x, a, P);
		fb = this.fx(x, b, P);
		fc = this.fx(x, c, P);

		// if fc > fa or fc > fb, return left or right side
		if (fc > fa || fc > fb || fc > fa && fc > fb) {
			if (fa >= fb) {
				return b;
			} else if (fb > fa) {
				return a;
			}
		}

		while (d >= this.delta) {

			// If left side interval is bigger
			if ((c - a) > (b - c)) {

				// Calculate y and fy
				y = a + ((c - a) / this._PHI_);
				double fy = this.fx(x, y, P);

				// If point is in left interval, recall GSS function
				if (fy < fc) {
					midpoint = this.GSS(a, c, y, x, dir, P);
					return midpoint;

				}
				// If point is in right interval, recall GSS function
				else if (fy > fc) {
					midpoint = this.GSS(y, b, c, x, dir, P);
					return midpoint;
				}
			}

			// If right side interval is bigger
			else if ((b - c) > (c - a)) {

				// Calculate y and fy
				y = b - ((b - c) / this._PHI_);
				double fy = this.fx(x, y, P);

				// If point is in right interval, recall GSS function
				if (fy < fc) {
					midpoint = this.GSS(c, b, y, x, dir, P);
					return midpoint;

				}

				// If point is in left interval, recall GSS function
				else if (fy > fc) {
					midpoint = this.GSS(a, y, c, x, dir, P);
					return midpoint;
				}
			}

		}
		// If d < delta, calculate and return midpoint
		midpoint = (a + b) / 2;
		return midpoint;
	}

	// Calculate the minimum with respect to alpha
	private double fx(double[] x, double alpha, Polynomial P) {
		double[] f = new double[x.length];
		double fx;
		int counter;
		for (counter = 0; counter < x.length; counter++) {
			f[counter] = x[counter] - (alpha * (P.gradient(x))[counter]);
		}
		fx = P.f(f);
		return fx;
	}
}