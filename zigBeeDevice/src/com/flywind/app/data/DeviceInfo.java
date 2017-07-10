package com.flywind.app.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author: zhuangxd
 * 时间：2013-12-5 下午4:11:24
 */
@XStreamAlias("DeviceInfo")
public class DeviceInfo {
	
	@XStreamAlias("Group")
	private Xmlgroup xmlgroup;

	public DeviceInfo() {
	}

	public Xmlgroup getXmlgroup() {
		return xmlgroup;
	}

	public void setXmlgroup(Xmlgroup xmlgroup) {
		this.xmlgroup = xmlgroup;
	}

}
