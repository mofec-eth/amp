/*
 *   SiteManager.java
 * 	 @Author Mikheil Kapanadze mikheil@digijava.org
 *   Created: Jul 2, 2003
 *	 CVS-ID: $Id: SiteManager.java,v 1.1 2005-07-06 12:00:13 rahul Exp $
 *
 *   This file is part of DiGi project (www.digijava.org).
 *   DiGi is a multi-site portal system written in Java/J2EE.
 *
 *   Confidential and Proprietary, Subject to the Non-Disclosure
 *   Agreement, Version 1.0, between the Development Gateway
 *   Foundation, Inc and the Recipient -- Copyright 2001-2004 Development
 *   Gateway Foundation, Inc.
 *
 *   Unauthorized Disclosure Prohibited.
 *
 *************************************************************************/
package org.digijava.kernel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.digijava.kernel.entity.ModuleInstance;
import org.digijava.kernel.exception.DgException;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.kernel.request.Site;
import org.digijava.kernel.request.SiteDomain;
import org.digijava.kernel.viewmanager.ViewConfig;
import org.digijava.kernel.viewmanager.ViewConfigFactory;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

public class SiteManager {

    private static Logger logger = Logger.getLogger(SiteManager.class);

    public static void createSite(String name, String siteKey, String dirName,
                                  String[] hosts, String[] pathes,
                                  String siteRootDir, String templateName) throws DgException {

        Site site = prepareSiteObject(name, siteKey, dirName, hosts, pathes, templateName);

        Session sess = null;
        Transaction tx = null;
        try {
            sess = org.digijava.kernel.persistence.PersistenceManager.
                getSession();
            tx = sess.beginTransaction();
            sess.save(site);
            tx.commit();
        }
        catch (Exception ex) {
            logger.debug("Unable to create site ",ex);

            if (tx != null) {
                try {
                    tx.rollback();
                }
                catch (HibernateException ex1) {
                    logger.warn("rollback() failed ",ex1);
                }
            }
            throw new DgException(
                "Unable to create site", ex);
        }
        finally {
            if (sess != null) {
                try {
                    PersistenceManager.releaseSession(sess);
                }
                catch (Exception ex1) {
                    logger.warn("releaseSession() failed ",ex1);
                }
            }

        }
        SiteUtils.fixDefaultGroupPermissions(site);


        createSiteConfig(dirName, siteRootDir, templateName);

    }

    private static void createSiteConfig(String dirName, String siteRootDir,
                                         String templateName) {
        File siteDir = new File(siteRootDir + File.separator + dirName);

        if (!siteDir.exists()) {
            siteDir.mkdir();

            File siteConfig = new File(siteRootDir + File.separator + dirName +
                                       File.separator + "site-config.xml");

            try {
                siteConfig.createNewFile();
                PrintStream ps = new PrintStream(new FileOutputStream(
                    siteConfig));
                ps.println("<?xml version=\"1.0\"?>");
                if (templateName == null) {
                    ps.println("<site-config />");
                }
                else {
                    ps.println("<site-config template=\"" + templateName +
                               "\" />");
                }

            }
            catch (IOException ex3) {
                logger.warn("siteConfig.createNewFile() failed ",ex3);
            }
        }
    }

    private static Site prepareSiteObject(String name, String siteKey,
                                          String dirName, String[] hosts,
                                          String[] pathes, String templateName) throws
        DgException {
        Site site = new Site(name, siteKey);
        site.setFolder(dirName);
        site.setSiteDomains(new HashSet());

        for (int i = 0; i < hosts.length; i++) {
            SiteDomain siteDomain = new SiteDomain();
            siteDomain.setSite(site);
            siteDomain.setSiteDbDomain(hosts[i]);
            siteDomain.setDefaultDomain(i == 0);
            if (pathes != null) {
                try {
                    siteDomain.setSitePath(pathes[i]);
                }
                catch (IndexOutOfBoundsException ex2) {
                    logger.error("siteDomain.setSitePath() failed ",ex2);
                    siteDomain.setSitePath(null);
                }
            }
            site.getSiteDomains().add(siteDomain);
        }

        SiteUtils.addDefaultGroups(site);

        if (templateName != null) {
            ViewConfig templateViewConfig = ViewConfigFactory.getInstance().
                getTemplateViewConfig(templateName);
            HashSet moduleInstances = new HashSet(templateViewConfig.
                getReferencedInstances(false));
            Iterator instancesIter = moduleInstances.iterator();
            while (instancesIter.hasNext()) {
                ModuleInstance moduleInstance = (ModuleInstance)
                    instancesIter.next();
                moduleInstance.setSite(site);
                moduleInstance.setPermitted(true);
                moduleInstance.setRealInstance(null);
            }

            site.setModuleInstances(moduleInstances);
        }
        return site;
    }

    public static boolean createSiteFolder(String folderPath, String template) throws
        IOException {
        boolean retVal = false;
        // Check site directory
        File siteDir = new File(folderPath);

        if (siteDir.exists()) {
            retVal = true;
            logger.warn("directory " + folderPath + "already exists");
            //throw new AdminException("Such directory already exists");
        } else {
            siteDir.mkdir();
        }
        //siteDir.mkdir();

        File siteConfig = new File(siteDir.getAbsolutePath() + File.separator +
                                   "site-config.xml");

        if (!siteConfig.exists()) {
            siteConfig.createNewFile();
            PrintStream ps = new PrintStream(new FileOutputStream(siteConfig));
            ps.println("<?xml version=\"1.0\"?>");

            if (SiteConfigUtils.BLANK_TEMPLATE_NAME.equals(template)) {
                ps.println("<site-config />");
            }
            else {
                ps.println("<site-config template=\"" + template +
                           "\" />");
            }
            ps.close();
        }

        return retVal;
    }

}
