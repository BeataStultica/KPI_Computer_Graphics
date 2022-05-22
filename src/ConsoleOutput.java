package src;

import java.util.ArrayList;

public class ConsoleOutput implements Output{
    private ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
    private final int width;
    private final int height;
    public ConsoleOutput(int width, int height) {
        this.width = width;
        this.height = height;
        for (int i = 0; i < this.width; i++) {
            this.matrix.add(new ArrayList());
        }
    }


    public void add_element(int x, double elem){
        if (x < this.width) {
            this.matrix.get(x).add(elem);
        }
        else{
            System.out.println("Row index out of the image size");
        }
    }
    public void display_render_res(){
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                double dot = this.matrix.get(x).get(y);
                if (dot < 0) {
                    System.out.print('-');
                } else if (dot == 0){
                    System.out.print(' ');
                } else if (dot < 0.2) {
                    System.out.print('.');
                } else if (dot < 0.5) {
                    System.out.print('*');
                } else if (dot < 0.8) {
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
