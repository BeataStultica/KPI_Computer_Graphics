package src;

import java.awt.*;
import java.io.*;

public class FileOutput implements Output {
	private final String filename;

	public FileOutput(String filename) {
		this.filename = filename;
	}

	public void display(int[][] matrix) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.filename)));
			int width = matrix.length;
			int height = matrix[0].length;

			writer.write("P3");
			writer.newLine();
			writer.write(width + " " + height);
			writer.newLine();
			writer.write("256");
			writer.newLine();

			for (int[] ints : matrix) {
				for (int column = 0; column < height; column++) {
					Color color = new Color(ints[column]);

					writer.write(color.getRed() + " ");
					writer.write(color.getGreen() + " ");
					writer.write(color.getBlue() + "");
					writer.newLine();
				}

				writer.newLine();
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
