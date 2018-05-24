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
		setTitle("Update");
		setLayout(new GridBagLayout());
		add(new JLabel("Updating database… Please wait"),new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		pack();
		Utilities.showDialog(this);
		Utilities.invokeAfter(waitMillis, ()->{
			dispose();
			after.run();
		});
	}
}
