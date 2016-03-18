package com.jci.alex.draw;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import java.io.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.lang.*;
import java.util.*;
import java.awt.Graphics2D;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.*;

import com.jci.alex.*;
import com.jci.alex.datainterface.DataInterfaceContainer;
import com.jci.alex.datainterface.DataInterfaceGroup;
import com.jci.alex.datainterface.DataInterfaceImage;
import com.jci.alex.datainterface.DataInterfaceImageIndex;
import com.jci.alex.datainterface.DataInterfaceLabel;
import com.jci.alex.datainterface.DataInterfacePanel;
import com.jci.alex.draw.ImageView;

public class RenderPanel extends JPanel
{
	private int panelWidth;
	private int panelHeight;

	JPanel content = new JPanel();
	ImageView image;
	JLabel label;

	BufferedImage bi;
	Graphics2D gi;

	public RenderPanel()
	{
		this.draw();
	}

	public void draw()
	{
		String labelText;
		content.removeAll();
		content.updateUI();
		content.revalidate();
		content.repaint();

		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_OVER,
				1.0F);

		if (Constant.allNodesForRendering.size() > 0)
		{
			//Step1: Prepare panel
			if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
					.size() > 0)
			{

				DataInterfacePanel diPanel = (DataInterfacePanel) Constant.allNodesForRendering
						.elementAt(Constant.whichPanel).elementAt(0);

				if (diPanel.width == null)
					panelWidth = Constant.screenWidth;
				else
					panelWidth = Constant.string2integer(diPanel.width);

				if (diPanel.height == null)
					panelHeight = Constant.screenHeight;
				else
					panelHeight = Constant.string2integer(diPanel.height);
				content.setBounds(0, 0, panelWidth, panelHeight);
				content.setOpaque(false);

//				if (diPanel.highlight)
//					content.setBorder(BorderFactory.createLineBorder(
//							Color.yellow, 3));
//				else
//					content.setBorder(null);

				if (panelWidth > 0 && panelHeight > 0)
				{
					bi = new BufferedImage(panelWidth, panelHeight,
							BufferedImage.TYPE_INT_ARGB);
					gi = bi.createGraphics();
				}

			}

