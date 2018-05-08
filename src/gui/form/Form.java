package gui.form;

import java.awt.Dimension;
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
	
	public abstract Vehicle createVehicle();
	
	protected static Dimension preferredImageSize= new Dimension(60, 45);
	
	protected List<String> parameters = new ArrayList<String>();
	protected List<JLabel> labels = new ArrayList<JLabel>();
	protected List<JComponent> components = new ArrayList<JComponent>();
	protected GridBagLayout gridBagLayout;
	private int currentRow=0;
	
	
	protected static int textColumns = 10;
	
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
		parameters.add(source);		
		GridBagConstraints gbc;
		
		labels.add(new JLabel(source));
		gbc = new GridBagConstraints();
		gbc.gridy=currentRow;
		gbc.gridx=0;
		gbc.weightx=0;
		gbc.weighty=0;
		gbc.ipadx=5;
		gbc.anchor=GridBagConstraints.EAST;
		add(labels.get(currentRow),gbc);
		
		components.add(component);
		gbc = new GridBagConstraints();
		gbc.gridy=currentRow;
		gbc.gridx=1;
		gbc.ipadx=5;
		gbc.weightx=1;
		gbc.weighty=0;
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
