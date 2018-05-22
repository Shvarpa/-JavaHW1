//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import classes.Vehicle;

public class DataPanel extends JScrollPane implements ActionListener {

	private WrapLayout layout = new WrapLayout(WrapLayout.LEFT, 15, 10);

	static Dimension preferedImageSize = new Dimension(50, 50);

	public static void setPreferedImageSize(Dimension size) {
		DataPanel.preferedImageSize = size;
	}

	private DBConnect db = DBConnect.getConnection();

	List<VehicleSelectButton> vehicleSelectButtons = new ArrayList<VehicleSelectButton>();
	ButtonGroup group = new ButtonGroup();

	JPanel content = new JPanel();

	public void refresh() {
		for (VehicleSelectButton vS : vehicleSelectButtons) {
			vS.removeActionListener(this);
			group.remove(vS);
			content.remove(vS);
		}
		vehicleSelectButtons.clear();
		for (Vehicle v : db.getVehicles()) {
			VehicleSelectButton vS = new VehicleSelectButton(v, DataPanel.preferedImageSize);
			vS.addActionListener(this);
			vehicleSelectButtons.add(vS);
		}
		for (VehicleSelectButton vS : vehicleSelectButtons) {
			group.add(vS);
			content.add(vS);
		}
		processIsEmpty();
		processIsSelected();
		processHasSeaVehicles();
		repaint();
		revalidate();
	}
	

	
	public boolean isEmpty() {
		return vehicleSelectButtons.isEmpty();
	}

	public boolean isSelected() {
		for (VehicleSelectButton vS : vehicleSelectButtons) {
			if (vS.isSelected()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasSeaVehicles() {
		return db.hasSeaVehicles();
	}
	
	private void processIsEmpty() {
		boolean isEmpty = isEmpty();
		firePropertyChange("isEmpty", !isEmpty, isEmpty);
	}
	
	void processIsSelected() {
		boolean selected = isSelected();
		firePropertyChange("isSelected", !selected, selected);
	}
	
	private void processHasSeaVehicles() {
		boolean hasSeaVehicles=hasSeaVehicles();
		firePropertyChange("hasSeaVehicles", !hasSeaVehicles, hasSeaVehicles);
	}
	
	public DataPanel() {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		super.setViewportView(content);
		// super.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		content.setLayout(layout);
		
		// TODO: improve actions, meanwhile just resets the window components for each operation
		db.addPropertyChangeListener("addVehicle",(event)->{refresh();});
		db.addPropertyChangeListener("buyVehicle",(event)->{refresh();});
		db.addPropertyChangeListener("testDriveVehicle",(event)->{refresh();});
		db.addPropertyChangeListener("resetDistances",(event)->{refresh();});
		db.addPropertyChangeListener("changeFlags",(event)->{refresh();});
		refresh();
	}

	public void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	public VehicleSelectButton getVehicleSelectButton() {
		for (VehicleSelectButton vS : vehicleSelectButtons) {
			if (vS.isSelected()) {
				return vS;
			}
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		processIsSelected();
	}

}
