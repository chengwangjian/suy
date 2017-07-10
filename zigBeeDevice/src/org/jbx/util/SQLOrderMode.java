package org.jbx.util;

/**
 * SQL 语句排序模式：降序和升序
 *
 * @datetime 2011-12-16
 * @author yuguitao
 */
public enum SQLOrderMode {
	/**
	 * 升序
	 */
	ASC("ASC"),
	
	/**
	 * 降序
	 */
	DESC("DESC"),
	
	/**
	 * 
	 */
	NOSORT("NOSORT");
	
	private SQLOrderMode(String mode) {
		this.mode = mode;
	}
	
	private String mode;
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
}
