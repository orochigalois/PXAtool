package com.jci.alex;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.w3c.dom.DOMException;

import com.jci.alex.datainterface.DataInterfaceImage;

public class Constant
{
	/* store the path of index.xml */
	public static String workingPath;
	
	public static String excelPath;
	/* store all path in index.xml */
	public static Vector<String> allPanelPath = new Vector<String>();
	
	/* store the path of current PXA */
	public static String currentPanelPath;
	
	
	public static Vector<Vector> allNodesForRendering = new Vector<Vector>();
	
	/* store all treeModel */
	public static Vector<DefaultTreeModel> allTreeModel = new Vector<DefaultTreeModel>();
	
	/* stort current Index */
	public static int whichPanel;
	/* store Current TreePath */
	public static TreePath currentTreePath;
	
	public static String currentNodeName;
	
	/* store panel size */
	public static int iPanelSize;
	
	
	public static boolean isA_and_B_belong_to_the_same_href_node(TreePath A, TreePath B)
	{
		if(HrefRootOf(A).equals(HrefRootOf(B)))
			return true;
		else
			return false;
	}
	
	public static DefaultMutableTreeNode HrefRootOf(TreePath X)
	{
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) X.getLastPathComponent();
		Object nodeInfo = treeNode.getUserObject();
		org.w3c.dom.Node domNode = (org.w3c.dom.Node) nodeInfo;
		

		while(domNode.getAttributes().getNamedItem("href") == null&&treeNode.getParent()!=null)
		{
			X=X.getParentPath();
			treeNode=(DefaultMutableTreeNode) X.getLastPathComponent();
			nodeInfo = treeNode.getUserObject();
			domNode = (org.w3c.dom.Node) nodeInfo;
		}
		return treeNode;
	}
	public static boolean isAContainNoEqualB(TreePath A, TreePath B)
	{
		boolean is_it = true;
		if (A.getPathCount() >= B.getPathCount())
			return false;
		else
		{
			
			for (int i = 0; i < A.getPathCount(); i++)
			{
				if (!A.getPathComponent(i).equals(B.getPathComponent(i)))
				{
					is_it = false;
					break;
				}
			}
		}
		
		return is_it;
	}
	
	public static boolean isAContainB(TreePath A, TreePath B)
	{
		boolean is_it = true;
		if (A.getPathCount() > B.getPathCount())
			return false;
		else
		{
			
			for (int i = 0; i < A.getPathCount(); i++)
			{
				if (!A.getPathComponent(i).equals(B.getPathComponent(i)))
				{
					is_it = false;
					break;
				}
			}
		}
		
		return is_it;
	}
	
	public static boolean isAEqualB(TreePath A, TreePath B)
	{
		boolean is_it = true;
		if (A.getPathCount() != B.getPathCount())
			return false;
		else
		{
			
			for (int i = 0; i < A.getPathCount(); i++)
			{
				if (!A.getPathComponent(i).equals(B.getPathComponent(i)))
				{
					is_it = false;
					break;
				}
			}
		}
		
		return is_it;
	}
	//Update grammar for P2 project by Long 30,October,2015  
	//-------------Start--------------------------
	public static boolean isNumeric(String str)
	{ 
		   Pattern pattern = Pattern.compile("^-?[0-9]+"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
	}
//	public static int eval(String str)
//	{
//		ScriptEngineManager manager = new ScriptEngineManager();
//		ScriptEngine engine = manager.getEngineByName("js");
//		Object result = null;
//		int iResult = 0;
//		double douValue = 0;
//		
//		try {
//			result = engine.eval(str);
//		} catch (DOMException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ScriptException e) {
//			// TODO Auto- catch block
//			e.printStackTrace();
//		}
//		douValue = Double.parseDouble(result.toString());
//		iResult = (int)Math.floor(douValue);
//		return iResult;		
//	}
	
	public static Properties ambianceProperties=new Properties();
	public static String JSproperties;
	public static Object eval(String str) throws Exception{
	    ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	    
	    
	    //System.out.println(engine.eval(str));
	    return engine.eval(str);
	    
	}

	//-------------------------end--------------------------
	/* store current PXA name in the list */
	public static String currentPXAname;
	
	/* store table column width */
	public static int Column0 = 100;
	public static int Column1 = 150;
	public static int Column2 = 100;
	public static int Column3 = 100;
	public static int Column4 = 200;
	public static boolean processing;
	
	/* store the searching result */
	public static Vector<searchResult> vSearchResult = new Vector<searchResult>();
	
	public static Vector<statisticsResult> vStatisticsResult = new Vector<statisticsResult>();
	
	public static final int SEARCH_ALL = 0;
	public static final int SEARCH_NODENAME = 1;
	public static final int SEARCH_ATTRIBUTENAME = 2;
	public static final int SEARCH_ATTRIBUTEVALUE = 3;
	
	public static int getlocation(String str, String contain_pos)
	{
		int x;
		if (contain_pos == "")
		{
			if (!(str.equals("")))
			{
				x = Integer.parseInt(str);
			}
			else
			{
				x = 0;
			}
		}
		else
			x = Integer.parseInt(contain_pos);
		return x;
	}
	
	public static int getsize(String str, int imagesize, String contain_size)
	{
		
		int w;
		
		if (contain_size == "")
		{
			if (str.equals(""))
			{
				w = imagesize;
			}
			else
			{
				w = Integer.parseInt(str);
			}
		}
		else
			w = Integer.parseInt(contain_size);
		return w;
	}
	
	public static int string2integer(String str)
	{
		if(str==null)
			return 0;
		
		int Value=0;
		
		try
		{
			Value = Integer.parseInt(str);
		} catch (NumberFormatException e)
		{
			System.out.println("Number Format in not correct:" + str);
		}
		
		return Value;
		

	}
	
	public static boolean hasLoadedExcel = false;
	
	public static Vector<hrefRoot_name_value> vHrefRoot_name_value = new Vector<hrefRoot_name_value>();
	
	public static int screenWidth=320;
	public static int screenHeight=216;
	
	
	public static void Debug()
	{
		DataInterfaceImage diImage;
		for (int i = 0; i < Constant.allNodesForRendering.elementAt(
				Constant.whichPanel).size(); i++)
		{
			if (Constant.allNodesForRendering.elementAt(Constant.whichPanel)
					.elementAt(i) instanceof DataInterfaceImage)
			{
				diImage = (DataInterfaceImage) Constant.allNodesForRendering
						.elementAt(Constant.whichPanel).elementAt(i);
				System.out.println(diImage);

			}

		}
	}
	
	
	public static boolean shouldShowFrame;
	public static String inputIndexPath;
	public static String ambianceFilePath;
	public static String generateCodePath;
	
}

class searchResult
{
	int index;
	TreePath tp;
}

class statisticsResult
{
	String keyword;
	int Nb;
}

class hrefRoot_name_value
{
	DefaultMutableTreeNode hrefNode;
	String property_name;
	String property_value;
}