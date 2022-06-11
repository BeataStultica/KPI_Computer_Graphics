package src;

public class Point {
	private final double x;
	private final double y;
	private final double z;

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public double z() {
		return z;
	}

	public Point add(Vector v) {
		return new Point(x + v.x(), y + v.y(), z + v.z());
	}

	public Vector sub(Point p) {
		return new Vector(x - p.x, y - p.y, z - p.z);
	}
	public Point mult(double v) { return new Point(x*v, y *v, z*v);}

	public Vector toVector() { return new Vector(x, y, z); }
}
