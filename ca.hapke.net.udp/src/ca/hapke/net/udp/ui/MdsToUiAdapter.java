package ca.hapke.net.udp.ui;

import javax.vecmath.Point3d;

import ca.hapke.gyro.IGyroListener;
import ca.hapke.gyro.MultiDimensionStatus;

/**
 * 
 * @author Mr. Hapke
 *
 */
public class MdsToUiAdapter implements IGyroListener {

	protected ColouredTextField[][] txtFields;
	private GyroManualPanel pnlGyroManual;

	public void setPnlGyroManual(GyroManualPanel pnlGyroManual) {
		this.pnlGyroManual = pnlGyroManual;
	}

	public MdsToUiAdapter(ColouredTextField[][] txtFields, GyroManualPanel pnlGyroManual) {
		this.txtFields = txtFields;
		this.pnlGyroManual = pnlGyroManual;
	}

	@Override
	public void updateOccurred(MultiDimensionStatus status) {
		if (status == null || txtFields == null)
			return;

		for (int set = 0; set < status.getData().length; set++) {
			Point3d val = status.getData()[set];
			txtFields[0][set].setValue(val.x);
			txtFields[1][set].setValue(val.y);
			txtFields[2][set].setValue(val.z);
		}

		if (pnlGyroManual != null)
			pnlGyroManual.repaint();
	}

}
