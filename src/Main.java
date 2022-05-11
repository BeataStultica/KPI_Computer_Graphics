package src;

class Main {
	public static void main(String[] args) {
		Sphere sphere = new Sphere(new Point(0, -4, 0), 6);
		Sphere sphere2 = new Sphere(new Point(4, 2, 0), 10);
		Plane plane = new Plane(new Point(0, 0, 0), Normal.create(-1, 0, 0));
		Triangle triangle = new Triangle(new Point(5, 0, 0), new Point(5, 14, 0), new Point(9, 0, 12));
		Screen screen = new Screen(20, 20, new Point(10, 0, 0));
		Camera camera = new Camera(new Point(20, 0,0));
		DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
		Scene scene = new Scene(camera, screen, light);
		scene.addObject(sphere);
		//scene.addObject(sphere2);
		scene.addObject(triangle);
		scene.addObject(plane);

		scene.render();
	}
}
