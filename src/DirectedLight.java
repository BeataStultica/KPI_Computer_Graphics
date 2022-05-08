package src;

public class DirectedLight {
	private final Normal direction;

	public DirectedLight(Normal direction) {
		this.direction = direction;
	}

	public Normal getDirection() {
		return direction;
	}
}
