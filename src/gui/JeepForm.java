package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Insets;

public class JeepForm extends JPanel {
	private JTextField modelText;
	private JTextField speedText;
	private JTextField avgFuelConsumptionText;
	private JTextField avgMotorLifespanText;

	/**
	 * Create the panel.
	 */
	public JeepForm() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Model:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		modelText = new JTextField();
		GridBagConstraints gbc_modelText = new GridBagConstraints();
		gbc_modelText.insets = new Insets(0, 0, 5, 0);
		gbc_modelText.fill = GridBagConstraints.HORIZONTAL;
		gbc_modelText.gridx = 2;
		gbc_modelText.gridy = 0;
		add(modelText, gbc_modelText);
		modelText.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Speed:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		speedText = new JTextField();
		GridBagConstraints gbc_speedText = new GridBagConstraints();
		gbc_speedText.insets = new Insets(0, 0, 5, 0);
		gbc_speedText.fill = GridBagConstraints.HORIZONTAL;
		gbc_speedText.gridx = 2;
		gbc_speedText.gridy = 1;
		add(speedText, gbc_speedText);
		speedText.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Average fuel consumption::");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		avgFuelConsumptionText = new JTextField();
		GridBagConstraints gbc_avgFuelConsumptionText = new GridBagConstraints();
		gbc_avgFuelConsumptionText.insets = new Insets(0, 0, 5, 0);
		gbc_avgFuelConsumptionText.fill = GridBagConstraints.HORIZONTAL;
		gbc_avgFuelConsumptionText.gridx = 2;
		gbc_avgFuelConsumptionText.gridy = 2;
		add(avgFuelConsumptionText, gbc_avgFuelConsumptionText);
		avgFuelConsumptionText.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Average motor lifespan:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridwidth = 2;
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		avgMotorLifespanText = new JTextField();
		GridBagConstraints gbc_avgMotorLifespanText = new GridBagConstraints();
		gbc_avgMotorLifespanText.fill = GridBagConstraints.HORIZONTAL;
		gbc_avgMotorLifespanText.gridx = 2;
		gbc_avgMotorLifespanText.gridy = 3;
		add(avgMotorLifespanText, gbc_avgMotorLifespanText);
		avgMotorLifespanText.setColumns(10);
	}

}
