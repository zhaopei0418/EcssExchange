/**
 * 
 */
package com.cneport.tophare.para.support;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cneport.tophare.util.SpringApplicationContextSupport;


/**
 * @author mayujian
 *
 */
@Service
public class ParameterHandlerProvider{
	
	private static Map<String,ParameterHandler> parameterHandlerMap = new HashMap<String,ParameterHandler>();
	private final static Object lock = new Object();
	
    public	ParameterHandler getParameterHandler(String code){
    	if(parameterHandlerMap.isEmpty()){
    		initParameterHandlerMap();
    	}
    	return  parameterHandlerMap.get(code);
	}
    
    private  void  initParameterHandlerMap(){
    	if(parameterHandlerMap.isEmpty()){
    		synchronized (lock){
    			if(parameterHandlerMap.isEmpty()){
    				Map<String,ParameterHandler> parameterHandlerBeanTypes =  SpringApplicationContextSupport.getBeansOfType(ParameterHandler.class);
            		Iterator<String> iterator =parameterHandlerBeanTypes.keySet().iterator();
            		while(iterator.hasNext()){
            			String beanId = (String)iterator.next();
            			String[] aliases = SpringApplicationContextSupport.getAliases(beanId);
            			if(aliases!=null){
            				for(String alias : aliases){
            					parameterHandlerMap.put(alias, parameterHandlerBeanTypes.get(beanId));
            				}
            			}
            		}
            		parameterHandlerMap.putAll(parameterHandlerBeanTypes);
    			}
    		}
    	}
    }
    
}
