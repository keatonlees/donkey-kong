package ca.hapke.gyro;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;

/**
 * 
 * @author Mr. Hapke
 *
 */
public class MultiDimensionStatus {
	private Point3d[] data;
	private String[] names;
	private List<IGyroListener> listeners = new ArrayList<>();

	/**
	 * Defaults to 5 dimensions: accelAngles, accelAccelerations, gyroAngles,
	 * gyroAngularSpeeds, filteredAngles
	 */
	public MultiDimensionStatus() {
		this("accelAngles", "accelAccelerations", "gyroAngles", "gyroAngularSpeeds", "filteredAngles");
	}

	public MultiDimensionStatus(String... names) {
		this.names = names;

		int dims = names.length;
		data = new Point3d[dims];

		double[][] defaults = new double[dims][3];
		for (int i = 0; i < defaults.length; i++) {
			defaults[i][0] = 0;
			defaults[i][1] = 0;
			defaults[i][2] = 1;
		}
		update(defaults);
	}

	/**
	 * Format: value[dataset][x=0, y=1, z=2]
	 */
	public void update(double[][] values) {
		for (int i = 0; i < values.length && i < data.length; i++) {
			Point3d axis = data[i];
			if (axis == null)
				axis = data[i] = new Point3d();

			axis.x = values[i][0];
			axis.y = values[i][1];
			axis.z = values[i][2];
		}
		notifyListeners();
	}

	public void update(double[] values) {
		for (int set = 0; set < data.length; set++) {
			int x = set * 3;
			int y = set * 3 + 1;
			int z = set * 3 + 2;

			if (z >= values.length)
				return;

			data[set].x = values[x];
			data[set].y = values[y];
			data[set].z = values[z];
			notifyListeners();
		}

	}

	private void notifyListeners() {
		for (IGyroListener l : listeners) {
			l.updateOccurred(this);
		}
	}

	public boolean addListener(IGyroListener l) {
		return listeners.add(l);
	}

	public Point3d[] getData() {
		return data;
	}

	public String[] getNames() {
		return names;
	}

	public int getDimensions() {
		return data.length;
	}

}
