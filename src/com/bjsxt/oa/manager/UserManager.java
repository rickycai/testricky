package com.bjsxt.oa.manager;

import java.util.List;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.model.User;

public interface UserManager {
	public void addUser(User user,int personId);
	public void updateUser(User user,int personId);
	public void delUser(int userId);
	public User findUser(int userId);
	public PagerModel searchUsers();
	
	/**
	 * ���û����ɫ֮�佨������
	 * @param userId
	 * @param roleId
	 * @param orderNo
	 */
	public void addOrUpdateUserRole(int userId,int roleId,int orderNo);
	
	/**
	 * ɾ���û����ɫ֮��Ĺ���
	 * @param userId
	 * @param roleId
	 */
	public void delUserRole(int userId,int roleId);
	
	/**
	 * ����ĳ���û���ӵ�еĽ�ɫ�б�
	 * @param userId
	 * @return Ԫ����UsersRoles����
	 */
	public List searchUserRoles(int userId);
	
	/**
	 * �û���¼
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username,String password);
	
	/**
	 * ����ĳ����ɫ�µ��û����б�
	 * @param roleName ��ɫ��
	 * @return �û���(username)�б�
	 */
	public List searchUsersOfRole(String roleName);
}
