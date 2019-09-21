package ca.hapke.net.udp.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.vecmath.Point3d;

/**
 * 
 * @author Mr. Hapke
 *
 */
public class GyroManualPanel extends JPanel {
	private final AccelGyroFrame XyVisualizer;

	GyroManualPanel(AccelGyroFrame accelGyroFrame) {
		XyVisualizer = accelGyroFrame;
	}

	private static final long serialVersionUID = 4126456410195365723L;

	protected int[] xs = { 0, getWidth() / 2, getWidth() - 1 };
	protected int[] ys = { 0, getHeight() / 2, getHeight() - 1 };

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		for (int x : xs)
			g.drawLine(x, 0, x, getHeight());
		for (int y : ys)
			g.drawLine(0, y, getWidth(), y);

		g.setColor(Color.RED);
		Point3d p = XyVisualizer.status.getData()[2];
		double xVal = p.x / 360;
		double yVal = p.y / 360;

		int x = (int) (xs[1] * (1 + xVal));
		int y = (int) (ys[1] * (1 - yVal));
		int l = 3;

		g.drawLine(x - l, y, x + l, y);
		g.drawLine(x, y - l, x, y + l);
		g.drawString("(" + XyVisualizer.af.format(xVal) + "," + XyVisualizer.af.format(yVal) + ")", x, y);
	}

	@Override
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		xs = new int[] { 0, w / 2, w - 1 };
		ys = new int[] { 0, w / 2, w - 1 };
	}

}