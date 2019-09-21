package ca.hapke.net.udp.ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import ca.hapke.net.udp.IUdpServerListener;
import ca.hapke.net.udp.ReceiveMode;
import ca.hapke.net.udp.RunKillThread;
import ca.hapke.net.udp.UdpServerThread;

/**
 * @author Mr. Hapke
 */
public class FrmUdpReceiver extends AccelGyroFrame {

	private static final long serialVersionUID = -3471270600310979442L;
	private JTextField txtPort;
	private ReceiveMode mode = ReceiveMode.Stopped;
	private JButton btnStartStop;
	protected UdpServerThread udpReceiver;
	protected Queue<String> messages = new ConcurrentLinkedQueue<>();
	private UpdateUiThread displayThread;
	private JTextArea txtOutput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AccelGyroFrame frame = new FrmUdpReceiver();
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
	public FrmUdpReceiver() {
		super(109, 109);
		setTitle("UDP Receiver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblInputPort = new JLabel("Input Port:");
		lblInputPort.setBounds(22, 24, 134, 29);
		contentPane.add(lblInputPort);

		txtPort = new JTextField();
		txtPort.setText("8002");
		txtPort.setBounds(145, 21, 206, 35);
		contentPane.add(txtPort);
		txtPort.setColumns(10);

		btnStartStop = new JButton("Start");
		btnStartStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (mode == ReceiveMode.Stopped) {

					udpReceiver = new UdpServerThread(Integer.parseInt(txtPort.getText()), status);
					udpReceiver.add(new IUdpServerListener() {
						@Override
						public void serverOnline() {
							btnStartStop.setText("Stop");
							mode = ReceiveMode.Running;
						}

						@Override
						public void serverOffline() {
							btnStartStop.setText("Start");
							mode = ReceiveMode.Stopped;
						}

						@Override
						public void accelGyroUpdated() {
						}

						@Override
						public void sentenceReceived(String sentence) {
							messages.add(sentence);
						}

						@Override
						public void serverAbort(String msg) {
							messages.add("ABORT:" + msg);
						}
					});
					udpReceiver.start();

				} else {
					if (udpReceiver != null)
						udpReceiver.kill();
				}
			}
		});
		btnStartStop.setBounds(361, 20, 155, 37);
		contentPane.add(btnStartStop);

		JScrollPane sclOutput = new JScrollPane();
		sclOutput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sclOutput.setBounds(10, 350, 880, 300);
		contentPane.add(sclOutput);

		txtOutput = new JTextArea();
		txtOutput.setFont(new Font("monospaced", Font.PLAIN, 10));
		sclOutput.setViewportView(txtOutput);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtOutput.setText("");
			}
		});
		btnClear.setBounds(581, 173, 89, 23);
		contentPane.add(btnClear);

		displayThread = new UpdateUiThread();
		displayThread.start();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (udpReceiver != null)
			udpReceiver.kill();
		if (displayThread != null)
			displayThread.kill();
	}

	private class AddMessageRunnable implements Runnable {

		private String msg;

		public AddMessageRunnable(String msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			// txtOutput.setText(msg + "\n" + txtOutput.getText());
			txtOutput.setText(txtOutput.getText() + "\n" + msg);
		}
	}

	private class UpdateUiThread extends RunKillThread {

		@Override
		public void run() {
			running = true;
			while (!kill) {

				String msg = "";
				while (!messages.isEmpty()) {
					if (msg.length() > 0)
						msg = msg + "\n";

					msg = msg + messages.poll();
				}
				if (msg != null && msg.length() > 0) {
					SwingUtilities.invokeLater(new AddMessageRunnable(msg));
				}
				// pokeAccelGyroBoxes();

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					break;
				}
			}
			running = false;
			kill = false;
		}

	}

	@Override
	protected MdsToUiAdapter getMdsAdapter() {
		return new MdsToUiAdapter(txtFields, pnlGyroManual);
	}
}
