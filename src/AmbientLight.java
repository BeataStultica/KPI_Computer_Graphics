package src;

import java.util.Random;

public class AmbientLight implements Light{
    private final Vector color;
    private final Vector intens;
    public AmbientLight(Vector color, float[] intens) {
        this.color = color;
        this.intens = new Vector(intens[0], intens[1], intens[2]);
    }
    @Override
    public double calcLighting(Normal normalAtPoint, Point point, BoundingTree tree) {
        Random rd = new Random();
        double dotProduct = 0;
        for (int i=0; i<4; i++){
            Ray r = new Ray(Normal.create(normalAtPoint.x()*rd.nextInt(10), normalAtPoint.y()*rd.nextInt(10), normalAtPoint.z()*rd.nextInt(10)), point.add(normalAtPoint.mult(2)));
            boolean isLight = false;
            for (Object object : tree.getTriangles(r)) {
                Double intersection = object.intersectionWith(r);

                if (intersection != null) {
                    isLight =true;
                    break;
                }
            }
            dotProduct += r.getDirection().toNormal().dot(normalAtPoint);
        }
        //double dotProduct = this.getDirection(point).dot(normalAtPoint);


        return dotProduct/4;

    }

    @Override
    public Normal getDirection(Point point) {
        return Normal.create(0,0,0);
    }

    @Override
    public Vector getColor() {
        return color;
    }

    @Override
    public Vector getIntens() {
        return intens;
    }
}
