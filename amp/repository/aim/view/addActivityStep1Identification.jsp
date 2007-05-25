<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>
<%@ taglib uri="/taglib/category" prefix="category" %>

<script language="JavaScript">

</script>


<digi:instance property="aimEditActivityForm" />
										<table width="100%" bgcolor="#cccccc" cellPadding=5 cellSpacing=1>
											<tr bgcolor="#ffffff"><td valign="top" align="left">
												<FONT color=red>*</FONT>
												<a title="<digi:trn key="aim:TitleInDonorsOrMoFEDInternalSystems">Title used in donors or MoFED internal systems</digi:trn>">
												<digi:trn key="aim:projectTitle">Project Title</digi:trn>
												</a>
											</td>
											<td valign="top" align="left">
												<a title="<digi:trn key="aim:TitleInDonorsOrMoFEDInternalSystems">
												Title used in donors or MoFED internal systems
												</digi:trn>">
												<html:textarea property="title" cols="60" rows="2" styleClass="inp-text"/>
												</a>
											</td></tr>
											<tr bgcolor="#ffffff"><td valign="top" align="left">
												<a title="<digi:trn key="aim:ObjectivesAndComponentsofProject">The key objectives and main components of the project</digi:trn>">
												<digi:trn key="aim:objective">Objective</digi:trn>
												</a>
											</td>
											<td valign="top" align="left">
												<table cellPadding=0 cellSpacing=0>
													<tr>
														<td>
															<bean:define id="objKey">
																<c:out value="${aimEditActivityForm.objectives}"/>
															</bean:define>
															<digi:edit key="<%=objKey%>"/>
														</td>
													</tr>
													<tr>
														<td>
															<%--
															<a href="<c:out value="${aimEditActivityForm.context}"/>/editor/showEditText.do?id=<%=objKey%>&referrer=<c:out value="${aimEditActivityForm.context}"/>/aim/addActivity.do?edit=true">Edit</a>
															--%>
															<a href="javascript:edit('<%=objKey%>')">
															Edit</a>	
															&nbsp;
															<a href="javascript:commentWin('objAssumption')" id="CommentObjAssumption">Add/Edit Assumption</a>
															&nbsp;
															<a href="javascript:commentWin('objVerification')" id="CommentObjVerification">Add/Edit Verification</a>		
														</td>
													</tr>
												</table>												
											</td></tr>
											
											<logic:notEmpty name="SA" scope="application">
											<tr bgcolor="#ffffff"><td valign="top" align="left">
												<a title="<digi:trn key="aim:DescriptionofProject">Summary information describing the project</digi:trn>">
												<digi:trn key="aim:description">
												Description
												</digi:trn>
												</a>
											</td>
											<td valign="top" align="left">
												<table cellPadding=0 cellSpacing=0>
													<tr>
														<td>
															<bean:define id="descKey">
																<c:out value="${aimEditActivityForm.description}"/>
															</bean:define>
			
															<digi:edit key="<%=descKey%>"/>
														</td>
													</tr>
													<tr>
														<td>
															<%--
															<a href="<c:out value="${aimEditActivityForm.context}"/>/editor/showEditText.do?id=<%=descKey%>&referrer=<c:out value="${aimEditActivityForm.context}"/>/aim/addActivity.do?edit=true">Edit</a>
															--%>
															<a href="javascript:edit('<%=descKey%>')">
															Edit</a>
														</td>
													</tr>
												</table>
											</td></tr>
											</logic:notEmpty>
		
											<!-- Purpose -->
											<logic:empty name="SA" scope="application">
											<tr bgcolor="#ffffff"><td valign="top" align="left">
												<a title="<digi:trn key="aim:PurposeofProject">Purpose of the project</digi:trn>">
												<digi:trn key="aim:purpose">
												Purpose
												</digi:trn>
												</a>
											</td>
											
											<td valign="top" align="left">
												<table cellPadding=0 cellSpacing=0>
													<tr>
														<td>
															<bean:define id="purpKey">
																<c:out value="${aimEditActivityForm.purpose}"/>
															</bean:define>
			
															<digi:edit key="<%=purpKey%>"/>
														</td>
													</tr>
													<tr>
														<td>
															<%--
															<a href="<c:out value="${aimEditActivityForm.context}"/>/editor/showEditText.do?id=<%=descKey%>&referrer=<c:out value="${aimEditActivityForm.context}"/>/aim/addActivity.do?edit=true">Edit</a>--%>
			
															<a href="javascript:edit('<%=purpKey%>')">
															Edit</a>
															&nbsp;
															<a href="javascript:commentWin('purpAssumption')" id="CommentPurpAssumption">Add/Edit Assumption</a>
															&nbsp;
															<a href="javascript:commentWin('purpVerification')" id="CommentPurpVerification">Add/Edit Verification</a>
														</td>
													</tr>
												</table>
											</td></tr>
										</logic:empty>
										<logic:empty name="SA" scope="application">
											<!-- Results -->
											<tr bgcolor="#ffffff"><td valign="top" align="left">
												<a title="<digi:trn key="aim:ResultsofProject">Results of the project</digi:trn>">
												<digi:trn key="aim:results">
												Results
												</digi:trn>
												</a>
											</td>
											
											<td valign="top" align="left">
												<table cellPadding=0 cellSpacing=0>
													<tr>
														<td>
															<bean:define id="resKey">
																<c:out value="${aimEditActivityForm.results}"/>
															</bean:define>
			
															<digi:edit key="<%=resKey%>"/>
														</td>
													</tr>
													<tr>
														<td>
															<%--
															<a href="<c:out value="${aimEditActivityForm.context}"/>/editor/showEditText.do?id=<%=descKey%>&referrer=<c:out value="${aimEditActivityForm.context}"/>/aim/addActivity.do?edit=true">Edit</a>--%>
			
															<a href="javascript:edit('<%=resKey%>')">
															Edit</a>
															&nbsp;
															<a href="javascript:commentWin('resAssumption')" id="CommentResAssumption">Add/Edit Assumption</a>
															&nbsp;
															<a href="javascript:commentWin('resVerification')" id="CommentResVerification">Add/Edit Verification</a>
														</td>
													</tr>
												</table>
											</td></tr>
											</logic:empty>
											<logic:empty name="SA" scope="application">
											<tr bgcolor="#ffffff"><td valign="top" align="left">
												<a title="<digi:trn key="aim:DescriptionOfAccessionInstrument">Accession Instrument of the project</digi:trn>">
												<digi:trn key="aim:AccessionInstrument">
												Accession Instrument
												</digi:trn>
												</a>
											</td>
											<td valign="top" align="left">
													<category:showoptions name="aimEditActivityForm" property="accessionInstrument" categoryName="<%= org.digijava.module.aim.helper.CategoryConstants.ACCESSION_INSTRUMENT_NAME %>" styleClass="inp-text" />
											</td></tr>	
											</logic:empty>
											<logic:empty name="SA" scope="application">
											<tr bgcolor="#ffffff"><td valign="top" align="left">
												<a title="<digi:trn key="aim:DescriptionofACChapter">A.C. Chapter of the project</digi:trn>">
												<digi:trn key="aim:acChapter">
												A.C. Chapter
												</digi:trn>
												</a>
											</td>
											<td valign="top" align="left">
													<category:showoptions name="aimEditActivityForm" property="acChapter" categoryName="<%= org.digijava.module.aim.helper.CategoryConstants.ACCHAPTER_NAME %>" styleClass="inp-text" />
											</td></tr>											
											</logic:empty>								
											<logic:notEmpty name="SA" scope="application">																																						
											<tr bgcolor="#ffffff"><td valign="top" align="left">
												<a title="<digi:trn key="aim:DescriptionofProject">Summary information describing the project</digi:trn>">
												<digi:trn key="aim:actBudget">
												Activity Budget
												</digi:trn>
												</a>
											</td>
											<td valign="top" align="left">
													<html:checkbox property="budget">
													<digi:trn key="aim:actBudgeton">
												Activity is On Budget
												</digi:trn>
													</html:checkbox>
											</td></tr>
											</logic:notEmpty>
										</table>

