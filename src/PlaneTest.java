package src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

	@Test
	void intersectsWith() {
		Plane plane = new Plane(new Point(0, 0, 0), Normal.create(2, 3, 2));
		Ray ray = new Ray(new Vector(2, 3, 2), new Point(-5, -3, -5));
		Ray oppositeRay = new Ray(new Vector(-2, -3, -2), new Point(-5, -3, -5));
		Ray oppositeRay1 = new Ray(new Vector(-2, -3, -2), new Point(5, 3, 5));
		Ray parallelRay = new Ray(new Vector(1, -1, 0.5), new Point(5, 3, 5));
		Ray rayInsidePlane = new Ray(new Vector(1, -1, 0.5), new Point(0, 0, 0));

		assertNotNull(plane.intersectionWith(ray));
		assertNull(plane.intersectionWith(oppositeRay));
		assertNull(plane.intersectionWith(oppositeRay1));
		assertNull(plane.intersectionWith(rayInsidePlane));
		assertNull(plane.intersectionWith(parallelRay));
	}
}