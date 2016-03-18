/**
 * 
 */
package com.jci.alex.generator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * @author YXIN1
 *
 */
public class C_Typedefs
{
	private String Content="";
	public C_Typedefs()
	{
		Content+="	typedef typename C::Element      Element;\n";
		Content+="	typedef typename C::Object       Object;\n";
		Content+="	typedef typename C::Transform    Transform;\n";
		Content+="	typedef typename C::Scale        Scale;\n";
		Content+="	typedef typename C::Item2D       Item2D;\n";
		Content+="	typedef typename C::Container    Container;\n";
		Content+="	typedef typename C::ContractItem ContractItem;\n";
		Content+="	typedef typename C::Group        Group;\n";
		Content+="	typedef typename C::Panel        Panel;\n";
		Content+="	typedef typename C::Image        Image;\n";
		Content+="	typedef typename C::Layout       Layout;\n";
		Content+="	typedef typename C::ColumnLayout ColumnLayout;\n";
		Content+="	typedef typename C::Label        Label;\n";
		Content+="	typedef typename C::Transition   Transition;\n";
		Content+="	typedef typename C::Animation           Animation;\n";
		Content+="	typedef typename C::AnimationGroup      AnimationGroup;\n";
		Content+="	typedef typename C::PropertyAnimation   PropertyAnimation;\n";
		Content+="	typedef typename C::ParallelAnimation   ParallelAnimation;\n";
		Content+="	typedef typename C::SequentialAnimation SequentialAnimation;\n";
		Content+="	typedef typename C::NumberAnimation     NumberAnimation;\n";
		Content+="	typedef typename C::PauseAnimation      PauseAnimation;\n";
	}
	
	
	public String getContent()
	{
		return Content;
	}
}
