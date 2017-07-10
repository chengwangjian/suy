package com.flywind.app.data;

public class MyException extends Exception {

	private Object status_ob=null;	
	public MyException(String message,Object status_ob) {
		super(message);
		this.status_ob = status_ob;
	}
	public Object getStatus_ob() {
		return status_ob;
	}
	public void setStatus_ob(Object status_ob) {
		this.status_ob = status_ob;
	}

}
