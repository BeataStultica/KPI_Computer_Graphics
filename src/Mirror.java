package src;

public class Mirror implements Material{

    public Mirror(){

    }
    @Override
    public Vector bxdf_func(Vector wo, Vector wi, Object obj, Point p) {
        return new Vector(1,1,1);//null;
    }
    public float ismirror(){
        return 1f;
    }
    @Override
    public Ray reflected_ray(Vector dir, Normal normalAtPoint, Point intersectionPoint) {
        Ray reflect_ray = new Ray(dir.sub(normalAtPoint.mult(dir.dot(normalAtPoint) * 2)).toNormal(), intersectionPoint);
        return reflect_ray;
    }
}
