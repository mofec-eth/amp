/*
 * ViewActivityDashboard.java
 * Created : 18-Apr-2006
 */
package org.digijava.module.aim.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.actions.TilesAction;
import org.digijava.module.aim.util.MEIndicatorsUtil;

public class ViewActivityDashboard extends TilesAction {
	
	private static Logger logger = Logger.getLogger(
			ViewActivityDashboard.class);

	public ActionForward execute(ComponentContext context,ActionMapping mapping,
			ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long actId = null;
		
		DynaActionForm adForm = (DynaActionForm) form;
		
		if (request.getParameter("ampActivityId") != null) {
			actId = new Long(Long.parseLong(
					request.getParameter("ampActivityId")));
			
			int risk = MEIndicatorsUtil.getOverallRisk(actId);
			String riskName = MEIndicatorsUtil.getRiskRatingName(risk);
			String rskColor = MEIndicatorsUtil.getRiskColor(risk);
			adForm.set("overallRisk",riskName);
			adForm.set("riskColor",rskColor);
		}
		request.setAttribute("actId",actId);
		
		
		return null;
	}
}