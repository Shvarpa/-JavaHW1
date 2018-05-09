//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui.form;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import classes.Vehicle;

public abstract class Form extends JPanel {
	
	public abstract Vehicle createVehicle();
	
	protected static Dimension preferredImageSize= new Dimension(60, 45);
		
	protected Map<String,JComponent> dict = new Hashtable<String,JComponent>();
	
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
		dict.put(source, component);
		GridBagConstraints gbc;
		
		gbc = new GridBagConstraints();
		gbc.gridy=currentRow;
		gbc.gridx=0;
		gbc.weightx=0;
		gbc.weighty=0;
		gbc.ipadx=5;
		gbc.anchor=GridBagConstraints.EAST;
		add(new JLabel(source),gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridy=currentRow;
		gbc.gridx=1;
		gbc.ipadx=5;
		gbc.weightx=1;
		gbc.weighty=0;
		gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		add(component,gbc);
		
		currentRow++;
	}
	
	protected JComponent getComponent(String source) {
		return dict.get(source);
	}
	
	protected String getInput(String source) {
		JComponent curr = getComponent(source);
		if (curr instanceof JTextField) {
			return ((JTextField)curr).getText();
		}
		return null;
	}
}
