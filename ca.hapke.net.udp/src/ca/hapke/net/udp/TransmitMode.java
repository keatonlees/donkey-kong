package ca.hapke.net.udp;

/**
 * 
 * @author Mr. Hapke
 */
public enum TransmitMode {
	Bytes(0), String(1), AccelGyroData(2);

	public final byte mode;

	private TransmitMode(int mode) {
		this.mode = (byte) mode;
	}

	public static TransmitMode fromMode(byte mode) {
		for (TransmitMode t : values()) {
			if (mode == t.mode)
				return t;
		}
		return null;
	}
}
