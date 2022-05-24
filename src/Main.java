package src;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;

class Main {
	public static void main(String[] args) throws Exception {

		// if run without arguments, assign file paths to these variables
		String input_file=null;
		String output_file=null;

		// arguments without space
		for (String a: args){
			if (a.startsWith("--source=")){
				input_file=a.split("=")[1];
			} else if (a.startsWith("--output")){
				output_file = a.split("=")[1];
			}
		}
		Sphere sphere = new Sphere(new Point(0, 0, 0), 50);
		Sphere sphere2 = new Sphere(new Point(4, 0, 0), 10);
		Plane plane = new Plane(new Point(0, 0, 0), Normal.create(-1, 0, 0));
		Plane plane2 = new Plane(new Point(9, 0, 0), Normal.create(-1, -1, -1));
		Triangle triangle = new Triangle(new Point(5, 0, 0), new Point(5, 14, 0), new Point(6, 0, 12),
				new Vector(0,0,0),new Vector(0,0,0),new Vector(0,0,0));
		Triangle triangle2 = new Triangle(new Point(6, -7, -5), new Point(6, 10, -5), new Point(9, -6, 7),
				new Vector(0,0,0),new Vector(0,0,0),new Vector(0,0,0));
		Screen screen = new Screen(300, 300, new Point(450, 0, 0));
		Camera camera = new Camera(new Point(550, 0,0));
		DirectedLight light = new DirectedLight(Normal.create(1, 1, 1));
		//Output out = new ConsoleOutput(screen.getWidth(),screen.getHeight());
		Output out = new FileOutput(screen.getWidth(),screen.getHeight(), output_file);
		Scene scene = new Scene(camera, screen, light, out);

		ObjReader reader = new ObjReader(input_file);
		ArrayList<Triangle> poligons = reader.readfile();
		System.out.println(poligons.size());
		Matrix4x4 m1 = new Matrix4x4();
		m1.move(100, -150,0);
		m1.rotate_x(55);
		//m1.rotate_z(45);
		//m1.rotate_y(45);
		m1.scale(600,600,600);
		for (Triangle tr: poligons){
			tr.transform(m1);
			scene.addObject(tr);
		}

		//m1.multiply_vector(new Vector(1.,2.,3.));
		//Matrix4x4 m2 = new Matrix4x4(1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7);
		//m1.multiply_matrices(m2);
		//System.out.println(Arrays.deepToString(m1.matrix));
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
