/**
 * 
 */
package com.cneport.tophare.integration.transformer.jaxb.marshal;

import java.io.ByteArrayOutputStream;

import com.cneport.tophare.integration.transformer.Transformer;
/**
 * @author mayujian
 *
 */
public class ObjectToByteArrayJaxbMarshallerTransformer extends AbstractJaxbMarshallerTransformer<Object,byte[]>  implements Transformer<Object,byte[]>{
	/* (non-Javadoc)
	 * @see com.cneport.tophare.integration.transformer.Transformer#tranform(java.lang.Object)
	 */
	public byte[] transform(Object object) {
		ByteArrayOutputStream  byteArrayOutputStream = new ByteArrayOutputStream();
		try{
			getMarshaller(object.getClass()).marshal(object, byteArrayOutputStream);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				byteArrayOutputStream.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return byteArrayOutputStream.toByteArray();
	}
}
