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

import org.w3c.dom.NamedNodeMap;

import com.jci.alex.Constant;

import com.jci.alex.util.Util;

/**
 * @author YXIN1
 *
 */
public class J_Keys
{
	private String Content="";
	public J_Keys(int i)
	{

		Content+="enum\n";
		Content+="{\n";

		Vector<String> allEnum = getAllEnum(i);
		for (int j = 0; j < allEnum.size(); j++) {
			Content+=allEnum.elementAt(j).toString() + ",\n";
		}
		Content+="};\n";
	}
	
	
	public String getContent()
	{
		return Content;
	}
	/***
	 * Return the All Enum in single PXA file --example-- in
	 * alert_5100_MenuAlert_Z2_Alertes(i=0): return: enum {
	 * key_alert_5102_Label_TexteAlertes_alpha, key_scale_popup_xScale,
	 * key_scale_popup_yScale, key_scale_popup_down_alpha,
	 * key_scale_popup_down_xScale, key_scale_popup_down_yScale,
	 * key_background_elements_alpha,
	 */
	Vector<String> getAllEnum(int i) {
		Vector<String> allEnum = new Vector<String>();
		traverseTree_For_AllEnum(i, allEnum);
		return allEnum;
	}

	public void traverseTree_For_AllEnum(int i, Vector allEnum) {

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Z_DataStructure.treeModels.elementAt(i).getRoot());

		traverseNodes_For_AllEnum(i, top, allEnum);

	}

	public void traverseNodes_For_AllEnum(int i, DefaultMutableTreeNode root, Vector allEnum) {

		checkNode_For_AllEnum(i, root, allEnum);
		Enumeration children = root.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				if (!Util.isRootNode(currentNode))
					traverseNodes_For_AllEnum(i, currentNode, allEnum);
			}
		}
	}

	public void checkNode_For_AllEnum(int i, DefaultMutableTreeNode node, Vector<String> allEnum) {

		org.w3c.dom.Node domNode = Util.getDomNodeFromTreeNode(node);
		if (domNode.getAttributes().getNamedItem("href") != null)
			return;

		if (domNode.getNodeName().equals("PropertyChanges"))
			return;
		if (domNode.getNodeName().equals("NumberAnimation"))
			return;

		if (domNode.getAttributes().getNamedItem("id") != null) {
			NamedNodeMap tmp_attr = domNode.getAttributes();
			for (int j = 0; j < tmp_attr.getLength(); j++) {
				allEnum.add("key_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "_"
						+ (tmp_attr.item(j).getNodeName().replaceAll(":", "_")).replaceAll("\\.", "_"));
			}

		}

		else {
			NamedNodeMap tmp_attr = domNode.getAttributes();
			for (int j = 0; j < tmp_attr.getLength(); j++) {
				allEnum.add("key_Anonymous_" + domNode.getNodeName() + "_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "_"
						+ (tmp_attr.item(j).getNodeName().replaceAll(":", "_")).replaceAll("\\.", "_"));
			}
		}

	}
}
