/**
 * 
 */
package com.jci.alex.generator;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import com.jci.alex.Constant;

import com.jci.alex.util.Util;

/**
 * @author YXIN1
 *
 */
public class F_Constructor
{
	private String Content = "";

	public F_Constructor(int i)
	{
		
		
		Vector<String> allProperties = getAllProperties(i);
		for (int j = 0; j < allProperties.size(); j++) {
			Content+=allProperties.elementAt(j).toString();
		}

	}

	public String getContent()
	{
		return Content;
	}

	/***
	 * Return the All Properties in single PXA file --example-- in
	 * alert_5100_MenuAlert_Z2_Alertes(i=0): return: get_root().set_id("root");
	 * get_root().set_alpha( 1.F );//EBX => the type of "alpha" is a real, real
	 * are generated as float. get_root().set_scale( 1.F );
	 * get_root().add_data(get_background_elements()); //EBX => the operation is
	 * "add_data" and not "set_data" because multiple data can be attached to an
	 * "Container" get_root().add_data(get_foreground_elements()); //EBX => the
	 * type of the attribute "data" can be any PXAObject, it is implemented as
	 * PXAObject* get_root().add_transition( obj_anonymous_Transition_L77 );
	 * //EBX => This object have no ID in the PXA. "L77" represent the line
	 * number in PXA. get_root().add_transition( obj_popup_closing ); .. ...
	 */
	Vector getAllProperties(int i) {
		Vector<DefaultMutableTreeNode> allInterestingNode = new Vector<DefaultMutableTreeNode>();
		traverseTree_For_AllInterestingNode(i, allInterestingNode);

		Vector<String> allProperties = new Vector<String>();
		for (int j = 0; j < allInterestingNode.size(); j++) {
			putNodeIntoVector(i, allInterestingNode.elementAt(j), allProperties);
		}

		return allProperties;
	}

