package com.bjsxt.oa.manager.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bjsxt.oa.manager.AclManager;
import com.bjsxt.oa.manager.Permission;
import com.bjsxt.oa.model.ACL;

public class AclManagerImpl extends AbstractManager implements AclManager {

	public void addOrUpdatePermission(String principalType, int principalId,
			int moduleId, int permission, boolean yes) {
		
		//查找ACL对象
		ACL acl = findACL(principalType, principalId, moduleId);
		
		if(acl != null){
			//根据permission和yes，更新aclState的值
			acl.setPermission(permission, yes);
			getHibernateTemplate().update(acl);
			return;
		}
		
		acl = new ACL();
		acl.setPrincipalType(principalType);
		acl.setPrincipalId(principalId);
		acl.setModuleId(moduleId);
		//根据permission和yes，更新aclState的值
		acl.setPermission(permission, yes);
		getHibernateTemplate().save(acl);		
	}

	public void addOrUpdateUserExtends(int userId, int moduleId, boolean yes) {
		ACL acl = findACL(ACL.TYPE_USER, userId, moduleId);
		if(acl != null){
			acl.setExtends(yes);
			getHibernateTemplate().update(acl);
			return;
		}
		acl = new ACL();
		acl.setPrincipalType(ACL.TYPE_USER);
		acl.setPrincipalId(userId);
		acl.setModuleId(moduleId);
		acl.setExtends(yes);
		getHibernateTemplate().save(acl);
	}

	public void delPermission(String principalType, int principalId,
			int moduleId) {
		ACL acl = findACL(principalType, principalId, moduleId);
		if(acl != null){
			getHibernateTemplate().delete(acl);
		}
	}

	public boolean hasPermission(int userId, int moduleId, int permission) {
		
		//根据用户标识和模块标识查找授权记录
		ACL acl = findACL(ACL.TYPE_USER,userId,moduleId);
		
		//有授权记录
		if(acl != null){
			int yesOrNo = acl.getPermission(permission);
			
			//如果是确定的授权
			if(yesOrNo != ACL.ACL_NEUTRAL){
				return yesOrNo == ACL.ACL_YES ? true : false;
			}
		}
		
		//继续查找用户的角色的授权记录
		
		String hql = "select r.id from UsersRoles ur join ur.role r join ur.user u " +
				"where u.id = ? order by ur.orderNo";
		List roleIds = getHibernateTemplate().find(hql, userId);
		for (Iterator iterator = roleIds.iterator(); iterator.hasNext();) {
			Integer rid = (Integer) iterator.next();
			acl = findACL(ACL.TYPE_ROLE, rid, moduleId);
			if(acl != null){
				return acl.getPermission(permission) == ACL.ACL_YES ? true : false;
			}
		}
		
		return false;
	}

	public List searchModules(int userId) {
		
		//查询用户拥有的角色，并按优先级从低到高排序
		String hql = "select r.id from UsersRoles ur join ur.role r join ur.user u " +
		"where u.id = ? order by ur.orderNo desc";
		List roleIds = getHibernateTemplate().find(hql, userId);
		
		Map temp = new HashMap();
		
		//依次查找角色的授权列表
		for (Iterator iterator = roleIds.iterator(); iterator.hasNext();) {
			Integer rid = (Integer) iterator.next();
			List acls = findRoleAcls(rid);
			for (Iterator iterator2 = acls.iterator(); iterator2.hasNext();) {
				ACL acl = (ACL) iterator2.next();
				temp.put(acl.getModuleId(), acl);
			}
		}
		
		//针对用户查找有效的用户授权列表
		List acls = findUserAcls(userId);
		for (Iterator iterator = acls.iterator(); iterator.hasNext();) {
			ACL acl = (ACL) iterator.next();
			temp.put(acl.getModuleId(), acl);
		}
		
		//去除掉那些没有读取权限的授权记录
		Set entries = temp.entrySet();
		for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			ACL acl = (ACL)entry.getValue();
			if(acl.getPermission(Permission.READ) != ACL.ACL_YES){
				iterator.remove();
			}
		}
		
		if(temp.isEmpty()){
			return null;
		}
		
		String searchModules = "select m from Module m where m.id in (:ids) order by m.orderNo";
		return getSession().createQuery(searchModules)
				.setParameterList("ids", temp.keySet())
				.list();
	}
	
	private ACL findACL(String principalType,int principalId,int moduleId){
		String hql = "select a from ACL where a.principalType = ? and a.principalId = ? and a.moduleId = ?";
		ACL acl = (ACL)getSession().createQuery(hql)
			.setParameter(0, principalType)
			.setParameter(1, principalId)
			.setParameter(2, moduleId)
			.uniqueResult();
		return acl;
	}
	
	private List findRoleAcls(int roleId){
		return getHibernateTemplate().find(
				"select acl from ACL acl where acl.principalType = ? " +
				"and acl.principalId=?",
				new Object[]{ACL.TYPE_ROLE,roleId}
			);
	}
	
	private List findUserAcls(int userId){
		return getHibernateTemplate().find(
				"select acl from ACL acl where acl.principalType = ? " +
				"and acl.principalId=? and acl.aclTriState = ?",
				new Object[]{ACL.TYPE_USER,userId,ACL.ACL_TRI_STATE_UNEXTENDS}
			);
	}

}
