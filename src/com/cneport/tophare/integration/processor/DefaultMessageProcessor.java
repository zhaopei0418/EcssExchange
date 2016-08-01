/**
 * 
 */
package com.cneport.tophare.integration.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cneport.tophare.integration.MessageContext;
import com.cneport.tophare.integration.MessageProcessor;



/**
 * 默认的MessageProcessor,一般为测试用
 * @author mayujian
 * 
 */
@Component("defaultMessageProcessor")
public class DefaultMessageProcessor implements MessageProcessor{
	private static final Log log = LogFactory.getLog(DefaultMessageProcessor.class);

	/* (non-Javadoc) 
	 * @see com.cneport.tophare.integration.MessageProcessor#process(java.lang.Object)
	 */
	public void process(MessageContext context,Object message) {
		if(log.isInfoEnabled()){
			String clazz = message == null ? null : message.getClass().getName();
			log.info("接收到对象："+clazz);
		}
	}

}
