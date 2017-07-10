package sy.model;

public class ErrorBack {
	private String msg;

	public ErrorBack(String msg) {
		super();
		this.msg = msg;
	}

	// 失败错误
	public Integer getResult() {
		return 0;
	}

	public String getMsg() {
		return msg;
	}

}