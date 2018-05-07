package gui;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ImageTextRenderer extends JLabel implements ListCellRenderer<ImageText> {
	
	private boolean textVisable=true;
	
	public ImageTextRenderer() {
	       setOpaque(true);
           setVerticalAlignment(CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList list, ImageText item, int index, boolean isSelected,
			boolean cellHasFocus) {

		ImageIcon img = item.getImage();
		setIcon(item.getImage());
		if (textVisable) {
			setText(item.toString());
		}


        if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
        
		setFont(list.getFont());
		
		return this;
	}
	
	public void setTextVisable(boolean bool) {
		textVisable=bool;
	}

}
