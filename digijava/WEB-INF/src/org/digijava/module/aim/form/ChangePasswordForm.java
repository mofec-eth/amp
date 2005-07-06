package org.digijava.module.aim.form;

import org.apache.struts.action.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.validator.ValidatorForm;

public class ChangePasswordForm extends ValidatorForm {

		  private String userId = null;
		  private String oldPassword = null;
		  private String newPassword = null;
		  private String conformNewPassword = null;

		  public String getUserId() {
					 return this.userId;
		  }

		  public void setUserId(String userId) {
					 this.userId = userId;
		  }

		  public String getOldPassword() {
					 return this.oldPassword;
		  }

		  public void setOldPassword(String oldPassword) {
					 this.oldPassword = oldPassword;
		  }

		  public String getNewPassword() {
					 return this.newPassword;
		  }

		  public void setNewPassword(String newPassword) {
					 this.newPassword = newPassword;
		  }		  

		  public String getConfirmNewPassword() {
					 return this.conformNewPassword;
		  }

		  public void setConfirmNewPassword(String conformNewPassword) {
					 this.conformNewPassword = conformNewPassword;
		  }		  


		 public ActionErrors validate( ActionMapping mapping,HttpServletRequest request) {
					
					return super.validate(mapping, request);					
					
					/*
					ActionErrors errors =  super.validate(mapping, request);

					if ( (this.getNewPassword() != null) &&
										 this.getNewPassword().trim().length() != 0) {
							  if (! (this.getNewPassword().equals(this.getConfirmNewPassword()))) {
										 ActionError error = new ActionError(
															  "error.registration.NoPasswordMatch");
										 errors.add(null, error);
							  }
					}

					return errors.isEmpty() ? null : errors;					

					*/

		 }
}
