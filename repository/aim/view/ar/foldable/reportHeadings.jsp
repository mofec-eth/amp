<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>

<bean:define id="columnReport" name="viewable" type="org.dgfoundation.amp.ar.ColumnReportData" scope="request" toScope="page"/>
<bean:define id="reportMeta" name="reportMeta" type="org.digijava.module.aim.dbentity.AmpReports" scope="session" toScope="page"/>


<%int rowIdx = 2;%>

<!-- generate report headings -->
<logic:equal name="columnReport" property="globalHeadingsDisplayed" value="false">
<%int maxDepth = columnReport.getMaxColumnDepth();
				columnReport.setGlobalHeadingsDisplayed(new Boolean(true));
				%>
<%for (int curDepth = 0; curDepth <= columnReport
						.getMaxColumnDepth(); curDepth++, rowIdx++) {%>
<tr title="Report Headings">
<logic:iterate name="columnReport" property="items" id="column" scope="page" type="org.dgfoundation.amp.ar.Column">
	<%column.setCurrentDepth(curDepth);%>
	<%int rowsp = column.getCurrentRowSpan();
						%>
	<logic:iterate name="column" property="subColumnList" id="subColumn" scope="page" type="org.dgfoundation.amp.ar.Column">
	<td align="center" class=clsTableTitleCol rowspan="<%=rowsp%>" colspan='<bean:write name="subColumn" property="width"/>'>
	
	<logic:equal name="column" property="columnDepth" value="1">

	
	
	<logic:equal name="widget" scope="request" value="true">

	<a style="color:#FFFFFF;cursor:pointer" onclick="changeTabUrl('MyTabs','Tab-<bean:write name="reportMeta" property="name"/>','/aim/viewNewAdvancedReport.do~viewFormat=foldable~ampReportId=<bean:write name="reportMeta" property="ampReportId"/>~widget=true~sortBy=<bean:write name="column" property="name"/>');">
		<%=subColumn.getName(reportMeta.getHideActivities())%>		
	</a>
	</logic:equal>
	<logic:notEqual name="widget" scope="request" value="true">			
	<html:link style="color:#FFFFFF;cursor:pointer" page="/viewNewAdvancedReport.do" paramName="column" paramProperty="name" paramId="sortBy">
		<%=subColumn.getName(reportMeta.getHideActivities())%>
	</html:link>
	</logic:notEqual>
	<c:if test="${column.name == columnReport.sorterColumn}">
	<logic:equal name="columnReport" property="sortAscending" value="false">
	<img src= "../ampTemplate/images/down.gif" align="absmiddle" border="0"/>
	</logic:equal>
	<logic:equal name="columnReport" property="sortAscending" value="true">
	<img src= "../ampTemplate/images/up.gif" align="absmiddle" border="0"/>
	</logic:equal>
	</c:if>
	</logic:equal>
	<logic:notEqual name="column" property="columnDepth" value="1">
		<%=subColumn.getName(reportMeta.getHideActivities())%>
	</logic:notEqual>
	</td>
	
	</logic:iterate>
</logic:iterate>
</tr>
<%}
			%>
</logic:equal>
