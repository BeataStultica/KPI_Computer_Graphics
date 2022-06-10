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
        double dotProduct = 1;
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
            if (!isLight){
                double a = r.getDirection().toNormal().dot(normalAtPoint);
                if (Double.isNaN(a)){
                    a = 0;
                }
                dotProduct += a;

            }
        }

        if (Double.isNaN(dotProduct/5)) {
            System.out.println(dotProduct);
            dotProduct=1;
        }
        if (dotProduct/5<0.3){
            dotProduct=4;
        }
        return dotProduct/5;

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
