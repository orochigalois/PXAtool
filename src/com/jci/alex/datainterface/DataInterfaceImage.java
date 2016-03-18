package com.jci.alex.datainterface;

import java.util.Vector;

import javax.swing.tree.TreePath;


public class DataInterfaceImage
{
	//original information from the XML node directly, can not be changed during running time
	public TreePath tp;
	public String id;
	public String x;
	public String y;
	public String width;
	public String height;
	
	public String path;
	public String source;
	
	public Vector<ImageState> states;
	
	//user configuration or calculation, can be changed during running time
	public boolean visible;
	public boolean highlight;
	
	public String user_config_x;
	public String user_config_y;
	public String user_config_source;
	public String user_config_state;
	
	public String absolute_x;
	public String absolute_y;
	
	
	
	
	
	
	

	public DataInterfaceImage(TreePath TP,String ID, String X, String Y, String WIDTH,
			String HEIGHT,String PATH, String SOURCE, String ABSOLUTE_X,String ABSOLUTE_Y)
	
	{
		this.tp = TP;
		this.id = ID;
		this.x = X;
		this.y = Y;
		this.width = WIDTH;
		this.height = HEIGHT;
		this.path = PATH;
		this.source = SOURCE;
		
		this.states  = new Vector<ImageState>();

		this.visible = true;
		this.highlight = false;
		
		this.absolute_x=ABSOLUTE_X;
		this.absolute_y=ABSOLUTE_Y;
		
		
		
	}
}

