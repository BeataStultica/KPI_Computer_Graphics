package src;

public class Triangle implements Object {
    private final Point v1;
    private final Point v2;
    private final Point v3;
    private final double eps = 0.00001;

    public Triangle(Point v1, Point v2, Point v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Double intersectionWith(Ray ray) {
        Point o = ray.getOrigin();
        Vector d = ray.getDirection();

        Vector edge1 = v2.sub(v1);
        Vector edge2 = v3.sub(v1);

        Vector pvec = d.cross(edge2);

        double det = edge1.dot(pvec);

        if (det< eps){
            return null;
        }

        Vector tvec = o.sub(v1);

        double u = tvec.dot(pvec);

        if (u<0.0 || u>det){
            return null;
        }

        Vector qvec = tvec.cross(edge1);
        double v = d.dot(qvec);
        if (v<0.0 || u+v>det){
            return null;
        }
        double t = edge1.dot(edge2);

        double inv_det = 1.0/det;
        t *= inv_det;
        //u *= inv_det;
        //v *= inv_det;


        if (t >= 0) {
            return t;
        } else {
            return null;
        }
    }

    @Override
    public Normal getNormalAtPoint(Point p) {
        Vector edge1 = v2.sub(v1);
        Vector edge2 = v3.sub(v1);
        return edge1.cross(edge2).toNormal();
    }
}

