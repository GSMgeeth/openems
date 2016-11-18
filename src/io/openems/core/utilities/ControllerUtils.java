package io.openems.core.utilities;

import java.util.Collections;
import java.util.List;

public class ControllerUtils {

	public static double calculateCosPhi(long activePower, long reactivePower) {
		return activePower / calculateApparentPower(activePower, reactivePower);
	}

	public static long calculateReactivePower(long activePower, double cosPhi) {
		return (long) (activePower * Math.sqrt(1 / Math.pow(cosPhi, 2) - 1));
	}

	public static long calculateApparentPower(long activePower, long reactivePower) {
		return (long) Math.sqrt(Math.pow(activePower, 2) + Math.pow(reactivePower, 2));
	}

	public static long calculateActivePower(long apparentPower, double cosPhi) {
		return (long) (apparentPower * cosPhi);
	}

	public static long calculateApparentPower(long activePower, double cosPhi) {
		return (long) (activePower / cosPhi);
	}

	public static boolean isCharge(long activePower, long reactivePower) {
		if (activePower >= 0 && reactivePower >= 0) {
			return false;
		} else if (activePower < 0 && reactivePower >= 0) {
			return true;
		} else if (activePower < 0 && reactivePower < 0) {
			return true;
		} else {
			return false;
		}
	}

	public static Long getValueOfLine(List<Point> points, long x) {
		Point smaller = getSmallerPoint(points, x);
		Point greater = getGreaterPoint(points, x);
		if (smaller != null && greater != null) {
			double m = (greater.y - smaller.y) / (greater.x - smaller.x);
			double t = smaller.y - m * smaller.x;
			return (long) (m * x + t);
		} else {
			throw new ArithmeticException("x is out of Range of the points");
		}
	}

	private static Point getGreaterPoint(List<Point> points, long x) {
		Collections.sort(points, (p1, p2) -> (int) (p1.x - p2.x));
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			if (p.x > x) {
				return p;
			}
		}
		return null;
	}

	private static Point getSmallerPoint(List<Point> points, long x) {
		Collections.sort(points, (p1, p2) -> (int) (p2.x - p1.x));
		for (int i = points.size() - 1; i >= 0; i--) {
			Point p = points.get(i);
			if (p.x < x) {
				return p;
			}
		}
		return null;
	}

	class Point {
		final long x;
		final long y;

		public Point(long x, long y) {
			this.x = x;
			this.y = y;
		}

	}
}
