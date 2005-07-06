/*
 *   TranslatorNavigation.java
 * 	 @Author Shamanth Murthy shamanth.murthy@mphasis.com
 *   Created:
 *   CVS-ID: $Id: TranslatorNavigation.java,v 1.1 2005-07-06 10:34:07 rahul Exp $
 *
 *   This file is part of DiGi project (www.digijava.org).
 *   DiGi is a multi-site portal system written in Java/J2EE.
 *
 *   Confidential and Proprietary, Subject to the Non-Disclosure
 *   Agreement, Version 1.0, between the Development Gateway
 *   Foundation, Inc and the Recipient -- Copyright 2001-2004 Development
 *   Gateway Foundation, Inc.
 *
 *   Unauthorized Disclosure Prohibited.
 *
 *************************************************************************/

/**
* File Name : TranslatorLocaleUpdate.java
*
* Purpose   : This action class extends Strut's action class
*			  This action class gets the Parent tile Id i.e SiteId attribute
*
*
*
* @version  :    1.0
*
* @author   : Shamanth Murthy,  Oct, 2003
*
*
*
* REVISION HISTORY:
* <PRE>
* S. No.      Name        Initials        Date        Bug          Fix No. Description
*
* </PRE>
*/

package org.digijava.kernel.translator.action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.digijava.kernel.translator.form.TranslatorNavForm;
import org.digijava.kernel.user.User;
import org.digijava.kernel.util.DgUtil;
import org.digijava.kernel.util.LabelValueBean;
import org.digijava.kernel.util.RequestUtils;

/* Controller Class that's called by Struts or Tiles ActionServlet... See the definitions in
 * struts-config.xml under /Web-INF/
 */

public final class TranslatorNavigation extends Action {

	/* This method overrides the Action classes execute method. This is the function called by the
	 * controller servlet
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		TranslatorNavForm nav = (TranslatorNavForm) form;

		java.util.Set languages = RequestUtils.getSiteDomain(request).getSite().getTranslationLanguages();
		java.util.Iterator itLang = languages.iterator();
		java.util.ArrayList rtList = new java.util.ArrayList();

		while(itLang.hasNext()){

				org.digijava.kernel.entity.Locale locale = (org.digijava.kernel.entity.Locale)itLang.next();

				LabelValueBean lvb = new LabelValueBean(locale.getCode(),locale.getName());
				rtList.add(lvb);

			}

		nav.setLocales(rtList);
		nav.setLocalesSelected(request.getParameter("lang"));
		return mapping.findForward("success");

	}
}
