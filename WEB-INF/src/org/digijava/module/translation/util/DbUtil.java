/**
 * This file is part of DiGi project (www.digijava.org).
 * DiGi is a multi-site portal system written in Java/J2EE.
 *
 * Copyright (C) 2002-2007 Development Gateway Foundation, Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */

package org.digijava.module.translation.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.digijava.kernel.entity.Locale;
import org.digijava.kernel.entity.Message;
import org.digijava.kernel.exception.DgException;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.kernel.persistence.WorkerException;
import org.digijava.kernel.request.SiteDomain;
import org.digijava.kernel.translator.TranslatorWorker;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import org.digijava.kernel.util.DgUtil;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DbUtil {

    private static Logger logger = Logger.getLogger(DbUtil.class);

    /**
     * Returns collection of TrnLocale objects, translated to the given language
     *
     * @return
     * @throws DgException
     */
    public static Collection getLanguages() throws DgException {
        return getLanguages(null, null);
    }

    /**
     * Returns collection of TrnLocale objects, translated to the given language
     *
     * @param codes
     * @return
     * @throws DgException
     */
    public static Collection getLanguages(Collection codes, Long siteId) throws
        DgException {
        List languages = null;
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        try {
            session = PersistenceManager.getSession();

            queryString.append("select new org.digijava.module.translation.form.TranslationInfo(l.code, l.name, msg.message, d.siteDbDomain, d.sitePath) from " +
                               Message.class.getName() + " msg, " +
                               Locale.class.getName() + " l, " +
                               SiteDomain.class.getName() + " d " +
                               " where (msg.key(+) = l.messageLangKey and msg.locale(+) = l.code and msg.siteId(+)='0') and " +
                               " (d.site.id(+) = :siteId and d.language(+) = l.code) " +
                               " and l.available=true ");

            if (codes != null) {
                queryString.append("and l.code in(");

                Iterator iter = codes.iterator();
                while (iter.hasNext()) {
                    Locale item = (Locale) iter.next();
                    queryString.append("'" + item.getCode() + "',");
                }
                queryString.delete(queryString.length() - 1, queryString.length());
                queryString.append(")");
            }

            logger.debug("SQL: " + queryString.toString());
            Query query = session.createQuery(queryString.toString());
            query.setCacheable(true);
            query.setParameter("siteId", siteId);
            languages = query.list();
            logger.debug("GET LANGUAGES SIZE: " + languages.size());

        }
        catch (Exception ex) {
            logger.debug("Unable to get Languages ", ex);
            throw new DgException("Unable to get Languages ", ex);
        }
        finally {
            try {
                PersistenceManager.releaseSession(session);
            }
            catch (Exception ex1) {
                logger.warn("releaseSession() failed ", ex1);
            }
        }
        return languages;
    }

    /**
     *
     * @return
     * @throws DgException
     */
    public static List getKeyPrefixesList(Long siteId, String sourceLang) throws
        DgException {

        List keyPrefixes = new ArrayList();
        Session session = null;
        try {
            session = PersistenceManager.getSession();
            Query q = session.createQuery(
                "select msg.key from msg in class " +
                Message.class.getName() +
                " where msg.siteId = :siteId and msg.locale = :sourceLang and msg.key like '%:%'");

            q.setParameter("siteId", siteId.toString(), Hibernate.STRING);
            q.setParameter("sourceLang", sourceLang, Hibernate.STRING);

            List keyList = q.list();

            if (keyList != null) {
                Iterator iter = keyList.iterator();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    String prefix = key.trim();
                    prefix = key.split(":")[0].trim();

                    if (!keyPrefixes.contains( (String) prefix)) {
                        keyPrefixes.add(prefix);
                    }
                }
            }
        }
        catch (Exception ex) {
            logger.debug("Unable to get keys for messages", ex);
            throw new DgException("Unable to get keys for messages", ex);
        }
        finally {
            try {
                PersistenceManager.releaseSession(session);
            }
            catch (Exception ex1) {
                logger.warn("releaseSession() failed ", ex1);
            }
        }
        if (keyPrefixes.size() != 0) {
            return keyPrefixes;
        }
        else {
            return null;
        }

    }

    public static boolean keyExists(String key) throws
        DgException{

        Session session = null;
        List messages = null;
        try {
            session = PersistenceManager.getSession();
            Query q = session.createQuery("from Message m where m.key=:key");
            q.setParameter("key", key);
            messages = q.list();
            if (messages.size() > 0) {
                return true;
            }
        }
        catch (Exception ex) {
            logger.debug("Unable to get key from database", ex);
            throw new DgException(
                "Unable to get key from database", ex);
        }
        finally {
            if (session != null) {
                try {
                    PersistenceManager.releaseSession(session);
                }
                catch (Exception ex1) {
                    logger.warn("releaseSession() failed", ex1);
                }
            }
        }

        return false;

    }

    /**
     *
     * @param key
     * @throws DgException
     */
    public static void deleteMessage(String key, String langCode, Long siteId) throws
        DgException {

        Session session = null;
        Transaction tx = null;

        try {
            session = PersistenceManager.getSession();
            tx = session.beginTransaction();
            Message msgToDelete = getMessage(key, langCode, siteId);
            if (msgToDelete != null) {
                session.delete(msgToDelete);
            }

            tx.commit();
        }
        catch (Exception ex) {
            logger.debug("Unable to delete message into database", ex);

            if (tx != null) {
                try {
                    tx.rollback();
                }
                catch (HibernateException ex1) {
                    logger.warn("rollback() failed", ex1);
                }
            }
            throw new DgException(
                "Unable to delete message into database", ex);
        }
        finally {
            if (session != null) {
                try {
                    PersistenceManager.releaseSession(session);
                }
                catch (Exception ex1) {
                    logger.warn("releaseSession() failed", ex1);
                }
            }
        }

    }

    /**
     *
     * @param msg
     * @throws DgException
     * @deprecated Use TranslatorWorker call directly
     */
    public static void updateMessage(Message msg) throws DgException {
        try {
            TranslatorWorker.getInstance(msg.getKey()).update(msg);
        }
        catch (WorkerException ex) {
            throw new DgException(ex);
        }
    }

    /**
     *
     * @param key
     * @return
     * @throws DgException
     */
    public static Message getMessage(String key, String langCode, Long siteId) throws
        DgException {
        try {
            return TranslatorWorker.getInstance(key).get(key, langCode,
                siteId.toString());
        }
        catch (WorkerException ex) {
            throw new DgException(ex);
        }
        /*
        Session session = null;
        Message msg = null;

        try {
            session = PersistenceManager.getSession();
            Query q = session.createQuery("from msg in class " +
                                          Message.class.getName() +
                                          " where(msg.siteId = :siteId) and (msg.key=:key) and (msg.locale=:langCode)");
            q.setParameter("key", key, Hibernate.STRING);
            q.setParameter("langCode", langCode, Hibernate.STRING);
            q.setParameter("siteId", siteId.toString(), Hibernate.STRING);

            List mList = q.list();
            if (mList != null && mList.size() != 0) {
                msg = (Message) q.list().get(0);
            }
        }
        catch (Exception ex) {
            logger.debug("Unable to get message", ex);
            throw new DgException("Unable to get message", ex);
        }
        finally {
            try {
                PersistenceManager.releaseSession(session);
            }
            catch (Exception ex1) {
                logger.warn("releaseSession() failed ", ex1);
            }
        }
        return msg;
*/
    }

    /**
     *
     * @param msg
     * @throws DgException
     * @deprecated Use TranslatorWorker call directly
     *
     */
    public static void saveMessage(Message msg) throws DgException {
        try {
            TranslatorWorker.getInstance(msg.getKey()).save(msg);
        }
        catch (WorkerException ex) {
            throw new DgException(ex);
        }
    }

    /**
     *
     * @param key
     * @param langCode
     * @param siteId
     * @param firstResult
     * @param maxResult
     * @return
     * @throws DgException
     */
    public static List searchMessagesByKey(String key, String langCode,
                                           Long siteId, int firstResult,
                                           int maxResult) throws
        DgException {

        List messages = null;
        Session session = null;
        try {
            session = PersistenceManager.getSession();

            String queryString = "select new org.digijava.module.translation.form.TranslationInfo(msg.locale, msg.key, msg.message) from " +
                Message.class.getName() + " msg, " +
                " where (msg.siteId = :siteId)";

            key = key.toLowerCase().trim();
            if (key.indexOf("'") >= 0) {
                key = key.replaceAll("'", "''");
            }

            queryString += " and (lower(msg.key)  like '%" + key + "%')";

            if (langCode != null) {
                queryString +=
                    " and (msg.locale = :langCode)";
            }

            queryString += " order by msg.key";
            logger.debug("Query:" + queryString);

            Query q = session.createQuery(queryString);
            q.setParameter("siteId", siteId.toString(), Hibernate.STRING);
            if (langCode != null) {
                q.setParameter("langCode", langCode,
                               Hibernate.STRING);
            }
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResult);

            messages = q.list();
        }
        catch (Exception ex) {
            logger.debug("Unable to get keys for messages", ex);
            throw new DgException("Unable to get keys for messages", ex);
        }
        finally {
            try {
                PersistenceManager.releaseSession(session);
            }
            catch (Exception ex1) {
                logger.warn("releaseSession() failed ", ex1);
            }
        }
        if (messages.size() != 0) {
            return messages;
        }
        else {
            return null;
        }

    }

    /**
     *
     * @param sourceMessage
     * @param langCode
     * @param siteId
     * @param firstResult
     * @param maxResult
     * @return
     * @throws DgException
     */
    public static List searchMessagesBySourceOrTarget(String message,
        String langCode,
        Long siteId, int firstResult,
        int maxResult) throws
        DgException {

        List messages = null;
        Session session = null;
        try {
            session = PersistenceManager.getSession();

            String queryString = "select new org.digijava.module.translation.form.TranslationInfo(msg.locale, msg.key, msg.message) from " +
                Message.class.getName() + " msg, " +
                " where (msg.siteId = :siteId)";

            message = message.toLowerCase().trim();
            if (message.indexOf("'") >= 0) {
                message = message.replaceAll("'", "''");
            }

            queryString += " and (lower(msg.message)  like '%" + message +
                "%')";

            if (langCode != null) {
                queryString +=
                    " and (msg.locale = :langCode)";
            }

            queryString += " order by msg.key";
            logger.debug("Query:" + queryString);

            Query q = session.createQuery(queryString);
            q.setParameter("siteId", siteId.toString(), Hibernate.STRING);
            if (langCode != null) {
                q.setParameter("langCode", langCode,
                               Hibernate.STRING);
            }
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResult);

            messages = q.list();
        }
        catch (Exception ex) {
            logger.debug("Unable to get keys for messages", ex);
            throw new DgException("Unable to get keys for messages", ex);
        }
        finally {
            try {
                PersistenceManager.releaseSession(session);
            }
            catch (Exception ex1) {
                logger.warn("releaseSession() failed ", ex1);
            }
        }
        if (messages.size() != 0) {
            return messages;
        }
        else {
            return null;
        }

    }

    /**
     *
     * @param prefix
     * @param langCode
     * @param siteId
     * @param firstResult
     * @param maxResult
     * @return
     * @throws DgException
     */
    public static List getMessagesList(String prefix, String langCode,
                                       Long siteId, int firstResult,
                                       int maxResult) throws
        DgException {

        List messages = null;
        Session session = null;
        try {
            session = PersistenceManager.getSession();

            String queryString = "select new org.digijava.module.translation.form.TranslationInfo(msg.locale, msg.key, msg.message) from " +
                Message.class.getName() + " msg, " +
                " where (msg.siteId = :siteId)";

            if (prefix != null) {
                if (prefix.indexOf("'") >= 0) {
                    prefix = prefix.replaceAll("'", "''");
                }
                prefix = prefix.replaceAll("&quot;", "\"");
                queryString += " and ( (msg.key  like '" + prefix +
                    "%') or (msg.key  like '" + prefix + ":" + "%') )";
            }
            if (langCode != null) {
                queryString +=
                    " and (msg.locale = :langCode)";
            }

            queryString += " order by msg.key";
            logger.debug("Query:" + queryString);

            Query q = session.createQuery(queryString);
            q.setParameter("siteId", siteId.toString(), Hibernate.STRING);
            if (langCode != null) {
                q.setParameter("langCode", langCode, Hibernate.STRING);
            }
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResult);

            messages = q.list();
        }
        catch (Exception ex) {
            logger.debug("Unable to get keys for messages", ex);
            throw new DgException("Unable to get keys for messages", ex);
        }
        finally {
            try {
                PersistenceManager.releaseSession(session);
            }
            catch (Exception ex1) {
                logger.warn("releaseSession() failed ", ex1);
            }
        }
        if (messages.size() != 0) {
            return messages;
        }
        else {
            return null;
        }

    }

}
