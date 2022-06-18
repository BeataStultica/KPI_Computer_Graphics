package src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Scene {
	public static final int BACKGROUND_COLOR = Color.magenta.getRGB();
	private final Camera camera;
	private final Screen screen;
	private final Light light;
	private final BoundingTree tree;
	private ArrayList<Object> obj_list = new ArrayList<>();

	public Scene(Camera camera, Screen screen, Light light, BoundingTree tree) {
		this.camera = camera;
		this.screen = screen;
		this.light = light;
		this.tree = tree;
	}

	public void add_obj(Object obj){
		obj_list.add(obj);
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
		//Vector test = new Vector(500, 250, 500);
		//Vector test2 = roundColor(test);
		//System.out.println(test2.x());
		//System.out.println(test2.y());
		//System.out.println(test2.z());
		int[][] matrix = new int[screen.getWidth()][screen.getHeight()];

		for (int x = 0; x < screen.getWidth(); x++) {
			for (int y = 0; y < screen.getHeight(); y++) {
				Point dest = screen.getPoint(x, y);
				Vector direction = dest.sub(origin);
				Ray ray = new Ray(direction.toNormal(), origin);
				int color = calcucate_intersection(ray, withShadows, matrix,x,y,0, null);
				matrix[x][y] = color;
				/*double tVal = Double.MAX_VALUE;
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
						Object obj2 = obj;
						int deep = 3;
						while (deep>0 && obj2.getMaterial().ismirror >0){
							Ray reflect_ray = new Ray(ray.getDirection().sub(ray.getDirection().dot_el(normalAtPoint).dot_el(
									ray.getDirection())).mult(2).toNormal(), intersectionPoint);

						}
						Vector c = obj.getMaterial().bxdf_func(light.getDirection(intersectionPoint), ray.getDirection(),
								obj, intersectionPoint).dot_el(light.getColor().dot_el(light.getIntens())).mult(lighting); // replace obj.getColor with texture function
						//int shade = (int) Math.round(lighting * 255);
						matrix[x][y] = new Color(roundColor(c.x()), roundColor(c.y()), roundColor(c.z())).getRGB();
					}
				} else {
					matrix[x][y] = BACKGROUND_COLOR;
				}*/
			}
		}

		output.display(matrix);
		return matrix;
	}

	private int calcucate_intersection(Ray ray, boolean withShadows, int[][] matrix, int x, int y, int deep, Object selfObj){
		double tVal = Double.MAX_VALUE;
		Object obj = null;

		List<Triangle> triangles = tree.getTriangles(ray);
		//System.out.println(x * screen.getWidth() + y + " out of " + screen.getWidth() * screen.getHeight());
		//System.out.println("triangles size: " + triangles.size());
		for (Object object : triangles) {
			Double ttval = object.intersectionWith(ray);

			if (ttval != null && ttval < tVal && object!=selfObj) {
				tVal = ttval;
				obj = object;
			}
		}
		//if (deep==0) {
		for (Object object : obj_list) {
			Double ttval = object.intersectionWith(ray);
			if (ttval != null && ttval < tVal && object!=selfObj) {
				tVal = ttval;
				obj = object;
			}
		}
		//}

		if (obj != null) {
			Point intersectionPoint = ray.getPointAt(tVal);
			Normal normalAtPoint = obj.getNormalAtPoint(intersectionPoint);

			if (withShadows && lightObstructed(new Ray(light.getDirection(intersectionPoint), intersectionPoint.add(normalAtPoint.mult(2))), obj)) {
				//matrix[x][y] = Color.BLACK.getRGB();
				return Color.BLACK.getRGB();
			} else {
				double lighting = light.calcLighting(normalAtPoint, intersectionPoint, tree);
				while (deep<3 && obj.getMaterial().ismirror() >0){
					Ray reflect_ray = new Ray(ray.getDirection().sub(normalAtPoint.mult(ray.getDirection().dot(normalAtPoint)*2)).toNormal(), intersectionPoint);
					//new Ray((normalAtPoint.mult(ray.getDirection().dot(normalAtPoint)*2))
							//.sub(ray.getDirection()).toNormal(), intersectionPoint);//.add(normalAtPoint.mult(2)));

					int color = calcucate_intersection(reflect_ray, withShadows, matrix, x,y,deep+1, obj);

					//System.out.println(tVal);
					return color;

				}
				if (obj.getMaterial().ismirror() >0){
					//System.out.println(deep);
					return Color.GREEN.getRGB();
				}

				Vector c = obj.getMaterial().bxdf_func(light.getDirection(intersectionPoint), ray.getDirection(),
						obj, intersectionPoint).dot_el(light.getColor().dot_el(light.getIntens()));//.mult(lighting);

				Vector c_rl = roundColor(c).mult(lighting);
				Vector c_r = roundColor(c_rl);
				return new Color((int) c_r.x(), (int) c_r.y(), (int) c_r.z()).getRGB();
			}
		} else {
			//matrix[x][y] = BACKGROUND_COLOR;
			if (deep>0){
				return Color.GREEN.getRGB();
			}
			return BACKGROUND_COLOR;
		}
	}
	private Vector roundColor(Vector v){
		if (v.x()<=0 & v.y()<=0 & v.z()<=0){
			return new Vector(0,0,0);
		}
		if (v.x()<=255 & v.y()<=255 & v.z()<=255){
			return v;
		}
		if (v.x() >= v.y() & v.x() >= v.z()){
			return new Vector(255, 255*(v.y()/v.x()), 255*(v.z()/v.x()) );
		}else if (v.y() >= v.x() & v.y() >= v.z()){
			return new Vector(255*(v.x()/v.y()), 255, 255*(v.z()/v.y()) );
		}else{
			return new Vector(255*(v.x()/v.z()), 255*(v.y()/v.z()), 255 );
		}
	}
}
