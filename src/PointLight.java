package src;

public class PointLight implements Light{
    private final Point position;
    private final Vector color;
    private final Vector intens;
    public PointLight(Point position, Vector color, float[] intens) {
        this.color = color;
        this.position = position;
        this.intens = new Vector(intens[0], intens[1], intens[2]);
    }

    public Normal getDirection(Point p) {
        return position.sub(p).toNormal();
    }
    public Vector getColor() { return color; }
    public Vector getIntens() {return intens; }

    @Override
    public double calcLighting(Normal normalAtPoint, Point point) {
        double dotProduct = this.getDirection(point).dot(normalAtPoint);

        if (dotProduct < 0) {
            return 0;
        } else {
            return dotProduct;
        }
    }
}
