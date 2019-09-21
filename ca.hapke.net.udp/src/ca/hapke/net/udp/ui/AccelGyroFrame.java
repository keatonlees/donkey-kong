package ca.hapke.net.udp.ui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ca.hapke.gyro.MultiDimensionStatus;
import ca.hapke.util.AngleFormatter;

/**
 * 
 * @author Mr. Hapke
 */
public abstract class AccelGyroFrame extends JFrame {

	private static final long serialVersionUID = 8381316893140758524L;

	protected GyroManualPanel pnlGyroManual;
	protected MultiDimensionStatus status;
	protected ColouredTextField[][] txtFields;

	protected AngleFormatter af;

	protected JPanel contentPane;
	protected MdsToUiAdapter sender;

	protected abstract MdsToUiAdapter getMdsAdapter();

	public AccelGyroFrame(int xBase, int yBase) {
		af = new AngleFormatter();
		this.status = new MultiDimensionStatus();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 655, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		int w = 107;
		int h = 32;
		int xSp = 21;
		int ySp = 10;

		JLabel lblX = new JLabel("x");
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setBounds(xBase, yBase - 30, w, h);
		contentPane.add(lblX);

		JLabel lblY = new JLabel("y");
		lblY.setHorizontalAlignment(SwingConstants.CENTER);
		lblY.setBounds(xBase + xSp + w, yBase - 30, w, h);
		contentPane.add(lblY);

		JLabel lblZ = new JLabel("z");
		lblZ.setHorizontalAlignment(SwingConstants.CENTER);
		lblZ.setBounds(xBase + 2 * (xSp + w), yBase - 30, w, h);
		contentPane.add(lblZ);

		int dims = status.getDimensions();
		txtFields = new ColouredTextField[3][dims];

		for (int j = 0; j < dims; j++) {
			JLabel lblGyro = new JLabel(status.getNames()[j]);
			lblGyro.setBounds(10, yBase + j * (ySp + h), 92, 26);
			contentPane.add(lblGyro);
		}

		ColourScaleParameters[] scales = { new ColourScaleParameters(1, 0.1, 0.5),
				new ColourScaleParameters(0, 0.1, 0.5), new ColourScaleParameters(0, 10, 180),
				new ColourScaleParameters(180, 10, 180), new ColourScaleParameters(0, 10, 50),
				new ColourScaleParameters(0, 10, 360) };
		int[][] paramList = { { 3, 3, 2 }, { 1, 1, 0 }, { 2, 2, 2 }, { 4, 4, 4 }, { 5, 5, 5 } };
		for (int i = 0; i < 3; i++) {
			// i is to the right
			if (txtFields[i] == null)
				txtFields[i] = new ColouredTextField[3];
			for (int j = 0; j < dims; j++) {
				ColourScaleParameters params = scales[paramList[j][i]];
				// j is down the page
				txtFields[i][j] = new ColouredTextField(params, af);
				JTextField txtBox = txtFields[i][j];
				txtBox.setFont(new Font("monospaced", Font.PLAIN, 11));
				txtBox.setText("");
				txtBox.setBounds(xBase + i * (xSp + w), yBase + j * (ySp + h), w, h);
				contentPane.add(txtBox);

				// TODO wut?
				txtBox.setColumns(10);
			}
		}

		pnlGyroManual = new GyroManualPanel(this);
		pnlGyroManual.setBounds(570, 65, 100, 100);
		contentPane.add(pnlGyroManual);

		sender = getMdsAdapter();
		status.addListener(sender);

	}
}