			//Step2: Handle highlight object first, so that this object will be
			// painted on the top layer
			for (int i = Constant.allNodesForRendering.elementAt(
					Constant.whichPanel).size() - 1; i > 0; i--)
			{

				if (Constant.allNodesForRendering
						.elementAt(Constant.whichPanel).elementAt(i) instanceof DataInterfaceImage)
				{
					DataInterfaceImage diImage = (DataInterfaceImage) Constant.allNodesForRendering
							.elementAt(Constant.whichPanel).elementAt(i);
					if (diImage.highlight)
					{

						String fullPath = "";
						if (diImage.user_config_source == null)

						{
							fullPath = diImage.path + "\\"
									+ diImage.source.replace('/', '\\');
						}

						else
						{
							fullPath = diImage.path
									+ "\\"
									+ diImage.user_config_source.replace('/',
											'\\');

						}
						fullPath = FilenameUtils.normalize(fullPath);
						image = new ImageView(fullPath);

						int x=0,y=0;
						
						if (diImage.user_config_x == null)
						{
							x=Integer.parseInt(diImage.absolute_x);
						}
						else
						{
							x=Integer.parseInt(diImage.user_config_x)+
									Integer.parseInt(diImage.absolute_x)-
									Integer.parseInt(diImage.x);
						}
						
						if (diImage.user_config_y == null)
						{
							y=Integer.parseInt(diImage.absolute_y);
						}
						else
						{
							y=Integer.parseInt(diImage.user_config_y)+
									Integer.parseInt(diImage.absolute_y)-
									Integer.parseInt(diImage.y);
						}
							
						
						image.setBounds(x,
								y,
								image.width, image.height);
						
						
						image.setOpaque(false);
						image.setBorder(BorderFactory.createLineBorder(
								Color.yellow, 3));
						content.add(image);
						
						try
						{
							gi.setComposite(ac);

							gi.drawImage(
									ImageIO.read(new File(fullPath)),
									Integer.parseInt(diImage.absolute_x),
									Integer.parseInt(diImage.absolute_y),
									image.width, image.height, null);

						}
						catch (IOException e)
						{

//							JOptionPane.showMessageDialog(null, e.toString(),
//									null, JOptionPane.WARNING_MESSAGE);
						}
						
					}
				}
				else if (Constant.allNodesForRendering.elementAt(
						Constant.whichPanel).elementAt(i) instanceof DataInterfaceLabel)
				{
					DataInterfaceLabel diLabel = (DataInterfaceLabel) Constant.allNodesForRendering
							.elementAt(Constant.whichPanel).elementAt(i);
					if (diLabel.highlight)
					{
						if (diLabel.textFromExcel != null)
							labelText = diLabel.textFromExcel;
						else if (null != diLabel.sampleText)
							labelText = diLabel.sampleText;
						else
							labelText = "no text";

						if (diLabel.horizontalAlignment == "HorizontalAlignment.Center")
						{
							label = new JLabel(labelText, JLabel.CENTER);
						}
						else if (diLabel.horizontalAlignment == "HorizontalAlignment.Right")
						{
							label = new JLabel(labelText, JLabel.RIGHT);
						}
						else
						{
							label = new JLabel(labelText, JLabel.LEFT);
						}
						label.setBorder(BorderFactory.createEmptyBorder(1, 1,
								1, 1));

						label.setFont(new LabelText()
								.getFont(diLabel.font_pixelSize));

						label.setForeground(Color.decode(diLabel.color));
						
						int x=0,y=0;
						
						if (diLabel.user_config_x == null)
						{
							x=Integer.parseInt(diLabel.absolute_x);
						}
						else
						{
							x=Integer.parseInt(diLabel.user_config_x)+
									Integer.parseInt(diLabel.absolute_x)-
									Integer.parseInt(diLabel.x);
						}
						
						if (diLabel.user_config_y == null)
						{
							y=Integer.parseInt(diLabel.absolute_y);
						}
						else
						{
							y=Integer.parseInt(diLabel.user_config_y)+
									Integer.parseInt(diLabel.absolute_y)-
									Integer.parseInt(diLabel.y);
						}
						

						label.setBounds(
								x,
								y,
								Constant.string2integer(diLabel.width),
								Constant.string2integer(diLabel.height));
						
						//alex added for width=0
						if(Constant.string2integer(diLabel.width)==0)
							label.setBounds(
									Constant.string2integer(diLabel.absolute_x),
									Constant.string2integer(diLabel.absolute_y),
									500,
									Constant.string2integer(diLabel.height));
						
						
						label.setOpaque(false);
						label.setBorder(BorderFactory.createLineBorder(
								Color.yellow, 3));
						content.add(label);

					}
				}

				
				else if (Constant.allNodesForRendering.elementAt(
						Constant.whichPanel).elementAt(i) instanceof DataInterfaceContainer)
				{
					DataInterfaceContainer diContainer = (DataInterfaceContainer) Constant.allNodesForRendering
							.elementAt(Constant.whichPanel).elementAt(i);

					if (diContainer.highlight)
					{
						JPanel containerPanel = new JPanel();
						containerPanel
								.setBounds(
										Constant.string2integer(diContainer.absolute_x),
										Constant.string2integer(diContainer.absolute_y),
										Constant.string2integer(diContainer.width),
										Constant.string2integer(diContainer.height));
						containerPanel.setOpaque(false);
						containerPanel.setBorder(BorderFactory
								.createLineBorder(Color.yellow, 3));
						content.add(containerPanel);
					}

				}
				else if (Constant.allNodesForRendering.elementAt(
						Constant.whichPanel).elementAt(i) instanceof DataInterfaceGroup)
				{
					DataInterfaceGroup diGroup = (DataInterfaceGroup) Constant.allNodesForRendering
							.elementAt(Constant.whichPanel).elementAt(i);

					if (diGroup.highlight)
					{
						JPanel groupPanel = new JPanel();
						groupPanel.setBounds(
								Constant.string2integer(diGroup.absolute_x),
								Constant.string2integer(diGroup.absolute_y),
								Constant.string2integer(diGroup.width),
								Constant.string2integer(diGroup.height));
						groupPanel.setOpaque(false);
						groupPanel.setBorder(BorderFactory.createLineBorder(
								Color.yellow, 3));
						content.add(groupPanel);
					}

				}

			}

