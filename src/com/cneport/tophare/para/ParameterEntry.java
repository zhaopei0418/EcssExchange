package com.cneport.tophare.para;

import java.io.Serializable;


public  class ParameterEntry implements Serializable{ 
	private static final long serialVersionUID = -4600252173016879529L;
		private Object code;
		private Object name;
		public ParameterEntry(){
		}
		public ParameterEntry(Object code,Object name){
			this.code = code;
			this.name = name;
		}
		public Object getCode() {
			return code;
		} 
		public void setCode(Object code) {
			this.code = code;
		}
		public Object getName() {
			return name;
		}
		public void setName(Object name) {
			this.name = name;
		}
		
		public String toString(){
			return "parameterEntry={code="+code+",name="+name+"}";
		}
		
	}