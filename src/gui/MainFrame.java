//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import interfaces.IVehicle;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel, centerPanel, totalDistancesPanel, mementoPanel;
	String baseTotalDistances = "Total distance driven:";
	JLabel totalDistancesLabel;
	private JButton addVehicleButton,buyVehicleButton,testDriveButton,resetDistancesButton,changeFlagsButton,currentDbButton, restoreMementoButton, saveMementoButton;
	private DataPanel dataPanel = new DataPanel();
	private JTextArea toStringTextArea;
	TestDrive testDriveWindow;
	private DBConnect db = DBConnect.getConnection();
	
	////making mainframe a singleton as per the assignment
	static volatile private MainFrame self = null;
	static public MainFrame getInstance() {
		if (self == null) {
			synchronized (DBConnect.class) {
				if (self == null) {
					self = new MainFrame();
				}
			}
		}
		return self;
	}
	
	private MainFrame() {
		///the main menu of the application, form which you can view the vehicle database real time (the DataPanel component)
		///and select an operation to do from the button menu.
		setTitle("Car Agency");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 350));
		contentPanel = new JPanel();
		setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel rightPanel = new JPanel();
		contentPanel.add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new GridLayout(7, 1, 0, 0));

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
		
		mementoPanel = new JPanel(new GridLayout(1,2,0,0));
		restoreMementoButton = new JButton("restore");
		saveMementoButton = new JButton("save");
		mementoPanel.add(restoreMementoButton);
		mementoPanel.add(saveMementoButton);
		
		rightPanel.add(addVehicleButton);
		rightPanel.add(buyVehicleButton);
		rightPanel.add(testDriveButton);
		rightPanel.add(resetDistancesButton);
		rightPanel.add(changeFlagsButton);
		rightPanel.add(currentDbButton);
		rightPanel.add(mementoPanel);

		// actions
		addVehicleButton.addActionListener((event) -> {
			Utilities.showDialog(new AddVehicle());
		});

		buyVehicleButton.addActionListener((event) -> {
			VehicleSelectButton vS = dataPanel.getVehicleSelectButton();
			if (vS == null)
				return;
			updateToString("preparing vehicle for purchase...");
			db.buyVehicle(vS.getVehicle());
		});

		testDriveButton.addActionListener((event)->{
			IVehicle currVehicle = dataPanel.getVehicleSelectButton().getVehicle();
			if (currVehicle == null)
				return;
			Utilities.showDialog(new TestDrive(currVehicle));
		});

		resetDistancesButton.addActionListener((event) -> {
			db.resetDistances();
		});
		
		changeFlagsButton.addActionListener((event)->{
			Utilities.showDialog(new ChangeFlags());
		});
		currentDbButton.addActionListener((event)->{
			Utilities.showDialog(new CurrentDBView());
		});
		restoreMementoButton.addActionListener((event)->{
			if(db.restoreMemento())
				updateToString("last save restored!");
		});
		saveMementoButton.addActionListener((event)->{
			db.saveMemento();
			updateToString("database saved!");
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
		centerPanel = new JPanel();
		totalDistancesLabel = new JLabel(baseTotalDistances);
		
		PropertyChangeListener distancesListener = (event)->{
			updateTotalDistances(db.getTotalDistances());
		};
		db.addPropertyChangeListener("restoreMemento",distancesListener);
		db.addPropertyChangeListener("testDriveVehicle",distancesListener);
		db.addPropertyChangeListener("resetDistances",distancesListener);
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(dataPanel,BorderLayout.CENTER);
		
		totalDistancesPanel = new JPanel(new GridBagLayout());
		totalDistancesPanel.setVisible(false);
		totalDistancesPanel.setBorder(BorderFactory.createEtchedBorder());
		totalDistancesPanel.add(totalDistancesLabel,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 5, 2, 5), 0, 0));
		
		centerPanel.add(totalDistancesPanel,BorderLayout.SOUTH);
		contentPanel.add(centerPanel, BorderLayout.CENTER);

		JPanel toStringPanel = new JPanel();
		toStringPanel.setLayout(new GridBagLayout());
		toStringPanel.setBorder(BorderFactory.createEtchedBorder());
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
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(db.hasDuringTransaction()) {
					JOptionPane.showMessageDialog(null, "there are vehicles during buy/test transaction, cant exit", "Close App",JOptionPane.ERROR_MESSAGE);
				}
				else
		           System.exit(0);
			}
			public void windowOpened(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowActivated(WindowEvent arg0) {}
		});
		
		/// Initial refresh does'nt account for the button creation.
		dataPanel.refresh();
	}
	
	private VehicleSelectButton currentVehicle = null;
	private PropertyChangeListener revalidateListener = (event) -> {updateToString();};
	private String defaultUpdateString = "try hovering over the vehicle...";
	private List<String> properties = Arrays.asList("revalidate", "changeFlags", "resetDistances", "testDriveVehicle");
	private void updateToString() {
		if(currentVehicle!=null) {
			for(String property:properties)
				currentVehicle.removePropertyChangeListener(property,revalidateListener);
		}
		VehicleSelectButton vS = dataPanel.getVehicleSelectButton();
		if(vS!=null) {
			currentVehicle = vS;
			for(String property:properties)
				currentVehicle.addPropertyChangeListener(property,revalidateListener);
			toStringTextArea.setText(vS.getVehicle()!=null ? "current vehicle: " + currentVehicle.getVehicle().toString() : defaultUpdateString);
		}
		else {
			toStringTextArea.setText(defaultUpdateString);
		}
	}
	
	private void updateToString(String text) {
		toStringTextArea.setText(text);
	}
	
	private void updateTotalDistances(double distance) {
		totalDistancesPanel.setVisible(true);
		totalDistancesLabel.setText(baseTotalDistances + Double.toString(distance));
	}
}
