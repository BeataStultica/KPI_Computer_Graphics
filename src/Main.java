package src;

class Main {
	public static void main(String[] args) {
		Sphere sphere = new Sphere(new Point(0, -4, 0), 5);
		Sphere sphere2 = new Sphere(new Point(4, 0, 0), 10);
		Plane plane = new Plane(new Point(0, 0, 0), Normal.create(-1, 0, 0));
		Plane plane2 = new Plane(new Point(9, 0, 0), Normal.create(-1, -1, -1));
		Triangle triangle = new Triangle(new Point(5, 0, 0), new Point(5, 14, 0), new Point(6, 0, 12));
		Triangle triangle2 = new Triangle(new Point(6, -7, -5), new Point(6, 10, -5), new Point(9, -6, 7));
		Screen screen = new Screen(20, 20, new Point(10, 0, 0));
		Camera camera = new Camera(new Point(20, 0,0));
		DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
		Output out = new ConsoleOutput(screen.getWidth(),screen.getHeight());
		Scene scene = new Scene(camera, screen, light, out);
		//scene.addObject(sphere2);
		scene.addObject(sphere);
		scene.addObject(triangle);
		//scene.addObject(plane2);
		//scene.addObject(plane);
		//scene.addObject(triangle2);

		scene.render_mult();
		//scene.render();
	}
}
