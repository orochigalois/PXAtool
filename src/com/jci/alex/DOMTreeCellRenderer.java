package com.jci.alex;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * This class renders an XML node.
 */
public class DOMTreeCellRenderer extends DefaultTreeCellRenderer
{
	public DOMTreeCellRenderer()
	{
		
		// setOpenIcon(new ImageIcon("c:\\star.png"));
		// setClosedIcon(new ImageIcon("c:\\leaf.gif"));
		// setLeafIcon(new ImageIcon("c:\\leaf.gif"));
		//
		// setBackgroundNonSelectionColor(new Color(255, 255, 221));
		// setBackgroundSelectionColor(new Color(0, 0, 128));
		// setBorderSelectionColor(Color.black);
		// setTextSelectionColor(Color.white);
		// setTextNonSelectionColor(Color.blue);
		
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus)
	{
		
		this.selected = selected;
		
		if (!selected)
			setForeground(Color.black);
		else
			setForeground(Color.white);
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		
		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node selectedNode = (org.w3c.dom.Node) nodeInfo;
		
		if (selectedNode instanceof Element)
		{
			// nodename equals "pxa" which means it's a href node
			if (selectedNode.getPreviousSibling().getNodeName() == "pxa")
				setForeground(Color.red);
			if(((Element) selectedNode).getAttributes().getNamedItem("partName")!=null)
				setText(((Element) selectedNode).getTagName()+" "+((Element) selectedNode).getAttributes().getNamedItem("partName").getNodeValue());
			else if(((Element) selectedNode).getAttributes().getNamedItem("id")!=null)
				setText(((Element) selectedNode).getTagName()+" "+((Element) selectedNode).getAttributes().getNamedItem("id").getNodeValue());
			else if(((Element) selectedNode).getAttributes().getNamedItem("name")!=null)
				setText(((Element) selectedNode).getTagName()+" "+((Element) selectedNode).getAttributes().getNamedItem("name").getNodeValue());
			else
				setText(((Element) selectedNode).getTagName());
			
		}
		else
			setText(null);
		
		// return elementPanel((Element) node);
		
		return this;
		
	}
	
	public static JPanel elementPanel(Element e)
	{
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		
		panel.add(new JLabel(e.getTagName()));
		final NamedNodeMap map = e.getAttributes();
		
		JTable table = new JTable(new AbstractTableModel()
		{
			public int getRowCount()
			{
				return map.getLength();
			}
			
			public int getColumnCount()
			{
				return 2;
			}
			
			public Object getValueAt(int r, int c)
			{
				return c == 0 ? map.item(r).getNodeName() : map.item(r)
						.getNodeValue();
			}
			
		});
		TableColumn column = null;
		
		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(500);
		
		panel.add(table);
		return panel;
	}
	
	public static String characterString(CharacterData node)
	{
		StringBuilder builder = new StringBuilder(node.getData());
		for (int i = 0; i < builder.length(); i++)
		{
			if (builder.charAt(i) == '\r')
			{
				builder.replace(i, i + 1, "\\r");
				i++;
			}
			else if (builder.charAt(i) == '\n')
			{
				builder.replace(i, i + 1, "\\n");
				i++;
			}
			else if (builder.charAt(i) == '\t')
			{
				builder.replace(i, i + 1, "\\t");
				i++;
			}
		}
		if (node instanceof CDATASection)
			builder.insert(0, "CDATASection: ");
		else if (node instanceof Text)
			builder.insert(0, "Text: ");
		else if (node instanceof Comment)
			builder.insert(0, "Comment: ");
		
		return builder.toString();
	}
}
