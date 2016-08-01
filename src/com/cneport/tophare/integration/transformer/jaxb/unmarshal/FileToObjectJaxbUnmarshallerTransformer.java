/**
 * 
 */
package com.cneport.tophare.integration.transformer.jaxb.unmarshal;

import java.io.File;

import com.cneport.tophare.integration.transformer.Transformer;
/**
 * @author mayujian
 *
 */
public class FileToObjectJaxbUnmarshallerTransformer extends AbstractJaxbUnmarshallerTransformer<File,Object>  implements Transformer<File,Object>{
	/* (non-Javadoc)
	 * @see com.cneport.tophare.integration.transformer.Transformer#tranform(java.lang.Object)
	 */
	public Object transform(File message) throws Exception {
		return getUnmarshaller().unmarshal(message);
	}
	
	
}
