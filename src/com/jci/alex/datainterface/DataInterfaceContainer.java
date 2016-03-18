package com.jci.alex.datainterface;

import javax.swing.tree.TreePath;


/***
 * This class is here only because of the need to draw rectangle for highlight function
 * @author X
 *
 */
public class DataInterfaceContainer
{
	//original information from the XML node directly, can not be changed during running time
	public TreePath tp;
	public String id;
	public String x;
	public String y;
	public String width;
	public String height;
	
	
	//user configuration or calculation, can be changed during running time
	public boolean visible;
	public boolean highlight;
	public String user_config_x;
	public String user_config_y;
	public String absolute_x;
	public String absolute_y;
	
	
	public DataInterfaceContainer(TreePath TP,String ID, String X, String Y, String WIDTH,String HEIGHT,String ABSOLUTE_X,String ABSOLUTE_Y)
	{
		this.tp=TP;
		this.id=ID;
		this.x=X;
		this.y=Y;
		this.width=WIDTH;
		this.height=HEIGHT;
		
		
		this.visible=true;
		this.highlight=false;
		
		this.absolute_x=ABSOLUTE_X;
		this.absolute_x=ABSOLUTE_Y;

	}
}
