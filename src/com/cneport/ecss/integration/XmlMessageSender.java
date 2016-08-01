/**
 * 
 */
package com.cneport.ecss.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cneport.fine.kit.exception.FineException;
import com.cneport.tophare.integration.MessageContext;
import com.cneport.tophare.integration.processor.SendXmlMessageProcessor;

/**
 * 
 * @author mayujian
 * 
 */
@Component
public  class XmlMessageSender{
	private static final Log log = LogFactory.getLog(XmlMessageSender.class);
	@Autowired
	private SendXmlMessageProcessor sendXmlMessageProcessor;

	public void sendMessage(Object payload){
		try{ 
			sendXmlMessageProcessor.process(MessageContext.newInstance().setReceiver(""),payload);
			log.debug("发送报文["+payload.getClass().getSimpleName()+"]成功");
		}catch(Exception ex){
			log.error("发送报文["+payload.getClass().getSimpleName()+"]失败");
			throw FineException.getInstance(ex);
		} 
	}
	public void sendMessage(Object payload, String cspCode){
		try{ 
			sendXmlMessageProcessor.process(MessageContext.newInstance().setReceiver(cspCode), payload);
			log.debug("向["+cspCode+"]发送报文["+payload.getClass().getSimpleName()+"]成功");
		}catch(Exception ex){
			log.error("向["+cspCode+"]发送报文["+payload.getClass().getSimpleName()+"]失败");
			throw FineException.getInstance(ex);
		}
	}
}
