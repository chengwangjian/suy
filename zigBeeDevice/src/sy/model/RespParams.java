package sy.model;

public class RespParams {
	private String request_id;
	private Object response_params;
	public Object getResponse_params() {
		return response_params;
	}
	public void setResponse_params(Object response_params) {
		this.response_params = response_params;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
}
