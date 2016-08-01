/**
 * 
 */
package com.cneport.tophare.integration.transformer.jaxb.marshal;

import java.io.File;
import java.io.FileOutputStream;

import com.cneport.tophare.integration.transformer.Transformer;
/**
 * @author mayujian
 *
 */
public class ObjectToFileJaxbMarshallerTransformer extends AbstractJaxbMarshallerTransformer<Object,File> implements Transformer<Object,File>{
	/* (non-Javadoc)
	 * @see com.cneport.tophare.integration.transformer.Transformer#tranform(java.lang.Object)
	 */
	public File transform(Object object) {
		FileOutputStream fileOutputStream =null;
		File file =null;
		try{
			file = File.createTempFile(object.getClass().getSimpleName()+"_"+System.nanoTime(), ".xml"); 
			fileOutputStream = new FileOutputStream(file);
			getMarshaller(object.getClass()).marshal(object, fileOutputStream);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				fileOutputStream.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return file;
	}
	
}
