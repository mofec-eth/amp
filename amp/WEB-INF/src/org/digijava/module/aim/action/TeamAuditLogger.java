package org.digijava.module.aim.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dgfoundation.amp.utils.MultiAction;
import org.digijava.module.aim.dbentity.AmpAuditLogger;
import org.digijava.module.aim.form.TeamAuditForm;
import org.digijava.module.aim.helper.TeamMember;
import org.digijava.module.aim.util.AuditLoggerUtil;

public class TeamAuditLogger extends MultiAction {

    private static Logger logger = Logger.getLogger(TeamAuditLogger.class);

    private ServletContext ampContext = null;

    public ActionForward modePrepare(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        boolean permitted = AuditLoggerUtil.checkPermission(request);
        if (!permitted) {
            return mapping.findForward("index");
        }
        String teamname = null;
        if (session.getAttribute("currentMember") != null) {
            TeamMember tm = (TeamMember) session.getAttribute("currentMember");
            teamname = tm.getTeamName();
        }

        TeamAuditForm vForm = (TeamAuditForm) form;
        vForm.setUserList(AuditLoggerUtil.getEditorNameFromLog());
        vForm.setTeamName(teamname);
        vForm.populateEffectiveFilters();

        Collection<AmpAuditLogger> logs = AuditLoggerUtil.getLogObjects(false,
                vForm.getEffectiveSelectedUser(), teamname, vForm.getEffectiveDateFrom(),
                vForm.getEffectiveDateTo());

        applySortBy(request, vForm, (List<AmpAuditLogger>) logs);

        vForm.setPagesToShow(10);
        int totalrecords=20;
        int page = 0;
        if (request.getParameter("page") == null) {
            page = 1;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int stIndex = ((page - 1) * totalrecords) + 1;
        int edIndex = page * totalrecords;
        Collection tempCol = new ArrayList();
        AmpAuditLogger[] tmplogs = (AmpAuditLogger[])logs.toArray(new AmpAuditLogger[0]);
        for (int i = (stIndex - 1); i < edIndex; i++) {
            if (logs.size() > i) {
                tempCol.add(tmplogs[i]);
            } else {
                break;
            }
        }

        Collection pages = null;
        int numpages;
        numpages = logs.size() / totalrecords;
        numpages += (logs.size()  % totalrecords != 0) ? 1 : 0;

        if ((numpages) >= 1) {
            pages = new ArrayList();
            for (int i = 0; i < (numpages); i++) {
                Integer pageNum = new Integer(i + 1);
                pages.add(pageNum);
            }
        } else {
            pages = new ArrayList<AmpAuditLogger>();
        }

        vForm.setPages(pages);
        vForm.setCurrentPage(new Integer(page));
        vForm.setLogs(tempCol);
        vForm.setPagesSize(pages.size());

        return  modeSelect(mapping, form, request, response);
    }

    private void applySortBy(HttpServletRequest request, TeamAuditForm vForm, List<AmpAuditLogger> logs) {
        if (request.getParameter("sortBy") != null) {
            vForm.setSortBy(request.getParameter("sortBy"));
        }
        if (vForm.getSortBy() == null) {
            vForm.setSortBy("changedatedesc");
        }
        if ("nameasc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerNameComparator());
        } else if ("namedesc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerNameComparator());
            Collections.reverse(logs);
        } else if ("typeasc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerTypeComparator());
        } else if ("typedesc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerTypeComparator());
            Collections.reverse(logs);
        } else if ("teamasc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerTeamComparator());
        } else if ("teamdesc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerTeamComparator());
            Collections.reverse(logs);
        } else if ("authorasc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerAuthorComparator());
        } else if ("authordesc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerAuthorComparator());
            Collections.reverse(logs);
        } else if ("creationdateasc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerCreationDateComparator());
        } else if ("creationdatedesc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerCreationDateComparator());
            Collections.reverse(logs);
        } else if ("editorasc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerEditorNameComparator());
        } else if ("editordesc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerEditorNameComparator());
            Collections.reverse(logs);
        } else if ("actionasc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerActionComparator());
        } else if ("actiondesc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerActionComparator());
            Collections.reverse(logs);
        } else if ("changedateasc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerChangeDateComparator());
        } else if ("changedatedesc".equalsIgnoreCase(vForm.getSortBy())) {
            Collections.sort(logs, new AuditLoggerUtil.HelperAuditloggerChangeDateComparator());
            Collections.reverse(logs);
        }
    }

    public ActionForward modeSelect(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        return mapping.findForward("forward");
    }

}