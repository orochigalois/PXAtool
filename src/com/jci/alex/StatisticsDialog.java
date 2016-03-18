package com.jci.alex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class StatisticsDialog extends JDialog {
	  public StatisticsDialog(JFrame parent, final MainPanel mp) {
		  
	    super(parent, "Statistics", true);

	    JScrollPane sP1;
	    
	
	    
	    JTextArea txtInput = new JTextArea();
	    txtInput.setEditable(false);
	   
	    sP1 = new JScrollPane(txtInput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sP1.getVerticalScrollBar().setUnitIncrement(10);
		sP1.setBorder(ShadowBorder.getInstance());
	    getContentPane().add(sP1, "Center");

	    
	
	    
	    try
		{
			mp.statistics();
		} catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    txtInput.setText(mp.returnStatisticsResult());
	    
	    JPanel p2 = new JPanel();
	    JButton ok = new JButton("Ok");
	    p2.add(ok);
	    getContentPane().add(p2, "South");

	    ok.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	        setVisible(false);
	      }
	    });

	    setSize(300, 500);
	  }

	 
	}