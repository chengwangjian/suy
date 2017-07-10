package com.flywind.app.data;

public class Json implements java.io.Serializable {

	private String request_id = "1234";
	private Object response_params = null;


	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public Object getResponse_params() {
		return response_params;
	}

	public void setResponse_params(Object response_params) {
		this.response_params = response_params;
	}


}
