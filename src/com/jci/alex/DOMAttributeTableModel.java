package com.jci.alex;

import java.awt.Container;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreeModel;

import org.w3c.dom.NamedNodeMap;

@SuppressWarnings("serial")
public class DOMAttributeTableModel extends AbstractTableModel
{

	NamedNodeMap map = null;
	Vector attrMap = null;

	String absoluteX = null;
	String absoluteY = null;
	String user_config_x = null;
	String user_config_y = null;
	String user_config_source = null;
	String user_config_state=null;

	Vector imageState;

	Object[] data_user_config = null;
	boolean[] thisRowShouldBeRed = null;


	public DOMAttributeTableModel(NamedNodeMap mapIN, Vector attrMapIN,
			String absoluteX, String absoluteY, String user_config_x,
			String user_config_y, String user_config_source, String user_config_state,Vector imageState)
	{
		this.map = mapIN;
		this.attrMap = attrMapIN;
		this.absoluteX = absoluteX;
		this.absoluteY = absoluteY;

		this.user_config_x = user_config_x;
		this.user_config_y = user_config_y;
		this.user_config_source = user_config_source;
		this.user_config_state=user_config_state;

		this.imageState = imageState;


		// override default property value defined in XSD files
		for (int i = 0; i < map.getLength(); i++)
		{
			for (int j = 0; j < attrMap.size(); j++)
			{
				AttributeData dataTmp = (AttributeData) (attrMap.elementAt(j));
				if (dataTmp.getAttrName().equals(map.item(i).getNodeName()))
				{
					attrMap.remove(j);
				}
			}
		}

		this.data_user_config = new Object[getRowCount()];
		this.thisRowShouldBeRed = new boolean[getRowCount()];

		for (int i = 0; i < map.getLength(); i++)
		{
			if (map.item(i).getNodeName() == "x")
				data_user_config[i + attrMap.size()] = this.user_config_x;
			if (map.item(i).getNodeName() == "y")
				data_user_config[i + attrMap.size()] = this.user_config_y;
			if (map.item(i).getNodeName() == "source")
				data_user_config[i + attrMap.size()] = this.user_config_source;

		}
		if(getRowCount()==attrMap.size() + map.getLength() + 1)
			data_user_config[map.getLength() + attrMap.size()]=user_config_state;

	}

	@Override
	public int getColumnCount()
	{
		return 5;
	}

	@Override
	public int getRowCount()
	{
		if (this.imageState == null)
			return attrMap.size() + map.getLength();
		else
		{
			if (this.imageState.isEmpty())
				return attrMap.size() + map.getLength();
			else
				return attrMap.size() + map.getLength() + 1;
		}
	}

	@Override
	public Object getValueAt(int row, int column)
	{

		if (row < attrMap.size())
		{
			AttributeData dataTmp = (AttributeData) (attrMap.elementAt(row));

			switch (column)
			{
			case 0:
				return dataTmp.getAttrName();
			case 1:
				return dataTmp.getAttrDefault();
			case 2:
				return dataTmp.getAttrType();
			case 3:
				return dataTmp.getAttrUse();
			case 4:
				return new String();

			}

			return new String();

		}
		else if (row < attrMap.size() + map.getLength())
		{
			switch (column)
			{
			case 0:
				return map.item(row - attrMap.size()).getNodeName();
			case 1:
				if (map.item(row - attrMap.size()).getNodeName() == "x")
					return map.item(row - attrMap.size()).getNodeValue()
							+ " / absolute: " + absoluteX;
				else if (map.item(row - attrMap.size()).getNodeName() == "y")
					return map.item(row - attrMap.size()).getNodeValue()
							+ " / absolute: " + absoluteY;
				else
					return map.item(row - attrMap.size()).getNodeValue();

			case 2:
				return new String();
			case 3:
				return new String();
			case 4:
				return data_user_config[row];

			}

			return new String();
		}
		else
		{
			switch (column)
			{
			case 0:
				return "State";
			case 1:
				return new String();
			case 2:
				return new String();
			case 3:
				return new String();
			case 4:
				return data_user_config[row];

			}
			return new String();

		}

	}


	public int getColorIndex(int row)
	{
		if (row < attrMap.size())
			return 0;
		else
		{
			if (thisRowShouldBeRed[row])
				return 2;
			else
				return 1;
		}

	}

	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return true;
	}

	public void setValueAt(Object value, int row, int col)
	{

		if (col != 4)
			return;

		if (row < attrMap.size())
			return;
		else if (row < attrMap.size() + map.getLength())
		{
			if (map.item(row - attrMap.size()).getNodeName() != "x"
					&& map.item(row - attrMap.size()).getNodeName() != "y"
					&& map.item(row - attrMap.size()).getNodeName() != "source")
			{
				if(value!=null)
					if(value.toString().length() != 0)
						thisRowShouldBeRed[row] = true;
			}
			else
			{
				if (map.item(row - attrMap.size()).getNodeName().equals("x")
						|| map.item(row - attrMap.size()).getNodeName()
								.equals("y"))
				{
					if(value!=null)
						if (!Constant.isNumeric(value.toString())
								&& value.toString().length() != 0)
						{
							JOptionPane.showMessageDialog(null,
									"expect valid value");
							return;
						}

				}

			}
			data_user_config[row] = value;
		}
		else
		{
			data_user_config[row] = value;
		}

		fireTableCellUpdated(row, col);
	}

}
