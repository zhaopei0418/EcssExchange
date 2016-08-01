package com.cneport.tophare.web.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cneport.tophare.para.ParameterConvertor;
import com.cneport.tophare.util.SpringApplicationContextSupport;
 public   class ResponseDataHolder extends HashMap<String,Object>{
			private static final long serialVersionUID       = -8137055728569291873L;
			private static final String DATA_STATUS_KEY      = "success";
			private static final String ERROR_KEY            = "error";
			private static final String PAGED_TOTAL_ROWS_KEY = "totalRows";
			private static final String RESULT_DATA_KEY      = "data";
			private boolean hasNullResult = false;
			
			private static final String BIZ_ERROR_NULL_RESULTS = "系统中无所查数据。"; 
			private ParameterConvertor parameterConvertor;
			
			
			public ResponseDataHolder(){
				super();
				this.put(DATA_STATUS_KEY, true);
			}
			
			
			
			public void setNullResult(boolean isNullResult){
				if(isNullResult){
					hasNullResult = true;
					this.put(ERROR_KEY, BIZ_ERROR_NULL_RESULTS);
				}
			}
			public boolean hasNullResult(){
				return hasNullResult;
			}
			
			@SuppressWarnings("unchecked")
			public void addData(Object value){
				System.out.println(value.getClass().toString());
				addData(RESULT_DATA_KEY, value);
			}
			@SuppressWarnings("unchecked")
			public void addData(String key,Object value){
				addData(key,value,true);
			}
			@SuppressWarnings("unchecked")
			public void addData(Object value,boolean parameterAble){
				addData(RESULT_DATA_KEY, value,parameterAble);
			} 
			@SuppressWarnings("unchecked")
			public void addData(String key,Object value,boolean parameterAble){
				if(!parameterAble){
					if(value instanceof PagedQueryResult){
						PagedQueryResult pagedQueryResult = (PagedQueryResult)value;
						this.put(PAGED_TOTAL_ROWS_KEY, pagedQueryResult.getTotalRows());
						this.put(key, pagedQueryResult.getResultList());
					}else{
						this.put(key, value);
					}
				}else{
					if(value instanceof PagedQueryResult){
						PagedQueryResult pagedQueryResult = (PagedQueryResult)value;
						this.put(PAGED_TOTAL_ROWS_KEY, pagedQueryResult.getTotalRows());
						this.put(key, appendParaNamesForList(pagedQueryResult.getResultList())); 
					}else{
						this.put(key, appendParaNames(value));
					}
				}
				
			}
			
			
			

			@SuppressWarnings("unchecked")
			private List<Map<String,Object>>  appendParaNamesForList(List<?> list){
				if(list==null)
					return  Collections.emptyList();
				List result = new ArrayList(list.size());
				for(int i=0,size=list.size();i<size;i++){
					result.add(appendParaNames(list.get(i)));
				}
				return result;
			}
			@SuppressWarnings("unchecked")
			private Object appendParaNames(Object value){
				if(value instanceof List){
					return appendParaNamesForList((List)value);
				}				
				parameterConvertor = SpringApplicationContextSupport.getBean("parameterConvertor",ParameterConvertor.class); 
				return parameterConvertor.parameterlize(value);
			}
			public void removeData(String key,Object value){
				this.remove(key);
			}
			
			public void setError(){
				this.put(DATA_STATUS_KEY, false);
			}
			public void setError(String errorMessage){
				this.put(DATA_STATUS_KEY, false);
				this.put(ERROR_KEY, errorMessage);
			}
			
			
			@SuppressWarnings("unchecked")
			public String toString(){
				StringBuffer sb = new StringBuffer();
				sb.append("\n").append("StatisticDataHolder.toString():\n")
				  .append("{hasNullResult="+hasNullResult).append(";\n");
				for(String key : this.keySet()){
					Object value = this.get(key);	
					if(value instanceof String){
						printStringProperty(key,(String)value,sb);
					}
					if(value instanceof List){
						printListProperty(key,(List)value,sb);
					}
					
				}
				sb.append("}\n");
				return sb.toString();
			}



			/**
			 * @param value
			 * @param sb
			 */
			private void printStringProperty(String key,String value, StringBuffer sb) {
				sb.append("{").append(key).append("=").append(value).append("}\n");
			}
			/**
			 * @param bean
			 * @param sb
			 */
			@SuppressWarnings("unchecked")
			private void printListProperty(String key,List list, StringBuffer sb) {
				sb.append(key).append("={").append("\n");
				if(list!=null && !list.isEmpty()){
					for(int i = 0;i<list.size();i++){
						Object bean  = list.get(i);
						printJavaBeanProperty(key,bean,sb);
					}
				}
				sb.append("};\n");
				
			}
			/**
			 * @param bean
			 * @param sb
			 */
			private void printJavaBeanProperty(String key,Object bean, StringBuffer sb) {
				try{
					sb.append("Class=").append(bean.getClass().getSimpleName()).append(";values=[");
					Field[] fields  = bean.getClass().getDeclaredFields();
					for(int i = 0;i<fields.length;i++){
						fields[i].setAccessible(true);
						sb.append(fields[i].getName()).append("=").append(fields[i].get(bean));
						if(i!=(fields.length-1))
							sb.append(",");
					}
					sb.append("]").append("\n");
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
		}
