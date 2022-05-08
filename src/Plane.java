package src;

public class Plane implements Object {
	private final Point center;
	private final Normal normal;

	public Plane(Point center, Normal normal) {
		this.center = center;
		this.normal = normal;
	}

	public Double intersectionWith(Ray ray) {
		double denom = normal.dot(ray.getDirection());
		if (denom > 1e-6) {
			Vector p0l0 = center.sub(ray.getOrigin());
			double t = p0l0.dot(normal) / denom;

			if (t >= 0) {
				return t;
			} else {
				return null;
			}
		}

		return null;
	}

	@Override
	public Normal getNormalAtPoint(Point p) {
		return normal;
	}
}
