/**
 * 
 */
package com.cneport.tophare.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Headers;
import org.springframework.integration.support.MessageBuilder;




/**
 * @author mayujian
 *
 */
public class ImportServiceActivator {
	private static final Log log = LogFactory.getLog(ImportServiceActivator.class);
	
	private IntegrationChannelResover integrationChannelResover;
	private List<MessageProcessor> messageProcessors = new ArrayList<MessageProcessor>();
	private boolean isBackup = true;
	
	
	public   void receiveMessage(@Headers Map<String, Object> headers,Object data){
		Message<?> message =null;
		try{
			for(MessageProcessor processor : messageProcessors){
				processor.process(null,data);
			}
			if(isBackup){
				message= MessageBuilder.withPayload(data).copyHeadersIfAbsent(headers).build();
				integrationChannelResover.getReceiveBackupChannel().send(message);
			}
		}catch(Exception ex){
			log.error("接收处理数据出错:",ex);
			message= MessageBuilder.withPayload(data).copyHeadersIfAbsent(headers).build();
			integrationChannelResover.getReceiveErrorChannel().send(message);
		}
	}	
	public void setMessageProcessors(List<MessageProcessor> messageProcessors) {
		if(messageProcessors!=null)
			this.messageProcessors = messageProcessors;
	}
	public void setIntegrationChannelResover(IntegrationChannelResover integrationChannelResover) {
		this.integrationChannelResover = integrationChannelResover;
	}
}
