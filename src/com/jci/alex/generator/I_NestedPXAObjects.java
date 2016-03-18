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
public class I_NestedPXAObjects
{
	private String Content="";
	public I_NestedPXAObjects(int i)
	{

		Vector<String> allProtectedItem = getAllProtectedItem(i);

		for (int j = 0; j < allProtectedItem.size(); j++) {
			Content+=allProtectedItem.elementAt(j).toString() + ";\n";
		}
	}
	
	
	public String getContent()
	{
		return Content;
	}
	/***
	 * Return the All Protected Item in single PXA file --example-- in
	 * alert_5100_MenuAlert_Z2_Alertes(i=0): return: Panel obj_root; Image
	 * obj_background_elements; Scale obj_scale_popup_down; Group
	 * obj_foreground_elements; Scale obj_Anonymous_Scale_L16; //EBX => This
	 * object have no ID in the PXA. "L16" represent the line number in PXA.
	 * Scale obj_scale_popup; Group obj_popup_content; ColumnLayout
	 * obj_Anonymous_ColumnLayout_L26; Label obj_alert_5102_Label_TexteAlertes;
	 */
	Vector<String> getAllProtectedItem(int i) {
		Vector<String> allProtectedItem = new Vector<String>();
		traverseTree_For_AllProtectedItem(i, allProtectedItem);
		return allProtectedItem;
	}

	public void traverseTree_For_AllProtectedItem(int i, Vector allProtectedItem) {

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Z_DataStructure.treeModels.elementAt(i).getRoot());

		traverseNodes_For_AllProtectedItem(i, top, allProtectedItem);

	}

	public void traverseNodes_For_AllProtectedItem(int i, DefaultMutableTreeNode root, Vector allProtectedItem) {

		checkNode_For_AllProtectedItem(i, root, allProtectedItem);
		Enumeration children = root.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				if (!Util.isRootNode(currentNode))
					traverseNodes_For_AllProtectedItem(i, currentNode, allProtectedItem);
			}
		}
	}

	

	

	public void checkNode_For_AllProtectedItem(int i, DefaultMutableTreeNode node,
			Vector<String> allProtectedItem) {

		
		org.w3c.dom.Node domNode = Util.getDomNodeFromTreeNode(node);
		if (domNode.getAttributes().getNamedItem("href") != null)
			return;

		if (domNode.getNodeName().equals("Panel")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProtectedItem.add("Panel obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue());
			else
				allProtectedItem
						.add("Panel obj_Anonymous_Panel_L" + Z_DataStructure.getNodeLineNumber(domNode, i));

		}
		if (domNode.getNodeName().equals("Image")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProtectedItem.add("Image obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue());
			else
				allProtectedItem
						.add("Image obj_Anonymous_Image_L" + Z_DataStructure.getNodeLineNumber(domNode, i));

		}
		if (domNode.getNodeName().equals("Scale")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProtectedItem.add("Scale obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue());
			else
				allProtectedItem
						.add("Scale obj_Anonymous_Scale_L" + Z_DataStructure.getNodeLineNumber(domNode, i));

		}
		if (domNode.getNodeName().equals("Group")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProtectedItem.add("Group obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue());
			else
				allProtectedItem
						.add("Group obj_Anonymous_Group_L" + Z_DataStructure.getNodeLineNumber(domNode, i));

		}
		if (domNode.getNodeName().equals("ColumnLayout")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProtectedItem.add("ColumnLayout obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue());
			else
				allProtectedItem.add("ColumnLayout obj_Anonymous_ColumnLayout_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i));

		}
		if (domNode.getNodeName().equals("Label")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProtectedItem.add("Label obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue());
			else
				allProtectedItem
						.add("Label obj_Anonymous_Label_L" + Z_DataStructure.getNodeLineNumber(domNode, i));

		}

	}
}
