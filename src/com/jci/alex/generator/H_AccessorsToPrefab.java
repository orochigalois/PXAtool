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
public class H_AccessorsToPrefab
{
	private String Content="";
	public H_AccessorsToPrefab(int i)
	{

		Vector<String> allAccessors2Prefab = getAllAccessors2Prefab(i);
		for (int j = 0; j < allAccessors2Prefab.size(); j++) {
			Content+=allAccessors2Prefab.elementAt(j).toString();
		}
	}
	
	
	public String getContent()
	{
		return Content;
	}
	
	
	/***
	 * Return Accessors to prefab --example-- in
	 * alert_5100_MenuAlert_Z2_Alertes(i=0): return: inline Group&
	 * get_alert_5103_Image_Background(void) {return
	 * prefab_ALERT_5103_Image_Background.get_root();} inline
	 * Prefab_ALERT_5103_Image_Background<C>&
	 * getPrefab_ALERT_5103_Image_Background(void) {return
	 * prefab_ALERT_5103_Image_Background;}
	 * 
	 * inline Image& get_alert_5101_Image_PictoAlertes(void) {return
	 * prefab_ALERT_5101_Image_PictoAlertes.get_root();} inline
	 * Prefab_ALERT_5101_Image_PictoAlertes<C>&
	 * getPrefab_ALERT_5101_Image_PictoAlertes(void) {return
	 * prefab_ALERT_5101_Image_PictoAlertes;}
	 */
	Vector<String> getAllAccessors2Prefab(int i) {
		Vector<String> allAccessors2Prefab = new Vector<String>();
		traverseTree_For_AllAccessors2Prefab(i, allAccessors2Prefab);
		return allAccessors2Prefab;
	}

	public void traverseTree_For_AllAccessors2Prefab(int i, Vector allAccessors2Prefab) {

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Z_DataStructure.treeModels.elementAt(i).getRoot());

		traverseNodes_For_AllAccessors2Prefab(top, allAccessors2Prefab);

	}

	public void traverseNodes_For_AllAccessors2Prefab(DefaultMutableTreeNode root, Vector allAccessors2Prefab) {

		checkNode_For_AllAccessors2Prefab(root, allAccessors2Prefab);
		Enumeration children = root.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				if (!Util.isRootNode(currentNode))
					traverseNodes_For_AllAccessors2Prefab(currentNode, allAccessors2Prefab);
			}
		}
	}

	public void checkNode_For_AllAccessors2Prefab(DefaultMutableTreeNode node,
			Vector<String> allAccessors2Prefab) {

		org.w3c.dom.Node domNode = Util.getDomNodeFromTreeNode(node);

		if (domNode.getAttributes().getNamedItem("partName") != null
				&& domNode.getAttributes().getNamedItem("href") != null) {
			allAccessors2Prefab.add("inline " + domNode.getNodeName() + "&      get_"
					+ domNode.getAttributes().getNamedItem("partName").getNodeValue() + "(void)   {return prefab_"
					+ domNode.getAttributes().getNamedItem("href").getNodeValue().replaceAll(".pxa", "")
					+ ".get_root();}\n");

			allAccessors2Prefab.add("inline   Prefab_"
					+ domNode.getAttributes().getNamedItem("href").getNodeValue().replaceAll(".pxa", "")
					+ "<C>& getPrefab_"
					+ domNode.getAttributes().getNamedItem("href").getNodeValue().replaceAll(".pxa", "")
					+ "(void){return prefab_"
					+ domNode.getAttributes().getNamedItem("href").getNodeValue().replaceAll(".pxa", "") + ";}\n");

		}

	}
}
