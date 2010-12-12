/**
 * Copyright (c) 2010 Development Gateway (www.developmentgateway.org)
 *
*/
package org.dgfoundation.amp.onepager.components.features.subsections;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.dgfoundation.amp.onepager.components.features.tables.AmpDonorCommitmentsFormTableFeature;
import org.dgfoundation.amp.onepager.components.features.tables.AmpDonorExpendituresFormTableFeature;
import org.dgfoundation.amp.onepager.components.fields.AmpButtonField;
import org.digijava.module.aim.dbentity.AmpFunding;
import org.digijava.module.aim.dbentity.AmpFundingDetail;
import org.digijava.module.aim.helper.Constants;

/**
 * @author mpostelnicu@dgateway.org
 * since Nov 8, 2010
 */
public class AmpDonorExpendituresSubsectionFeature extends
		AmpSubsectionFeaturePanel<AmpFunding> {

	protected AmpDonorExpendituresFormTableFeature expTableFeature;
	
	/**
	 * @param id
	 * @param fmName
	 * @param model
	 * @throws Exception
	 */
	public AmpDonorExpendituresSubsectionFeature(String id,
			final IModel<AmpFunding> model, String fmName, int transactionType) throws Exception {
		super(id, fmName, model);
		expTableFeature = new AmpDonorExpendituresFormTableFeature("expTableFeature", model, "Expenditures Table");
		add(expTableFeature);
		
		AmpButtonField addCommit=new AmpButtonField("addExp","Add Expenditure") {
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				AmpFundingDetail fd= new AmpFundingDetail();
				fd.setAmpFundingId(model.getObject());
				fd.setTransactionAmount(0d);
				fd.setTransactionDate(new Date(System.currentTimeMillis()));
				fd.setTransactionType(Constants.EXPENDITURE);
				model.getObject().getFundingDetails().add(fd);
				expTableFeature.getList().removeAll();
				target.addComponent(expTableFeature);
			}
		};
		add(addCommit);
	}

}
