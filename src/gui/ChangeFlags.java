//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ChangeFlags extends JDialog {	
	private static final long serialVersionUID = 1L;
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

		JComboBox<ImageText> combo = Utilities.createFlagsComboBox(preferdFlagsSize);
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");

		contentPanel.add(combo, BorderLayout.CENTER);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		okButton.addActionListener((event) -> {
			String flag = combo.getSelectedItem().toString();
			if (flag == null)
				return;
			if (!db.hasSeaVehicles()) {
				Utilities.showMessageDialog("all sea vehicles were bought, cant change flags");
				dispose();
				return;
			}
			db.changeFlags(flag);
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
