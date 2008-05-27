package org.digijava.module.aim.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.digijava.module.aim.helper.CategoryManagerUtil;
import org.digijava.module.aim.helper.Constants;

import org.digijava.module.aim.dbentity.AmpCategoryValue;

public class UpdateWorkspaceForm extends ValidatorForm {

	private Long selectedOrgId;
	private Boolean addActivity=null;
	private Boolean computation=null;
	private Collection organizations;
	private String id = null;
	private String teamName = null;
	private String description = null;
	private Long teamId = null;
	private String teamLead = null;
	private String actionEvent = null;
	private String category = null;				// 'DONOR' or 'MOFED', added for Donor-access
	private Long relatedTeam = null;			// MOFED team mapped to DONOR team
	private String relatedTeamName = null;
	// Available bilateral mofed-teams for mapping with donor-team
	private Collection relatedTeamBilatColl = new ArrayList();
	// Available multilateral mofed-teams for mapping with donor-team
	private Collection relatedTeamMutilatColl = new ArrayList();
	private String relatedTeamFlag = "no";		// 'yes', means relatedTeamColl collection is loaded, 'no' otherwise
	//private String type = null;					// 'Multilateral' or 'Bilateral'
	Long typeId;								// replaces type
	private Integer relatedTeamBilatCollSize = null;
	private String deleteFlag = null;
	private boolean updateFlag = false;
	
	private String workspaceType = null;		// 'Team' or 'Management'
	private Collection childWorkspaces;

	private boolean addFlag = false;
	private boolean reset;
	private boolean resetButton = false;
	
	private String mainAction;
	
	private String actionType;
	
	// for select child workspaces popup
	private String childWorkspaceType;
//	private String childTeamCategoryId;
	private Long childTeamTypeId;
	private Collection availChildWorkspaces;
	private Collection availChildOrgs;
	private Long[] selChildWorkspaces;
	private Long[] selChildOrgs;
	private boolean popupReset;
	private String dest;
	private Collection allChildren;
	private Collection allOrganizations;
	private Collection orgTypes;

	// FOR SELECT ORGANIZATION POPUP
	private Long ampOrgTypeId;
	private String orgType;
	private String keyword;
	private int tempNumResults;
	private Collection pagedCol;
	private Integer currentPage;
	private String currentAlpha;
	private boolean startAlphaFlag;
	private Long selOrganisations[]; // list of org selected from
	private TreeSet selectedOrganisationPaged;
	private Integer selectedOrganisationFromPages;

	// pop-up organisation selector window
	private Collection pages;
	private String[] alphaPages;
	private int numResults;
	private boolean orgPopupReset;
	
	
	
	public Collection getAllChildren() {
		Collection aux=new TreeSet();
		if (availChildWorkspaces != null)
			aux.addAll(availChildWorkspaces);
		if (childWorkspaces != null)
			aux.addAll(childWorkspaces);
		return aux;
	}

	public void setAllChildren(Collection allChildren) {
		this.allChildren = allChildren;
	}

