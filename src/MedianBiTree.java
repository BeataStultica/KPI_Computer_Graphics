package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class MedianBiTree implements BoundingTree {
    private final ArrayList<Triangle> triangles;
    private final Box3 boundingBox;
    private MedianBiTree left;
    private MedianBiTree right;
    private boolean hasChildren;

    public static MedianBiTree create(ArrayList<Triangle> triangles, int treeDepth) {
        Vector[] bounds = getBounds(triangles);
        return new MedianBiTree(triangles, new Box3(bounds[0], bounds[1]), 0, treeDepth);
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

    private void divideTriangles(double middle, Function<Vector, Double> axis, ArrayList<Triangle> leftTs, ArrayList<Triangle> rightTs) {
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

    private double medianPoint(Function<Vector, Double> axis) {
        double[] centers = new double[triangles.size()];
        for (int i = 0; i < triangles.size(); i++) {
            centers[i] = axis.apply(triangles.get(i).getCenter().toVector());
        }
        Arrays.sort(centers);

        double median;
        if (centers.length % 2 == 0) {
            median = (centers[centers.length / 2] + centers[centers.length / 2 - 1]) / 2;
        } else {
            median = centers[centers.length / 2];
        }

        return median;
    }

    private double findDivisionPoint(Vector minBox, Vector maxBox, Function<Vector, Double> axis) {
        return medianPoint(axis);
    }

    private MedianBiTree(ArrayList<Triangle> triangles, Box3 boundingBox, int depth, int treeDepth) {
        this.triangles = triangles;
        this.boundingBox = boundingBox;
        Vector[] bounds = getBounds(triangles);
        Vector minBox = bounds[0];
        Vector maxBox = bounds[1];
        Vector diff = maxBox.sub(minBox);
        double maxDiff = Math.max(diff.x(), Math.max(diff.y(), diff.z()));
        Box3 leftBox;
        Box3 rightBox;
        ArrayList<Triangle> leftTs = new ArrayList<>();
        ArrayList<Triangle> rightTs = new ArrayList<>();

        if (maxDiff == diff.x()) {
            double middle = findDivisionPoint(minBox, maxBox, Vector::x);
            divideTriangles(middle, Vector::x, leftTs, rightTs);
            leftBox = new Box3(minBox, new Vector(middle, maxBox.y(), maxBox.z()));
            rightBox = new Box3(new Vector(middle, minBox.y(), minBox.z()), maxBox);
        } else if (maxDiff == diff.y()) {
            double middle = findDivisionPoint(minBox, maxBox, Vector::y);
            divideTriangles(middle, Vector::y, leftTs, rightTs);
            leftBox = new Box3(minBox, new Vector(maxBox.x(), middle, maxBox.z()));
            rightBox = new Box3(new Vector(minBox.x(), middle, minBox.z()), maxBox);
        } else {
            double middle = findDivisionPoint(minBox, maxBox, Vector::z);
            divideTriangles(middle, Vector::z, leftTs, rightTs);
            leftBox = new Box3(minBox, new Vector(maxBox.x(), maxBox.y(), middle));
            rightBox = new Box3(new Vector(minBox.x(), minBox.y(), middle), maxBox);
        }

        if (leftTs.size() >= triangles.size() || rightTs.size() >= triangles.size() || depth > treeDepth) {
            return;
        }
        hasChildren = true;
        left = new MedianBiTree(leftTs, leftBox, depth + 1, treeDepth);
        right = new MedianBiTree(rightTs, rightBox, depth + 1, treeDepth);
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

