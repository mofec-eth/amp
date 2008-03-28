package org.digijava.module.aim.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.Session;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dgfoundation.amp.utils.MultiAction;
import org.dgfoundation.amp.visibility.AmpTreeVisibility;
import org.digijava.module.aim.dbentity.AmpFeaturesVisibility;
import org.digijava.module.aim.dbentity.AmpFieldsVisibility;
import org.digijava.module.aim.dbentity.AmpModulesVisibility;
import org.digijava.module.aim.dbentity.AmpTemplatesVisibility;
import org.digijava.module.aim.form.VisibilityManagerForm;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.util.FeaturesUtil;
 
public class VisibilityManager extends MultiAction {
	
	private static Logger logger = Logger.getLogger(VisibilityManager.class);
	
	private ServletContext ampContext = null;
		public ActionForward modePrepare(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Collection templates=FeaturesUtil.getAMPTemplatesVisibility();
		VisibilityManagerForm vForm=(VisibilityManagerForm) form;
		vForm.setTemplates(templates);
		vForm.setMode("manageTemplates");
		//this function generate a file containing all the fields, features and modules from 
		//all the .jsp files from AMP
		//generateAllFieldsInFile();
		
		return  modeSelect(mapping, form, request, response);
	}

	public ActionForward modeSelect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
	  //clear all message previously added
	    ((VisibilityManagerForm) form).clearMessages();
	    
