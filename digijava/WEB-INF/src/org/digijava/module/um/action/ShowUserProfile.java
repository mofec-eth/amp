/*
*   ShowUserProfile.java
*   @Author Lasha Dolidze lasha@digijava.org
*   Created:
*   CVS-ID: $Id: ShowUserProfile.java,v 1.1 2005-07-06 10:34:07 rahul Exp $
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

package org.digijava.module.um.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.entity.Locale;
import org.digijava.kernel.entity.UserPreferences;
import org.digijava.kernel.translator.util.TrnUtil;
import org.digijava.kernel.user.User;
import org.digijava.kernel.util.DgUtil;
import org.digijava.kernel.util.RequestUtils;
import org.digijava.module.um.form.UserProfileForm;
import org.digijava.module.um.util.DbUtil;
import org.digijava.module.um.util.InterestsCallback;

/**
 * <p>Title: DiGiJava</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ShowUserProfile
    extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
        java.lang.Exception {

        UserProfileForm userForm = (UserProfileForm) form;
        User user = RequestUtils.getUser(request);
        User selectedUser = null;
        boolean owner;
        boolean publicProfile = false;

        if (userForm.getSiteId() == null ){
            userForm.setSiteId(RequestUtils.getSite(request).getId());
        }

        if (userForm.getActiveUserId() != null) {
            selectedUser = DbUtil.getSelectedUser(userForm.getActiveUserId(),
                                                  request);
            owner = false;
//                userForm.setActiveUserId(null);
        }
        else {
            selectedUser = user;
            owner = true;
        }

        userForm.setContact(false);

        if (selectedUser != null) {

            userForm.setOwner(owner);

            UserPreferences userPreferences = selectedUser.getUserPreference();

            if (userPreferences != null) {
                publicProfile = userPreferences.isPublicProfile();
            }

            userForm.setPublicProfile(publicProfile);

            // set user first name
            userForm.setFirstNames(selectedUser.getFirstNames());

            // set user last name
            userForm.setLastName(selectedUser.getLastName());

            // set user creation date
            Date creationDate = selectedUser.getCreationDate();
            if (creationDate != null) {
                userForm.setMembersinceMonth(new SimpleDateFormat("MMMMM").
                                             format(creationDate));
                userForm.setMembersinceDay(new SimpleDateFormat("d").format(
                    creationDate));
                userForm.setMembersinceYear(new SimpleDateFormat("yyyy").format(
                    creationDate));
            }
            // -----------------------

            // set Bio/Organization
            if (publicProfile) {

                if (selectedUser.getPhoto() != null)
                    userForm.setActiveUserImage(selectedUser.getPhoto().getId());
                else
                    userForm.setActiveUserImage(new Long(0));

                userForm.setMemberShip(true);
                userForm.setUrl(selectedUser.getUrl());
                userForm.setMailingAddress(selectedUser.getAddress());

                Set interests = selectedUser.getInterests();

                if (interests != null) {
                    List interestsList = new ArrayList(interests);

                    //populate Locale according to Navigation Language
                    Locale locale = new Locale();
                    locale.setCode(RequestUtils.getNavigationLanguage(request).
                                   getCode());

                    //sort Topics Array
                    List sortedInterests = TrnUtil.sortByTranslation(
                        interestsList, locale,
                        new InterestsCallback());

                    userForm.setSites(sortedInterests);

                }
                else {
                    userForm.setSites(null);
                }

                if (user != null) {

                    if (!selectedUser.getEmail().equals(user.getEmail())) {
                        userForm.setContact(true);
                    }
                }

                if (owner) {

                    // set user organization name
                    if (user != null) {
                        userForm.setOrganizationName(selectedUser.
                            getOrganizationName());

                        String bio = "No bio";
                        if (userPreferences != null) {
                            bio = userPreferences.getBiography();
                        }
                        userForm.setBioText(bio);

                        userForm.setContact(false);
                    }
                }
            }
            // -------
        }
        else {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("error.um.selectedUserEmpty"));
        }

        return mapping.findForward("forward");

    }

}