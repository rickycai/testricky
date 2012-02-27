package com.bjsxt.oa.manager.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.manager.OrgManager;
import com.bjsxt.oa.manager.SystemException;
import com.bjsxt.oa.model.Organization;

public class OrgManagerImpl extends AbstractManager implements OrgManager {

	public void addOrg(Organization org, int parentId) {
		if(parentId != 0){
			org.setParent(
				findOrg(parentId)
			);
		}
		getHibernateTemplate().save(org);
		
		org.setSn(
			org.getParent() == null ?
				(""+org.getId()) :
					(org.getParent().getSn()+"_"+org.getId())
		);
		
		getHibernateTemplate().update(org);
	}

	public void delOrg(int orgId) {
		Organization org = (Organization)findOrg(orgId);
		if(org.getChildren().size() > 0){
			//throw new RuntimeException("������"+org.getName()+"����������ӻ�����Ϣ��������ɾ����");
			throw new SystemException("org.suborg.not.null",org.getName(),"������"+org.getName()+"����������ӻ�����Ϣ��������ɾ����");
		}
		String hql = "select count(*) from Person p where p.org.id = ? ";
		Long size = (Long)getSession().createQuery(hql).setParameter(0, orgId).uniqueResult();
		if(size > 0){
			throw new SystemException("������"+org.getName()+"�����������Ա��Ϣ��������ɾ����");
			//throw new RuntimeException("������"+org.getName()+"�����������Ա��Ϣ��������ɾ����");
		}
		getHibernateTemplate().delete(org);
	}

	public Organization findOrg(int orgId) {
		return (Organization)getHibernateTemplate().load(Organization.class, orgId);
	}

	public PagerModel searchOrgs(int parentId) {
		
		String selectHql = "select o from Organization o where o.parent is null";
		if(parentId != 0){
			selectHql = "select o from Organization o where o.parent.id = "+parentId;
		}
		
		return searchPaginated(selectHql);
	}

	public void updateOrg(Organization org, int parentId) {
		if(parentId != 0){
			org.setParent(
				findOrg(parentId)
			);
		}
		getHibernateTemplate().update(org);
	}

}
