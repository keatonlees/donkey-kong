package ca.hapke.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

import ca.hapke.gyro.MultiDimensionStatus;
import ca.hapke.util.ByteUtil;
import ca.hapke.util.RunKillThread;

/**
 * @author Mr. Hapke
 */
public class UdpServerThread extends RunKillThread {

	// private final Queue<String> messages;
	private final int port;
	private DatagramSocket serverSocket;
	private MultiDimensionStatus status;
	private List<IUdpServerListener> listeners = new ArrayList<>();

	public UdpServerThread(int port, MultiDimensionStatus status) {
		// this.messages = messages;
		this.port = port;
		this.status = status;
	}

	@Override
	public void run() {
		byte[] recvData = new byte[256];
		try {
			try {
				serverSocket = new DatagramSocket(port);
				running = true;
				for (IUdpServerListener listener : listeners)
					listener.serverOnline();

			} catch (Exception e) {
				kill = true;

				for (IUdpServerListener listener : listeners)
					listener.serverAbort("Failed to bind port: " + e.toString());
				return;
			}

			while (!kill) {
				DatagramPacket recvP = new DatagramPacket(recvData, recvData.length);
				serverSocket.receive(recvP);
				byte[] rawData = recvP.getData();
				byte[] data = ByteUtil.removeFrontPadding(rawData, UdpUtil.PREFIX_BYTES);

				String sentence = "";
				TransmitMode mode = TransmitMode.fromMode(rawData[0]);
				switch (mode) {
				case Bytes:
					sentence = "(raw data un-processed)";
					break;
				case AccelGyroData:
					double[] doubles = ByteUtil.bytesToDouble(data);
					// for (int i = 0; i < doubles.length; i++) {
					// double d = doubles[i];
					//
					// if (sentence.length() > 0)
					// sentence = sentence + ", ";
					// sentence = sentence + nf.format(d);
					// }
					status.update(doubles);
					for (IUdpServerListener listener : listeners)
						listener.accelGyroUpdated();
					break;
				case String:
					sentence = new String(data);
					for (IUdpServerListener listener : listeners)
						listener.sentenceReceived(sentence);
					break;

				}
			}
		} catch (Exception e) {
			for (IUdpServerListener listener : listeners)
				listener.serverAbort("ABORT:" + e.getMessage());
		}

		for (IUdpServerListener listener : listeners)
			listener.serverOffline();

		running = false;
		kill = false;
	}

	public void add(IUdpServerListener l) {
		listeners.add(l);
	}

}
