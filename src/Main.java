package src;

import java.util.ArrayList;

class Main {
	public static void main(String[] args) throws Exception {
		Sphere sphere = new Sphere(new Point(0, -4, 0), 100);
		Sphere sphere2 = new Sphere(new Point(4, 0, 0), 10);
		Plane plane = new Plane(new Point(0, 0, 0), Normal.create(-1, 0, 0));
		Plane plane2 = new Plane(new Point(9, 0, 0), Normal.create(-1, -1, -1));
		Triangle triangle = new Triangle(new Point(5, 0, 0), new Point(5, 14, 0), new Point(6, 0, 12),
				new Vector(0,0,0),new Vector(0,0,0),new Vector(0,0,0));
		Triangle triangle2 = new Triangle(new Point(6, -7, -5), new Point(6, 10, -5), new Point(9, -6, 7),
				new Vector(0,0,0),new Vector(0,0,0),new Vector(0,0,0));
		Screen screen = new Screen(100, 100, new Point(1, 0, 0));
		Camera camera = new Camera(new Point(1.1, 0,0));
		DirectedLight light = new DirectedLight(Normal.create(-1, 1, 1));
		//Output out = new ConsoleOutput(screen.getWidth(),screen.getHeight());
		Output out = new FileOutput(screen.getWidth(),screen.getHeight(), "test.ppm");
		Scene scene = new Scene(camera, screen, light, out);

		ObjReader reader = new ObjReader("cow.obj");
		ArrayList<Triangle> poligons = reader.readfile();
		System.out.println(poligons.size());
		for (Triangle tr: poligons){
			scene.addObject(tr);
		}
		//scene.addObject(sphere2);
		//scene.addObject(sphere);
		//scene.addObject(triangle);
		//scene.addObject(plane2);
		//scene.addObject(plane);
		//scene.addObject(triangle2);
		//System.out.println(Integer.parseInt("1/1/2"));
		scene.render_mult();
		//scene.render();
	}
}
