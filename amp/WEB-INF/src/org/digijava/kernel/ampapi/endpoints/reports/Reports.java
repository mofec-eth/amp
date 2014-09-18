package org.digijava.kernel.ampapi.endpoints.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.dgfoundation.amp.error.AMPException;
import org.dgfoundation.amp.newreports.GeneratedReport;
import org.dgfoundation.amp.newreports.ReportArea;
import org.dgfoundation.amp.newreports.ReportAreaImpl;
import org.dgfoundation.amp.newreports.ReportSpecificationImpl;
import org.dgfoundation.amp.reports.ReportAreaMultiLinked;
import org.dgfoundation.amp.reports.ReportPaginationCacher;
import org.dgfoundation.amp.reports.ReportPaginationUtils;
import org.dgfoundation.amp.reports.mondrian.MondrianReportGenerator;
import org.dgfoundation.amp.reports.mondrian.converters.AmpReportsToReportSpecification;
import org.digijava.kernel.ampapi.endpoints.util.JSONResult;
import org.digijava.kernel.ampapi.endpoints.util.ReportMetadata;
import org.digijava.kernel.ampapi.saiku.SaikuGeneratedReport;
import org.digijava.kernel.ampapi.saiku.SaikuReportArea;
import org.digijava.module.aim.dbentity.AmpApplicationSettings;
import org.digijava.module.aim.dbentity.AmpDesktopTabSelection;
import org.digijava.module.aim.dbentity.AmpReports;
import org.digijava.module.aim.dbentity.AmpTeamMember;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.helper.TeamMember;
import org.digijava.module.aim.util.DbUtil;
import org.digijava.module.aim.util.TeamUtil;
import org.saiku.web.rest.objects.resultset.QueryResult;
import org.saiku.web.rest.util.RestUtil;

/***
 * 
 * @author
 * 
 */

@Path("data")
public class Reports {

	private static final String DEFAULT_CATALOG_NAME = "AMP";
	private static final String DEFAULT_CUBE_NAME = "[Donor Funding]";
	private static final String DEFAULT_QUERY_NAME = "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX";
	private static final String DEFAULT_CONNECTION_NAME = "amp";
	private static final String DEFAULT_SCHEMA_NAME = "AMP";

	protected static final Logger logger = Logger.getLogger(Reports.class); 
	
	@Context
	private HttpServletRequest httpRequest;

