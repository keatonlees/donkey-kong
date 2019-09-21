package ca.hapke.gyro;

import com.raspoid.additionalcomponents.MPU6050;

import ca.hapke.util.RunKillThread;

/**
 * @author Mr. Hapke
 */
public class GyroDataUpdater {

	protected class UpdatingThread extends RunKillThread {
		private static final int UPDATE_FREQUENCY = 10;

		@Override
		public void run() {

			running = true;
			if (mpu == null) {
				mpu = new MPU6050();
				mpu.startUpdatingThread();
			}
			while (!kill) {
				updateFromRaspoid();
				try {
					Thread.sleep(UPDATE_FREQUENCY);
				} catch (InterruptedException e) {
					kill = true;
				}
			}
			try {
				mpu.stopUpdatingThread();
			} catch (InterruptedException e) {
			}
			mpu = null;
			running = false;
			kill = false;
		}
	}

	private MultiDimensionStatus status;
	private MPU6050 mpu;
	private UpdatingThread t;

	public GyroDataUpdater() {
		this(new MultiDimensionStatus("accelAngles", "accelAccelerations", "gyroAngles", "gyroAngularSpeeds",
				"filteredAngles"));
	}

	public GyroDataUpdater(MultiDimensionStatus status) {
		this.status = status;
	}

	public MultiDimensionStatus getStatus() {
		return status;
	}

	public void updateFromRaspoid() {
		if (mpu == null)
			return;
		double[] accelAngles = mpu.getAccelAngles();
		double[] accelAccelerations = mpu.getAccelAccelerations();
		double[] gyroAngles = mpu.getGyroAngles();
		double[] gyroAngularSpeeds = mpu.getGyroAngularSpeeds();
		double[] filteredAngles = mpu.getFilteredAngles();

		status.update(
				new double[][] { accelAngles, accelAccelerations, gyroAngles, gyroAngularSpeeds, filteredAngles });
	}

	public void start() {
		if (t == null || !t.isRunning()) {
			t = new UpdatingThread();
			t.start();
		}
	}
}