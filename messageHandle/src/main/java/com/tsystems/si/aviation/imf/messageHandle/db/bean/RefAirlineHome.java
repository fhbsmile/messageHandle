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
 * Home object for domain model class RefAirline.
 * @see com.tsystems.si.aviation.imf.messageHandle.db.bean.RefAirline
 * @author Hibernate Tools
 */
public class RefAirlineHome {

	private static final Log log = LogFactory.getLog(RefAirlineHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(RefAirline transientInstance) {
		log.debug("persisting RefAirline instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(RefAirline instance) {
		log.debug("attaching dirty RefAirline instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RefAirline instance) {
		log.debug("attaching clean RefAirline instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(RefAirline persistentInstance) {
		log.debug("deleting RefAirline instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RefAirline merge(RefAirline detachedInstance) {
		log.debug("merging RefAirline instance");
		try {
			RefAirline result = (RefAirline) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public RefAirline findById(java.lang.Integer id) {
		log.debug("getting RefAirline instance with id: " + id);
		try {
			RefAirline instance = (RefAirline) sessionFactory.getCurrentSession()
					.get("com.tsystems.si.aviation.imf.messageHandle.db.bean.RefAirline", id);
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

	public List findByExample(RefAirline instance) {
		log.debug("finding RefAirline instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.tsystems.si.aviation.imf.messageHandle.db.bean.RefAirline")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
