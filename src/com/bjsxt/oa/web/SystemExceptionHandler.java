package com.bjsxt.oa.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import com.bjsxt.oa.manager.SystemException;

public class SystemExceptionHandler extends ExceptionHandler {

	private static final Log logger = LogFactory.getLog(SystemExceptionHandler.class);
	
	@Override
	public ActionForward execute(Exception ex, ExceptionConfig ae,
			ActionMapping mapping, ActionForm formInstance,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		
        ActionForward forward = null;
        ActionMessage error = null;

        // Build the forward from the exception mapping if it exists
        // or from the form input
        if (ae.getPath() != null) {
            forward = new ActionForward(ae.getPath());
        } else {
            forward = mapping.getInputForward();
        }
		
        logger.error("程序出现异常", ex);
        
		if(ex instanceof SystemException){
			SystemException se = (SystemException)ex;
			
			//取出key值
			String key = se.getKey();
			if(key == null){
				error = new ActionMessage(ae.getKey(), ex.getMessage());
			}else{
				if(se.getValues() != null){
					error = new ActionMessage(key,se.getValues());
				}else{
					error = new ActionMessage(key);
				}
			}
			
			this.storeException(request, key, error, forward, ae.getScope());
			
			return forward;
		}
		
		
		return super.execute(ex, ae, mapping, formInstance, request, response);
	}
	
}
