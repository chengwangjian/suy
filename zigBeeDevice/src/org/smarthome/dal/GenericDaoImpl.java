package org.smarthome.dal;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jbx.util.SQLOrderMode;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

  
/** 
 * 基于Hibernate的Crud DAO基础实现，所有使用Hibernate并支持Crud操作的DAO都继承该类。<BR> 
 * 可用的异常类如下： 
 * 
 * @datetime 2010-7-6 下午11:00:00 
 * @author jiangzx@yahoo.com 
 */  
@Repository("slaveDao")
public class GenericDaoImpl<E, PK extends Serializable> implements GenericDao<E, PK> {  
//        @Autowired(required=false)  
//        protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;  
//        @Autowired(required=false)  
//        protected JdbcTemplate jdbcTemplate;       
        protected Class<E> clazz;  
         
        @SuppressWarnings("unchecked")  
        public GenericDaoImpl() {  
                Type type = getClass().getGenericSuperclass();  
                if (type instanceof ParameterizedType) {  
                        clazz = (Class<E>) ((ParameterizedType) type).getActualTypeArguments()[0];  
                }  
        }  
  
        /** 
         * 获得DAO类对应的实体类型 
         */  
        protected Class<E> getClazz() {  
                return clazz;  
        }  
  
        @Override
		public E find(PK id) {  
                return (E) getHibernateTemplate().get(this.getClazz(), id);  
        }  
  
        @Override
		public void delete(PK id) {  
                E entity = this.find(id);  
                if (entity == null) {  
                        throw new DataRetrievalFailureException("No entity found for deletion: " + id);  
                }  
                getHibernateTemplate().delete(entity);  
        }  
  
        @Override
		public E findAndLock(PK id, LockMode lockMode) {  
                E entity = (E) this.getHibernateTemplate().get(this.getClazz(), id, lockMode);  
                return entity;  
        }  
  
        @Override
		public void create(E entity) {  
                getHibernateTemplate().save(entity);  
        }  
  
        @Override
		public void create(Collection<E> entities) {  
                for(E entity : entities) {  
                        create(entity);  
                }  
        }  
  
        @Override
		public void createOrUpdate(E entity) {  
                getHibernateTemplate().saveOrUpdate(entity);  
        }  
  
        @Override
		public void createOrUpdate(Collection<E> entities) {  
                for(E entity : entities) {  
                        createOrUpdate(entity);  
                }  
        }  
        @Override
		public void delete(E entity) {  
                getHibernateTemplate().delete(entity);  
        }  
  
        @Override
		@Transactional(readOnly=false)  
        public void delete(Collection<E> entities) {  
                for(E entity : entities) {  
                        delete(entity);  
                }  
        }  
         
        @Override
		public void bulkDelete(Class<E> clazz, String[] propertyNames, Object[] values) {  
                StrBuilder buf = new StrBuilder();  
                String entityName = clazz.getSimpleName();  
                buf.append("delete " + entityName);// 实体名称  
                 
                int len = propertyNames.length;  
                for(int i=0; i<len; i++) {  
                        if(i==0)  
                                buf.append(" WHERE ").append(propertyNames[i]).append(" = :").append(propertyNames[i]);  
                        else  
                                buf.append(" and ").append(propertyNames[i]).append(" = :").append(propertyNames[i]);  
                }  
                 
                 Query query = this.getHibernateTemplate().createQuery(buf.toString());  
                 for(int i=0; i<len; i++) {  
                         query.setParameter(propertyNames[i], values[i]);  
                 }  
         query.executeUpdate();  
        }  
  
        @Override
		public void merge(E entity) {  
                getHibernateTemplate().merge(entity);  
        }  
  
        @Override
		public void merge(Collection<E> entities) {  
                for(E entity : entities) {  
                        merge(entity);  
                }  
        }  
  
        @Override
		public void update(E entity) {  
                getHibernateTemplate().update(entity);  
        }  
  
        @Override
		public void update(Collection<E> entities) {  
                for(E entity : entities) {  
                        update(entity);  
                }  
        }  
  
        @Override
		public void refresh(Object entity) {  
                getHibernateTemplate().refresh(entity);  
        }  
  
        @Override
		public List<E> findAll() {  
                return null;  
        }  
         
