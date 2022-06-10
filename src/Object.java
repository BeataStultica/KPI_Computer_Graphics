package src;

public interface Object {
	Double intersectionWith(Ray ray); // Returns the t value in ray equation (dt + o = p)
	Normal getNormalAtPoint(Point p);
	Vector getColor();
	void setColor(Vector c);
}
