package com.jci.alex;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
//import org.droiddraw.Editor;
//import org.droiddraw.gui.ViewerListener;
//import org.droiddraw.gui.WidgetPanel;
import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.MultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout.Node;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.jci.alex.datainterface.DataInterfaceContainer;
import com.jci.alex.datainterface.DataInterfaceGroup;
import com.jci.alex.datainterface.DataInterfaceImage;
import com.jci.alex.datainterface.DataInterfaceImageIndex;
import com.jci.alex.datainterface.DataInterfaceLabel;
import com.jci.alex.datainterface.DataInterfacePanel;
import com.jci.alex.datainterface.ImageState;
import com.jci.alex.draw.*;
import com.jci.alex.generator.A_Header;
import com.jci.alex.generator.B_Includes;
import com.jci.alex.generator.C_Typedefs;
import com.jci.alex.generator.D_InternalState;
import com.jci.alex.generator.E_PartNameAccessor;
import com.jci.alex.generator.F_Constructor;
import com.jci.alex.generator.G_AccessorToLocalObjects;
import com.jci.alex.generator.H_AccessorsToPrefab;
import com.jci.alex.generator.I_NestedPXAObjects;
import com.jci.alex.generator.J_Keys;
import com.jci.alex.generator.K_NestedPXAPrefab;
import com.jci.alex.generator.Z_DataStructure;
import com.jci.alex.util.Util;

/**
 * Main Panel for everything
 * 
 * @version 1.0 2014-06-03
 * @author alex
 */
