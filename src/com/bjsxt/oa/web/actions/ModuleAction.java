package com.bjsxt.oa.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.bjsxt.oa.manager.ModuleManager;
import com.bjsxt.oa.model.Module;
import com.bjsxt.oa.web.forms.ModuleActionForm;

public class ModuleAction extends DispatchAction {
	
	private ModuleManager moduleManager;
	
	/**
	 * 打开机构管理主界面
	 */
	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModuleActionForm maf = (ModuleActionForm)form;
		
		request.setAttribute("pm", moduleManager.searchModules(maf.getParentId()));
		
		return mapping.findForward("index");
	}
	
	/**
	 * 打开机构管理录入界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("add_input");
	}

	//添加机构信息
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModuleActionForm maf = (ModuleActionForm)form;
		
		Module module = new Module();
		
		BeanUtils.copyProperties(module, maf);
		
		moduleManager.addModule(module, maf.getParentId());
		
		return mapping.findForward("pub_add_success");
	}
	

	public ActionForward updateInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModuleActionForm maf = (ModuleActionForm)form;
		request.setAttribute("module", moduleManager.findModule(maf.getId()));
		
		return mapping.findForward("update_input");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModuleActionForm maf = (ModuleActionForm)form;
		Module module = new Module();
		BeanUtils.copyProperties(module, maf);
		
		moduleManager.updateModule(module, maf.getParentId());
		
		return mapping.findForward("pub_update_success");
	}		
	
	//删除机构信息
	public ActionForward del(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModuleActionForm maf = (ModuleActionForm)form;
		
		moduleManager.delModule(maf.getId());
		
		return mapping.findForward("pub_del_success");
	}

	public void setModuleManager(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}
}