			//Step3:Draw other objects except highlight one
			for (int i = Constant.allNodesForRendering.elementAt(
					Constant.whichPanel).size() - 1; i > 0; i--)
			{

				if (Constant.allNodesForRendering
						.elementAt(Constant.whichPanel).elementAt(i) instanceof DataInterfaceImage)
				{

					DataInterfaceImage diImage = (DataInterfaceImage) Constant.allNodesForRendering
							.elementAt(Constant.whichPanel).elementAt(i);

					if (!diImage.highlight && diImage.visible)
					{

						String fullPath = "";
						if (diImage.user_config_source == null)

						{
							fullPath = diImage.path + "\\"
									+ diImage.source.replace('/', '\\');
						}

						else
						{
							fullPath = diImage.path
									+ "\\"
									+ diImage.user_config_source.replace('/',
											'\\');

						}
						fullPath = FilenameUtils.normalize(fullPath);
						image = new ImageView(fullPath);

						
						int x=0,y=0;
						
						if (diImage.user_config_x == null)
						{
							x=Integer.parseInt(diImage.absolute_x);
						}
						else
						{
							x=Integer.parseInt(diImage.user_config_x)+
									Integer.parseInt(diImage.absolute_x)-
									Integer.parseInt(diImage.x);
						}
						
						if (diImage.user_config_y == null)
						{
							y=Integer.parseInt(diImage.absolute_y);
						}
						else
						{
							y=Integer.parseInt(diImage.user_config_y)+
									Integer.parseInt(diImage.absolute_y)-
									Integer.parseInt(diImage.y);
						}
						
						
						image.setBounds(x,
								y,
								image.width, image.height);
						image.setOpaque(false);

						content.add(image);
						try
						{
							gi.setComposite(ac);

							gi.drawImage(
									ImageIO.read(new File(fullPath)),
									Integer.parseInt(diImage.absolute_x),
									Integer.parseInt(diImage.absolute_y),
									image.width, image.height, null);

						}
						catch (IOException e)
						{

//							JOptionPane.showMessageDialog(null, e.toString(),
//									null, JOptionPane.WARNING_MESSAGE);
						}

					}

				}
				else if (Constant.allNodesForRendering.elementAt(
						Constant.whichPanel).elementAt(i) instanceof DataInterfaceLabel)
				{
					DataInterfaceLabel diLabel = (DataInterfaceLabel) Constant.allNodesForRendering
							.elementAt(Constant.whichPanel).elementAt(i);

					if (!diLabel.highlight && diLabel.visible)
					{

						// Long Chen add
						if (diLabel.textFromExcel != null)
							labelText = diLabel.textFromExcel;
						else if (null != diLabel.sampleText)
							labelText = diLabel.sampleText;
						else
							labelText = "no text";
						// Long add end

						if (diLabel.horizontalAlignment == "HorizontalAlignment.Center")
						{
							label = new JLabel(labelText, JLabel.CENTER);
						}
						else if (diLabel.horizontalAlignment == "HorizontalAlignment.Right")
						{
							label = new JLabel(labelText, JLabel.RIGHT);
						}
						else
						{
							label = new JLabel(labelText, JLabel.LEFT);
						}
						label.setBorder(BorderFactory.createEmptyBorder(1, 1,
								1, 1));

						label.setFont(new LabelText()
								.getFont(diLabel.font_pixelSize));

						label.setForeground(Color.decode(diLabel.color));
						
						
						int x=0,y=0;
						
						if (diLabel.user_config_x == null)
						{
							x=Integer.parseInt(diLabel.absolute_x);
						}
						else
						{
							x=Integer.parseInt(diLabel.user_config_x)+
									Integer.parseInt(diLabel.absolute_x)-
									Integer.parseInt(diLabel.x);
						}
						
						if (diLabel.user_config_y == null)
						{
							y=Integer.parseInt(diLabel.absolute_y);
						}
						else
						{
							y=Integer.parseInt(diLabel.user_config_y)+
									Integer.parseInt(diLabel.absolute_y)-
									Integer.parseInt(diLabel.y);
						}
						
						

						label.setBounds(
								x,
								y,
								Constant.string2integer(diLabel.width),
								Constant.string2integer(diLabel.height));
						label.setOpaque(false);

						content.add(label);
						if(gi!=null)
						{
						gi.setFont(new LabelText()
								.getFont(diLabel.font_pixelSize));
						gi.setColor(new Color(0xffffff));

						gi.drawString(
								labelText,
								Constant.string2integer(diLabel.absolute_x)
										+ Constant
												.string2integer(diLabel.width)
										/ 2 - labelText.length() * 7 / 2,
								Constant.string2integer(diLabel.absolute_y)
										+ Constant
												.string2integer(diLabel.height)
										/ 2);
						}
					}

				}
				else
				{

				}
			}

		}
		content.setLayout(null);
		content.setOpaque(false);

		add(content);
		setLayout(null);
		repaint();

	}

	public void exportPNG(String fileName)
	{
		try
		{
			ImageIO.write(bi, "png", new File(fileName));
		}
		catch (Exception e)
		{
		}
	}

	public int getPNGwidth()
	{
		return panelWidth;
	}

	public int getPNGheight()
	{
		return panelHeight;
	}
}
