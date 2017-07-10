package com.flywind.app.data;

import java.util.List;

import org.dom4j.Element;



public interface Interface<T,M> {

	List<T> getMain();
	List<M> getSub();
	void setMain(List<T> main);
	void setSub(List<M> sub);	
	Element toxml();
}
