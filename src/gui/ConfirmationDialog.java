//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
		setResizable(false);
		contentPanel.setLayout(new GridBagLayout());
		textLabel=new JLabel(text);
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 25, 25);
		contentPanel.add(textLabel,gbc);
		
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