        @Override
		public List<E> findByOrder(String orderCol, SQLOrderMode orderMode) {  
                Assert.hasText(orderCol, "orderCol not text");  
                Assert.notNull(orderMode, "orderMode not null");  
                 
                return null;  
        }  
         
        @Override
		public List<E> findByPropertyAndOrder(String propertyName, Object value, String orderCol, SQLOrderMode orderMode) {  
                Assert.hasText(propertyName);  
                Assert.notNull(value);  
                Assert.hasText(orderCol, "orderCol not text");  
                Assert.notNull(orderMode, "orderMode not null");  
  
                return null;  
        }  
         
        @Override
		public List<E> findByPropertysAndOrder(String[] propertyNames, Object[] values, String orderCol, SQLOrderMode orderMode) {  
                Assert.state(propertyNames.length == values.length);  
                Assert.hasText(orderCol, "orderCol not text");  
                Assert.notNull(orderMode, "orderMode not null");  
  
                return null;  
        }  
  
        @Override
		public List<E> findByProperty(String propertyName, Object value) {  
                Assert.hasText(propertyName);  
                Assert.notNull(value);  
  
                return null;  
        }  
  
        @Override
		public List<E> findByPropertys(String[] propertyNames, Object[] values) {  
                Assert.state(propertyNames.length == values.length);  
  
                return null;  
        }  

        @Override
		public List<E> find(String queryString, Object... values) {  
                return getHibernateTemplate().createQuery(queryString).setProperties(values).list();  
        }  
  
        @Override
		public List<E> find(String queryString) {  
                return getHibernateTemplate().createQuery(queryString).list();  
        }  
  
        @Override
		public List<E> findByNamedParam(String queryString, String paramNames, Object[] values) {  
                return getHibernateTemplate().createQuery(queryString).setParameterList(paramNames, values).list();  
        }  
  
        @Override
		public List<E> findByNamedQuery(String queryName, String paramNames,Object[] values) {  
                return getHibernateTemplate().createSQLQuery(queryName).setParameterList(paramNames, values).list();  
        }  
  
        @Override
		public List<E> findByNamedQuery(String queryName) {  
                return getHibernateTemplate().createSQLQuery(queryName).list();  
        }  
  
        @Override
		public List<E> findByNamedQueryAnd(String queryName, Object[] values) {  
                return getHibernateTemplate().createSQLQuery(queryName).setProperties(values).list();  
        }  
  
        @Override
		public Long queryForLong(String queryString, Object... values) {  
                return DataAccessUtils.longResult(this.find(queryString, values));  
        }  
  
        @Override
		public Long queryForLong(String queryString) {  
                return queryForLong(queryString, new Object[] {});  
        }  
  
        @Override
		public <T> T queryForObject(Class<T> requiredType, String queryString) {  
                return queryForObject(requiredType, queryString, new Object[] {});  
        }  
  
        @Override
		public <T> T queryForObject(Class<T> requiredType, String queryString, Object... values) {  
                return DataAccessUtils.objectResult(this.find(queryString, values), requiredType);  
        }  
  
        @Override
		public boolean isUnique(E entity, String uniquePropertyNames) {  
                Assert.hasText(uniquePropertyNames);  
                Criteria criteria = getHibernateTemplate().createCriteria(this.getClazz()).setProjection(Projections.rowCount());  
                String[] nameList = uniquePropertyNames.split(",");  
                try {  
                        // 循环加入唯一列  
                        for (int i = 0; i < nameList.length; i++) {  
                                criteria.add(Restrictions.eq(nameList[i], PropertyUtils.getProperty(entity, nameList[i])));  
                        }  
                } catch (Exception e) {  
                        ReflectionUtils.handleReflectionException(e);  
                }  
                return ((Number) criteria.uniqueResult()).intValue() == 1;  
        }  
  
        @Override
		public void execute(String spName, Map<String, Object> parameters) {  
  
        }  
  
        @Override
		public void execute(String spName) {  
  
        }  
 
        @Resource
        private SessionFactory sessionFactory2; 
        private Session session;
       
        @Override
		public Session getHibernateTemplate(){
        	return sessionFactory2.getCurrentSession();
        }

        @Override
		public void closeSession(Session session){
            if(null != this.session){
                this.session.close();
            }
        }

          
}  