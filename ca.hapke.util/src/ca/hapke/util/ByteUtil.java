package ca.hapke.util;

import java.nio.ByteBuffer;

/**
 * @author Mr. Hapke
 *
 */
public abstract class ByteUtil {

	public static byte[] doublesToBytes(double[] input) {
		byte[] output = new byte[8 * input.length];
		for (int i = 0; i < input.length; i++) {
			doubleToBytes(input[i], output, 8 * i);
		}
		return output;
	}

	public static void doubleToBytes(double d, byte[] output, int from) {
		long lng = Double.doubleToLongBits(d);
		for (int i = 0; i < 8; i++)
			output[from + i] = (byte) ((lng >> ((7 - i) * 8)) & 0xff);
	}

	public static double[] bytesToDouble(byte[] bytes) {
		int doubles = bytes.length / 8;
		double[] output = new double[doubles];

		ByteBuffer buffer = ByteBuffer.wrap(bytes);

		for (int i = 0; i < doubles; i++)
			output[i] = buffer.getDouble();

		return output;
	}

	/**
	 * TODO switch with Arrays.copySomething(from 1,length-1)?
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] removeFrontPadding(byte[] input, int prefixBytes) {
		byte[] output = new byte[Math.max(0, Math.min(input[1], input.length - prefixBytes))];

		for (int i = 0; i < output.length; i++) {
			output[i] = input[i + prefixBytes];
		}
		return output;
	}

}
