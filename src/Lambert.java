package src;

import java.awt.*;

public class Lambert implements Material{
    private Vector color;
    private int[][] texture;
    public Lambert(Vector color, int[][] texture){
        this.color = color;
        this.texture = texture;
    }
    @Override
    public Vector bxdf_func(Vector wo, Vector wi, Object obj, Point p) {
        if (texture == null) {
            return color.mult(1/(2*3.14));//obj.getColor();
        }else{
            Double[] xy = obj.get_uv_bari(p);
            Color color = new Color(texture[(int) (xy[0]*texture.length)][(int) (xy[1]*texture.length)]);
            return new Vector(color.getRed(),color.getGreen(), color.getBlue());
        }
    }

    @Override
    public float ismirror() {
        return 0;
    }
}
