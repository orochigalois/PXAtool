package com.jci.alex;

public class AttributeData
{
	private String AttrName;
	
	private String AttrDefault;
	
	private String AttrType;
	
	private String AttrUse;
	
	public AttributeData(String aa, String bb, String cc, String dd)
	{
		AttrName = aa;
		AttrDefault = bb;
		AttrType = cc;
		AttrUse = dd;
		
	}
	
	public String getAttrName()
	{
		return AttrName;
	}
	
	public String getAttrDefault()
	{
		return AttrDefault;
	}
	
	public String getAttrType()
	{
		return AttrType;
	}
	
	public String getAttrUse()
	{
		return AttrUse;
	}
	
	public void setAttrName(String aa)
	{
		AttrName = aa;
	}
	
	public void setAttrDefault(String aa)
	{
		AttrDefault = aa;
	}
	
	public void setAttrType(String aa)
	{
		AttrType = aa;
	}
	
	public void setAttrUse(String aa)
	{
		AttrUse = aa;
	}
	
}
