package com.jci.alex.generator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jci.alex.Constant;
import com.jci.alex.util.Util;

public class Z_DataStructure
{
	public static final String GeneratorVersion = "1.0";

	private static DocumentBuilder builder;

	public static Vector<String> fullPaths = new Vector<String>();

	public static Vector<DefaultTreeModel> treeModels = new Vector<DefaultTreeModel>();

	public static void initAllTreeModel() throws ParserConfigurationException, SAXException, IOException
	{

		fill_fullPaths();
		for (int i = 0; i < fullPaths.size(); i++)
		{
			File file = new File(fullPaths.elementAt(i));

			String path = fullPaths.elementAt(i).substring(0, fullPaths.elementAt(i).lastIndexOf(File.separator));
			if (builder == null)
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

				builder = factory.newDocumentBuilder();

			}

			Element docRoot = builder.parse(file).getDocumentElement();

			DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode(docRoot);

			createTree(treeRoot, path);

			DefaultTreeModel treeModel = new DefaultTreeModel(treeRoot);

			treeModels.add(treeModel);

		}

	}

	public static void fill_fullPaths() throws ParserConfigurationException, SAXException, IOException
	{
		for (int i = 0; i < Constant.allPanelPath.size(); i++)
		{

			String fullPath = Constant.workingPath + "\\" + Constant.allPanelPath.elementAt(i).replace('/', '\\');

			fullPaths.add(fullPath);

			File file = new File(fullPath);

			String path = fullPath.substring(0, fullPath.lastIndexOf(File.separator));

			if (builder == null)
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				builder = factory.newDocumentBuilder();
				Element docRoot = builder.parse(file).getDocumentElement();

				DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode(docRoot);
				traverseTree(treeRoot, path);

			}

		}
	}

	private static void traverseTree(DefaultMutableTreeNode currentNode, String currentPath) throws IOException
	{

		String hrefPath;
		org.w3c.dom.Document hrefDoc = null;

		org.w3c.dom.Node domNode = Util.getDomNodeFromTreeNode(currentNode);

		NodeList list = domNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
		{
			// If list.item(i) is an Element, then list.item(i).getAttributes()
			// definitely does not equal null.So, here we get all nodes that we
			// are
			// interested in
			if (list.item(i) instanceof Element)
			{

				// This node should be expanded
				if (list.item(i).getAttributes().getNamedItem("href") != null)
				{
					String href = list.item(i).getAttributes().getNamedItem("href").getNodeValue();
					String fullPath = currentPath + "\\" + href.replace('/', '\\');
					fullPath = FilenameUtils.normalize(fullPath);

					fullPaths.add(fullPath);

					File workingFile = new File(fullPath);

					String absolutePath = workingFile.getAbsolutePath();

					hrefPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
					try
					{
						hrefDoc = builder.parse(workingFile);
					}
					catch (SAXException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(list.item(i));
					traverseTree(newNode, hrefPath);

					DefaultMutableTreeNode newNodeHref = new DefaultMutableTreeNode(hrefDoc.getDocumentElement());
					traverseTree(newNodeHref, hrefPath);
				}
				// Normal nodes
				else
				{
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(list.item(i));

					if (list.item(i).getChildNodes().getLength() != 0)
					{
						traverseTree(newNode, currentPath);
					}

				}

			}
		}

	}

	private static void createTree(DefaultMutableTreeNode currentNode, String currentPath) throws IOException
	{

		String hrefPath;
		org.w3c.dom.Document hrefDoc = null;

		Object nodeInfo = currentNode.getUserObject();
		org.w3c.dom.Node selectedNode = (org.w3c.dom.Node) nodeInfo;

		NodeList list = selectedNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
		{
			// If list.item(i) is an Element, then list.item(i).getAttributes()
			// definitely does not equal null.So, here we get all nodes that we
			// are
			// interested in
			if (list.item(i) instanceof Element)
			{

				// This node should be expanded
				if (list.item(i).getAttributes().getNamedItem("href") != null)
				{
					String href = list.item(i).getAttributes().getNamedItem("href").getNodeValue();
					String fullPath = currentPath + "\\" + href.replace('/', '\\');
					fullPath = FilenameUtils.normalize(fullPath);

					File workingFile = new File(fullPath);
					String absolutePath = workingFile.getAbsolutePath();

					hrefPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
					try
					{
						hrefDoc = builder.parse(workingFile);
					}
					catch (SAXException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(list.item(i));
					currentNode.add(newNode);

					createTree(newNode, hrefPath);

					DefaultMutableTreeNode newNodeHref = new DefaultMutableTreeNode(hrefDoc.getDocumentElement());
					newNode.add(newNodeHref);

					createTree(newNodeHref, hrefPath);
				}
				// Normal nodes
				else
				{
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(list.item(i));
					currentNode.add(newNode);

					if (list.item(i).getChildNodes().getLength() != 0)
					{
						createTree(newNode, currentPath);
					}

				}

			}
		}

	}
	
	
	
	/***
	 * Return the LineNumber of node in PXA file --example-- in
	 * alert_5100_MenuAlert_Z2_Alertes(i=0),line 16, we have this:
	 * <Scale yScale="-1" />
	 * 
	 * we put domNode="<Scale yScale="-1" />" then Return=16
	 */
	public static String getNodeLineNumber(org.w3c.dom.Node domNode, int i) {

		FileReader fr = null;
		LineNumberReader lnr = null;
		String str;
		int lineNumber = 0;

		try {


			// create new reader
			fr = new FileReader(fullPaths.elementAt(i));
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

}
