package gui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

class ImageAndText{
	protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ImageAndText.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
                return null;
        }
	}
		
	private ImageIcon img;
	private String text;
	
	public ImageAndText(String path,String text) {
		this.img=createImageIcon(path);
		this.text=text;
	}
	
	void scaleImg(Dimension d) {
		scaleImg(d.width,d.height);
	}

	
	void scaleImg(int width,int height) {
		Image image = this.img.getImage(); // transform it 
		Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		this.img = new ImageIcon(newimg);  // transform it back
	}
	
	public ImageIcon getImage() {return img;}
	public String toString() {return text;}
}