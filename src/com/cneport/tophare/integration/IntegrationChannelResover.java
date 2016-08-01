/**
 * 
 */
package com.cneport.tophare.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.stereotype.Component;


/**
 * @author mayujian
 *
 */
@Component
public class IntegrationChannelResover {
	@Autowired
	private DirectChannel receiveChannel;
	@Autowired
	private DirectChannel sendChannel;
	@Autowired
	private DirectChannel receiveBackupChannel;
	@Autowired
	private DirectChannel sendBackupChannel;
	@Autowired
	private DirectChannel sendErrorChannel;
	@Autowired
	private DirectChannel receiveErrorChannel;
	
	public MessageChannel getSendChannel(){
		return sendChannel;
	}
	public MessageChannel getSendBackupChannel(){
		return sendBackupChannel;
	}
	public MessageChannel getReceiveChannel(){
		return receiveChannel;
	}
	public MessageChannel getReceiveBackupChannel(){
		return receiveBackupChannel;
	}
	public MessageChannel getSendErrorChannel() {
		return sendErrorChannel;
	}
	public MessageChannel getReceiveErrorChannel() {
		return receiveErrorChannel;
	}
	
}
