package src;

import java.awt.Color;
import java.util.List;

public class Scene {
	public static final int BACKGROUND_COLOR = Color.magenta.getRGB();
	private final Camera camera;
	private final Screen screen;
	private final Light light;
	private final BoundingTree tree;

	public Scene(Camera camera, Screen screen, Light light, BoundingTree tree) {
		this.camera = camera;
		this.screen = screen;
		this.light = light;
		this.tree = tree;
	}



	private boolean lightObstructed(Ray ray, Object self) {
		for (Object object : tree.getTriangles(ray)) {
			if (object == self) continue;
			Double intersection = object.intersectionWith(ray);

			if (intersection != null) {
				return true;
			}
		}

		return false;
	}

	public int[][] render(Output output, boolean withShadows) {
		Point origin = camera.getLocation();
		int[][] matrix = new int[screen.getWidth()][screen.getHeight()];

		for (int x = 0; x < screen.getWidth(); x++) {
			for (int y = 0; y < screen.getHeight(); y++) {
				Point dest = screen.getPoint(x, y);
				Vector direction = dest.sub(origin);
				Ray ray = new Ray(direction.toNormal(), origin);

				double tVal = Double.MAX_VALUE;
				Object obj = null;

				List<Triangle> triangles = tree.getTriangles(ray);
				//System.out.println(x * screen.getWidth() + y + " out of " + screen.getWidth() * screen.getHeight());
				//System.out.println("triangles size: " + triangles.size());
				for (Object object : triangles) {
					Double ttval = object.intersectionWith(ray);

					if (ttval != null && ttval < tVal) {
						tVal = ttval;
						obj = object;
					}
				}

				if (obj != null) {
					Point intersectionPoint = ray.getPointAt(tVal);
					Normal normalAtPoint = obj.getNormalAtPoint(intersectionPoint);

					if (withShadows && lightObstructed(new Ray(light.getDirection(intersectionPoint), intersectionPoint.add(normalAtPoint.mult(2))), obj)) {
						matrix[x][y] = Color.BLACK.getRGB();
					} else {
						double lighting = light.calcLighting(normalAtPoint, intersectionPoint, tree);
						Vector c = obj.getMaterial().bxdf_func(light.getDirection(intersectionPoint), ray.getDirection(),
								obj, intersectionPoint).dot_el(light.getColor().dot_el(light.getIntens())).mult(lighting); // replace obj.getColor with texture function
						//int shade = (int) Math.round(lighting * 255);
						matrix[x][y] = new Color(roundColor(c.x()), roundColor(c.y()), roundColor(c.z())).getRGB();
					}
				} else {
					matrix[x][y] = BACKGROUND_COLOR;
				}
			}
		}

		output.display(matrix);
		return matrix;
	}
	private int roundColor(double x){
		if (x>255){
			return 255;
		}else{
			return (int) x;
		}
	}
}
