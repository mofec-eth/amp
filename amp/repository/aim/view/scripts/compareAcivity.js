/**
 * 
 */
function viewDifferences(activityOneId) {
    document.aimCompareActivityVersionsForm.method.value = "viewDifferences";
    document.aimCompareActivityVersionsForm.activityOneId.value = activityOneId;
    document.aimCompareActivityVersionsForm.submit();
}