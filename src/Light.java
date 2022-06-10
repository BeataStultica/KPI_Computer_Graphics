package src;

public interface Light {
    double calcLighting(Normal normalAtPoint, Point point, BoundingTree tree);
    Normal getDirection(Point point);
    Vector getColor();
    Vector getIntens();
}
