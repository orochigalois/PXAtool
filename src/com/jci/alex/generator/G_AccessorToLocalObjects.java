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
public class G_AccessorToLocalObjects
{
	private String Content="";
	public G_AccessorToLocalObjects(int i)
	{
		
		Vector<String> allinlineFunc = getAllInlineFunc(i);

		for (int j = 0; j < allinlineFunc.size(); j++) {
			Content+=allinlineFunc.elementAt(j).toString() + "\n";
		}
	}
	
	
	public String getContent()
	{
		return Content;
	}
	
	
	/***
	 * Return the All InlineFunc in single PXA file --example-- in
	 * alert_5100_MenuAlert_Z2_Alertes(i=0): return: inline Panel&
	 * get_root(void) {return obj_root;} inline Image&
	 * get_background_elements(void) {return obj_background_elements;} inline
	 * Scale& get_scale_popup_down(void) {return obj_scale_popup_down;} inline
	 * Group& get_foreground_elements(void) {return obj_foreground_elements;}
	 * inline Scale& get_Anonymous_Scale_L16(void) {return
	 * obj_Anonymous_Scale_L16;} //EBX => This object have no ID in the PXA.
	 * "L16" represent the line number in PXA. inline Scale&
	 * get_scale_popup(void) {return obj_scale_popup;} inline Group&
	 * get_popup_content(void) {return obj_popup_content;} inline ColumnLayout&
	 * get_Anonymous_ColumnLayout_L26(void) {return
	 * obj_Anonymous_ColumnLayout_L26;} inline Label&
	 * get_alert_5102_Label_TexteAlertes(void) {return
	 * obj_alert_5102_Label_TexteAlertes;}
	 */
	Vector<String> getAllInlineFunc(int i) {
		Vector<String> allInlineFunc = new Vector<String>();
		traverseTree_For_AllInlineFunc(i, allInlineFunc);
		return allInlineFunc;
	}

	public void traverseTree_For_AllInlineFunc(int i, Vector allInlineFunc) {

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Z_DataStructure.treeModels.elementAt(i).getRoot());

		traverseNodes_For_AllInlineFunc(i, top, allInlineFunc);

	}

	public void traverseNodes_For_AllInlineFunc(int i, DefaultMutableTreeNode root, Vector<String> allInlineFunc) {

		checkNode_For_AllInlineFunc(i, root, allInlineFunc);
		Enumeration children = root.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				if (!Util.isRootNode(currentNode))
					traverseNodes_For_AllInlineFunc(i, currentNode, allInlineFunc);
			}
		}
	}

	public void checkNode_For_AllInlineFunc(int i, DefaultMutableTreeNode node, Vector<String> allInlineFunc) {

		
		org.w3c.dom.Node domNode = Util.getDomNodeFromTreeNode(node);

		if (domNode.getAttributes().getNamedItem("href") != null)
			return;

		if (domNode.getNodeName().equals("Panel")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline Panel& get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "(void) {return obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline Panel& get_Anonymous_Panel_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "(void) {return obj_Anonymous_Panel_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + ";}");

		}
		if (domNode.getNodeName().equals("Image")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline Image& get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "(void) {return obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline Image& get_Anonymous_Image_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "(void) {return obj_Anonymous_Image_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + ";}");

		}
		if (domNode.getNodeName().equals("Scale")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline Scale& get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "(void) {return obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline Scale& get_Anonymous_Scale_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "(void) {return obj_Anonymous_Scale_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + ";}");

		}
		if (domNode.getNodeName().equals("Group")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline Group& get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "(void) {return obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline Group& get_Anonymous_Group_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "(void) {return obj_Anonymous_Group_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + ";}");

		}
		if (domNode.getNodeName().equals("ColumnLayout")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline ColumnLayout& get_"
						+ domNode.getAttributes().getNamedItem("id").getNodeValue() + "(void) {return obj_"
						+ domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline ColumnLayout& get_Anonymous_ColumnLayout_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i)
						+ "(void) {return obj_Anonymous_ColumnLayout_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + ";}");

		}
		if (domNode.getNodeName().equals("Label")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline Label& get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "(void) {return obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline Label& get_Anonymous_Label_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + "(void) {return obj_Anonymous_Label_L"
						+ Z_DataStructure.getNodeLineNumber(domNode, i) + ";}");
		}

	}
}
