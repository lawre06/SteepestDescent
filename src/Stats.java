import java.util.*;

public class Stats {

	// Calculate Average for a double array
	public static double averageDouble(double[] x) {
		int counter = 0;
		double average = 0;

		// Sum up average and divide by N
		for (counter = 0; counter < x.length; counter++) {
			average += x[counter];
		}
		average = average / (x.length);
		return average;

	}

	// Calculate Standard Deviation for a double array
	public static double stdevDouble(double[] x) {
		int counter = 0;
		double Average;
		double sum = 0;
		double Stdev = 0;
		Average = Stats.averageDouble(x);

		// Calculate St Dev using average and formula
		for (counter = 0; counter < x.length; counter++) {
			sum += Math.pow((x[counter] - Average), 2);
		}
		Stdev = Math.sqrt(sum / (x.length - 1));
		return Stdev;
	}

	// Calculate Minimum value for a double array
	public static double minDouble(double[] x) {
		int counter = 0;
		double min = 0;
		for (counter = 0; counter < x.length; counter++) {
			if (counter == 0) {
				min = x[counter];
			} else {
				if (x[counter] <= min) {
					min = x[counter];
				}
			}
		}
		return min;

	}

	// Calculate Maximum value for a double array
	public static double maxDouble(double[] x) {
		int counter = 0;
		double max = 0;
		for (counter = 0; counter < x.length; counter++) {
			if (counter == 0) {
				max = x[counter];
			} else {
				if (x[counter] >= max) {
					max = x[counter];
				}
			}
		}
		return max;
	}

	// Calculate Average for a integer array
	public static double averageInt(int[] x) {
		int counter = 0;
		double average = 0;

		// Sum up average and divide by N
		for (counter = 0; counter < x.length; counter++) {
			average += x[counter];
		}
		average = average / (x.length);
		return average;

	}

	// Calculate Standard Deviation for a integer array
	public static double stdevInt(int[] x) {
		int counter = 0;
		double newDouble = 0;
		double Average;
		double sum = 0;
		double Stdev = 0;
		Average = Stats.averageInt(x);

		// Calculate St Dev using average and formula
		for (counter = 0; counter < x.length; counter++) {
			newDouble = x[counter];
			sum += Math.pow(Math.abs(newDouble - Average), 2);
		}
		Stdev = Math.sqrt(sum / (x.length - 1));
		return Stdev;
	}

	// Calculate Minimum value for a integer array
	public static int minInt(int[] x) {
		int counter = 0;
		int min = 0;
		for (counter = 0; counter < x.length; counter++) {
			if (counter == 0) {
				min = x[counter];
			} else {
				if (x[counter] <= min) {
					min = x[counter];
				}
			}
		}
		return min;

	}

	// Calculate Maximum value for a integer array
	public static int maxInt(int[] x) {
		int counter = 0;
		int max = 0;
		for (counter = 0; counter < x.length; counter++) {
			if (counter == 0) {
				max = x[counter];
			} else {
				if (x[counter] >= max) {
					max = x[counter];
				}
			}
		}
		return max;

	}

	// Calculate Average for a long array
	public static double averageLong(long[] x) {
		int counter = 0;
		double average = 0;

		// Sum up average and divide by N
		for (counter = 0; counter < x.length; counter++) {
			average += x[counter];
		}
		average = average / (x.length);
		return average;

	}

	// Calculate Standard Deviation for a long array
	public static double stdevLong(long[] x) {
		int counter = 0;
		double newDouble = 0;
		double Average;
		double sum = 0;
		double Stdev = 0;
		Average = Stats.averageLong(x);

		// Calculate St Dev using average and formula
		for (counter = 0; counter < x.length; counter++) {
			newDouble = x[counter];
			sum += Math.pow(Math.abs(newDouble - Average), 2);
		}
		Stdev = Math.sqrt(sum / (x.length - 1));
		return Stdev;
	}

	// Calculate Minimum value for a long array
	public static long minLong(long[] x) {
		int counter = 0;
		long min = 0;
		for (counter = 0; counter < x.length; counter++) {
			if (counter == 0) {
				min = x[counter];
			} else {
				if (x[counter] <= min) {
					min = x[counter];
				}
			}
		}
		return min;

	}

	// Calculate Maximum value for a long array
	public static long maxLong(long[] x) {
		int counter = 0;
		long max = 0;
		for (counter = 0; counter < x.length; counter++) {
			if (counter == 0) {
				max = x[counter];
			} else {
				if (x[counter] >= max) {
					max = x[counter];
				}
			}
		}
		return max;
	}
}