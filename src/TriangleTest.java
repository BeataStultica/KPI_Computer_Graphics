package src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TriangleTest {
    @Test
    void intersectsWith() {
        Ray ray = new Ray(new Vector(2, 3, 2), new Point(-5, -3, -5));
        Ray ray1 = new Ray(new Vector(-1, 0, 0), new Point(5, 0, 0));
        Triangle triangle= new Triangle(new Point(0, 0, 0), new Point(0, 15, 0), new Point(0, 0, 15),
                new Vector(0,0,0),new Vector(0,0,0),new Vector(0,0,0));

        assertNull(triangle.intersectionWith(ray));
        assertNotNull(triangle.intersectionWith(ray1));
    }
}
