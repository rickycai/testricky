package com.bjsxt.oa.manager;

import java.util.List;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.model.Organization;

public interface OrgManager {
	
	/**
	 * ��ӻ�����Ϣ�����parentIdΪ0������ӵ��Ƕ�������
	 * ��Ҫ�Զ����ɻ���Ψһ���
	 * @param org ������Ϣ
	 * @param parentId ��������ʶ
	 */
	public void addOrg(Organization org,int parentId);
	
	/**
	 * ɾ��������Ϣ����������������ӻ����������������Ա��Ϣ��������ɾ��
	 * @param orgId ������ʶ
	 */
	public void delOrg(int orgId);
	
	
	public void updateOrg(Organization org,int parentId);
	
	/**
	 * ��ѯ�ض��Ļ���
	 * @param orgId
	 * @return
	 */
	public Organization findOrg(int orgId);
	
	/**
	 * ���ݸ�������ʶ��ѯ���µ��ӻ����б�
	 * ���parentIdΪ0�����ѯ���������б�
	 * @param parentId ��������ʶ
	 * @return ���ϵ�Ԫ����Organization����
	 */
	public PagerModel searchOrgs(int parentId);
}
