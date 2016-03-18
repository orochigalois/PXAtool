package com.jci.alex;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DOMAttributeTableCellRenderer extends DefaultTableCellRenderer
{
	
	// Color backgroundColor = getBackground();
	
	public DOMAttributeTableCellRenderer()
	{
		super();
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		DOMAttributeTableModel model = (DOMAttributeTableModel) table
				.getModel();
		
		switch(model.getColorIndex(row))
		{
		case 0:
			c.setForeground(Color.green);
			break;
		case 1:
			c.setForeground(Color.blue);
			break;
		case 2:
			c.setForeground(Color.red);
			break;
		default:
				break;
		}
		
		
//		if (model.isNeedColored(row))
//		{
//			//c.setBackground(Color.yellow);
//			c.setForeground(Color.green);
//		}
//		else if (!isSelected)
//		{
//			//c.setBackground(Color.red);
//			c.setForeground(Color.red);
//		}
		return c;
	}
}