//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ConfirmationDialog extends JDialog{

	private final JPanel contentPanel = new JPanel();
	private JLabel textLabel;
	JButton okButton;
	
	public ConfirmationDialog(String text) {
		
		contentPanel.setLayout(new BorderLayout());
		textLabel=new JLabel(text);
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(textLabel,BorderLayout.CENTER);
		
		getContentPane().setLayout(new BorderLayout(100,0));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			
			JButton okButton = new JButton("OK");
			getRootPane().setDefaultButton(okButton);
			okButton.addActionListener((event)->{dispose();});
			buttonPanel.add(okButton);
			
		}
		pack();
	}


}
