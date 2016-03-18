package com.jci.alex;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.NamedNodeMap;

import com.jci.alex.util.Util;

public class UtilForGeneratingHref {

	// ////////////////////////////////////////////////////////////////////////////////////////////////////

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
	static Vector getAllProperties(int i, String hrefPath) {
		Vector<DefaultMutableTreeNode> allInterestingNode = new Vector<DefaultMutableTreeNode>();
		traverseTree_For_AllInterestingNode(i, hrefPath, allInterestingNode);

		Vector<String> allProperties = new Vector<String>();
		for (int j = 0; j < allInterestingNode.size(); j++) {
			putNodeIntoVector(i, hrefPath, allInterestingNode.elementAt(j), allProperties);
		}

		return allProperties;
	}

	static boolean hasData(DefaultMutableTreeNode node) {
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

	static Vector<String> returnDataList(int i, String hrefPath, DefaultMutableTreeNode node) {
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
										+ getNodeLineNumber(i, grandDomNode, hrefPath));
						}
					}

				}
			}
		}

		return returnDataList;

	}

	static void putNodeIntoVector(int i, String hrefPath, DefaultMutableTreeNode node, Vector<String> allProperties) {
		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;
		if (domNode.getAttributes().getNamedItem("id") != null)

			allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_id(\""
					+ domNode.getAttributes().getNamedItem("id").getNodeValue() + "\");\n");
		else
			allProperties.add("get_Anonymous" + domNode.getNodeName() + "L" + getNodeLineNumber(i, domNode, hrefPath)
					+ "().set_id(\"Anonymous_" + domNode.getNodeName() + "_L" + getNodeLineNumber(i, domNode, hrefPath)
					+ "\");\n");

		if (domNode.getAttributes().getNamedItem("spacing") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_spacing("
						+ domNode.getAttributes().getNamedItem("spacing").getNodeValue() + ");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_spacing("
						+ domNode.getAttributes().getNamedItem("spacing").getNodeValue() + ");\n");
		}

		if (domNode.getAttributes().getNamedItem("source") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_source(\""
								+ domNode.getAttributes().getNamedItem("source").getNodeValue() + "\");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_source("
						+ domNode.getAttributes().getNamedItem("source").getNodeValue() + ");\n");
		}

		if (domNode.getAttributes().getNamedItem("partName") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_partName(\""
								+ domNode.getAttributes().getNamedItem("partName").getNodeValue() + "\");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_partName(\""
						+ domNode.getAttributes().getNamedItem("partName").getNodeValue() + "\");\n");
		}
		if (domNode.getAttributes().getNamedItem("horizontalAlignment") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "().set_horizontalAlignment(\""
						+ domNode.getAttributes().getNamedItem("horizontalAlignment").getNodeValue() + "\");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_horizontalAlignment("
						+ domNode.getAttributes().getNamedItem("horizontalAlignment").getNodeValue() + ");\n");
		}

		if (domNode.getAttributes().getNamedItem("lineHeight") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_lineHeight("
								+ domNode.getAttributes().getNamedItem("lineHeight").getNodeValue() + ");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_lineHeight("
						+ domNode.getAttributes().getNamedItem("lineHeight").getNodeValue() + ");\n");
		}

		if (domNode.getAttributes().getNamedItem("maximumHeight") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add(
						"get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_maximumHeight("
								+ domNode.getAttributes().getNamedItem("maximumHeight").getNodeValue() + ");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_maximumHeight("
						+ domNode.getAttributes().getNamedItem("maximumHeight").getNodeValue() + ".F);\n");
		}

		if (domNode.getAttributes().getNamedItem("sampleText") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_sampleText(\""
								+ domNode.getAttributes().getNamedItem("sampleText").getNodeValue() + "\");\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_sampleText(\""
						+ domNode.getAttributes().getNamedItem("sampleText").getNodeValue() + "\");\n");
		}

		if (domNode.getAttributes().getNamedItem("alpha") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_alpha("
						+ domNode.getAttributes().getNamedItem("alpha").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_alpha("
						+ domNode.getAttributes().getNamedItem("alpha").getNodeValue() + ".F);\n");
		}
		if (domNode.getAttributes().getNamedItem("scale") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_scale("
						+ domNode.getAttributes().getNamedItem("scale").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_scale("
						+ domNode.getAttributes().getNamedItem("scale").getNodeValue() + ".F);\n");
		}

		if (hasData(node)) {
			Vector<String> returnDataList = returnDataList(i, hrefPath, node);

			for (int j = 0; j < returnDataList.size(); j++) {
				if (domNode.getAttributes().getNamedItem("id") != null)
					allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
							+ "().add_data(get_" + returnDataList.elementAt(j) + "());\n");
				else
					allProperties
							.add("get_Anonymous" + domNode.getNodeName() + "L" + getNodeLineNumber(i, domNode, hrefPath)
									+ "().add_data(get_" + returnDataList.elementAt(j) + "());\n");
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
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_width("
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
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_height("
						+ domNode.getAttributes().getNamedItem("height").getNodeValue() + ".F);\n");
		}
		if (domNode.getAttributes().getNamedItem("x") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_x("
						+ domNode.getAttributes().getNamedItem("x").getNodeValue() + ".F);\n");
			else
				allProperties
						.add("get_Anonymous" + domNode.getNodeName() + "L" + getNodeLineNumber(i, domNode, hrefPath)
								+ "().set_x(" + domNode.getAttributes().getNamedItem("x").getNodeValue() + ".F);\n");
		}
		if (domNode.getAttributes().getNamedItem("y") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_y("
						+ domNode.getAttributes().getNamedItem("y").getNodeValue() + ".F);\n");
			else
				allProperties
						.add("get_Anonymous" + domNode.getNodeName() + "L" + getNodeLineNumber(i, domNode, hrefPath)
								+ "().set_y(" + domNode.getAttributes().getNamedItem("y").getNodeValue() + ".F);\n");
		}
		if (domNode.getAttributes().getNamedItem("origin.x") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_origin_x("
								+ domNode.getAttributes().getNamedItem("origin.x").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_origin_x("
						+ domNode.getAttributes().getNamedItem("origin.x").getNodeValue() + ".F);\n");
		}

		if (domNode.getAttributes().getNamedItem("origin.y") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties
						.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_origin_y("
								+ domNode.getAttributes().getNamedItem("origin.y").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_origin_y("
						+ domNode.getAttributes().getNamedItem("origin.y").getNodeValue() + ".F);\n");
		}

		if (domNode.getAttributes().getNamedItem("xScale") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_xScale("
						+ domNode.getAttributes().getNamedItem("xScale").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_xScale("
						+ domNode.getAttributes().getNamedItem("xScale").getNodeValue() + ".F);\n");
		}

		if (domNode.getAttributes().getNamedItem("yScale") != null) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allProperties.add("get_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + "().set_yScale("
						+ domNode.getAttributes().getNamedItem("yScale").getNodeValue() + ".F);\n");
			else
				allProperties.add("get_Anonymous" + domNode.getNodeName() + "L"
						+ getNodeLineNumber(i, domNode, hrefPath) + "().set_yScale("
						+ domNode.getAttributes().getNamedItem("yScale").getNodeValue() + ".F);\n");
		}

	}

	public static void traverseTree_For_AllInterestingNode(int i, String hrefPath, Vector allInterestingNode) {

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Constant.allTreeModel.elementAt(i).getRoot());

		traverseNodes_For_AllInterestingNode(top, hrefPath, allInterestingNode);

	}

	public static void traverseNodes_For_AllInterestingNode(DefaultMutableTreeNode root, String hrefPath,
			Vector allInterestingNode) {

		// checkNode_For_AllInterestingNode(root, allInterestingNode);
		Enumeration children = root.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				if (isMyHrefNode(currentNode, hrefPath)) {
					traverseNodes_For_AllInterestingNode_AfterHrefIsFound(currentNode, allInterestingNode);
					break;
				}

				else
					traverseNodes_For_AllInterestingNode(currentNode, hrefPath, allInterestingNode);
			}
		}
	}

	public static void traverseNodes_For_AllInterestingNode_AfterHrefIsFound(DefaultMutableTreeNode root,
			Vector allInterestingNode) {

		checkNode_For_AllInterestingNode_AfterHrefIsFound(root, allInterestingNode);
		Enumeration children = root.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				if (!isRootNode(currentNode))
					traverseNodes_For_AllInterestingNode_AfterHrefIsFound(currentNode, allInterestingNode);
			}
		}

	}

	public static void checkNode_For_AllInterestingNode_AfterHrefIsFound(DefaultMutableTreeNode node,
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

	public static boolean isMyHrefNode(DefaultMutableTreeNode node, String hrefPath) {
		org.w3c.dom.Node domParent = Util.getDomNodeFromTreeNode((DefaultMutableTreeNode) node.getParent());
		if (!isRootNode(node))
			return false;
		if (domParent.getAttributes().getNamedItem("href") == null)
			return false;
		if (domParent.getAttributes().getNamedItem("href").getNodeValue().equals(hrefPath))
			return true;
		else
			return false;

	}

	public static void checkNode_For_AllInterestingNode(DefaultMutableTreeNode node,
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

	public static boolean isRootNode(DefaultMutableTreeNode node) {
		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;
		if (domNode.getAttributes().getNamedItem("xmlns") != null)
			return true;
		else
			return false;

	}

	/***
	 * Return the LineNumber of node in PXA file --example-- in
	 * alert_5100_MenuAlert_Z2_Alertes(i=0),line 16, we have this:
	 * <Scale yScale="-1" />
	 * 
	 * we put domNode="<Scale yScale="-1" />" then Return=16
	 */
	static String getNodeLineNumber(int i, org.w3c.dom.Node domNode, String hrefPath) {

		FileReader fr = null;
		LineNumberReader lnr = null;
		String str;
		int lineNumber = 0;

		try {

			String rootFilePath = Constant.workingPath + "\\" + Constant.allPanelPath.elementAt(i).replace('/', '\\');

			String rootFolderPath = rootFilePath.substring(0, rootFilePath.lastIndexOf(File.separator));
			String fullPath = rootFolderPath + "\\" + hrefPath.replace('/', '\\');

			fullPath = FilenameUtils.normalize(fullPath);

			// create new reader
			fr = new FileReader(fullPath);
			lnr = new LineNumberReader(fr);

			// read lines till the end of the stream
			while ((str = lnr.readLine()) != null) {

				if (isEqual(domNode, str)) {
					lineNumber = lnr.getLineNumber();
					break;
				}

			}
		} catch (Exception e) {

			// if any error occurs
			e.printStackTrace();
		} finally {

			// closes the stream and releases system resources
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (lnr != null)
				try {
					lnr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			Integer tmp = lineNumber;
			return tmp.toString();
		}

	}

	static boolean isEqual(org.w3c.dom.Node domNode, String str) {
		boolean re = true;
		if (!str.contains(domNode.getNodeName()))
			re = false;

		NamedNodeMap tmp_attr = domNode.getAttributes();

		for (int i = 0; i < tmp_attr.getLength(); ++i) {

			if (!str.contains(tmp_attr.item(i).getNodeName()))
				re = false;
			if (!str.contains(tmp_attr.item(i).getNodeValue()))
				re = false;

		}
		return re;

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
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
	static Vector getAllInlineFunc(int i, String hrefPath) {
		Vector<String> allInlineFunc = new Vector<String>();
		traverseTree_For_AllInlineFunc(i, hrefPath, allInlineFunc);
		return allInlineFunc;
	}

	public static void traverseTree_For_AllInlineFunc(int i, String hrefPath, Vector allInlineFunc) {

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Constant.allTreeModel.elementAt(i).getRoot());

		traverseNodes_For_AllInlineFunc(i,hrefPath, top, allInlineFunc);

	}

	public static void traverseNodes_For_AllInlineFunc( int i,String hrefPath, DefaultMutableTreeNode root,
			Vector allInlineFunc) {

		Enumeration children = root.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				if (isMyHrefNode(currentNode, hrefPath)) {
					traverseNodes_For_AllInlineFunc_AfterHrefIsFound(i,hrefPath,currentNode, allInlineFunc);
					break;
				}

				else
					traverseNodes_For_AllInlineFunc(i,hrefPath,currentNode, allInlineFunc);
			}
		}
	}
	
	public static void traverseNodes_For_AllInlineFunc_AfterHrefIsFound(int i,String hrefPath,DefaultMutableTreeNode root,
			Vector allInlineFunc) {

		checkNode_For_AllInlineFunc_AfterHrefIsFound(i, hrefPath, root, allInlineFunc);
		Enumeration children = root.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children.nextElement();

				if (!isRootNode(currentNode))
					traverseNodes_For_AllInterestingNode_AfterHrefIsFound(currentNode, allInlineFunc);
			}
		}

	}

	public static void checkNode_For_AllInlineFunc_AfterHrefIsFound(int i,String hrefPath, DefaultMutableTreeNode node, Vector<String> allInlineFunc) {

		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;

		if (domNode.getAttributes().getNamedItem("href") != null)
			return;

		if (domNode.getNodeName().equals("Panel")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline Panel& get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "(void) {return obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline Panel& get_Anonymous_Panel_L" + getNodeLineNumber(i, domNode, hrefPath)
						+ "(void) {return obj_Anonymous_Panel_L" + getNodeLineNumber(i, domNode, hrefPath) + ";}");

		}
		if (domNode.getNodeName().equals("Image")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline Image& get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "(void) {return obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline Image& get_Anonymous_Image_L" + getNodeLineNumber(i, domNode, hrefPath)
						+ "(void) {return obj_Anonymous_Image_L" + getNodeLineNumber(i, domNode, hrefPath) + ";}");

		}
		if (domNode.getNodeName().equals("Scale")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline Scale& get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "(void) {return obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline Scale& get_Anonymous_Scale_L" + getNodeLineNumber(i, domNode, hrefPath)
						+ "(void) {return obj_Anonymous_Scale_L" + getNodeLineNumber(i, domNode, hrefPath) + ";}");

		}
		if (domNode.getNodeName().equals("Group")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline Group& get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "(void) {return obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline Group& get_Anonymous_Group_L" + getNodeLineNumber(i, domNode, hrefPath)
						+ "(void) {return obj_Anonymous_Group_L" + getNodeLineNumber(i, domNode, hrefPath) + ";}");

		}
		if (domNode.getNodeName().equals("ColumnLayout")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline ColumnLayout& get_"
						+ domNode.getAttributes().getNamedItem("id").getNodeValue() + "(void) {return obj_"
						+ domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline ColumnLayout& get_Anonymous_ColumnLayout_L" + getNodeLineNumber(i, domNode, hrefPath)
						+ "(void) {return obj_Anonymous_ColumnLayout_L" + getNodeLineNumber(i, domNode, hrefPath) + ";}");

		}
		if (domNode.getNodeName().equals("Label")) {
			if (domNode.getAttributes().getNamedItem("id") != null)
				allInlineFunc.add("inline Label& get_" + domNode.getAttributes().getNamedItem("id").getNodeValue()
						+ "(void) {return obj_" + domNode.getAttributes().getNamedItem("id").getNodeValue() + ";}");
			else
				allInlineFunc.add("inline Label& get_Anonymous_Label_L" + getNodeLineNumber(i, domNode, hrefPath)
						+ "(void) {return obj_Anonymous_Label_L" + getNodeLineNumber(i, domNode, hrefPath) + ";}");
		}

	}

}
