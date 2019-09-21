package ca.hapke.net.udp.ui;

import java.io.IOException;

import ca.hapke.gyro.MultiDimensionStatus;
import ca.hapke.net.udp.TransmitMode;
import ca.hapke.net.udp.UdpUtil;

/**
 * 
 * @author Mr. Hapke
 *
 */
public class MdsToUdpAdapter extends MdsToUiAdapter {

	private int port;
	private String ip;
	private boolean udpEnabled = false;

	public MdsToUdpAdapter(ColouredTextField[][] txtFields, GyroManualPanel pnlGyroManual) {
		super(txtFields, pnlGyroManual);
	}

	public void setTarget(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.udpEnabled = true;
	}

	public void disableUdp() {
		this.udpEnabled = false;
	}

	@Override
	public void updateOccurred(MultiDimensionStatus status) {
		super.updateOccurred(status);
		if (udpEnabled) {
			byte[] bytes = UdpUtil.statusToBytes(status);
			try {
				UdpUtil.send(ip, port, bytes, TransmitMode.AccelGyroData.mode);
			} catch (IOException e) {
				// NOOP Don't care
			}
		}
	}

}
