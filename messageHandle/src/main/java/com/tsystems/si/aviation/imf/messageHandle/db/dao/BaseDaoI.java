package com.tsystems.si.aviation.imf.messageHandle.db.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface BaseDaoI<T> {

	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public Serializable save(T o);

	/**
	 * 删除一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void delete(T o);

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void update(T o);



	/**
	 * 通过主键获得对象
	 * 
	 * @param c
	 *            类名.class
	 * @param id
	 *            主键
	 * @return 对象
	 */
	public T get(Class<T> c, Serializable id);


	public List<T> find(String hql, Map<String, Object> params);

	public void saveOrUpdate(T o);
	public  int executeHql(String paramString);
	public  int executeHql(String paramString, Map<String, Object> paramMap);

}
