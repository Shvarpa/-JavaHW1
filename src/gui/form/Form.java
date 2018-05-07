package gui.form;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import classes.Vehicle;

public abstract class Form extends JPanel {
	abstract Vehicle createVehicle();
	
	protected List<String> parameters;
	protected List<JLabel> labels = new ArrayList<JLabel>();
	protected List<JComponent> components = new ArrayList<JComponent>();
	protected GridBagLayout gridBagLayout;
	private int currentRow=0;
	
	
	private static int textColumns = 10;
	
	protected Form() {
		gridBagLayout=new GridBagLayout();
		setLayout(gridBagLayout);
	}
	
	protected Form(List<String> sources) {
		gridBagLayout=new GridBagLayout();
		setLayout(gridBagLayout);
		for(String s:sources) {
			addComponent(s,new JTextField(textColumns));
		}
	}
		
	protected void addComponent(String source,JComponent component) {
		GridBagConstraints gbc;
		
		labels.add(new JLabel(source));
		gbc = new GridBagConstraints();
		gbc.gridy=currentRow;
		gbc.gridx=0;
		gbc.ipadx=5;
		gbc.anchor=GridBagConstraints.EAST;
		add(labels.get(currentRow),gbc);
		
		components.add(component);
		gbc = new GridBagConstraints();
		gbc.gridy=currentRow;
		gbc.gridx=1;
		gbc.ipadx=5;
		gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		add(components.get(currentRow),gbc);
		
		currentRow++;
	}
	
	protected JComponent getComponent(String source) {
		return components.get(parameters.indexOf(source));
	}
	
	protected String getInput(String source) {
		JComponent curr = getComponent(source);
		if (curr instanceof JTextField) {
			return ((JTextField)curr).getText();
		}
		return null;
	}
}
