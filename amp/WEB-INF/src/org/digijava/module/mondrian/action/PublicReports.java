package org.digijava.module.mondrian.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dgfoundation.amp.ar.ARUtil;
import org.dgfoundation.amp.ar.AmpARFilter;
import org.dgfoundation.amp.ar.ArConstants;

	public class PublicReports extends Action {
		public ActionForward execute(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response) throws Exception {

			HttpSession session = request.getSession();
			
			session.setAttribute("publicReports", ARUtil.getAllPublicReports(null,null,null));
//			this code makes no sense as report publicness is saved on a report-by-report basis			
//			AmpARFilter arf = (AmpARFilter) session.getAttribute(ArConstants.REPORTS_Z_FILTER);
//			if(arf==null) arf=new AmpARFilter();		
//			arf.setPublicView(true);
//			session.setAttribute(ArConstants.REPORTS_Z_FILTER, arf);
			return mapping.findForward("forward");
			
		}
}
