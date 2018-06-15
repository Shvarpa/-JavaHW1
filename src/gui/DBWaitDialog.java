//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import gui.Images.ImageOpener;

public class DBWaitDialog extends JDialog{
	private static final long serialVersionUID = 1L;	
	/// a dialog that will dispose itself after the given time
	public DBWaitDialog(long waitMillis) throws InterruptedException {
		showSelf(waitMillis, ()->{});
	}
	
	/// a dialog that will run code and dispose itself after the given time, will dispose on interrupt
	public DBWaitDialog(long waitMillis, Runnable code) throws InterruptedException {
		showSelf(waitMillis, code);
	}
	
	private void showSelf(long waitMillis, Runnable code) throws InterruptedException{
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//		setIconImage();
		setTitle("Update");
		setLayout(new GridBagLayout());
//		ImageIcon gif = ImageOpener.createGIF("\\Icons\\loading.gif",(float) 0.5);
		ImageIcon gif = ImageOpener.createImageIcon("\\Icons\\loading.gif");
		if(gif != null) {
			JLabel image = new JLabel();
			image.setIcon(gif);
			add(image,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		}
		add(new JLabel("Updating database… Please wait"),new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		pack();
		setVisible(true);
		try {
			Thread.sleep(waitMillis);
			dispose();
			code.run();
		} catch (InterruptedException e) {
			dispose();
			throw e;
		}
	}
}
