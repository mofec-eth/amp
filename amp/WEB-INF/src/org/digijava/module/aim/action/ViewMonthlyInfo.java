package org.digijava.module.aim.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.actions.TilesAction;
import org.digijava.kernel.exception.DgException;
import org.digijava.module.aim.form.MonthlyInfoForm;
import org.digijava.module.aim.helper.ApplicationSettings;
import org.digijava.module.aim.helper.CommonWorker;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.helper.Currency;
import org.digijava.module.aim.helper.FilterParams;
import org.digijava.module.aim.helper.FinancialFilters;
import org.digijava.module.aim.helper.MonthlyInfoWorker;
import org.digijava.module.aim.helper.TeamMember;
import org.digijava.module.aim.helper.YearUtil;
import org.digijava.module.aim.helper.YearlyDiscrepancyWorker;
import org.digijava.module.aim.util.CurrencyUtil;
import org.digijava.module.aim.util.DbUtil;

/**
 *
 * @author 
 */
public class ViewMonthlyInfo extends TilesAction {

    private static Logger logger = Logger.getLogger(ViewYearlyInfo.class);

    public ActionForward execute(ComponentContext context,
            ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        TeamMember teamMember = (TeamMember) session.getAttribute("currentMember");
        MonthlyInfoForm monthlyForm = (MonthlyInfoForm) form;
        ActionErrors errors = new ActionErrors();

        FinancialFilters ff = CommonWorker.getFilters(teamMember.getTeamId(), "FP");
        monthlyForm.setCalendarPresent(ff.isCalendarPresent());
        monthlyForm.setCurrencyPresent(ff.isCurrencyPresent());
        monthlyForm.setPerspectivePresent(ff.isPerspectivePresent());
        monthlyForm.setYearRangePresent(ff.isYearRangePresent());
        monthlyForm.setGoButtonPresent(ff.isGoButtonPresent());
        FilterParams fp = (FilterParams) session.getAttribute("filterParams");
        fp.setTransactionType(monthlyForm.getTransactionType());
        ApplicationSettings apps = null;
        if (teamMember != null) {
            apps = teamMember.getAppSettings();
        }

        if (monthlyForm.getCurrency() != null) {
            fp.setCurrencyCode(monthlyForm.getCurrency());
        } else {
            if (fp.getCurrencyCode() == null) {
                Currency curr = CurrencyUtil.getCurrency(apps.getCurrencyId());
                fp.setCurrencyCode(curr.getCurrencyCode());
            }

        }

        if (monthlyForm.getPerspective() != null) {
            fp.setPerspective(monthlyForm.getPerspective());
        } else {
            String perspective = CommonWorker.getPerspective(apps.getPerspective());
            fp.setPerspective(perspective);
        }
        monthlyForm.setPerpsectiveName(DbUtil.getPerspective(fp.getPerspective()).getName());

        if (monthlyForm.getFromYear() == 0 || monthlyForm.getToYear() == 0) {
            int year = new GregorianCalendar().get(Calendar.YEAR);
            fp.setFromYear(year - Constants.FROM_YEAR_RANGE);
            fp.setToYear(year + Constants.TO_YEAR_RANGE);
        } else {
            if(fp.getToYear()==0&&fp.getFromYear()==0){
            fp.setToYear(monthlyForm.getToYear());
            fp.setFromYear(monthlyForm.getFromYear());
            }
        }
        session.setAttribute("filterParams", fp);

        monthlyForm.setPerspective(fp.getPerspective());
        monthlyForm.setPerpsectiveName(apps.getPerspective());
        monthlyForm.setCurrency(fp.getCurrencyCode());
        monthlyForm.setFiscalCalId(fp.getFiscalCalId().longValue());
        monthlyForm.setFromYear(fp.getFromYear());
        monthlyForm.setToYear(fp.getToYear());
        monthlyForm.setYears(YearUtil.getYears());
        monthlyForm.setCurrencies(CurrencyUtil.getAmpCurrency());
        monthlyForm.setPerspectives(DbUtil.getAmpPerspective());
        if (fp.getPerspective().equals(Constants.DISCREPANCY)) {
            Collection discrepancies = YearlyDiscrepancyWorker.getYearlyDiscrepancy(fp);
        //monthlyForm.setDiscrepancies(discrepancies);
        } else {
            List monthlyInfos = new ArrayList();
            try {
                if (mapping.getInput().equals("/aim/viewMonthlyComparisons")) {

                    monthlyInfos = MonthlyInfoWorker.getMonthlyComparisons(fp);

                } else {
                    monthlyInfos = MonthlyInfoWorker.getMonthlyData(fp);
                }
            } catch (DgException ex) {
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.aim.monthlyview.unableLoadFundingDetails"));
                saveErrors(request, errors);

            }
            monthlyForm.setMonthlyInfoList(monthlyInfos);

        }




        return null;

    }
    }
