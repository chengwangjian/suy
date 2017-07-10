package com.flywind.app.dal;

import java.util.List;

import org.hibernate.Session;

import sy.test.User;

public class SearchDAOImpl implements SearchDAO{

	private Session session;
	
	public SearchDAOImpl(Session session){
		this.session = session;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> searchUserQuery(String queryName){
		StringBuffer sql = new StringBuffer();
		sql.append("from User u ");
		sql.append("where u.name like:key ");
		return session.createQuery(sql.toString()).setParameter("key", "%" + queryName + "%").list();
	}
	
}
