package org.digijava.module.aim.action;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.persistence.WorkerException;
import org.digijava.kernel.translator.TranslatorWorker;
import org.digijava.module.aim.dbentity.AmpContactProperty;
import org.digijava.module.aim.dbentity.AmpOrgRecipient;
import org.digijava.module.aim.dbentity.AmpOrgStaffInformation;
import org.digijava.module.aim.dbentity.AmpOrganisation;
import org.digijava.module.aim.dbentity.AmpOrganisationContact;
import org.digijava.module.aim.dbentity.AmpOrganizationBudgetInformation;
import org.digijava.module.aim.form.AddOrgForm;
import org.digijava.module.aim.helper.ActivitySector;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.helper.Location;
import org.digijava.module.aim.util.DbUtil;
import org.digijava.module.aim.util.DynLocationManagerUtil;
import org.digijava.module.aim.util.FiscalCalendarUtil;
import org.digijava.module.categorymanager.util.CategoryManagerUtil;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ExportNGOToPdf extends Action {
	
	private static final com.lowagie.text.Font plainFont = new com.lowagie.text.Font(com.lowagie.text.Font.COURIER, 11,Font.NORMAL);
	private static final com.lowagie.text.Font titleFont = new com.lowagie.text.Font(com.lowagie.text.Font.COURIER, 11,Font.BOLD);
	private static final com.lowagie.text.Font headingsFont = new com.lowagie.text.Font(com.lowagie.text.Font.COURIER, 12,Font.BOLD, Color.BLUE);
	private static final com.lowagie.text.Font contactTableHeaderFont = new com.lowagie.text.Font(com.lowagie.text.Font.COURIER, 11,Font.BOLD,Color.WHITE);
	private final static char BULLETCHAR = '\u2022';
    private final static char NEWLINECHAR = '\n';
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		AddOrgForm editForm = (AddOrgForm) form;
		
		com.lowagie.text.Font headerFont = new com.lowagie.text.Font(com.lowagie.text.Font.COURIER, 11, Font.BOLD, new Color(255, 255, 255));
		Paragraph p1=null;
		String columnName="";
		String columnVal="";
		
		response.setContentType("application/pdf");
		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		document.open();
		PdfPTable mainLayout = new PdfPTable(4);
		mainLayout.setWidthPercentage(100);
		//mainLayout.setWidths(new float[]{1f,1f,1f,1f});
		// title
		PdfPCell titleCell=new PdfPCell();
		p1=new Paragraph(TranslatorWorker.translateText("Organization Details",request),headerFont);
		p1.setAlignment(Element.ALIGN_CENTER);
		titleCell.addElement(p1);
		titleCell.setColspan(4);
		titleCell.setBackgroundColor(new Color(0,102,153));
		mainLayout.addCell(titleCell);
		
		//name
		columnName= TranslatorWorker.translateText("Organization Name", request);
		createGeneralInfoRow(mainLayout,columnName,editForm.getName(),null);
		
		columnName= TranslatorWorker.translateText("Organization Acronym", request);
		createGeneralInfoRow(mainLayout,columnName,editForm.getAcronym(),null);
		
		columnName= TranslatorWorker.translateText("Organization Type", request);
		createGeneralInfoRow(mainLayout,columnName,(DbUtil.getAmpOrgType(editForm.getAmpOrgTypeId())).getOrgType(),null);
		
		columnName= TranslatorWorker.translateText("Organization Group", request);
		 if (editForm.getAmpOrgGrpId()!=null && editForm.getAmpOrgGrpId().intValue()>0){
			 createGeneralInfoRow(mainLayout,columnName, (DbUtil.getAmpOrgGroup(editForm.getAmpOrgGrpId()).getOrgGrpName()),null);
		 }else{
			 createGeneralInfoRow(mainLayout,columnName,"",null);
		 }
		 
		columnName= TranslatorWorker.translateText("Funding Org Id", request);
		createGeneralInfoRow(mainLayout,columnName,editForm.getFundingorgid(),null);
		buildEmptyCell(mainLayout, 2);
		
		columnName= TranslatorWorker.translateText("Organization Primary Purpose", request);
		createGeneralInfoRow(mainLayout,columnName,editForm.getOrgPrimaryPurpose(),2);
		buildEmptyCell(mainLayout, 2);
		
		/**
		 * General Information
		 */
		titleCell=new PdfPCell();
		p1=new Paragraph(TranslatorWorker.translateText("General Information",request),headingsFont);
		titleCell.addElement(p1);
		titleCell.setColspan(4);
		titleCell.setBorder(0);
		mainLayout.addCell(titleCell);	
		
		
		buildGeneralInfoTable(request, editForm, mainLayout);	
		
		/**
		 * Staff Information
		 */
		titleCell=new PdfPCell();
		p1=new Paragraph(TranslatorWorker.translateText("Staff Information",request),headingsFont);
		titleCell.addElement(p1);
		titleCell.setColspan(4);
		titleCell.setBorder(0);
		mainLayout.addCell(titleCell);
		
		buildStaffInfoTable(request, editForm,mainLayout);		
		
		/**
		 * budget information
		 */
		titleCell=new PdfPCell();
		p1=new Paragraph(TranslatorWorker.translateText("Budget Information",request),headingsFont);
		titleCell.addElement(p1);
		titleCell.setColspan(4);
		titleCell.setBorder(0);
		mainLayout.addCell(titleCell);
		
		buildBudgetInfo(request, editForm, mainLayout);
		
		/**
		 * contact information
		 */
		titleCell=new PdfPCell();
		p1=new Paragraph(TranslatorWorker.translateText("Contact Information",request),headingsFont);
		titleCell.addElement(p1);
		titleCell.setColspan(4);
		titleCell.setBorder(0);
		mainLayout.addCell(titleCell);
		
		buildContactsInfoTable(request, editForm, mainLayout);
		
		document.add(mainLayout); //put pdfTable in document
		document.close();		
		response.setContentLength(baos.size());
		ServletOutputStream out = response.getOutputStream();
		baos.writeTo(out);
		out.flush();
		return null;
	}

	private void buildContactsInfoTable(HttpServletRequest request,AddOrgForm editForm, PdfPTable mainLayout) throws WorkerException {
		String columnName;
		PdfPCell contactInfoCell=new PdfPCell();
		contactInfoCell.setColspan(4);
		PdfPTable contactInfoTable = new PdfPTable(7);
		contactInfoTable.setWidthPercentage(100);
		
		columnName = TranslatorWorker.translateText("LAST NAME", request);
		generateContactsTableHeader(columnName, contactInfoTable);
		
		columnName = TranslatorWorker.translateText("FIRST NAME", request);
		generateContactsTableHeader(columnName, contactInfoTable);
		
		columnName = TranslatorWorker.translateText("EMAIL", request);
		generateContactsTableHeader(columnName, contactInfoTable);
		
		columnName = TranslatorWorker.translateText("TELEPHONE", request);
		generateContactsTableHeader(columnName, contactInfoTable);
		
		columnName = TranslatorWorker.translateText("FAX", request);
		generateContactsTableHeader(columnName, contactInfoTable);
		
		columnName = TranslatorWorker.translateText("TITLE", request);
		generateContactsTableHeader(columnName, contactInfoTable);
		
		columnName = TranslatorWorker.translateText("PRIMARY", request);
		generateContactsTableHeader(columnName, contactInfoTable);
		
		
		String primary= TranslatorWorker.translateText("yes", request);
		String nonPrimary= TranslatorWorker.translateText("no", request);
		if(editForm.getOrgContacts() !=null){
			Iterator<AmpOrganisationContact> contactInfoIter = editForm.getOrgContacts().iterator();
			while (contactInfoIter.hasNext()) {
				AmpOrganisationContact orgContact = contactInfoIter.next();
				generateTableCell(orgContact.getContact().getLastname(), contactInfoTable);
				generateTableCell(orgContact.getContact().getName(), contactInfoTable);		  		    
			    
			    String emails="";			
			    String phones="";
			    String faxes="";
			    String currentRecord = null;
			    for (AmpContactProperty property : orgContact.getContact().getProperties()) {
					if(property.getName().equals(Constants.CONTACT_PROPERTY_NAME_EMAIL)){
						currentRecord = property.getValue(); 
						emails+= BULLETCHAR + currentRecord + ";\n";
					}else if(property.getName().equals(Constants.CONTACT_PROPERTY_NAME_PHONE)){
						currentRecord = TranslatorWorker.translateText(property.getPhoneCategory(), request)+property.getActualPhoneNumber(); 
						phones+= BULLETCHAR + currentRecord + ";\n";
					}else{
						currentRecord = property.getValue(); 
						faxes+=BULLETCHAR + currentRecord + ";\n";
					}
				}
			    
			    generateTableCell(emails, contactInfoTable);
			    generateTableCell(phones, contactInfoTable);
			    generateTableCell(faxes, contactInfoTable);
			    
			    String title="";
			    if(orgContact.getContact().getTitle()!=null){
			        title=orgContact.getContact().getTitle().getValue();
			    }
			    generateTableCell(title, contactInfoTable);
			    
			    String isprimaryContact=nonPrimary;
			    if(orgContact.getPrimaryContact()!=null&&orgContact.getPrimaryContact()){
			        isprimaryContact=primary;
			    }
			    generateTableCell(isprimaryContact, contactInfoTable);
			}
		}
		
		
		
		contactInfoCell.addElement(contactInfoTable);
		mainLayout.addCell(contactInfoCell);
	}

	private void generateContactsTableHeader(String columnName,PdfPTable contactInfoTable) {
		Paragraph p1;
		PdfPCell cell1=new PdfPCell();
		p1=new Paragraph(columnName,contactTableHeaderFont);
		p1.setAlignment(Element.ALIGN_LEFT);
		cell1.addElement(p1);
		cell1.setBorder(0);
		cell1.setBackgroundColor(new Color(34, 46, 93));
		contactInfoTable.addCell(cell1);
	}

	private void buildBudgetInfo(HttpServletRequest request,AddOrgForm editForm, PdfPTable mainLayout) throws WorkerException {
		String columnName;
		PdfPCell budgetInfoCell=new PdfPCell();
		budgetInfoCell.setColspan(4);
		PdfPTable budgetInfoTable = new PdfPTable(5);
		budgetInfoTable.setWidthPercentage(100);
		
		columnName= TranslatorWorker.translateText("Year", request);
		generateTableHeaders(columnName, budgetInfoTable);
		
		columnName= TranslatorWorker.translateText("Type of Organization", request);
		generateTableHeaders(columnName, budgetInfoTable);
		
		columnName= TranslatorWorker.translateText("Organization(s)", request);
		generateTableHeaders(columnName, budgetInfoTable);
		
		columnName= TranslatorWorker.translateText("Amount", request);
		generateTableHeaders(columnName, budgetInfoTable);
		
		columnName= TranslatorWorker.translateText("Currency", request);
		generateTableHeaders(columnName, budgetInfoTable);
		
		if(editForm.getOrgInfos()!= null ){
			Iterator<AmpOrganizationBudgetInformation> budgetInfoIter = editForm.getOrgInfos().iterator();
			while (budgetInfoIter.hasNext()) {
			    AmpOrganizationBudgetInformation budgetInfo = budgetInfoIter.next();
			    generateTableCell(budgetInfo.getYear().toString(), budgetInfoTable);
			    generateTableCell(TranslatorWorker.translateText(budgetInfo.getType().getValue(), request), budgetInfoTable);
			   
			    String organizations="";
			    String longestOrgRecord = "";
				String currentRecord = null;
			    if(budgetInfo.getOrganizations()!=null){
			        for(AmpOrganisation organisation:budgetInfo.getOrganizations() ){
			        	currentRecord = BULLETCHAR+organisation.getName();
			            organizations+= currentRecord + NEWLINECHAR;
			            if(currentRecord.length() > longestOrgRecord.length()){
			            	longestOrgRecord = currentRecord;
				        }
			        }
			    }
			    generateTableCell(organizations, budgetInfoTable);
			    generateTableCell(budgetInfo.getAmount().toString(), budgetInfoTable);
			    generateTableCell(budgetInfo.getCurrency().getCurrencyCode(), budgetInfoTable);
			}
		}
		
		
		budgetInfoCell.addElement(budgetInfoTable);
		mainLayout.addCell(budgetInfoCell);
	}

	private void buildStaffInfoTable(HttpServletRequest request,AddOrgForm editForm, PdfPTable mainLayout) throws WorkerException {
		String columnName;
		PdfPCell StaffInfoCell=new PdfPCell();
		StaffInfoCell.setColspan(4);
		PdfPTable StaffInfoTable = new PdfPTable(3);
		StaffInfoTable.setWidthPercentage(100);
		
		columnName= TranslatorWorker.translateText("Year", request);
		generateTableHeaders(columnName, StaffInfoTable);
		
		columnName= TranslatorWorker.translateText("Type Of Staff", request);
		generateTableHeaders(columnName, StaffInfoTable);
		
		columnName= TranslatorWorker.translateText("Number Of Staff", request);
		generateTableHeaders(columnName, StaffInfoTable);
		if(editForm.getStaff()!=null){
			Iterator<AmpOrgStaffInformation> staffInfoIter = editForm.getStaff().iterator();
			while (staffInfoIter.hasNext()) {
			    AmpOrgStaffInformation staffInfo = staffInfoIter.next();
			    generateTableCell(staffInfo.getYear().toString(), StaffInfoTable);
			    generateTableCell(TranslatorWorker.translateText(staffInfo.getType().getValue(),request), StaffInfoTable);
			    generateTableCell(staffInfo.getStaffNumber().toString(), StaffInfoTable);
			}
		}				
		
		StaffInfoCell.addElement(StaffInfoTable);
		mainLayout.addCell(StaffInfoCell);
	}

	private void generateTableHeaders(String columnName, PdfPTable infoTable) {
		Paragraph p1;
		PdfPCell cell1=new PdfPCell();
		p1=new Paragraph(columnName,titleFont);
		p1.setAlignment(Element.ALIGN_LEFT);
		cell1.addElement(p1);
		cell1.setBorder(0);
		infoTable.addCell(cell1);
	}
	
	private void generateTableCell(String columnValue, PdfPTable infoTable) {
		Paragraph p1;
		PdfPCell cell1=new PdfPCell();
		p1=new Paragraph(columnValue,plainFont);
		p1.setAlignment(Element.ALIGN_LEFT);
		cell1.addElement(p1);
		cell1.setBorder(0);
		infoTable.addCell(cell1);
	}

	private void buildGeneralInfoTable(HttpServletRequest request,AddOrgForm editForm, PdfPTable mainLayout) throws WorkerException {
		String columnName;
		PdfPCell generalInformationCell=new PdfPCell();
		generalInformationCell.setColspan(4);
		PdfPTable generalInformationTable = new PdfPTable(4);
		generalInformationTable.setWidthPercentage(100);
		
		
		columnName= TranslatorWorker.translateText("Registration Number in MinPlan", request);
		createGeneralInfoRow(generalInformationTable,columnName,editForm.getRegNumbMinPlan(),null);
		
		columnName= TranslatorWorker.translateText("Legal Personality Number", request);
		createGeneralInfoRow(generalInformationTable,columnName,editForm.getLegalPersonNum(),null);
		
		columnName= TranslatorWorker.translateText("Registration Date in MinPlan", request);
		createGeneralInfoRow(generalInformationTable,columnName,editForm.getMinPlanRegDate(),null);
		
		columnName= TranslatorWorker.translateText("Legal Personality Registration Date in the country of origin", request);
		createGeneralInfoRow(generalInformationTable,columnName,editForm.getLegalPersonRegDate(),null);
		
		columnName= TranslatorWorker.translateText("Operation approval  date in the country of origin", request);
		createGeneralInfoRow(generalInformationTable,columnName,editForm.getOperFuncApprDate(),null);
		
		columnName= TranslatorWorker.translateText("Registration date of Line Ministry", request);
		createGeneralInfoRow(generalInformationTable,columnName,editForm.getLineMinRegDate(),null);
		
		columnName= TranslatorWorker.translateText("Receipt of legal personality act/form in DRC", request);
		createGeneralInfoRow(generalInformationTable,columnName,editForm.getReceiptLegPersonalityAct(),null);
		
		columnName= TranslatorWorker.translateText("Recipients", request);		
		List<AmpOrgRecipient> orgs=editForm.getRecipients();
		String organizations="";
		String currentRecord = null;
		if(orgs!=null){
		    Iterator<AmpOrgRecipient> orgIter=orgs.iterator();
		    while(orgIter.hasNext()){
		        AmpOrgRecipient organisation=orgIter.next();
		        currentRecord= BULLETCHAR+organisation.getOrganization().getName()+" ("+organisation.getDescription()+")";
		        organizations+= currentRecord+NEWLINECHAR;

		    }
		}		
		createGeneralInfoRow(generalInformationTable,columnName,organizations,null);
		
		columnName= TranslatorWorker.translateText("Fiscal Calendar", request);
		 if (editForm.getFiscalCalId() != null&&editForm.getFiscalCalId()!=-1){
			 createGeneralInfoRow(generalInformationTable,columnName, FiscalCalendarUtil.getAmpFiscalCalendar(editForm.getFiscalCalId()).getName(),null);
		 }else{
			 createGeneralInfoRow(generalInformationTable,columnName,"",null);
		 }
		 
		columnName= TranslatorWorker.translateText("Sector Prefernces", request);
		Collection<ActivitySector> activitySectors=editForm.getSectors();
		String sectors="";
		currentRecord = null;
		if(activitySectors!=null){
		    Iterator<ActivitySector> activitySectorsIter=activitySectors.iterator();
		    while(activitySectorsIter.hasNext()){
		        ActivitySector activitySector=activitySectorsIter.next();
		        currentRecord = BULLETCHAR+activitySector.getSectorName(); 
		        sectors+= currentRecord + NEWLINECHAR;
		    }
		}
		createGeneralInfoRow(generalInformationTable,columnName,sectors,null);
		
		buildEmptyCell(generalInformationTable, 2);
		
		columnName= TranslatorWorker.translateText("Country of Origin", request);
		 if (editForm.getCountryId() != null&&editForm.getCountryId()!=-1){
			 createGeneralInfoRow(generalInformationTable,columnName, DynLocationManagerUtil.getLocation(editForm.getCountryId(), false).getName(),null);
		 }else{
			 createGeneralInfoRow(generalInformationTable,columnName,"",null);
		 }
		 
		columnName= TranslatorWorker.translateText("Organization website", request);
		createGeneralInfoRow(generalInformationTable,columnName,editForm.getOrgUrl(),null);
		
		columnName= TranslatorWorker.translateText("Tax Number", request);
		createGeneralInfoRow(generalInformationTable,columnName,editForm.getTaxNumber(),null);
		
		columnName= TranslatorWorker.translateText("Organization Headquarters Address", request);
		createGeneralInfoRow(generalInformationTable,columnName,editForm.getAddress(),null);
		
		columnName= TranslatorWorker.translateText("Organization Intervention Level", request);
		if (editForm.getImplemLocationLevel() != null&&editForm.getImplemLocationLevel()!=0){
			 createGeneralInfoRow(generalInformationTable,columnName, CategoryManagerUtil.getAmpCategoryValueFromDb(editForm.getImplemLocationLevel()).getValue(),null);
		 }else{
			 createGeneralInfoRow(generalInformationTable,columnName,"",null);
		 }
		
		columnName= TranslatorWorker.translateText("Organization Address Abroad(Internation NGO)", request);
		createGeneralInfoRow(generalInformationTable,columnName,editForm.getAddressAbroad(),null);
		
		columnName= TranslatorWorker.translateText("Organization Intervention Location", request);
		Collection<Location> selectedLocations=editForm.getSelectedLocs();
		String locations="";
		currentRecord = null;	
		if(selectedLocations!=null){
		    Iterator<Location> locationIter=selectedLocations.iterator();
		    while(locationIter.hasNext()){
		        Location location=locationIter.next();
		        currentRecord = BULLETCHAR+location.getAmpCVLocation().getName() ; 
		        locations+= currentRecord +NEWLINECHAR;		        
		    }
		}
		createGeneralInfoRow(generalInformationTable,columnName,locations,null);
		
		generalInformationCell.addElement(generalInformationTable);		
		mainLayout.addCell(generalInformationCell);
	}
	
	/**
	 * Used to create simple two columned row
	 * @param mainLayout
	 * @param columnName
	 * @param value
	 */
	private void createGeneralInfoRow(PdfPTable mainLayout,String columnName,String value,Integer valueColspan){
		PdfPCell cell1=new PdfPCell();
		Paragraph p1=new Paragraph(columnName,titleFont);
		p1.setAlignment(Element.ALIGN_LEFT);
		cell1.addElement(p1);
		cell1.setBorder(0);
		mainLayout.addCell(cell1);
		
		PdfPCell cell2=new PdfPCell();
		p1=new Paragraph(value,plainFont);
		p1.setAlignment(Element.ALIGN_LEFT);
		cell2.addElement(p1);
		cell2.setBorder(0);
		if(valueColspan!=null){
			cell2.setColspan(valueColspan);
		}
		mainLayout.addCell(cell2);
	}
	
	private void buildEmptyCell(PdfPTable table, int colspan) {
		PdfPCell lineCell;
		lineCell=new PdfPCell(new Paragraph(0));
		lineCell.setBorder(0);
		lineCell.setColspan(colspan);
		table.addCell(lineCell);
	}
}
