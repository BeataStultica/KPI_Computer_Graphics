package src;


public class Triangle implements Object {
    private Point v1;
    private Point v2;
    private Point v3;
    private Vector n1;
    private Vector n2;
    private Vector n3;
    private double u;
    private double v;
    private final double eps = 0.00001;

    public Triangle(Point v1, Point v2, Point v3, Vector n1, Vector n2, Vector n3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }
    public void transform(Matrix4x4 m){
        this.v1 = m.multiply_point(v1);
        this.v2 = m.multiply_point(v2);
        this.v3 = m.multiply_point(v3);
        this.n1 = m.multiply_vector(n1);
        this.n2 = m.multiply_vector(n2);
        this.n3 = m.multiply_vector(n3);
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
        this.u = u;

        if (u<0.0 || u>det){
            return null;
        }

        Vector qvec = tvec.cross(edge1);
        double v = d.dot(qvec);
        this.v = v;
        if (v<0.0 || u+v>det){
            return null;
        }

        double t = edge2.dot(qvec);
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
        //return this.n2.mult(u).add(this.n3.mult(v).add(this.n1.mult(1-v-u))).toNormal();  // barycentric normal
        Vector edge1 = v2.sub(p);
        Vector edge2 = v3.sub(p);
        return edge1.cross(edge2).toNormal();
    }
}

