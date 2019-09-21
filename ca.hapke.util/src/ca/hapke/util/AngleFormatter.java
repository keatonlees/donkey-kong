package ca.hapke.util;

import java.text.NumberFormat;

/**
 * 
 * @author Mr. Hapke
 *
 */
public class AngleFormatter {

	private final double zeroThreshold;
	private NumberFormat nf;

	public AngleFormatter() {
		this(3, 3, 1, 1, 1);
	}

	public AngleFormatter(int decimalPlaces) {
		this(1, 3, decimalPlaces, decimalPlaces, 1);
	}
	public AngleFormatter(int intPlaces, int decimalPlaces, double zeroThreshold) {
		this(intPlaces,intPlaces, decimalPlaces, decimalPlaces, zeroThreshold);
	}

	public AngleFormatter(int minInt, int maxInt, int minDec, int maxDec, double zeroThreshold) {
		nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(minDec);
		nf.setMinimumFractionDigits(maxDec);
		nf.setMinimumIntegerDigits(minInt);
		nf.setMaximumIntegerDigits(maxInt);
		
		this.zeroThreshold = zeroThreshold;
	}

	public String format(double d) {
		String val = nf.format(d);
		if (NumberUtil.within(d, 0, zeroThreshold)) {
			if (d < 0)
				val = val.substring(1);
			val = "=" + val;
		} else if (d > 0)
			val = "+" + val;

		return val;
	}
}
