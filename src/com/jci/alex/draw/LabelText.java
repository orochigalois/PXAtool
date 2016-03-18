package com.jci.alex.draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.io.*;
import java.net.MalformedURLException;
import java.awt.GraphicsEnvironment;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jci.alex.Constant;
import com.jci.alex.ReadExcel;

public class LabelText
{
	
	String stringExcel = null;
	TextAttribute iterator;
	
	int fontSize = 0;
	String fontFamily;
	Font f = null;
	
	String sContent;
	String sSampleText;
	
	Font font = null;
	
	// this.buildFont();
	// iterator
	
	public Font getFont(String pixelsize)
	{
		float size = 0.0f;
		size = this.fontsize(pixelsize);
		//System.out.println(size);
		try
		{
			font = Font.createFont(Font.TRUETYPE_FONT,
					new File(Constant.workingPath
							+ "\\assets\\fonts\\DS_Bold_V5300.ttf"));
			font = font.deriveFont(size);// SIZE
			font = font.deriveFont(Font.BOLD);// Style
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (FontFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Font error", "System message",
					JOptionPane.INFORMATION_MESSAGE);
			font = new Font("Verdana", Font.BOLD, 18);
		}
		return font;
	}
	
	public String getText()
	{
		String txt = sContent;
		if (txt == null || txt.length() == 0)
		{
			txt = sSampleText;
		}
		return txt;
	}
	
	public Color textColor(String colorstr)
	{
		Color tcolor = null;
		
		return tcolor;
	}
	
	public float fontsize(String pixelsize)
	{
		int sizeint = 0;
		float sizefloat = 0.0f;
		if (pixelsize.equals(""))
		{
//			System.out.println("pixelsize value is" + pixelsize
//					+ ". The default value is 24f");
			sizefloat = 24f;
		}
		else
		{
			if(Constant.isNumeric(pixelsize))
			{
				sizeint = Integer.parseInt(pixelsize);
				if (23 == sizeint)
					sizefloat = 11f;
				else if (27 == sizeint)
					sizefloat = 13f;
				else if (31 == sizeint)
					sizefloat = 15f;
				else if (36 == sizeint)
					sizefloat = 17.5f;
				else
					sizefloat = 24f; // set the default value for test				
			}
			else
			{
				
			}
			
		}
		
		return sizefloat;
	}
	
}
