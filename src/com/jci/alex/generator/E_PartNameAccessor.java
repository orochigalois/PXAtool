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
public class E_PartNameAccessor
{
	private String Content = "";

	public E_PartNameAccessor(int i)
	{

		Vector<String> allPart = getAllPart(i);
		for (int j = 0; j < allPart.size(); j++)
		{
			Content += allPart.elementAt(j).toString();
		}
	}

	public String getContent()
	{
		return Content;
	}

	/***
	 * Return the objects associated to a partNames --example-- in
	 * alert_5100_MenuAlert_Z2_Alertes(i=0): return: Group&
	 * getPart_alert_5103_Image_Background(void) {return
	 * prefab_ALERT_5103_Image_Background.get_root();} void
	 * setPartState_alert_5103_Image_Background(State s) {return
	 * prefab_ALERT_5103_Image_Background.setState(s);}
	 * 
	 * Image& getPart_alert_5101_Image_PictoAlertes(void) {return
	 * prefab_ALERT_5101_Image_PictoAlertes.get_root();} void
	 * setPartState_alert_5101_Image_PictoAlertes(State s) {return
	 * prefab_ALERT_5101_Image_PictoAlertes.setState(s);}
	 * 
	 * Label& getPart_alert_5102_Label_TexteAlertes(void) {return
	 * get_alert_5102_Label_TexteAlertes();}
	 */
	Vector<String> getAllPart(int i)
	{
		Vector<String> allPart = new Vector<String>();
		traverseTree_For_AllPart(i, allPart);
		return allPart;
	}

	public void traverseTree_For_AllPart(int i, Vector allPart)
	{

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Z_DataStructure.treeModels.elementAt(i).getRoot());

		traverseNodes_For_AllPart(top, allPart);

	}

	public void traverseNodes_For_AllPart(DefaultMutableTreeNode root, Vector allPart)
	{

		checkNode_For_AllPart(root, allPart);
		Enumeration children = root.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				if (!Util.isRootNode(currentNode))
					traverseNodes_For_AllPart(currentNode, allPart);
			}
		}
	}

	public void checkNode_For_AllPart(DefaultMutableTreeNode node, Vector<String> allPart)
	{

		org.w3c.dom.Node domNode = Util.getDomNodeFromTreeNode(node);

		if (domNode.getAttributes().getNamedItem("partName") != null
				&& domNode.getAttributes().getNamedItem("href") != null)
		{
			
			String fullPath=domNode.getAttributes().getNamedItem("href").getNodeValue();
			allPart.add(domNode.getNodeName() + "&      getPart_"
					+ domNode.getAttributes().getNamedItem("partName").getNodeValue() + "(void)   {return prefab_"
					+ fullPath.substring(fullPath.lastIndexOf('/') + 1).replaceAll(".pxa", "")
					+ ".get_root();}\n");
			// if(nodeHasStates(node))
			allPart.add("void   setPartState_" + domNode.getAttributes().getNamedItem("partName").getNodeValue()
					+ "(State s)   {return prefab_"
					+ fullPath.substring(fullPath.lastIndexOf('/') + 1).replaceAll(".pxa", "")
					+ ".setState(s);}\n");

		}

		if (domNode.getAttributes().getNamedItem("partName") != null
				&& domNode.getAttributes().getNamedItem("href") == null)
		{
			allPart.add(domNode.getNodeName() + "&      getPart_"
					+ domNode.getAttributes().getNamedItem("partName").getNodeValue() + "(void)   {return get_"
					+ domNode.getAttributes().getNamedItem("partName").getNodeValue() + "();}\n");
		}

	}
}
