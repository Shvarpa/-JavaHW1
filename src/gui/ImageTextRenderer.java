//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ImageTextRenderer extends JLabel implements ListCellRenderer<ImageText> {
	private static final long serialVersionUID = 1L;
	private boolean textVisable=true;
	
	public ImageTextRenderer() {
		super();
		setOpaque(true);
		setVerticalAlignment(CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList list, ImageText item, int index, boolean isSelected,
			boolean cellHasFocus) {
		
		setIcon(item.getImage());
		if (textVisable) {
			setText(item.toString());
		}

        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
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
