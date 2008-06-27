/**
 * AssignFieldPermissions.java
 * (c) 2007 Development Gateway Foundation
 */
package org.digijava.module.aim.action;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.module.aim.dbentity.AmpFieldsVisibility;
import org.digijava.module.aim.form.FieldPermissionsForm;
import org.digijava.module.gateperm.core.CompositePermission;
import org.digijava.module.gateperm.core.GatePermConst;
import org.digijava.module.gateperm.core.GatePermission;
import org.digijava.module.gateperm.core.Permission;
import org.digijava.module.gateperm.core.PermissionMap;
import org.digijava.module.gateperm.gates.OrgRoleGate;
import org.digijava.module.gateperm.gates.UserLevelGate;
import org.digijava.module.gateperm.util.PermissionUtil;

/**
 * AssignFieldPermissions.java
 * TODO description here
 * @author mihai
 * @package org.digijava.module.aim.action
 * @since 01.11.2007
 */
public class AssignFieldPermissions extends Action {

	
	private void initializeAndSaveGatePermission(Session session,CompositePermission cp,String permissionName,String parameter, Class gate,String readFlag,String editFlag) throws HibernateException {
		GatePermission baGate=new GatePermission(true);
		baGate.setName(permissionName);
		baGate.getGateParameters().add(parameter);
		baGate.setGateTypeName(gate.getName());
		HashSet baActions=new HashSet();
		if("on".equals(editFlag)) baActions.add(GatePermConst.Actions.EDIT);
		if("on".equals(readFlag)) baActions.add(GatePermConst.Actions.VIEW);
		baGate.setActions(baActions);
		if(baGate.getActions().size()>0) { 
			session.save(baGate);
			cp.getPermissions().add(baGate);
		}
	}
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws java.lang.Exception {
	
	FieldPermissionsForm fpf=(FieldPermissionsForm) form;
	Long fieldId=Long.parseLong(request.getParameter("fieldId"));
	
	
	Session session = PersistenceManager.getSession();
	Map permScope= PermissionUtil.resetScope(request.getSession());
	AmpFieldsVisibility afv=(AmpFieldsVisibility) session.get(AmpFieldsVisibility.class, fieldId);
	fpf.setFieldName(afv.getName());

	//saving the data
	if(request.getParameter("save")!=null) { 
		PermissionMap permissionMap = PermissionUtil.getOwnPermissionMapForPermissible(afv);
		if(permissionMap!=null) { 
		    Permission p=permissionMap.getPermission();
		    //we delete the old permissions, if they are dedicated
		    if (p!=null && p.isDedicated()) {
			CompositePermission cp = (CompositePermission)p;
			Iterator<Permission> i = cp.getPermissions().iterator();
			while (i.hasNext()) {
			    Permission element = (Permission) i.next();
			    Object object = session.load(Permission.class, element.getId());
			    session.delete(object);
			}
			    Object object = session.load(Permission.class, cp.getId());
			session.delete(object);
		    }
		    Object object = session.load(PermissionMap.class, permissionMap.getId());
		    session.delete(object);
		    session.flush();
		}
	
		//we create a fresh list of permissions and bind them with the field
		permissionMap=new PermissionMap(); 
		permissionMap.setPermissibleCategory(afv.getPermissibleCategory().getSimpleName());
		permissionMap.setObjectIdentifier(afv.getId());
		
		CompositePermission cp=new CompositePermission(true);
		
		cp.setName(afv.getName()+" - Composite Permission");
		
		initializeAndSaveGatePermission(session,cp,afv.getName()+ " - Beneficiary Agency Permission","BA",OrgRoleGate.class,fpf.getBaRead(),fpf.getBaEdit());
		initializeAndSaveGatePermission(session,cp,afv.getName()+ " - Contracting Agency Permission","CA",OrgRoleGate.class,fpf.getCaRead(),fpf.getCaEdit());		
		initializeAndSaveGatePermission(session,cp,afv.getName()+ " - Executing Agency Permission","EA",OrgRoleGate.class,fpf.getEaRead(),fpf.getEaEdit());
		initializeAndSaveGatePermission(session,cp,afv.getName()+ " - Implementing Agency Permission","IA",OrgRoleGate.class,fpf.getIaRead(),fpf.getIaEdit());
		initializeAndSaveGatePermission(session,cp,afv.getName()+ " - Responsible Agency Permission","RA",OrgRoleGate.class,fpf.getRaRead(),fpf.getRaEdit());
		initializeAndSaveGatePermission(session,cp,afv.getName()+ " - Funding Agency Permission","DN",OrgRoleGate.class,fpf.getFaRead(),fpf.getFaEdit());		
		initializeAndSaveGatePermission(session,cp,afv.getName()+ " - Regional Group Permission","RG",OrgRoleGate.class,fpf.getRgRead(),fpf.getRgEdit());	
		initializeAndSaveGatePermission(session,cp,afv.getName()+ " - Sector Group Permission","SG",OrgRoleGate.class,fpf.getSgRead(),fpf.getSgEdit());
		initializeAndSaveGatePermission(session,cp,afv.getName()+ " - Everyone Permission",UserLevelGate.PARAM_EVERYONE,UserLevelGate.class,fpf.getEvRead(),fpf.getEvEdit());
		initializeAndSaveGatePermission(session,cp,afv.getName()+ " - Guest Permission",UserLevelGate.PARAM_GUEST,UserLevelGate.class,fpf.getGuRead(),fpf.getGuEdit());
		initializeAndSaveGatePermission(session,cp,afv.getName()+ " - Owner Permission",UserLevelGate.PARAM_OWNER,UserLevelGate.class,fpf.getOwRead(),fpf.getOwEdit());
		session.save(cp);
		
		permissionMap.setPermission(cp);
		
		session.save(permissionMap);
		
	    request.setAttribute("close", "close");
	} else { 
	    //loading data ... 
	    Permission permission = afv.getPermission(true);		
	    if(permission!=null && permission.isDedicated() && permission instanceof CompositePermission) {
		CompositePermission cp=(CompositePermission) permission;
		Iterator i=cp.getPermissions().iterator();
		while (i.hasNext()) {
		    GatePermission agencyPerm = (GatePermission) i.next();
		    if(agencyPerm.hasParameter("BA")) {
			if(agencyPerm.hasAction(GatePermConst.Actions.EDIT)) fpf.setBaEdit("on");
			if(agencyPerm.hasAction(GatePermConst.Actions.VIEW)) fpf.setBaRead("on");			
		    }
		    if(agencyPerm.hasParameter("CA")) {
			if(agencyPerm.hasAction(GatePermConst.Actions.EDIT)) fpf.setCaEdit("on");
			if(agencyPerm.hasAction(GatePermConst.Actions.VIEW)) fpf.setCaRead("on");			
		    }
		    if(agencyPerm.hasParameter("EA")) {
			if(agencyPerm.hasAction(GatePermConst.Actions.EDIT)) fpf.setEaEdit("on");
			if(agencyPerm.hasAction(GatePermConst.Actions.VIEW)) fpf.setEaRead("on");			
		    }
		    if(agencyPerm.hasParameter("IA")) {
			if(agencyPerm.hasAction(GatePermConst.Actions.EDIT)) fpf.setIaEdit("on");
			if(agencyPerm.hasAction(GatePermConst.Actions.VIEW)) fpf.setIaRead("on");			
		    }
		    if(agencyPerm.hasParameter("DN")) {
			if(agencyPerm.hasAction(GatePermConst.Actions.EDIT)) fpf.setFaEdit("on");
			if(agencyPerm.hasAction(GatePermConst.Actions.VIEW)) fpf.setFaRead("on");			
		    }
		    if(agencyPerm.hasParameter("SG")) {
				if(agencyPerm.hasAction(GatePermConst.Actions.EDIT)) fpf.setSgEdit("on");
				if(agencyPerm.hasAction(GatePermConst.Actions.VIEW)) fpf.setSgRead("on");			
			}
		    if(agencyPerm.hasParameter("RG")) {
				if(agencyPerm.hasAction(GatePermConst.Actions.EDIT)) fpf.setRgEdit("on");
				if(agencyPerm.hasAction(GatePermConst.Actions.VIEW)) fpf.setRgRead("on");			
			}  
		    if(agencyPerm.hasParameter("RA")) {
				if(agencyPerm.hasAction(GatePermConst.Actions.EDIT)) fpf.setRaEdit("on");
				if(agencyPerm.hasAction(GatePermConst.Actions.VIEW)) fpf.setRaRead("on");			
			}  
		    if(agencyPerm.hasParameter(UserLevelGate.PARAM_EVERYONE)) {
				if(agencyPerm.hasAction(GatePermConst.Actions.EDIT)) fpf.setEvEdit("on");
				if(agencyPerm.hasAction(GatePermConst.Actions.VIEW)) fpf.setEvRead("on");			
			}    

		    
		    if(agencyPerm.hasParameter(UserLevelGate.PARAM_GUEST)) {
				if(agencyPerm.hasAction(GatePermConst.Actions.EDIT)) fpf.setGuEdit("on");
				if(agencyPerm.hasAction(GatePermConst.Actions.VIEW)) fpf.setGuRead("on");			
			}

		    if(agencyPerm.hasParameter(UserLevelGate.PARAM_OWNER)) {
				if(agencyPerm.hasAction(GatePermConst.Actions.EDIT)) fpf.setOwEdit("on");
				if(agencyPerm.hasAction(GatePermConst.Actions.VIEW)) fpf.setOwRead("on");			
			} 

		}
	    } else { 
		//initialize everyone + guest
		fpf.setEvEdit(null);
		fpf.setGuEdit(null);
		fpf.setEvRead("on");
		fpf.setGuRead("on");
		fpf.setOwEdit("on");
		fpf.setOwRead("on");
		
		//get the global for the fields:
		Permission globalPermissionForPermissibleClass = PermissionUtil.getGlobalPermissionForPermissibleClass(AmpFieldsVisibility.class);
		if(globalPermissionForPermissibleClass!=null) {Set<String> allowedActions = globalPermissionForPermissibleClass.getAllowedActions(permScope);
		if(allowedActions.contains(GatePermConst.Actions.EDIT)) {
		    fpf.setBaEdit("on");
		    fpf.setCaEdit("on");
		    fpf.setEaEdit("on");
		    fpf.setIaEdit("on");
		    fpf.setRaEdit("on");
		    fpf.setFaEdit("on");
		    fpf.setRgEdit("on");
		    fpf.setSgEdit("on");
		}
		if(allowedActions.contains(GatePermConst.Actions.VIEW)) {
		    fpf.setBaRead("on");
		    fpf.setCaRead("on");
		    fpf.setEaRead("on");
		    fpf.setIaRead("on");
		    fpf.setRaRead("on");
		    fpf.setFaRead("on");
		    fpf.setRgRead("on");
		    fpf.setSgRead("on");
		}
		}
		
		
	    }
	}
	session.flush();

	PersistenceManager.releaseSession(session);
	
	return mapping.getInputForward();
    }
}
