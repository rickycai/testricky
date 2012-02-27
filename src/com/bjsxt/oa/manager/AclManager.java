package com.bjsxt.oa.manager;

import java.util.List;

public interface AclManager {
	
	/**
	 * ��Ȩ
	 * @param principalType ��������
	 * @param principalId �����ʶ
	 * @param moduleId ģ���ʶ
	 * @param permission ������ʶ��C/R/U/D��
	 * @param yes true��ʾ������false��ʾ������
	 */
	public void addOrUpdatePermission(
			String principalType,
			int principalId,
			int moduleId,
			int permission,
			boolean yes);
	
	/**
	 * ɾ����Ȩ
	 * @param principalType ��������
	 * @param principalId �����ʶ
	 * @param moduleId ģ���ʶ
	 */
	public void delPermission(String principalType,int principalId,int moduleId);
	
	/**
	 * �����û���Ȩ�ļ̳�����
	 * @param userId �û�ID
	 * @param moduleId ģ��ID
	 * @param yes true��ʾ�̳С���Ч����false��ʾ���̳С���Ч��
	 */
	public void addOrUpdateUserExtends(int userId,int moduleId,boolean yes);
	
	/**
	 * ��ʱ�ж��û���ĳ��ģ���ĳ�������Ƿ�����
	 * @param userId �û�ID
	 * @param moduleId ģ��ID
	 * @param permission �������ͣ�C/R/U/D��
	 * @return �������������true�����򷵻�false
	 */
	public boolean hasPermission(int userId,int moduleId,int permission);
	
	/**
	 * ��ѯ�û�ӵ�ж�ȡȨ�޵�ģ���б����ڵ�¼��̨�����������ʱ����Ҫ��������б������ɵ����˵���
	 * @param userId �û�ID
	 * @return �б���Ԫ����Module����
	 */
	public List searchModules(int userId);
}