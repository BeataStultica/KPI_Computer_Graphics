package src;
import java.io.File;
import java.io.FileWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture {
    private int[][] texture;
    public Texture(String filepath) throws IOException {
        this.setTexture(filepath);
    }
    public int[][] getTexture(){
        return texture;
    }
    public void setTexture(String filepath) throws IOException {
        File file= new File(filepath);
        BufferedImage img = ImageIO.read(file);
        this.texture = new int[img.getHeight()][img.getWidth()];
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x,y);
                Color color = new Color(pixel);
                this.texture[y][x] = color.getRGB();
            }
        }
    }
}
