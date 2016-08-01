/**
 * 
 */
package com.cneport.tophare.para.support.jdbc;

import java.util.List;

import com.cneport.tophare.para.ParameterEntry;
import com.cneport.tophare.para.support.ParameterHandler;
import com.cneport.tophare.para.support.jdbc.persistence.ParameterMapper;

/**
 * @author mayujian
 *
 */

public class JdbcParameterHandler implements ParameterHandler{
	private String tableName ;
	private String tableCodeName;
	private String tableCodeDescriptionName;  
	private ParameterMapper parameterMapper;
	private String surffix="";
	private boolean autoConvert = true;
	
	@SuppressWarnings("unchecked")
	public List<ParameterEntry> getParameterEntry() {
		List<ParameterEntry>  parameterEntryList = parameterMapper.queryParameterEntry(tableName,tableCodeName,tableCodeDescriptionName);
		return parameterEntryList;
	}

	public Object getName(Object codeValue){
		List<ParameterEntry>  parameterEntryList = parameterMapper.queryParameterNameByCode(tableName,tableCodeName,tableCodeDescriptionName,(String)codeValue);
		return (parameterEntryList==null||parameterEntryList.isEmpty()) ? codeValue :  parameterEntryList.get(0).getName(); 
	}

	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}	
	public void setTableCodeName(String tableCodeName) {
		this.tableCodeName = tableCodeName;
	}
	public void setTableCodeDescriptionName(String tableCodeDescriptionName) {
		this.tableCodeDescriptionName = tableCodeDescriptionName;
	}
	public void setParameterMapper(ParameterMapper parameterMapper) {
		this.parameterMapper = parameterMapper;
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
