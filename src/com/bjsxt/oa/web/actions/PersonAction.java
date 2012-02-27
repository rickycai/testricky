package com.bjsxt.oa.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.bjsxt.oa.manager.PersonManager;
import com.bjsxt.oa.model.Person;
import com.bjsxt.oa.web.forms.PersonActionForm;

public class PersonAction extends DispatchAction {

	private PersonManager personManager;
	
	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		request.setAttribute("pm",
			personManager.searchPersons()
		);
		
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

		PersonActionForm paf = (PersonActionForm)form;
		Person person = new Person();
		BeanUtils.copyProperties(person, paf);
		personManager.addPerson(person, paf.getOrgId());
		return mapping.findForward("pub_add_success");
	}
	
	public ActionForward updateInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		PersonActionForm paf = (PersonActionForm)form;
		
		Person person = personManager.findPerson(paf.getId());
		request.setAttribute("person", person);
		
		return mapping.findForward("update_input");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PersonActionForm paf = (PersonActionForm)form;
		Person person = new Person();
		BeanUtils.copyProperties(person, paf);
		personManager.updatePerson(person, paf.getOrgId());
		return mapping.findForward("pub_update_success");
	}
	
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PersonActionForm paf = (PersonActionForm)form;
		personManager.delPerson(paf.getId());
		return mapping.findForward("pub_del_success");
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}
	
	
	
}
