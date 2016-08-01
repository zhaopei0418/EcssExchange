/**
 * 
 */
package com.cneport.tophare.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Headers;
import org.springframework.integration.support.MessageBuilder;

/**
 * @author mayujian
 *
 */

public class ExportServiceActivator{
	private static final Log log = LogFactory.getLog(ExportServiceActivator.class);
	private IntegrationChannelResover integrationChannelResover;
	private boolean isBackup = true;
	public void sendMessage(Object object){
		sendMessage(null,object);
	}
	public void sendMessage(@Headers MessageContext context, Object object){
		Message<?> message =null;
		try{
			if(context!=null){
				message = MessageBuilder.withPayload(object).copyHeadersIfAbsent(context.getItems()).build();
			}else{
				message = MessageBuilder.withPayload(object).build();
			}
			integrationChannelResover.getSendChannel().send(message);
			if(isBackup){
				integrationChannelResover.getSendBackupChannel().send(message);
			}
		}catch(Exception e){
			log.error("发送报文错误:",e);
			integrationChannelResover.getSendErrorChannel().send(message);
		}
	}
	public void setIntegrationChannelResover(IntegrationChannelResover integrationChannelResover) {
		this.integrationChannelResover = integrationChannelResover;
	}
}
