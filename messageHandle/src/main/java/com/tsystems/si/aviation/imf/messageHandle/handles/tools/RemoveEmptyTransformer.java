package com.tsystems.si.aviation.imf.messageHandle.handles.tools;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoveEmptyTransformer {
	private static final Logger     logger               = LoggerFactory.getLogger(RemoveEmptyTransformer.class);
	
	private static String xslName = "remove_empty_nodes.xsl";
	  private static Transformer transformer;
	  private static URL urlFilterXSL = ConfigurationUtils.locate(xslName);
	  private static String xsl = null;
	  private static Source xslSource;

	  public static String xmlFilter(String xml)
	  {
		logger.info("Orignal Xml: \n{}", xml);
	    Source xmlInput = new StreamSource(new StringReader(xml));
	    StringWriter stringWriter = new StringWriter();
	    StreamResult xmlOutput = new StreamResult(stringWriter);
	    try {
	      transformer.transform(xmlInput, xmlOutput);
	    } catch (Exception e) {
	    	logger.error(e.getMessage(), e);
	      return xml;
	    }

	    logger.info("After filter: \n{}", xmlOutput.getWriter().toString());
	    return xmlOutput.getWriter().toString();
	  }

	  static
	  {
	    try
	    {
	      xsl = IOUtils.toString(urlFilterXSL, "utf-8");
	      logger.info("Loading  Filter XSL Success!");
	      logger.info("\n{}", xsl);
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	      logger.error("Loading  Filter XSL Failed!!!,System exit!!");
	      logger.error(e.getMessage(), e);
	      System.exit(0);
	    }

	    xslSource = new StreamSource(new StringReader(xsl));
	    try {
	      transformer = TransformerFactory.newInstance().newTransformer(xslSource);
	      transformer.setOutputProperty("indent", "yes");
	    }
	    catch (TransformerConfigurationException e) {
	      e.printStackTrace();
	      System.exit(0);
	    }
	    catch (TransformerFactoryConfigurationError e) {
	      e.printStackTrace();
	      System.exit(0);
	    }
	  }
}
