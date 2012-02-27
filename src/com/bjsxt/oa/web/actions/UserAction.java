package com.bjsxt.oa.web.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.manager.PersonManager;
import com.bjsxt.oa.manager.RoleManager;
import com.bjsxt.oa.manager.UserManager;
import com.bjsxt.oa.model.User;
import com.bjsxt.oa.web.forms.UserActionForm;

public class UserAction extends DispatchAction {
	
	private PersonManager personManager;
	private UserManager userManager;
	private RoleManager roleManager;
	
	//首页，显示人员列表
	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setAttribute("pm",
				personManager.searchPersons()
				);
		
		return mapping.findForward("index");
	}
	
	//打开添加界面
	public ActionForward addInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("add_input");
	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//从页面表单接收数据
		UserActionForm uaf = (UserActionForm)form;
		
		User user = new User();
		
		BeanUtils.copyProperties(user, uaf);
		
		userManager.addUser(user, uaf.getPersonId());
		
		return mapping.findForward("pub_add_success");
	}
	
	public ActionForward updateInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserActionForm uaf = (UserActionForm)form;
		request.setAttribute("user", userManager.findUser(uaf.getId()));
		
		return mapping.findForward("update_input");
	}	
	
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserActionForm uaf = (UserActionForm)form;
		
		User user = new User();
		
		BeanUtils.copyProperties(user, uaf);
		
		userManager.updateUser(user, uaf.getPersonId());
		
		return mapping.findForward("pub_update_success");
	}	
	
	//打开用户已有角色的列表页面，在页面上需要显示：用户的姓名、以及用户已拥有的角色列表
	public ActionForward userRoleList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UserActionForm uaf = (UserActionForm)form;
		
		//用户信息
		User user = userManager.findUser(uaf.getId());
		
		//需要加载已分配给用户的角色列表
		List usersRolesList = userManager.searchUserRoles(uaf.getId());
		
		request.setAttribute("user", user);
		request.setAttribute("urs", usersRolesList);
		
		return mapping.findForward("user_role_list");
	}
	
	//打开给用户分配角色页面：即从角色列表中选择某个角色，并设置优先级
	public ActionForward userRoleInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UserActionForm uaf = (UserActionForm)form;
		
		//查找角色列表，并传输到界面，以便用户的选择
		PagerModel roles = roleManager.searchRoles();
		
		request.setAttribute("pm", roles);
		
		return mapping.findForward("user_role_input");
	}
	
	//给用户分配角色：页面上需要传递过来的参数包括：用户标识、角色标识、优先级
	public ActionForward addUserRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UserActionForm uaf = (UserActionForm)form;
		
		//用户标识
		int userId = uaf.getId();
		
		//角色标识
		int roleId = uaf.getRoleId();
		
		//优先级
		int orderNo = uaf.getOrderNo();
		
		userManager.addOrUpdateUserRole(userId, roleId, orderNo);
		
		return mapping.findForward("pub_add_success");
	}
	
	//删除分配给用户的角色，页面上需要传输过来的参数包括：用户标识、角色标识
	public ActionForward delUserRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UserActionForm uaf = (UserActionForm)form;
		
		//用户标识
		int userId = uaf.getId();
		
		//角色标识
		int roleId = uaf.getRoleId();
		
		userManager.delUserRole(userId, roleId);
		
		return mapping.findForward("pub_add_success");
	}
	
	public ActionForward del(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UserActionForm uaf = (UserActionForm)form;
		userManager.delUser(uaf.getId());
		
		return mapping.findForward("pub_del_success");
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

}
