package src;

public class DirectedLight implements Light{
	private final Normal direction;
	private final Vector color;
	private final Vector intens;
	public DirectedLight(Normal direction, Vector color, float[] intens) {
		this.color = color;
		this.direction = direction.reverse().toNormal();
		this.intens = new Vector(intens[0], intens[1], intens[2]);
	}

	public Normal getDirection(Point point) {
		return direction;
	}
	public Vector getColor() { return color; }
	public Vector getIntens() {return intens; }

	@Override
	public double calcLighting(Normal normalAtPoint, Point point, BoundingTree tree) {
		double dotProduct = this.getDirection(point).dot(normalAtPoint);

		if (dotProduct < 0) {
			return 0;
		} else {
			return dotProduct;
		}
	}
}
