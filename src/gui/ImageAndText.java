package gui;


import java.awt.Dimension;

import javax.swing.ImageIcon;

import gui.Images.ImageOpener;


class ImageAndText{
	
		
	private ImageIcon img;
	private String text;
	
	public ImageAndText() {
		img=null;
		setText("");	
	}
	
	public ImageAndText(String path,String text) {
		setImg(path);
		setText(text);
	}
	
	public ImageAndText(String path,String text, Dimension d) {
		setImg(path);
		setText(text);
		scaleImg(d);
	}
	
	public void scaleImg(Dimension d)	{
		setImg(ImageOpener.scaleImg(this.getImage(), d));
	}
	
	public void scaleImg(int width,int height)	{
		setImg(ImageOpener.scaleImg(this.getImage(), width, height));
	}
	
	public void setImg(ImageIcon img) {this.img=img;}
	public void setImg(String path) {this.img=ImageOpener.createImageIcon(path);}
	public void setText(String s) {this.text=s;}
	public ImageIcon getImage() {return img;}
	public String toString() {return text;}
	public String getText() {return toString();}
}