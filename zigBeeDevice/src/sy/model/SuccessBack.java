package sy.model;

public class SuccessBack {

	private Object data;
	private String msg;

	public SuccessBack(Object data, String msg) {
		super();
		this.data = data;
		this.msg = msg;
	}

	public Integer getResult() {
		return 1;
	}

	public Object getData() {
		return data;
	}

	public String getMsg() {
		return msg;
	}
}
