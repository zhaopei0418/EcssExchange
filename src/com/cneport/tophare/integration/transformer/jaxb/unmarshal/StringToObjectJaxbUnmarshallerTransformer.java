/**
 * 
 */
package com.cneport.tophare.integration.transformer.jaxb.unmarshal;

import java.io.StringReader;

import com.cneport.tophare.integration.transformer.Transformer;
/**
 * @author mayujian
 *
 */
public class StringToObjectJaxbUnmarshallerTransformer extends AbstractJaxbUnmarshallerTransformer<String,Object>  implements Transformer<String,Object>{
	/* (non-Javadoc)
	 * @see com.cneport.tophare.integration.transformer.Transformer#tranform(java.lang.Object)
	 */
	public Object transform(String message) throws Exception {
		return getUnmarshaller().unmarshal(new StringReader(message));
	}
}
