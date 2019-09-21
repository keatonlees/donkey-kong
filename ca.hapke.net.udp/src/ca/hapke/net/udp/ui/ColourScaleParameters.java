package ca.hapke.net.udp.ui;

/**
 * @author Mr. Hapke
 */
public class ColourScaleParameters {
	public final double centre, deadzone, extreme;

	public ColourScaleParameters(double centre, double deadzone, double extreme) {
		this.centre = centre;
		this.deadzone = deadzone;
		this.extreme = extreme;
	}
}
