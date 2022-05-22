package src;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileOutput implements Output{
    private ArrayList<ArrayList<List<Integer>>> matrix = new ArrayList<>();
    private final int width;
    private final int height;
    private final String filename;
    public FileOutput(int width, int height, String filename) {
        this.width = width;
        this.height = height;
        this.filename = filename;
        for (int i = 0; i < this.width; i++) {
            this.matrix.add(new ArrayList());
        }
    }


    public void add_element(int x, double elem){
        if (x < this.width) {
            if (elem <0){
                elem = 0;
            }
            this.matrix.get(x).add(Arrays.asList( (int) Math.round(255*elem),
                    (int) Math.round(255*elem), (int) Math.round(255*elem)));
        }
        else{
            System.out.println("Row index out of the image size");
        }
    }
    public void display_render_res(){
        try {
            /*FileWriter fw = new FileWriter(this.filename);
            fw.write("P3\n");
            fw.write(this.width);
            fw.write(' ');
            fw.write(this.height);
            fw.write("\n255\n");
            for (int x = 0; x < this.width; x++) {
                for (int y = 0; y < this.height; y++) {
                    List<Integer> pixel = this.matrix.get(x).get(y);
                    for (Integer pix: pixel){
                        fw.write(pix);
                        fw.write(' ');
                    }
                    fw.write('\n');
                }
            }
            fw.close();

             */
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.filename)));

            writer.write("P3");
            writer.newLine();
            writer.write(this.width+" "+this.height);
            writer.newLine();
            writer.write("256");
            writer.newLine();
            for(int row=0;row<this.width;row++){
                for(int column=0;column<this.height;column++){
                    writer.write(this.matrix.get(row).get(column).get(0)+" ");
                    writer.write(this.matrix.get(row).get(column).get(1)+" ");
                    writer.write(this.matrix.get(row).get(column).get(2)+"");
                    writer.newLine();
                    //if(column < this.height - 1)writer.write(" ");
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
