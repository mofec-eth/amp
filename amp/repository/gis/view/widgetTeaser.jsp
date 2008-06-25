<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>

<digi:instance property="gisWidgetTeaserForm" />

<table>
	<tr>
		<td>
			<c:if test="${gisWidgetTeaserForm.rendertype==3}">
				<img src="/gis/widgetchart.do~widgetId=${gisWidgetTeaserForm.id}">		
			</c:if>
			<c:if test="${gisWidgetTeaserForm.rendertype!=3}">
				empty teaser		
			</c:if>
		</td>
	</tr>
</table>