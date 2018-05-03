package gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import classes.Database;
import classes.Jeep;
import classes.Vehicle;


public class DataPanel extends JScrollPane implements ActionListener{
	
	private WrapLayout layout= new WrapLayout(WrapLayout.LEFT, 15, 10);

	
	private static Dimension preferedImageSize=new Dimension(50, 50);
	public static void setPreferedImageSize(Dimension size) {DataPanel.preferedImageSize=size;}	
	
//	private boolean vehicleSelected=false;
	private Database db = Database.getInstance();
	
	private List<VehicleSelectButton> vehicleSelectButtons= new ArrayList<VehicleSelectButton>();
	private ButtonGroup vehicleSelectButtonsGroup = new ButtonGroup();
	
	private JPanel content= new JPanel();
	
	
	public void refresh() {
		for(VehicleSelectButton vS:vehicleSelectButtons) {
			vehicleSelectButtonsGroup.remove(vS);
			content.remove(vS);
		}
		vehicleSelectButtons.clear();
		for(Vehicle v:db.getVehicles()) {
			VehicleSelectButton vS=new VehicleSelectButton(v,DataPanel.preferedImageSize);
//			vS.addFocusListener(this);
			vehicleSelectButtons.add(vS);
		}
		for(VehicleSelectButton vS:vehicleSelectButtons) {
			vehicleSelectButtonsGroup.add(vS);
			content.add(vS);
		}
		revalidate();
	}
	
	public DataPanel() {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		super.setViewportView(content);
//		super.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		content.setLayout(layout);
		refresh();
		JButton btn = new JButton("aaaaaa");
		btn.addActionListener(this);
		content.add(btn);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		refresh();
		System.out.println("ref");
	}
 
//	private void setVehicleSelected(boolean status) {
//		vehicleSelected=status;
//	}
//	
//	public Vehicle getSelectedVehicle() {
//		if(vehicleSelected) {
//			for(VehicleSelectButton vS: vehicleSelectButtons) {
//				if (vS.isSelected()) {return vS.getVehicle();}
//			}
//		}
//		return null;
//	}
}
