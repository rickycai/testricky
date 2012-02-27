package com.bjsxt.oa.manager.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.manager.SystemException;
import com.bjsxt.oa.manager.UserManager;
import com.bjsxt.oa.model.Person;
import com.bjsxt.oa.model.Role;
import com.bjsxt.oa.model.User;
import com.bjsxt.oa.model.UsersRoles;

public class UserManagerImpl extends AbstractManager implements UserManager {

	public void addOrUpdateUserRole(int userId, int roleId, int orderNo) {
		//首先根据userId和roleId，判断这两者之间是否已建立关联
		UsersRoles ur = findUsersRoles(userId, roleId);
		if(ur == null){
			ur = new UsersRoles();
			ur.setRole((Role)getHibernateTemplate().load(Role.class, roleId));
			ur.setUser((User)getHibernateTemplate().load(User.class, userId));
			ur.setOrderNo(orderNo);
			getHibernateTemplate().save(ur);
			return;
		}
		
		ur.setOrderNo(orderNo);
		getHibernateTemplate().update(ur);
	}

	public void addUser(User user, int personId) {
		if(personId == 0){
			throw new SystemException("建立用户帐号时，人员信息不允许为空");
		}
		user.setPerson((Person)getHibernateTemplate().load(Person.class, personId));
		user.setCreateTime(new Date());
		getHibernateTemplate().save(user);
	}

	public void delUser(int userId) {
		getHibernateTemplate().delete(findUser(userId));
	}

	public void delUserRole(int userId, int roleId) {
		getHibernateTemplate().delete(findUsersRoles(userId, roleId));
	}

	public User findUser(int userId) {
		return (User)getHibernateTemplate().load(User.class, userId);
	}

	public User login(String username, String password) {
		
		User user = (User)getSession()
			.createQuery("select u from com.bjsxt.oa.model.User u where u.username = ?")
			.setParameter(0, username)
			.uniqueResult();
		
		if(user == null){
			throw new SystemException("没有这个用户");
		}
		
		if(!user.getPassword().equals(password)){
			throw new SystemException("密码错误！");
		}
		
		if(user.getExpireTime() != null){
			
			//现在时间
			Calendar now = Calendar.getInstance();
			
			//失效时间
			Calendar expireTime = Calendar.getInstance();
			expireTime.setTime(user.getExpireTime());
			
			//如果现在在失效时间之后
			if(now.after(expireTime)){
				throw new SystemException("用户帐号已失效！");
			}
		}
		
		return user;
	}

	public List searchUserRoles(int userId) {
		return getHibernateTemplate().find("select ur from UsersRoles ur " +
				"where ur.user.id = ? order by ur.orderNo",userId);
	}

	public PagerModel searchUsers() {
		
		return searchPaginated("from com.bjsxt.oa.model.User");
	}

	public void updateUser(User user, int personId) {
		if(personId == 0){
			throw new SystemException("更新用户帐号时，人员信息不允许为空");
		}
		user.setPerson((Person)getHibernateTemplate().load(Person.class, personId));
		getHibernateTemplate().update(user);
	}
	
	//本方法被【AdminRoleAssignmentHandler】所使用
	public List searchUsersOfRole(String roleName) {
		String hql = "select u.username from UsersRoles ur " +
				"join ur.user u join ur.role r where r.name = ? ";
		return getHibernateTemplate().find(hql,roleName);
	}

	private UsersRoles findUsersRoles(int userId,int roleId){
		return (UsersRoles)getSession().createQuery(
					"select ur from UsersRoles ur where " +
					"ur.role.id = ? and ur.user.id = ?"
				)
				.setParameter(0, roleId)
				.setParameter(1, userId)
				.uniqueResult();
	}

}
