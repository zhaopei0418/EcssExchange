package com.cneport.tophare.integration.config;

import org.springframework.integration.Message;
import org.springframework.integration.file.FileNameGenerator;

/**
 * @author mayujian
 *
 */
public class DefaultFileNameGenerator implements FileNameGenerator {

	/* (non-Javadoc)
	 * @see org.springframework.integration.file.FileNameGenerator#generateFileName(org.springframework.integration.Message)
	 */
	private String prefix = "";
	private String suffix = ".xml";
	private boolean useUUID = false;
	private String timeFormat = "yyyyMMddHHmmssSSS";
	
	public String generateFileName(Message<?> message) {
		if(useUUID)
			return prefix + "_" + message.getHeaders().getId() + suffix;
		
		String time = new java.text.SimpleDateFormat(timeFormat).format(new java.util.Date());
		return prefix + time + "_" + System.nanoTime() + suffix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public void setUseUUID(boolean useUUID) {
		this.useUUID = useUUID;
	}
	
	public static void main(String[] args){
		java.util.Random r = new java.util.Random(Integer.MAX_VALUE);
		for(int i = 0; i < 30; i++){
			System.out.println("long======= " + r.nextLong());
			System.out.println(" Int======= " + r.nextInt());
			System.out.println("   n======= " + System.nanoTime());
			System.out.println("   c======= " + System.currentTimeMillis());
		}
	}
	
}
