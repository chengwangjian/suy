package sy.pageModel;

public class Json implements java.io.Serializable {

	private Integer status ;

	private Object message ;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}


}
