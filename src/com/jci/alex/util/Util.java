package com.jci.alex.util;

import java.io.StringWriter;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Util
{
	public static org.w3c.dom.Node getDomNodeFromTreeNode(DefaultMutableTreeNode node)
	{
		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;
		return domNode;
	}
	
	public static boolean isRootNode(DefaultMutableTreeNode node) {
	
		org.w3c.dom.Node domNode = getDomNodeFromTreeNode(node);
		if (domNode.getAttributes().getNamedItem("xmlns") != null)
			return true;
		else
			return false;

	}
	
	public static String nodeToString(org.w3c.dom.Node node) {
		StringWriter sw = new StringWriter();
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.transform(new DOMSource((org.w3c.dom.Node) node), new StreamResult(sw));
		} catch (TransformerException te) {
			System.out.println("nodeToString Transformer Exception");
		}
		return sw.toString();
	}

}
