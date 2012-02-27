package com.bjsxt.oa.manager.impl;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.manager.RoleManager;
import com.bjsxt.oa.model.Role;

public class RoleManagerImpl extends AbstractManager implements RoleManager {

	public void addRole(Role role) {
		getHibernateTemplate().save(role);
	}

	public void delRole(int roleId) {
		getHibernateTemplate().delete(findRole(roleId));
	}

	public Role findRole(int roleId) {
		return (Role)getHibernateTemplate().load(Role.class, roleId);
	}

	public PagerModel searchRoles() {
		return searchPaginated("from Role");
	}

	public void updateRole(Role role) {
		getHibernateTemplate().update(role);
	}

}
