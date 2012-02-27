package com.bjsxt.oa.manager;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.model.Person;

public interface PersonManager {
	
	/**
	 * �����Ա��Ϣ
	 * @param person ��Ա��Ϣ
	 * @param orgId ���������ı�ʶ
	 */
	public void addPerson(Person person,int orgId);
	
	/**
	 * ������Ա����Ϣ
	 * @param person ��Ա��Ϣ
	 * @param orgId ���������ı�ʶ
	 */
	public void updatePerson(Person person,int orgId);
	
	/**
	 * ������Ա��ʶɾ����Ա��Ϣ
	 * @param personId ��Ա��ʶ
	 */
	public void delPerson(int personId);
	
	/**
	 * �����ض���Ա����Ϣ
	 * @param personId ��Ա��ʶ
	 * @return
	 */
	public Person findPerson(int personId);
	
	/**
	 * ����������Ա���б���ҳ��ѯ��
	 * @return
	 */
	public PagerModel searchPersons();
	
	/**
	 * ����ĳ�������µ���Ա�б�
	 * @param orgId
	 * @return
	 */
	public PagerModel searchPersons(int orgId);	
}
