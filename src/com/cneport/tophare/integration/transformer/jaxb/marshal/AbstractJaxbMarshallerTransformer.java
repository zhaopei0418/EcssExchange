/**
 * 
 */
package com.cneport.tophare.integration.transformer.jaxb.marshal;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.cneport.tophare.integration.transformer.Transformer;
/**
 * @author mayujian
 *
 */
public abstract class AbstractJaxbMarshallerTransformer<Source,Target> implements Transformer<Source,Target>{
	private Map<Class<?>,Marshaller> marshallers = new HashMap<Class<?>,Marshaller>();
	private boolean formated = true;
	protected Marshaller getMarshaller(Class<?> clazz)throws Exception{
		if(!marshallers.containsKey(clazz)){
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formated);
			marshallers.put(clazz, marshaller);
		}
		return marshallers.get(clazz);
	}
	
	public void setFormated(boolean formated) {
		this.formated = formated;
	}
}