	boolean hasData(DefaultMutableTreeNode node) {
		boolean re = false;
		Enumeration children = node.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				org.w3c.dom.Node currentDomNode = Util.getDomNodeFromTreeNode(currentNode);
				if (currentDomNode.getNodeName().equals("data")) {
					re = true;
				}
			}
		}

		return re;

	}

	Vector<String> returnDataList(int i, DefaultMutableTreeNode node) {
		Vector<String> returnDataList = new Vector<String>();
		org.w3c.dom.Node domNode = Util.getDomNodeFromTreeNode(node);
		Enumeration children = node.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				org.w3c.dom.Node currentDomNode = Util.getDomNodeFromTreeNode(currentNode);

				if (currentDomNode.getNodeName().equals("data")) {
					Enumeration grandChildren = currentNode.children();
					if (grandChildren != null) {
						while (grandChildren.hasMoreElements()) {
							DefaultMutableTreeNode grandNode = (DefaultMutableTreeNode) grandChildren.nextElement();

							org.w3c.dom.Node grandDomNode = Util.getDomNodeFromTreeNode(grandNode);
							if (grandDomNode.getAttributes().getNamedItem("id") != null)
								returnDataList.add(grandDomNode.getAttributes().getNamedItem("id").getNodeValue());
							else
								returnDataList.add("Anonymous_" + grandDomNode.getNodeName() + "_L"
										+ Z_DataStructure.getNodeLineNumber(grandDomNode, i));
						}
					}

				}
			}
		}

		return returnDataList;

	}

	void putNodeIntoVector(int i, DefaultMutableTreeNode node, Vector<String> allProperties) {
		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;
		if (domNode.getAttributes().getNamedItem("id") != null)

			allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_id(\""
					+ domNode.getAttributes().getNamedItem("id").getNodeValue() + "\");\n");
		else
			allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
					+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_id(\"Anonymous_"
					+ domNode.getNodeName() + "_L" + Z_DataStructure.getNodeLineNumber(domNode, i) + "\");\n");

		if (domNode.getAttributes().getNamedItem("spacing") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_spacing("
						+ domNode.getAttributes().getNamedItem("spacing").getNodeValue() + ");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_spacing("
						+ domNode.getAttributes().getNamedItem("spacing").getNodeValue() + ");\n");
		}

		if (domNode.getAttributes().getNamedItem("source") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_source(\""
								+ domNode.getAttributes().getNamedItem("source").getNodeValue() + "\");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_source("
						+ domNode.getAttributes().getNamedItem("source").getNodeValue() + ");\n");
		}

		if (domNode.getAttributes().getNamedItem("partName") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_partName(\""
								+ domNode.getAttributes().getNamedItem("partName").getNodeValue() + "\");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_partName(\""
						+ domNode.getAttributes().getNamedItem("partName").getNodeValue() + "\");\n");
		}
		if (domNode.getAttributes().getNamedItem("horizontalAlignment") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "().set_horizontalAlignment(\""
						+ domNode.getAttributes().getNamedItem("horizontalAlignment").getNodeValue() + "\");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_horizontalAlignment("
						+ domNode.getAttributes().getNamedItem("horizontalAlignment").getNodeValue() + ");\n");
		}

		if (domNode.getAttributes().getNamedItem("lineHeight") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_lineHeight("
								+ domNode.getAttributes().getNamedItem("lineHeight").getNodeValue() + ");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_lineHeight("
						+ domNode.getAttributes().getNamedItem("lineHeight").getNodeValue() + ");\n");
		}

		if (domNode.getAttributes().getNamedItem("maximumHeight") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add(
						"get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_maximumHeight("
								+ domNode.getAttributes().getNamedItem("maximumHeight").getNodeValue() + ");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_maximumHeight("
						+ domNode.getAttributes().getNamedItem("maximumHeight").getNodeValue() + ".F);\n");
		}

		if (domNode.getAttributes().getNamedItem("sampleText") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_sampleText(\""
								+ domNode.getAttributes().getNamedItem("sampleText").getNodeValue() + "\");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_sampleText(\""
						+ domNode.getAttributes().getNamedItem("sampleText").getNodeValue() + "\");\n");
		}

		if (domNode.getAttributes().getNamedItem("alpha") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_alpha("
						+ domNode.getAttributes().getNamedItem("alpha").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_alpha("
						+ domNode.getAttributes().getNamedItem("alpha").getNodeValue() + ".F);\n");
		}
		if (domNode.getAttributes().getNamedItem("scale") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_scale("
						+ domNode.getAttributes().getNamedItem("scale").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_scale("
						+ domNode.getAttributes().getNamedItem("scale").getNodeValue() + ".F);\n");
		}

		if (hasData(node)) {
			Vector<String> returnDataList = returnDataList(i, node);

			for (int j = 0; j < returnDataList.size(); j++) {
				if (domNode.getAttributes().getNamedItem("id") != null)
					allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
							+ "().add_data(get_" + returnDataList.elementAt(j) + "());\n");
				else
					allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
							+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().add_data(get_"
							+ returnDataList.elementAt(j) + "());\n");
			}

		}

		if (domNode.getAttributes().getNamedItem("width") != null) {

			if (domNode.getAttributes().getNamedItem("width").getNodeValue().contains("{"))
				return;

			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_width("
						+ domNode.getAttributes().getNamedItem("width").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_width("
						+ domNode.getAttributes().getNamedItem("width").getNodeValue() + ".F);\n");
		}

		if (domNode.getAttributes().getNamedItem("height") != null) {
			if (domNode.getAttributes().getNamedItem("height").getNodeValue().contains("{"))
				return;

			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_height("
						+ domNode.getAttributes().getNamedItem("height").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_height("
						+ domNode.getAttributes().getNamedItem("height").getNodeValue() + ".F);\n");
		}
		if (domNode.getAttributes().getNamedItem("x") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_x("
						+ domNode.getAttributes().getNamedItem("x").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_x("
						+ domNode.getAttributes().getNamedItem("x").getNodeValue() + ".F);\n");
		}
		if (domNode.getAttributes().getNamedItem("y") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_y("
						+ domNode.getAttributes().getNamedItem("y").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_y("
						+ domNode.getAttributes().getNamedItem("y").getNodeValue() + ".F);\n");
		}
		if (domNode.getAttributes().getNamedItem("origin.x") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_origin_x("
								+ domNode.getAttributes().getNamedItem("origin.x").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_origin_x("
						+ domNode.getAttributes().getNamedItem("origin.x").getNodeValue() + ".F);\n");
		}

		if (domNode.getAttributes().getNamedItem("origin.y") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_origin_y("
								+ domNode.getAttributes().getNamedItem("origin.y").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_origin_y("
						+ domNode.getAttributes().getNamedItem("origin.y").getNodeValue() + ".F);\n");
		}

		if (domNode.getAttributes().getNamedItem("xScale") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_xScale("
						+ domNode.getAttributes().getNamedItem("xScale").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_xScale("
						+ domNode.getAttributes().getNamedItem("xScale").getNodeValue() + ".F);\n");
		}

		if (domNode.getAttributes().getNamedItem("yScale") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_yScale("
						+ domNode.getAttributes().getNamedItem("yScale").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "().set_yScale("
						+ domNode.getAttributes().getNamedItem("yScale").getNodeValue() + ".F);\n");
		}

	}

	public void traverseTree_For_AllInterestingNode(int i, Vector allInterestingNode) {

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Z_DataStructure.treeModels.elementAt(i).getRoot());

		traverseNodes_For_AllInterestingNode(top, allInterestingNode);

	}

	public void traverseNodes_For_AllInterestingNode(DefaultMutableTreeNode root, Vector allInterestingNode) {

		checkNode_For_AllInterestingNode(root, allInterestingNode);
		Enumeration children = root.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				if (!Util.isRootNode(currentNode))
					traverseNodes_For_AllInterestingNode(currentNode, allInterestingNode);
			}
		}
	}

	public void checkNode_For_AllInterestingNode(DefaultMutableTreeNode node,
			Vector<DefaultMutableTreeNode> allInterestingNode) {

		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;

		if (domNode.getAttributes().getNamedItem("href") != null)
			return;

		if (domNode.getNodeName().equals("Panel")) {
			allInterestingNode.add(node);
		}
		if (domNode.getNodeName().equals("Image")) {
			allInterestingNode.add(node);

		}
		if (domNode.getNodeName().equals("Scale")) {
			allInterestingNode.add(node);

		}
		if (domNode.getNodeName().equals("Group")) {
			allInterestingNode.add(node);
		}
		if (domNode.getNodeName().equals("ColumnLayout")) {
			allInterestingNode.add(node);
		}
		if (domNode.getNodeName().equals("Label")) {
			allInterestingNode.add(node);

		}

	}

}
