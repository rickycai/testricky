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
	
	//��ҳ����ʾ��Ա�б�
	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setAttribute("pm",
				personManager.searchPersons()
				);
		
		return mapping.findForward("index");
	}
	
	//����ӽ���
	public ActionForward addInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("add_input");
	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//��ҳ�����������
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
	
	//���û����н�ɫ���б�ҳ�棬��ҳ������Ҫ��ʾ���û����������Լ��û���ӵ�еĽ�ɫ�б�
	public ActionForward userRoleList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UserActionForm uaf = (UserActionForm)form;
		
		//�û���Ϣ
		User user = userManager.findUser(uaf.getId());
		
		//��Ҫ�����ѷ�����û��Ľ�ɫ�б�
		List usersRolesList = userManager.searchUserRoles(uaf.getId());
		
		request.setAttribute("user", user);
		request.setAttribute("urs", usersRolesList);
		
		return mapping.findForward("user_role_list");
	}
	
	//�򿪸��û������ɫҳ�棺���ӽ�ɫ�б���ѡ��ĳ����ɫ�����������ȼ�
	public ActionForward userRoleInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UserActionForm uaf = (UserActionForm)form;
		
		//���ҽ�ɫ�б������䵽���棬�Ա��û���ѡ��
		PagerModel roles = roleManager.searchRoles();
		
		request.setAttribute("pm", roles);
		
		return mapping.findForward("user_role_input");
	}
	
	//���û������ɫ��ҳ������Ҫ���ݹ����Ĳ����������û���ʶ����ɫ��ʶ�����ȼ�
	public ActionForward addUserRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UserActionForm uaf = (UserActionForm)form;
		
		//�û���ʶ
		int userId = uaf.getId();
		
		//��ɫ��ʶ
		int roleId = uaf.getRoleId();
		
		//���ȼ�
		int orderNo = uaf.getOrderNo();
		
		userManager.addOrUpdateUserRole(userId, roleId, orderNo);
		
		return mapping.findForward("pub_add_success");
	}
	
	//ɾ��������û��Ľ�ɫ��ҳ������Ҫ��������Ĳ����������û���ʶ����ɫ��ʶ
	public ActionForward delUserRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UserActionForm uaf = (UserActionForm)form;
		
		//�û���ʶ
		int userId = uaf.getId();
		
		//��ɫ��ʶ
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
