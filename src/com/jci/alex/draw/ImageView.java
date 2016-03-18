package com.jci.alex.draw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageView extends JPanel
{
	Image img;

	public int width;
	public int height;
	

	
	public ImageView(String SOURCE)
	{
		try
		{
			
			img = ImageIO.read(new File(SOURCE));
			width = img.getWidth(null);
			height = img.getHeight(null);
			
		} catch (IOException e)
		{
			System.out.println("can not find image file in the specific place:"+SOURCE);
		}
		
		// System.out.println(width+ " "+height );
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (img != null)
		
		{
			g.drawImage(img, 0, 0, img.getWidth(null),
						img.getHeight(null), this);

		}
	}
	
}
