package gui;

import javax.swing.ImageIcon;

public class ImageOpener {
	protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ImageAndText.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
                return null;
        }
	}
}
