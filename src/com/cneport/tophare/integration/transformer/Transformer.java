/**
 * 
 */
package com.cneport.tophare.integration.transformer;


/**
 * @author mayujian
 *
 */
public interface Transformer<Source,Target> {
	Target transform(Source source)throws Exception;
}
