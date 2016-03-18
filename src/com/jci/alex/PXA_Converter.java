package com.jci.alex;

import java.awt.*;

import javax.swing.*;

/**
 * PXA_Converter
 * 
 * @version 1.0 2014-06-03
 * @author alex
 * 
 *         140613 TODO: 1.parse xsd files >>>done 2.render picture 3.state
 *         update picture 4.statistics panel 5.memory panel size 6.make list
 *         more beautiful 7.help movie 8.PXA file no change attributes order
 *         9.copy&paste from attribute table >>>done
 * 
 * 
 *         140727 TODO: 1.Container visible attribute 2.import excel
 */
public class PXA_Converter
{
	public static void main(String[] args)
	{
		if(args.length != 0)
	    {
	        Constant.shouldShowFrame=false;
	        Constant.inputIndexPath=args[0];
	        Constant.ambianceFilePath=args[1];
	        Constant.generateCodePath=args[2];
	        
	    }
		else
			Constant.shouldShowFrame=true;
	
		
		for (int i = 0; i < args.length; i++)
            System.out.println(args[i]);
		
		try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception fail) {
        }
		
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				MainFrame frame = new MainFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(Constant.shouldShowFrame);
				
				if(!Constant.shouldShowFrame)
				{
					frame.openFile(Constant.inputIndexPath,Constant.ambianceFilePath);
					
				}
				
			}
		});
	}
}
