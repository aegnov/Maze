package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class SolverImage
{
	private ImageIcon icon;
	private double x, y;
	private int width, height;

	public SolverImage( String filename, double x, double y, int width, int height )
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		icon = new ImageIcon( filename );
		Image scaledImg = scaleImage( icon.getImage() );
		icon.setImage( scaledImg );
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	public double getY()
	{
		return y;
	}

	public double getX()
	{
		return x;
	}

	public Image getImage()
	{
		return icon.getImage();
	}

	private Image scaleImage( Image srcImg )
	{
		//    	Graphics2D g2 = resizedImg.createGraphics();

//	    g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
//	    g2.drawImage(srcImg, 0, 0, w, h, null);
//	    g2.dispose();

    	return new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
	}
}