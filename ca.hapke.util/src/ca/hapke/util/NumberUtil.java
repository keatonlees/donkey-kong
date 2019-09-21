/**
 * 
 */
package ca.hapke.util;

import java.awt.geom.Point2D;

/**
 * @author Mr. Hapke
 */
public abstract class NumberUtil {
	public static double limit(double val, double min, double max) {
		return Math.min(Math.max(val, min), max);
	}

	public static boolean within(double val, double target, double delta) {
		delta = Math.abs(delta);
		double mostExtreme = target + delta;
		double leastExtreme = target - delta;

		return val <= mostExtreme && val >= leastExtreme;
	}

	public static double relax(double val, double target, double adjust) {
		if (within(val, target, adjust))
			return target;
		return val + (val > 0 ? -1 : 1) * adjust;
	}

	public static double getPercentage(boolean useX, double xBase, double yBase, double xRaw, double yRaw) {
		double dx = xRaw - xBase;
		double dy = yRaw - yBase;
		double c = distance(dx, dy);

		return (useX ? dx : dy) / c;
	}

	public static double distance(double dx, double dy) {
		return Math.hypot(dx, dy);
	}

	public static Point2D.Double getPercentages(double xBase, double yBase, double xRaw, double yRaw) {
		double dx = xRaw - xBase;
		double dy = yRaw - yBase;
		double c = distance(dx, dy);

		return new Point2D.Double(dx / c, dy / c);
	}
}
