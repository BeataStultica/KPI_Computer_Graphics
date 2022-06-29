package src;

import java.awt.*;

public class ConsoleOutput implements Output {
	public void display(int[][] matrix) {
		int height = matrix[0].length;

		for (int[] ints : matrix) {
			for (int y = 0; y < height; y++) {
				int rgb = ints[y];
				double shade = new Color(rgb).getRed() / 255.0;

				if (rgb == Scene.BACKGROUND_COLOR) {
					System.out.print('-');
				} else if (shade == 0) {
					System.out.print(' ');
				} else if (shade < 0.2) {
					System.out.print('.');
				} else if (shade < 0.5) {
					System.out.print('*');
				} else if (shade < 0.8) {
					System.out.print('0');
				} else {
					System.out.print('#');
				}
				System.out.print(" ");
			}
			System.out.print("\n");
		}
	}
}
