package src;

import java.util.ArrayList;

class Main {
	public static void main(String[] args) throws Exception {
		// if run without arguments, assign file paths to these variables
		String input_file = null;
		String output_file = null;

		// arguments without space
		for (String a : args) {
			if (a.startsWith("--source=")) {
				input_file = a.split("=")[1];
			} else if (a.startsWith("--output")) {
				output_file = a.split("=")[1];
			}
		}

		Screen screen = new Screen(300, 300, new Point(450, 0, 0));
		Camera camera = new Camera(new Point(950, 0, 0));
		DirectedLight light = new DirectedLight(Normal.create(0, -1, 1));
		Output out = new FileOutput(output_file);
		Scene scene = new Scene(camera, screen, light);

		ObjReader reader = new ObjReader(input_file);
		ArrayList<Triangle> poligons = reader.readfile();
		System.out.println(poligons.size());
		Matrix4x4 m1 = new Matrix4x4();
		m1.move(0, 0, -100);
		m1.rotateZ(55);
		m1.scale(400, 400, 400);
		for (Triangle tr : poligons) {
			tr.transform(m1);
			scene.addObject(tr);
		}

		scene.render(out, true);
	}
}
