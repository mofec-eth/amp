<%@ taglib uri="/taglib/digijava" prefix="digi" %>
<jsp:include page="/repository/aim/view/scripts/newCalendar.jsp"  />
<style>
.contentbox_border{
	border:1px solid #666666;
	width:100%;	
	background-color: #f4f4f2;
	padding: 20 0 20 0;
}
</style>
<style>
.link{
	text-decoration: none;
	font-size: 8pt; font-family: Tahoma;
}
</style>

<style>

.tableEven {
	background-color:#dbe5f1;
	font-size:8pt;
	padding:2px;
}

.tableOdd {
	background-color:#FFFFFF;
	font-size:8pt;!important
	padding:2px;
}
 
.Hovered {
	background-color:#a5bcf2;
}
.divTable{
	display: table;
	width: 100%;
}
.divTableRow {
	display: table-row;
}
.divTableCellLeft{
	text-align: right;
	font-weight: bold;
}
.divTableCell {
	display: table-cell;
	padding: 3px 10px;
	width: 50%;
}
.divTableBody {
	display: table-row-group;
}


</style>
<script language="javascript">
function toggleFilterSettings(){
	var currentFilterSettings = $('#currentFilterSettings');
	var displayFilterButton = $('#displayFilterButton');
	if(currentFilterSettings.css('display') == "inline-flex"){
		currentFilterSettings.hide();
		$('#exportScorecard').hide();
		displayFilterButton.html('<digi:trn jsFriendly="true" key="aim:Showfilteroptions">Show Filter options</digi:trn>'+ ' &gt;&gt;');
	}
	else
	{
		currentFilterSettings.css('display', 'inline-flex');
		$('#exportScorecard').css('display','inline-flex');
		displayFilterButton.html('<digi:trn jsFriendly="true" key="aim:Hidefilteroptions">Hide Filter options</digi:trn>'+ ' &lt;&lt;');	
	}
}

function viewDifferences(activityOneId) {
    document.aimCompareActivityVersionsForm.method.value = "viewDifferences";
    document.aimCompareActivityVersionsForm.activityOneId.value = activityOneId;
    document.aimCompareActivityVersionsForm.submit();
}

function compareAll(){
    document.getElementById("compPrevForm").target = "_blank";
    var user = document.getElementById("userId").value;
    var team = document.getElementById("teamId").value;
    document.aimCompareActivityVersionsForm.selectedUser.value = user;
    document.aimCompareActivityVersionsForm.selectedTeam.value = team;
    document.aimCompareActivityVersionsForm.selectedDateFrom.value = document.getElementById("selectedDateFromText").value;
    document.aimCompareActivityVersionsForm.selectedDateTo.value = document.getElementById("selectedDateToText").value;
    document.aimCompareActivityVersionsForm.method.value = "compareAll";
    document.aimCompareActivityVersionsForm.submit();
}
</script>

<digi:form styleId="compPrevForm" action="/compareActivityVersions.do" method="post" type="aimCompareActivityVersionsForm">
<input type="hidden" name="activityOneId" id="activityOneId">
<input type="hidden" name="method" id="method">
<input type="hidden" name="selectedUser" id="selectedUser">
<input type="hidden" name="selectedTeam" id="selectedTeam" />
<input type="hidden" name="selectedDateFrom" id="selectedDateFrom">
<input type="hidden" name="selectedDateTo" id="selectedDateTo">
</digi:form>