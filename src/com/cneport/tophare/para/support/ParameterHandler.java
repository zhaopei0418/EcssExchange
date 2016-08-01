/**
 * 
 */
package com.cneport.tophare.para.support;

import java.util.List;

import com.cneport.tophare.para.ParameterEntry;

/**
 * @author mayujian
 *
 */
public interface ParameterHandler {	
	public List<ParameterEntry> getParameterEntry() ;
	public Object getName(Object code); 
	public String getSurffix();
	public void setSurffix(String surffix);
	public boolean isAutoConvert();
}
