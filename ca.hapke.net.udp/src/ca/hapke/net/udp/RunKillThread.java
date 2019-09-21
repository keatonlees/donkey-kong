package ca.hapke.net.udp;

/**
 * @author Mr. Hapke
 */
public abstract class RunKillThread extends Thread {

	protected boolean kill = false;
	protected boolean running = false;

	public void kill() {
		this.kill = true;
	}

	public boolean isRunning() {
		return running;
	}
}