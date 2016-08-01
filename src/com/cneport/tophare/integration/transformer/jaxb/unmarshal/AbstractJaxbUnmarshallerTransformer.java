/**
 * 
 */
package com.cneport.tophare.integration.transformer.jaxb.unmarshal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.cneport.tophare.integration.transformer.Transformer;
/**
 * @author mayujian
 *
 */
public abstract class AbstractJaxbUnmarshallerTransformer<Source,Target> implements Transformer<Source,Target>{
	
	private List<String> scanPackages = new ArrayList<String>();
	private Unmarshaller unmarshaller;
	
	protected Unmarshaller getUnmarshaller()throws Exception{
		if(unmarshaller==null){
			String contextPath="";
			for(int i=0,j=scanPackages.size();i<j;i++){
				contextPath +=   scanPackages.get(i);
				if(i < (j-1)){
					contextPath += ":";
				}				
			}
			JAXBContext jaxbContext = JAXBContext.newInstance(contextPath); 
			unmarshaller = jaxbContext.createUnmarshaller();
		}
		return unmarshaller;
	}
	public List<String> getScanPackages() {
		return scanPackages;
	}
	public void setScanPackages(List<String> scanPackages) {
		this.scanPackages = scanPackages;
	}
}
