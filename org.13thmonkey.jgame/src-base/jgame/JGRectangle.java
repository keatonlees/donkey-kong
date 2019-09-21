package jgame;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Mr. Hapke
 *
 */
public class JGRectangle {
	public JGRectangle() {
		this(0, 0, 0, 0);
	}

	public JGRectangle(int x, int y, int width, int height) {
		setAll(x, y, width, height);
	}

	public void setAll(int x, int y, int width, int height) {
		datas.clear();
		RectangleData data = new RectangleData(x, y, width, height);
		datas.add(data);
		bounds.copyFrom(data);
	}

	/**
	 * Copy Constructor
	 */
	public JGRectangle(JGRectangle r) {
		copyFrom(r);
		updateBoundingBox();
	}

	public JGRectangle(RectangleData[] inputs) {

		for (RectangleData otherData : inputs)
			datas.add(new RectangleData(otherData));
		updateBoundingBox();
	}

	private void updateBoundingBox() {
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;

		int right = Integer.MIN_VALUE;
		int bottom = Integer.MIN_VALUE;
		// boolean firstPass = true;

		for (RectangleData d : datas) {
			x = Math.min(d.x, x);
			y = Math.min(d.y, y);

			int dRight = d.x + d.width;
			int dHeight = d.y + d.height;
			if (dRight > right) {
				right = dRight;
			}
			if (dHeight > dHeight) {
				bottom = dHeight;
			}
		}

		bounds.x = x;
		bounds.y = y;
		bounds.width = right - x;
		bounds.height = bottom - y;
	}

	public void copyFrom(JGRectangle r) {
		copyFrom(r, 0, 0);
	}

	public void copyFrom(JGRectangle r, int x2, int y2) {
		datas.clear();
		for (RectangleData otherData : r.datas)
			datas.add(new RectangleData(otherData, x2, y2));
	}

	private List<RectangleData> datas = new ArrayList<>();
	private final RectangleData bounds = new RectangleData();

	public int getX() {
		return datas.get(0).x;
	}

	public void setX(int x) {
		this.datas.get(0).x = x;

		if (datas.size() > 1)
			debugMultiSet("X", x);
	}

	public int getY() {
		return datas.get(0).y;
	}

	public void setY(int y) {
		this.datas.get(0).y = y;
		if (datas.size() > 1)
			debugMultiSet("Y", y);
	}

	public int getWidth() {
		return datas.get(0).width;
	}

	public void setWidth(int width) {
		this.datas.get(0).width = width;
		if (datas.size() > 1)
			debugMultiSet("W", width);
	}

	public int getHeight() {
		return datas.get(0).height;
	}

	public void setHeight(int height) {
		this.datas.get(0).height = height;
		if (datas.size() > 1)
			debugMultiSet("H", height);

	}

	public List<RectangleData> getDatas() {
		return datas;
	}

	public RectangleData getIntersecting(JGRectangle other) {
		return datas.stream().flatMap(r -> other.datas.stream().filter((RectangleData r2) -> r.intersects(r2)))
				.findFirst().orElse(null);
	}

	public boolean intersects(JGRectangle other) {
		// for (RectangleData data : datas)
		// for (RectangleData otherData : other.datas)
		// if (data.intersects(otherData))
		// return true;
		// return false;

		return getIntersecting(other) != null;
	}

	public void debugMultiSet(String var, int value) {
		System.err.println("TELL MR. HAPKE RIGHT NOW! SET-" + var + " ON MULTI: " + value);

		StackTraceElement[] stackTraceElements = Thread.getAllStackTraces().get(Thread.currentThread());
		for (StackTraceElement e : stackTraceElements)
			System.out.println(e + "Line: " + e.getLineNumber());
	}
}
