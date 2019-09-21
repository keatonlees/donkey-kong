package jgame;

/**
 * 
 * @author Mr. Hapke
 *
 */
public class RectangleData {

	public int x, y, width, height;

	public RectangleData() {
		this(0, 0, 0, 0);
	}

	public RectangleData(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Copy Constructor
	 */
	public RectangleData(RectangleData other) {
		copyFrom(other, 0, 0);
	}

	/**
	 * Copy Constructor (with offset)
	 */
	public RectangleData(RectangleData other, int x, int y) {
		copyFrom(other, x, y);
	}

	public void copyFrom(RectangleData other) {
		copyFrom(other, 0, 0);
	}

	public void copyFrom(RectangleData other, int x2, int y2) {
		this.x = other.x + x2;
		this.y = other.y + y2;
		this.width = other.width;
		this.height = other.height;
	}

	public boolean intersects(RectangleData other) {
		return other.x < x + width && other.x + other.width > x && other.y < y + height && other.y + other.height > y;
	}

	@Override
	public String toString() {
		return "RectangleData [(" + x + ", " + y + ") + (" + width + "," + height + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RectangleData other = (RectangleData) obj;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
