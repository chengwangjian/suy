package com.flywind.app.data;

public class DataGrid {

	private Object status_ob=null;
	private int status=1;
	private String status_msg="success";
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatus_msg() {
		return status_msg;
	}
	public void setStatus_msg(String status_msg) {
		this.status_msg = status_msg;
	}
	public Object getStatus_ob() {
		return status_ob;
	}
	public void setStatus_ob(Object status_ob) {
		this.status_ob = status_ob;
	}	


}
