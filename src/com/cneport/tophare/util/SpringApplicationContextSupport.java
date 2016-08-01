/**
 * 
 */
package com.cneport.tophare.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.cneport.tophare.persistence.DbCommonMapper;

/**
 * @author mayujian
 * 
 */
@Service
public class SpringApplicationContextSupport implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;

	public static Object getBean(String id) {
		return applicationContext.getBean(id);
	}

	public static <T> T getBean(String id, Class<T> clazz) {
		return applicationContext.getBean(id, clazz);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz);
	}

	public static DbCommonMapper getDbCommonMapper() {
		return getBean("dbCommonMapper", DbCommonMapper.class);
	}

	public static String[] getAliases(String beanid) {
		return applicationContext.getAliases(beanid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;

	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
