package src;

import java.util.ArrayList;

public class Scene {
	private final ArrayList<Object> objects = new ArrayList<Object>();
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

	private char calcLighting(Normal normalAtPoint) {
		double dotProduct = light.getDirection().dot(normalAtPoint);

		if (dotProduct < 0) {
			return ' ';
		} else if (dotProduct < 0.2) {
			return '.';
		} else if (dotProduct < 0.5) {
			return '*';
		} else if (dotProduct < 0.8) {
			return '0';
		} else {
			return '#';
		}
	}

	public void render() {
		Point origin = camera.getLocation();

		for (int x = 0; x < screen.getWidth(); x++) {
			for (int y = 0; y < screen.getHeight(); y++) {
				Point dest = screen.getPoint(x, y);
				Vector direction = dest.sub(origin);
				Ray ray = new Ray(direction, origin);

				for (Object object : objects) { // Make this work for multiple objects
					Double tVal = object.intersectionWith(ray); // The smallest t is closest to camera

					if (tVal != null) {
						Point intersectionPoint = ray.getPointAt(tVal);
						Normal normalAtPoint = object.getNormalAtPoint(intersectionPoint);
						System.out.print(calcLighting(normalAtPoint));
						break;
					} else {
						System.out.print("-");
					}
				}

				System.out.print(" ");
			}
			System.out.print("\n");
		}
	}
}
