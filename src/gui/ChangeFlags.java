package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class ChangeFlags extends JDialog {

	private DBConnect db = DBConnect.getConnection();
	private final JPanel contentPanel = new JPanel();
	private static Dimension preferdFlagsSize = new Dimension(120, 80);

	public ChangeFlags() {
		setTitle("Change Flags");

		getContentPane().setPreferredSize(new Dimension(300, 200));
		getContentPane().setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JComboBox<ImageText> combo = ComboBoxesCreator.createFlagsComboBox(preferdFlagsSize);
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");

		contentPanel.add(combo, BorderLayout.CENTER);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		okButton.addActionListener((event) -> {
			String flag = combo.getSelectedItem().toString();
			if (flag == null)
				return;
			db.changeFlags(flag);
			SwingUtilities.invokeLater(() -> {
				ConfirmationDialog confirmation = new ConfirmationDialog(
						"all sea vehicles flags changed to " + flag + " successfully!!");
				confirmation.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				confirmation.addWindowListener(new WindowListener() {

					@Override
					public void windowOpened(WindowEvent arg0) {
					}

					@Override
					public void windowIconified(WindowEvent arg0) {
					}

					@Override
					public void windowDeiconified(WindowEvent arg0) {
					}

					@Override
					public void windowDeactivated(WindowEvent arg0) {
					}

					@Override
					public void windowClosing(WindowEvent arg0) {
					}

					@Override
					public void windowClosed(WindowEvent arg0) {
						dispose();
					}

					@Override
					public void windowActivated(WindowEvent arg0) {
					}
				});
				confirmation.setLocationRelativeTo(null);
				confirmation.setVisible(true);
			});
		});
		cancelButton.addActionListener((event) -> {
			dispose();
		});

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getRootPane().setDefaultButton(okButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}

}
