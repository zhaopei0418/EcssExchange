/**
 * 
 */
package com.cneport.tophare.integration;

import java.util.HashMap;

import java.util.Map;


/**
 * @author mayujian
 *
 */
public class MessageContext {

	private Map<String,Object> items = new HashMap<String,Object>();//SpringIntegration中的MessageHeaders是一个Map;此属性用来跟MessageHeaders方便转换
	private String receiver;
	
	public MessageContext(){}
	public static MessageContext newInstance(){
		return new MessageContext();
	}
	
	
	
	public MessageContext setReceiver(String receiver) {
		this.receiver = receiver;
		this.items.put("receiver", receiver);
		return this;
	}
	public MessageContext setItems(Map<String, Object> items) {
		this.items = items;
		return this;
	}
	
	
	public Map<String, Object> getItems() {
		return items;
	}
	public String getReceiver() {
		return receiver;
	}
}
