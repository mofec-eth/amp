/**
 * Copyright (c) 2010 Development Gateway (www.developmentgateway.org)
 *
 */
package org.dgfoundation.amp.onepager.components.features.items;

import java.util.Set;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.dgfoundation.amp.onepager.components.features.AmpFeaturePanel;
import org.dgfoundation.amp.onepager.components.features.subsections.AmpRegionalTransactionsSubsectionFeature;
import org.dgfoundation.amp.onepager.components.fields.AmpLabelFieldPanel;
import org.digijava.module.aim.dbentity.AmpActivity;
import org.digijava.module.aim.dbentity.AmpCategoryValueLocations;
import org.digijava.module.aim.dbentity.AmpFunding;
import org.digijava.module.aim.dbentity.AmpRegionalFunding;
import org.digijava.module.aim.helper.Constants;

/**
 * Represents visually one funding item {@link AmpFunding} The model here is
 * represented by a {@link CompoundPropertyModel} around an {@link AmpFunding}
 * item
 * 
 * @author mpostelnicu@dgateway.org since Nov 3, 2010
 */
public class AmpRegionalFundingItemFeaturePanel extends AmpFeaturePanel<Set<AmpRegionalFunding>> {
	

	
	/**
	 * @param id
	 * @param fmName
	 * @param am 
	 * @throws Exception
	 */
	public AmpRegionalFundingItemFeaturePanel(String id, String fmName,
			IModel<AmpActivity> am, IModel<Set<AmpRegionalFunding>> fundingModel, IModel<AmpCategoryValueLocations> cvLocationModel) throws Exception {
		super(id, fundingModel, fmName, true);
		AmpLabelFieldPanel<AmpCategoryValueLocations> regionLocation = new AmpLabelFieldPanel<AmpCategoryValueLocations>(
				"region", cvLocationModel, "Region", true);
		add(regionLocation);
		
		AmpRegionalTransactionsSubsectionFeature commitments = new AmpRegionalTransactionsSubsectionFeature(
				"commitments", am,fundingModel,"Commitments",Constants.COMMITMENT,cvLocationModel);
		add(commitments);
		
		AmpRegionalTransactionsSubsectionFeature disbursements = new AmpRegionalTransactionsSubsectionFeature(
				"disbursements", am,fundingModel,"Disbursements",Constants.DISBURSEMENT,cvLocationModel);
		add(disbursements);
		
		AmpRegionalTransactionsSubsectionFeature expenditures = new AmpRegionalTransactionsSubsectionFeature(
				"expenditures", am,fundingModel,"Expenditures",Constants.EXPENDITURE,cvLocationModel);
		add(expenditures);
	
	}

}
