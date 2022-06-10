package src;

public interface Material {
    Vector bxdf_func(Vector wo, Vector wi, Object obj, Point p);
}
