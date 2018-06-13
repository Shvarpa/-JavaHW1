package gui;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import classes.Input;

class FormParameter<T>{
	enum Parameter{model, seats, speed, wheels, avgMotorLifespan, avgFuelConsumption, energySource, withWindDiraction, vehicleUse, roadType, licence, energyRating, flag;}
	private Parameter param;
	private JLabel key;
	private JComponent val;
	private Class<T> returnType;
	private boolean initialized;
	FormParameter(Parameter param,Class<T> c, JLabel key,JComponent val) {
		this.param = param;
		this.returnType = c;
		initialize(key,val);
	}
	FormParameter(Parameter param,Class<T> c) {
		this.param = param;
		this.returnType = c;
	}
	void initialize(JLabel key,JComponent val){
		if (!initialized) {
			initialized = true;
			this.key = key;
			this.val = val;
		}
	}
	
	JLabel getKey(){
		return this.key;
	}
	JComponent getVal() {
		return this.val;
	}
	
	Parameter getParameter() {
		return param;
	}
	private String getText() {
		if (val instanceof JTextField)	return ((JTextField)val).getText().toString();
		else if (val instanceof JComboBox<?>) return ((JComboBox<?>)val).getSelectedItem().toString();
		else return null;
	}
	private String getTarget() {
		return key.getText().toString();
	}
		
	private Integer getInt() throws NumberFormatException {
		String target = getTarget();
		if (val instanceof JTextField) return (int) Input.parsePositive(target, Input.parseInt(target, ((JTextField) val).getText().toString()));
		return null;
	}
	
	private Float getFloat() throws NumberFormatException {
		String target = getTarget();
		if (val instanceof JTextField) return (float) Input.parsePositive(target, Input.parseFloat(target, ((JTextField) val).getText().toString()));
		return null;
	}
	
	private Double getDouble() throws NumberFormatException {
		String target = getTarget();
		if (val instanceof JTextField) return (double) Input.parsePositive(target, Input.parseDouble(target, ((JTextField) val).getText()));
		else return null;
	}
		
	private Boolean getBoolean() {
		if (val instanceof JRadioButton) return ((JRadioButton)val).isSelected();
		String text = getText();
		if (text!=null) return Input.parseBoolean(getTarget(), text);
		else return null;
	}
	private void setText(Object text) {
		if (val instanceof JTextField) ((JTextField) val).setText(text.toString());
	}
	private void setSelectedItem(Object item) {
		if(val instanceof JComboBox<?>) ((JComboBox<?>) val).setSelectedItem(item);
	}
	private void setEnabled(boolean b) { 
		val.setEnabled(b);
	}
	void setDefault(Object text) {
		setText(text);
		setSelectedItem(text);
		setEnabled(false);
	}
	void restoreDefault() {
		setText("");
		setEnabled(true);
	}
	@SuppressWarnings("unchecked")
	T get() {
		String ret = returnType.getSimpleName();
		if(ret.equals(String.class.getSimpleName()))
			return (T)getText();
		else if(ret.equals(Integer.class.getSimpleName()))
			return (T)getInt();
		else if (ret.equals(Float.class.getSimpleName()))
			return (T)getFloat();
		else if (ret.equals(Double.class.getSimpleName()))
			return (T)getDouble();
		else if (ret.equals(Boolean.class.getSimpleName()))
			return (T)getBoolean();
		return null;
	}
	
	boolean initialized() {
		return initialized;
	}
	
}
