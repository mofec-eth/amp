/*
 * @Author Priyajith C
 */
package org.digijava.module.aim.startup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;
import org.dgfoundation.amp.visibility.AmpTreeVisibility;
import org.digijava.kernel.lucene.LuceneWorker;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.module.aim.dbentity.AmpTemplatesVisibility;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.helper.GlobalSettings;
import org.digijava.module.aim.util.ActivityVersionUtil;
import org.digijava.module.aim.util.CustomFieldsUtil;
import org.digijava.module.aim.util.FeaturesUtil;
import org.digijava.module.aim.util.LuceneUtil;
import org.digijava.module.gateperm.core.GatePermConst;
import org.digijava.module.gateperm.util.PermissionUtil;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

public class AMPStartupListener extends HttpServlet implements
		ServletContextListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5724776790911414323L;

	private static final String PATCH_METHOD_KEY = "patchAMP";

	private static Logger logger = Logger.getLogger(AMPStartupListener.class);

	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("The AMP ServletContext has been terminated.");
	}

	/**
	 * Initialize Quartz using some customized AMP settings - set the amp
	 * servlet context as a metadata of the scheduler, to be available later
	 * inside Jobs - set the quartz datasource the same as the Hibernate
	 * datasource to reduce configuration redundancy
	 * 
	 * @param sce
	 *            the servlet context event received from the initialization
	 */
	private void initializeQuartz(ServletContextEvent sce) {
//		logger.info("Intializing Quartz Scheduler using AMP datasource...");

		ServletContext ampContext = sce.getServletContext();
		try {
	/*	Configuration hCfg = PersistenceManager.getHibernateConfiguration();

			String requestedFile = System
					.getProperty(StdSchedulerFactory.PROPERTIES_FILE);
			String propFileName = requestedFile != null ? requestedFile
					: ampContext
							.getRealPath("/WEB-INF/classes/quartz.properties");

			File propFile = new File(propFileName);
			
			Properties quartzProperties = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(
					propFileName));
			quartzProperties.load(in);

			
			//ALWAYS start AMP with a datasource while in servlet mode
			String dataSource = (String) hCfg.getProperties().get(
					"connection.datasource");

			quartzProperties.put("org.quartz.dataSource.ampQuartzDS.jndiURL",
					dataSource);

			SchedulerFactory factory = new StdSchedulerFactory(quartzProperties);
*/
			SchedulerFactory factory = (SchedulerFactory) ampContext.getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);

			Scheduler scheduler = factory.getScheduler();
			SchedulerMetaData metaData = scheduler.getMetaData();

			scheduler.getContext().put(Constants.AMP_SERVLET_CONTEXT,
					ampContext);

			scheduler.start();

		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ampContext = null;

		try {
			ampContext = sce.getServletContext();

			ampContext.setAttribute(Constants.ME_FEATURE, new Boolean(true));
			ampContext.setAttribute(Constants.AA_FEATURE, new Boolean(true));
			ampContext.setAttribute(Constants.PI_FEATURE, new Boolean(true));
			ampContext.setAttribute(Constants.CL_FEATURE, new Boolean(true));
			ampContext.setAttribute(Constants.DC_FEATURE, new Boolean(true));
			ampContext.setAttribute(Constants.SC_FEATURE, new Boolean(true));
			ampContext.setAttribute(Constants.MS_FEATURE, new Boolean(true));
			ampContext.setAttribute(Constants.AC_FEATURE, new Boolean(true));
			ampContext.setAttribute(Constants.LB_FEATURE, new Boolean(true));
			ampContext.setAttribute(Constants.SA_FEATURE, new Boolean(true));

			if (FeaturesUtil.getDefaultFlag() != null)
				ampContext.setAttribute(Constants.DEF_FLAG_EXIST, new Boolean(
						true));

			AmpTreeVisibility ampTreeVisibility = new AmpTreeVisibility();
			// get the default amp template!!!
			AmpTreeVisibility ampTreeVisibilityAux = new AmpTreeVisibility();
			AmpTreeVisibility ampTreeVisibilityAux2 = new AmpTreeVisibility();
			Session session = PersistenceManager.getSession();

			AmpTemplatesVisibility currentTemplate = null;
			try {
				currentTemplate = FeaturesUtil
						.getTemplateVisibility(
								FeaturesUtil
										.getGlobalSettingValueLong("Visibility Template"),
								session);
				ampTreeVisibility.buildAmpTreeVisibility(currentTemplate);
			} finally {
				PersistenceManager.releaseSession(session);
			}
			ampContext.setAttribute("ampTreeVisibility", ampTreeVisibility);
			ampContext.setAttribute("FMcache", "read");

			// currentTemplate=FeaturesUtil.getTemplateVisibility(FeaturesUtil.getGlobalSettingValueLong("Visibility
			// Template"),session);
			// ampTreeVisibilityAux.buildAmpTreeVisibilityMultiLevel(currentTemplate);
			// ampTreeVisibilityAux2.displayVisibilityTreeForDebug(ampTreeVisibilityAux);
			// ampContext.setAttribute("ampTreeVisibility",ampTreeVisibility);
			Collection ampColumns = FeaturesUtil.getAMPColumnsOrder();
			ampContext.setAttribute("ampColumnsOrder", ampColumns);

			GlobalSettings globalSettings = GlobalSettings.getInstance();
			globalSettings.setShowComponentFundingByYear(FeaturesUtil
					.isShowComponentFundingByYear());
			FeaturesUtil.switchLogicInstance();

			ampContext.setAttribute(Constants.GLOBAL_SETTINGS, globalSettings);

			// Lucene indexation
			LuceneUtil.checkIndex(sce.getServletContext());
			LuceneUtil.createHelp(sce.getServletContext());
			//ampContext.setAttribute(Constants.LUCENE_INDEX, idx); //deprecated
			LuceneWorker.init(sce.getServletContext());

			PermissionUtil.getAvailableGates(ampContext);

			// initialize permissible simple name singleton
			GatePermConst.availablePermissiblesBySimpleNames = new Hashtable<String, Class>();
			for (int i = 0; i < GatePermConst.availablePermissibles.length; i++) {
				GatePermConst.availablePermissiblesBySimpleNames.put(
						GatePermConst.availablePermissibles[i].getSimpleName(),
						GatePermConst.availablePermissibles[i]);
			}

			initializeQuartz(sce);

			CustomFieldsUtil.parseXMLFile(sce.getServletContext().getResourceAsStream("/WEB-INF/custom-fields.xml"));
			
			//Update amp_activity view AMP-7616
			ActivityVersionUtil.updateActivityView();
			
		} catch (Exception e) {
			logger.error("Exception while initialising AMP :" + e.getMessage());
			e.printStackTrace(System.out);
		}
	}
}
