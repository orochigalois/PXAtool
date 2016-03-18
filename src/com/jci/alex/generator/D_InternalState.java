/**
 * 
 */
package com.jci.alex.generator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import com.jci.alex.Constant;

import com.jci.alex.util.Util;

/**
 * @author YXIN1
 *
 */
public class D_InternalState
{
	private String Content = "";

	public D_InternalState(int i)
	{
		if (isHavingInternalStatesValues(i))
		{
			Vector<String> allInternalStates = getAllInternalStates(i);

			Content += "// -------------------------------------------------------------------------------------------------------------\n";
			Content += "// internal States values\n";
			Content += "// -------------------------------------------------------------------------------------------------------------\n";

			Content += "	struct StateValues\n";
			Content += "	{\n";
			for (int j = 0; j < allInternalStates.size(); j++)
			{
				Content +="		static const State " + allInternalStates.elementAt(j).toString() + ";\n";
			}
			Content +="	};\n";
		}

	}

	/***
	 * Determine if there is Internal States in PXA file --example-- in
	 * alert_5100_MenuAlert_Z2_Alertes(i=0) there is a structure like this:
	 * Panel root --states
	 * 
	 * return:true
	 */
	boolean isHavingInternalStatesValues(int i)
	{
		boolean found = false;
		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Z_DataStructure.treeModels.elementAt(i).getRoot());
		Enumeration children = top.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				org.w3c.dom.Node domNode = Util.getDomNodeFromTreeNode((DefaultMutableTreeNode) children.nextElement());

				if (domNode.getNodeName().equals("states"))
				{
					found = true;
					break;
				}
			}
		}

		return found;
	}

	/***
	 * get all Internal States in PXA file --example-- in
	 * alert_5100_MenuAlert_Z2_Alertes(i=0) there is a structure like this:
	 * Panel root --states
	 * 
	 * return: State_5100_Red; State_5100_Blue; State_5100_Orange;
	 * State_5100_Green; State_5100_Grey; opening; closing;
	 */
	Vector<String> getAllInternalStates(int i)
	{
		Vector<String> allInternalStates = new Vector<String>();
		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Z_DataStructure.treeModels.elementAt(i).getRoot());
		Enumeration children = top.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();
	
				org.w3c.dom.Node domNode = Util.getDomNodeFromTreeNode(currentNode);

				if (domNode.getNodeName().equals("states"))
				{
					Enumeration grandChildren = currentNode.children();
					if (grandChildren != null)
					{
						while (grandChildren.hasMoreElements())
						{

							DefaultMutableTreeNode currentNode_grand = (DefaultMutableTreeNode) grandChildren
									.nextElement();
					
							org.w3c.dom.Node domNode_grand = Util.getDomNodeFromTreeNode(currentNode_grand);
							allInternalStates.add(domNode_grand.getAttributes().getNamedItem("name").getNodeValue());

						}
					}

					break;
				}
			}
		}

		return allInternalStates;
	}

	public String getContent()
	{
		return Content;
	}
}
