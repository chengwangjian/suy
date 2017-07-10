package com.flywind.app.dal;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;


public interface StartDAO {
	
	Session getSession();
	<T> T create(T t);
	<T> T update(T t);

    <T, PK extends Serializable> void delete(Class<T> type, PK id);
    <T> void delete(T type);
    
    <T, PK extends Serializable> T get(Class<T> type, Serializable id);  
    <T> T get(String hql, Map<String, Object> params);
	<T> List<T> find(String hql);//List<T> params);
	<T> List<T> find(String hql, Map<String, Object> params);

    <T> ResultSet executeHql(String hql);
	<T> int executeHql(String hql, Map<String, Object> params);
    <T> List<T> executeSql(String hql, Map<String, Object> params);
    <T> List<T> executeSql(String hql, Map<String, Object> params, Class<T> c);

    <T> List<T> findWithNamedQuery(String queryName);

    <T> List<T> findWithNamedQuery(String queryName, Map<String, Object> params);

    <T> T findUniqueWithNamedQuery(String queryName);

    <T> T findUniqueWithNamedQuery(String queryName, Map<String, Object> params);
    
    <T> int executeSql(String sql);
}
