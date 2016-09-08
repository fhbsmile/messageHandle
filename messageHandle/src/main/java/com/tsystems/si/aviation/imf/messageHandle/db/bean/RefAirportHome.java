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
 * Home object for domain model class RefAirport.
 * @see com.tsystems.si.aviation.imf.messageHandle.db.bean.RefAirport
 * @author Hibernate Tools
 */
public class RefAirportHome {

	private static final Log log = LogFactory.getLog(RefAirportHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(RefAirport transientInstance) {
		log.debug("persisting RefAirport instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(RefAirport instance) {
		log.debug("attaching dirty RefAirport instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RefAirport instance) {
		log.debug("attaching clean RefAirport instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(RefAirport persistentInstance) {
		log.debug("deleting RefAirport instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RefAirport merge(RefAirport detachedInstance) {
		log.debug("merging RefAirport instance");
		try {
			RefAirport result = (RefAirport) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public RefAirport findById(java.lang.Integer id) {
		log.debug("getting RefAirport instance with id: " + id);
		try {
			RefAirport instance = (RefAirport) sessionFactory.getCurrentSession()
					.get("com.tsystems.si.aviation.imf.messageHandle.db.bean.RefAirport", id);
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

	public List findByExample(RefAirport instance) {
		log.debug("finding RefAirport instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.tsystems.si.aviation.imf.messageHandle.db.bean.RefAirport")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
