package src;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Scene {
	private final ArrayList<Object> objects = new ArrayList<Object>();
	private final Camera camera;
	private final Screen screen;
	private final DirectedLight light;
	private final Output output;

	public Scene(Camera camera, Screen screen, DirectedLight light, Output output) {
		this.camera = camera;
		this.screen = screen;
		this.light = light;
		this.output = output;
	}

	public void addObject(Object obj) {
		objects.add(obj);
	}

	private double calcLighting(Normal normalAtPoint) {
		double dotProduct = light.getDirection().dot(normalAtPoint);
		//return dotProduct;
		if (dotProduct < 0){
			return 0;
		}else{
			return dotProduct;
		}
		/*if (dotProduct < 0) {
			return ' ';
		} else if (dotProduct < 0.2) {
			return '.';
		} else if (dotProduct < 0.5) {
			return '*';
		} else if (dotProduct < 0.8) {
			return '0';
		} else {
			return '#';
		}*/
	}

	// render 1 object which contains the point closest to camera
	/*public void render(){
		Point origin = camera.getLocation();
		ArrayList<Output> res = new ArrayList<>();
		for (Object object : objects) {
			Class op = this.output.getClass();//ConsoleOutput(screen.getWidth(), screen.getHeight());
			try {
				Constructor cons = op.getConstructor();
				int[] args = {screen.getWidth(), screen.getHeight()};
				java.lang.Object output = cons.newInstance(args);
				res.add((Output) output);
			}catch (InstantiationException | NoSuchMethodException ie) {

			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
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
						res.get(o).add_element(x, calcLighting(normalAtPoint));//.get(x).add(calcLighting(normalAtPoint));
					} else {
						res.get(o).add_element(x,0);//get(x).add('-');

					}
				}
			}
		}

		res.get(obj).display_render_res();

	}*/

	// render where near objects overlap distant objects

	public void render_mult() {
		Point origin = camera.getLocation();


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
					//System.out.print(calcLighting(normalAtPoint));
					this.output.add_element(x, calcLighting(normalAtPoint));

				} else {
					this.output.add_element(x, -1.0);

				}

			}

		}
		this.output.display_render_res();
	}
}
