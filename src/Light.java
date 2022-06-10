package src;

public interface Light {
    double calcLighting(Normal normalAtPoint, Point point);
}
