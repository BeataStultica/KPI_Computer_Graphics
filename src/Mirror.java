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
}
