package com.tsystems.si.aviation.imf.messageHandle.db.bean;
// Generated Jun 27, 2016 10:40:01 AM by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class SysParameter.
 * @see com.tsystems.si.aviation.imf.messageHandle.db.bean.SysParameter
 * @author Hibernate Tools
 */
public class SysParameterHome {

	private static final Log log = LogFactory.getLog(SysParameterHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(SysParameter transientInstance) {
		log.debug("persisting SysParameter instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(SysParameter instance) {
		log.debug("attaching dirty SysParameter instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysParameter instance) {
		log.debug("attaching clean SysParameter instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SysParameter persistentInstance) {
		log.debug("deleting SysParameter instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysParameter merge(SysParameter detachedInstance) {
		log.debug("merging SysParameter instance");
		try {
			SysParameter result = (SysParameter) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SysParameter findById(java.lang.String id) {
		log.debug("getting SysParameter instance with id: " + id);
		try {
			SysParameter instance = (SysParameter) sessionFactory.getCurrentSession()
					.get("com.tsystems.si.aviation.imf.messageHandle.db.bean.SysParameter", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysParameter instance) {
		log.debug("finding SysParameter instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.tsystems.si.aviation.imf.messageHandle.db.bean.SysParameter")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
