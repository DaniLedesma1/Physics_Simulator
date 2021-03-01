package tp.examples.threads.bouncingball.basic;

import java.awt.Color;
import java.util.Random;

public class Ball {

	private static final int MINWIDTH = 15;
	private static final int MINHEIGHT = 15;

	private int width;
	private int height;
	private int x;
	private int y;
	private int dx;
	private int dy;
	private Color color;

	public Ball() {
		this(0, 0);
	}

	public Ball(int maxX, int maxY) {
		Random r = new Random();

		dx = 1 + r.nextInt(4);
		dy = 1 + r.nextInt(4);

		int sizeExt = r.nextInt(5);
		width = MINWIDTH + sizeExt;
		height = MINHEIGHT + sizeExt;

		x = maxX - width > 0 ? r.nextInt(maxX - width) : 0;
		y = maxY - height > 0 ? r.nextInt(maxY - height) : 0;

		color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}

	public void move(int maxX, int maxY) {
		// TODO
	}

}
