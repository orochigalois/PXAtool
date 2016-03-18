package com.jci.alex;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * MainFrame.
 */
public class MainFrame extends JFrame
{

	MainPanel mp;
	String dirPNG;
	String dirGenerate;
	String dirWord;
	String dirUserConfig;
	JMenuItem searchItem;

	public MainFrame()
	{
		setTitle("PXA Converter");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		JMenu fileMenu = new JMenu("File");
		JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				openFile(null, null);
			}
		});
		KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask());
		openItem.setAccelerator(ctrlO);
		fileMenu.add(openItem);

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
		fileMenu.add(exitItem);

		JMenu optionMenu = new JMenu("Option");
		JMenuItem exportItem = new JMenuItem("Export to PNG");
		exportItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				exportPNG();
			}
		});
		optionMenu.add(exportItem);

		JMenuItem exportWordItem = new JMenuItem("Export to Word");
		exportWordItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				exportWord();
			}
		});
		optionMenu.add(exportWordItem);

		searchItem = new JMenuItem("Search");
		searchItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				search();
			}
		});

		KeyStroke ctrlF = KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask());
		searchItem.setAccelerator(ctrlF);

		optionMenu.add(searchItem);

		JMenuItem statisticsItem = new JMenuItem("Statistics");
		statisticsItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				statistics();
			}
		});
		optionMenu.add(statisticsItem);

		JMenu configMenu = new JMenu("User Configuration");
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				saveUserConfiguration();
			}
		});
		KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask());
		saveItem.setAccelerator(ctrlS);
		configMenu.add(saveItem);

		JMenuItem loadItem = new JMenuItem("Load");
		loadItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				loadUserConfiguration();
			}
		});
		KeyStroke ctrlL = KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask());
		loadItem.setAccelerator(ctrlL);

		configMenu.add(loadItem);

		JMenuItem resetItem = new JMenuItem("Reset");
		resetItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				resetUserConfiguration();
			}
		});
		KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask());
		resetItem.setAccelerator(ctrlR);

		configMenu.add(resetItem);

		JMenuItem commentItem = new JMenuItem("Comment");
		commentItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				commentUserConfiguration();
			}
		});
		// configMenu.add(commentItem);

		JMenu generateMenu = new JMenu("Generate");
		JMenuItem generateItem = new JMenuItem("Generate Code");
		generateItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				generateCode(null);
			}
		});
		generateMenu.add(generateItem);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem tutoItem = new JMenuItem("Tutorial");
		tutoItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// openFile();
			}
		});
		// helpMenu.add(tutoItem);

		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				openAbout();
			}
		});
		helpMenu.add(aboutItem);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(optionMenu);
		menuBar.add(configMenu);
		menuBar.add(generateMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);

	}

	public void commentUserConfiguration()
	{

		JTextArea firstName = new JTextArea();

		final JComponent[] inputs = new JComponent[] { new JLabel("First"),
				firstName

		};
		JOptionPane.showMessageDialog(null, inputs, "My custom dialog",
				JOptionPane.PLAIN_MESSAGE);
		System.out.println("You entered " + firstName.getText());
	}

	public void resetUserConfiguration()
	{
		if (mp != null)
		{
			mp.resetUserConfiguration();
		}
	}

	public void loadUserConfiguration()
	{
		if (mp != null)

		{
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("."));

			chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
			{
				public boolean accept(File f)
				{
					return f.isDirectory()
							|| f.getName().toLowerCase()
									.endsWith("_user_config.xml");
				}

				public String getDescription()
				{
					return "user_config.xml";
				}
			});
			int r = chooser.showOpenDialog(this);
			if (r != JFileChooser.APPROVE_OPTION)
				return;
			final File file = chooser.getSelectedFile();

			new SwingWorker<Document, Void>()
			{
				protected Document doInBackground() throws Exception
				{
					if (builder == null)
					{
						DocumentBuilderFactory factory = DocumentBuilderFactory
								.newInstance();

						builder = factory.newDocumentBuilder();

					}
					return builder.parse(file);

				}

				protected void done()
				{
					try
					{
						Document doc = get();

						mp.loadUserConfiguration(doc);

					}
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(MainFrame.this, e);
					}
				}
			}.execute();
		}
	}

	public void saveUserConfiguration()
	{
		String tmpDir;
		if (mp != null)

		{
			JFileChooser c;
			if (null != dirUserConfig)
				c = new JFileChooser(dirUserConfig);
			else
				c = new JFileChooser();
			c.setSelectedFile(new File(Constant.currentPXAname
					+ "_user_config.xml"));

			int rVal = c.showSaveDialog(null);
			if (rVal == JFileChooser.APPROVE_OPTION)
			{

				tmpDir = c.getCurrentDirectory().toString();
				if (dirUserConfig != tmpDir)
					dirUserConfig = tmpDir;
				mp.saveUserConfiguration(dirUserConfig + "\\"
						+ c.getSelectedFile().getName());
			}
		}
	}

	public void search()
	{
		if (mp != null)
		{
			JDialog f = new SearchDialog(this, mp, searchItem);

			f.setDefaultCloseOperation(f.DO_NOTHING_ON_CLOSE);
			f.addWindowListener(new WindowAdapter()
			{
				@Override
				public void windowClosed(WindowEvent e)
				{
					searchItem.setEnabled(true);
				}
			});
			f.setLocationRelativeTo(this);
			f.setModal(false);
			f.setVisible(true);

			searchItem.setEnabled(false);

		}
	}

	public void statistics()
	{
		if (mp != null)
		{
			JDialog f = new StatisticsDialog(this, mp);

			f.setLocationRelativeTo(this);

			f.setVisible(true);
		}
	}

	public void exportWord()
	{
		String tmpDir;
		if (mp != null)

		{
			JFileChooser c;
			if (null != dirWord)
				c = new JFileChooser(dirWord);
			else
				c = new JFileChooser();
			c.setSelectedFile(new File(Constant.currentPXAname + ".doc"));

			int rVal = c.showSaveDialog(null);
			if (rVal == JFileChooser.APPROVE_OPTION)
			{
				// filename.setText(c.getSelectedFile().getName());
				// dir.setText(c.getCurrentDirectory().toString());

				tmpDir = c.getCurrentDirectory().toString();
				if (dirWord != tmpDir)
					dirWord = tmpDir;
				mp.exportWord(dirWord + "\\" + c.getSelectedFile().getName());
			}
		}

	}

	public void newWordDoc(String filename, String fileContent)
			throws Exception
	{
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph tmpParagraph = document.createParagraph();
		XWPFRun tmpRun = tmpParagraph.createRun();
		tmpRun.setText(fileContent);
		tmpRun.setFontSize(18);
		FileOutputStream fos = new FileOutputStream(new File("C:\\" + filename
				+ ".doc"));
		document.write(fos);
		fos.close();
	}

	public void generateCode(String exportPath)
	{

		if (exportPath == null)
		{
			String tmpDir;
			if (mp != null)

			{
				JFileChooser c;
				if (null != dirGenerate)
					c = new JFileChooser(dirGenerate);
				else
					c = new JFileChooser();

				c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int rVal = c.showSaveDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION)
				{

					tmpDir = c.getSelectedFile().toString();
					if (dirGenerate != tmpDir)
						dirGenerate = tmpDir;

					mp.generateCode(dirGenerate);

				}

			}

		}
		else
			mp.generateCode(exportPath);

	}

	public void openAbout()
	{
		JDialog f = new AboutDialog(this);

		f.setLocationRelativeTo(this);

		f.setVisible(true);
	}

	/**
	 * Open a file and load the document.
	 * 
	 * 
	 */
	public void openFile(final String inputFilePath,
			final String ambianceFilePath)
	{

		final File file;
		if (inputFilePath != null)
		{
			file = new File(inputFilePath);
		}
		else
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("."));

			chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
			{
				public boolean accept(File f)
				{
					return f.isDirectory()
							|| f.getName().toLowerCase().endsWith("index.xml");
				}

				public String getDescription()
				{
					return "index.xml";
				}
			});
			int r = chooser.showOpenDialog(this);
			if (r != JFileChooser.APPROVE_OPTION)
				return;
			file = chooser.getSelectedFile();
		}

		String absolutePath = file.getAbsolutePath();

		Constant.workingPath = absolutePath.substring(0,
				absolutePath.lastIndexOf(File.separator));

		new SwingWorker<Document, Void>()
		{
			protected Document doInBackground() throws Exception
			{
				if (builder == null)
				{
					DocumentBuilderFactory factory = DocumentBuilderFactory
							.newInstance();

					builder = factory.newDocumentBuilder();

				}
				return builder.parse(file);

			}

			protected void done()
			{
				try
				{

					if (inputFilePath != null)
						OpenAmbianceFile(ambianceFilePath);
					else
					{
						// Long add Themes 2,November,2015
						if (!OpenSkinsFile(Constant.workingPath))
						{
							JOptionPane.showMessageDialog(null,
									"Load file error: skins.xml");
						}
					}

					if (!OpenUIServerConfigFile(Constant.workingPath))
					{
						JOptionPane.showMessageDialog(null,
								"Load file error: UIServerConfig.pxa");
					}
					else
					{
					}
					Document doc = get();

					setLayout(new BorderLayout());

					clearConstant();
					mp = new MainPanel(doc);

					setContentPane(mp);
					validate();

					if (!Constant.shouldShowFrame)
					{
						generateCode(Constant.generateCodePath);
						System.exit(0);
					}

				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(MainFrame.this, e);
				}
			}
		}.execute();
	}

	public void clearConstant()
	{
		Constant.excelPath = "";
		Constant.allPanelPath.clear();
		Constant.currentPanelPath = "";
		Constant.allNodesForRendering.clear();
		Constant.allTreeModel.clear();
		Constant.whichPanel = 0;
		Constant.currentTreePath = null;
		Constant.vSearchResult.clear();
		Constant.vStatisticsResult.clear();

	}

	public void exportPNG()
	{
		String tmpDir;
		if (mp != null)

		{
			JFileChooser c;
			if (null != dirPNG)
				c = new JFileChooser(dirPNG);
			else
				c = new JFileChooser();
			c.setSelectedFile(new File(Constant.currentPXAname + ".png"));

			int rVal = c.showSaveDialog(null);
			if (rVal == JFileChooser.APPROVE_OPTION)
			{
				// filename.setText(c.getSelectedFile().getName());
				// dir.setText(c.getCurrentDirectory().toString());
				tmpDir = c.getCurrentDirectory().toString();
				if (dirPNG != tmpDir)
					dirPNG = tmpDir;
				mp.rP.exportPNG(dirPNG + "\\" + c.getSelectedFile().getName());

			}

		}
	}

	// deprecated
	public static void removeRecursively(Node node, short nodeType, String name)
	{
		if (node instanceof CharacterData
				&& (name == null || node.getNodeName().equals(name)))
		{
			node.getParentNode().removeChild(node);
		}
		else
		{
			// check the children recursively
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++)
			{
				removeRecursively(list.item(i), nodeType, name);
			}
		}
	}

	// deprecated
	public File trimFile(File rawFile) throws Exception
	{
		FileReader fr;

		fr = new FileReader(rawFile);

		BufferedReader br = new BufferedReader(fr);
		FileWriter fw = new FileWriter("outfile.tmp");
		String line;

		while ((line = br.readLine()) != null)
		{
			line = line.trim(); // remove leading and trailing whitespace
			if (!line.equals("")) // don't write out blank lines
			{
				fw.write(line, 0, line.length());
			}
		}
		fr.close();
		fw.close();

		File file = new File("outfile.tmp");
		return file;
	}

	public boolean OpenSkinsFile(String strFilePath) throws Exception
	{
		int iCounter = 0;
		int iACII = 0;
		String tempString = null;
		String tempS = null;
		String fullPath = strFilePath + "\\skins\\skins.xml";

		File workingFile = new File(fullPath);
		Long filelength = workingFile.length();
		byte[] filecontent = new byte[filelength.intValue()];

		FileInputStream in = new FileInputStream(workingFile);
		in.read(filecontent);
		tempString = new String(filecontent);
		in.close();

		int iTemp1 = tempString.lastIndexOf("id");
		int iTemp2 = tempString.lastIndexOf("/>");

		if (iTemp1 != -1)
		{
			tempS = tempString.substring(iTemp1 + 3, iTemp2);
			;
			tempS = tempS.substring(0, tempS.lastIndexOf(" propertyIndex"));
			tempS = tempS.replaceAll("\"", "");
			if (Constant.isNumeric(tempS))
			{
				iCounter = Integer.parseInt(tempS);
				Object[] possibleValues = new Object[iCounter];
				for (int i = 0; i < iCounter; i++)
				{
					iACII = i + 65;
					possibleValues[i] = "ambiance_"
							+ String.valueOf((char) iACII);
				}
				String selectedValue = (String) JOptionPane.showInputDialog(
						null, "Choose one", "Themes",
						JOptionPane.QUESTION_MESSAGE, null, possibleValues,
						possibleValues[0]);
				if ((selectedValue != null) && (selectedValue.length() > 0))
				{
					String sFullpath = Constant.workingPath + "\\skins\\"
							+ selectedValue + ".ini";
					OpenAmbianceFile(sFullpath);
					return true;
				}
			}
			else
			{
			}
		}
		else
		{
		}
		return false;
	}

	public boolean OpenUIServerConfigFile(String strFilePath) throws Exception
	{
		String fullPath = strFilePath + "\\UIServerConfig.pxa";
		DocumentBuilder builder = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		builder = factory.newDocumentBuilder();
		try
		{
			Node Pool0 = builder.parse(fullPath).getElementsByTagName("Pool")
					.item(0);
			Constant.screenWidth = Integer.parseInt(Pool0.getAttributes()
					.getNamedItem("width").getNodeValue());
			Constant.screenHeight = Integer.parseInt(Pool0.getAttributes()
					.getNamedItem("height").getNodeValue());
		}
		catch (Exception e)
		{
			return false;
		}

		return true;
	}

	// private boolean OpenAmbianceFile(String sFilePath) throws IOException
	// {
	// String strContext = null;
	// FileInputStream fr = new FileInputStream(sFilePath);
	// BufferedReader in = new BufferedReader(new
	// InputStreamReader(fr,"GB2312"));
	//
	// while((strContext = in.readLine())!= null)
	// {
	// if(strContext.contains("image_repository"))
	// {
	// Constant.ImagePath = strContext.substring(strContext.indexOf("\"")+1,
	// strContext.lastIndexOf("/"));
	// }
	//
	// }
	//
	// return false;
	// }

	public void OpenAmbianceFile(String sFilePath) throws IOException
	{
		Constant.ambianceProperties.load(new FileInputStream(sFilePath));
		Constant.JSproperties = "var $style = {properties: {";

		for (String key : Constant.ambianceProperties.stringPropertyNames())
		{
			if (key.matches("^[a-zA-Z0-9_]*$"))
			{
				String value = Constant.ambianceProperties.getProperty(key);
				// Open ambiance_A.ini, you will see this: image_repository =
				// "ambiance_A/"
				if (value.startsWith("\"") && value.endsWith("/\""))
					value = value.substring(1, value.length() - 2);

				Constant.JSproperties += key + ":'" + value + "',";
			}
		}
		Constant.JSproperties += "}};";

	}

	private DocumentBuilder builder;
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
}