package com.bjsxt.oa.manager.impl;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.manager.ModuleManager;
import com.bjsxt.oa.manager.SystemException;
import com.bjsxt.oa.model.Module;

public class ModuleManagerImpl extends AbstractManager implements ModuleManager {

	public void addModule(Module module, int parentId) {
		if(parentId != 0){
			module.setParent(findModule(parentId));
		}
		getHibernateTemplate().save(module);
	}

	public void delModule(int moduleId) {
		Module module = findModule(moduleId);
		if(module.getChildren().size() > 0){
			throw new SystemException("模块【"+module.getName()+"】存在有子模块，不允许删除！");
		}
		
		getHibernateTemplate().delete(module);
	}

	public Module findModule(int moduleId) {
		return (Module)getHibernateTemplate().load(Module.class,moduleId);
	}

	public PagerModel searchModules(int parentId) {

		String hql = "select m from Module m";
		
		if(parentId == 0){
			//返回顶级模块列表
			hql = hql + " where m.parent is null";
			
		}else{
			hql = hql + " where m.parent.id = "+parentId;
		}
		
		return searchPaginated(hql);
	}

	public void updateModule(Module module, int parentId) {
		if(parentId != 0){
			module.setParent(findModule(parentId));
		}
		getHibernateTemplate().update(module);
	}

}
