package sy.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sy.dao.BaseDaoI;

@Repository("baseDao")
@SuppressWarnings("all")
public class BaseDaoImpl<T> implements BaseDaoI<T> {

	@Resource
	private SessionFactory sessionFactory;
	private static Connection connection;
//	private final ThreadLocal<T> session = new ThreadLocal<T>();
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

//	@Autowired
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}

	private Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
//		Session session_ = (Session)session.get();
//        if(session_ == null){
//        	session_ = sessionFactory.getCurrentSession();
//            session.set((T)session_);
//        }
//        return session_;
	}

	@Override
	public Serializable save(T o) {
		
		return this.getCurrentSession().save(o);
	}

	
	@Override
	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	@Override
	public T get(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	@Override
	public void update(T o) {
		this.getCurrentSession().update(o);
	}

	@Override
	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	@Override
	public List<T> find(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.list();
	}
	

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}
	
	@Override
	public List<T> findSql(String sql, Map<String, Object> params,Class<T> c) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
//		return q.setResultTransformer(Transformers.aliasToBean(c)).list();
		return q.addEntity(c).list();
	}
	
	@Override
	public List<Object[]> findSql(String sql, Map<String, Object> params,Class s, Class e) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
//		return q.setResultTransformer(Transformers.aliasToBean(c)).list();
//		SQLQuery ss=(SQLQuery) session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//		SQLQuery s3=(SQLQuery) session.createSQLQuery(sql).setResultTransformer(Transformers.TO_LIST);
		return q.addEntity("s", s).addEntity("e", e).list();
	}

	@Override
	public List<Object[]> findsep(String sql, Map<String, Object> params,Class s, Class e,Class p) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
//		return q.setResultTransformer(Transformers.aliasToBean(c)).list();
//		SQLQuery ss=(SQLQuery) session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//		SQLQuery s3=(SQLQuery) session.createSQLQuery(sql).setResultTransformer(Transformers.TO_LIST);
		return q.addEntity("s", s).addEntity("e", e).addEntity("p",p).list();
	}
	
	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<T> find(String hql, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public Long count(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	@Override
	public Long count(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public int executeHql(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}
	
	@Override
	public int executeSql2(String sql) {
		Query q = this.getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}
	
	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}
	
	@Override
	public int executeSql2(String sql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}
	
	
	
	@Override
	public List<T> executeSql(String sql) {
		Query q = this.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	@Override
	public List<T> executeSql(String sql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);;
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);  
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
                if(obj instanceof Collection<?>){  
                    q.setParameterList(key, (Collection<?>)obj);  
                }else if(obj instanceof Object[]){  
                    q.setParameterList(key, (Object[])obj);  
                }else{  
    				q.setParameter(key, params.get(key));  
                }  
				
			}
		}
		return q.list();
	}
	
	/**  
	 * 查询数据库是否有某表  
	 * @param tableName  
	 * @throws Exception  
	 */  
	  @SuppressWarnings("unchecked")   
	  public boolean getAllTableName(String tableName) throws Exception { 
		  Work work = new Work() {			  
			  @Override
			public void execute(Connection connection) {     
				  // 这里已经得到connection了，可以继续你的JDBC代码。     
				  // 注意不要close了这个connection。 
				  BaseDaoImpl.connection = connection;				  
			  } 			  
		  };   
		  this.getCurrentSession().doWork(work); 
		  ResultSet tabs = null;   
	      try {   
	    	  DatabaseMetaData dbMetaData = connection.getMetaData();     
          String[]   types   =   { "TABLE" };   
          tabs = dbMetaData.getTables(null, null, tableName, types);   
          if (tabs.next()) {   
        	  return true; 
	       }   
	      } 
//	      catch (Exception e) { 
//	    	  
//           e.printStackTrace();   
//	      }
	      finally{   
	          tabs.close(); 
	          connection.close(); 
	      }   
	  	return false;  
	}  
	  
	  /**  
	   * 根据表名称创建一张表  
	   * @param tableName  
	   */  
	  /*public static int createTable(String tableName,Object obj){   
		  StringBuffer sb = new StringBuffer("");   
		  sb.append("CREATE TABLE `" + tableName + "` (");   
		  sb.append(" `id` int(11) NOT NULL AUTO_INCREMENT,");           
		  Map<String,String> map = ObjectUtil.getProperty(obj);   
		  Set<String> set = map.keySet();   
		  for(String key : set){   
			  sb.append("`" + key + "` varchar(255) DEFAULT '',");   
		  }          
		  sb.append(" `tableName` varchar(255) DEFAULT '',");   
		  sb.append(" PRIMARY KEY (`id`)");   
		  sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");   
		  try {   
			  jt.update(sb.toString());   
			  return 1;   
		  } catch (Exception e) {   
			  e.printStackTrace();   
		  }   
		  return 0;   
	  } */  

	public static void setConnection(Connection connection) {
		BaseDaoImpl.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	} 

}
