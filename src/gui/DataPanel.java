package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import classes.Database;
import classes.Vehicle;

public class DataPanel extends JScrollPane implements ActionListener {

	private List<ItemListener> itemListeners = new ArrayList<ItemListener>();

	private WrapLayout layout = new WrapLayout(WrapLayout.LEFT, 15, 10);

	static Dimension preferedImageSize = new Dimension(50, 50);

	public static void setPreferedImageSize(Dimension size) {
		DataPanel.preferedImageSize = size;
	}

	Database db = Database.getInstance();

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

	private void processIsEmpty() {
		boolean isEmpty = isEmpty();
		firePropertyChange("isEmpty", !isEmpty, isEmpty);
	}
	
	void processIsSelected() {
		boolean selected = isSelected();
		firePropertyChange("isSelected", !selected, selected);
	}
	
	public DataPanel() {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		super.setViewportView(content);
		// super.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		content.setLayout(layout);
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