public class MainPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DocumentBuilder builder;

	MultiSplitPane multiSplitPane = null;
	
	/* left top */
	public JList objList;

	JScrollPane sP1;

	/* left middle */
	JTree tree = new JTree();

	JScrollPane sP2;

	/* left bottom */

	JCheckBox chk21 = new JCheckBox("Container Visible");
	JCheckBox chk22 = new JCheckBox("Group Visible");
	JButton tbtn = new JButton("Load");
	// JScrollPane sP21;
	JPanel sP22 = new JPanel();

	JPanel sP22_language = new JPanel();
	JPanel sP22_config_visible = new JPanel();

	/* middle */
	// RenderPanel rP;
	RenderPanel rP = new RenderPanel();
	JFrame Viewframe = new JFrame();
	JScrollPane sP3;

	/* right */
	JTable table = new JTable();
	JScrollPane sP4;

	/* bottom */
	JTextArea textArea = new JTextArea();
	JScrollPane sP5;

	/* XsdHandler */
	XsdHandler xh = null;

	JComboBox tLanguage;
	String sLanguage[] =
	{ "FR", "EN", "DE", "NL", "ES", "PT", "IT", "TR", "PL", "ZH", "BU", "RU", "BR", "HR", "HU", "CS", "BG", "DA", "ET",
			"FI", "NO", "RO", "SR", "SV", "UA", "AR", "EL", "VI", "FA", "KR", "IL", "JA" };

	public MainPanel(org.w3c.dom.Document doc) throws IOException
	{

		try
		{
			xh = new XsdHandler(doc);
		}
		catch (ParserConfigurationException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (SAXException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		setLayout(new BorderLayout());

		multiSplitPane = new MultiSplitPane();
		MultiSplitLayout mspLayout = multiSplitPane.getMultiSplitLayout();

		Node model = null;
		String layoutDef = "(COLUMN (ROW (COLUMN left.top left.middle) (COLUMN middle.top middle.bottom middle ) right) bottom)";

		model = MultiSplitLayout.parseModel(layoutDef);

		mspLayout.setModel(model);

		/* left top */
		NodeList nl = doc.getElementsByTagName("Panel");
		Constant.iPanelSize = nl.getLength();
		Vector<String> v = new Vector<String>();

		for (int i = 0; i < nl.getLength(); i++)
		{
			String href = nl.item(i).getAttributes().getNamedItem("href").getNodeValue();

			v.add((href.substring(href.lastIndexOf('/') + 1)).replaceAll(".pxa", ""));

			Constant.allPanelPath.add(nl.item(i).getAttributes().getNamedItem("href").getNodeValue());

		}
		// Collections.sort(v);

		// for (int i = 0; i < v.size(); i++)
		// {
		// for (int j = 0; j < nl.getLength(); j++)
		// {
		// if (nl.item(j).getAttributes().getNamedItem("id")
		// .getNodeValue().toUpperCase().equals(v.elementAt(i).toUpperCase()))
		// Constant.allPanelPath.add(nl.item(j).getAttributes()
		// .getNamedItem("href").getNodeValue());
		// }
		//
		// }
		objList = new JList(v);

		objList.setSelectedIndex(0);
		objList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ListSelectionListener listSelectionListener = new ListSelectionListener()
		{

			public void valueChanged(ListSelectionEvent listSelectionEvent)
			{

				boolean adjust = listSelectionEvent.getValueIsAdjusting();

				if (!adjust)
				{

					try
					{
						Constant.currentPXAname = objList.getSelectedValue().toString();
						Constant.whichPanel = objList.getSelectedIndex();
						updateTree(Constant.whichPanel);

						if (Constant.hasLoadedExcel)
						{
							DataInterfaceLabel diLabel;
							try
							{
								for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel)
										.size(); i++)
								{

									if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
											.elementAt(i) instanceof DataInterfaceLabel)
									{

										diLabel = (DataInterfaceLabel) Constant.allNodesForRendering
												.elementAt(Constant.whichPanel).elementAt(i);

										if (diLabel.id != null && Constant.excelPath != null)

										{
											diLabel.textFromExcel = ReadExcel.ExcelOpen(Constant.excelPath, diLabel.id,
													sLanguage[tLanguage.getSelectedIndex()]);
										}
									}

								}

							}
							catch (IOException e1)
							{
								e1.printStackTrace();
							}
						}
						rP.draw();
						Viewframe.add(rP);

					}
					catch (ParserConfigurationException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
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

				}
			}
		};
		objList.addListSelectionListener(listSelectionListener);

		ItemListener languageListener = new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					tLanguage = (JComboBox) e.getSource();
					tLanguage.setSelectedIndex(tLanguage.getSelectedIndex());
					DataInterfaceLabel diLabel;

					try
					{
						for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
						{

							if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i) instanceof DataInterfaceLabel)
							{

								diLabel = (DataInterfaceLabel) Constant.allNodesForRendering
										.elementAt(Constant.whichPanel).elementAt(i);

								if (diLabel.id != null && Constant.excelPath != null)

								{
									diLabel.textFromExcel = ReadExcel.ExcelOpen(Constant.excelPath, diLabel.id,
											sLanguage[tLanguage.getSelectedIndex()]);
								}
							}

						}
						rP.draw();
						Viewframe.add(rP);
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}

			}
		};
		sP1 = new JScrollPane(objList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sP1.getVerticalScrollBar().setUnitIncrement(10);
		sP1.setBorder(ShadowBorder.getInstance());

		/* left bottom */
		// Long Chen add language list
		tLanguage = new JComboBox(sLanguage);
		tLanguage.addItemListener(languageListener);
		tLanguage.setSelectedIndex(0);
		tLanguage.setEnabled(false);
		tLanguage.setBackground(Color.white);
		sP22.setLayout(new GridLayout(2, 1));

		sP22_language.setBorder(new TitledBorder("Load Language Excel"));
		sP22_language.add(tbtn);
		sP22_language.add(new JLabel("    language:"));
		sP22_language.add(tLanguage);

		sP22_config_visible.setBorder(new TitledBorder("Visible Configuration"));

		/* left middle */

		sP2 = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sP2.getVerticalScrollBar().setUnitIncrement(10);
		sP2.setBorder(ShadowBorder.getInstance());

		TreeTransferHandler th = new TreeTransferHandler();
		tree.setTransferHandler(th);
		/* left bottom */

		chk21.setEnabled(false);
		chk21.setSelected(true);

		chk21.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				DataInterfaceImage diImage;
				DataInterfaceLabel diLabel;
				JCheckBox cb = (JCheckBox) event.getSource();
				if (cb.isSelected())
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceImage)
						{

							diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diImage.tp))
							{
								diImage.visible = true;
							}
						}
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceLabel)
						{

							diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diLabel.tp))
							{
								diLabel.visible = true;
							}
						}

					}
				}
				else
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceImage)
						{

							diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diImage.tp))
							{
								diImage.visible = false;
							}
						}
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceLabel)
						{

							diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diLabel.tp))
							{
								diLabel.visible = false;
							}
						}

					}
				}

				rP.draw();
				Viewframe.add(rP);
			}
		});

		/*
		 * sP21 = new JScrollPane(chk21, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		 * JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 * sP21.getVerticalScrollBar().setUnitIncrement(10);
		 * sP21.setBorder(ShadowBorder.getInstance());
		 */
		sP22_config_visible.add(chk21);
		// add Group checkbox
		chk22.setEnabled(false);
		chk22.setSelected(true);

		chk22.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				DataInterfaceImage diImage;
				DataInterfaceLabel diLabel;
				JCheckBox cb = (JCheckBox) event.getSource();
				if (cb.isSelected())
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceImage)
						{

							diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diImage.tp))
							{
								diImage.visible = true;
							}
						}
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceLabel)
						{

							diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diLabel.tp))
							{
								diLabel.visible = true;
							}
						}

					}
				}
				else
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceImage)
						{

							diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diImage.tp))
							{
								diImage.visible = false;
							}
						}
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceLabel)
						{

							diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diLabel.tp))
							{
								diLabel.visible = false;
							}
						}

					}
				}

				rP.draw();
				Viewframe.add(rP);
			}

		});
		sP22_config_visible.add(chk22);

		sP22.add(sP22_language);
		sP22.add(sP22_config_visible);

		/* middle */
		rP.draw();

		/*
		 * sP3 = new JScrollPane(rP, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		 * JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); sP3.setPreferredSize(new
		 * Dimension(600, 0)); sP3.getVerticalScrollBar().setUnitIncrement(10);
		 * sP3.setBorder(ShadowBorder.getInstance());
		 */

		/* right */
		sP4 = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sP4.getVerticalScrollBar().setUnitIncrement(10);
		sP4.setBorder(ShadowBorder.getInstance());

		/* bottom */
		sP5 = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sP5.getVerticalScrollBar().setUnitIncrement(10);
		sP5.setBorder(ShadowBorder.getInstance());

		/* add to multiSplitPane */
		multiSplitPane.add(sP1, "left.top");
		multiSplitPane.add(sP2, "left.middle");
		// multiSplitPane.add(sP21, "middle.bottom");
		multiSplitPane.add(sP22, "middle.top");
		// multiSplitPane.add(sP3, "middle");
		multiSplitPane.add(sP4, "right");
		multiSplitPane.add(sP5, "bottom");

		add(multiSplitPane, BorderLayout.CENTER);

		// Long Chen add load excel file 7.2.2014
		tbtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				openExcelFile();
			}

		});

		/* alex updated: Table size */
		TableColumnModelListener tableColumnModelListener = new TableColumnModelListener()
		{

			public void columnMarginChanged(ChangeEvent e)
			{

				if (!Constant.processing)
				{
					Constant.Column0 = table.getColumnModel().getColumn(0).getWidth();

					Constant.Column1 = table.getColumnModel().getColumn(1).getWidth();

					Constant.Column2 = table.getColumnModel().getColumn(2).getWidth();

					Constant.Column3 = table.getColumnModel().getColumn(3).getWidth();
					Constant.Column4 = table.getColumnModel().getColumn(4).getWidth();
				}

			}

			@Override
			public void columnAdded(TableColumnModelEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void columnMoved(TableColumnModelEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void columnRemoved(TableColumnModelEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void columnSelectionChanged(ListSelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}

		};
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.addColumnModelListener(tableColumnModelListener);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		/* alex tree */
		tree.addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent event)
			{
				DataInterfaceImage diImage = null;
				DataInterfaceLabel diLabel;
				DataInterfacePanel diPanel;

				DataInterfaceImageIndex diImageIndex;
				DataInterfaceContainer diContainer;
				DataInterfaceGroup diGroup;

				boolean have_visible_child = false;
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (node == null)
					return;

				Object nodeInfo = node.getUserObject();
				org.w3c.dom.Node selectedNode = (org.w3c.dom.Node) nodeInfo;

				Constant.currentTreePath = tree.getSelectionPath();
				Constant.currentNodeName = selectedNode.getNodeName();

				/*
				 * 1.get absolute position 2.get w h for imageindex and
				 * container
				 */
				String absolute_x = "", absolute_y = "", user_config_x = "", user_config_y = "",
						user_config_source = "", user_config_state = "";
				Vector imageState = null;
				if (selectedNode.getNodeName().equals("Image"))
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceImage)
						{

							diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAEqualB(Constant.currentTreePath, diImage.tp))
							{
								absolute_x = diImage.absolute_x;
								absolute_y = diImage.absolute_y;
								user_config_x = diImage.user_config_x;
								user_config_y = diImage.user_config_y;
								user_config_source = diImage.user_config_source;
								user_config_state = diImage.user_config_state;
								imageState = diImage.states;
								break;
							}
						}

					}

				}
				if (selectedNode.getNodeName().equals("Label"))
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceLabel)
						{

							diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAEqualB(Constant.currentTreePath, diLabel.tp))
							{
								absolute_x = diLabel.absolute_x;
								absolute_y = diLabel.absolute_y;
								user_config_x = diLabel.user_config_x;
								user_config_y = diLabel.user_config_y;
								break;
							}
						}

					}

				}

				if (selectedNode.getNodeName().equals("Container"))
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceContainer)
						{

							diContainer = (DataInterfaceContainer) Constant.allNodesForRendering
									.elementAt(Constant.whichPanel).elementAt(i);

							if (Constant.isAEqualB(Constant.currentTreePath, diContainer.tp))
							{
								absolute_x = diContainer.absolute_x;
								absolute_y = diContainer.absolute_y;
								user_config_x = diContainer.user_config_x;
								user_config_y = diContainer.user_config_y;
								break;
							}
						}

					}

				}
				if (selectedNode.getNodeName().equals("Group"))
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceGroup)
						{

							diGroup = (DataInterfaceGroup) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAEqualB(Constant.currentTreePath, diGroup.tp))
							{
								absolute_x = diGroup.absolute_x;
								absolute_y = diGroup.absolute_y;
								user_config_x = diGroup.user_config_x;
								user_config_y = diGroup.user_config_y;
								break;
							}
						}

					}

				}

				updateTable(selectedNode.getAttributes(), selectedNode.getNodeName(), absolute_x, absolute_y,
						user_config_x, user_config_y, user_config_source, user_config_state, imageState, diImage);

				/* clear all highlight */
				for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
				{
					if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i) instanceof DataInterfaceImage)
					{

						diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i);

						diImage.highlight = false;
					}
					if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i) instanceof DataInterfaceLabel)
					{

						diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i);
						diLabel.highlight = false;

					}

					if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i) instanceof DataInterfacePanel)
					{

						diPanel = (DataInterfacePanel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i);
						diPanel.highlight = false;

					}
					if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i) instanceof DataInterfaceImageIndex)
					{

						diImageIndex = (DataInterfaceImageIndex) Constant.allNodesForRendering
								.elementAt(Constant.whichPanel).elementAt(i);
						diImageIndex.highlight = false;

					}
					if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i) instanceof DataInterfaceContainer)
					{

						diContainer = (DataInterfaceContainer) Constant.allNodesForRendering
								.elementAt(Constant.whichPanel).elementAt(i);
						diContainer.highlight = false;

					}
					if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i) instanceof DataInterfaceGroup)
					{

						diGroup = (DataInterfaceGroup) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i);
						diGroup.highlight = false;

					}

				}
				/* set highlight */
				if (selectedNode.getNodeName().equals("Panel"))
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfacePanel)
						{

							diPanel = (DataInterfacePanel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAEqualB(Constant.currentTreePath, diPanel.tp))
							{
								diPanel.highlight = true;
								break;
							}
						}

					}

				}
				if (selectedNode.getNodeName().equals("ImageIndex"))
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceImageIndex)
						{

							diImageIndex = (DataInterfaceImageIndex) Constant.allNodesForRendering
									.elementAt(Constant.whichPanel).elementAt(i);

							if (Constant.isAEqualB(Constant.currentTreePath, diImageIndex.tp))
							{
								diImageIndex.highlight = true;
								break;
							}
						}

					}
				}
				if (selectedNode.getNodeName().equals("Container"))
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceContainer)
						{

							diContainer = (DataInterfaceContainer) Constant.allNodesForRendering
									.elementAt(Constant.whichPanel).elementAt(i);

							if (Constant.isAEqualB(Constant.currentTreePath, diContainer.tp))
							{
								diContainer.highlight = true;
								break;
							}
						}

					}

				}
				if (selectedNode.getNodeName().equals("Group"))
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceGroup)
						{

							diGroup = (DataInterfaceGroup) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAEqualB(Constant.currentTreePath, diGroup.tp))
							{
								diGroup.highlight = true;
								break;
							}
						}

					}

				}
				if (selectedNode.getNodeName().equals("Image"))
				{

					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceImage)
						{

							diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAEqualB(Constant.currentTreePath, diImage.tp))
							{
								diImage.highlight = true;
								break;
							}
						}

					}

				}
				if (selectedNode.getNodeName().equals("Label"))
				{
					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceLabel)
						{

							diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAEqualB(Constant.currentTreePath, diLabel.tp))
							{
								diLabel.highlight = true;
								break;
							}
						}

					}
				}

				/* for state change */
				// if (selectedNode.getNodeName().equals("PropertyChanges"))
				// {
				//
				// for (int i = 0; i < Constant.allNodesForRendering
				// .elementAt(Constant.whichPanel).size(); i++)
				// {
				// if (Constant.allNodesForRendering.elementAt(
				// Constant.whichPanel).elementAt(i) instanceof
				// DataInterfaceImage)
				// {
				//
				// diImage = (DataInterfaceImage) Constant.allNodesForRendering
				// .elementAt(Constant.whichPanel)
				// .elementAt(i);
				//
				// if (diImage.id.equals(selectedNode.getAttributes()
				// .getNamedItem("target").getNodeValue())
				// && Constant
				// .isA_and_B_belong_to_the_same_href_node(
				// diImage.tp,
				// Constant.currentTreePath))
				// {
				// if (selectedNode.getAttributes().getNamedItem(
				// "source") != null)
				// {
				//
				// diImage.user_config_source = selectedNode
				// .getAttributes()
				// .getNamedItem("source")
				// .getNodeValue();
				//
				// diImage.highlight = true;
				//
				// }
				//
				// if (selectedNode.getAttributes().getNamedItem(
				// "x") != null)
				// {
				// Integer tmp = Integer.parseInt(diImage.x)
				// + Integer.parseInt(selectedNode
				// .getAttributes()
				// .getNamedItem("x")
				// .getNodeValue());
				// diImage.user_config_x = tmp.toString();
				// }
				//
				// if (selectedNode.getAttributes().getNamedItem(
				// "y") != null)
				// {
				// Integer tmp = Integer.parseInt(diImage.y)
				// + Integer.parseInt(selectedNode
				// .getAttributes()
				// .getNamedItem("y")
				// .getNodeValue());
				// diImage.user_config_y = tmp.toString();
				// }
				// }
				//
				// }
				//
				// }
				// }

				rP.draw();

				Viewframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				Viewframe.setSize(Constant.screenWidth, Constant.screenHeight);

				Container c = Viewframe.getContentPane();

				Dimension d = new Dimension(Constant.screenWidth, Constant.screenHeight);
				c.setPreferredSize(d);
				Viewframe.pack();
				Viewframe.setResizable(false);

				Viewframe.add(rP);

				if (Constant.shouldShowFrame)
					Viewframe.setVisible(true);
				else
					Viewframe.setVisible(false);

				if (selectedNode.getNodeName().equals("Container"))
				{
					chk21.setEnabled(true);
					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceImage)
						{

							diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diImage.tp))
							{
								if (diImage.visible)
									have_visible_child = true;
							}
						}
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceLabel)
						{

							diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diLabel.tp))
							{
								if (diLabel.visible)
									have_visible_child = true;
							}
						}

					}
					if (have_visible_child)
						chk21.setSelected(true);
					else
						chk21.setSelected(false);

				}
				else
				{
					chk21.setEnabled(false);
					chk21.setSelected(true);
				}

				if (selectedNode.getNodeName().equals("Group"))
				{
					chk22.setEnabled(true);
					for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
					{
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceImage)
						{

							diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diImage.tp))
							{
								if (diImage.visible)
									have_visible_child = true;
							}
						}
						if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i) instanceof DataInterfaceLabel)
						{

							diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i);

							if (Constant.isAContainB(Constant.currentTreePath, diLabel.tp))
							{
								if (diLabel.visible)
									have_visible_child = true;
							}
						}

					}
					if (have_visible_child)
						chk22.setSelected(true);
					else
						chk22.setSelected(false);

				}
				else
				{
					chk22.setEnabled(false);
					chk22.setSelected(true);
				}
				textArea.setText(Util.nodeToString(selectedNode));

			}
		});

		/* Load the first item of panel list */
		try
		{
			initAllTreeModel();

			Z_DataStructure.initAllTreeModel();

			updateTree(0);
			//
			Constant.currentPXAname = objList.getModel().getElementAt(0).toString();
		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	}

	public void initAllTreeModel() throws ParserConfigurationException, SAXException, IOException
	{
		for (int panelIndex = 0; panelIndex < Constant.allPanelPath.size(); panelIndex++)
		{

			Vector tmpV = new Vector();

			String fullPath = Constant.workingPath + "\\"
					+ Constant.allPanelPath.elementAt(panelIndex).replace('/', '\\');

			File workingFile = new File(fullPath);

			//String absolutePath = workingFile.getAbsolutePath();

			Constant.currentPanelPath = fullPath.substring(0, fullPath.lastIndexOf(File.separator));

			if (builder == null)
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

				builder = factory.newDocumentBuilder();

			}

			// Create the nodes.

			Element docRoot = builder.parse(workingFile).getDocumentElement();

			DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode(docRoot);

			createTree(treeRoot, Constant.currentPanelPath);

			parseTree_Step1_Replace_All_Theme(treeRoot);

			Map<String, String> pxapropertyMap = new HashMap<String, String>();
			parseTree_Step2_Replace_All_Linked_Properties(treeRoot, pxapropertyMap);

			Constant.vHrefRoot_name_value.clear();
			parseTree_Step3_Prepare_properties_map(treeRoot, treeRoot);
			parseTree_Step3_Replace_All_Properties(treeRoot);

			parseTree_Step4_Calculate_X_Y_PushData(treeRoot, 0, 0, Constant.currentPanelPath, tmpV);

			InsertStateData2DataInterfaceImage(treeRoot, tmpV);

			handleRenderData_For_Container(tmpV);

			DefaultTreeModel treeModel = new DefaultTreeModel(treeRoot);

			Constant.allTreeModel.add(treeModel);

			Constant.allNodesForRendering.add(tmpV);

		}
	}

	private void createTree(DefaultMutableTreeNode currentNode, String currentPath) throws IOException
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
					//String absolutePath = workingFile.getAbsolutePath();

					hrefPath = fullPath.substring(0, fullPath.lastIndexOf(File.separator));
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

	private void InsertStateData2DataInterfaceImage(DefaultMutableTreeNode currentNode, Vector tmpV)
	{
		DataInterfaceImage diImage;
		Object nodeInfo = currentNode.getUserObject();
		org.w3c.dom.Node nodeState = (org.w3c.dom.Node) nodeInfo;

		if (nodeState.getNodeName().equals("State"))
		{

			Object nodeInfo1 = currentNode.getFirstLeaf().getUserObject();
			org.w3c.dom.Node nodePropertyChanges = (org.w3c.dom.Node) nodeInfo1;
			if (nodePropertyChanges.getNodeName().equals("PropertyChanges"))
			{

				for (int i = 0; i < tmpV.size(); i++)
				{
					if (tmpV.elementAt(i) instanceof DataInterfaceImage)
					{
						diImage = (DataInterfaceImage) tmpV.elementAt(i);
						if (diImage.id.equals(nodePropertyChanges.getAttributes().getNamedItem("target").getNodeValue())
								&& Constant.isA_and_B_belong_to_the_same_href_node(diImage.tp,
										getPathFromNode(currentNode)))
						{
							ImageState iS = null;
							if (nodePropertyChanges.getAttributes().getNamedItem("source") != null
									|| nodePropertyChanges.getAttributes().getNamedItem("x") != null
									|| nodePropertyChanges.getAttributes().getNamedItem("y") != null)
							{
								iS = new ImageState();
								if (nodeState.getAttributes().getNamedItem("name") != null)
									iS.name = nodeState.getAttributes().getNamedItem("name").getNodeValue();

								if (nodePropertyChanges.getAttributes().getNamedItem("source") != null)
								{

									iS.source = nodePropertyChanges.getAttributes().getNamedItem("source")
											.getNodeValue();

								}
								if (nodePropertyChanges.getAttributes().getNamedItem("x") != null)
								{
									iS.x = nodePropertyChanges.getAttributes().getNamedItem("x").getNodeValue();
								}
								if (nodePropertyChanges.getAttributes().getNamedItem("y") != null)
								{
									iS.y = nodePropertyChanges.getAttributes().getNamedItem("y").getNodeValue();
								}

								diImage.states.add(iS);

							}

						}
					}
				}

			}
		}

		Enumeration children = currentNode.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				InsertStateData2DataInterfaceImage((DefaultMutableTreeNode) children.nextElement(), tmpV);
			}
		}
	}

	private void parseTree_Step1_Replace_All_Theme(DefaultMutableTreeNode currentNode)
	{
		Object nodeInfo = currentNode.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;

		try
		{
			loadTheme(domNode.getAttributes());
		}
		catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Enumeration children = currentNode.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				parseTree_Step1_Replace_All_Theme((DefaultMutableTreeNode) children.nextElement());
			}
		}
	}

	/***
	 * requirement: PXA_Manual_v2.pdf 4.2 / 5.1
	 * 
	 * @param node
	 * @param map
	 * @param hrefLevel
	 * @param hrefNode
	 */
	private void parseTree_Step2_Replace_All_Linked_Properties(DefaultMutableTreeNode node, Map<String, String> map)
	{

		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;

		NamedNodeMap tmp_attr = domNode.getAttributes();
		boolean found_href = false;
		boolean found_pxaproperty = false;

		if (domNode.getNodeName().equals("property"))
		{
			String key = domNode.getAttributes().getNamedItem("name").getNodeValue();
			if (domNode.getAttributes().getNamedItem("type").getNodeValue() != "alias")
			{
				if (map.get(key) != null)
					domNode.getAttributes().getNamedItem("value").setNodeValue(map.get(key));
			}
		}
		for (int i = 0; i < tmp_attr.getLength(); ++i)
		{
			if (tmp_attr.item(i).getNodeName().contains("pxaproperty:"))
				found_pxaproperty = true;
			if (tmp_attr.item(i).getNodeName().contains("href"))
			{
				found_href = true;

				map.clear();
			}
		}

		if (found_href && found_pxaproperty)
		{
			for (int i = 0; i < tmp_attr.getLength(); ++i)
			{
				if (tmp_attr.item(i).getNodeName().contains("pxaproperty:"))
				{
					map.put(tmp_attr.item(i).getNodeName().substring(12), tmp_attr.item(i).getNodeValue());
				}
			}

		}

		Enumeration children = node.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				parseTree_Step2_Replace_All_Linked_Properties((DefaultMutableTreeNode) children.nextElement(), map);
			}
		}
	}

	private void parseTree_Step3_Prepare_properties_map(DefaultMutableTreeNode node,
			DefaultMutableTreeNode rootHrefNode)
	{

		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;
		NamedNodeMap tmp_attr = domNode.getAttributes();

		for (int i = 0; i < tmp_attr.getLength(); ++i)
		{
			if (tmp_attr.item(i).getNodeName().contains("href"))
			{
				rootHrefNode = node;
			}
		}

		if (domNode.getNodeName().equals("property") && domNode.getAttributes().getNamedItem("type") != null
				&& domNode.getAttributes().getNamedItem("name") != null
				&& domNode.getAttributes().getNamedItem("value") != null)
		{
			if (domNode.getAttributes().getNamedItem("type").getNodeValue() != "alias")
			{
				hrefRoot_name_value hNv = new hrefRoot_name_value();
				hNv.hrefNode = rootHrefNode;
				hNv.property_name = domNode.getAttributes().getNamedItem("name").getNodeValue();
				hNv.property_value = domNode.getAttributes().getNamedItem("value").getNodeValue();
				Constant.vHrefRoot_name_value.add(hNv);
			}
		}

		Enumeration children = node.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				parseTree_Step3_Prepare_properties_map((DefaultMutableTreeNode) children.nextElement(), rootHrefNode);
			}
		}
	}

	private void parseTree_Step3_Replace_All_Properties(DefaultMutableTreeNode node)
	{
		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;

		if (domNode.getAttributes().getNamedItem("x") != null)
		{
			if (domNode.getAttributes().getNamedItem("x").getNodeValue().startsWith("{")
					&& domNode.getAttributes().getNamedItem("x").getNodeValue().endsWith("}"))
			{
				String rawStr = domNode.getAttributes().getNamedItem("x").getNodeValue();

				String realStr = "";
				try
				{
					realStr = Constant.eval(prepareJS(node) + rawStr).toString();
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					// e.printStackTrace();
					domNode.getAttributes().getNamedItem("x").setNodeValue("0");
				}
				if (!Constant.isNumeric(realStr))
					realStr = "0";

				domNode.getAttributes().getNamedItem("x").setNodeValue(realStr);

			}
		}

		if (domNode.getAttributes().getNamedItem("y") != null)
		{
			if (domNode.getAttributes().getNamedItem("y").getNodeValue().startsWith("{")
					&& domNode.getAttributes().getNamedItem("y").getNodeValue().endsWith("}"))
			{
				String rawStr = domNode.getAttributes().getNamedItem("y").getNodeValue();

				String realStr = "";
				try
				{
					realStr = Constant.eval(prepareJS(node) + rawStr).toString();
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					// e.printStackTrace();
					domNode.getAttributes().getNamedItem("y").setNodeValue("0");
				}
				if (!Constant.isNumeric(realStr))
					realStr = "0";

				domNode.getAttributes().getNamedItem("y").setNodeValue(realStr);
			}
		}

		Enumeration children = node.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				parseTree_Step3_Replace_All_Properties((DefaultMutableTreeNode) children.nextElement());
			}
		}
	}

	private void parseTree_Step4_Calculate_X_Y_PushData(DefaultMutableTreeNode node, int offset_x, int offset_y,
			String path, Vector tmpV)
	{
		String tmpPath = path;

		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;

		try
		{
			pushRenderData(node, domNode, tmpPath, offset_x, offset_y, getPathFromNode(node), tmpV, "");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try
		{
			if (domNode.getAttributes().getNamedItem("href") != null)
			{
				if (domNode.getAttributes().getNamedItem("x") != null)
				{
					offset_x = offset_x + Integer.parseInt(domNode.getAttributes().getNamedItem("x").getNodeValue());
				}

				if (domNode.getAttributes().getNamedItem("y") != null)
				{
					offset_y = offset_y + Integer.parseInt(domNode.getAttributes().getNamedItem("y").getNodeValue());
				}

				String href = domNode.getAttributes().getNamedItem("href").getNodeValue();
				String fullPath = path + "\\" + href.replace('/', '\\');
				fullPath = FilenameUtils.normalize(fullPath);

				File workingFile = new File(fullPath);
				//String absolutePath = workingFile.getAbsolutePath();

				tmpPath = fullPath.substring(0, fullPath.lastIndexOf(File.separator));

			}
			else if (domNode.getNodeName().equals("Container"))
			{
				if (domNode.getAttributes().getNamedItem("x") != null)
				{
					offset_x = offset_x + Integer.parseInt(domNode.getAttributes().getNamedItem("x").getNodeValue());
				}

				if (domNode.getAttributes().getNamedItem("y") != null)
				{
					offset_y = offset_y + Integer.parseInt(domNode.getAttributes().getNamedItem("y").getNodeValue());
				}
			}
			else if (domNode.getNodeName().equals("Group"))
			{
				if (domNode.getAttributes().getNamedItem("x") != null)
				{
					offset_x = offset_x + Integer.parseInt(domNode.getAttributes().getNamedItem("x").getNodeValue());
				}

				if (domNode.getAttributes().getNamedItem("y") != null)
				{
					offset_y = offset_y + Integer.parseInt(domNode.getAttributes().getNamedItem("y").getNodeValue());
				}
			}
			else if (domNode.getNodeName().equals("RowLayout"))
			{
				if (domNode.getAttributes().getNamedItem("x") != null)
				{
					offset_x = offset_x + Integer.parseInt(domNode.getAttributes().getNamedItem("x").getNodeValue());
				}

				if (domNode.getAttributes().getNamedItem("y") != null)
				{
					offset_y = offset_y + Integer.parseInt(domNode.getAttributes().getNamedItem("y").getNodeValue());
				}
			}
			else
			{
				// do nothing
			}
		}
		catch (Exception e)
		{

		}

		Enumeration children = node.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				parseTree_Step4_Calculate_X_Y_PushData((DefaultMutableTreeNode) children.nextElement(), offset_x,
						offset_y, tmpPath, tmpV);
			}
		}
	}

	private String prepareJS(DefaultMutableTreeNode node)
	{
		String result = "";
		DefaultMutableTreeNode lastNode = null;
		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;

		if (domNode.getAttributes().getNamedItem("href") != null)
		{
			node = (DefaultMutableTreeNode) node.getParent();
			nodeInfo = node.getUserObject();
			domNode = (org.w3c.dom.Node) nodeInfo;
		}

		while (node != null && domNode.getAttributes().getNamedItem("href") == null)
		{
			lastNode = node;
			node = (DefaultMutableTreeNode) node.getParent();
			if (node != null)
			{
				nodeInfo = node.getUserObject();
				domNode = (org.w3c.dom.Node) nodeInfo;
			}
		}
		if (node == null)
			node = lastNode;

		for (int i = 0; i < Constant.vHrefRoot_name_value.size(); i++)
		{
			if (Constant.vHrefRoot_name_value.elementAt(i).hrefNode.equals(node))
			{
				result += "var " + Constant.vHrefRoot_name_value.elementAt(i).property_name + "= \""
						+ Constant.vHrefRoot_name_value.elementAt(i).property_value + "\";";
			}
		}

		return result;
	}

	private void loadTheme(NamedNodeMap attributeArray) throws Exception
	{
		for (int i = 0; i < attributeArray.getLength(); i++)
		{
			String rawStr = attributeArray.item(i).getNodeValue();
			if (rawStr.contains("$style.properties."))
			{
				String realStr = Constant.eval(Constant.JSproperties + rawStr).toString();

				attributeArray.item(i).setNodeValue(realStr);
			}

		}
	}

	/***
	 * Obsolete Nested procedure to build the tree recursively
	 * 
	 * @param node
	 * @param path
	 * @param xOffset
	 * @param yOffset
	 */
	private void createNodes(DefaultMutableTreeNode node, String path, int xOffset, int yOffset, int panelIndex,
			Vector tmpV, String sWidget) throws IOException
	{

		int tmpX = 0;
		int tmpY = 0;
		String tmpPath;
		org.w3c.dom.Document tmpDoc = null;

		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node selectedNode = (org.w3c.dom.Node) nodeInfo;

		NodeList list = selectedNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
		{
			// If list.item(i) is an Element, then list.item(i).getAttributes()
			// definitely does not equal null
			if (list.item(i) instanceof Element)
			{
				if (list.item(i).getAttributes().getNamedItem("x") != null)
					list.item(i).getAttributes().getNamedItem("x").setNodeValue("123");
				if (list.item(i).getAttributes().getNamedItem("y") != null)
					list.item(i).getAttributes().getNamedItem("y").setNodeValue("123");

				try
				{
					loadTheme(list.item(i).getAttributes());
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// This node should be expanded
				if (list.item(i).getAttributes().getNamedItem("href") != null)
				{
					String href = list.item(i).getAttributes().getNamedItem("href").getNodeValue();
					String fullPath = path + "\\" + href.replace('/', '\\');
					fullPath = FilenameUtils.normalize(fullPath);

					File workingFile = new File(fullPath);
					//String absolutePath = workingFile.getAbsolutePath();

					tmpPath = fullPath.substring(0, fullPath.lastIndexOf(File.separator));
					try
					{
						tmpDoc = builder.parse(workingFile);
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
					node.add(newNode);

					String sWidgetId = "";

					if (list.item(i).getAttributes().getNamedItem("id") != null)
					{
						sWidgetId = list.item(i).getAttributes().getNamedItem("id").getNodeValue();
					}
					pushRenderData(newNode, list.item(i), path, xOffset, yOffset, getPathFromNode(newNode), tmpV,
							sWidget);
					createNodes(newNode, tmpPath, xOffset, yOffset, panelIndex, tmpV, sWidgetId);

					if (list.item(i).getAttributes().getNamedItem("x") != null)
					{
						tmpX = xOffset
								+ Integer.parseInt(list.item(i).getAttributes().getNamedItem("x").getNodeValue());
					}
					else
						tmpX = xOffset;

					if (list.item(i).getAttributes().getNamedItem("y") != null)
					{
						tmpY = yOffset
								+ Integer.parseInt(list.item(i).getAttributes().getNamedItem("y").getNodeValue());
					}
					else
						tmpY = yOffset;

					DefaultMutableTreeNode newNodeHref = new DefaultMutableTreeNode(tmpDoc.getDocumentElement());
					newNode.add(newNodeHref);

					pushRenderData(newNodeHref, tmpDoc.getDocumentElement(), path, tmpX, tmpY,
							getPathFromNode(newNodeHref), tmpV, sWidget);
					createNodes(newNodeHref, tmpPath, tmpX, tmpY, panelIndex, tmpV, sWidgetId);
				}
				// Container(not href) node should be considered with Offset x y
				else if ((list.item(i).getNodeName().equals("Container"))
						&& ((list.item(i).getAttributes().getNamedItem("x") != null)
								|| (list.item(i).getAttributes().getNamedItem("y") != null)))
				{
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(list.item(i));
					node.add(newNode);
					if (list.item(i).getAttributes().getNamedItem("x") != null)
					{
						tmpX = xOffset
								+ Integer.parseInt(list.item(i).getAttributes().getNamedItem("x").getNodeValue());
					}

					if (list.item(i).getAttributes().getNamedItem("y") != null)
					{
						tmpY = yOffset
								+ Integer.parseInt(list.item(i).getAttributes().getNamedItem("y").getNodeValue());
					}

					pushRenderData(newNode, list.item(i), path, tmpX, tmpY, getPathFromNode(newNode), tmpV, sWidget);

					if (list.item(i).getChildNodes().getLength() != 0)
					{
						createNodes(newNode, path, tmpX, tmpY, panelIndex, tmpV, sWidget);
					}
				}
				// Normal nodes
				else
				{
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(list.item(i));
					node.add(newNode);

					pushRenderData(newNode, list.item(i), path, xOffset, yOffset, getPathFromNode(newNode), tmpV,
							sWidget);
					if (list.item(i).getChildNodes().getLength() != 0)
					{
						createNodes(newNode, path, xOffset, yOffset, panelIndex, tmpV, sWidget);
					}

				}

			}
		}

	}

	public TreePath getPathFromNode(DefaultMutableTreeNode treeNode)
	{
		List<Object> nodes = new ArrayList<Object>();
		if (treeNode != null)
		{
			nodes.add(treeNode);
			treeNode = (DefaultMutableTreeNode) treeNode.getParent();
			while (treeNode != null)
			{
				nodes.add(0, treeNode);
				treeNode = (DefaultMutableTreeNode) treeNode.getParent();
			}
		}

		return nodes.isEmpty() ? null : new TreePath(nodes.toArray());
	}

	private void pushRenderData(DefaultMutableTreeNode treeNode, org.w3c.dom.Node node, String path, int xOffset,
			int yOffset, TreePath tp, Vector tmpV, String sWidgetId) throws IOException
	{
		String panel_id = null;
		String panel_width = null;
		String panel_height = null;

		String container_id = null;
		String container_x = null;
		String container_y = null;
		String container_width = null;
		String container_height = null;
		String container_absolute_x = null;
		String container_absolute_y = null;

		String group_id = null;
		String group_x = null;
		String group_y = null;
		String group_width = null;
		String group_height = null;
		String group_absolute_x = null;
		String group_absolute_y = null;

		String image_id = null;
		String image_x = null;
		String image_y = null;
		String image_width = null;
		String image_height = null;
		String image_absolute_x = null;
		String image_absolute_y = null;
		String image_source = null;

		String label_id = null;
		String label_x = null;
		String label_y = null;
		String label_width = null;
		String label_height = null;
		String label_absolute_x = null;
		String label_absolute_y = null;

		String label_horizontalAlignment = null;
		String label_verticalAlignment = null;
		String label_fontSize = null;
		String label_fontColor = null;
		String label_sampleText = null;

		String siia = "", siib = "", siic = "", siid = "";

		if (node.getNodeName().equals("Panel"))
		{
			if (node.getAttributes().getNamedItem("id") != null)
				panel_id = node.getAttributes().getNamedItem("id").getNodeValue();
			if (node.getAttributes().getNamedItem("width") != null)
				panel_width = node.getAttributes().getNamedItem("width").getNodeValue();
			if (node.getAttributes().getNamedItem("height") != null)
				panel_height = node.getAttributes().getNamedItem("height").getNodeValue();
			tmpV.add(new DataInterfacePanel(tp, panel_id, panel_width, panel_height));
		}
		else if (node.getNodeName().equals("Container"))
		{

			if (node.getAttributes().getNamedItem("id") != null)
				container_id = node.getAttributes().getNamedItem("id").getNodeValue();

			if (node.getAttributes().getNamedItem("x") != null)
			{
				container_x = node.getAttributes().getNamedItem("x").getNodeValue();

				Integer tmp = xOffset + Integer.parseInt(container_x);
				container_absolute_x = tmp.toString();

			}
			else
			{
				container_x = "0";
				Integer tmp = xOffset;
				container_absolute_x = tmp.toString();
			}
			if (node.getAttributes().getNamedItem("y") != null)
			{
				container_y = node.getAttributes().getNamedItem("y").getNodeValue();

				Integer tmp = yOffset + Integer.parseInt(container_y);
				container_absolute_y = tmp.toString();

			}
			else
			{
				container_y = "0";
				Integer tmp = yOffset;
				container_absolute_y = tmp.toString();
			}

			if (node.getAttributes().getNamedItem("width") != null)
				container_width = node.getAttributes().getNamedItem("width").getNodeValue();
			else
				container_width = "TBD";

			if (node.getAttributes().getNamedItem("height") != null)
				container_height = node.getAttributes().getNamedItem("height").getNodeValue();
			else
				container_height = "TBD";

			tmpV.add(new DataInterfaceContainer(tp, container_id, container_x, container_y, container_width,
					container_height, container_absolute_x, container_absolute_y));

		}
		else if (node.getNodeName().equals("Group"))
		{

			if (node.getAttributes().getNamedItem("id") != null)
				group_id = node.getAttributes().getNamedItem("id").getNodeValue();

			if (node.getAttributes().getNamedItem("x") != null)
			{
				group_x = node.getAttributes().getNamedItem("x").getNodeValue();

				Integer tmp = xOffset + Constant.string2integer(group_x);
				group_absolute_x = tmp.toString();

			}
			else
			{
				group_x = "0";
				Integer tmp = xOffset;
				group_absolute_x = tmp.toString();
			}

			if (node.getAttributes().getNamedItem("y") != null)
			{
				group_y = node.getAttributes().getNamedItem("y").getNodeValue();

				Integer tmp = yOffset + Constant.string2integer(group_y);
				group_absolute_y = tmp.toString();

			}
			else
			{
				group_y = "0";
				Integer tmp = yOffset;
				group_absolute_y = tmp.toString();
			}

			if (node.getAttributes().getNamedItem("width") != null)
				group_width = node.getAttributes().getNamedItem("width").getNodeValue();
			else
				group_width = "TBD";

			if (node.getAttributes().getNamedItem("height") != null)
				group_height = node.getAttributes().getNamedItem("height").getNodeValue();
			else
				group_height = "TBD";

			// if (Group_y.equals("{root.width}"))
			// Group_y = "Constant.screenWidth";
			// if (Group_y.equals("{childrenRect.width}"))
			// Group_y = "TBD";
			// if (Group_y.equals("{data.width}"))
			// Group_y = "TBD";
			// if (Group_y.equals("{view.width}"))
			// Group_y = "TBD";
			// if (Group_y.equals("{parent.width}"))
			// Group_y = "TBD";

			tmpV.add(new DataInterfaceGroup(tp, group_id, group_x, group_y, group_width, group_height, group_absolute_x,
					group_absolute_y));

		}
		else if (node.getNodeName().equals("Image"))
		{
			if (node.getAttributes().getNamedItem("source") != null)
			{

				if (node.getAttributes().getNamedItem("id") != null)
					image_id = node.getAttributes().getNamedItem("id").getNodeValue();
				if (node.getAttributes().getNamedItem("width") != null)
					image_width = node.getAttributes().getNamedItem("width").getNodeValue();
				if (node.getAttributes().getNamedItem("height") != null)
					image_height = node.getAttributes().getNamedItem("height").getNodeValue();

				if (node.getAttributes().getNamedItem("x") != null)
				{
					image_x = node.getAttributes().getNamedItem("x").getNodeValue();

					Integer tmp = xOffset + Integer.parseInt(image_x);
					image_absolute_x = tmp.toString();

				}
				else
				{
					image_x = "0";
					Integer tmp = xOffset;
					image_absolute_x = tmp.toString();
				}

				if (node.getAttributes().getNamedItem("y") != null)
				{
					image_y = node.getAttributes().getNamedItem("y").getNodeValue();

					Integer tmp = yOffset + Integer.parseInt(image_y);
					image_absolute_y = tmp.toString();

				}
				else
				{
					image_y = "0";
					Integer tmp = yOffset;
					image_absolute_y = tmp.toString();
				}

				if (node.getAttributes().getNamedItem("source") != null)
				{
					// String fullPath;
					// if (node.getAttributes().getNamedItem("source")
					// .getNodeValue().equals(""))
					// {
					// image_source = "empty.png";
					//
					// }
					// else
					// {
					image_source = node.getAttributes().getNamedItem("source").getNodeValue();
					// }

				}

				tmpV.add(new DataInterfaceImage(tp, image_id, image_x, image_y, image_width, image_height, path,
						image_source, image_absolute_x, image_absolute_y));
			}
		}
		else if (node.getNodeName().equals("Label"))
		{
			if (node.getAttributes().getNamedItem("href") == null)
			{

				if (node.getAttributes().getNamedItem("id") != null)
					label_id = node.getAttributes().getNamedItem("id").getNodeValue();

				if (node.getAttributes().getNamedItem("x") != null)
				{
					label_x = node.getAttributes().getNamedItem("x").getNodeValue();

					Integer tmp = xOffset + Integer.parseInt(label_x);
					label_absolute_x = tmp.toString();

				}
				else
				{
					label_x = "0";
					Integer tmp = xOffset;
					label_absolute_x = tmp.toString();
				}

				if (node.getAttributes().getNamedItem("y") != null)
				{
					label_y = node.getAttributes().getNamedItem("y").getNodeValue();

					Integer tmp = yOffset + Integer.parseInt(label_y);
					label_absolute_y = tmp.toString();

				}
				else
				{
					label_y = "0";
					Integer tmp = yOffset;
					label_absolute_y = tmp.toString();
				}

				if (node.getAttributes().getNamedItem("width") != null)
					label_width = node.getAttributes().getNamedItem("width").getNodeValue();

				if (node.getAttributes().getNamedItem("maximumHeight") != null)
					label_height = node.getAttributes().getNamedItem("maximumHeight").getNodeValue();
				if (node.getAttributes().getNamedItem("height") != null)
					label_height = node.getAttributes().getNamedItem("height").getNodeValue();

				if (node.getAttributes().getNamedItem("horizontalAlignment") != null)
					label_horizontalAlignment = node.getAttributes().getNamedItem("horizontalAlignment").getNodeValue();

				if (node.getAttributes().getNamedItem("verticalAlignment") != null)
				{
					label_verticalAlignment = node.getAttributes().getNamedItem("verticalAlignment").getNodeValue();
				}

				if (node.getAttributes().getNamedItem("font.pixelSize") != null)
					label_fontSize = node.getAttributes().getNamedItem("font.pixelSize").getNodeValue();

				if (node.getAttributes().getNamedItem("color") != null)
					label_fontColor = node.getAttributes().getNamedItem("color").getNodeValue();

				if (node.getAttributes().getNamedItem("sampleText") != null)
					label_sampleText = node.getAttributes().getNamedItem("sampleText").getNodeValue();

				tmpV.add(new DataInterfaceLabel(tp, label_id, label_x, label_y, label_width, label_height,
						label_absolute_x, label_absolute_y, label_horizontalAlignment, label_verticalAlignment,
						label_fontSize, label_fontColor, label_sampleText));
			}

		}
		else
		{
			// do nothing
		}

	}

	public void handleRenderData_For_Container(Vector tmpV)
	{

		for (int j = 0; j < tmpV.size(); j++)
		{
			if (isContainer(tmpV.elementAt(j)))
			{
				for (int i = tmpV.size() - 1; i >= 0; i--)
				{
					if (isContainer(tmpV.elementAt(i)))
					{

						if (isTBD(tmpV.elementAt(i)))
						{
							if (!isHavingChildrenOfTBD(tmpV.elementAt(i), tmpV))
							{
								calculateWidthHeight(tmpV.elementAt(i), tmpV);
								// System.out.println("test1");
							}
						}

					}

				}
			}

		}

	}

	public boolean isTBD(Object ob)
	{
		if (ob instanceof DataInterfaceContainer)
		{
			if (((DataInterfaceContainer) ob).width == "TBD")
				return true;
			if (((DataInterfaceContainer) ob).height == "TBD")
				return true;

		}

		if (ob instanceof DataInterfaceGroup)
		{
			if (((DataInterfaceGroup) ob).width == "TBD")
				return true;
			if (((DataInterfaceGroup) ob).height == "TBD")
				return true;
		}
		return false;
	}

	public boolean isContainer(Object ob)
	{
		if (ob instanceof DataInterfaceContainer || ob instanceof DataInterfaceGroup)
		{
			return true;
		}

		else
			return false;
	}

	public boolean isHavingChildrenOfTBD(Object ob, Vector tmpV)
	{
		if (ob instanceof DataInterfaceContainer)
		{
			for (int i = 0; i < tmpV.size(); i++)
			{
				if (tmpV.elementAt(i) instanceof DataInterfaceContainer)
				{
					if (Constant.isAContainNoEqualB(((DataInterfaceContainer) ob).tp,
							((DataInterfaceContainer) tmpV.elementAt(i)).tp))
					{
						if (((DataInterfaceContainer) tmpV.elementAt(i)).width == "TBD"
								|| ((DataInterfaceContainer) tmpV.elementAt(i)).height == "TBD")
						{
							return true;
						}
					}
				}
				if (tmpV.elementAt(i) instanceof DataInterfaceGroup)
				{
					if (Constant.isAContainNoEqualB(((DataInterfaceContainer) ob).tp,
							((DataInterfaceGroup) tmpV.elementAt(i)).tp))
					{
						if (((DataInterfaceGroup) tmpV.elementAt(i)).width == "TBD"
								|| ((DataInterfaceGroup) tmpV.elementAt(i)).height == "TBD")
						{
							return true;
						}
					}
				}
			}
		}

		if (ob instanceof DataInterfaceGroup)
		{
			for (int i = 0; i < tmpV.size(); i++)
			{
				if (tmpV.elementAt(i) instanceof DataInterfaceContainer)
				{
					if (Constant.isAContainNoEqualB(((DataInterfaceGroup) ob).tp,
							((DataInterfaceContainer) tmpV.elementAt(i)).tp))
					{
						if (((DataInterfaceContainer) tmpV.elementAt(i)).width == "TBD"
								|| ((DataInterfaceContainer) tmpV.elementAt(i)).height == "TBD")
						{
							return true;
						}
					}
				}

				if (tmpV.elementAt(i) instanceof DataInterfaceGroup)
				{
					if (Constant.isAContainNoEqualB(((DataInterfaceGroup) ob).tp,
							((DataInterfaceGroup) tmpV.elementAt(i)).tp))
					{
						if (((DataInterfaceGroup) tmpV.elementAt(i)).width == "TBD"
								|| ((DataInterfaceGroup) tmpV.elementAt(i)).height == "TBD")
						{
							return true;
						}
					}
				}
			}
		}
		return false;

	}

	public void calculateWidthHeight(Object ob, Vector tmpV)
	{
		int wMax = 0;
		int hMax = 0;
		if (ob instanceof DataInterfaceContainer)
		{
			for (int i = 0; i < tmpV.size(); i++)
			{
				if (tmpV.elementAt(i) instanceof DataInterfaceContainer)
				{
					if (Constant.isAContainNoEqualB(((DataInterfaceContainer) ob).tp,
							((DataInterfaceContainer) tmpV.elementAt(i)).tp))
					{
						int x1, w1, x2, w2;

						x1 = Constant.string2integer(((DataInterfaceContainer) tmpV.elementAt(i)).absolute_x);
						w1 = Constant.string2integer(((DataInterfaceContainer) tmpV.elementAt(i)).width);
						x2 = Constant.string2integer(((DataInterfaceContainer) ob).absolute_x);
						w2 = x1 + w1 - x2;
						if (w2 > wMax)
							wMax = w2;

						int y1, h1, y2, h2;

						y1 = Constant.string2integer(((DataInterfaceContainer) tmpV.elementAt(i)).absolute_y);
						h1 = Constant.string2integer(((DataInterfaceContainer) tmpV.elementAt(i)).height);
						y2 = Constant.string2integer(((DataInterfaceContainer) ob).absolute_y);
						h2 = y1 + h1 - y2;
						if (h2 > hMax)
							hMax = h2;
					}
				}

				if (tmpV.elementAt(i) instanceof DataInterfaceGroup)
				{
					if (Constant.isAContainNoEqualB(((DataInterfaceContainer) ob).tp,
							((DataInterfaceGroup) tmpV.elementAt(i)).tp))
					{
						int x1, w1, x2, w2;

						x1 = Constant.string2integer(((DataInterfaceGroup) tmpV.elementAt(i)).absolute_x);
						w1 = Constant.string2integer(((DataInterfaceGroup) tmpV.elementAt(i)).width);
						x2 = Constant.string2integer(((DataInterfaceContainer) ob).absolute_x);
						w2 = x1 + w1 - x2;
						if (w2 > wMax)
							wMax = w2;

						int y1, h1, y2, h2;

						y1 = Constant.string2integer(((DataInterfaceGroup) tmpV.elementAt(i)).absolute_y);
						h1 = Constant.string2integer(((DataInterfaceGroup) tmpV.elementAt(i)).height);
						y2 = Constant.string2integer(((DataInterfaceContainer) ob).absolute_y);
						h2 = y1 + h1 - y2;
						if (h2 > hMax)
							hMax = h2;
					}
				}
			}
			Integer wMaxtmp = wMax;
			((DataInterfaceContainer) ob).width = wMaxtmp.toString();
			Integer hMaxtmp = hMax;
			((DataInterfaceContainer) ob).height = hMaxtmp.toString();
		}

		if (ob instanceof DataInterfaceGroup)
		{
			for (int i = 0; i < tmpV.size(); i++)
			{
				if (tmpV.elementAt(i) instanceof DataInterfaceContainer)
				{
					if (Constant.isAContainNoEqualB(((DataInterfaceGroup) ob).tp,
							((DataInterfaceContainer) tmpV.elementAt(i)).tp))
					{
						int x1, w1, x2, w2;

						x1 = Constant.string2integer(((DataInterfaceContainer) tmpV.elementAt(i)).absolute_x);
						w1 = Constant.string2integer(((DataInterfaceContainer) tmpV.elementAt(i)).width);
						x2 = Constant.string2integer(((DataInterfaceGroup) ob).absolute_x);
						w2 = x1 + w1 - x2;
						if (w2 > wMax)
							wMax = w2;

						int y1, h1, y2, h2;

						y1 = Constant.string2integer(((DataInterfaceContainer) tmpV.elementAt(i)).absolute_y);
						h1 = Constant.string2integer(((DataInterfaceContainer) tmpV.elementAt(i)).height);
						y2 = Constant.string2integer(((DataInterfaceGroup) ob).absolute_y);
						h2 = y1 + h1 - y2;
						if (h2 > hMax)
							hMax = h2;
					}
				}

				if (tmpV.elementAt(i) instanceof DataInterfaceGroup)
				{
					if (Constant.isAContainNoEqualB(((DataInterfaceGroup) ob).tp,
							((DataInterfaceGroup) tmpV.elementAt(i)).tp))
					{
						int x1, w1, x2, w2;

						x1 = Constant.string2integer(((DataInterfaceGroup) tmpV.elementAt(i)).absolute_x);
						w1 = Constant.string2integer(((DataInterfaceGroup) tmpV.elementAt(i)).width);
						x2 = Constant.string2integer(((DataInterfaceGroup) ob).absolute_x);
						w2 = x1 + w1 - x2;
						if (w2 > wMax)
							wMax = w2;

						int y1, h1, y2, h2;

						y1 = Constant.string2integer(((DataInterfaceGroup) tmpV.elementAt(i)).absolute_y);
						h1 = Constant.string2integer(((DataInterfaceGroup) tmpV.elementAt(i)).height);
						y2 = Constant.string2integer(((DataInterfaceGroup) ob).absolute_y);
						h2 = y1 + h1 - y2;
						if (h2 > hMax)
							hMax = h2;
					}
				}
			}

			Integer wMaxtmp = wMax;
			((DataInterfaceGroup) ob).width = wMaxtmp.toString();
			Integer hMaxtmp = hMax;
			((DataInterfaceGroup) ob).height = hMaxtmp.toString();
		}
	}

	public void openExcelFile()
	{

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));

		chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
		{
			public boolean accept(File f)
			{
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".xls");
			}

			public String getDescription()
			{
				return ".xls";
			}
		});
		int r = chooser.showOpenDialog(this);
		if (r != JFileChooser.APPROVE_OPTION)
			return;
		File file = chooser.getSelectedFile();

		if ((file.getPath().contains("Liste_libelles")) || (file.getPath().contains("Libelles")))
		{
			tLanguage.setEnabled(true);
			Constant.excelPath = file.getPath();

			DataInterfaceLabel diLabel;
			try
			{
				for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
				{

					if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i) instanceof DataInterfaceLabel)
					{

						diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
								.elementAt(i);

						if (diLabel.id != null && Constant.excelPath != null)

						{
							diLabel.textFromExcel = ReadExcel.ExcelOpen(Constant.excelPath, diLabel.id,
									sLanguage[tLanguage.getSelectedIndex()]);
						}
					}

				}
				rP.draw();
				Viewframe.add(rP);
				Constant.hasLoadedExcel = true;
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Please open correct file!", null, JOptionPane.WARNING_MESSAGE);
		}

	}

	public void updateTree(int index) throws ParserConfigurationException, SAXException, IOException
	{
		tree.setModel(Constant.allTreeModel.elementAt(index));

		tree.setCellRenderer(new DOMTreeCellRenderer());

		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		/* alex updated:Window flush */
		tree.setSelectionPath(new TreePath(((DefaultMutableTreeNode) tree.getModel().getRoot()).getPath()));

	}

	public void updateTable(NamedNodeMap map, String elementName, String absoluteX, String absoluteY,
			String user_config_x, String user_config_y, String user_config_source, String user_config_state,
			Vector imageState, DataInterfaceImage diImage)
	{
		xh.makeAttributeMap(elementName);

		table.setModel(new DOMAttributeTableModel(map, xh.attrMapData, absoluteX, absoluteY, user_config_x,
				user_config_y, user_config_source, user_config_state, imageState));

		table.setDefaultRenderer(Object.class, new DOMAttributeTableCellRenderer());
		TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(0);
		column1.setHeaderValue("Name");
		TableColumn column2 = table.getTableHeader().getColumnModel().getColumn(1);
		column2.setHeaderValue("Value");
		TableColumn column3 = table.getTableHeader().getColumnModel().getColumn(2);
		column3.setHeaderValue("Type");
		TableColumn column4 = table.getTableHeader().getColumnModel().getColumn(3);
		column4.setHeaderValue("Use");
		TableColumn column5 = table.getTableHeader().getColumnModel().getColumn(4);
		column5.setHeaderValue("user configuration");

		Constant.processing = true;
		table.getColumnModel().getColumn(0).setPreferredWidth(Constant.Column0);
		table.getColumnModel().getColumn(1).setPreferredWidth(Constant.Column1);
		table.getColumnModel().getColumn(2).setPreferredWidth(Constant.Column2);
		table.getColumnModel().getColumn(3).setPreferredWidth(Constant.Column3);
		table.getColumnModel().getColumn(4).setPreferredWidth(Constant.Column4);
		Constant.processing = false;

		if (imageState != null)
		{
			if (!imageState.isEmpty())
			{
				DOMAttributeTableModel model = (DOMAttributeTableModel) table.getModel();
				DOMAttributeTableCellEditor myEditor = new DOMAttributeTableCellEditor(table);
				JComboBox comboBox = new JComboBox();

				comboBox.addItem("none");

				for (int i = 0; i < imageState.size(); i++)
				{
					comboBox.addItem(((ImageState) imageState.elementAt(i)).name);

				}
				comboBox.addItemListener(new ItemListener()
				{

					private DataInterfaceImage diImage_innerclass;

					public void itemStateChanged(ItemEvent e)
					{
						if (e.getStateChange() == ItemEvent.SELECTED)
						{

							if (((JComboBox) e.getSource()).getSelectedIndex() >= 1)
							{
								int index = ((JComboBox) e.getSource()).getSelectedIndex() - 1;

								if (diImage_innerclass.states.elementAt(index).name != null)
								{
									diImage_innerclass.user_config_state = diImage_innerclass.states
											.elementAt(index).name;

								}

								if (diImage_innerclass.states.elementAt(index).source != null)
								{
									diImage_innerclass.user_config_source = diImage_innerclass.states
											.elementAt(index).source;

								}
								if (diImage_innerclass.states.elementAt(index).x != null)
								{
									Integer tmp = Integer.parseInt(diImage_innerclass.absolute_x)
											- Integer.parseInt(diImage_innerclass.x)
											+ +Integer.parseInt(diImage_innerclass.states.elementAt(index).x);
									diImage_innerclass.user_config_x = tmp.toString();

								}
								if (diImage_innerclass.states.elementAt(index).y != null)
								{
									Integer tmp = Integer.parseInt(diImage_innerclass.absolute_y)
											- Integer.parseInt(diImage_innerclass.y)
											+ +Integer.parseInt(diImage_innerclass.states.elementAt(index).y);
									diImage_innerclass.user_config_x = tmp.toString();

								}

							}
							else
							{
								diImage_innerclass.user_config_x = null;
								diImage_innerclass.user_config_y = null;
								diImage_innerclass.user_config_source = null;
								diImage_innerclass.user_config_state = null;

							}

						}
					}

					private ItemListener init(DataInterfaceImage diImage)
					{
						diImage_innerclass = diImage;
						return this;
					}
				}.init(diImage));

				myEditor.setEditorAt(model.getRowCount() - 1, new DefaultCellEditor(comboBox));
				table.getColumn("user configuration").setCellEditor(myEditor);
			}
		}

		table.getModel().addTableModelListener(new TableModelListener()
		{

			public void tableChanged(TableModelEvent e)
			{

				DataInterfaceImage diImage;
				DataInterfaceLabel diLabel;

				DataInterfaceContainer diContainer;
				DataInterfaceGroup diGroup;

				int row = e.getFirstRow();
				int column = e.getColumn();
				DOMAttributeTableModel model = (DOMAttributeTableModel) e.getSource();

				String dataString = "";
				Object data = model.getValueAt(row, column);

				if (data != null)
				{
					if (data.toString().length() == 0)
						dataString = null;
					else
						dataString = data.toString();
				}
				else
					dataString = null;
				Object dataAttributeName = model.getValueAt(row, 0);
				if (column != 4)
					return;

				/*
				 * updated by alex 2016.1.6 update x of the matching node in
				 * allNodesForRendering
				 */
				if (dataAttributeName.toString().equals("x"))
				{

					if (Constant.currentNodeName.equals("Container"))
					{

						for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
						{
							if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i) instanceof DataInterfaceContainer)
							{

								diContainer = (DataInterfaceContainer) Constant.allNodesForRendering
										.elementAt(Constant.whichPanel).elementAt(i);

								if (Constant.isAEqualB(Constant.currentTreePath, diContainer.tp))
								{
									diContainer.user_config_x = dataString;
								}
							}

						}

					}
					if (Constant.currentNodeName.equals("Group"))
					{

						for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
						{
							if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i) instanceof DataInterfaceGroup)
							{

								diGroup = (DataInterfaceGroup) Constant.allNodesForRendering
										.elementAt(Constant.whichPanel).elementAt(i);

								if (Constant.isAEqualB(Constant.currentTreePath, diGroup.tp))
								{
									diGroup.user_config_x = dataString;
								}
							}

						}

					}
					if (Constant.currentNodeName.equals("Image"))
					{

						for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
						{
							if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i) instanceof DataInterfaceImage)
							{

								diImage = (DataInterfaceImage) Constant.allNodesForRendering
										.elementAt(Constant.whichPanel).elementAt(i);

								if (Constant.isAEqualB(Constant.currentTreePath, diImage.tp))
								{
									diImage.user_config_x = dataString;
								}
							}

						}

					}
					if (Constant.currentNodeName.equals("Label"))
					{
						for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
						{
							if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i) instanceof DataInterfaceLabel)
							{

								diLabel = (DataInterfaceLabel) Constant.allNodesForRendering
										.elementAt(Constant.whichPanel).elementAt(i);

								if (Constant.isAEqualB(Constant.currentTreePath, diLabel.tp))
								{
									diLabel.user_config_x = dataString;
								}
							}

						}
					}

				}

				/*
				 * updated by alex 2016.1.6 update y of the matching node in
				 * allNodesForRendering
				 */
				if (dataAttributeName.toString().equals("y"))
				{
					if (Constant.currentNodeName.equals("Container"))
					{

						for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
						{
							if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i) instanceof DataInterfaceContainer)
							{

								diContainer = (DataInterfaceContainer) Constant.allNodesForRendering
										.elementAt(Constant.whichPanel).elementAt(i);

								if (Constant.isAEqualB(Constant.currentTreePath, diContainer.tp))
								{
									diContainer.user_config_y = dataString;

								}
							}

						}

					}
					if (Constant.currentNodeName.equals("Group"))
					{

						for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
						{
							if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i) instanceof DataInterfaceGroup)
							{

								diGroup = (DataInterfaceGroup) Constant.allNodesForRendering
										.elementAt(Constant.whichPanel).elementAt(i);

								if (Constant.isAEqualB(Constant.currentTreePath, diGroup.tp))
								{
									diGroup.user_config_y = dataString;

								}
							}

						}

					}
					if (Constant.currentNodeName.equals("Image"))
					{

						for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
						{
							if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i) instanceof DataInterfaceImage)
							{

								diImage = (DataInterfaceImage) Constant.allNodesForRendering
										.elementAt(Constant.whichPanel).elementAt(i);

								if (Constant.isAEqualB(Constant.currentTreePath, diImage.tp))
								{
									diImage.user_config_y = dataString;

								}
							}

						}

					}
					if (Constant.currentNodeName.equals("Label"))
					{
						for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
						{
							if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i) instanceof DataInterfaceLabel)
							{

								diLabel = (DataInterfaceLabel) Constant.allNodesForRendering
										.elementAt(Constant.whichPanel).elementAt(i);

								if (Constant.isAEqualB(Constant.currentTreePath, diLabel.tp))
								{
									diLabel.user_config_y = dataString;

								}
							}

						}
					}
				}

				/*
				 * updated by alex 2016.1.6 update source of the matching node
				 * in allNodesForRendering
				 */
				if (dataAttributeName.toString().equals("source"))
				{
					if (Constant.currentNodeName.equals("Image"))
					{
						for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
						{
							if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
									.elementAt(i) instanceof DataInterfaceImage)
							{

								diImage = (DataInterfaceImage) Constant.allNodesForRendering
										.elementAt(Constant.whichPanel).elementAt(i);

								if (Constant.isAEqualB(Constant.currentTreePath, diImage.tp))
								{
									diImage.user_config_source = dataString;
								}
							}

						}
					}
				}

				rP.draw();

			}
		});

	}

	public void resetUserConfiguration()
	{
		DataInterfaceImage diImage;
		for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
		{
			if (Constant.allNodesForRendering.elementAt(Constant.whichPanel).elementAt(i) instanceof DataInterfaceImage)
			{
				diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
						.elementAt(i);
				diImage.user_config_x = null;
				diImage.user_config_y = null;
				diImage.user_config_source = null;
				diImage.user_config_state = null;

			}

		}
		rP.draw();

		for (int i = 0; i < table.getRowCount(); i++)
		{
			table.setValueAt(null, i, 4);
		}

	}

	public void loadUserConfiguration(org.w3c.dom.Document doc)
	{

		DataInterfaceImage diImage;
		DataInterfaceLabel diLabel;

		DataInterfaceContainer diContainer;
		DataInterfaceGroup diGroup;

		NodeList whichPanelList = doc.getElementsByTagName("whichPanel");

		ListModel model = objList.getModel();

		for (int i = 0; i < model.getSize(); i++)
		{
			Object o = model.getElementAt(i);
			if (o.toString().equals(whichPanelList.item(0).getTextContent()))
				objList.setSelectedIndex(i);
		}

		NodeList changedNodeList = doc.getElementsByTagName("ChangedNode");
		for (int j = 0; j < changedNodeList.getLength(); j++)
		{
			org.w3c.dom.Node changedNode = changedNodeList.item(j);
			Element changedNodeElement = (Element) changedNode;

			for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
			{
				if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
						.elementAt(i) instanceof DataInterfaceImage)
				{

					diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i);
					if (changedNodeElement.getAttribute("name").equals("Image")
							&& diImage.id.equals(changedNodeElement.getAttribute("id"))
							&& diImage.tp.toString().equals(changedNodeElement.getAttribute("path")))
					{
						NodeList modifiedAttributeList = changedNodeElement.getElementsByTagName("ModifiedAttribute");

						for (int m = 0; m < modifiedAttributeList.getLength(); m++)
						{
							org.w3c.dom.Node modifiedAttributeNode = modifiedAttributeList.item(m);
							Element modifiedAttributeElement = (Element) modifiedAttributeNode;

							if (modifiedAttributeElement.getAttribute("name").equals("x"))
								diImage.user_config_x = modifiedAttributeElement.getAttribute("value");
							if (modifiedAttributeElement.getAttribute("name").equals("y"))
								diImage.user_config_y = modifiedAttributeElement.getAttribute("value");
							if (modifiedAttributeElement.getAttribute("name").equals("source"))
								diImage.user_config_source = modifiedAttributeElement.getAttribute("value");
						}

						NodeList modifiedStateList = changedNodeElement.getElementsByTagName("ModifiedState");
						for (int m = 0; m < modifiedStateList.getLength(); m++)
						{
							org.w3c.dom.Node modifiedStateNode = modifiedStateList.item(0);
							Element modifiedStateElement = (Element) modifiedStateNode;
							diImage.user_config_state = modifiedStateElement.getAttribute("value");
						}

					}

				}
				if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
						.elementAt(i) instanceof DataInterfaceLabel)
				{

					diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i);

					if (changedNodeElement.getAttribute("name").equals("Label")
							&& diLabel.id.equals(changedNodeElement.getAttribute("id"))
							&& diLabel.tp.toString().equals(changedNodeElement.getAttribute("path")))
					{
						NodeList modifiedAttributeList = changedNodeElement.getElementsByTagName("ModifiedAttribute");

						for (int m = 0; m < modifiedAttributeList.getLength(); m++)
						{
							org.w3c.dom.Node modifiedAttributeNode = modifiedAttributeList.item(m);
							Element modifiedAttributeElement = (Element) modifiedAttributeNode;

							if (modifiedAttributeElement.getAttribute("name").equals("x"))
								diLabel.user_config_x = modifiedAttributeElement.getAttribute("value");
							if (modifiedAttributeElement.getAttribute("name").equals("y"))
								diLabel.user_config_y = modifiedAttributeElement.getAttribute("value");

						}

					}

				}

				if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
						.elementAt(i) instanceof DataInterfaceContainer)
				{

					diContainer = (DataInterfaceContainer) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i);

					if (changedNodeElement.getAttribute("name").equals("Container")
							&& diContainer.id.equals(changedNodeElement.getAttribute("id"))
							&& diContainer.tp.toString().equals(changedNodeElement.getAttribute("path")))
					{
						NodeList modifiedAttributeList = changedNodeElement.getElementsByTagName("ModifiedAttribute");

						for (int m = 0; m < modifiedAttributeList.getLength(); m++)
						{
							org.w3c.dom.Node modifiedAttributeNode = modifiedAttributeList.item(m);
							Element modifiedAttributeElement = (Element) modifiedAttributeNode;

							if (modifiedAttributeElement.getAttribute("name").equals("x"))
								diContainer.user_config_x = modifiedAttributeElement.getAttribute("value");
							if (modifiedAttributeElement.getAttribute("name").equals("y"))
								diContainer.user_config_y = modifiedAttributeElement.getAttribute("value");

						}

					}

				}
				if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
						.elementAt(i) instanceof DataInterfaceGroup)
				{

					diGroup = (DataInterfaceGroup) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i);

					if (changedNodeElement.getAttribute("name").equals("Group")
							&& diGroup.id.equals(changedNodeElement.getAttribute("id"))
							&& diGroup.tp.toString().equals(changedNodeElement.getAttribute("path")))
					{
						NodeList modifiedAttributeList = changedNodeElement.getElementsByTagName("ModifiedAttribute");

						for (int m = 0; m < modifiedAttributeList.getLength(); m++)
						{
							org.w3c.dom.Node modifiedAttributeNode = modifiedAttributeList.item(m);
							Element modifiedAttributeElement = (Element) modifiedAttributeNode;

							if (modifiedAttributeElement.getAttribute("name").equals("x"))
								diGroup.user_config_x = modifiedAttributeElement.getAttribute("value");
							if (modifiedAttributeElement.getAttribute("name").equals("y"))
								diGroup.user_config_y = modifiedAttributeElement.getAttribute("value");

						}

					}

				}

			}

		}

		rP.draw();
	}

	public void saveUserConfiguration(String filename)
	{
		try
		{

			DataInterfaceImage diImage;
			DataInterfaceLabel diLabel;

			DataInterfaceContainer diContainer;
			DataInterfaceGroup diGroup;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			org.w3c.dom.Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("UserConfiguration");
			doc.appendChild(rootElement);

			Element whichPanelElement = doc.createElement("whichPanel");
			whichPanelElement.appendChild(doc.createTextNode(Constant.currentPXAname));
			rootElement.appendChild(whichPanelElement);
			for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel).size(); i++)
			{
				if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
						.elementAt(i) instanceof DataInterfaceImage)
				{

					diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i);

					if (diImage.user_config_source != null || diImage.user_config_x != null
							|| diImage.user_config_y != null || diImage.user_config_state != null)
					{
						Element changedNode = doc.createElement("ChangedNode");
						rootElement.appendChild(changedNode);
						changedNode.setAttribute("name", "Image");
						changedNode.setAttribute("id", diImage.id);
						changedNode.setAttribute("path", diImage.tp.toString());

						if (diImage.user_config_source != null)
						{
							Element modifiedAttribute = doc.createElement("ModifiedAttribute");
							modifiedAttribute.setAttribute("name", "source");
							modifiedAttribute.setAttribute("value", diImage.user_config_source);
							changedNode.appendChild(modifiedAttribute);
						}
						if (diImage.user_config_x != null)
						{
							Element modifiedAttribute = doc.createElement("ModifiedAttribute");
							modifiedAttribute.setAttribute("name", "x");
							modifiedAttribute.setAttribute("value", diImage.user_config_x);
							changedNode.appendChild(modifiedAttribute);
						}
						if (diImage.user_config_y != null)
						{
							Element modifiedAttribute = doc.createElement("ModifiedAttribute");
							modifiedAttribute.setAttribute("name", "y");
							modifiedAttribute.setAttribute("value", diImage.user_config_y);
							changedNode.appendChild(modifiedAttribute);
						}
						if (diImage.user_config_state != null)
						{
							Element modifiedState = doc.createElement("ModifiedState");
							modifiedState.setAttribute("value", diImage.user_config_state);
							changedNode.appendChild(modifiedState);

						}

					}

				}

				if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
						.elementAt(i) instanceof DataInterfaceLabel)
				{

					diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i);

					if (diLabel.user_config_x != null || diLabel.user_config_y != null)
					{
						Element changedNode = doc.createElement("ChangedNode");
						rootElement.appendChild(changedNode);
						changedNode.setAttribute("name", "Label");
						changedNode.setAttribute("id", diLabel.id);
						changedNode.setAttribute("path", diLabel.tp.toString());

						if (diLabel.user_config_x != null)
						{
							Element modifiedAttribute = doc.createElement("ModifiedAttribute");
							modifiedAttribute.setAttribute("name", "x");
							modifiedAttribute.setAttribute("value", diLabel.user_config_x);
							changedNode.appendChild(modifiedAttribute);
						}
						if (diLabel.user_config_y != null)
						{
							Element modifiedAttribute = doc.createElement("ModifiedAttribute");
							modifiedAttribute.setAttribute("name", "y");
							modifiedAttribute.setAttribute("value", diLabel.user_config_y);
							changedNode.appendChild(modifiedAttribute);
						}

					}

				}

				if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
						.elementAt(i) instanceof DataInterfaceContainer)
				{

					diContainer = (DataInterfaceContainer) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i);

					if (diContainer.user_config_x != null || diContainer.user_config_y != null)
					{
						Element changedNode = doc.createElement("ChangedNode");
						rootElement.appendChild(changedNode);
						changedNode.setAttribute("name", "Container");
						changedNode.setAttribute("id", diContainer.id);
						changedNode.setAttribute("path", diContainer.tp.toString());

						if (diContainer.user_config_x != null)
						{
							Element modifiedAttribute = doc.createElement("ModifiedAttribute");
							modifiedAttribute.setAttribute("name", "x");
							modifiedAttribute.setAttribute("value", diContainer.user_config_x);
							changedNode.appendChild(modifiedAttribute);
						}
						if (diContainer.user_config_y != null)
						{
							Element modifiedAttribute = doc.createElement("ModifiedAttribute");
							modifiedAttribute.setAttribute("name", "y");
							modifiedAttribute.setAttribute("value", diContainer.user_config_y);
							changedNode.appendChild(modifiedAttribute);
						}

					}

				}

				if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
						.elementAt(i) instanceof DataInterfaceGroup)
				{

					diGroup = (DataInterfaceGroup) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
							.elementAt(i);

					if (diGroup.user_config_x != null || diGroup.user_config_y != null)
					{
						Element changedNode = doc.createElement("ChangedNode");
						rootElement.appendChild(changedNode);
						changedNode.setAttribute("name", "Group");
						changedNode.setAttribute("id", diGroup.id);
						changedNode.setAttribute("path", diGroup.tp.toString());

						if (diGroup.user_config_x != null)
						{
							Element modifiedAttribute = doc.createElement("ModifiedAttribute");
							modifiedAttribute.setAttribute("name", "x");
							modifiedAttribute.setAttribute("value", diGroup.user_config_x);
							changedNode.appendChild(modifiedAttribute);
						}
						if (diGroup.user_config_y != null)
						{
							Element modifiedAttribute = doc.createElement("ModifiedAttribute");
							modifiedAttribute.setAttribute("name", "y");
							modifiedAttribute.setAttribute("value", diGroup.user_config_y);
							changedNode.appendChild(modifiedAttribute);
						}

					}

				}

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource((org.w3c.dom.Document) doc);
			StreamResult result = new StreamResult(new File(filename));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();

		}
		catch (TransformerException tfe)
		{
			tfe.printStackTrace();
		}
	}

	// /////////Export Word
	public void exportWord(String filename)
	{
		try
		{
			newWordDoc(filename);

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void newWordDoc(String filename) throws Exception
	{

		// create an empty doc
		XWPFDocument emptyDoc = new XWPFDocument();
		FileOutputStream fos = new FileOutputStream(new File("tmp.doc"));
		emptyDoc.write(fos);
		fos.close();
		// create working doc
		CustomXWPFDocument workingDoc = new CustomXWPFDocument(new FileInputStream(new File("tmp.doc")));

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Constant.allTreeModel.elementAt(Constant.whichPanel)
				.getRoot());
		exportWord_traverseNodes(top, workingDoc);
		// save to final Doc
		FileOutputStream finalDoc = new FileOutputStream(new File(filename));
		workingDoc.write(finalDoc);
		finalDoc.flush();
		finalDoc.close();

	}

	public void exportWord_traverseNodes(DefaultMutableTreeNode node, CustomXWPFDocument workingDoc)
			throws IOException, InvalidFormatException
	{

		DataInterfaceImage diImage;
		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;
		if ((domNode.getPreviousSibling().getNodeName() != "pxa" && ((Element) domNode).getTagName() != "data")
				|| node.isRoot())
		{

			if (((Element) domNode).getTagName() != "Image")
			{
				if (renderNode(node))
				{

					XWPFParagraph par = workingDoc.createParagraph();

					XWPFRun run = par.createRun();

					if (((Element) domNode).getAttributes().getNamedItem("partName") != null)
						run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
								+ ((Element) domNode).getTagName() + " "
								+ ((Element) domNode).getAttributes().getNamedItem("partName").getNodeValue());
					else if (((Element) domNode).getAttributes().getNamedItem("id") != null)
						run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
								+ ((Element) domNode).getTagName() + " "
								+ ((Element) domNode).getAttributes().getNamedItem("id").getNodeValue());
					else if (((Element) domNode).getAttributes().getNamedItem("name") != null)
						run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
								+ ((Element) domNode).getTagName() + " "
								+ ((Element) domNode).getAttributes().getNamedItem("name").getNodeValue());
					else
						run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
								+ ((Element) domNode).getTagName());

					run.setFontSize(12);

					String blipId = workingDoc.addPictureData(new FileInputStream(new File("tmp.png")),
							org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG);

					workingDoc.createPicture(blipId,
							workingDoc.getNextPicNameNumber(org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG),
							Constant.screenWidth, Constant.screenHeight);

				}
			}
			else
			{
				Enumeration childrenIMG = node.children();
				if (childrenIMG != null)
				{
					boolean hasState = false;
					while (childrenIMG.hasMoreElements())
					{
						hasState = true;
						DefaultMutableTreeNode treeNodeIMG = (DefaultMutableTreeNode) childrenIMG.nextElement();
						Object nodeIMG = treeNodeIMG.getUserObject();
						org.w3c.dom.Node domIMG = (org.w3c.dom.Node) nodeIMG;
						if (((Element) domIMG).getTagName().equals("statesBindings"))
						{
							Enumeration childrenState = treeNodeIMG.children();
							if (childrenState != null)
							{
								while (childrenState.hasMoreElements())
								{
									DefaultMutableTreeNode treeNodeState = (DefaultMutableTreeNode) childrenState
											.nextElement();
									Object nodeState = treeNodeState.getUserObject();
									org.w3c.dom.Node domState = (org.w3c.dom.Node) nodeState;
									for (int i = 0; i < Constant.allNodesForRendering.elementAt(Constant.whichPanel)
											.size(); i++)
									{
										if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
												.elementAt(i) instanceof DataInterfaceImage)
										{

											diImage = (DataInterfaceImage) Constant.allNodesForRendering
													.elementAt(Constant.whichPanel).elementAt(i);

											if (Constant.isAContainB(diImage.tp, getPathFromNode(treeNodeState)))
											{
												if (domState.getAttributes().getNamedItem("source") != null)
												{
													String fullPath = diImage.path + "\\" + domState.getAttributes()
															.getNamedItem("source").getNodeValue().replace('/', '\\');
													fullPath = FilenameUtils.normalize(fullPath);

													diImage.source = fullPath;

												}

												if (domState.getAttributes().getNamedItem("x") != null)
												{
													Integer tmp1 = Integer.parseInt(diImage.x) + Integer.parseInt(
															domState.getAttributes().getNamedItem("x").getNodeValue());
													diImage.x = tmp1.toString();
												}

												if (domState.getAttributes().getNamedItem("y") != null)
												{
													Integer tmp2 = Integer.parseInt(diImage.y) + Integer.parseInt(
															domState.getAttributes().getNamedItem("y").getNodeValue());
													diImage.y = tmp2.toString();
												}

											}
										}
									}
									if (renderNode(node))
									{

										XWPFParagraph par = workingDoc.createParagraph();

										XWPFRun run = par.createRun();

										if (((Element) domNode).getAttributes().getNamedItem("partName") != null)
											run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
													+ ((Element) domNode).getTagName() + " "
													+ ((Element) domNode).getAttributes().getNamedItem("partName")
															.getNodeValue()
													+ " State:" + domState.getAttributes().getNamedItem("name"));
										else if (((Element) domNode).getAttributes().getNamedItem("id") != null)
											run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
													+ ((Element) domNode).getTagName() + " "
													+ ((Element) domNode).getAttributes().getNamedItem("id")
															.getNodeValue()
													+ " State:" + domState.getAttributes().getNamedItem("name"));
										else if (((Element) domNode).getAttributes().getNamedItem("name") != null)
											run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
													+ ((Element) domNode).getTagName() + " "
													+ ((Element) domNode).getAttributes().getNamedItem("name")
															.getNodeValue()
													+ " State:" + domState.getAttributes().getNamedItem("name"));
										else
											run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
													+ ((Element) domNode).getTagName() + " State:"
													+ domState.getAttributes().getNamedItem("name"));

										run.setFontSize(12);

										String blipId = workingDoc.addPictureData(
												new FileInputStream(new File("tmp.png")),
												org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG);

										workingDoc.createPicture(blipId,
												workingDoc.getNextPicNameNumber(
														org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG),
												Constant.screenWidth, Constant.screenHeight);

									}

								}
							}
						}

					}
					if (!hasState)// this image does not have any state binding
					{
						if (renderNode(node))
						{

							XWPFParagraph par = workingDoc.createParagraph();

							XWPFRun run = par.createRun();

							if (((Element) domNode).getAttributes().getNamedItem("partName") != null)
								run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
										+ ((Element) domNode).getTagName() + " "
										+ ((Element) domNode).getAttributes().getNamedItem("partName").getNodeValue());
							else if (((Element) domNode).getAttributes().getNamedItem("id") != null)
								run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
										+ ((Element) domNode).getTagName() + " "
										+ ((Element) domNode).getAttributes().getNamedItem("id").getNodeValue());
							else if (((Element) domNode).getAttributes().getNamedItem("name") != null)
								run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
										+ ((Element) domNode).getTagName() + " "
										+ ((Element) domNode).getAttributes().getNamedItem("name").getNodeValue());
							else
								run.setText((new TreePath(node.getPath())).toString().replace(": null", "")
										+ ((Element) domNode).getTagName());

							run.setFontSize(12);

							String blipId = workingDoc.addPictureData(new FileInputStream(new File("tmp.png")),
									org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG);

							workingDoc.createPicture(blipId,
									workingDoc.getNextPicNameNumber(
											org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG),
									Constant.screenWidth, Constant.screenHeight);

						}
					}
				}

			}
		}
		Enumeration children = node.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				exportWord_traverseNodes((DefaultMutableTreeNode) children.nextElement(), workingDoc);
			}
		}
	}

	public boolean renderNode(DefaultMutableTreeNode node) throws IOException
	{
		DataInterfaceImage diImage;
		DataInterfaceLabel diLabel;
		ImageView image;
		int x, y, w, h;
		boolean found = false;
		String labelText;

		// prepare for drawing
		BufferedImage bi;
		Graphics2D gi;
		bi = new BufferedImage(Constant.screenWidth, Constant.screenHeight, BufferedImage.TYPE_INT_ARGB);
		gi = bi.createGraphics();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_OVER, 1.0F);
		gi.setComposite(ac);

		for (int i = Constant.allNodesForRendering.elementAt(Constant.whichPanel).size() - 1; i >= 0; i--)
		{
			if (Constant.allNodesForRendering.elementAt(Constant.whichPanel).elementAt(i) instanceof DataInterfaceImage)
			{

				diImage = (DataInterfaceImage) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
						.elementAt(i);

				if (Constant.isAContainB(getPathFromNode(node), diImage.tp) && diImage.visible)
				{
					String fullPath = "";
					if (diImage.user_config_source == null)

					{
						fullPath = diImage.path + "\\" + diImage.source.replace('/', '\\');
					}

					else
					{
						fullPath = diImage.path + "\\" + diImage.user_config_source.replace('/', '\\');

					}
					fullPath = FilenameUtils.normalize(fullPath);
					image = new ImageView(fullPath);

					try
					{

						gi.drawImage(ImageIO.read(new File(fullPath)), Integer.parseInt(diImage.absolute_x),
								Integer.parseInt(diImage.absolute_y), image.width, image.height, null);
					}
					catch (IOException e)
					{
						gi.setColor(Color.red);

						gi.drawString("Can not find the image!", 100, 100);
						System.out.println("can not find image file in the specific place: " + diImage.source);
					}
					found = true;
				}
			}
			if (Constant.allNodesForRendering.elementAt(Constant.whichPanel).elementAt(i) instanceof DataInterfaceLabel)
			{

				diLabel = (DataInterfaceLabel) Constant.allNodesForRendering.elementAt(Constant.whichPanel)
						.elementAt(i);

				if (Constant.isAContainB(getPathFromNode(node), diLabel.tp) && diLabel.visible)
				{
					if (diLabel.textFromExcel != "")
						labelText = diLabel.textFromExcel;
					else if ("" != diLabel.sampleText)
						labelText = diLabel.sampleText;
					else
						labelText = "no text";
					w = Constant.string2integer(diLabel.width);
					h = Constant.string2integer(diLabel.height);
					x = Constant.string2integer(diLabel.x);
					y = Constant.string2integer(diLabel.y);
					gi.setFont(new LabelText().getFont(diLabel.font_pixelSize));
					gi.setColor(new Color(0xffffff));
					if (labelText != null)
						gi.drawString(labelText, x + w / 2 - labelText.length() * 7 / 2, y + h / 2);
					found = true;
				}
			}

		}

		gi.dispose();
		if (found)
		{
			ImageIO.write(bi, "png", new File("tmp.png"));

			return true;
		}
		else
		{
			return false;
		}
	}

	// /////////search
	public void search(String s, int scope, boolean sensitive)
			throws ParserConfigurationException, SAXException, IOException
	{
		for (int i = 0; i < Constant.allPanelPath.size(); i++)
		{
			this.traverseTree(i, s, scope, sensitive);
		}

	}

	public void traverseTree(int i, String s, int scope, boolean sensitive)
			throws ParserConfigurationException, SAXException, IOException
	{

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Constant.allTreeModel.elementAt(i).getRoot());

		traverseNodes(top, s, i, scope, sensitive);

	}

	public void traverseNodes(DefaultMutableTreeNode root, String s, int i, int scope, boolean sensitive)
	{

		checkNode(root, s, i, scope, sensitive);
		Enumeration children = root.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				traverseNodes((DefaultMutableTreeNode) children.nextElement(), s, i, scope, sensitive);
			}
		}
	}

	public void checkNode(DefaultMutableTreeNode node, String s, int index, int scope, boolean sensitive)
	{

		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;

		NamedNodeMap tmp_attr = domNode.getAttributes();
		boolean found = false;

		if (sensitive)
		{
			if (Constant.SEARCH_ALL == scope)
			{
				if (domNode.getNodeName().contains(s))
					found = true;

				for (int i = 0; i < tmp_attr.getLength(); ++i)
				{
					if (tmp_attr.item(i).getNodeName().contains(s))
						found = true;
					if (tmp_attr.item(i).getNodeValue().contains(s))
						found = true;
				}

			}
			else if (Constant.SEARCH_NODENAME == scope)
			{
				if (domNode.getNodeName().contains(s))
					found = true;
			}
			else if (Constant.SEARCH_ATTRIBUTENAME == scope)
			{
				for (int i = 0; i < tmp_attr.getLength(); ++i)
				{
					if (tmp_attr.item(i).getNodeName().contains(s))
						found = true;
				}

			}
			else if (Constant.SEARCH_ATTRIBUTEVALUE == scope)
			{
				for (int i = 0; i < tmp_attr.getLength(); ++i)
				{
					if (tmp_attr.item(i).getNodeValue().contains(s))
						found = true;
				}
			}
			else
			{
			}

		}
		else
		{
			if (Constant.SEARCH_ALL == scope)
			{
				if (domNode.getNodeName().toLowerCase().contains(s.toLowerCase()))
					found = true;

				for (int i = 0; i < tmp_attr.getLength(); ++i)
				{
					if (tmp_attr.item(i).getNodeName().toLowerCase().contains(s.toLowerCase()))
						found = true;
					if (tmp_attr.item(i).getNodeValue().toLowerCase().contains(s.toLowerCase()))
						found = true;
				}

			}
			else if (Constant.SEARCH_NODENAME == scope)
			{
				if (domNode.getNodeName().toLowerCase().contains(s.toLowerCase()))
					found = true;
			}
			else if (Constant.SEARCH_ATTRIBUTENAME == scope)
			{
				for (int i = 0; i < tmp_attr.getLength(); ++i)
				{
					if (tmp_attr.item(i).getNodeName().toLowerCase().contains(s.toLowerCase()))
						found = true;
				}

			}
			else if (Constant.SEARCH_ATTRIBUTEVALUE == scope)
			{
				for (int i = 0; i < tmp_attr.getLength(); ++i)
				{
					if (tmp_attr.item(i).getNodeValue().toLowerCase().contains(s.toLowerCase()))
						found = true;
				}
			}
			else
			{
			}
		}

		if (found)
		{
			searchResult sR = new searchResult();
			sR.index = index;
			sR.tp = new TreePath(node.getPath());
			Constant.vSearchResult.add(sR);
		}

	}

	public void jumpToTheTarget(int index)
	{
		objList.setSelectedIndex(Constant.vSearchResult.elementAt(index).index);
		tree.setSelectionPath(Constant.vSearchResult.elementAt(index).tp);
	}

	// /////////statistics
	public void statistics() throws ParserConfigurationException, SAXException, IOException
	{
		Constant.vStatisticsResult.clear();
		for (int i = 0; i < Constant.allPanelPath.size(); i++)
		{
			this.traverseTree1(i);
		}

	}

	public void traverseTree1(int i) throws ParserConfigurationException, SAXException, IOException
	{

		DefaultMutableTreeNode top = (DefaultMutableTreeNode) (Constant.allTreeModel.elementAt(i).getRoot());

		traverseNodes1(top, i);

	}

	public void traverseNodes1(DefaultMutableTreeNode root, int i)
	{

		checkNode1(root, i);
		Enumeration children = root.children();
		if (children != null)
		{
			while (children.hasMoreElements())
			{
				traverseNodes1((DefaultMutableTreeNode) children.nextElement(), i);
			}
		}
	}

	public void checkNode1(DefaultMutableTreeNode node, int index)
	{

		Object nodeInfo = node.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;

		boolean found = false;
		for (int i = 0; i < Constant.vStatisticsResult.size(); i++)
		{
			if (Constant.vStatisticsResult.elementAt(i).keyword.equals(domNode.getNodeName()))
			{
				found = true;
				Constant.vStatisticsResult.elementAt(i).Nb++;
			}
		}

		if (!found)
		{
			statisticsResult sR = new statisticsResult();
			sR.keyword = domNode.getNodeName();
			sR.Nb = 1;
			Constant.vStatisticsResult.add(sR);
		}

	}

	public String returnStatisticsResult()
	{
		String s = "PXA files:" + Constant.allPanelPath.size() + "\n";
		for (int i = 0; i < Constant.vStatisticsResult.size(); i++)
		{
			s += Constant.vStatisticsResult.elementAt(i).keyword + ":" + Constant.vStatisticsResult.elementAt(i).Nb
					+ "\n";
		}
		return s;
	}

	// /////////generate
	public void generateCode(String dirGenerate) {

		for (int i = 0; i < Z_DataStructure.fullPaths.size(); i++) {

			try {
				String fullPath=Z_DataStructure.fullPaths.elementAt(i);
				
				String PXA_name = (fullPath.substring(fullPath.lastIndexOf(File.separator) + 1)).replaceAll(".pxa", "");

				File file = new File(dirGenerate + "\\CPanelGEN_" + PXA_name + ".hpp");

				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				A_Header a_Header=new A_Header(PXA_name);
				bw.write(a_Header.getContent());

				

				B_Includes b_Includes =new B_Includes(i);
				bw.write(b_Includes.getContent());
				
				
				
				
				

				bw.write("using namespace pxaframework;\n");

				bw.write("template <typename C>\n");

				bw.write("class Prefab_" + PXA_name + " : public Prefab<C>\n");
				bw.write("{\n");
				
				C_Typedefs c_Typedefs =new C_Typedefs();
				bw.write(c_Typedefs.getContent());
				
				
				D_InternalState d_InternalState =new D_InternalState(i);
				bw.write(d_InternalState.getContent());
				
				bw.write("\n");
				bw.write("public :\n");

				E_PartNameAccessor e_PartNameAccessor =new E_PartNameAccessor(i);
				bw.write(e_PartNameAccessor.getContent());
				
				
				
				
				
				
				bw.write("Prefab_" + PXA_name + "(void)\n");
				bw.write("{\n");

			
				
				F_Constructor f_Constructor =new F_Constructor(i);
				bw.write(f_Constructor.getContent());

				bw.write("}\n");
				bw.write("virtual ~Prefab_" + PXA_name + "(void){}\n");

				bw.write("virtual Element& getRoot() {return obj_root;}\n");

				G_AccessorToLocalObjects g_AccessorToLocalObjects =new G_AccessorToLocalObjects(i);
				bw.write(g_AccessorToLocalObjects.getContent());

				
				
				H_AccessorsToPrefab h_AccessorsToPrefab =new H_AccessorsToPrefab(i);
				bw.write(h_AccessorsToPrefab.getContent());

				bw.write("protected :\n");
				
				I_NestedPXAObjects i_NestedPXAObjects =new I_NestedPXAObjects(i);
				bw.write(i_NestedPXAObjects.getContent());
				
				J_Keys j_Keys =new J_Keys(i);
				bw.write(j_Keys.getContent());
				
				K_NestedPXAPrefab k_NestedPXAPrefab =new K_NestedPXAPrefab(i);
				bw.write(k_NestedPXAPrefab.getContent());



				bw.write("};\n");
				bw.write("#endif");

				bw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

//	public void generateHref(String dirGenerate, String hrefPath, int i)
//	{
//		try
//		{
//			String PXA_name;
//			if (hrefPath.contains("/"))
//
//				PXA_name = (hrefPath.substring(hrefPath.lastIndexOf('/') + 1)).replaceAll(".pxa", "");
//			else
//				PXA_name = hrefPath.replaceAll(".pxa", "");
//
//			File file = new File(dirGenerate + "\\CPanelGEN_" + PXA_name + ".hpp");
//
//			if (!file.exists())
//			{
//				file.createNewFile();
//			}
//
//			FileWriter fw = new FileWriter(file.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write("#ifndef I_PREFAB_" + PXA_name.toUpperCase());
//			bw.write("\n");
//			bw.write("#define I_PREFAB_" + PXA_name.toUpperCase());
//			bw.write("\n");
//
//			bw.write(
//					"// *********************************************************************************************************************");
//			bw.write("\n");
//			bw.write(
//					"//*                                                                                                                    *");
//			bw.write("\n");
//			bw.write(
//					"//*                                          CONFIDENTIAL VISTEON CORPORATION                                          *");
//			bw.write("\n");
//			bw.write(
//					"//*                                                                                                                    *");
//			bw.write("\n");
//			bw.write(
//					"//* This is an unpublished work of authorship, which contains trade secrets, created in 2014. Visteon Corporation      *");
//			bw.write("\n");
//			bw.write(
//					"//* owns all rights to this work and intends to maintain it in confidence to preserve its trade secret status.         *");
//			bw.write("\n");
//			bw.write(
//					"//* Visteon Corporation reserves the right, under the copyright laws of the United States or those of any other        *");
//			bw.write("\n");
//			bw.write(
//					"//* country that may have jurisdiction, to protect this work as an unpublished work, in the event of an inadvertent    *");
//			bw.write("\n");
//			bw.write(
//					"//* or deliberate unauthorized publication. Visteon Corporation also reserves its rights under all copyright laws      *");
//			bw.write("\n");
//			bw.write(
//					"//* to protect this work as a published work, when appropriate. Those having access to this work may not copy it,      *");
//			bw.write("\n");
//			bw.write(
//					"//* use it, modify it or disclose the information contained in it without the written authorization of                 *");
//			bw.write("\n");
//			bw.write(
//					"//* Visteon Corporation.                                                                                               *");
//			bw.write("\n");
//			bw.write(
//					"//*                                                                                                                    *");
//			bw.write("\n");
//			bw.write(
//					"// *********************************************************************************************************************");
//			bw.write("\n");
//			bw.write("// Language:            CPP");
//			bw.write("\n");
//			bw.write(
//					"// ---------------------------------------------------------------------------------------------------------------------");
//			bw.write("\n");
//			bw.write(
//					"//				      DO NOT CHANGE THIS FILE! IT HAS BEEN GENERATED. ANY CHANGES WILL BE OVERWRITTEN AT THE NEXT GENERATION!");
//			bw.write("\n");
//			bw.write(
//					"// ---------------------------------------------------------------------------------------------------------------------");
//			bw.write("\n");
//			bw.write("// Generated by the VISTEON PXA Generation chain ");
//			bw.write("\n");
//			bw.write(
//					"// ---------------------------------------------------------------------------------------------------------------------");
//			bw.write("\n");
//			bw.write("// Input file:          " + PXA_name + ".pxa");
//			bw.write("\n");
//			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//			Date date = new Date();
//			bw.write("// Input file date:        " + dateFormat.format(date));
//			bw.write("\n");
//			bw.write(
//					"// ---------------------------------------------------------------------------------------------------------------------");
//			bw.write("\n");
//			// bw.write("// Generator version: " +
//			// UtilForGeneratingCode.GeneratorVersion);
//			bw.write("\n");
//			bw.write("// Generator date:      " + dateFormat.format(date));
//			bw.write("\n");
//			bw.write(
//					"// *********************************************************************************************************************");
//			bw.write("\n");
//
//			bw.write("#include \"PSA_UI_RENDERER_PXA\\PSA_UI_RENDERER_PXA_CORE\\pxaframework.hpp\"\n");
//
//			bw.write("using namespace pxaframework;\n");
//
//			bw.write("template <class C>\n");
//
//			bw.write("class Prefab_" + PXA_name + " : public Prefab<C>\n");
//			bw.write("{\n");
//			bw.write("	typedef typename C::Element      Element;\n");
//			bw.write("	typedef typename C::Object       Object;\n");
//			bw.write("	typedef typename C::Transform    Transform;\n");
//			bw.write("	typedef typename C::Scale        Scale;\n");
//			bw.write("	typedef typename C::Item2D       Item2D;\n");
//			bw.write("	typedef typename C::Container    Container;\n");
//			bw.write("	typedef typename C::ContractItem ContractItem;\n");
//			bw.write("	typedef typename C::Group        Group;\n");
//			bw.write("	typedef typename C::Panel        Panel;\n");
//			bw.write("	typedef typename C::Image        Image;\n");
//			bw.write("	typedef typename C::Layout       Layout;\n");
//			bw.write("	typedef typename C::ColumnLayout ColumnLayout;\n");
//			bw.write("	typedef typename C::Label        Label;\n");
//			bw.write("	typedef typename C::Transition   Transition;\n");
//			bw.write("	typedef typename C::Animation           Animation;\n");
//			bw.write("	typedef typename C::AnimationGroup      AnimationGroup;\n");
//			bw.write("	typedef typename C::PropertyAnimation   PropertyAnimation;\n");
//			bw.write("	typedef typename C::ParallelAnimation   ParallelAnimation;\n");
//			bw.write("	typedef typename C::SequentialAnimation SequentialAnimation;\n");
//			bw.write("	typedef typename C::NumberAnimation     NumberAnimation;\n");
//			bw.write("	typedef typename C::PauseAnimation      PauseAnimation;\n");
//
//			bw.write("public :\n");
//
//			bw.write("Prefab_" + PXA_name + "(void)\n");
//			bw.write("{\n");
//
//			Vector allProperties = UtilForGeneratingHref.getAllProperties(i, hrefPath);
//			for (int j = 0; j < allProperties.size(); j++)
//			{
//				bw.write(allProperties.elementAt(j).toString());
//			}
//
//			bw.write("}\n");
//			bw.write("virtual ~Prefab_" + PXA_name + "(void){}\n");
//
//			bw.write("virtual Element& getRoot() {return obj_root;}\n");
//
//			Vector allinlineFunc = UtilForGeneratingHref.getAllInlineFunc(i, hrefPath);
//
//			for (int j = 0; j < allinlineFunc.size(); j++)
//			{
//				bw.write(allinlineFunc.elementAt(j).toString() + "\n");
//			}
//
//			bw.close();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//
//	}

}