		if(request.getParameter("action")!=null)
			{
				if(request.getParameter("action").compareTo("add")==0) return modeAddTemplate(mapping, form, request, response);
				if(request.getParameter("action").compareTo("viewFields")==0) return modeViewFields(mapping, form, request, response);
				if(request.getParameter("action").compareTo("edit")==0) {
					
					return modeEditTemplate(mapping, form, request, response);
				}
				if(request.getParameter("action").compareTo("delete")==0) return modeDeleteTemplate(mapping, form, request, response);				
				if(request.getParameter("action").compareTo("deleteFFM")==0) return modeDeleteFFM(mapping, form, request, response);
				if(request.getParameter("action").compareTo("manageTemplates")==0) return modeManageTemplates(mapping, form, request, response);
			}
		if(request.getParameter("newTemplate")!=null) return modeSaveTemplate(mapping, form, request, response);
		if(request.getParameter("saveEditTemplate")!=null) return modeSaveEditTemplate(mapping, form, request, response);
		if(request.getParameter("saveTreeVisibility")!=null) return modeSaveTreeVisibility(mapping, form, request, response);
		return mapping.findForward("forward");
	}
	
	
	public ActionForward modeManageTemplates(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Collection templates=FeaturesUtil.getAMPTemplatesVisibility();
		VisibilityManagerForm vForm=(VisibilityManagerForm) form;
		vForm.setTemplates(templates);
		vForm.setMode("manageTemplates");
		return mapping.findForward("forward");
	}

	public ActionForward modeAddTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		VisibilityManagerForm vForm=(VisibilityManagerForm) form;
		Collection modules=FeaturesUtil.getAMPModulesVisibility();
		vForm.setMode("addNew");
		vForm.setModules(modules);
		return mapping.findForward("forward");
	}

	
	public ActionForward modeViewFields(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		VisibilityManagerForm vForm=(VisibilityManagerForm) form;
		Collection modules=FeaturesUtil.getAMPModulesVisibility();
		Collection features=FeaturesUtil.getAMPFeaturesVisibility();
		Collection fields=FeaturesUtil.getAMPFieldsVisibility();
		vForm.setMode("viewFields");
		vForm.setAllModules(modules);
		vForm.setAllFeatures(features);
		vForm.setAllFields(fields);
		return mapping.findForward("forward");
	}
	
	public ActionForward modeSaveTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Session hbsession=this.createSession();
		if(FeaturesUtil.existTemplateVisibility(request.getParameter("templateName")))
		{
		    
		    ((VisibilityManagerForm) form).addError("aim:fm:errortemplateExistent", "Template name already exist in database. Please choose another name for template.");
		    
		    //errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.aim.templateExistent"));
			
			//saveErrors(request, errors);
		}
		else
		{
			FeaturesUtil.insertTemplate(request.getParameter("templateName"), hbsession);
		}
		//for refreshing the page...
		VisibilityManagerForm vForm=(VisibilityManagerForm) form;
		Collection templates=FeaturesUtil.getAMPTemplatesVisibility();
		vForm.setTemplates(templates);
		hbsession.close();
		return mapping.findForward("forward");
	}
	
	public ActionForward modeEditTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getParameter("changeLevel")!=null) System.out.println("o daaaaaaaaaaaaaaaaaaaaaaaaa");
		VisibilityManagerForm vForm = (VisibilityManagerForm) form;
		Long templateId=null;
		Session hbsession = this.createSession();
		HttpSession session=request.getSession();
		if(request.getParameter("templateId")!=null)
			templateId=new Long(Long.parseLong(request.getParameter("templateId")));
		if(templateId==null) templateId=(Long)request.getAttribute("templateId");
		
		String templateName=FeaturesUtil.getTemplateNameVisibility(templateId);
		session.setAttribute("templateName", templateName);
		session.setAttribute("templateId",templateId);
		//AmpTemplatesVisibility templateVisibility = FeaturesUtil.getTemplateVisibility(templateId, hbsession);
		AmpTemplatesVisibility templateVisibility = FeaturesUtil.getTemplateById(templateId);
		AmpTreeVisibility ampTreeVisibility=new AmpTreeVisibility();
		AmpTreeVisibility ampTreeVisibilityAux=new AmpTreeVisibility();
		ampTreeVisibility.buildAmpTreeVisibility(templateVisibility);
		String existSubmodules;
		//ampContext=this.getServlet().getServletContext();
    	//ampContext.setAttribute("ampTreeVisibility",ampTreeVisibility);
		if( ampTreeVisibilityAux.buildAmpTreeVisibilityMultiLevel(templateVisibility) )
			existSubmodules="true";
		else existSubmodules="false";
		vForm.setExistSubmodules(existSubmodules);
		vForm.setAmpTreeVisibility(ampTreeVisibilityAux);
		
		
		vForm.setMode("editTemplateTree");

		{//for refreshing the page
			Collection templates=FeaturesUtil.getAMPTemplatesVisibility();
			vForm.setTemplates(templates);
		}
		hbsession.close();
		return mapping.findForward("forward");
	}
	
	public ActionForward modeSaveEditTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		VisibilityManagerForm vForm = (VisibilityManagerForm) form;
		Long templateId;
		HttpSession session=request.getSession();
		templateId=vForm.getTemplateId();
		String templateName=FeaturesUtil.getTemplateNameVisibility(templateId);
		session.setAttribute("templateName", templateName);
		Collection allAmpModules=FeaturesUtil.getAMPModules();
		Collection newTemplateModulesList=new ArrayList();
		for(Iterator it=allAmpModules.iterator();it.hasNext();)
		{
			
			AmpModulesVisibility ampModule=(AmpModulesVisibility)it.next();
			String existentModule=ampModule.getName().replaceAll(" ","");
			if(request.getParameter("moduleVis:"+existentModule)!=null)
				if(request.getParameter("moduleVis:"+existentModule).compareTo("enable")==0)
					{
						newTemplateModulesList.add(ampModule);
					}
		}
		FeaturesUtil.updateModulesTemplate(newTemplateModulesList, templateId, vForm.getTemplateName());
		{//for refreshing the page
			Collection templates=FeaturesUtil.getAMPTemplatesVisibility();
			vForm.setTemplates(templates);
		}
		
		vForm.addMessage("aim:ampfeaturemanager:updatedTemplate", "Template was updated");
		return mapping.findForward("forward");
	}
	
	public ActionForward modeDeleteTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Session hbsession = null;
		hbsession = this.createSession();
		FeaturesUtil.deleteTemplateVisibility(new Long(Long.parseLong(request.getParameter("templateId"))),hbsession);
		{//for refreshing the page
			VisibilityManagerForm vForm = (VisibilityManagerForm) form;
			Collection templates=FeaturesUtil.getAMPTemplatesVisibility();
			vForm.setTemplates(templates);
		}
	 	((VisibilityManagerForm)form).addMessage("aim:fm:message:deletedTemplate", "The template was deleted.");
		
	 	hbsession.close();
		return mapping.findForward("forward");
	}
	
	public ActionForward modeDeleteFFM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Session hbsession = null;
		hbsession = this.createSession();
		if(request.getParameter("fieldId")!=null) FeaturesUtil.deleteFieldVisibility(new Long(Long.parseLong(request.getParameter("fieldId"))),hbsession);//delete field
		if(request.getParameter("featureId")!=null) FeaturesUtil.deleteFeatureVisibility(new Long(Long.parseLong(request.getParameter("featureId"))),hbsession);//delete feature
		if(request.getParameter("moduleId")!=null) FeaturesUtil.deleteModuleVisibility(new Long(Long.parseLong(request.getParameter("moduleId"))),hbsession);//delete module
		hbsession.close();
		return modeViewFields(mapping, form, request, response);
	}
	
	public ActionForward modeSaveTreeVisibility(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		AmpTreeVisibility ampTreeVisibility=new AmpTreeVisibility();
		Session hbsession = null;

		hbsession = this.createSession();
		HttpSession session=request.getSession();
		Long templateId=(Long)session.getAttribute("templateId");
		AmpTemplatesVisibility templateVisibility = FeaturesUtil.getTemplateVisibility(templateId, hbsession);
		ampTreeVisibility.buildAmpTreeVisibility(templateVisibility);
		TreeSet modules=new TreeSet();
		TreeSet features=new TreeSet();
		TreeSet fields=new TreeSet();
		
		AmpTreeVisibility x=ampTreeVisibility;
		/*for(Iterator i=x.getItems().values().iterator();i.hasNext();)
		{
			AmpTreeVisibility m=(AmpTreeVisibility) i.next();
			if(request.getParameter("moduleVis:"+m.getRoot().getId())!=null)
			{
				modules.add(m.getRoot());
				for(Iterator j=m.getItems().values().iterator();j.hasNext();)
				{
					AmpTreeVisibility n=(AmpTreeVisibility) j.next();
					if(request.getParameter("featureVis:"+n.getRoot().getId())!=null)
					{
						features.add(n.getRoot());
						for(Iterator k=n.getItems().values().iterator();k.hasNext();)
						{
							AmpTreeVisibility p=(AmpTreeVisibility) k.next();
							if(request.getParameter("fieldVis:"+p.getRoot().getId())!=null)
							{
								fields.add(p.getRoot()); 
							}
						}
					}
				}
			}
		}*/
		for(Iterator it=x.getItems().values().iterator();it.hasNext();)
		{
			Object obj=it.next();
			AmpTreeVisibility treeNode=(AmpTreeVisibility)obj;
			if(treeNode.getRoot() instanceof AmpModulesVisibility)
				if(request.getParameter("moduleVis:"+treeNode.getRoot().getId())!=null)
					{
						modules.add(treeNode.getRoot());
					}
			displayRecTreeForDebug(treeNode,modules,features,fields,request);
		}
		

		FeaturesUtil.updateAmpTemplateNameTreeVisibility(request.getParameter("templateName"), templateId, hbsession);
		FeaturesUtil.updateAmpModulesTreeVisibility(modules, templateId, hbsession);
		FeaturesUtil.updateAmpFeaturesTreeVisibility(features, templateId, hbsession);
		FeaturesUtil.updateAmpFieldsTreeVisibility(fields, templateId, hbsession);
		request.setAttribute("templateId", templateId);
			
		
		((VisibilityManagerForm) form).addMessage("aim:ampfeaturemanager:visibilityTreeUpdated",  Constants.FEATURE_MANAGER_VISIBILITY_TREE_UPDATED);
		
		AmpTemplatesVisibility currentTemplate=FeaturesUtil.getTemplateVisibility(FeaturesUtil.getGlobalSettingValueLong("Visibility Template"),hbsession);
        	ampTreeVisibility.buildAmpTreeVisibility(currentTemplate);
        	
        	ampContext=this.getServlet().getServletContext();
        	ampContext.setAttribute("ampTreeVisibility",ampTreeVisibility);
        	
        	hbsession.close();
    	
		return modeEditTemplate(mapping,form,request,response);
	}
	

	public void displayRecTreeForDebug(AmpTreeVisibility atv, TreeSet modules, TreeSet features, TreeSet fields,HttpServletRequest request)
	{
		if(atv.getItems()==null) return;
		if(atv.getItems().isEmpty()) return;
		for(Iterator it=atv.getItems().values().iterator();it.hasNext();)
		{
			AmpTreeVisibility auxTree=(AmpTreeVisibility) it.next();
			if(auxTree.getRoot() instanceof AmpModulesVisibility)
				if(request.getParameter("moduleVis:"+auxTree.getRoot().getId())!=null)
					{
						modules.add(auxTree.getRoot());
					}
			if(auxTree.getRoot() instanceof AmpFeaturesVisibility)
				if(request.getParameter("featureVis:"+auxTree.getRoot().getId())!=null)
					{
						features.add(auxTree.getRoot());
					}
			if(auxTree.getRoot() instanceof AmpFieldsVisibility)
				if(request.getParameter("fieldVis:"+auxTree.getRoot().getId())!=null)
					{
						fields.add(auxTree.getRoot());
					}
			displayRecTreeForDebug(auxTree, modules, features, fields, request);
		}
	}
	
	 public int getAllPatchesFiles(String abstractPatchesLocation, TreeSet<String> modules, TreeSet<String> features, TreeSet<String> fields)
	    {
		String path=abstractPatchesLocation;
		File dir = new File(path);
		   String[] files = dir.list();
		   if(files!=null)
			   if(files.length>0)
		   try {
			   	for (int i = 0; i < files.length; i++) {
			   		File f = new File(dir, files[i]);
		    
			   		if (f.isDirectory())
			   		{
			   			getAllPatchesFiles(f.getAbsolutePath(), modules,features,fields);
			   		}
			   		if(!f.isDirectory() && f.getName().contains(".jsp"))
			   		{
			   			Scanner scanner = new Scanner(f);
			   			scanner.useDelimiter (System.getProperty("line.separator"));
			   			while(scanner.hasNext()) {
			   				String s=scanner.next();
			   				if(s.indexOf("<module:display")>=0)
			   				{
			   					String aux="";
			   					if(s.indexOf(">",(s.indexOf("<module:display"))+1)<0)
			   						{
			   							aux=scanner.next();
			   						}
			   					String module="";
			   					if("".equals(aux)) module=s.substring(s.indexOf("<module:display"), s.indexOf(">",(s.indexOf("<module:display"))+1)+1);
			   						else module=s.substring(s.indexOf("<module:display"), s.length()-1)+aux;
			   					modules.add(module+"</module:display>\n");
			   				}
			   				if(s.indexOf("<field:display")>=0)
			   				{
			   					String aux="";
			   					if(s.indexOf(">",(s.indexOf("<field:display"))+1)<0)
			   						{
			   							aux=scanner.next();
			   						}
			   					String module="";
			   					if("".equals(aux)) module=s.substring(s.indexOf("<field:display"), s.indexOf(">",(s.indexOf("<field:display"))+1)+1);
			   						else module=s.substring(s.indexOf("<field:display"), s.length()-1)+aux;
			   					fields.add(module+"</field:display>\n");
			   				}
			   				
			   				if(s.indexOf("<feature:display")>=0)
			   				{
			   					String aux="";
			   					if(s.indexOf(">",(s.indexOf("<field:display"))+1)<0)
			   						{
			   							aux=scanner.next();
			   						}
			   					String module="";
			   					if("".equals(aux)) module=s.substring(s.indexOf("<feature:display"), s.indexOf(">",(s.indexOf("<feature:display"))+1)+1);
			   						else module=s.substring(s.indexOf("<feature:display"), s.length()-1)+aux;
			   					features.add(module+"</feature:display>\n");
			   				}

			   			}
			   			scanner.close();
			   		}
			   	}
		   }catch(Exception e){ e.printStackTrace(); }
	   return 1;
	  }

	 private void generateAllFieldsInFile(){
		 TreeSet modules=new TreeSet();
		 TreeSet features=new TreeSet();
		 TreeSet fields=new TreeSet();
			getAllPatchesFiles(this.getServlet().getServletContext().getRealPath("/"),modules, features, fields);
			int i=0;
			try{
			    FileWriter fstream = new FileWriter(this.getServlet().getServletContext().getRealPath("/")+"/out.txt");
			        BufferedWriter out = new BufferedWriter(fstream);
			        for (Iterator iter = modules.iterator(); iter.hasNext();) {
						String s = (String) iter.next();
						out.append(s);
					}
			        for (Iterator iter = features.iterator(); iter.hasNext();) {
						String s = (String) iter.next();
						out.append(s);
					}
			        for (Iterator iter = fields.iterator(); iter.hasNext();) {
						String s = (String) iter.next();
						out.append(s);
					}
			    out.close();
			    }catch (Exception e){//Catch exception if any
			      System.err.println("Error: " + e.getMessage());
			    }
			
	 }
}