package ca.hapke.net.udp.ui;

import java.awt.Color;

import javax.swing.JTextField;

import ca.hapke.util.AngleFormatter;

/**
 * @author Mr. Hapke
 */
public class ColouredTextField extends JTextField {
	private static final float SATURATION_EXTREME = 0.6f;

	private static final long serialVersionUID = 3348593870721471384L;

	private static final float RED = 0 / 360f, GREEN = 100 / 360f;

	// private static final boolean ENABLE_HSB_OUTPUT = true;
	private static final boolean ENABLE_HSB_OUTPUT = false;
	private ColourScaleParameters params;
	private AngleFormatter af;

	private AngleFormatter hsbF;

	public ColouredTextField(ColourScaleParameters params, AngleFormatter af) {
		this.params = params;
		this.af = af;
		this.hsbF = new AngleFormatter(2);
	}

	public void setValue(double x) {
		double x2;
		float h = GREEN, s, b = 0.85f;
		double c = params.centre;
		double d = params.deadzone;
		double e = params.extreme;
		if (x > c - d && x < c + d) {
			// in dead-zone
			s = 0;
		} else if (x < c - e || x > c + e) {
			s = SATURATION_EXTREME;
		} else {
			if (x < c - d) {
				// low
				x2 = x + d;
			} else {
				x2 = x - d;
			}

			s = SATURATION_EXTREME * (float) (Math.abs(x2 - c) / (e - d));
		}
		if (x < c)
			h = RED;

		Color bg = Color.getHSBColor(h, s, b);
		setBackground(bg);

		super.setText(af.format(x)
				+ (ENABLE_HSB_OUTPUT ? " (" + hsbF.format(h) + "," + hsbF.format(s) + "," + hsbF.format(b) + ")" : ""));
	}

	@Override
	public void setText(String val) {
		try {
			double d = Double.parseDouble(val);
			setValue(d);
		} catch (NumberFormatException e) {
			super.setText(val);
		}
	}
}