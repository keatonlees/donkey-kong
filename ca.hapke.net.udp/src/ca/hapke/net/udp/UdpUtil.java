package ca.hapke.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.vecmath.Point3d;

import ca.hapke.gyro.MultiDimensionStatus;
import ca.hapke.util.ByteUtil;

/**
 * 
 * @author Mr. Hapke
 *
 */
public abstract class UdpUtil {

	public static final int PREFIX_BYTES = 2;

	public static void send(String ip, int port, String text) throws IOException {
		byte[] buffer = new byte[Math.min(255, text.length() + 1)];

		int i = 0;
		for (; i < buffer.length - 1 && i < text.length(); i++) {
			buffer[i] = (byte) text.charAt(i);
		}
		// null terminate
		buffer[i] = 0;

		send(ip, port, buffer, TransmitMode.String.mode);
	}

	/**
	 * sends first (up to) 255 bytes, prefixed with 1 byte telling the length
	 * after it.
	 * 
	 * @param mode
	 */
	public static void send(String ip, int port, byte[] buffer, byte mode) throws IOException {
		InetAddress target = InetAddress.getByName(ip);

		byte[] out = new byte[Math.min(256, buffer.length + PREFIX_BYTES)];
		// byte[] out = Arrays.copyOfRange(buffer, 0, 1);
		int length = Math.min(255, buffer.length);
		out[0] = mode;
		out[1] = (byte) length;
		for (int i = 0; i < length; i++)
			out[i + PREFIX_BYTES] = buffer[i];

		DatagramPacket packet = new DatagramPacket(out, length + 1, target, port);
		DatagramSocket datagramSocket = new DatagramSocket();
		datagramSocket.send(packet);

		datagramSocket.close();
	}

	// public static byte[] statusToBytes(AccelGyroStatus ags) {
	// double[] input = new double[] { ags.getAccel().x, ags.getAccel().y,
	// ags.getAccel().z, ags.getGyro().x,
	// ags.getGyro().y, ags.getGyro().z, };
	// // byte[] output = new byte[8 * 6];
	// // doubleToBytes(, output, 0);
	// // doubleToBytes(, output, 8);
	// // doubleToBytes( output, 16);
	// // doubleToBytes(output, 24);
	// // doubleToBytes( output, 32);
	// // doubleToBytes( output, 40);
	//
	// return doublesToBytes(input);
	// }

	public static byte[] statusToBytes(MultiDimensionStatus status) {
		int dims = status.getDimensions();
		double[] input = new double[3 * dims];
		for (int i = 0; i < dims; i++) {
			Point3d axis = status.getData()[i];
			input[3 * i] = axis.x;
			input[3 * i + 1] = axis.y;
			input[3 * i + 2] = axis.z;
		}
		return ByteUtil.doublesToBytes(input);
	}

}
