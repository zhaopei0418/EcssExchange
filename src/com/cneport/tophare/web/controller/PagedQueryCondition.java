/**
 * 
 */
package com.cneport.tophare.web.controller;


/**
 * @author mayujian
 *
 */
public class PagedQueryCondition {
	  private int totalRows;                //数据库总记录数
	  private int startIndex;               //待查询的数据库表记录的开始行索引
	  private int lastIndex;                //待查询的数据库表记录的截止行索引
	  private int start = 0;                //前台传递的数据记录索引
	  private int limit = 15;               //前台传递的每页记录数
	  
	  public int getTotalRows() {
		return this.totalRows;
	  }

	  public void setTotalRows(int totalRows) {
		 this.totalRows = totalRows;
	  }
	  public int getTotalPages()
	  {
	    int totalPages = 0;
	    if (this.totalRows % this.limit == 0)
	      totalPages = this.totalRows / this.limit;
	    else {
	      totalPages = this.totalRows / this.limit + 1;
	    }
	    return totalPages;
	  }

	  public int getStartIndex()
	  {
	    this.startIndex = ((getCurrentPage() - 1) * this.limit + 1);
	    return this.startIndex;
	  }

	  public int getLastIndex()
	  {
	    int currentPage = getCurrentPage();
	    if (this.totalRows < this.limit)
	      this.lastIndex = this.totalRows;
	    else if ((this.totalRows % this.limit == 0) || ((this.totalRows % this.limit != 0) && (currentPage < getTotalPages())))
	    {
	      this.lastIndex = (currentPage * this.limit);
	    } else if ((this.totalRows % this.limit != 0) && (currentPage == getTotalPages())) {
	      this.lastIndex = this.totalRows;
	    }
	    return this.lastIndex;
	  }

	  public int getStart() {
	    return this.start;
	  }

	  public void setStart(int start) {
	    this.start = start;
	  }

	  public int getLimit() {
	    return this.limit;
	  }

	  public void setLimit(int limit) {
	    this.limit = limit;
	  }

	  public int getCurrentPage()
	  {
	    int currentPage = 0;
	    if (currentPage > getTotalPages())
	      currentPage = getTotalPages();
	    else {
	      currentPage = getStart() / getLimit() + 1;
	    }
	    return currentPage;
	  }

}
