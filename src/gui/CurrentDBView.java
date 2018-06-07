package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import classes.Vehicle;
import interfaces.IVehicle;

public class CurrentDBView extends JDialog{
	private static final long serialVersionUID = 1L;
	private DBConnect db = DBConnect.getConnection();
	private List<VehicleSelectButton> vehicleSelectButtons = new ArrayList<VehicleSelectButton>();
	
	
	public void refresh() {
		for (VehicleSelectButton vS : vehicleSelectButtons) {
			content.remove(vS);
		}
		vehicleSelectButtons.clear();
		Collection<IVehicle> data = DBConnect.getConnection().getVehicles();
		synchronized (data) {
			for (IVehicle v:data) {
				VehicleSelectButton vS = v.draw();
				vehicleSelectButtons.add(vS);
			}
			for (VehicleSelectButton vS : vehicleSelectButtons) {
				content.add(vS);
			}
		}
		content.repaint();
		content.revalidate();
		pack();
	}
	
	private JPanel content;
	public CurrentDBView() {
		setLayout(new BorderLayout());
		content = new JPanel(new WrapLayout(WrapLayout.LEFT, 15, 10));
		JScrollPane scroll = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll,BorderLayout.CENTER);
		setTitle("Database");
		addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent arg0) {
			}
			
			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				refresh();
			}
		});
		refresh();
		pack();
	}
}
