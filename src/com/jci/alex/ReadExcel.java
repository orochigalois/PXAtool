package com.jci.alex;

import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

public class ReadExcel
{
	private static final org.apache.log4j.Logger logger =
			org.apache.log4j.Logger.getLogger(ReadExcel.class);
	public static String ExcelOpen(String strSource, String strTextID,
			String strLanguage) throws IOException
	{
		strSource = new ReadExcel().parseExcel(strSource, strTextID,
				strLanguage);
		return strSource;
	}
	
	private String parseExcel(String excelFile, String strTextID,
			String strLanguage) throws IOException
	{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelFile));
		HSSFWorkbook wbHssfWorkbook = new HSSFWorkbook(fs);
		HSSFSheet sheet = wbHssfWorkbook.getSheetAt(2);
		
		HSSFRow row = null;
		String data = null;
		int ilanguage = 0;
		int iFlag = 0;
		int iCellNum = sheet.getRow(0).getLastCellNum();
		
		iFlag = strTextID.indexOf("_");
		
		strTextID = strTextID.substring(0, iFlag).toUpperCase()
				+ strTextID.substring(iFlag);
		try
		{
			for (int i = 1; i <= iCellNum - 1; i++)
			{
				sheet.getRow(0).getCell(i).setCellType(Cell.CELL_TYPE_STRING);
				data = 	sheet.getRow(0).getCell(i).getStringCellValue();
				if (data.equals(strLanguage))
				{
					ilanguage = i;
					break;
				}
			}
			
			for (int i = 1; i <= sheet.getLastRowNum(); i++)
			{
				row = sheet.getRow(i);
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				data = row.getCell(0).getStringCellValue();
				
				if (data.contains(strTextID)) 
				{	
					if (row.getCell(ilanguage).getCellType() == HSSFCell.CELL_TYPE_STRING)
					{
						data = row.getCell(ilanguage).getRichStringCellValue().toString();
					}
					else if(row.getCell(ilanguage).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
					{
						data = String.valueOf(row.getCell(ilanguage).getNumericCellValue());
					}
					else 
					{
						data = "";
					}
					return data;
				}
			}
		} catch (NullPointerException e)
		{
			 logger.error(e, e);
		}
		return "";
	}
}
