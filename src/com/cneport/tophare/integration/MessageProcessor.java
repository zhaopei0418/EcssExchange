/**
 * 
 */
package com.cneport.tophare.integration;


/**
 * @author mayujian
 *
 */
public interface MessageProcessor {
	public void process(MessageContext context,Object payload) throws Exception;
}
