package gui.form;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.ConfirmationDialog;
import gui.ImageText;

public class ImageSelect extends JDialog {
	private JComboBox<ImageText> combo;
	private JFileChooser search;
	
	public ImageSelect(DefaultComboBoxModel<ImageText> model) {
		combo = new JComboBox<ImageText>(model);
		search = new JFileChooser();
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton cancelButton = new JButton("Cancel");
		JButton okButton = new JButton("OK");
		okButton.addActionListener((event)->{
			SwingUtilities.invokeLater(()->{
				ConfirmationDialog confirmation = new ConfirmationDialog("image selected");
				confirmation.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosing(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent arg0) {
						dispose();
					}
					
					@Override
					public void windowActivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				confirmation.setVisible(true);
				confirmation.setLocationRelativeTo(null);
				
			});
			firePropertyChange("okButton", null, getImagePath());
		});
		cancelButton.addActionListener((event)->{dispose();});
		setLayout(new BorderLayout());
		add(combo,BorderLayout.NORTH);
		add(search,BorderLayout.CENTER);
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);
		add(buttonPanel,BorderLayout.SOUTH);
	}
	
	private String getImagePath() {
		File selectedFile = search.getSelectedFile();
		if(selectedFile!=null) {
			return selectedFile.getAbsolutePath();
		}
		else {
			return combo.getSelectedItem().toString();
		}
	}
}
