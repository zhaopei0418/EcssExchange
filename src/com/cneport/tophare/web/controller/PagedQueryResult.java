/**
 * 
 */
package com.cneport.tophare.web.controller;

import java.util.ArrayList;
import java.util.List;


/**
 * @author mayujian
 *
 */
public class PagedQueryResult<T> {                        
	private List<T> resultList = new ArrayList<T>();           //查询结果集
	private PagedQueryCondition pagedQueryCondition;           //分页查询条件
	
	
	public PagedQueryResult(PagedQueryCondition pagedQueryCondition){
		this.pagedQueryCondition = pagedQueryCondition;
	}
	
	public PagedQueryResult(PagedQueryCondition pagedQueryCondition, List<T> resultList){
		this.pagedQueryCondition = pagedQueryCondition;
		this.setResultList(resultList);
	}
	
	public int getTotalRows() {
		return this.pagedQueryCondition.getTotalRows();
	}
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList == null ? new ArrayList<T>() : resultList;
	}
	public PagedQueryCondition getPagedQueryCondition() {
		return pagedQueryCondition;
	}
	public void setPagedQueryCondition(PagedQueryCondition pagedQueryCondition) {
		this.pagedQueryCondition = pagedQueryCondition;
	}
}
