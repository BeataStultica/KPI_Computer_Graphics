package src;

import java.util.List;

public interface BoundingTree {
	List<Triangle> getTriangles(Ray ray);
}
