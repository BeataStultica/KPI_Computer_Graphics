package src;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjReader {
    private final String filepath;
    private ArrayList<Triangle> poligons = new ArrayList<>();
    public ObjReader(String filepath){
        this.filepath = filepath;
    }

    public ArrayList<Triangle> readfile() throws Exception{
        this.poligons = new ArrayList<>();
        FileReader fr = new FileReader(this.filepath);
        Scanner scan = new Scanner(fr);
        ArrayList<ArrayList<Double>> vertex_list = new ArrayList<>();
        ArrayList<ArrayList<Double>> normal_list = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Integer>>> index_list = new ArrayList<>();
        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            ArrayList<String> wordsInLine = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
            if (wordsInLine.isEmpty() || wordsInLine.get(0).equals("#")) {
                continue;
            }

            final String token = wordsInLine.get(0);
            wordsInLine.remove(0);

            if (token.equals("v")){
                ArrayList<Double> temp = new ArrayList<>();
                for (int i=0; i<3; i++){
                    temp.add(Double.parseDouble(wordsInLine.get(i)));
                }
                vertex_list.add(temp);
            } else if (token.equals("vn")){
                ArrayList<Double> temp = new ArrayList<>();
                for (int i=0; i<3; i++){
                    temp.add(Double.parseDouble(wordsInLine.get(i)));
                }
                normal_list.add(temp);
            } else if (token.equals("f")){
                ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
                for (int i=0; i<3; i++){
                    ArrayList<Integer> temp2 = new ArrayList<>();
                    ArrayList<String> indexs = new ArrayList<String>(Arrays.asList(wordsInLine.get(i).split("/")));
                    for (String ind: indexs) {
                        if (ind !="") {
                            temp2.add(Integer.parseInt(ind));
                        }
                        else {
                            temp2.add(null);
                        }
                    }
                    temp.add(temp2);
                }
                index_list.add(temp);
            }

        }
        //System.out.println(index_list.size());
        //System.out.println(vertex_list.size());
        //System.out.println(normal_list.size());
        for (ArrayList<ArrayList<Integer>> f: index_list){
            //System.out.println(f);
            ArrayList<Point> p = new ArrayList<>();
            ArrayList<Vector> n = new ArrayList<>();
            for (ArrayList<Integer> t: f){
                ArrayList<Double> vertex_p1 = vertex_list.get(t.get(0)-1);
                ArrayList<Double> normal_p2 = normal_list.get(t.get(2)-1);
                p.add(new Point(vertex_p1.get(0), vertex_p1.get(1),vertex_p1.get(2)));
                n.add(new Vector(normal_p2.get(0), normal_p2.get(1),normal_p2.get(2)));
            }
            this.poligons.add(new Triangle(p.get(0), p.get(1),p.get(2), n.get(0), n.get(1), n.get(2)));
        }
        fr.close();

        return this.poligons;
    }
}
