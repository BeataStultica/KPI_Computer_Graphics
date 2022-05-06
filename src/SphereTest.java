package src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
	@Test
	void intersectsWith() {
		Ray ray = new Ray(new Vector(2, 3, 2), new Point(-5, -3, -5));
		Ray ray1 = new Ray(new Vector(2, 3, 2), new Point(-10, -3, -5));
		Sphere sphere = new Sphere(new Point(0, 0, 0), 4);

		assertNotNull(sphere.intersectionWith(ray));
		assertNull(sphere.intersectionWith(ray1));
	}
}