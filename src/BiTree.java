package src;

import java.util.ArrayList;
import java.util.function.Function;

public class BiTree implements BoundingTree {
	private final ArrayList<Triangle> triangles;
	private final ArrayList<Triangle> leftTs = new ArrayList<>();
	private final ArrayList<Triangle> rightTs = new ArrayList<>();

	private final Box3 boundingBox;
	private BiTree left;
	private BiTree right;
	private boolean hasChildren;

	public static BiTree create(ArrayList<Triangle> triangles, int treeDepth) {
		Vector[] bounds = getBounds(triangles);
		return new BiTree(triangles, new Box3(bounds[0], bounds[1]), 0, treeDepth);
	}

	private static Vector[] getBounds(ArrayList<Triangle> triangles) {
		Vector[] res = new Vector[2];
		double minX = Double.MAX_VALUE;
		double maxX = -Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxY = -Double.MAX_VALUE;
		double minZ = Double.MAX_VALUE;
		double maxZ = -Double.MAX_VALUE;

		for (Triangle tr : triangles) {
			Vector min = tr.minBounds();
			Vector max = tr.maxBounds();

			if (min.x() < minX)
				minX = min.x();
			if (min.y() < minY)
				minY = min.y();
			if (min.z() < minZ)
				minZ = min.z();

			if (max.x() > maxX)
				maxX = max.x();
			if (max.y() > maxY)
				maxY = max.y();
			if (max.z() > maxZ)
				maxZ = max.z();
		}

		res[0] = new Vector(minX, minY, minZ);
		res[1] = new Vector(maxX, maxY, maxZ);

		return res;
	}

	private void divideTriangles(double middle, Function<Vector, Double> axis) {
		for (Triangle tr : triangles) {
			Point c = tr.getCenter();
			Vector center = new Vector(c.x(), c.y(), c.z());
			Vector min = tr.minBounds();
			Vector max = tr.maxBounds();

			if (axis.apply(min) <= middle && axis.apply(max) >= middle) {
				leftTs.add(tr);
				rightTs.add(tr);
			} else if (axis.apply(center) < middle) {
				leftTs.add(tr);
			} else {
				rightTs.add(tr);
			}
		}
	}

	private BiTree(ArrayList<Triangle> triangles, Box3 boundingBox, int depth, int treeDepth) {
		this.triangles = triangles;
		this.boundingBox = boundingBox;
		Vector[] bounds = getBounds(triangles);
		Vector minBox = bounds[0];
		Vector maxBox = bounds[1];
		Vector diff = maxBox.sub(minBox);
		double maxDiff = Math.max(diff.x(), Math.max(diff.y(), diff.z()));
		Box3 leftBox;
		Box3 rightBox;

		if (maxDiff == diff.x()) {
			double middle = minBox.x() + diff.x() / 2;
			divideTriangles(middle, Vector::x);
			leftBox = new Box3(minBox, new Vector(middle, maxBox.y(), maxBox.z()));
			rightBox = new Box3(new Vector(middle, minBox.y(), minBox.z()), maxBox);
		} else if (maxDiff == diff.y()) {
			double middle = minBox.y() + diff.y() / 2;
			divideTriangles(middle, Vector::y);
			leftBox = new Box3(minBox, new Vector(maxBox.x(), middle, maxBox.z()));
			rightBox = new Box3(new Vector(minBox.x(), middle, minBox.z()), maxBox);
		} else {
			double middle = minBox.z() + diff.z() / 2;
			divideTriangles(middle, Vector::z);
			leftBox = new Box3(minBox, new Vector(maxBox.x(), maxBox.y(), middle));
			rightBox = new Box3(new Vector(minBox.x(), minBox.y(), middle), maxBox);
		}

		if (triangles.size() == leftTs.size() || triangles.size() == rightTs.size() || depth > treeDepth) {
			return;
		}
		hasChildren = true;
		left = new BiTree(leftTs, leftBox, depth + 1, treeDepth);
		right = new BiTree(rightTs, rightBox, depth + 1, treeDepth);
	}

	public ArrayList<Triangle> getTriangles(Ray ray) {
		boolean leftIntersection = hasChildren && left.boundingBox.intersectionWith(ray);
		boolean rightIntersection = hasChildren && right.boundingBox.intersectionWith(ray);

		if (leftIntersection && rightIntersection) {
			ArrayList<Triangle> res = new ArrayList<>();
			res.addAll(right.getTriangles(ray));
			res.addAll(left.getTriangles(ray));
			return res;
		} else if (leftIntersection) {
			return left.getTriangles(ray);
		} else if (rightIntersection) {
			return right.getTriangles(ray);
		} else if (!hasChildren) {
			return triangles;
		} else {
			return new ArrayList<>();
		}
	}
}
