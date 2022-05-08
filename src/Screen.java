package src;

public class Screen {
	private final int width;
	private final int height;
	private final Point center;

	public Screen(int width, int height, Point center) {
		this.width = width;
		this.height = height;
		this.center = center;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Point getPoint(int x, int y) {
		return new Point(center.x(), -(double) width / 2 + y + 0.5, (double) height / 2 - 0.5 - x);
	}
}
