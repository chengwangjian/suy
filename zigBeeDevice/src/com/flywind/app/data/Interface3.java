package com.flywind.app.data;

import java.util.List;

import org.dom4j.Element;



public interface Interface3<T,M,N> extends Interface<T,M> {

	@Override
	List<T> getMain();
	@Override
	List<M> getSub();
	List<N> getModemainclauses();
	@Override
	void setMain(List<T> main);
	@Override
	void setSub(List<M> sub);	
	void setModemainclauses(List<N> modemainclauses);	
	@Override
	Element toxml();
}
