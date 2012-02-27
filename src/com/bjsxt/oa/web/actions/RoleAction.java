package com.bjsxt.oa.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.bjsxt.oa.manager.RoleManager;
import com.bjsxt.oa.model.Role;
import com.bjsxt.oa.web.forms.RoleActionForm;

public class RoleAction extends DispatchAction {
	private RoleManager roleManager;
	
	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setAttribute("pm",
				roleManager.searchRoles()
				);
		
		return mapping.findForward("index");
	}
	
	//打开添加界面
	public ActionForward addInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("add_input");
	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//从页面表单接收数据
		RoleActionForm raf = (RoleActionForm)form;
		
		Role role = new Role();
		
		BeanUtils.copyProperties(role, raf);
		
		roleManager.addRole(role);
		
		return mapping.findForward("pub_add_success");
	}
	
	public ActionForward del(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		RoleActionForm raf = (RoleActionForm)form;
		
		roleManager.delRole(raf.getId());
		
		return mapping.findForward("pub_del_success");
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

}
