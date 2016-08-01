/**
 * 
 */
package com.cneport.tophare.integration.transformer.jaxb.marshal;

import java.io.StringWriter;

import com.cneport.tophare.integration.transformer.Transformer;
/**
 * @author mayujian
 *
 */
public class ObjectToStringJaxbMarshallerTransformer extends AbstractJaxbMarshallerTransformer<Object,String> implements Transformer<Object,String>{
	/* (non-Javadoc)
	 * @see com.cneport.tophare.integration.transformer.Transformer#tranform(java.lang.Object)
	 */
	public String transform(Object object) {
		StringWriter stringWriter = new StringWriter();
		try{
			getMarshaller(object.getClass()).marshal(object, stringWriter);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				stringWriter.close(); 
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return stringWriter.toString();
	}
}
