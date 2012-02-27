package com.bjsxt.oa.manager;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.model.Person;

public interface PersonManager {
	
	/**
	 * 添加人员信息
	 * @param person 人员信息
	 * @param orgId 所属机构的标识
	 */
	public void addPerson(Person person,int orgId);
	
	/**
	 * 更新人员的信息
	 * @param person 人员信息
	 * @param orgId 所属机构的标识
	 */
	public void updatePerson(Person person,int orgId);
	
	/**
	 * 根据人员标识删除人员信息
	 * @param personId 人员标识
	 */
	public void delPerson(int personId);
	
	/**
	 * 查找特定人员的信息
	 * @param personId 人员标识
	 * @return
	 */
	public Person findPerson(int personId);
	
	/**
	 * 搜索所有人员的列表（分页查询）
	 * @return
	 */
	public PagerModel searchPersons();
	
	/**
	 * 搜索某个机构下的人员列表
	 * @param orgId
	 * @return
	 */
	public PagerModel searchPersons(int orgId);	
}
