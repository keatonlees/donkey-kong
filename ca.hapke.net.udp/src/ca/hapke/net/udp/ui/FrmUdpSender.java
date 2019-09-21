package ca.hapke.net.udp.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.hapke.gyro.GyroDataUpdater;

/**
 * 
 * @author Mr. Hapke
 */
public class FrmUdpSender extends AccelGyroFrame {

	private static final long serialVersionUID = 1432629860045856616L;
	// private JTextField txtDestIp;
	private JTextField txtPort;
	private JLabel label;
	private JLabel lblPort;
	private JTextField txtInput;
	private JLabel lblResultT;
	private JLabel lblResult;
	protected int i = 0;
	// private JButton btnSendAG;
	// protected UpdatingThread updatingThread;
	private JRadioButton chkScrollValues;
	private JCheckBox chkRelaxValues;
	private JRadioButton chkRaspoidGyro;
	private JButton btnStartStop;

	private static final String _10_50 = "10.50.";
	private JTextField txtIp;
	private JComboBox<String> cmbHost;
	private JRadioButton radIp;
	private JRadioButton radHostname;
	private String destHost = _10_50;
	private int destPort = 8002;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					FrmUdpSender frame = new FrmUdpSender();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmUdpSender() {
		super(109, 166);

		// JLabel lblDestinationIp = new JLabel("Destination IP");
		// lblDestinationIp.setBounds(10, 11, 92, 14);
		// contentPane.add(lblDestinationIp);
		//
		// txtDestIp = new JTextField();
		// txtDestIp.setText(NetworkSettings.getIp());
		//
		// txtDestIp.setBounds(10, 36, 92, 20);
		// contentPane.add(txtDestIp);
		// txtDestIp.setColumns(10);
		radHostname = new JRadioButton("Hostname");
		radHostname.setHorizontalAlignment(SwingConstants.LEFT);
		radHostname.setBounds(6, 7, 109, 23);
		contentPane.add(radHostname);

		radIp = new JRadioButton("IP");
		radIp.setSelected(true);
		radIp.setBounds(6, 33, 109, 23);
		contentPane.add(radIp);

		txtIp = new JTextField();
		txtIp.setText(_10_50);
		txtIp.setBounds(121, 34, 127, 20);
		contentPane.add(txtIp);
		txtIp.setColumns(10);

		cmbHost = new JComboBox<String>();
		cmbHost.setEditable(true);
		List<String> hosts = new ArrayList<>();
		for (int i = 1; i <= 32; i++) {
			hosts.add("SEMI-R123-W0" + (i < 10 ? "0" : "") + i);
		}
		hosts.add("SEMI-SURF-W006");
		cmbHost.setModel(new DefaultComboBoxModel(hosts.toArray()));
		cmbHost.setBounds(121, 8, 127, 20);
		contentPane.add(cmbHost);

		ButtonGroup group = new ButtonGroup();
		group.add(radIp);
		group.add(radHostname);

		ChangeListener ipHostListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateIpHostUi();
			}

		};
		radIp.addChangeListener(ipHostListener);
		radHostname.addChangeListener(ipHostListener);

		updateIpHostUi();

		txtPort = new JTextField();
		txtPort.setText("" + destPort);
		txtPort.setBounds(319, 36, 86, 20);
		contentPane.add(txtPort);
		txtPort.setColumns(10);

		label = new JLabel(":");
		label.setBounds(309, 39, 16, 14);
		contentPane.add(label);

		lblPort = new JLabel("Port");
		lblPort.setBounds(319, 11, 86, 14);
		contentPane.add(lblPort);

		txtInput = new JTextField();
		txtInput.setBounds(20, 109, 294, 20);
		contentPane.add(txtInput);
		txtInput.setColumns(10);

		// JButton btnSend = new JButton("Send");
		// btnSend.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// String msg = txtInput.getText();
		// send(msg, null);
		// }
		// });
		// btnSend.setBounds(321, 108, 89, 23);
		// contentPane.add(btnSend);

		lblResultT = new JLabel("Result:");
		lblResultT.setHorizontalAlignment(SwingConstants.RIGHT);
		lblResultT.setBounds(10, 62, 58, 14);
		contentPane.add(lblResultT);

		lblResult = new JLabel("N/A");
		lblResult.setVerticalAlignment(SwingConstants.TOP);
		lblResult.setBounds(78, 62, 226, 31);
		contentPane.add(lblResult);

		btnStartStop = new JButton("Start");
		btnStartStop.addActionListener(new StartStopListener());
		btnStartStop.setBounds(478, 126, 141, 31);
		contentPane.add(btnStartStop);

		// btnSendAG = new JButton("Send");
		// btnSendAG.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent paramActionEvent) {
		// sendStatus();
		// }
		// });
		// btnSendAG.setBounds(478, 205, 141, 35);
		// contentPane.add(btnSendAG);

		// pnlGyroManual = new GyroManualPanel(this);
		// // new GyroMouseListener(pnlGyroManual);
		// pnlGyroManual.setBounds(478, 10, 100, 100);
		// contentPane.add(pnlGyroManual);

		chkScrollValues = new JRadioButton("Scroll Values");
		chkScrollValues.setBounds(474, 244, 145, 35);
		contentPane.add(chkScrollValues);

		chkRelaxValues = new JCheckBox("Relax Values");
		chkRelaxValues.setSelected(true);
		chkRelaxValues.setBounds(474, 283, 145, 35);
		contentPane.add(chkRelaxValues);

		JButton btnStartRaspoid = new JButton("Start Raspoid");
		btnStartRaspoid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread() {

					@Override
					public void run() {
						new GyroDataUpdater(status).start();
					}

				}.start();
			}
		});
		btnStartRaspoid.setBounds(478, 325, 141, 31);
		contentPane.add(btnStartRaspoid);

		chkRaspoidGyro = new JRadioButton("Raspoid Gyro");
		chkRaspoidGyro.setBounds(478, 362, 109, 23);
		contentPane.add(chkRaspoidGyro);

	}

	@Override
	protected MdsToUiAdapter getMdsAdapter() {
		return new MdsToUdpAdapter(txtFields, pnlGyroManual);
	}

	private void updateIpHostUi() {
		boolean ip = radIp.isSelected();
		cmbHost.setEnabled(!ip);
		txtIp.setEnabled(ip);
	}

	public String getIp() {
		if (radIp.isSelected())
			return txtIp.getText();
		else
			return cmbHost.getSelectedItem().toString();
	}

	// public void send(String msg, byte[] bs) {
	// try {
	// if (msg != null)
	// UdpUtil.send(destHost, destPort, msg);
	// else if (bs != null)
	// UdpUtil.send(destHost, destPort, bs, TransmitMode.AccelGyroData.mode);
	// lblResult.setText(i++ + "Success");
	// } catch (NumberFormatException e) {
	// lblResult.setText(i++ + "NFE");
	// } catch (IOException e) {
	// lblResult.setText(i++ + " IOE: " + e.getMessage());
	// }
	// }
	//
	// public void sendStatus() {
	// send(null, UdpUtil.statusToBytes(status.getStatus()));
	// }
	//
	// /**
	// * @author Mr. Hapke
	// */
	private class StartStopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			MdsToUdpAdapter adapter = (MdsToUdpAdapter) sender;
			if (btnStartStop.getText().equalsIgnoreCase("Start")) {
				String target = getIp();
				if (_10_50.equals(target)) {
					JOptionPane.showMessageDialog(rootPane, "Make sure you select a target first");
					return;
				}
				// updatingThread = new UpdatingThread();
				// updatingThread.start();
				adapter.setTarget(getIp(), Integer.parseInt(txtPort.getText()));
				btnStartStop.setText("Stop");
			} else {
				// updatingThread.kill();
				adapter.disableUdp();
				btnStartStop.setText("Start");
			}
		}
	}
	//
	// protected class UpdatingThread extends RunKillThread {
	// private static final int UPDATE_FREQUENCY = 10;
	//
	// @Override
	// public void run() {
	//
	// running = true;
	// while (!kill) {
	// if (chkRaspoidGyro.isSelected())
	// status.updateFromRaspoid();
	// sendStatus();
	// pokeAccelGyroBoxes();
	// try {
	// Thread.sleep(UPDATE_FREQUENCY);
	// } catch (InterruptedException e) {
	// kill = true;
	// }
	// }
	// running = false;
	// kill = false;
	// }
	// }

}
