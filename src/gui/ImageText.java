//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;


import java.awt.Dimension;
import javax.swing.ImageIcon;
import gui.Images.ImageOpener;


public class ImageText{
	
		
	private ImageIcon img;
	private String text;
	
	public ImageText() {
		img=null;
		setText("");	
	}
	
	public ImageText(String imagePath,String text) {
		setImg(imagePath);
		setText(text);
	}
	
	public ImageText(String imagePath,String text, Dimension d) {
		setImg(imagePath);
		setText(text);
		if(d!=null) scaleImg(d);
	}
	
	public void scaleImg(Dimension d)	{
		setImg(ImageOpener.scaleImageIcon(this.getImage(), d));
	}
	
	public void setImg(ImageIcon img) {this.img=img;}
	public void setImg(String imagePath) {this.img=ImageOpener.createImageIcon(imagePath);}
	public void setText(String s) {this.text=s;}
	public ImageIcon getImage() {return img;}
	public String toString() {return text;}
	public String getText() {return toString();}
}