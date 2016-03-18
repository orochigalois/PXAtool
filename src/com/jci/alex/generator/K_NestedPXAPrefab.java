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


import com.jci.alex.util.Util;

/**
 * @author YXIN1
 *
 */
public class K_NestedPXAPrefab
{
	private String Content="";
	public K_NestedPXAPrefab(int i)
	{
		
		Vector<String> allHref = getAllHref(i);
		for (int j = 0; j < allHref.size(); j++) {
			Content+="Prefab_" + allHref.elementAt(j).toString().replaceAll(".pxa", "") + "<C>  prefab_"
					+ allHref.elementAt(j).toString().replaceAll(".pxa", "") + ";\n";
		}
	}
	
	
	public String getContent()
	{
		return Content;
	}
	
	/***
	 * Return the All Href in single PXA file --example-- in
	 * alert_5100_MenuAlert_Z2_Alertes(i=0): return:
	 * href="ALERT_5103_Image_Background.pxa"
	 * href="ALERT_5101_Image_PictoAlertes.pxa"
	 */
	Vector<String> getAllHref(int i)
	{
		Vector<String> allHref = new Vector<String>();
		traverseTree_For_AllHref(i, allHref);
		return allHref;
	}

	public void traverseTree_For_AllHref(int i, Vector allHref)
	{

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Z_DataStructure.treeModels.elementAt(i).getRoot());

		traverseNodes_For_AllHref(top, allHref);

	}

	public void traverseNodes_For_AllHref(DefaultMutableTreeNode root, Vector allHref)
	{

		checkNode_For_AllHref(root, allHref);
		Enumeration children = root.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{

				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				if (!Util.isRootNode(currentNode))
					traverseNodes_For_AllHref(currentNode, allHref);
			}
		}
	}

	public void checkNode_For_AllHref(DefaultMutableTreeNode node, Vector<String> allHref)
	{

		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;

		if (domNode.getAttributes().getNamedItem("href") != null)
		{
			allHref.add(domNode.getAttributes().getNamedItem("href").getNodeValue());
		}
	}
}
