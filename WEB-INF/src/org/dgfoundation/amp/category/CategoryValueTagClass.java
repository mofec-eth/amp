package org.dgfoundation.amp.category;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.digijava.module.aim.dbentity.AmpCategoryValue;
import org.digijava.module.aim.helper.CategoryManagerUtil;

public class CategoryValueTagClass extends TagSupport {
	private static Logger logger	= Logger.getLogger( CategoryValueTagClass.class );
	Long categoryValueId;
	String categoryKey;
	Long categoryIndex;
	
	public Long getCategoryValueId() {
		return categoryValueId;
	}
	public void setCategoryValueId(Long categoryValueId) {
		this.categoryValueId = categoryValueId;
	}
	public int doStartTag() {
		return SKIP_BODY;
	}
	
	
	public Long getCategoryIndex() {
		return categoryIndex;
	}
	public void setCategoryIndex(Long categoryIndex) {
		this.categoryIndex = categoryIndex;
	}
	public String getCategoryKey() {
		return categoryKey;
	}
	public void setCategoryKey(String categoryKey) {
		this.categoryKey = categoryKey;
	}
	public int doEndTag() throws JspException {
		HttpServletRequest request			= (HttpServletRequest) pageContext.getRequest();
		AmpCategoryValue ampCategoryValue	= null;
		JspWriter out						= pageContext.getOut();
			
		if (categoryValueId != null && categoryValueId.longValue() > 0 ) {
			ampCategoryValue	= CategoryManagerUtil.getAmpCategoryValueFromDb( categoryValueId );
			if (ampCategoryValue == null) {
				try {
					out.println("<font color='red'>No category value found with id " + categoryValueId + "</font>");
					logger.info("No AmpCategoryValue found with id " + categoryValueId);
				}
				catch (Exception E) {
					logger.error(E);
					E.printStackTrace();
				}
			}
		}
		if (categoryKey != null && categoryIndex != null) {
			ampCategoryValue	= CategoryManagerUtil.getAmpCategoryValueFromDb(categoryKey, categoryIndex);
			if (ampCategoryValue == null) {
				try {
					out.println("<font color='red'>No category value with index " + categoryIndex + " in category with key " + categoryKey + "</font>");
					logger.info("No category value with index " + categoryIndex + " in category with key " + categoryKey);
				}
				catch (Exception E) {
					logger.error(E);
					E.printStackTrace();
				}
			}
		}
		
		if (ampCategoryValue != null) {
			try{
				out.print( CategoryManagerUtil.translateAmpCategoryValue(ampCategoryValue, request, null) );
			}
			catch(Exception E){
				logger.info(E);
				E.printStackTrace();
			}
		}
		else {
			try{
				out.print( "<font color='red'>No category value could be selected</font>" );
			}
			catch(Exception E){
				logger.info(E);
				E.printStackTrace();
			}
		}
		
		return EVAL_PAGE;
	}
}
