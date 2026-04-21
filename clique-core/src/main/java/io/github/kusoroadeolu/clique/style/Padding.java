package io.github.kusoroadeolu.clique.style;

public record Padding(
		int horizontal,
		int vertical
) {
	public static final Padding ZERO = new Padding(0, 0);

	public Padding {
		if (horizontal < 0) {
			throw new IllegalArgumentException("Horizontal padding value cannot be negative");
		}

		if (vertical < 0) {
			throw new IllegalArgumentException("Vertical padding value cannot be negative");
		}
	}
}
