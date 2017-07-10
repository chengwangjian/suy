package sy.pageModel;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author: zhuangxd
 * 时间：2013-12-5 下午4:11:24
 */
public class House {
	
	@XStreamAlias("Main")  
	private Main main;

	public House() {
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

}
