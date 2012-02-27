package com.bjsxt.oa.manager.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.SystemContext;
import com.bjsxt.oa.manager.SystemException;

public class AbstractManager extends HibernateDaoSupport {
	public PagerModel searchPaginated(String hql){
		return searchPaginated(hql, null);
	}
	
	public PagerModel searchPaginated(String hql,Object param){
		return searchPaginated(hql, new Object[]{param});
	}
	
	public PagerModel searchPaginated(String hql,Object[] params){
		return searchPaginated(hql, params,SystemContext.getOffset(), SystemContext.getPagesize());
	}
	
	public PagerModel searchPaginated(String hql,int offset,int pagesize){
		return searchPaginated(hql,null, offset, pagesize);
	}
	public PagerModel searchPaginated(String hql,Object param,int offset,int pagesize){
		return searchPaginated(hql, new Object[]{param}, offset, pagesize);
	}
	public PagerModel searchPaginated(String hql,Object[] params,int offset,int pagesize){
		//��ȡ��¼����
		String countHql = getCountQuery(hql);
		Query query = getSession().createQuery(countHql);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				query.setParameter(i, params[i]);
			}
		}
		int total = ((Long)query.uniqueResult()).intValue();
		
		//��ȡ�����
		query = getSession().createQuery(hql);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				query.setParameter(i, params[i]);
			}
		}
		query.setFirstResult(offset);
		query.setMaxResults(pagesize);
		List datas = query.list();
		
		//����PagerModel
		PagerModel pm = new PagerModel();
		pm.setDatas(datas);
		pm.setTotal(total);
		return pm;
	}
	
	/**
	 * ����HQL��䣬��ò�ѯ�ܼ�¼����HQL���
	 * �磺
	 * select ... from Organization o where o.parent is null
	 * ����ת�������Եõ���
	 * select count(*) from Organziation o where o.parent is null
	 * @param hql
	 * @return
	 */
	private String getCountQuery(String hql){
		int index = hql.indexOf("from");
		if(index != -1){
			return "select count(*) " + hql.substring(index);
		}
		throw new SystemException("��Ч��HQL��ѯ��䡾"+hql+"��");
	}
}
