/**
 * 
 */
package com.cneport.tophare.integration.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cneport.tophare.integration.MessageContext;
import com.cneport.tophare.integration.MessageProcessor;
import com.cneport.tophare.integration.transformer.jaxb.unmarshal.ByteArrayToObjectJaxbUnmarshallerTransformer;



/**
 * 
 * @author mayujian
 * 
 */
public abstract class AbstractXmlMessageProcessor implements MessageProcessor{
	private static final Log log = LogFactory.getLog(AbstractXmlMessageProcessor.class);
	@Autowired
	private ByteArrayToObjectJaxbUnmarshallerTransformer byteArrayToObjectJaxbUnmarshallerTransformer;
	/* (non-Javadoc)
	 * @see com.cneport.tophare.integration.MessageProcessor#process(java.lang.Object)
	 */
	public void process(MessageContext context,Object message)throws Exception {
		try{
    		Object data = this.byteArrayToObjectJaxbUnmarshallerTransformer.transform((byte[])message);
    		this.processData(data);
    	}catch(Exception ex){
    		log.error("接收xml文件处理失败", ex);
    		throw ex;
    	}
	}
	protected abstract void processData(Object data);
}
