package com.jci.alex;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Element;

public class TreeTransferHandler extends TransferHandler
{
	protected Transferable createTransferable(JComponent c)
	{
		String returnStr="";
		JTree tree = (JTree) c;
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		
		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node selectedNode = (org.w3c.dom.Node) nodeInfo;
		
		if(((Element) selectedNode).getAttributes().getNamedItem("partName")!=null)
			returnStr=(((Element) selectedNode).getTagName()+" "+((Element) selectedNode).getAttributes().getNamedItem("partName").getNodeValue());
		else if(((Element) selectedNode).getAttributes().getNamedItem("id")!=null)
			returnStr=(((Element) selectedNode).getTagName()+" "+((Element) selectedNode).getAttributes().getNamedItem("id").getNodeValue());
		else if(((Element) selectedNode).getAttributes().getNamedItem("name")!=null)
			returnStr=(((Element) selectedNode).getTagName()+" "+((Element) selectedNode).getAttributes().getNamedItem("name").getNodeValue());
		else
			returnStr=(((Element) selectedNode).getTagName());
		
	
		return new StringSelection(returnStr);
	}
	public int getSourceActions(JComponent c)
	{
		return COPY;
	}
	public boolean canImport(TransferHandler.TransferSupport support)
	{
		// we only import Strings
		if (!support.isDataFlavorSupported(DataFlavor.stringFlavor))
		{
			return false;
		}
		return true;
	}
}
