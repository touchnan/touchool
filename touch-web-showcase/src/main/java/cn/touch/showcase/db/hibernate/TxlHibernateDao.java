/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.touch.showcase.db.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.touch.showcase.db.ITxlDao;
import cn.touch.showcase.entity.User;
import cn.touch.showcase.entity.UserProperty;

/**
 * 
 * @author touchnan
 */
@Repository
public class TxlHibernateDao implements ITxlDao {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        Session s = getHibernateSession();
        Criteria c = s.createCriteria(User.class);
        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.setCacheable(true);
//        Query c = s.createQuery("FROM "+User.class.getName());
//      c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//      c.setCacheable(true);
        return c.list();
    }

	/**
	 * @return
	 */
	private Session getHibernateSession() {
		return this.sessionFactory.openSession();
	}

    @Override
    public User save(User u) {
        Session s = getHibernateSession();
        s.saveOrUpdate(u);
        return u;
    }

    @Override
    public User find(Long id) {
        return (User) this.sessionFactory.getCurrentSession().load(User.class, id);
    }

    @Override
    public User find(String propValue) {
        Session s = getHibernateSession();
        Criteria c = s.createCriteria(User.class).createAlias("props", " p ");
        c.add(Restrictions.like("p.value", "%" + propValue + "%"));
        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.setCacheable(true);
        return (User) c.uniqueResult();
    }

    @Override
    public User findByLoginName(String loginName) {
        Session s = getHibernateSession();
        Criteria c = s.createCriteria(User.class);
        c.add(Restrictions.eq("loginName", loginName));
        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.setCacheable(true);
        
        // c.setProjection(Projections.countDistinct("id"));
        // List v = c.list();
        // ProjectionList projectionList = Projections.projectionList();
        // projectionList.add(Projections.property("id"));
        // projectionList.add(Projections.property("loginName"));
        // projectionList.add(Projections.property("passwd"));
        // c.setProjection(Projections.distinct(projectionList));

        return (User) c.uniqueResult();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.zkytxl.db.ITxlDao#deletes(java.lang.Class, java.util.Set)
     */
    @Override
    public void deletes(Class<?> clazz, Set<?> entities) {
        Session s = getHibernateSession();
        for (Object o : entities) {
            s.delete(o);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.zkytxl.db.ITxlDao#findLabels()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> findLabels() {
        Session s = getHibernateSession();
        Criteria c = s.createCriteria(UserProperty.class);
        //c.setProjection(Projections.distinct(Projections.property("title")));
        //c.addOrder(Order.asc("id"));
        
         ProjectionList projectionList = Projections.projectionList();
         projectionList.add(Projections.groupProperty("title"));
         projectionList.add(Projections.alias(Projections.min("id"), "_id"));
         c.setProjection(projectionList);
         c.addOrder(Order.asc("_id"));
         List<Object[]> list = c.list();
         List<String> r = new ArrayList<String>(list.size());
         for (Object[] ao : list) {
        	 r.add((String)ao[0]);
         }
        //c.setCacheable(true);
        return r;
    }

}
