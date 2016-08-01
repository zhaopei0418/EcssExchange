/**
 * 
 */
package com.cneport.tophare.para;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cneport.tophare.para.support.ParameterHandler;
import com.cneport.tophare.para.support.ParameterHandlerProvider;
import com.cneport.tophare.util.DateUtil;
import com.cneport.tophare.util.ObjectUtil;

/**
 * @author mayujian
 * 
 */
@Service
public class ParameterConvertor {
	@Autowired
	private ParameterHandlerProvider parameterHandlerProvider;

	@SuppressWarnings("unchecked")
	public Map<String, Object> parameterlize(Object object) {
		Map result = new HashMap();
		try {
			PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(object
					.getClass());
			for (int i = 0; i < pds.length; ++i) {
				if (pds[i].getName().equalsIgnoreCase("class"))
					continue;
				Method m = pds[i].getReadMethod();
				Object value = m.invoke(object, new Object[0]);
				if (value instanceof Date) {
					result.put(pds[i].getName(), DateUtil.getDate((Date) value));
				} else {
					result.put(pds[i].getName(), value);
				}
				
				if (!ObjectUtil.IsStringNullOrEmpty(value)) {//只有值不为空时才反填参数名称,加快系统响应时间
					ParameterHandler parameterHandler = parameterHandlerProvider
							.getParameterHandler(object.getClass().getName()
									+ "." + pds[i].getName());
					if (parameterHandler == null)
						parameterHandler = parameterHandlerProvider
								.getParameterHandler(pds[i].getName());
					if (parameterHandler != null
							&& parameterHandler.isAutoConvert()) {
						Object valueConverted = parameterHandler.getName(value);
						result.put(
								pds[i].getName()
										+ parameterHandler.getSurffix(),
								valueConverted);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Collection parameterlize(Collection<?> objectList) {
		if ((objectList == null) || (objectList.isEmpty())) {
			return Collections.EMPTY_LIST;
		}
		List resultMapList = new ArrayList();
		for (Iterator it = objectList.iterator(); it.hasNext();) {
			Object resultObj = it.next();
			Map resultMap = parameterlize(resultObj);
			resultMapList.add(resultMap);
		}
		return resultMapList;
	}

}
