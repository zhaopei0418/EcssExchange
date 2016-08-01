/**
 * 
 */
package com.cneport.tophare.integration.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cneport.tophare.integration.ExportServiceActivator;
import com.cneport.tophare.integration.MessageContext;
import com.cneport.tophare.integration.MessageProcessor;
import com.cneport.tophare.integration.transformer.jaxb.marshal.ObjectToByteArrayJaxbMarshallerTransformer;



/**
 * 
 * @author mayujian
 * 
 */
@Component
public  class SendXmlMessageProcessor implements MessageProcessor{
	private static final Log log = LogFactory.getLog(SendXmlMessageProcessor.class);
	@Autowired
	private ExportServiceActivator exportServiceActivator;
	@Autowired 
	private ObjectToByteArrayJaxbMarshallerTransformer objectToByteArrayJaxbMarshallerTransformer;
	/* (non-Javadoc)
	 * @see com.cneport.tophare.integration.MessageProcessor#process(com.cneport.tophare.integration.MessageContext, java.lang.Object)
	 */
	public void process(MessageContext context, Object payload) throws Exception {
		try{
			byte[] bytes  = objectToByteArrayJaxbMarshallerTransformer.transform(payload);
			exportServiceActivator.sendMessage(context, bytes);
		}catch(Exception ex){
			log.error("对象报文发送失败:",ex);
			throw ex;
		}
	}
}
