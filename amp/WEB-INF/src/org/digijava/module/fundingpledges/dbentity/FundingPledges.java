// Generated by delombok at Mon Mar 24 00:10:06 EET 2014
package org.digijava.module.fundingpledges.dbentity;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import org.dgfoundation.amp.ar.AmpARFilter;
import org.digijava.module.aim.dbentity.AmpOrgGroup;
import org.digijava.module.aim.dbentity.AmpOrganisation;
import org.digijava.module.aim.dbentity.AmpSector;
import org.digijava.module.aim.logic.FundingCalculationsHelper;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;

/**
 * @author Diego Dimunzio
 */
public class FundingPledges implements Comparable<FundingPledges>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private AmpCategoryValue title;
	private String titleFreeText;
	private String additionalInformation;
	private String whoAuthorizedPledge;
	private String furtherApprovalNedded;
	@Deprecated
	private AmpOrganisation organization;
	private AmpOrgGroup organizationGroup;
	private Set<FundingPledgesSector> sectorlist;
	private Set<FundingPledgesLocation> locationlist;
	private Set<FundingPledgesProgram> programlist;
	private Set<FundingPledgesDetails> fundingPledgesDetails;
	// "Point of Contact at Donors Conference on March 31st"
	private String contactName;
	private String contactAddress;
	private String contactEmail;
	private String contactTitle;
	private String contactMinistry;
	private String contactTelephone;
	private String contactFax;
	private AmpOrganisation contactOrganization;
	private String contactAlternativeName;
	private String contactAlternativeTelephone;
	private String contactAlternativeEmail;
	//"is Point of Contact for Follow Up"
	private String contactName_1;
	private String contactAddress_1;
	private String contactEmail_1;
	private String contactTitle_1;
	private String contactMinistry_1;
	private String contactTelephone_1;
	private String contactFax_1;
	private AmpOrganisation contactOrganization_1;
	private String contactAlternativeName_1;
	private String contactAlternativeTelephone_1;
	private String contactAlternativeEmail_1;

	private TreeSet<String> yearsList;
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof FundingPledges)) return false;
		FundingPledges p = (FundingPledges)o;
		return this.getId().equals(p.getId());
	}
	
	@Override
	public int compareTo(FundingPledges o) {
		return (int)(this.getId() - o.getId());
	}
	
	/**
	 * computes the effectively-displayed name of the pledge
	 * @return
	 */
	public String getEffectiveName(){
		if (PledgesEntityHelper.useFreeText() && (this.titleFreeText != null) && (!this.titleFreeText.isEmpty()))
			return this.titleFreeText;
		else 
			return this.getTitle() == null ? "(null)" : this.getTitle().getValue();
	}
	
	public boolean isUsedInActivityFunding(){
		return !PledgesEntityHelper.getFundingRelatedToPledges(this).isEmpty();
	}
	// trash getters / setters below
	
	@java.lang.SuppressWarnings("all")
	public Long getId() {
		return this.id;
	}
	
	@java.lang.SuppressWarnings("all")
	public AmpCategoryValue getTitle() {
		return this.title;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getTitleFreeText() {
		return this.titleFreeText;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getAdditionalInformation() {
		return this.additionalInformation;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getWhoAuthorizedPledge() {
		return this.whoAuthorizedPledge;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getFurtherApprovalNedded() {
		return this.furtherApprovalNedded;
	}
	
	@java.lang.Deprecated
	@java.lang.SuppressWarnings("all")
	public AmpOrganisation getOrganization() {
		return this.organization;
	}
	
	@java.lang.SuppressWarnings("all")
	public AmpOrgGroup getOrganizationGroup() {
		return this.organizationGroup;
	}
	
	@java.lang.SuppressWarnings("all")
	public Set<FundingPledgesSector> getSectorlist() {
		return this.sectorlist;
	}
	
	@java.lang.SuppressWarnings("all")
	public Set<FundingPledgesLocation> getLocationlist() {
		return this.locationlist;
	}
	
	@java.lang.SuppressWarnings("all")
	public Set<FundingPledgesProgram> getProgramlist() {
		return this.programlist;
	}
	
	@java.lang.SuppressWarnings("all")
	public Set<FundingPledgesDetails> getFundingPledgesDetails() {
		return this.fundingPledgesDetails;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactName() {
		return this.contactName;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactAddress() {
		return this.contactAddress;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactEmail() {
		return this.contactEmail;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactTitle() {
		return this.contactTitle;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactMinistry() {
		return this.contactMinistry;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactTelephone() {
		return this.contactTelephone;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactFax() {
		return this.contactFax;
	}
	
	@java.lang.SuppressWarnings("all")
	public AmpOrganisation getContactOrganization() {
		return this.contactOrganization;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactAlternativeName() {
		return this.contactAlternativeName;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactAlternativeTelephone() {
		return this.contactAlternativeTelephone;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactAlternativeEmail() {
		return this.contactAlternativeEmail;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactName_1() {
		return this.contactName_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactAddress_1() {
		return this.contactAddress_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactEmail_1() {
		return this.contactEmail_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactTitle_1() {
		return this.contactTitle_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactMinistry_1() {
		return this.contactMinistry_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactTelephone_1() {
		return this.contactTelephone_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactFax_1() {
		return this.contactFax_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public AmpOrganisation getContactOrganization_1() {
		return this.contactOrganization_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactAlternativeName_1() {
		return this.contactAlternativeName_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactAlternativeTelephone_1() {
		return this.contactAlternativeTelephone_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public String getContactAlternativeEmail_1() {
		return this.contactAlternativeEmail_1;
	}
	
	/**
	 * calculates the sum of all the pledges <strong>in the currency of the workspace</strong>
	 * @param currencyCode: the currency to make calculatins in. If null, then use defaultCurrency (workspace / base)
	 * @return
	 */
	public Double getTotalPledgedAmount(String currencyCode) {
		FundingCalculationsHelper calc = new FundingCalculationsHelper();
		if (currencyCode == null)
			currencyCode = AmpARFilter.getDefaultCurrency().getCurrencyCode();
		calc.doCalculations(this.getFundingPledgesDetails(), currencyCode, true);
		return calc.getTotalPledged().doubleValue();
	}
	
		
	@java.lang.SuppressWarnings("all")
	public TreeSet<String> getYearsList() {
		return this.yearsList;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setId(final Long id) {
		this.id = id;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setTitle(final AmpCategoryValue title) {
		this.title = title;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setTitleFreeText(final String titleFreeText) {
		this.titleFreeText = titleFreeText;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setAdditionalInformation(final String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setWhoAuthorizedPledge(final String whoAuthorizedPledge) {
		this.whoAuthorizedPledge = whoAuthorizedPledge;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setFurtherApprovalNedded(final String furtherApprovalNedded) {
		this.furtherApprovalNedded = furtherApprovalNedded;
	}
	
	@java.lang.Deprecated
	@java.lang.SuppressWarnings("all")
	public void setOrganization(final AmpOrganisation organization) {
		this.organization = organization;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setOrganizationGroup(final AmpOrgGroup organizationGroup) {
		this.organizationGroup = organizationGroup;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setSectorlist(final Set<FundingPledgesSector> sectorlist) {
		this.sectorlist = sectorlist;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setLocationlist(final Set<FundingPledgesLocation> locationlist) {
		this.locationlist = locationlist;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setProgramlist(final Set<FundingPledgesProgram> programlist) {
		this.programlist = programlist;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setFundingPledgesDetails(final Set<FundingPledgesDetails> fundingPledgesDetails) {
		this.fundingPledgesDetails = fundingPledgesDetails;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactName(final String contactName) {
		this.contactName = contactName;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactAddress(final String contactAddress) {
		this.contactAddress = contactAddress;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactEmail(final String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactTitle(final String contactTitle) {
		this.contactTitle = contactTitle;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactMinistry(final String contactMinistry) {
		this.contactMinistry = contactMinistry;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactTelephone(final String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactFax(final String contactFax) {
		this.contactFax = contactFax;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactOrganization(final AmpOrganisation contactOrganization) {
		this.contactOrganization = contactOrganization;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactAlternativeName(final String contactAlternativeName) {
		this.contactAlternativeName = contactAlternativeName;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactAlternativeTelephone(final String contactAlternativeTelephone) {
		this.contactAlternativeTelephone = contactAlternativeTelephone;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactAlternativeEmail(final String contactAlternativeEmail) {
		this.contactAlternativeEmail = contactAlternativeEmail;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactName_1(final String contactName_1) {
		this.contactName_1 = contactName_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactAddress_1(final String contactAddress_1) {
		this.contactAddress_1 = contactAddress_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactEmail_1(final String contactEmail_1) {
		this.contactEmail_1 = contactEmail_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactTitle_1(final String contactTitle_1) {
		this.contactTitle_1 = contactTitle_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactMinistry_1(final String contactMinistry_1) {
		this.contactMinistry_1 = contactMinistry_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactTelephone_1(final String contactTelephone_1) {
		this.contactTelephone_1 = contactTelephone_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactFax_1(final String contactFax_1) {
		this.contactFax_1 = contactFax_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactOrganization_1(final AmpOrganisation contactOrganization_1) {
		this.contactOrganization_1 = contactOrganization_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactAlternativeName_1(final String contactAlternativeName_1) {
		this.contactAlternativeName_1 = contactAlternativeName_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactAlternativeTelephone_1(final String contactAlternativeTelephone_1) {
		this.contactAlternativeTelephone_1 = contactAlternativeTelephone_1;
	}
	
	@java.lang.SuppressWarnings("all")
	public void setContactAlternativeEmail_1(final String contactAlternativeEmail_1) {
		this.contactAlternativeEmail_1 = contactAlternativeEmail_1;
	}
	
	
	@java.lang.SuppressWarnings("all")
	public void setYearsList(final TreeSet<String> yearsList) {
		this.yearsList = yearsList;
	}
}