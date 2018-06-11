//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import interfaces.IVehicle;

public class DataPanel extends JScrollPane implements ActionListener {
	private static final long serialVersionUID = 1L;
	private WrapLayout layout = new WrapLayout(WrapLayout.LEFT, 15, 10);
	private DBConnect db = DBConnect.getConnection();
	private List<VehicleSelectButton> vehicleSelectButtons = new ArrayList<VehicleSelectButton>();
	private ButtonGroup group = new ButtonGroup();
	private JPanel content = new JPanel();

	static Dimension preferedImageSize = new Dimension(50, 50);
	public static void setPreferedImageSize(Dimension size) {
		DataPanel.preferedImageSize = size;
	}
	
	
	public void refresh() {
		for (VehicleSelectButton vS : vehicleSelectButtons) {
			vS.removeActionListener(this);
			group.remove(vS);
			content.remove(vS);
		}
		vehicleSelectButtons.clear();
		Collection<IVehicle> data = db.getVehicles();
		synchronized (data) {
			for (IVehicle v : data) {
				VehicleSelectButton vS = v.draw();
				vS.addActionListener(this);
				vehicleSelectButtons.add(vS);
			}
			for (VehicleSelectButton vS : vehicleSelectButtons) {
				group.add(vS);
				content.add(vS);
			}
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
		return (group.getSelection() != null);
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
		
		///deselecting a vehicleSelectButton
		addMouseListener(new MouseListener() {		
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 1 && event.getButton() == MouseEvent.BUTTON1) {
					clearSelection();
				}
			}
			@Override // unimplemented
			public void mouseEntered(MouseEvent arg0) {} public void mouseExited(MouseEvent arg0) {} public void mousePressed(MouseEvent arg0) {} public void mouseReleased(MouseEvent arg0) {}
		});
		
		// TODO: improve actions, meanwhile just resets the window components for each operation
		PropertyChangeListener refreshListner = (event)->{refresh();};
		for(String property:Arrays.asList("addVehicle", "buyVehicle", "restoreMemento"))
			db.addPropertyChangeListener(property,refreshListner);
		
		PropertyChangeListener clearListner = (event)->{clearSelection();};
		for(String property:Arrays.asList("resetDistances", "changeFlags"))
			db.addPropertyChangeListener(property,clearListner);		
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
	
	public void clearSelection() {
		for (VehicleSelectButton vS : vehicleSelectButtons) {
			vS.setToolTipText(vS.getVehicle().toString());
		}
		group.clearSelection();
		processIsSelected();
	}
}
