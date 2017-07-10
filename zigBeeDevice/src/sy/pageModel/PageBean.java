package sy.pageModel;

import javax.persistence.Transient;

/**
 * 分页使用
 * @author: zhuangxd
 * 时间：2014-6-30 下午2:29:10
 */
public class PageBean {
	
	// 非表字段
	private int startRow;
	private int pageSize;
	private int total;
	private String orderBy; // 页面排序用

	public PageBean() {
    }

	@Transient
	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	@Transient
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Transient
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	@Transient
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