	public boolean getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean flag) {
		updateFlag = flag;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getTeamLead() {
		return teamLead;
	}

	public void setTeamLead(String teamLead) {
		this.teamLead = teamLead;
	}

	public String getActionEvent() {
		return actionEvent;
	}

	public void setActionEvent(String actionEvent) {
		this.actionEvent = actionEvent;
	}

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * @return Returns the relatedTeam.
	 */
	public Long getRelatedTeam() {
		return relatedTeam;
	}
	/**
	 * @param relatedTeam The relatedTeam to set.
	 */
	public void setRelatedTeam(Long relatedTeam) {
		this.relatedTeam = relatedTeam;
	}
	
	/**
	 * @return Returns the relatedTeamName.
	 */
	public String getRelatedTeamName() {
		return relatedTeamName;
	}
	/**
	 * @param relatedTeamName The relatedTeamName to set.
	 */
	public void setRelatedTeamName(String relatedTeamName) {
		this.relatedTeamName = relatedTeamName;
	}
	/**
	 * @return Returns the relatedTeamBilatColl.
	 */
	public Collection getRelatedTeamBilatColl() {
		return relatedTeamBilatColl;
	}
	/**
	 * @param relatedTeamBilatColl The relatedTeamBilatColl to set.
	 */
	public void setRelatedTeamBilatColl(Collection relatedTeamBilatColl) {
		this.relatedTeamBilatColl = relatedTeamBilatColl;
	}
	/**
	 * @return Returns the relatedTeamMutilatColl.
	 */
	public Collection getRelatedTeamMutilatColl() {
		return relatedTeamMutilatColl;
	}
	/**
	 * @param relatedTeamMutilatColl The relatedTeamMutilatColl to set.
	 */
	public void setRelatedTeamMutilatColl(Collection relatedTeamMutilatColl) {
		this.relatedTeamMutilatColl = relatedTeamMutilatColl;
	}
	
	/**
	 * @return Returns the relatedTeamFlag.
	 */
	public String getRelatedTeamFlag() {
		return relatedTeamFlag;
	}
	/**
	 * @param relatedTeamFlag The relatedTeamFlag to set.
	 */
	public void setRelatedTeamFlag(String relatedTeamFlag) {
		this.relatedTeamFlag = relatedTeamFlag;
	}
	
	

	/**
	 * @return Returns the relatedTeamBilatCollSize.
	 */
	public Integer getRelatedTeamBilatCollSize() {
		return relatedTeamBilatCollSize;
	}
	/**
	 * @param relatedTeamBilatCollSize The relatedTeamBilatCollSize to set.
	 */
	public void setRelatedTeamBilatCollSize(Integer relatedTeamBilatCollSize) {
		this.relatedTeamBilatCollSize = relatedTeamBilatCollSize;
	}

	/**
	 * @return Returns the availChildWorkspaces.
	 */
	public Collection getAvailChildWorkspaces() {
		return availChildWorkspaces;
	}
	/**
	 * @param availChildWorkspaces The availChildWorkspaces to set.
	 */
	public void setAvailChildWorkspaces(Collection availChildWorkspaces) {
		this.availChildWorkspaces = availChildWorkspaces;
	}
	/**
	 * @return Returns the childTeamCategory.
	 */
//	public String getChildTeamCategory() {
//		return childTeamCategory;
//	}
	
	/**
	 * @param childTeamCategory The childTeamCategory to set.
	 */
//	public void setChildTeamCategory(String childTeamCategory) {
//		this.childTeamCategory = childTeamCategory;
//	}
	/**
	 * @return Returns the childWorkspaces.
	 */
	public Collection getChildWorkspaces() {
		return childWorkspaces;
	}
	/**
	 * @param childWorkspaces The childWorkspaces to set.
	 */
	public void setChildWorkspaces(Collection childWorkspaces) {
		this.childWorkspaces = childWorkspaces;
	}
	/**
	 * @return Returns the childWorkspaceType.
	 */
	public String getChildWorkspaceType() {
		return childWorkspaceType;
	}
	/**
	 * @param childWorkspaceType The childWorkspaceType to set.
	 */
	public void setChildWorkspaceType(String childWorkspaceType) {
		this.childWorkspaceType = childWorkspaceType;
	}
	/**
	 * @return Returns the selChildWorkspaces.
	 */
	public Long[] getSelChildWorkspaces() {
		return selChildWorkspaces;
	}
	/**
	 * @param selChildWorkspaces The selChildWorkspaces to set.
	 */
	public void setSelChildWorkspaces(Long[] selChildWorkspaces) {
		this.selChildWorkspaces = selChildWorkspaces;
	}
	/**
	 * @return Returns the workspaceType.
	 */
	public String getWorkspaceType() {
		return workspaceType;
	}
	/**
	 * @param workspaceType The workspaceType to set.
	 */
	public void setWorkspaceType(String workspaceType) {
		this.workspaceType = workspaceType;
	}
	
	/**
	 * @return Returns the addFlag.
	 */
	public boolean isAddFlag() {
		return addFlag;
	}
	/**
	 * @param addFlag The addFlag to set.
	 */
	public void setAddFlag(boolean addFlag) {
		this.addFlag = addFlag;
	}
	/**
	 * @return Returns the popupReset.
	 */
	public boolean isPopupReset() {
		return popupReset;
	}
	/**
	 * @param popupReset The popupReset to set.
	 */
	public void setPopupReset(boolean popupReset) {
		this.popupReset = popupReset;
	}
	/**
	 * @return Returns the reset.
	 */
	public boolean isReset() {
		return reset;
	}
	/**
	 * @param reset The reset to set.
	 */
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	
	/**
	 * @return Returns the dest.
	 */
	public String getDest() {
		return dest;
	}
	/**
	 * @param dest The dest to set.
	 */
	public void setDest(String dest) {
		this.dest = dest;
	}
	
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getMainAction() {
		return mainAction;
	}

	public void setMainAction(String mainAction) {
		this.mainAction = mainAction;		  
	}
	
	public void reset(ActionMapping mapping,HttpServletRequest request) {
		if (reset) {
			id = null;
			teamName = null;
			description = null;
			teamId = null;
			teamLead = null;
			actionEvent = null;
			typeId = new Long(0);
			deleteFlag = null;
			updateFlag = false;
			workspaceType = null;
			childWorkspaces = null;
			organizations=null;
			popupReset = true;
			mainAction = null;
			dest = null;
			
			category = null;
			relatedTeam = null;
			relatedTeamName = null;
			relatedTeamBilatColl = new ArrayList();
			relatedTeamMutilatColl = new ArrayList();
			relatedTeamFlag = "no";
			relatedTeamBilatCollSize = null;
			deleteFlag = null;
			updateFlag = false;
			workspaceType = null;
			//availChildWorkspaces = null;
			actionType=null;
			selChildWorkspaces = null;
			selChildOrgs=null;
			addFlag = false;
			reset	= false;
			addActivity=null;
			computation=null;
			
		}		
		if (popupReset) {
			availChildWorkspaces = null;
			selChildWorkspaces = null;	
			availChildOrgs=null;
			selChildOrgs=null;
		}
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors 					= null;
		AmpCategoryValue typeCategoryValue		= CategoryManagerUtil.getAmpCategoryValueFromDb(typeId);
		
		if ("no".equals(relatedTeamFlag)) {
			errors = super.validate(mapping, request);
			
//			if(("DONOR".equalsIgnoreCase(category) &&("Team".equalsIgnoreCase(workspaceType)|| "Management".equalsIgnoreCase(workspaceType)))
//					||("GOVERNMENT".equalsIgnoreCase(category) && ("Donor".equals(workspaceType))) ){
//				ActionError error = new ActionError("error.aim.updateWorkspace.incorrectWorkspaceType");
//				errors.add("workspaceType", error);
//			}
			
			if ("DONOR".equalsIgnoreCase(category) && "Donor".equalsIgnoreCase(workspaceType)) {
				if ("edit".equalsIgnoreCase(actionEvent)) {
					if (typeCategoryValue!=null && Constants.TEAM_TYPE_BILATERAL.equals( typeCategoryValue.getValue() )) {
						if (relatedTeamBilatColl.size() > 0 )
							if (null == relatedTeam || "-1".equals(relatedTeam) || relatedTeam.toString().trim().length() < 1) {
								ActionError error = new ActionError("error.aim.updateWorkspace.noRelatedTeam");
								errors.add("relatedTeam", error);
								relatedTeamFlag = "set";
							}
					}
					if ( typeCategoryValue!=null && Constants.TEAM_TYPE_MULTILATERAL.equals(typeCategoryValue.getValue()) ) {
						if (relatedTeamMutilatColl.size() > 0)
							if (null == relatedTeam || "-1".equals(relatedTeam) || relatedTeam.toString().trim().length() < 1) {
								ActionError error = new ActionError("error.aim.updateWorkspace.noRelatedTeam");
								errors.add("relatedTeam", error);
								relatedTeamFlag = "set";
							}
					}
				}
				else if ("add".equalsIgnoreCase(actionEvent)) {
					if (relatedTeamBilatColl.size() > 0 && relatedTeamMutilatColl.size() > 0) {
						if (null == typeCategoryValue) {
							ActionError error = new ActionError("error.aim.updateWorkspace.noTeamType");
							errors.add("type", error);
						}
						else if (null == relatedTeam || "-1".equals(relatedTeam) || relatedTeam.toString().trim().length() < 1) {
							ActionError error = new ActionError("error.aim.updateWorkspace.noRelatedTeam");
							errors.add("relatedTeam", error);
						}
						relatedTeamFlag = "set";
					}
				}
			}
		}
		return errors;
	}

	public boolean isResetButton() {
		return resetButton;
	}

	public boolean getResetButton() {
		return resetButton;
	}
	
	public void setResetButton(boolean resetButton) {
		this.resetButton = resetButton;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getChildTeamTypeId() {
		return childTeamTypeId;
	}

	public void setChildTeamTypeId(Long childTeamTypeId) {
		this.childTeamTypeId = childTeamTypeId;
	}





	public Long getSelectedOrgId() {
		return selectedOrgId;
	}

	public void setSelectedOrgId(Long selectedOrgId) {
		this.selectedOrgId = selectedOrgId;
	}

	public Collection getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Collection organizations) {
		this.organizations = organizations;
	}

	public Collection getAvailChildOrgs() {
		return availChildOrgs;
	}

	public void setAvailChildOrgs(Collection availChildOrgs) {
		this.availChildOrgs = availChildOrgs;
	}

	public Long[] getSelChildOrgs() {
		return selChildOrgs;
	}

	public void setSelChildOrgs(Long[] selChildOrgs) {
		this.selChildOrgs = selChildOrgs;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Collection getAllOrganizations() {
		return allOrganizations;
	}

	public void setAllOrganizations(Collection allOrganizations) {
		this.allOrganizations = allOrganizations;
	}

	public Collection getOrgTypes() {
		return orgTypes;
	}
	public void setOrgTypes(Collection orgTypes) {
		this.orgTypes = orgTypes;
	}

	/**
	 * @param ampOrgTypeId The ampOrgTypeId to set.
	 */
	public void setAmpOrgTypeId(Long ampOrgTypeId) {
		this.ampOrgTypeId = ampOrgTypeId;
	}
	/**
	 * @return Returns the ampOrgTypeId.
	 */
	public Long getAmpOrgTypeId() {
		return ampOrgTypeId;
	}
	/**
	 * @return Returns the keyword.
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            The keyword to set.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @return Returns the pagedCol.
	 */
	public Collection getPagedCol() {
		return pagedCol;
	}

	/**
	 * @param pagedCol
	 *            The pagedCol to set.
	 */
	public void setPagedCol(Collection pagedCol) {
		this.pagedCol = pagedCol;
	}
	/**
	 * @return Returns the tempNumResults.
	 */
	public int getTempNumResults() {
		return tempNumResults;
	}

	/**
	 * @param tempNumResults
	 *            The tempNumResults to set.
	 */
	public void setTempNumResults(int tempNumResults) {
		this.tempNumResults = tempNumResults;
	}
	/**
	 * @param orgPopupReset
	 *            The orgPopupReset to set.
	 */
	public void setOrgPopupReset(boolean orgPopupReset) {
		this.orgPopupReset = orgPopupReset;
	}

	public Boolean getAddActivity() {
		return addActivity;
	}

	public void setAddActivity(Boolean addActivity) {
		this.addActivity = addActivity;
	}

	public Boolean getComputation() {
		return computation;
	}

	public void setComputation(Boolean computation) {
		this.computation = computation;
	}






}
