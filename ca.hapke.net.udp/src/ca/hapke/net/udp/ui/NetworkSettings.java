/**
 * 
 */
package ca.hapke.net.udp.ui;

/**
 * @author Mr. Hapke
 */
@SuppressWarnings("unused")
public abstract class NetworkSettings {

	private static final String LOCALHOST = "127.0.0.1";
	private static final String SURFACE = "10.50.250.67";
	private static final String DESKTOP = "10.50.31.10";
	private static final String LAPTOP = "10.50.31.73";

	public static String getIp() {
		// return SURFACE;
		// return LOCALHOST;
		return DESKTOP;
	}
}
