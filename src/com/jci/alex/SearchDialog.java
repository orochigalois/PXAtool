package com.jci.alex;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.jci.alex.datainterface.DataInterfaceLabel;

public class SearchDialog extends JDialog
{
	
	public SearchDialog(JFrame parent, final MainPanel mp,
			final JMenuItem searchItem)
	{

		super(parent, "search", true);
		
		JScrollPane sP1;
		
		final JTextField txtInput = new JTextField();
		
		txtInput.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		txtInput.setBorder(new LineBorder(Color.GRAY));
		JButton btnCancel = new JButton("Cancel");
		JButton btnSearch = new JButton("Search");
		
		
		final JCheckBox chk = new JCheckBox("Case sensitive");
		
		
		String sComb[] ={ "All", "NodeName", "AttributeName", "AttributeValue"};
		final JComboBox comb = new JComboBox(sComb);

		comb.setSelectedIndex(0);
		comb.setBackground(Color.white);

		
		
		
		
		Box boxNorth = Box.createVerticalBox();
		Box box1=Box.createHorizontalBox();
		Box box2=Box.createHorizontalBox();
		
		
		box1.add(Box.createGlue());
		box1.add(txtInput);
		box1.add(btnSearch);
		box1.add(btnCancel);
	
		
		
		box2.add(new JLabel("Scope  :  "));
		box2.add(comb);
		box2.add(new JLabel("              "));
		box2.add(chk);
		
		JPanel jp2 = new JPanel();
		jp2.setBorder(new TitledBorder("Option"));
		jp2.add(box2);
		
		boxNorth.add(Box.createGlue());
		boxNorth.add(box1);
		boxNorth.add(jp2);
		// boxNorth.setBorder(new LineBorder(Color.BLUE));
		
		getContentPane().add(boxNorth, "North");
		
		final DefaultListModel listModel = new DefaultListModel();
		
		JList list = new JList(listModel);
		// list.setSelectedIndex(1);
		// list.setBorder(new LineBorder(Color.BLUE));
		sP1 = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sP1.getVerticalScrollBar().setUnitIncrement(10);
		sP1.setBorder(ShadowBorder.getInstance());
		
		getContentPane().add(sP1, "Center");
		
		JRootPane rootPane = this.getRootPane();
	    rootPane.setDefaultButton(btnSearch);
	    
		
	    
	    
		
		
		list.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent evt)
			{
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2)
				{
					int index = list.locationToIndex(evt.getPoint());
					
					mp.jumpToTheTarget(index);
					
				}
				
			}
		});
		
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				
					listModel.clear();
					Constant.vSearchResult.clear();
					try
					{
					mp.search(txtInput.getText(),comb.getSelectedIndex(),chk.isSelected());
					} catch (ParserConfigurationException e)
					{
						e.printStackTrace();
					} catch (SAXException e)
					{
						e.printStackTrace();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
					
					if(Constant.vSearchResult.size()==0)
						JOptionPane.showMessageDialog(null, "Found Nothing!");
					for (int i = 0; i < Constant.vSearchResult.size(); i++)
					{
						listModel.addElement(Constant.allPanelPath
								.elementAt(Constant.vSearchResult.elementAt(i).index)
								+ " :    "
								+ Constant.vSearchResult.elementAt(i).tp
										.toString().replace(": null", ""));
					}
				
					// TODO Auto-generated catch block
			}
		});
		
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				setVisible(false);
				searchItem.setEnabled(true);
				
			}
		});
		
		setSize(400, 600);
	}
	
}