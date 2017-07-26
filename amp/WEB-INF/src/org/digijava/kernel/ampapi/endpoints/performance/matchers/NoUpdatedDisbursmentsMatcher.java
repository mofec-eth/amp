package org.digijava.kernel.ampapi.endpoints.performance.matchers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.digijava.kernel.ampapi.endpoints.performance.PerfomanceRuleManager;
import org.digijava.module.aim.dbentity.AmpActivityVersion;
import org.digijava.module.aim.dbentity.AmpFundingDetail;
import org.digijava.module.aim.dbentity.AmpPerformanceRule;
import org.digijava.module.aim.dbentity.AmpPerformanceRuleAttribute;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.util.ActivityUtil;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;

/**
 * 
 * @author Viorel Chihai
 *
 */
public class NoUpdatedDisbursmentsMatcher extends PerformanceRuleMatcher {
    
    public static final String ATTRIBUTE_MONTH = "month";

    public NoUpdatedDisbursmentsMatcher() {
        super("NoUpdatedDisbursments", "No updated disbursments in the last months");

        this.attributes = new ArrayList<>();
        this.attributes.add(new PerformanceRuleMatcherAttribute(ATTRIBUTE_MONTH, 
                "No updated disbursements in the last selected months",
                AmpPerformanceRuleAttribute.PerformanceRuleAttributeType.INTEGER));
    }

    @Override
    public boolean match(AmpPerformanceRule rule, AmpActivityVersion a) {
        List<AmpFundingDetail> activityDisbursements = ActivityUtil.getTransactionsWithType(a, Constants.DISBURSEMENT);
        
        PerfomanceRuleManager performanceRuleManager = PerfomanceRuleManager.getInstance();
        AmpPerformanceRuleAttribute monthAttribute = performanceRuleManager.getAttributeFromRule(rule, ATTRIBUTE_MONTH);
        
        if (monthAttribute != null && a.getApprovalDate() != null) {
            Calendar c = Calendar.getInstance();
           
            int month = Integer.parseInt(monthAttribute.getValue());
            c.add(Calendar.MONTH, -month);
            
            boolean hasActivityDisbursementsAfterSignatureDate = activityDisbursements.stream()
                    .anyMatch(disb -> disb.getTransactionDate().after(c.getTime()));
            
            return hasActivityDisbursementsAfterSignatureDate;
        }

        return false;
    }
}
