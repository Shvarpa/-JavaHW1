package gui;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

class ImageTextRenderer extends JLabel implements ListCellRenderer<ImageAndText> {

	@Override
	public Component getListCellRendererComponent(JList list, ImageAndText item, int index, boolean selected,
			boolean focused) {

		if (item instanceof ImageAndText) {
			ImageAndText imageNtext = (ImageAndText) item;
			ImageIcon img = imageNtext.getImage();
			if (img != null) {
				setIcon(imageNtext.getImage());
			}
			setText(imageNtext.toString());
		}

		if (selected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		if (focused){
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setFont(list.getFont());
		
		return this;
	}

}
