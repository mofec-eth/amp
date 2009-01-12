package org.digijava.module.aim.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;
import org.digijava.module.aim.dbentity.AmpActivity;
import org.digijava.module.aim.dbentity.AmpFunding;
import org.digijava.module.aim.util.ActivityUtil;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;
import org.digijava.module.categorymanager.util.CategoryConstants;
import org.digijava.module.categorymanager.util.CategoryManagerUtil;

/**
 * Activity helper bean.
 * Used to store activity data in more user friendly way.
 * Also suports converting Activity to XML for Ajax like use or any other use.
 * @author Irakli Kbiashvili
 *
 */
public class ActivityItem implements Comparable<ActivityItem>{

	public static final String TAG_NAME = "activity";

	public static final String DONORS_TAG_NAME = "donors";

	public static final String PARAM_STATUS = "status";

	public static final String PARAM_NAME = "name";

	public static final String PARAM_ID = "id";

	public static final String PARAM_PROPOSED_AMOUNT = "proposedAmount";

	public static final String PARAM_ACTUAL_AMOUNT = "actualAmount";

	public static final String PARAM_PLANNED_AMOUNT = "plannedAmount";

    public static final String PARAM_ACTUAL_DISB_AMOUNT = "actualDisbAmount";

	public static final String PARAM_DATE = "date";

	private Long id;

	private String name;

	private String status;

	private String proposedAmount = "error";

	private String plannedAmount = "error";

	private String actualAmount = "error";

    private String actualDisbAmount = "error";

	private String startDate;
	//end date not used
	private String endDate;

	private List<LabelValueBean> donors;

	private ActivityUtil.ActivityAmounts amounts;
    // percent of specific program assigned
    private Long percent;
    // used only for program percent applying
    private AmpActivity act;

    public AmpActivity getAct() {
        return act;
    }

    public void setAct(AmpActivity act) {
        this.act = act;
    }


	/**
	 * Default constructor.
	 * Not very usful.
	 */
	public ActivityItem() {

	}

    /**
	 * Constructs Helper bean from db entity and the percent of specific program
	 * @param act AmpActivity bean
     * @param percent Long percent of specific program
	 * @see ActivityItem#ActivityItem(AmpActivity, DateFormat)
     * @see ActivityItem#ActivityItem(AmpActivity)
	 */

    public ActivityItem(AmpActivity act,Long percent) {
        this.act=act;
        this.percent=percent;

	}

	/**
	 * Constructs Helper bean from db entity.
	 * See next constructor, which is used in this one.
	 * @param entity AmpActivity bean
	 * @see ActivityItem#ActivityItem(AmpActivity, DateFormat)
	 */
	public ActivityItem(AmpActivity entity) throws Exception{
		this(entity,new SimpleDateFormat(Constants.CALENDAR_DATE_FORMAT),"USD",null);
	}

    public ActivityItem(AmpActivity entity,String curenncyCode,Long percent) throws Exception{
        this(entity,new SimpleDateFormat(Constants.CALENDAR_DATE_FORMAT),curenncyCode,percent);
	}

