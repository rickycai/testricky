package com.bjsxt.oa.web.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.manager.OrgManager;
import com.bjsxt.oa.model.Organization;
import com.bjsxt.oa.web.forms.OrgActionForm;
/**
 * 
 * @author Administrator
 *
 */
public class OrgAction extends DispatchAction {

	private OrgManager orgManager;
	
	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrgActionForm oaf = (OrgActionForm)form;
		int parentId = oaf.getParentId();

		PagerModel pm = orgManager.searchOrgs(parentId);
		
		request.setAttribute("pm", pm);
		
		int ppid = 0;
		if(parentId != 0){
			Organization parent = orgManager.findOrg(parentId);
			if(parent.getParent() != null){
				ppid = parent.getParent().getId();
			}
		}
		request.setAttribute("ppid", ppid);
		
		if(oaf.isSelect()){
			return mapping.findForward("select");
		}
		
		
		return mapping.findForward("index");
	}
	
	public ActionForward addInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("add_input");
	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrgActionForm oaf = (OrgActionForm)form;
		Organization org = new Organization();
		BeanUtils.copyProperties(org, oaf);
		
		int parentId = oaf.getParentId();
		orgManager.addOrg(org, parentId);
		
		return mapping.findForward("pub_add_success");
	}
	public ActionForward updateInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("update_input");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("pub_update_success");
	}
	
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrgActionForm oaf = (OrgActionForm)form;
		int id = oaf.getId();
		
		orgManager.delOrg(id);

		
		return mapping.findForward("pub_del_success");
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}
	
}
