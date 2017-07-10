package sy.pageModel;

import sy.model.Modehouse;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author: zhuangxd
 * 时间：2013-12-5 下午4:11:24
 */
public class Main {
	
	@XStreamAlias("ID")  
	@XStreamAsAttribute
	private Modehouse modehouse;

	public Main() {
	}

	public Modehouse getModehouse() {
		return modehouse;
	}

	public void setModehouse(Modehouse modehouse) {
		this.modehouse = modehouse;
	}

}