	/**
	 * Constructs helper bean from db entity.
	 * Also DateFormat is passed to format dates
	 * @param entity AmpActivity db entity to construct helper from
	 * @param frmt date formatter
	 */
	public ActivityItem(AmpActivity entity,DateFormat frmt,String curenncyCode,Long percent) throws Exception {
		if (entity != null) {
			AmpCategoryValue statusValue = CategoryManagerUtil.getAmpCategoryValueFromListByKey(CategoryConstants.ACTIVITY_STATUS_KEY, entity.getCategories());
//			statusValue.setValue("fake status");
			if (statusValue != null)
				status		= statusValue.getValue();
			id = entity.getAmpActivityId();
			name = entity.getName();
			name = name.replaceAll("&","&amp;");
			name = name.replaceAll("\"","&quot;");
			try {
				amounts = ActivityUtil.getActivityAmmountIn(entity,curenncyCode,percent);
				proposedAmount=amounts.proposedAmout();
				actualAmount=amounts.actualAmount();
                actualDisbAmount=amounts.actualDisbAmount();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (entity.getActualStartDate() != null){
				startDate = frmt.format(entity.getActualStartDate());
			}else{
                            if(entity.getProposedStartDate()!=null){
				startDate = frmt.format(entity.getProposedStartDate());
                            }
                            else{
                                startDate="";
                            }
			}
			donors = getDonorsFromFundings(entity.getFunding());
		}
	}

	/**
	 * Generates XML.
	 * Result will include some properties as attributes,
	 * and donors list as child nodes.
	 * @return String representing XML of the Activity.
	 */
	public String getXml() {
		String result = "";
		if (id != null) {
			result += "<" + TAG_NAME;
			result += " " + PARAM_ID + "=\"" + id.toString()+ "\"";
			result += " " + PARAM_STATUS + "=\"" + getStatusValue() + "\"";
			result += " " + PARAM_NAME + "=\"" + getNameValue() + "\"";
			result += " " + PARAM_PROPOSED_AMOUNT + "=\"" + getProposedAmount() + "\"";
			result += " " + PARAM_ACTUAL_AMOUNT + "=\"" + getActualAmount() + "\"";
			result += " " + PARAM_ACTUAL_DISB_AMOUNT + "=\"" + getActualDisbAmount()+ "\"";
			result += " " + PARAM_DATE + "=\"" + getStartDate() + "\"";
			result += ">";
			result += "<" + DONORS_TAG_NAME + ">";
			result += getDonorsList();
			result += "</" + DONORS_TAG_NAME + ">";
			result += "</" + TAG_NAME + ">";
		}
		return result;
	}

	/**
	 * Constructs donors list for XML
	 * @return String representing donors tags with attributes
	 */
	private String getDonorsList(){
		String result = "";
		if (donors != null && donors.size()>0){
			for (LabelValueBean lvb : donors) {
				result += "<donor id=\""+lvb.getValue()+"\" name=\""+lvb.getLabel()+"\"/>";
			}
		}
		return result;
	}

	/**
	 * Converts donor organizations Set from AmpActivity to list of LabelValueBeans
	 * Organizations name is label, and org Id is value of the bean.
	 * @param donors Set of funding orgs from AmpActivity
	 * @return list of LabelValueBean objects
	 */
	private List<LabelValueBean> getDonorsFromFundings(Set<AmpFunding> donors){
		List<LabelValueBean> result = new ArrayList<LabelValueBean>();
		if (donors != null && donors.size() > 0){
			for (AmpFunding donor: donors) {
				String donorName = donor.getAmpDonorOrgId().getName();
				Long donorId = donor.getAmpDonorOrgId().getAmpOrgId();
				LabelValueBean lvb = new LabelValueBean(donorName,donorId.toString());
				result.add(lvb);
			}
		}
		return result;
	}

	private String getStatusValue() {
		return getStatus();
	}

	private String getNameValue() {
		return getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}
    public String getActualDisbAmount() {
        return actualDisbAmount;
    }

    public void setActualDisbAmount(String actualDisbAmount) {
        this.actualDisbAmount = actualDisbAmount;
    }

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getPlannedAmount() {
		return plannedAmount;
	}

	public void setPlannedAmount(String plannedAmount) {
		this.plannedAmount = plannedAmount;
	}

	public String getProposedAmount() {
		return proposedAmount;
	}

	public void setProposedAmount(String proposedAmount) {
		this.proposedAmount = proposedAmount;
	}

	public ActivityUtil.ActivityAmounts getAmounts() {
		return amounts;
	}

	public void setAmounts(ActivityUtil.ActivityAmounts amounts) {
		this.amounts = amounts;
	}
      public Long getPercent() {
        return percent;
    }

    public void setPercent(Long percent) {
        this.percent = percent;
    }

    public int compareTo(ActivityItem o) {
        String myName=(this.name==null)?"":this.name;
		String hisName=(o.getName()==null)?"":o.getName();
		return (myName.trim().toLowerCase().compareTo(hisName.trim().toLowerCase()));
    }

}
