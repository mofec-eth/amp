/**
 * 
 */
package org.digijava.module.aim.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.module.aim.dbentity.AmpCategoryValueLocations;
import org.digijava.module.aim.form.SelectLocationForm;
import org.digijava.module.aim.helper.KeyValue;
import org.digijava.module.aim.util.DynLocationManagerUtil;
import org.digijava.module.aim.util.FeaturesUtil;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;
import org.digijava.module.categorymanager.util.CategoryConstants;
import org.digijava.module.categorymanager.util.CategoryManagerUtil;

/**
 * @author Mauricio Coria
 *
 */
public class DynLocationSelected extends SelectorSwitchAction {
	private static Logger logger = Logger.getLogger(DynLocationSelected.class);
	
	@Override
	public ActionForward switchStart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SelectLocationForm locationForm = (SelectLocationForm) form;

		
		if (!locationForm.isReseted()) {
			locationForm.setReseted(false);
			logger.info("calling reset");
			locationForm.reset(mapping, request);
		}


		Integer impLevelValue;
		AmpCategoryValue implLocValue	= CategoryManagerUtil.getAmpCategoryValueFromDb( locationForm.getImplemLocationLevel() );
		if ( implLocValue == null )
			implLocValue				= CategoryManagerUtil.getAmpCategoryValueFromDB( CategoryConstants.IMPLEMENTATION_LOCATION_COUNTRY );
		if (implLocValue != null) {
			impLevelValue	= new Integer ( implLocValue.getIndex() + 1 );
		}
		else
			impLevelValue 	= new Integer( 1 );

		locationForm.setImpLevelValue( impLevelValue );
		
		/* New Region Manager changes */
		if ( !"true".equals( request.getParameter("edit") ) ) {
			locationForm.setParentLocId(null);
			locationForm.getLocationByLayers().clear();
			locationForm.getSelectedLayers().clear();
		}
		String cIso= FeaturesUtil.getDefaultCountryIso();
		Long parentLocId			= locationForm.getParentLocId();
		
		Map<Integer, Collection<KeyValue>> locationByLayers		=  locationForm.getLocationByLayers();
		
		AmpCategoryValue implLevel		= CategoryManagerUtil.getAmpCategoryValueFromDb( locationForm.getImplemLevel() );
		if ( implLevel!=null && 
				CategoryManagerUtil.equalsCategoryValue(implLevel, CategoryConstants.IMPLEMENTATION_LEVEL_INTERNATIONAL) && 
				CategoryManagerUtil.equalsCategoryValue(implLocValue, CategoryConstants.IMPLEMENTATION_LOCATION_COUNTRY) )  {
			Collection<AmpCategoryValueLocations> countries = 
				DynLocationManagerUtil.getLocationsByLayer(CategoryConstants.IMPLEMENTATION_LOCATION_COUNTRY);
			if ( countries != null ) {
				Integer countryIndex	= null;
				ArrayList<KeyValue> countriesKV				= new ArrayList<KeyValue>();
				for (AmpCategoryValueLocations country: countries) {
					countryIndex	= (countryIndex==null)?country.getParentCategoryValue().getIndex():countryIndex;
					KeyValue countryKV						= new KeyValue(country.getId().toString() , country.getName() );
					countriesKV.add(countryKV);
				}
				locationByLayers.put(countryIndex, countriesKV);
			}
			return mapping.findForward("forward");
		}
        if(cIso!=null && parentLocId == null){ // Setting up the country
        	locationForm.setDefaultCountryIsSet(true);
        	AmpCategoryValueLocations defCountry		= DynLocationManagerUtil.getLocationByIso(cIso, CategoryConstants.IMPLEMENTATION_LOCATION_COUNTRY);
        	Integer countryLayerIndex						= defCountry.getParentCategoryValue().getIndex();
        	KeyValue countryKV							= new KeyValue(defCountry.getId().toString() , defCountry.getName() );
        	ArrayList<KeyValue> countries				= new ArrayList<KeyValue>();
        	countries.add(countryKV);
        	locationByLayers.put(countryLayerIndex, countries);
        	if ( countryLayerIndex < implLocValue.getIndex() ) {
        		parentLocId			= defCountry.getId();
        	}
        }
        
        while ( parentLocId != null ) {
        	AmpCategoryValueLocations parentLoc					= DynLocationManagerUtil.getLocation(parentLocId, true);
        	locationForm.getSelectedLayers().put(parentLoc.getParentCategoryValue().getIndex(), parentLoc.getId() );
        	
        	Iterator<Entry<Integer, Collection<KeyValue>>> entryIter		= locationByLayers.entrySet().iterator();
        	while ( entryIter.hasNext() ) {
        		/**
        		 * In case the user changes a higher layer location all layers below need to be cleared.
        		 */
        		if ( entryIter.next().getKey() > parentLoc.getParentCategoryValue().getIndex() ) {
        			entryIter.remove();
        		}
        	}
        	
        	parentLocId											= null;
        	Collection<AmpCategoryValueLocations> childrenLocs 	= parentLoc.getChildLocations();
        	if ( childrenLocs != null && childrenLocs.size() > 0 ) {
        		Integer currentLayer							= 
        			((AmpCategoryValueLocations)childrenLocs.toArray()[0]).getParentCategoryValue().getIndex();
        		
        		if ( currentLayer <= implLocValue.getIndex() ) { 
	        		ArrayList<KeyValue> childrenKV					= new ArrayList<KeyValue>(childrenLocs.size());
	        		for ( AmpCategoryValueLocations child : childrenLocs ) {
	        			boolean addLocationAllowed		= true;
	        			/**
	        			 * If we are on the last layer that needs to be shown, we have to 
	        			 * hide locations that are already selected
	        			 */
	        			if ( currentLayer == implLocValue.getIndex() && locationForm.getSelectedLocs() != null ) {
	        				for ( org.digijava.module.aim.helper.Location l: locationForm.getSelectedLocs() ) {
	        					if ( child.equals(l.getAmpCVLocation()) ) {
	        						addLocationAllowed	= false;
	        						break;
	        					}
	        				}
	        			}
	        			if ( addLocationAllowed )
	        				childrenKV.add( new KeyValue(child.getId().toString(), child.getName() ) );
	        		}
	        		
	        		locationByLayers.put(currentLayer, childrenKV);
	        		
	        		if ( childrenLocs.size() == 1 ) {
	        			AmpCategoryValueLocations newParent	= (AmpCategoryValueLocations)childrenLocs.toArray()[0];
	        			parentLocId							= newParent.getId();
	        		}
        		}
     
        	}
        }

		return mapping.findForward("forward");
	}
}
