package src;

public interface Object {
	Double intersectionWith(Ray ray); // Returns the t value in ray equation (dt + o = p)
	Normal getNormalAtPoint(Point p);
	Vector getColor();
	Material getMaterial();
	void setColor(Vector c);
	void setMaterial(Material material);
	Double[] get_uv_bari(Point p);

}
