/**
 * 
 */
package com.cneport.tophare.integration.transformer.jaxb.unmarshal;

import java.io.ByteArrayInputStream;

import com.cneport.tophare.integration.transformer.Transformer;
/**
 * @author mayujian
 *
 */
public class ByteArrayToObjectJaxbUnmarshallerTransformer extends AbstractJaxbUnmarshallerTransformer<byte[],Object> implements Transformer<byte[],Object>{
	/* (non-Javadoc)
	 * @see com.cneport.tophare.integration.transformer.Transformer#tranform(java.lang.Object)
	 */
	public Object transform(byte[] bytes) throws Exception {
		return getUnmarshaller().unmarshal(new ByteArrayInputStream(bytes));
	}
}
