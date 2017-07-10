package com.flywind.app.dal;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("startDao")
public class StartDAOImpl implements StartDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartDAOImpl.class);
    @Autowired
    @Qualifier("sessionFactory")   
    private SessionFactory sessionFacotry; 
//	public SessionFactory getSessionFacotry() {
//		return sessionFacotry;
//	}
//    @Autowired 
//	public void setSessionFacotry(SessionFactory sessionFacotry) {
//		this.sessionFacotry = sessionFacotry;
//	}
	@Override
	public Session getSession() {
		//��������ǿ�����(Required)�������ȡ����
        return sessionFacotry.getCurrentSession();
    }

    @Override
	public <T> T create(T t)
    {
//        getSession().persist(t);
//        getSession().flush();
//    	getSession().refresh(t);   	
    	getSession().save(t);
        return t;
    }

    @Override
	public <T> T update(T type)
    {
//    	getSession().merge(type);
    	getSession().update(type);
        return type;
    }
    
    @Override
	public <T, PK extends Serializable> void delete(Class<T> type, PK id)
    {
        @SuppressWarnings("unchecked")
        T ref = (T) getSession().get(type, id);
        getSession().delete(ref);
    }
	@Override
	public <T> void delete(T type) {
		getSession().delete(type);
	}
	
	@Override
	public <T, PK extends Serializable> T get(Class<T> type, Serializable id) {
		return (T) this.getSession().get(type, id);
	}
	
	@Override
	public <T> T get(String hql, Map<String, Object> params) {
		Query q = this.getSession().createQuery(hql);
		q.setProperties(params);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;		
	}
	
	@Override
	public <T> List<T> find(String hql){// List<T> params) {
		Query q = this.getSession().createQuery(hql);
//		q.setParameterList(hql, params);
		return q.list();
	}
	
	@Override
	public <T> List<T> find(String hql, Map<String, Object> params) {
		Query q = this.getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}	

	@Override
	public <T> ResultSet executeHql(String sql) {
		//((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1))
		ResultSet resultSet=getSession().doReturningWork(
                new ReturningWork<ResultSet>() {
                    @Override
                    public ResultSet execute(Connection conn) throws SQLException {
                        String sql="{CALL Modescheme(1 ,0,0,?)";
                        //PreparedStatement pstmt=conn.prepareStatement(sql);
                        CallableStatement cstmt = conn.prepareCall(sql);
                        cstmt.setObject("id", 122);
                        ResultSet resultSet=cstmt.executeQuery();
                        return resultSet;
                    }
                }
        );
		return resultSet;
	}  	
	@Override
	public <T> int executeHql(String sql, Map<String, Object> params) {
		SQLQuery q = getSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}	
	@Override
	public <T> List<T> executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getSession().createSQLQuery(sql);
		q.setProperties(params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				//.setResultTransformer(Transformers.aliasToBean(c));
		return q.list();
	}
	@Override
	public <T> List<T> executeSql(String sql, Map<String, Object> params, Class<T> c) {
		SQLQuery q = this.getSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.addEntity(c).list();
	}	
	
    @Override
	@SuppressWarnings("unchecked")
    public <T> List<T> findWithNamedQuery(String queryName)
    {
        return getSession().getNamedQuery(queryName).list();
    }

    @Override
	@SuppressWarnings("unchecked")
    public <T> List<T> findWithNamedQuery(String queryName, Map<String, Object> params)
    {
        Set<Entry<String, Object>> rawParameters = params.entrySet();
        Query query = getSession().getNamedQuery(queryName);

        for (Entry<String, Object> entry : rawParameters)
        {
            query.setParameter(entry.getKey(), entry.getValue());

        }
        return query.list();
    }

    @Override
	@SuppressWarnings("unchecked")
    public <T> T findUniqueWithNamedQuery(String queryName)
    {
        return (T) getSession().getNamedQuery(queryName).uniqueResult();
    }

    @Override
	@SuppressWarnings("unchecked")
    public <T> T findUniqueWithNamedQuery(String queryName, Map<String, Object> params)
    {
        Set<Entry<String, Object>> rawParameters = params.entrySet();
        Query query = getSession().getNamedQuery(queryName);

        for (Entry<String, Object> entry : rawParameters)
        {
            query.setParameter(entry.getKey(), entry.getValue());

        }
        return (T) query.uniqueResult();
    }

	@Override
	public <T> int executeSql(String sql) {
		Query q = this.getSession().createSQLQuery(sql);
		return q.executeUpdate();
	}
	

}
