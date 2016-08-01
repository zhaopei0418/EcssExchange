package com.cneport.tophare.para;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cneport.fine.kit.exception.FineException;
import com.cneport.tophare.para.support.ParameterHandlerProvider;
import com.cneport.tophare.web.controller.ResponseDataHolder;
 

@Controller
@RequestMapping("/parameter")
public class ParameterController {
	@Autowired
	private ParameterHandlerProvider parameterHandlerProvider;
	
    @RequestMapping(value = "/queryParametes")
    @ResponseBody
	public Map<String,Object> queryParametes(@RequestParam("paraName") String paraName){
    	 ResponseDataHolder responseDataHolder = new ResponseDataHolder(); 
         try {
     		List<ParameterEntry> parameterEntryList = parameterHandlerProvider.getParameterHandler(paraName).getParameterEntry();
     		responseDataHolder.addData(parameterEntryList,false);
         } catch (Exception e) {
             responseDataHolder.setError(FineException.getInstance(e).getErrorName());
         }
         return responseDataHolder;
	}
	
}
