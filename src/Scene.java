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

	// render 1 object which contains the point closest to camera
	public void render() {
		Point origin = camera.getLocation();
		ArrayList<ArrayList<ArrayList<Character>>> res = new ArrayList<>();
		for (Object object : objects) {
			ArrayList<ArrayList<Character>> output = new ArrayList<>();
			for (int i = 0; i < screen.getWidth(); i++) {
				output.add(new ArrayList());
			}
			res.add(output);
		}
		Double tVal = Double.MAX_VALUE;
		int obj = -1;
		for (int x = 0; x < screen.getWidth(); x++) {
			for (int y = 0; y < screen.getHeight(); y++) {
				Point dest = screen.getPoint(x, y);
				Vector direction = dest.sub(origin);
				Ray ray = new Ray(direction, origin);


				for (int o=0; o<objects.size(); o++) {
					Double ttval = objects.get(o).intersectionWith(ray);
					if (ttval != null && ttval < tVal) {

						tVal = ttval;
						obj = o;
					}

					if (ttval !=null) {
						Point intersectionPoint = ray.getPointAt(ttval);
						Normal normalAtPoint = objects.get(o).getNormalAtPoint(intersectionPoint);
						res.get(o).get(x).add(calcLighting(normalAtPoint));
					} else {
						res.get(o).get(x).add('-');

					}
				}
			}
		}

		for (int x = 0; x < screen.getWidth(); x++) {
			for (int y = 0; y < screen.getHeight(); y++) {
				if (obj>-1) {
					System.out.print(res.get(obj).get(x).get(y));
				}else{
					System.out.print("-");
					}
				System.out.print(" ");
			}
			System.out.print("\n");
		}
		System.out.print(tVal);

	}

	// render where near objects overlap distant objects
	/*
		public void render() {
		Point origin = camera.getLocation();
		ArrayList<ArrayList<Character>> output = new ArrayList<>();
		for(int i=0; i <20; i++){
			output.add(new ArrayList());
		}

		for (int x = 0; x < screen.getWidth(); x++) {
			for (int y = 0; y < screen.getHeight(); y++) {
				Point dest = screen.getPoint(x, y);
				Vector direction = dest.sub(origin);
				Ray ray = new Ray(direction, origin);

				Double tVal = Double.MAX_VALUE;
				Object obj = null;
				for (Object object : objects) { // Make this work for multiple objects
					Double ttval = object.intersectionWith(ray); // The smallest t is closest to camera
					if (ttval !=null && ttval < tVal){
						tVal = ttval;
						obj = object;
					}
				}
				if (obj != null) {
					Point intersectionPoint = ray.getPointAt(tVal);
					Normal normalAtPoint = obj.getNormalAtPoint(intersectionPoint);
					System.out.print(calcLighting(normalAtPoint));
					output.get(x).add(calcLighting(normalAtPoint));
					//break;
				} else {
					System.out.print("-");
					output.get(x).add('-');

				}

				System.out.print(" ");
			}
			System.out.print("\n");
		}
		System.out.print(output);
	}
	 */
}
