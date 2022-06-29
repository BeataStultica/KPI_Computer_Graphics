package src;

public interface Material {
    float ismirror();
    Vector bxdf_func(Vector wo, Vector wi, Object obj, Point p);
    Ray reflected_ray(Vector dir, Normal normalAtPoint, Point intersectionPoint);
}
