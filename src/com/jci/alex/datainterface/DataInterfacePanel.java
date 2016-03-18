package com.jci.alex.datainterface;

import javax.swing.tree.TreePath;

public class DataInterfacePanel
{
	// original information from the XML node directly, can not be changed
	// during running time
	public TreePath tp;
	public String id;

	public String width;
	public String height;

	// user configuration or calculation, can be changed during running time
	public boolean visible;
	public boolean highlight;

	public DataInterfacePanel(TreePath TP, String ID, String WIDTH,
			String HEIGHT)
	{
		this.tp = TP;
		this.id = ID;
		this.width = WIDTH;
		this.height = HEIGHT;

		this.visible = true;
		this.highlight = false;
	}
}
