package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class WaitDialog extends JDialog{
		
	public WaitDialog(long waitMillis, Runnable after){
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Update");
		setLayout(new GridBagLayout());
		add(new JLabel("Updating database… Please wait"),new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		pack();
		Utilities.showDialog(this);
		try {
			Utilities.invokeAfter(waitMillis, ()->{
				dispose();
				after.run();
			});
		} catch (InterruptedException e) {
			dispose();
		}
	}
	
	public WaitDialog(long waitMillis) {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Update");
		setLayout(new GridBagLayout());
		add(new JLabel("Updating database… Please wait"),new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		pack();
		Utilities.showDialog(this);
		try {
			Thread.sleep(waitMillis);
			dispose();
		} catch (InterruptedException e) {
			dispose();
		}
	}
}
