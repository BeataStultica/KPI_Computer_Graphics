package src;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RenderTest {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Test
    public void triangle_partial_closer() {
        Sphere sphere = new Sphere(new Point(0, -4, 0), 6);
        Triangle triangle = new Triangle(new Point(5, 0, 0), new Point(5, 14, 0), new Point(9, 0, 12));
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Scene scene = new Scene(camera, screen, light);
        scene.addObject(sphere);
        scene.addObject(triangle);
        scene.render();
        Assert.assertEquals("- - - - - - - - - -   - - - - - - - - - \n" +
                "- - - - - - - - - -     - - - - - - - - \n" +
                "- - - - - - - - - -       - - - - - - - \n" +
                "- - - - - - - - - -         - - - - - - \n" +
                "- - - - - - - - - -           - - - - - \n" +
                "- - - - - - - - - -           - - - - - \n" +
                "- - - - - - - - - -             - - - - \n" +
                "- - - - - - - - - -               - - - \n" +
                "- - - - - - - - - -                 - - \n" +
                "- - - - - - - - - -                   - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n", output.toString());
    }

    @Test
    public void triangle_not_closer() {
        Sphere sphere = new Sphere(new Point(0, -4, 0), 6);
        Triangle triangle = new Triangle(new Point(5, 0, 0), new Point(5, 14, 0), new Point(5, 0, 12));
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Scene scene = new Scene(camera, screen, light);
        scene.addObject(sphere);
        scene.addObject(triangle);
        scene.render();
        Assert.assertNotEquals("- - - - - - - - - -   - - - - - - - - - \n" +
                "- - - - - - - - - -     - - - - - - - - \n" +
                "- - - - - - - - - -       - - - - - - - \n" +
                "- - - - - - - - - -         - - - - - - \n" +
                "- - - - - - - - - -           - - - - - \n" +
                "- - - - - - - - - -           - - - - - \n" +
                "- - - - - - - - - -             - - - - \n" +
                "- - - - - - - - - -               - - - \n" +
                "- - - - - - - - - -                 - - \n" +
                "- - - - - - - - - -                   - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n", output.toString());
    }

    @Test
    public void sphere_closer() {
        Sphere sphere = new Sphere(new Point(4, 0, 0), 10);
        Triangle triangle = new Triangle(new Point(5, 0, 0), new Point(5, 14, 0), new Point(9, 0, 12));
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Scene scene = new Scene(camera, screen, light);
        scene.addObject(sphere);
        scene.addObject(triangle);
        scene.render();
        Assert.assertEquals("- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - * * * * 0 0 - - - - - - - \n" +
                "- - - - - . . * * * * * * 0 0 - - - - - \n" +
                "- - - - . . . . . * * * * * 0 0 - - - - \n" +
                "- - -     . . . . . * * * * * 0 0 - - - \n" +
                "- - -       . . . . . * * * * * 0 - - - \n" +
                "- -           . . . . . * * * * * 0 - - \n" +
                "- -             . . . . . * * * * 0 - - \n" +
                "- -               . . . . . * * * * - - \n" +
                "- -                 . . . . . * * * - - \n" +
                "- -                   . . . . . * * - - \n" +
                "- -                     . . . . * * - - \n" +
                "- - -                     . . . . - - - \n" +
                "- - -                       . . . - - - \n" +
                "- - - -                       . - - - - \n" +
                "- - - - -                     - - - - - \n" +
                "- - - - - - -             - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n", output.toString());
    }

    @Test
    public void two_sphere() {
        Sphere sphere = new Sphere(new Point(4, 0, 0), 10);
        Sphere sphere2 = new Sphere(new Point(0, -4, 0), 6);
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Scene scene = new Scene(camera, screen, light);
        scene.addObject(sphere);
        scene.addObject(sphere2);
        scene.render();
        Assert.assertEquals("- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - * * * * 0 0 - - - - - - - \n" +
                "- - - - - . . * * * * * * 0 0 - - - - - \n" +
                "- - - - . . . . . * * * * * 0 0 - - - - \n" +
                "- - -     . . . . . * * * * * 0 0 - - - \n" +
                "- - -       . . . . . * * * * * 0 - - - \n" +
                "- -           . . . . . * * * * * 0 - - \n" +
                "- -             . . . . . * * * * 0 - - \n" +
                "- -               . . . . . * * * * - - \n" +
                "- -                 . . . . . * * * - - \n" +
                "- -                   . . . . . * * - - \n" +
                "- -                     . . . . * * - - \n" +
                "- - -                     . . . . - - - \n" +
                "- - -                       . . . - - - \n" +
                "- - - -                       . - - - - \n" +
                "- - - - -                     - - - - - \n" +
                "- - - - - - -             - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n", output.toString());
    }

    @Test
    public void plane_closer_sphere() {
        Sphere sphere = new Sphere(new Point(0, -4, 0), 6);
        Plane plane = new Plane(new Point(7, 0, 0), Normal.create(-1, 0, 0));
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Scene scene = new Scene(camera, screen, light);
        scene.addObject(sphere);
        scene.addObject(plane);
        scene.render();
        Assert.assertEquals(". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n" +
                ". . . . . . . . . . . . . . . . . . . . \n", output.toString());
    }

    @Test
    public void two_plains() {
        Plane plane = new Plane(new Point(9, 0, 0), Normal.create(-1, 0, 0));
        Plane plane2 = new Plane(new Point(9, 0, 0), Normal.create(-1, -1, -1));
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Scene scene = new Scene(camera, screen, light);
        scene.addObject(plane2);
        scene.addObject(plane);
        scene.render();
        Assert.assertEquals("                    - - - - - - - - - - \n" +
                "                      - - - - - - - - - \n" +
                "                        - - - - - - - - \n" +
                "                          - - - - - - - \n" +
                "                            - - - - - - \n" +
                "                              - - - - - \n" +
                "                                - - - - \n" +
                "                                  - - - \n" +
                "                                    - - \n" +
                "                                      - \n" +
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
        Triangle triangle = new Triangle(new Point(5, 0, 0), new Point(5, 14, 0), new Point(12, 0, 12));
        Triangle triangle2 = new Triangle(new Point(6, 0, 0), new Point(6, 14, 0), new Point(6, 0, 12));
        Screen screen = new Screen(20, 20, new Point(10, 0, 0));
        Camera camera = new Camera(new Point(20, 0,0));
        DirectedLight light = new DirectedLight(Normal.create(0, 1, 1));
        Scene scene = new Scene(camera, screen, light);
        scene.addObject(triangle);
        scene.addObject(triangle2);
        scene.render();
        Assert.assertEquals("- - - - - - - - - -       - - - - - - - \n" +
                "- - - - - - - - - -         - - - - - - \n" +
                "- - - - - - - - - -           - - - - - \n" +
                "- - - - - - - - - -           - - - - - \n" +
                "- - - - - - - - - -             - - - - \n" +
                "- - - - - - - - - -               - - - \n" +
                "- - - - - - - - - -               - - - \n" +
                "- - - - - - - - - -                 - - \n" +
                "- - - - - - - - - -                 - - \n" +
                "- - - - - - - - - -                   - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
                "- - - - - - - - - - - - - - - - - - - - \n" +
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
