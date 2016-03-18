package com.jci.alex.datainterface;

import javax.swing.tree.TreePath;

public class DataInterfaceLabel
{
	//original information from the XML node directly, can not be changed during running time
	public TreePath tp;
	public String id;
	public String x;
	public String y;
	public String width;
	public String height;
		
	
	public String horizontalAlignment;
	public String verticalAlignment;
	public String font_pixelSize;
	public String color;
	public String sampleText;
	
	
	//user configuration or calculation, can be changed during running time
	public boolean visible;
	public boolean highlight;
	public String user_config_x;
	public String user_config_y;

	public String absolute_x;
	public String absolute_y;
		
	public String textFromExcel;
	
	

	public DataInterfaceLabel(TreePath TP,String ID, String X, String Y, String WIDTH,
			String HEIGHT, String ABSOLUTE_X,String ABSOLUTE_Y,
			String HORIZONTAL_ALIGNMENT,String VERTICAL_ALIGNMENT,String FONT_SIZE, String FONT_COLOR, String SAMPLE_TEXT)
	{
		this.tp = TP;
		this.id = ID;
		this.x = X;
		this.y = Y;
		this.width = WIDTH;
		this.height = HEIGHT;


		this.visible = true;
		this.highlight = false;
		
		this.absolute_x=ABSOLUTE_X;
		this.absolute_y=ABSOLUTE_Y;
		
		this.horizontalAlignment=HORIZONTAL_ALIGNMENT;
		this.verticalAlignment=VERTICAL_ALIGNMENT;
		this.font_pixelSize=FONT_SIZE;
		this.color=FONT_COLOR;
		this.sampleText=SAMPLE_TEXT;
		


	}

}
