package gui;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;


public class MainFrame extends JFrame{
	public MainFrame(String title) {
		super(title);
		
		setLayout(new FlowLayout());
		
		DataTable dataTable = new DataTable();
		
		Container c = getContentPane();
		
		c.add(dataTable,FlowLayout.LEFT);
	}
	
}
