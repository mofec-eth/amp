/**
 * 
 */
package org.dgfoundation.amp.onepager.components;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.dgfoundation.amp.onepager.util.FMUtil;

/**
 * @author mihai
 *
 */
public class AmpFormComponentPanel<T> extends FormComponentPanel<T> implements
		AmpFMConfigurable {

	protected String fmName;
	protected AmpFMBehavior fmBehavior;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5847159396251223479L;

	public AmpFormComponentPanel (String id,String fmName, AmpFMBehavior fmBehavior) {
		super(id);
		setEnabled(FMUtil.isFMEnabled(this));
		setVisible(FMUtil.isFMVisible(this));
		this.fmName=fmName;
		this.fmBehavior=fmBehavior;
	}

	public AmpFormComponentPanel(String id,String fmName) {
		super(id);
		setEnabled(FMUtil.isFMEnabled(this));
		setVisible(FMUtil.isFMVisible(this));
		this.fmName=fmName;
		this.fmBehavior=AmpFMBehavior.FEATURE;
	}

	public AmpFormComponentPanel(String id, IModel<T> model,String fmName, AmpFMBehavior fmBehavior) {
		super(id, model);
		setEnabled(FMUtil.isFMEnabled(this));
		setVisible(FMUtil.isFMVisible(this));
		this.fmName=fmName;
		this.fmBehavior=fmBehavior;
	}
	
	public AmpFormComponentPanel(String id, IModel<T> model,String fmName) {
		super(id, model);
		setEnabled(FMUtil.isFMEnabled(this));
		setVisible(FMUtil.isFMVisible(this));
		this.fmName=fmName;
		this.fmBehavior=AmpFMBehavior.FEATURE;
	}

	/* (non-Javadoc)
	 * @see org.dgfoundation.amp.onepager.components.AmpFMConfigurable#getFMBehavior()
	 */
	@Override
	public AmpFMBehavior getFMBehavior() {
		return fmBehavior;
	}

	/* (non-Javadoc)
	 * @see org.dgfoundation.amp.onepager.components.AmpFMConfigurable#getFMName()
	 */
	@Override
	public String getFMName() {
		return fmName;
	}

}
