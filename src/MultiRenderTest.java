package src;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MultiRenderTest {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Test
    public void sphere_closer() {
        Sphere sphere = new Sphere(new Point(4, 0, -6), 10);
        Triangle triangle = new Triangle(new Point(5, 0, 0), new Point(5, 14, 0), new Point(9, 0, 12),
                new Vector(0,0,0),new Vector(0,0,0),new Vector(0,0,0));
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Output out = new ConsoleOutput(screen.getWidth(),screen.getHeight());
        Scene scene = new Scene(camera, screen, light, out);
        scene.addObject(sphere);
        scene.addObject(triangle);
        scene.render_mult();
        Assert.assertEquals("- - - - - - - - - -   - - - - - - - - - \n" +
                "- - - - - - - - - -     - - - - - - - - \n" +
                "- - - - - - - - - -       - - - - - - - \n" +
                "- - - - - - - - - -         - - - - - - \n" +
                "- - - - - - - - - -           - - - - - \n" +
                "- - - - - - - - - -           - - - - - \n" +
                "- - - - - - - - - -             - - - - \n" +
                "- - - - - - - - 0 0 0 0           - - - \n" +
                "- - - - - - * * * * 0 0 0 #         - - \n" +
                "- - - - - . * * * * * 0 0 0 0         - \n" +
                "- - - - . . . * * * * * 0 0 0 0 - - - - \n" +
                "- - -   . . . * * * * * * 0 0 0 0 - - - \n" +
                "- - -     . . . * * * * * * 0 0 0 - - - \n" +
                "- -       . . . . * * * * * * 0 0 0 - - \n" +
                "- -         . . . . * * * * * * 0 0 - - \n" +
                "- -         . . . . * * * * * * * 0 - - \n" +
                "- -           . . . . * * * * * * * - - \n" +
                "- -             . . . . * * * * * * - - \n" +
                "- -               . . . . * * * * * - - \n" +
                "- - -               . . . . . * * - - - \n", output.toString());
    }

    @Test
    public void two_sphere() {
        Sphere sphere = new Sphere(new Point(4, 8, 0), 10);
        Sphere sphere2 = new Sphere(new Point(0, -8, 0), 10);
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Output out = new ConsoleOutput(screen.getWidth(),screen.getHeight());
        Scene scene = new Scene(camera, screen, light, out);
        scene.addObject(sphere);
        scene.addObject(sphere2);
        scene.render_mult();
        Assert.assertEquals("- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - * * * * * \n" +
                "- - - - - - - - - - - - - . . . . . * * \n" +
                "- - - * 0 0 0 - - - -       . . . . . . \n" +
                "- * * * * 0 0 0 # -               . . . \n" +
                ". . * * * * 0 0 0 #                   . \n" +
                ". . . * * * * 0 0                       \n" +
                "  . . * * * * * 0                       \n" +
                "    . . * * * * *                       \n" +
                "      . . . * * *                       \n" +
                "        . . . * *                       \n" +
                "          . . . *                       \n" +
                "              . . .                     \n" +
                "-                 -                     \n" +
                "- - -         - - - -                   \n" +
                "- - - - - - - - - - - - -               \n" +
                "- - - - - - - - - - - - - - -           \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n", output.toString());
    }

    @Test
    public void sphere_closer_plain() {
        Sphere sphere = new Sphere(new Point(0, -4, 0), 6);
        Plane plane = new Plane(new Point(0, 0, 0), Normal.create(-1, 0, 0));
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Output out = new ConsoleOutput(screen.getWidth(),screen.getHeight());
        Scene scene = new Scene(camera, screen, light, out);
        scene.addObject(sphere);
        scene.addObject(plane);
        scene.render_mult();
        Assert.assertEquals("                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "            * * 0 #                     \n" +
                "            . * * 0 #                   \n" +
                "              . * * 0                   \n" +
                "                . * *                   \n" +
                "                  . *                   \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n", output.toString());
    }

    @Test
    public void two_plains() {
        Plane plane = new Plane(new Point(0, 0, 0), Normal.create(-1, 0, 0));
        Plane plane2 = new Plane(new Point(9, 0, 0), Normal.create(-1, -1, -1));
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Output out = new ConsoleOutput(screen.getWidth(),screen.getHeight());
        Scene scene = new Scene(camera, screen, light, out);
        scene.addObject(plane2);
        scene.addObject(plane);
        scene.render_mult();
        Assert.assertEquals("                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n", output.toString());
    }
    @Test
    public void two_triangle() {
        Triangle triangle = new Triangle(new Point(5, 0, 0), new Point(5, 14, 0), new Point(5, 0, 12),
                new Vector(0,0,0),new Vector(0,0,0),new Vector(0,0,0));
        Triangle triangle2 = new Triangle(new Point(6, -7, -5), new Point(6, 10, -5), new Point(9, -6, 7),
                new Vector(0,0,0),new Vector(0,0,0),new Vector(0,0,0));
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Output out = new ConsoleOutput(screen.getWidth(),screen.getHeight());
        Scene scene = new Scene(camera, screen, light, out);
        scene.addObject(triangle);
        scene.addObject(triangle2);
        scene.render_mult();
        Assert.assertEquals("- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - -   - - - - - - - - - \n" +
                "- - - - - - - - - -   . - - - - - - - - \n" +
                "- - - - -   - - - -   . . - - - - - - - \n" +
                "- - - - -     - - -     . . - - - - - - \n" +
                "- - - - -       - -   . .   . - - - - - \n" +
                "- - - - -         - .     . . . - - - - \n" +
                "- - - - -                 .   . .   - - \n" +
                "- - - - -               .             - \n" +
                "- - - - -                 - - - - - - - \n" +
                "- - - - -                     - - - - - \n" +
                "- - - - -                       - - - - \n" +
                "- - - - -                         - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n", output.toString());
    }
    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
}

