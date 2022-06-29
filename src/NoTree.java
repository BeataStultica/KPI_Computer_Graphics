package src;

import java.util.List;

public class NoTree implements BoundingTree {
	private final List<Triangle> triangles;

	public NoTree(List<Triangle> triangles) {
		this.triangles = triangles;
	}

	@Override
	public List<Triangle> getTriangles(Ray ray) {
		return triangles;
	}
}
