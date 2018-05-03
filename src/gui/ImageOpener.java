package gui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageOpener {
	protected static ImageIcon createImageIcon(String path) {
		if (path == null) {
			return null;
		}
		java.net.URL imgURL = ImageAndText.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	static ImageIcon scaleImg(ImageIcon icon, Dimension d) {
		return scaleImg(icon, d.width, d.height);
	}

	static ImageIcon scaleImg(ImageIcon icon, int width, int height) {
		if (icon != null) {
			Image image = icon.getImage(); // transform it
			Image convertedImage = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the
																										// smooth way
			icon = new ImageIcon(convertedImage); // transform it back
		}
		return icon;
	}
}
