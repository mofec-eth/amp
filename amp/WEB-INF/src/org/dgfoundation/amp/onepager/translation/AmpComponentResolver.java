/**
 * Copyright (c) 2010 Development Gateway (www.developmentgateway.org)
 *
 */
package org.dgfoundation.amp.onepager.translation;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupException;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.markup.parser.filter.WicketTagIdentifier;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author aartimon@dginternational.org
 * since Oct 6, 2010
 */
public class AmpComponentResolver implements IComponentResolver {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory
			.getLogger(AmpComponentResolver.class);
	public static final String TRANSLATION_COMPONENT_PREFIX = "translation_";
	public final static MetaDataKey TRANSLATION_KEY = new MetaDataKey(){
		private static final long serialVersionUID = 1L;
	};
	
	
	static {
		// register "wicket:trn"
		WicketTagIdentifier.registerWellKnownTagName("trn");
	}

	/**
	 * This method is called by the component resolver when a "wicket:trn" tag is encountered.
	 * Method will attach to the markup a TrnLabel component.
	 */
	@Override
	public boolean resolve(MarkupContainer container,
			MarkupStream markupStream, ComponentTag tag) {

		if (tag.getName().compareTo("trn") == 0) {
			String messageKey = tag.getAttributes().getString("key");
			if ((messageKey == null) || (messageKey.trim().length() == 0)) {
				throw new MarkupException(
						"Wrong format of <wicket:trn key='xxx'>: attribute 'key' is missing");
			}
			// generate an id for our component
			final String id = TRANSLATION_COMPONENT_PREFIX + container.getPage().getAutoIndex();
			// get the label's initial value, stored inside the tag
			String value = markupStream.get(markupStream.getCurrentIndex() + 1).toString();
			
			TrnLabel label = new TrnLabel(id, value);
			container.autoAdd(label, markupStream);
			
			// Yes, we handled the tag
			return true;
		}
		
		// We were not able to handle the tag
		return false;
	}
}
