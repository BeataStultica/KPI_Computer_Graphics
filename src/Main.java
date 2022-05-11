package src;

class Main {
	public static void main(String[] args) {
		Sphere sphere = new Sphere(new Point(0, 0, 0), 10);
		Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(8, 8, 0), new Point(0, 0, 16));
		Screen screen = new Screen(20, 20, new Point(10, 0, 0));
		Camera camera = new Camera(new Point(20, 0,0));
		DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
		Scene scene = new Scene(camera, screen, light);
		scene.addObject(triangle);

		scene.render();
	}
}
