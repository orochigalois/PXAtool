package com.jci.alex;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XsdHandler
{
	private DocumentBuilder builder;
	
	Vector<Document> vecDoc = new Vector<Document>();
	
	public Vector<AttributeData> attrMapData = new Vector<AttributeData>();
	
	public XsdHandler(Document doc) throws ParserConfigurationException,
			SAXException, IOException
	{
		NodeList n = doc.getElementsByTagName("panels");
		
		String rawStr = n.item(0).getAttributes()
				.getNamedItem("xsi:schemaLocation").getNodeValue();
		
		String relativePath = rawStr.substring(rawStr.indexOf(" ") + 1);
		
		String path = Constant.workingPath + "\\"
				+ relativePath.replace('/', '\\');
		File pxaXSD = new File(path);
		
		String absolutePath = pxaXSD.getAbsolutePath();
		
		String currentPath = absolutePath.substring(0,
				absolutePath.lastIndexOf(File.separator));
		
		if (builder == null)
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			
			builder = factory.newDocumentBuilder();
		}
		
		Document docXSD = builder.parse(pxaXSD);
		
		NodeList n1 = docXSD.getElementsByTagName("xs:include");
		
		for (int i = 0; i < n1.getLength(); i++)
		{
			String xsdPathtmp = n1.item(i).getAttributes()
					.getNamedItem("schemaLocation").getNodeValue();
			String xsdPath = currentPath + "\\" + xsdPathtmp.replace('/', '\\');
			
			File tmpFile = new File(xsdPath);
			
			vecDoc.add(builder.parse(tmpFile));
			
		}
		
		
	}
	
	public void makeAttributeMap(String elementName)
	{
		Document docTmp;
		NodeList nodeListTmp;
		NodeList nodeListTmp1;
		Element elementTmp;
		
		String nameTmp;
		String typeTmp;
		String useTmp;
		String defaultTmp;
		
		boolean isFound = false;
		
		attrMapData.clear();
		// Traverse through vecDoc to locate elementName
		for (int i = 0; i < vecDoc.size(); i++)
		{
			docTmp = vecDoc.elementAt(i);
			nodeListTmp = docTmp.getElementsByTagName("xs:complexType");
			for (int j = 0; j < nodeListTmp.getLength(); j++)
			{
				if (nodeListTmp.item(j).getAttributes().getNamedItem("name") != null)
				{
					if (nodeListTmp.item(j).getAttributes()
							.getNamedItem("name").getNodeValue()
							.equals(elementName))
					{
						
						if (nodeListTmp.item(j) instanceof Element)
						{
							elementTmp = (Element) nodeListTmp.item(j);
							nodeListTmp1 = elementTmp
									.getElementsByTagName("xs:attribute");
							
							for (int h = 0; h < nodeListTmp1.getLength(); h++)
							{
								if (nodeListTmp1.item(h).getAttributes()
										.getNamedItem("name") != null)
									nameTmp = nodeListTmp1.item(h)
											.getAttributes()
											.getNamedItem("name")
											.getNodeValue();
								else
									nameTmp = "";
								
								if (nodeListTmp1.item(h).getAttributes()
										.getNamedItem("default") != null)
									defaultTmp = nodeListTmp1.item(h)
											.getAttributes()
											.getNamedItem("default")
											.getNodeValue();
								else
									defaultTmp = "";
								if (nodeListTmp1.item(h).getAttributes()
										.getNamedItem("type") != null)
									typeTmp = nodeListTmp1.item(h)
											.getAttributes()
											.getNamedItem("type")
											.getNodeValue();
								else
									typeTmp = "";
								if (nodeListTmp1.item(h).getAttributes()
										.getNamedItem("use") != null)
									useTmp = nodeListTmp1.item(h)
											.getAttributes()
											.getNamedItem("use").getNodeValue();
								else
									useTmp = "";
								
								attrMapData.add(new AttributeData(nameTmp,
										defaultTmp, typeTmp, useTmp));
								
							}
							isFound = true;
							break;
						}
						
					}
				}
			}
			if (isFound)
				break;
		}
		
	}
}