	@GET
	@Path("/report/{report_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public final JSONResult getReport(@PathParam("report_id") Long reportId) {
		AmpReports ampReport = DbUtil.getAmpReport(reportId);

		//TODO: for now we do not translate other types of reports than Donor Type reports (hide icons for non-donor-type reports?)
		ReportSpecificationImpl spec = null;
		try {
			spec = AmpReportsToReportSpecification.convert(ampReport);
		} catch (AMPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JSONResult result = new JSONResult();
		ReportMetadata metadata = new ReportMetadata();
		metadata.setReportSpec(spec);
		metadata.setCatalog(DEFAULT_CATALOG_NAME);
		metadata.setCube(DEFAULT_CUBE_NAME);
		metadata.setQueryName(DEFAULT_QUERY_NAME);
		metadata.setName(ampReport.getName());
		metadata.setConnection(DEFAULT_CONNECTION_NAME);
		metadata.setSchema(DEFAULT_SCHEMA_NAME);

		result.setReportMetadata(metadata);
		return result;
	}
	
	@GET
	@Path("/report/{report_id}/result")
	@Produces(MediaType.APPLICATION_JSON)
	public final GeneratedReport getReportResult(@PathParam("report_id") Long reportId) {
		AmpReports ampReport = DbUtil.getAmpReport(reportId);
		MondrianReportGenerator generator = new MondrianReportGenerator(ReportAreaImpl.class, false);
		try{
			//TODO: for now we do not translate other types of reports than Donor Type reports (hide icons for non-donor-type reports?)
			ReportSpecificationImpl spec = AmpReportsToReportSpecification.convert(ampReport);
			GeneratedReport generatedReport = generator.executeReport(spec);
			cacheReportAreas(reportId, generatedReport);
			return generatedReport;
		} catch (AMPException e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * Gets the result for the specified reportId and a given page number
	 * @param reportId - report ID
	 * @param page - page number, starting from 1
	 * @return ReportArea result for the requested page
	 */
	@GET
	@Path("/report/{report_id}/result/pages/{page}")
	@Produces(MediaType.APPLICATION_JSON)
	public final ReportArea getReportResult(@PathParam("report_id") Long reportId,
			@PathParam("page") Integer page) {
		int pageSize = ReportPaginationUtils.getRecordsNumberPerPage();
		int start = (page - 1) * pageSize;
		return ReportPaginationUtils.getReportArea(ReportPaginationCacher.getReportAreas(reportId), start, pageSize);
	}
	
	@GET
	@Path("/report/{report_id}/result/pages")
	@Produces(MediaType.APPLICATION_JSON)
	public final int getReportPagesCount(@PathParam("report_id") Long reportId) {
		ReportAreaMultiLinked[] areas = ReportPaginationCacher.getReportAreas(reportId); 
		int pageSize = ReportPaginationUtils.getRecordsNumberPerPage();
		return areas == null ? 0 : (areas.length + pageSize - 1) / pageSize;
	}
	
	private final ReportAreaMultiLinked[] cacheReportAreas(Long reportId, GeneratedReport generatedReport) {
		if (generatedReport == null) return null;
		//adding
		ReportAreaMultiLinked[] res = ReportPaginationUtils.convert(generatedReport.reportContents);
		ReportPaginationCacher.addReportAreas(reportId, res);
		return res;
	}
	
	@GET
	@Path("/tabs")
	@Produces(MediaType.APPLICATION_JSON)
	public final List<JSONTab> getTabs() {

		TeamMember tm = (TeamMember) httpRequest.getSession().getAttribute(Constants.CURRENT_MEMBER);
		AmpTeamMember ampTeamMember = TeamUtil.getAmpTeamMember(tm.getMemberId());
		if (ampTeamMember != null) {
			return getDefaultTabs(ampTeamMember);
		} else {
			return getPublicTabs();
		}
	}

	private List<JSONTab> getDefaultTabs(AmpTeamMember ampTeamMember) {
		List<JSONTab> tabs = new ArrayList<JSONTab>();

		// Look for the Default Tab and add it visible to the list
		AmpApplicationSettings ampAppSettings = DbUtil.getTeamAppSettings(ampTeamMember.getAmpTeam().getAmpTeamId());
		AmpReports defaultTeamReport = ampAppSettings.getDefaultTeamReport();
		if (defaultTeamReport != null) {
			tabs.add(Util.convert(defaultTeamReport, true));
		}

		// Get the visible tabs of the currently logged user
		if (ampTeamMember.getDesktopTabSelections() != null && ampTeamMember.getDesktopTabSelections().size() > 0) {
			TreeSet<AmpDesktopTabSelection> sortedSelection = new TreeSet<AmpDesktopTabSelection>(AmpDesktopTabSelection.tabOrderComparator);
			sortedSelection.addAll(ampTeamMember.getDesktopTabSelections());
			Iterator<AmpDesktopTabSelection> iter = sortedSelection.iterator();

			while (iter.hasNext()) {
				AmpReports report = iter.next().getReport();
				JSONTab tab = new JSONTab(report.getAmpReportId(), report.getName(), true);
				tabs.add(tab);
			}
		}

		// Get the rest of the tabs that aren't visible on first instance
		List<AmpReports> userActiveTabs = TeamUtil.getAllTeamReports(ampTeamMember.getAmpTeam().getAmpTeamId(), true, null, null, true,
				ampTeamMember.getAmpTeamMemId(), null, null);
		Iterator<AmpReports> iter = userActiveTabs.iterator();

		while (iter.hasNext()) {
			AmpReports report = iter.next();
			JSONTab tab = new JSONTab(report.getAmpReportId(), report.getName(), false);
			boolean found = false;
			Iterator<JSONTab> iTabs = tabs.iterator();
			while (iTabs.hasNext() && !found) {
				JSONTab auxTab = iTabs.next();
				if (auxTab.getId() == tab.getId()) {
					found = true;
				}
			}
			if (!found) {
				tabs.add(tab);
			}
		}
		// tabs.addAll(userActiveTabs);
		return tabs;
	}

	private List<JSONTab> getPublicTabs() {
		List<JSONTab> tabs = new ArrayList<JSONTab>();
		return tabs;
	}

	
	/**
	 * Gets the result for the specified reportId for Saiku UI
	 * @param reportId - AMP report id
	 * @return QueryResult result converted for Saiku for the requested page
	 */
	
	@GET
	@Path("/saikureport/{report_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public final QueryResult getSaikuReportResult(@PathParam("report_id") Long reportId) {
		AmpReports ampReport = DbUtil.getAmpReport(reportId);
		MondrianReportGenerator generator = new MondrianReportGenerator(SaikuReportArea.class, false);
		SaikuGeneratedReport report = null;
		try {
			ReportSpecificationImpl spec = AmpReportsToReportSpecification.convert(ampReport);
			report = generator.generateReportForSaiku(spec);
			System.out.println("[" + spec.getReportName() + "] total report generation duration = " + report.generationTime + "(ms)");
		} catch (Exception e) {
			//TODO: Log this correctly
			System.out.println("Error:" + e.getStackTrace());
		}
		return RestUtil.convert(report.cellDataSet);
	}

}
