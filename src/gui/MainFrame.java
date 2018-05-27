//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import classes.Vehicle;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JButton addVehicleButton,buyVehicleButton,testDriveButton,resetDistancesButton,changeFlagsButton,currentDbButton;
	private DataPanel dataPanel = new DataPanel();
	private JTextArea toStringTextArea;
	TestDrive testDriveWindow;
	private DBConnect db = DBConnect.getConnection();

	public MainFrame() {
		setTitle("Car Agency");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 350));
		contentPanel = new JPanel();
		setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel rightPanel = new JPanel();
		contentPanel.add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new GridLayout(6, 1, 0, 0));

		/// buttons
		addVehicleButton = new JButton("Add Vehicle");
		addVehicleButton.setEnabled(true);

		buyVehicleButton = new JButton("Buy Vehicle");
		buyVehicleButton.setEnabled(false);

		testDriveButton = new JButton("Test Drive");
		testDriveButton.setEnabled(false);

		resetDistancesButton = new JButton("Reset Distances");
		resetDistancesButton.setEnabled(false);

		changeFlagsButton = new JButton("Change Flags");
		changeFlagsButton.setEnabled(false);
		
		currentDbButton = new JButton("Current Database view");

		rightPanel.add(addVehicleButton);
		rightPanel.add(buyVehicleButton);
		rightPanel.add(testDriveButton);
		rightPanel.add(resetDistancesButton);
		rightPanel.add(changeFlagsButton);
		rightPanel.add(currentDbButton);

		// actions
		addVehicleButton.addActionListener((event) -> {
			SwingUtilities.invokeLater(() -> {Utilities.showDialog(new AddVehicle());});
		});

		buyVehicleButton.addActionListener((event) -> {
			VehicleSelectButton vS = dataPanel.getVehicleSelectButton();
			if (vS == null)
				return;
			updateToString("preparing vehicle for purchase...");
			db.new buyVehicleThread(vS.getVehicle()){
				@Override
				protected void done() {
					try {
						switch(get()) {
						case RETRY:
							JOptionPane.showMessageDialog(null,DBConnect.duringTransactionMessege);
						default:
							return;
						}
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			}.execute();
		});

		testDriveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Vehicle currVehicle = dataPanel.getVehicleSelectButton().getVehicle();
				if (currVehicle == null)
					return;
				SwingUtilities.invokeLater(() -> {Utilities.showDialog(new TestDrive(currVehicle));});
			}
		});

		resetDistancesButton.addActionListener((event) -> {
			db.resetDistances();
			JOptionPane.showMessageDialog(null,"all vehicles distances were reset!");
		});
		
		changeFlagsButton.addActionListener((event)->{
			SwingUtilities.invokeLater(()->{
				Utilities.showDialog(new ChangeFlags());
			});
		});
		currentDbButton.addActionListener((event)->{
			SwingUtilities.invokeLater(()->{
				Utilities.showDialog(new CurrentDBView());
			});
		});
		dataPanel.addPropertyChangeListener("isEmpty", (event) -> {
			if (event.getNewValue().equals(true)) {
				resetDistancesButton.setEnabled(false);
				changeFlagsButton.setEnabled(false);
				buyVehicleButton.setEnabled(false);
				testDriveButton.setEnabled(false);
				changeFlagsButton.setEnabled(false);
			} else {
				resetDistancesButton.setEnabled(true);
			}
		});
		dataPanel.addPropertyChangeListener("isSelected", (event) -> {
			if (event.getNewValue().equals(true)) {
				buyVehicleButton.setEnabled(true);
				testDriveButton.setEnabled(true);
			} else {
				buyVehicleButton.setEnabled(false);
				testDriveButton.setEnabled(false);
			}
			updateToString();
		});
		dataPanel.addPropertyChangeListener("hasSeaVehicles", (event)->{
			if (event.getNewValue().equals(true)) {
				changeFlagsButton.setEnabled(true);
			} else {
				changeFlagsButton.setEnabled(false);
			}
		});

		contentPanel.add(dataPanel, BorderLayout.CENTER);

		JPanel toStringPanel = new JPanel();
		toStringPanel.setLayout(new GridBagLayout());

		// labels
		toStringTextArea = new JTextArea("");
		toStringTextArea.setOpaque(false);
		toStringTextArea.setEditable(false);
		toStringTextArea.setFocusable(false);
		toStringTextArea.setBorder(null);
		toStringTextArea.setLineWrap(true);
		toStringTextArea.setWrapStyleWord(true);
		toStringTextArea.setForeground(Color.GRAY);

		toStringPanel.add(toStringTextArea,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 5, 2, 5), 0, 0));
		contentPanel.add(toStringPanel, BorderLayout.SOUTH);

		/// Initial refresh does'nt account for the button creation.
		dataPanel.refresh();
	}

	private void updateToString() {
		VehicleSelectButton vS = dataPanel.getVehicleSelectButton();
		toStringTextArea.setText((vS == null ? "try hovering over the vehicle..." : "current vehicle: " + vS.getVehicle().toString()));
	}
	
	private void updateToString(String text) {
		toStringTextArea.setText(text);
	}
}
