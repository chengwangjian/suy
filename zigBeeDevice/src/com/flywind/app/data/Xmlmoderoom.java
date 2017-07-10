package com.flywind.app.data;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import sy.model.Moderoom;

import com.flywind.app.entities.Usermodemain;


public class Xmlmoderoom implements Interface<Moderoom,Usermodemain> {

	private List<Moderoom> Main;
	private List<Usermodemain> Sub;
	private List<Map> deviceList;

	@Override
	public List<Moderoom> getMain() {
		return Main;
	}
	@Override
	public void setMain(List<Moderoom> main) {
		Main = main;
	}
	@Override
	public List<Usermodemain> getSub() {
		return Sub;
	}
	@Override
	public void setSub(List<Usermodemain> sub) {
		Sub = sub;
	}
	public List<Map> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<Map> deviceList) {
		this.deviceList = deviceList;
	}
	@Override
	public Element toxml() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
