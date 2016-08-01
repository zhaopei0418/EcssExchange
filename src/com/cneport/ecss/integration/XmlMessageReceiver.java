/**
 * 
 */
package com.cneport.ecss.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cneport.tophare.integration.MessageProcessor;
import com.cneport.tophare.integration.processor.AbstractXmlMessageProcessor;

/**
 * 
 * @author mayujian
 * 
 */
@Component
public class XmlMessageReceiver extends AbstractXmlMessageProcessor implements
		MessageProcessor {

	private static final Log log = LogFactory.getLog(XmlMessageReceiver.class);

	
	public void processData(Object object) {
		
	}
}
