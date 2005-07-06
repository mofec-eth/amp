/*
 *   SavePermissions.java
 *   @Author Mikheil Kapanadze mikheil@digijava.org
 * 	 Created: Sep 20, 2003
 * 	 CVS-ID: $Id: SavePermissions.java,v 1.1 2005-07-06 10:34:10 rahul Exp $
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
package org.digijava.module.admin.action;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.request.SiteDomain;
import org.digijava.module.admin.form.SiteForm;
import org.digijava.kernel.request.Site;
import org.digijava.kernel.util.DgUtil;
import org.digijava.module.admin.util.DbUtil;
import org.digijava.module.admin.form.GroupsForm;
import java.util.ArrayList;
import java.util.Iterator;
import org.digijava.kernel.user.Group;
import org.digijava.module.admin.form.GroupPermissionsForm;
import org.digijava.kernel.user.GroupPermission;
import org.digijava.kernel.entity.ModuleInstance;
import org.digijava.module.admin.exception.*;
import java.util.HashSet;
import java.util.HashMap;
import org.digijava.kernel.security.DBPolicy;
import java.security.Policy;
import org.apache.struts.action.ActionErrors;
import org.digijava.kernel.security.ResourcePermission;
import org.apache.struts.action.ActionError;
import org.apache.log4j.Logger;
import org.digijava.kernel.util.RequestUtils;
import org.digijava.kernel.security.SitePermission;
import org.digijava.kernel.security.ModuleInstancePermission;
import org.digijava.module.admin.helper.AdminUIManager;

public class SavePermissions
    extends Action {

    private static Logger logger = Logger.getLogger(SavePermissions.class);

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 javax.servlet.http.HttpServletRequest request,
                                 javax.servlet.http.HttpServletResponse
                                 response) throws java.lang.Exception {

        Site currentSite = RequestUtils.getSite(request);
        GroupPermissionsForm groupPermsForm = (GroupPermissionsForm) form;

        Group group = null;
        try {
            group = DbUtil.getGroup(groupPermsForm.getGroupId());
        }
        catch (AdminException ex) {
            logger.debug("Unknown group ",ex);
            throw new AdminException("Unknown group",ex);
        }

        HashMap perms = new HashMap();
        Iterator iter = group.getPermissions().iterator();
        while (iter.hasNext()) {
            GroupPermission perm = (GroupPermission) iter.next();
            perms.put(new Long(perm.getGroupPermissionId()), perm);
        }

        ArrayList permissions = new ArrayList();
        iter = groupPermsForm.getPermissions().iterator();
        while (iter.hasNext()) {
            GroupPermissionsForm.PermissionInfo info = (GroupPermissionsForm.
                PermissionInfo) iter.next();

            ResourcePermission resourcePermission = null;
            boolean skip = false;
            // If this is a site permission, but we don't process the current
            // site's group (hacking?)
            if (info.getResourceId().equals("-")) {
                if (group.getSite().getId().equals(currentSite.getId())) {
                    resourcePermission = new SitePermission(currentSite, info.getAction());
                }
            }
            else {
                // ... or this is a module instance permission, but it does not
                // belong to current site too
                Long targetId = new Long(info.getResourceId());
                ModuleInstance modInst = DbUtil.getModuleInstance(targetId);
                if (modInst.getSite().getId().equals(currentSite.getId())) {
                    resourcePermission = new ModuleInstancePermission(modInst, info.getAction());
                }
            }
            if (resourcePermission != null) {
                permissions.add(resourcePermission);
            }
        }
/*
        iter = perms.values().iterator();
        while (iter.hasNext()) {
            GroupPermission item = (GroupPermission) iter.next();
            // For module instance permissions remove ones, which belong
            // to the current site, but were not passed to save
            if (item.getPermissionType() ==
                GroupPermission.MODULE_INSTANCE_PERMISSION) {
                Long targetId = new Long(item.getTargetName());
                ModuleInstance modInst = DbUtil.getModuleInstance(targetId);
                if (modInst.getSite().getId().equals(currentSite.getId())) {
                    item.setGroup(null);
                }
            }
            else {
                // Remove site permission too
                if (item.getPermissionType() == GroupPermission.SITE_PERMISSION) {
                    item.setGroup(null);
                }
            }
        }
 */
        ActionErrors errors = AdminUIManager.checkGroupPermissions(currentSite, group, permissions);
        if (errors != null) {
            saveErrors(request, errors);
        } else {
            AdminUIManager.setGroupPermissions(group, permissions);
        }

/*
        ActionErrors errors = new ActionErrors();
        // Check permissions for default groups only if group belongs to
        // current site
        if (group.isDefaultGroup() &&
            group.getSite().getId().equals(currentSite.getId())) {
            boolean permissionFound = false;
            String permissionName = null;
            if (group.isAdminGroup()) {
                permissionName = ResourcePermission.ADMIN;
            }
            else
            if (group.isMemberGroup()) {
                permissionName = ResourcePermission.READ;
            }
            else
            if (group.isTranslatorGroup()) {
                permissionName = ResourcePermission.TRANSLATE;
            }
            else
            if (group.isEditorGroup()) {
                permissionName = ResourcePermission.WRITE;
            }

            iter = group.getPermissions().iterator();
            while (iter.hasNext()) {
                GroupPermission perm = (GroupPermission) iter.next();
                if ( (perm.getGroup() != null) &&
                    (perm.getPermissionType() ==
                     GroupPermission.SITE_PERMISSION)) {
                    if (perm.getActions().equals(permissionName)) {
                        permissionFound = true;
                        break;
                    }
                }
            }
            if (!permissionFound) {
                Object[] params = {
                    permissionName};
                errors.add(null,
                           new ActionError(
                    "error.admin.defaultGroupMustHavePermission",
                    params));
            }
        }
        if (errors.isEmpty()) {

            DbUtil.editGroup(group);

            DBPolicy policy = (DBPolicy) Policy.getPolicy();
            policy.refresh();
        }
        else {
            saveErrors(request, errors);
        }
*/

        return mapping.findForward("forward");
    }

}
