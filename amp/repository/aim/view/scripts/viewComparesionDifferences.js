



	function viewDifferences(activityOneId) {
	    document.getElementById("compPrevForm").target = "_self";
	    document.aimCompareActivityVersionsForm.method.value = "viewDifferences";
	    document.aimCompareActivityVersionsForm.activityOneId.value = activityOneId;
	    document.aimCompareActivityVersionsForm.submit();
	}
