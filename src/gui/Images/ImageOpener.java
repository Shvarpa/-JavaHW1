//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui.Images;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageOpener {

	public static ImageIcon createImageIcon(String path) {
		if (path == null) {
			return null;
		}
		URL url = ImageOpener.class.getResource(path);
		if (url != null) {
			return new ImageIcon(url);
		}
		try {
			return new ImageIcon(ImageIO.read(new File(path)));
		} catch (IOException e) {
			System.err.println("couldent find image in path : '" + path + "'");
			return null;
		}
	}

	public static ImageIcon scaleImg(ImageIcon icon, Dimension d) {
		return scaleImg(icon, d.width, d.height);
	}

	public static ImageIcon scaleImg(ImageIcon icon, int width, int height) {
		if (icon != null) {
			Image image = icon.getImage(); // transform it
			Image convertedImage = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the
																										// smooth way
			icon = new ImageIcon(convertedImage); // transform it back
		}
		return icon;
	}
}
