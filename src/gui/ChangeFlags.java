package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ChangeFlags extends JDialog {
	
	private DBConnect db = DBConnect.getConnection();
	private final JPanel contentPanel = new JPanel();
	private static Dimension preferdFlagsSize= new Dimension(120, 80);
	
	public ChangeFlags() {
		setTitle("Change Flags");
		
		getContentPane().setPreferredSize(new Dimension(300,200));
		getContentPane().setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		FlagsComboBox combo = new FlagsComboBox(ChangeFlags.preferdFlagsSize);
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		
		contentPanel.add(combo, BorderLayout.CENTER);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		okButton.addActionListener((event)->{	
		});
		cancelButton.addActionListener((event)->{dispose();});
		
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getRootPane().setDefaultButton(okButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}
	
	public static void main(String[] args) {
		ChangeFlags window = new ChangeFlags();
		window.setVisible(true);
		window.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		window.setLocationRelativeTo(null);
	}
}
