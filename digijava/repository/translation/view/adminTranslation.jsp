<%@ page language="java" %>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib uri="/taglib/struts-bean" prefix="bean" %>
<%@ taglib uri="/taglib/struts-logic" prefix="logic" %>
<%@ taglib uri="/taglib/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/taglib/struts-html" prefix="html" %>
<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<%@ taglib uri="/taglib/jstl-core" prefix="c" %>


<script language=JavaScript type=text/javascript>
function fnOnSearchUser(search) {
      <digi:context name="searchUser" property="context/module/moduleinstance/showEditPermissions.do" />
      document.translationAdminForm.action = "<%=searchUser%>?search=true";
      document.translationAdminForm.submit();
  }
</script>

<digi:errors/>
<digi:form action="/searchUser.do" method="post">
<h3><digi:trn key="translation:administrationForSite">Translation Administration for site {siteName}</digi:trn></h3>

<table border="0" width="60%" bgcolor="#FFFFFF">
  <tr><td align="center">
	<b><digi:trn key="translation:groups">Groups</digi:trn></b>
  </td></tr>
</table>

<table border="0" width="60%" bgcolor="#FFFAF0">
  <tr><td noWrap>
    <b><digi:trn key="translation:groupName">Group Name</digi:trn></b>
  </td>
  </tr>
  <c:if test="${!empty translationAdminForm.groups}">
    <logic:iterate id="group" name="translationAdminForm" property="groups" type="org.digijava.module.translation.form.TranslationAdminForm.GroupInfo">
     <tr>
     <td noWrap>
        <digi:trn key='<%= "groups:" + group.getName() %>'><c:out value="${group.name}" /></digi:trn>
     </td>
     <td noWrap>
         <digi:link href="/showGroupAdministration.do" paramId="groupId" paramName="group" paramProperty="id">
             <digi:trn key="translation:adminPermissions">Administer Permissions</digi:trn>
         </digi:link>
     </td>
    </tr>
   </logic:iterate>
  </c:if>  
</table>
<table border="0" width="60%" bgcolor="#FFFFFF">
  <tr><td>
	<HR>
  </td></tr>
</table>

<table border="0" width="60%" bgcolor="#FFFFFF">
  <tr><td align="center">
        <b><digi:trn key="translation:searchUsers">Search Users</digi:trn></b>
  </td></tr>
</table>

<table border="0" width="60%" bgcolor="#F8F8FF">
  <tr>
     <td align="right" width="20%"><digi:trn key="translation:userInfo">User Info:</digi:trn></td>
     <td align="center" width="60%"><html:text name="translationAdminForm" property="searchUserInfo" size="40" /></td>
     <td align="left" width="20%"><html:submit value="Search" /></td>
  </tr>
  <c:if test="${!empty translationAdminForm.users}">
    <tr><td colspan=3>
    <table border="0" width="100%">
     <tr>
     <td noWrap>
        <b>
        <digi:trn key="translation:firstNames">First Names</digi:trn></b>
     </td>
     <td noWrap>
        <b>
        <digi:trn key="translation:lastName">Last Name</digi:trn> </b>
     </td>
     <td noWrap>
        <b>
        <digi:trn key="translation:email">Email</digi:trn> </b>
     </td>
    <logic:iterate id="user" name="translationAdminForm" property="users" type="org.digijava.module.translation.form.TranslationAdminForm.UserInfo">
     <tr>
     <td noWrap>
        <c:out value="${user.firstNames}" />
     </td>
     <td noWrap>
        <c:out value="${user.lastName}" />
     </td>
     <td noWrap>
        <c:out value="${user.email}" />
     </td>
     <td noWrap>
         <digi:link href="/showUserAdministration.do" paramId="userId" paramName="user" paramProperty="id">
             <digi:trn key="translation:administer">Administer</digi:trn>
         </digi:link>
     </td>
    </tr>
    </logic:iterate>
   </table> 
  </c:if>  
</table>
<digi:context name="indexUrl" property="context" />
<table>
  <tr><td colspan=4>&nbsp;</td></tr>
  <tr>
   <td colspan=4 align="left">
   <digi:trn key="translation:goTo">Go to</digi:trn> 
   <a href="<%= indexUrl%>">
    <b><digi:trn key="translation:indexPage">Index Page</digi:trn></b>
   </a> 
   </td>
  </tr>
</table>
</digi:form>