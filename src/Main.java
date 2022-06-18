package src;

import java.awt.*;
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

		Screen screen = new Screen(300, 300, 1, new Point(450, 0, 0));
		Camera camera = new Camera(new Point(950, 0, 0));
		//DirectedLight light = new DirectedLight(Normal.create(-1, -1, 0), new Vector(255,225,255), new float[]{1f,1f,1f});
		//PointLight light = new PointLight(new Point(150,100,-200), new Vector(255,0,0), new float[]{0.5f,1f,1f});
		AmbientLight light = new AmbientLight(new Vector(255,0,0), new float[]{5.5f,6f,6f});
		Output out = new FileOutput(output_file);
		Output out2 = new FileOutput("without_tree.ppm");
		Output out3 = new FileOutput("mask.ppm");


		ObjReader reader = new ObjReader(input_file);
		ArrayList<Triangle> poligons = reader.readfile();
		ArrayList<Triangle> all_poligons = new ArrayList<>();

		System.out.println(poligons.size());
		Matrix4x4 m1 = new Matrix4x4();
		m1.move(0, 0, 0);
		m1.rotateZ(55);
		m1.scale(400, 400, 400);
		Texture textur = new Texture("wood.jpg");
		Material lamb = new Lambert(new Vector(0.5, 1,1), null); // color from 0 to 1
		for (Triangle tr : poligons) {
			tr.transform(m1);
			tr.setMaterial(lamb);
			all_poligons.add(tr);
			//tr.setColor(new Vector(0.5, 1,1)); // color from 0 to 1
			//scene.addObject(tr);
		}


		//Sphere sphere = new Sphere(new Point(0,200,0), 100);
		Material mirror = new Mirror();
		//sphere.setMaterial(mirror);
		//all_poligons.add(sphere);

		double startTime = System.currentTimeMillis();
		BiTree tree = BiTree.create(all_poligons, 20, DivisionType.SAH);
		Scene scene = new Scene(camera, screen, light, tree);
		//scene.add_obj(sphere);
		int[][] withtree = scene.render(out, true);
		System.out.println("Time with tree = " +(System.currentTimeMillis()-startTime));
		/*Vector r1 = new Vector(1,1,1);
		Normal n = Normal.create(0,0,1);
		Vector r = (n.mult(r1.dot(n)*2)).sub(r1).toNormal();//r1.sub(n.mult(2).(2*(r1.dot(n))/(n.dot(
				//n))));//.toNormal();//, intersectionPoint.add(normalAtPoint.mult(2)));
		System.out.println(r.x());
		System.out.println(r.y());
		System.out.println(r.z());*/

		/*NoTree noTree = new NoTree(poligons);
		Scene scene2 = new Scene(camera, screen, light, noTree);
		double startTime2 = System.currentTimeMillis();
		int[][] withoutTree = scene2.render(out2, true);
		System.out.println("Time without tree = " +(System.currentTimeMillis()-startTime2));

		get_mask(out3, withtree,withoutTree);*/


	}
	private static void get_mask(Output output, int[][] withtree, int[][] withoutTree){
		int[][] mask = new int[withtree.length][withtree[0].length];
		for (int i=0; i<withtree.length; i++){
			for (int j=0; j<withtree[0].length; j++){
				int dif = withoutTree[i][j] - withtree[i][j];
				if (dif != 0){
					mask[i][j] = 1;
				}
				else {
					mask[i][j] =0;
				}
			}
		}
		output.display(mask);
	}
}
