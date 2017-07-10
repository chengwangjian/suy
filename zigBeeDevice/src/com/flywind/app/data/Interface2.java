package com.flywind.app.data;

import java.util.List;

import org.dom4j.Element;



public interface Interface2<T,M,N> extends Interface<T,M> {

	@Override
	List<T> getMain();
	@Override
	List<M> getSub();
	List<N> getDeviceCheck();
	@Override
	void setMain(List<T> main);
	@Override
	void setSub(List<M> sub);	
	void setDeviceCheck(List<N> sub);	
	@Override
	Element toxml();
}
