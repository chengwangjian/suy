package com.flywind.app.data;

import java.util.List;

import com.flywind.app.entities.Modedevicebind;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DeviceBind")
public class Xmldevicebind {

	private List<Modedevicebind> Main;

	public List<Modedevicebind> getMain() {
		return Main;
	}
	public void setMain(List<Modedevicebind> main) {
		Main = main;
	}

	
}
