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
	
	private static URL getURL(String path) {
		if(path==null) return null;
		return ImageOpener.class.getResource(path);
	}
	private static File getFile(String path) {
		if(path ==null) return null;
		return new File(path);
	}
	
	public static Image createImage(String path) {
		try {
			URL url = getURL(path);
			if(url!=null)
				return ImageIO.read(url);
			File file = getFile(path);
			if(file!=null)
				return ImageIO.read(file);
			return null;
		} catch (IOException e) {
			System.err.println("couldent find image in path : '" + path + "'");
			return null;
		}
	}
	
	
	public static Image createImage(String path, Dimension d) {
		return scaleImage(createImage(path), d);
	}

	public static Image scaleImage(Image image, Dimension d) {
		if (image != null) {
			return image.getScaledInstance(d.width, d.height, java.awt.Image.SCALE_SMOOTH); 
		}
		return null;
	}
		
	public static ImageIcon createImageIcon(String path) {
		try {
			URL url = getURL(path);
			if(url!=null)
				return new ImageIcon(url);
			File file = getFile(path);
			if(file!=null)
				return new ImageIcon(ImageIO.read(file));
			return null;
		} catch (IOException e) {
			System.err.println("couldent find image in path : '" + path + "'");
			return null;
		}	}
		
	public static ImageIcon createImageIcon(String path, Dimension d) {
		return scaleImageIcon(createImageIcon(path), d,Image.SCALE_SMOOTH);
	}
	
	public static ImageIcon scaleImageIcon(ImageIcon icon, Dimension d) {
		return scaleImageIcon(icon, d, Image.SCALE_SMOOTH);
	}
	
	public static ImageIcon scaleImageIcon(ImageIcon icon, Dimension d, int hint) {
		if (icon != null) {
			Image image = icon.getImage(); // transform it
			Image convertedImage = image.getScaledInstance(d.width, d.height, hint); // scale it the																						// smooth way
			icon = new ImageIcon(convertedImage); // transform it back
		}
		return icon;
	}
	
	public static ImageIcon createGIF(String path, float ratio) {
		ImageIcon gif = createImageIcon(path);
		return scaleImageIcon(gif, new Dimension((int)(gif.getIconWidth()*ratio),(int)(gif.getIconHeight()*ratio)) , Image.SCALE_DEFAULT);
	}
}
