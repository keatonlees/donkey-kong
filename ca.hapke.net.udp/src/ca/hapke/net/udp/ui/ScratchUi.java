package ca.hapke.net.udp.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Mr. Hapke
 */
public class ScratchUi extends JFrame {

	private static final String _10_50 = "10.50.";
	private JPanel contentPane;
	private JTextField txtIp;
	private JComboBox<String> cmbHost;
	private JRadioButton radIp;
	private JRadioButton radHostname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ScratchUi frame = new ScratchUi();
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
	public ScratchUi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startAction();
			}
		});
		btnStart.setBounds(6, 61, 89, 23);
		contentPane.add(btnStart);

		ChangeListener ipHostListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateIpHostUi();
			}

		};
		radIp.addChangeListener(ipHostListener);
		radHostname.addChangeListener(ipHostListener);

		updateIpHostUi();
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

	private void startAction() {
		String target = getIp();
		if (_10_50.equals(target))
			target = "N/A";
		System.out.println(target);
	}
}
