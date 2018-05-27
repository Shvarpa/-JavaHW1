package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class WaitDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	public WaitDialog(long waitMillis) {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Update");
		setLayout(new GridBagLayout());
		add(new JLabel("Updating database… Please wait"),new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		pack();
		setVisible(true);
		try {
			Thread.sleep(waitMillis);
			dispose();
		} catch (InterruptedException e) {
			dispose();
		}
	}
}
