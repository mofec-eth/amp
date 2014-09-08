function resetFilter(){
		resetRootCheckboxes();
		
			
		if (gisDashboardForm.currency)
			gisDashboardForm.currency.value=gisDashboardForm.defaultCurrency.value;			
				
		resetElement(gisDashboardForm.selectedSectors);
		resetElement(gisDashboardForm.selectedSecondarySectors);
        resetElement(gisDashboardForm.selectedTertiarySectors);
		resetElement(gisDashboardForm.selectedNatPlanObj);
		resetElement(gisDashboardForm.selectedPrimaryPrograms);
        resetElement(gisDashboardForm.selectedSecondaryPrograms);
        
 		resetElement(gisDashboardForm.selectedDonorGroups);
 		resetElement(gisDashboardForm.selectedDonorTypes);
 		resetElement(gisDashboardForm.selectedDonnorAgency); 		
 		
	}

function resetElement( elem ) {
	if (elem != null && elem.length > 1)
		for (var i=0; i<elem.length; i++)
			elem[i].checked = false;
	else 
		if (elem!=null){
			elem.checked	= false;
		}
}

function resetRootCheckboxes() {
	var allCheckboxes	= document.getElementsByTagName("input");
	for (var i=0; i<allCheckboxes.length; i++) {
		var inputYuiEl	= new YAHOO.util.Element(allCheckboxes[i]);
		if ( inputYuiEl.hasClass("root_checkbox") ) {
			allCheckboxes[i].checked = false;
		}
	}
}

YAHOO.namespace("YAHOO.amptab");

YAHOO.amptab.afterFiltersLoad   = function (){
	var selColor    = "#BFD2DF";
	
	 	donorsPropertyObj               = new RowManagerProperty(null, "filter_rows_donors", selColor);
	 	relAgenciesPropertyObj  		= new RowManagerProperty(null, "filter_rows_rel_agencies", selColor);
	 	sectorsPropertyObj              = new RowManagerProperty(null, "filter_rows_sectors", selColor);
	 	programsPropertyObj             = new RowManagerProperty(null, "filter_rows_programs", selColor);
	 	financingLocPropertyObj         = new RowManagerProperty(null, "filter_rows_finanacing_loc", selColor);
	 	otherCriteriaPropertyObj        = new RowManagerProperty(null, "filter_rows_other_criteria", selColor);
	 	
	 	YAHOO.amptab.initDisplayOfMemberSelectors("donorsTab");
	 	YAHOO.amptab.initDisplayOfMemberSelectors("relAgenciesTab");
	 	YAHOO.amptab.initDisplayOfMemberSelectors("sectorsTab");
	 	YAHOO.amptab.initDisplayOfMemberSelectors("programsTab");
	 	YAHOO.amptab.initDisplayOfMemberSelectors("financingLocTab");
	 	YAHOO.amptab.initDisplayOfMemberSelectors("otherCriteriaTab");
	 }
	 	
YAHOO.amptab.initDisplayOfMemberSelectors       = function(bigDivId) {
	var bigDivEl            = document.getElementById(bigDivId);
	 	//alert("aici" + bigDivEl);
	 	if (bigDivEl != null) {
		 	var listOfDivs  = bigDivEl.getElementsByTagName("div");
		 	if (listOfDivs != null && listOfDivs.length > 0) {
		 		for (var i=0; i<listOfDivs.length; i++) {
		 			var divYuiEl    = new YAHOO.util.Element(listOfDivs[i]);
		 			if ( divYuiEl.hasClass("grouping_selector_wrapper") ) {
		 				var buttonEl    = listOfDivs[i].getElementsByTagName("button")[0];
		 					if(typeof(buttonEl) != "undefined") buttonEl.click();
		 					break;
		 				}
		 		}
		 	}
		}
	}

function toggleCheckChildren(checkboxEl) {
	
	var parentTdEl				= checkboxEl.parentNode;
	for (var i=0; i<=5; i++) {
		if (parentTdEl.nodeName.toLowerCase()=="li") break;
		parentTdEl		= parentTdEl.parentNode;
	}
	var descendantCheckboxes	= parentTdEl.getElementsByTagName('input');
	for (var i=0; i<descendantCheckboxes.length; i++ ) {
		descendantCheckboxes[i].checked	= checkboxEl.checked ;
	}
	
	if ( ! checkboxEl.checked ) {
		var tempParent				= parentTdEl.parentNode;
		var nodeName				= tempParent.nodeName.toLowerCase();
		while ( tempParent != null && 
				(nodeName=="li" || nodeName=="ul" || 
						nodeName=="td" || nodeName=="tr" ||	
							nodeName=="table") ) {
			
			if ( nodeName=="li" ) {
				for ( var i=0; i<tempParent.childNodes.length; i++) {
					var tempNode	= tempParent.childNodes[i];
					if ( tempNode.nodeName.toLowerCase()=="table" )
						tempNode.getElementsByTagName("input")[0].checked	= false;
					if ( tempNode.nodeName.toLowerCase()=="input" )
						tempNode.checked	= false;
				}
			}
			tempParent				= tempParent.parentNode;
			nodeName				= tempParent.nodeName.toLowerCase();
		}
	}
}

	 
function DivManager(divId, propertyObj) {
	 	this.divEl                      = document.getElementById(divId);
	 	this.propertyObj        = propertyObj;
	 }
	
DivManager.prototype.onSelect           = function () {
	 	if ( this.propertyObj.lastShownDiv      != null )
	 		this.propertyObj.lastShownDiv.style.display     = "none";
	 	this.divEl.style.display                = "block";
	 	this.propertyObj.lastShownDiv   = this.divEl;
	 	var parentDiv                                   = this.divEl
	 	var found                                               = false;
	 	for (var i=0; i<4; i++) { //should be more than 5 steps aways
		 	parentDiv       = parentDiv.parentNode;
		 	if ( parentDiv.nodeName.toLowerCase() == "div" && parentDiv.id.indexOf("Tab") > 0  ){
		 		found   = true;
		 		break;
		 	}
	 	}
	 	if (found)
	 		getSearchManagerInstanceById( parentDiv.id+"_search" ).setDiv(this.divEl);
	 	else
	 		alert("Not found");
	 }