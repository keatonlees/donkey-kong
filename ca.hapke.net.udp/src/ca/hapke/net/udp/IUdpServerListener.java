/**
 * 
 */
package ca.hapke.net.udp;

/**
 * @author Mr. Hapke
 */
public interface IUdpServerListener {

	public void serverOnline();

	public void serverOffline();

	public void accelGyroUpdated();

	public void sentenceReceived(String sentence);

	public void serverAbort(String msg);

}
