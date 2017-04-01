package com.baiyi.watch.model;

/**
 * 请求响应，分页加载时的页面信息
 * @author Administrator
 *
 */
public class Pagemodel {
	private String page_count;    // 分页数
	private String rows_per_page; // 每页行数
	private String total;         // 总数
	private String page_current;  // 当前页号 
	
	public int getPage_count() {
		if (page_count != null && !page_count.equals("")) {
			return Integer.parseInt(page_count);
		}
		return 1;
	}

	public int getRows_per_page() {
		if (rows_per_page != null && !rows_per_page.equals("")) {
			return Integer.parseInt(rows_per_page);
		}
		return 1;
	}

	public int getTotal() {
		if (total != null && !total.equals("")) {
			return Integer.parseInt(total);
		}
		return 1;
	}

	public int getPage_current() {
		if (page_current != null && !page_current.equals("")) {
			return Integer.parseInt(page_current);
		}
		return 1;
	}

	
}
