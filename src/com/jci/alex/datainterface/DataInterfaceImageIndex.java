package com.jci.alex.datainterface;

import javax.swing.tree.TreePath;
/***
 * This class is here only because of the need to draw rectangle for highlight function
 * @author X
 *
 */
public class DataInterfaceImageIndex
{
	public TreePath tp;
	
	public boolean visible;
	
	public boolean highlight;
	public String x;
	public String y;
	public String width;
	public String height;
	
	
	public DataInterfaceImageIndex(String aa, String bb, String cc, String dd,TreePath zz)
	{
		x = aa;
		y = bb;
		width = cc;
		height=dd;
		tp=zz;
	
		visible=true;
	}
}
