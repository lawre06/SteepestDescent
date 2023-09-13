public class Polynomial {
	private int n;
	private int degree;
	private double[][] coefs;

	// Constructors to initialize variables
	public Polynomial(int n, int degree, double[][] coefs) {

	}

	public Polynomial() {
		this.n = 0;
		this.degree = 0;
		this.coefs = new double[0][0];
	}

	// Setters to assign values to private variables
	public void setN(int a) {
		this.n = a;
	}

	public void setDegree(int a) {
		this.degree = a;
	}

	public void setCoefs(int a, int d, double j) {
		this.coefs[a][d] = j;
	}

	// Getters to return private variables if called
	public int getN() {
		return n;
	}

	public int getDegree() {
		return degree;
	}

	public double[][] getCoefs() {
		return coefs;
	}

	// Check if polynomial has been set
	public boolean isSet() {
		if (n == 0 | degree == 0 | n == 0 && degree == 0) {
			n = 0;
			degree = 0;
			System.out.println("ERROR: No polynomials have been loaded!\n");
			return false;
		}
		return true;
	}

	// Initializes the coefficient array
	public void init() {
		this.coefs = new double[n][degree + 1];
	}

	// Prints the polynomial function
	public void print() {

		// Initialize booleans
		int counter;
		int counter1;
		// if Polynomial not set, return error

		System.out.print("f(x) = ");

		// For loop to print polynomial function per variable
		for (counter = 0; counter < this.getN(); counter++) {
			System.out.print("( ");
			for (counter1 = 0; counter1 < this.getDegree() + 1; counter1++) {
				if ((this.degree - counter1) == 0) {
					System.out.print(String.format("%.2f", this.coefs[counter][counter1]) + "");
				} else {
					System.out.print(String.format("%.2f", this.coefs[counter][counter1]) + "x" + (counter + 1) + "^"
							+ (this.degree - counter1) + " + ");
				}
			}
			if (counter == this.n - 1) {
				System.out.println(" )");
			} else {
				System.out.print(" ) + ");
			}
		}
	}

	// Calculates the function at point x
	public double f(double[] x) {

		// Initialize variables
		double total_function = 0;
		double function_value = 0;
		int counter;
		int counter1;

		// Calculate function for each variable
		for (counter = 0; counter < this.getN(); counter++) {
			int degree = this.degree;
			for (counter1 = 0; counter1 < this.degree + 1; counter1++) {

				if (degree == 0) {
					function_value = this.coefs[counter][counter1];
				} else {
					function_value = this.coefs[counter][counter1] * Math.pow(x[counter], degree);
				}
				degree -= 1;
				total_function += function_value;
			}
		}
		return total_function;
	}

	// Calculates the gradient at a point x
	public double[] gradient(double[] x) {
		int counter;
		int counter1;
		double derivative;
		double gradient[] = new double[this.getN()];

		// Calculate function per variable
		for (counter = 0; counter < this.getN(); counter++) {
			for (counter1 = 0; counter1 < this.getDegree() + 1; counter1++) {
				if (counter1 == this.degree) {
					derivative = 0;
				} else {
					derivative = (this.coefs[counter][counter1] * (this.degree - counter1))
							* Math.pow(x[counter], (this.degree - 1 - counter1));
				}
				gradient[counter] += derivative;
			}
		}

		return gradient;
	}

	// Calculates the gradientNorm of a polynomial function
	public double gradientNorm(double x[]) {

		// Initialize variables
		double gradientNorm = 0;
		double gradientTotal = 0;
		double gradient[] = this.gradient(x);
		int counter;

		// Calculates gradient^2 for each variable
		for (counter = 0; counter < this.getN(); counter++) {
			gradientTotal += (Math.pow(gradient[counter], 2));
		}

		gradientNorm = Math.sqrt(gradientTotal);
		return gradientNorm;

	}
}
