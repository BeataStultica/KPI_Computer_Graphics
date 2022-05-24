package src;


import java.util.Arrays;

public class Matrix4x4 {

    public double[][] matrix = null;
    private Matrix4x4 move;
    private Matrix4x4 rotate;
    private Matrix4x4 scale;

    public Matrix4x4()/*double x11, double x12, double x13, double x14,
                     double x21, double x22, double x23, double x24,
                     double x31, double x32, double x33, double x34,
                     double x41, double x42, double x43, double x44)*/{
        /*this.matrix = new double[][]{
                new double[]{x11, x12, x13, x14},
                new double[]{x21, x22, x23, x24},
                new double[]{x31, x32, x33, x34},
                new double[]{x41, x42, x43, x44},

        };*/
        //this.move = new Matrix4x4(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

    }
     public void multiply_matrices(double[][] secondMatrix) {
        double[][] result = new double[this.matrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                double cell = 0;
                for (int i = 0; i < secondMatrix.length; i++) {
                    cell += this.matrix[row][i] * secondMatrix[i][col];
                }
                result[row][col] = cell;
            }
        }
        this.matrix = result;
    }
    public Vector multiply_vector(Vector v){
        double[] result = new double[this.matrix.length];
        double[] vector4 = new double[] {v.x(),v.y(),v.z(),1};
        for (int row = 0; row < result.length; row++) {
            double cell = 0;
            for (int i = 0; i < 4; i++) {
                cell += this.matrix[row][i] * vector4[i];
            }
            result[row] = cell;
        }
        System.out.println(Arrays.toString(result));
        return new Vector(result[0], result[1], result[2]);
    }
    public Point multiply_point(Point v){
        double[] result = new double[this.matrix.length];
        double[] vector4 = new double[] {v.x(),v.y(),v.z(),1};
        for (int row = 0; row < result.length; row++) {
            double cell = 0;
            for (int i = 0; i < 4; i++) {
                cell += this.matrix[row][i] * vector4[i];
            }
            result[row] = cell;
        }
        //System.out.println(Arrays.toString(result));
        return new Point(result[0], result[1], result[2]);
    }
    public void move(double x,double y, double z){
        double [][] new_move = new double[][]{
                new double[]{1,0,0,x},
                new double[]{0,1,0,y},
                new double[]{0,0,1,z},
                new double[]{0,0,0,1}
        };
        if (this.matrix==null){
            this.matrix = new_move;
        }
        else {
            this.multiply_matrices(new_move);
        }
    }
    public void scale(double x,double y, double z){
        double [][] new_move = new double[][]{
                new double[]{x,0,0,0},
                new double[]{0,y,0,0},
                new double[]{0,0,z,0},
                new double[]{0,0,0,1}
        };
        if (this.matrix==null){
            this.matrix = new_move;
        }
        else {
            this.multiply_matrices(new_move);
        }
    }
    public void rotate_x(double x_angle){
        double rad = Math.toRadians(x_angle) ;
        double [][] new_move = new double[][]{
                new double[]{Math.cos(rad),-Math.sin(rad),0,0},
                new double[]{Math.sin(rad),Math.cos(rad),0,0},
                new double[]{0,0,1,0},
                new double[]{0,0,0,1}
        };
        if (this.matrix==null){
            this.matrix = new_move;
        }
        else {
            this.multiply_matrices(new_move);
        }
    }
    public void rotate_y(double y_angle){
        double rad = Math.toRadians(y_angle) ;
        double [][] new_move = new double[][]{
                new double[]{Math.cos(rad),0, Math.sin(rad),0},
                new double[]{0,1,0,0},
                new double[]{-Math.sin(rad),0, Math.cos(rad),0},
                new double[]{0,0,0,1}
        };
        if (this.matrix==null){
            this.matrix = new_move;
        }
        else {
            this.multiply_matrices(new_move);
        }
    }
    public void rotate_z(double z_angle){
        double rad = Math.toRadians(z_angle) ;
        double [][] new_move = new double[][]{
                new double[]{1,0,0,0},
                new double[]{0, Math.cos(rad),-Math.sin(rad),0},
                new double[]{0, Math.sin(rad),Math.cos(rad),0},
                new double[]{0,0,0,1}
        };
        if (this.matrix==null){
            this.matrix = new_move;
        }
        else {
            this.multiply_matrices(new_move);
        }
    }

}
