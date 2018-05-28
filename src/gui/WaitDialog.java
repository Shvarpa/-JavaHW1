//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class WaitDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	/// a dialog that will dispose itself after the given time
	
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
