/**
 * 
 */
package com.cneport.tophare.para.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cneport.tophare.para.ParameterEntry;


/**
 * @author mayujian
 *
 */
public class HashMapParameterHandler implements ParameterHandler{	
	
	private Map<Object,Object> parameters = new HashMap<Object,Object>();

	private String surffix="";
	private boolean autoConvert = true;
	
	
	@SuppressWarnings("unchecked")
	public List<ParameterEntry> getParameterEntry() {
		List<ParameterEntry> parameterEntryList = new ArrayList<ParameterEntry>();
		for(Iterator it = parameters.entrySet().iterator();it.hasNext();){
			Map.Entry<String,String> entry = (Map.Entry)it.next();
			parameterEntryList.add(new ParameterEntry(entry.getKey(),entry.getValue()));
		}
		return parameterEntryList;
	}
	
	public Object getName(Object code){
		return parameters.containsKey(code) ? parameters.get(code) : code;
	}

	
	public void setParameters(Map<Object, Object> parameters) {
		this.parameters = parameters;
	}

	/* (non-Javadoc)
	 * @see com.cneport.tophare.para.support.ParameterHandler#getSurffix()
	 */
	public String getSurffix() {
		return surffix;
	}

	/* (non-Javadoc)
	 * @see com.cneport.tophare.para.support.ParameterHandler#setSurffix(java.lang.String)
	 */
	public void setSurffix(String surffix) {
		this.surffix = surffix;
		
	}

	
	public boolean isAutoConvert() {
		return autoConvert;
	}

	
	public void setAutoConvert(boolean autoConvert) {
		this.autoConvert = autoConvert;
	}

	
}
