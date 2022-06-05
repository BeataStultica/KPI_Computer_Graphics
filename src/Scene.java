package src;

import java.util.ArrayList;
import java.awt.Color;

public class Scene {
	public static final int BACKGROUND_COLOR = Color.magenta.getRGB();
	private final ArrayList<Object> objects = new ArrayList<>();
	private final Camera camera;
	private final Screen screen;
	private final DirectedLight light;

	public Scene(Camera camera, Screen screen, DirectedLight light) {
		this.camera = camera;
		this.screen = screen;
		this.light = light;
	}

	public void addObject(Object obj) {
		objects.add(obj);
	}

	private double calcLighting(Normal normalAtPoint) {
		double dotProduct = light.getDirection().dot(normalAtPoint);

		if (dotProduct < 0) {
			return 0;
		} else {
			return dotProduct;
		}
	}

	private boolean lightObstructed(Ray ray, Object self) {
		for (Object object : objects) {
			if (object == self) continue;
			Double intersection = object.intersectionWith(ray);

			if (intersection != null) {
				return true;
			}
		}

		return false;
	}

	public void render(Output output, boolean withShadows) {
		Point origin = camera.getLocation();
		int[][] matrix = new int[screen.getWidth()][screen.getHeight()];

		for (int x = 0; x < screen.getWidth(); x++) {
			for (int y = 0; y < screen.getHeight(); y++) {
				Point dest = screen.getPoint(x, y);
				Vector direction = dest.sub(origin);
				Ray ray = new Ray(direction.toNormal(), origin);

				double tVal = Double.MAX_VALUE;
				Object obj = null;

				for (Object object : objects) {
					Double ttval = object.intersectionWith(ray);

					if (ttval != null && ttval < tVal) {
						tVal = ttval;
						obj = object;
					}
				}

				if (obj != null) {
					Point intersectionPoint = ray.getPointAt(tVal);
					Normal normalAtPoint = obj.getNormalAtPoint(intersectionPoint);

					if (withShadows && lightObstructed(new Ray(light.getDirection(), intersectionPoint.add(normalAtPoint.mult(2))), obj)) {
						matrix[x][y] = Color.BLACK.getRGB();
					} else {
						double lighting = calcLighting(normalAtPoint);
						int shade = (int) Math.round(lighting * 255);
						matrix[x][y] = new Color(shade, shade, shade).getRGB();
					}
				} else {
					matrix[x][y] = BACKGROUND_COLOR;
				}
			}
		}

		output.display(matrix);
	}
}
