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
		DirectedLight light = new DirectedLight(Normal.create(0, 1, 0), new Vector(255,125,0), new float[]{1f,1f,1f});
		//PointLight light = new PointLight(new Point(150,100,-200), new Vector(255,0,0), new float[]{0.5f,1f,1f});
		//AmbientLight light = new AmbientLight(new Vector(255,0,0), new float[]{0.5f,1f,1f});
		Output out = new FileOutput(output_file);
		Output out2 = new FileOutput("without_tree.ppm");
		Output out3 = new FileOutput("mask.ppm");


		ObjReader reader = new ObjReader(input_file);
		ArrayList<Triangle> poligons = reader.readfile();

		System.out.println(poligons.size());
		Matrix4x4 m1 = new Matrix4x4();
		m1.move(0, 0, -100);
		m1.rotateZ(55);
		m1.scale(400, 400, 400);

		for (Triangle tr : poligons) {
			tr.transform(m1);
			tr.setColor(new Vector(0.5, 1,1)); // color from 0 to 1
			//scene.addObject(tr);
		}

		double startTime = System.currentTimeMillis();
		BiTree tree = BiTree.create(poligons, 10, DivisionType.MIDDLE);
		Scene scene = new Scene(camera, screen, light, tree);

		NoTree noTree = new NoTree(poligons);
		Scene scene2 = new Scene(camera, screen, light, noTree);

		int[][] withtree = scene.render(out, true);
		System.out.println("Time with tree = " +(System.currentTimeMillis()-startTime));

		double startTime2 = System.currentTimeMillis();
		int[][] withoutTree = scene2.render(out2, true);
		System.out.println("Time without tree = " +(System.currentTimeMillis()-startTime2));

		get_mask(out3, withtree,withoutTree);


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
