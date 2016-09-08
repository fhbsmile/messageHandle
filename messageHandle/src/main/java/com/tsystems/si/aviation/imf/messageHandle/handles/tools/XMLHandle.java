package com.tsystems.si.aviation.imf.messageHandle.handles.tools;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLHandle {
	private static final Logger     logger               = LoggerFactory.getLogger(XMLHandle.class);
	
	
	public static String rmNoUseAndEmptyNode(String xmlOrigin) throws DocumentException{

		       Document document = DocumentHelper.parseText(xmlOrigin);
		            @SuppressWarnings("unchecked")
					List<Element> list1 = document.selectNodes("//*[count(*)=0]");
	               if (list1 == null || list1.size() <= 0) {

		            }else{
		            for (Element e : list1) {
		                 if(e.getTextTrim().contains("@") || e.getTextTrim().equals("")){
		                 e.getParent().remove(e);
		                 }
		            }
		            }
		           
		        System.out.println(document.asXML());
		        return document.asXML();
	}
	
	public static String makeXMLMessage(HashMap<String,String> attrabuteForXMLMap,String xmlTemplate ,Transformer transformer){
		String xmlMessage = xmlTemplate;
    	for (Map.Entry<String, String> entry : attrabuteForXMLMap.entrySet()) {

			   String attrbuteName = entry.getKey();
			   String attrbuteValue = entry.getValue();
	    		
				  if(attrbuteValue!=null){
					  if(attrbuteName.equals("SendDateTime") && attrbuteName.equals("MessageSequenceID") && attrbuteName.equals("OriginalDateTime") && attrbuteName.equals("OperationMode") && attrbuteName.equals("CreateDateTime")){
						  logger.info("                    {}={}",new Object[]{attrbuteName,attrbuteValue});
					  }
					  
					  xmlMessage = StringUtils.replaceOnce(xmlMessage, "@"+attrbuteName+"@", attrbuteValue);
				  }else{
					  //logger.warn("Take care! attribute {}  is null, please check!", attrbuteName);
				  }
			  }
			//logger.debug("XML org: {}",xmlMessage);
			if(xmlMessage!=null){
				//按配置中的xsl过滤
				xmlMessage = XMLHandle.xmlFilter(transformer, xmlMessage);
				xmlMessage = XMLHandle.XMLformat(xmlMessage);
				//logger.debug("After filter:\n{}",xmlMessage);
				//去掉空节点和无用节点
				try {
					xmlMessage = XMLHandle.rmNoUseAndEmptyNode(xmlMessage);
					xmlMessage = XMLHandle.XMLformat(xmlMessage);
					//logger.debug("After rm No used:\n{}",xmlMessage);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				xmlMessage = RemoveEmptyTransformer.xmlFilter(xmlMessage);
				xmlMessage = XMLHandle.XMLformat(xmlMessage);
				
				
				//插入数据库
				logger.info("XML ready:\n{}",xmlMessage);
			 }
		return xmlMessage;
	}
	
	public static String xmlFilter(Transformer ts,String xml){
		String out = xml;
		if(ts!=null){
			Source xmlInput = new StreamSource(new StringReader(xml));
			StringWriter stringWriter = new StringWriter();
	        StreamResult xmlOutput = new StreamResult(stringWriter);
	        try {
	            ts.transform(xmlInput, xmlOutput);
	        } catch (Exception e) {
	            return xml;
	        }
	         out = xmlOutput.getWriter().toString();
		}else{
			logger.info("Transformer is null, XML wouldn't be transformed.");
		}

        return out;

	}
	
	   public static String XMLformat(String str) {
				SAXReader reader = new SAXReader();
				StringReader in = new StringReader(str);
				Document doc = null;
				try {
					doc = reader.read(in);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				OutputFormat formater = OutputFormat.createPrettyPrint();
				formater.setSuppressDeclaration(true);
				formater.setEncoding("utf-8");
				StringWriter out = new StringWriter();
				XMLWriter writer = new XMLWriter(out, formater);
				try {
					writer.write(doc);
	                writer.flush();
	                writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return out.toString();
				 
			} 
}
