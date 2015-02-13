/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.broker;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.connection.proxy.ConnectionProxy;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.apache.commons.collections.comparators.ReverseComparator;

import java.sql.Date;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.belis.gui.widget.KeyTableListener;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.exception.LockAlreadyExistsException;

import de.cismet.belisEE.util.EntityComparator;
import de.cismet.belisEE.util.StandortKey;

import de.cismet.cids.custom.beans.belis2.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis2.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis2.SperreCustomBean;
import de.cismet.cids.custom.beans.belis2.SperreEntityCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cismap.commons.BoundingBox;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class CidsBroker {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CidsBroker.class);
    public static final String BELIS_DOMAIN = BelisMetaClassConstants.DOMAIN;
    private static CidsBroker brokerInstance = null;

    //~ Instance fields --------------------------------------------------------

    HashMap<String, Collection<KeyTableListener>> keyTableListeners =
        new HashMap<String, Collection<KeyTableListener>>();
    private ConnectionProxy proxy = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CidsBroker object.
     */
    public CidsBroker() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * synchronized because the lookup take some time and multiple calls causes multiple lookups --> exception.
     *
     * @return  DOCUMENT ME!
     */
    public static synchronized CidsBroker getInstance() {
        if (brokerInstance == null) {
            brokerInstance = new CidsBroker();
        }
        return brokerInstance;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ConnectionProxy getProxy() {
        return proxy;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  proxy  DOCUMENT ME!
     */
    public void setProxy(final ConnectionProxy proxy) {
        this.proxy = proxy;
        CsvExportBackend.getInstance().init();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   tablename  DOCUMENT ME!
     * @param   domain     DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MetaClass getMetaClass(final String tablename, final String domain) {
        try {
            return CidsBean.getMetaClassFromTableName(domain, tablename);
        } catch (final Exception ex) {
            LOG.error("couldn't load metaclass for " + tablename, ex);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id      DOCUMENT ME!
     * @param   domain  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MetaClass getMetaClass(final Integer id, final String domain) {
        try {
            return proxy.getMetaClass(id, domain);
        } catch (final ConnectionException ex) {
            LOG.error("couldn't load metaclass with the id =" + id, ex);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   tablename  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MetaClass getBelisMetaClass(final String tablename) {
        return getMetaClass(tablename, BELIS_DOMAIN);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   query  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MetaObject[] getBelisMetaObject(final String query) {
        return getMetaObject(query, BELIS_DOMAIN);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   query   DOCUMENT ME!
     * @param   domain  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MetaObject[] getMetaObject(final String query, final String domain) {
        MetaObject[] mos = null;
        try {
            mos = getProxy().getMetaObjectByQuery(SessionManager.getSession().getUser(), query, domain);
        } catch (ConnectionException ex) {
            LOG.error("error retrieving metaobject by query", ex);
        }
        return mos;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean checkForDelete() {
        try {
            return getProxy().hasConfigAttr(SessionManager.getSession().getUser(), "belis.delete");
        } catch (final Exception ex) {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean checkForCreateBasic() {
        try {
            return getProxy().hasConfigAttr(SessionManager.getSession().getUser(), "belis.create.Basic");
        } catch (final Exception ex) {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean checkForEditBasic() {
        try {
            return getProxy().hasConfigAttr(SessionManager.getSession().getUser(), "belis.edit.Basic");
        } catch (final Exception ex) {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean checkForCreateVeranlassung() {
        try {
            return getProxy().hasConfigAttr(SessionManager.getSession().getUser(), "belis.create.Veranlassung");
        } catch (final Exception ex) {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean checkForEditVeranlassung() {
        try {
            return getProxy().hasConfigAttr(SessionManager.getSession().getUser(), "belis.edit.Veranlassung");
        } catch (final Exception ex) {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean checkForCreateArbeitsauftrag() {
        try {
            return getProxy().hasConfigAttr(SessionManager.getSession().getUser(), "belis.create.Arbeitsauftrag");
        } catch (final Exception ex) {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean checkForEditArbeitsauftrag() {
        try {
            return getProxy().hasConfigAttr(SessionManager.getSession().getUser(), "belis.edit.Arbeitsauftrag");
        } catch (final Exception ex) {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean checkForEditKeytables() {
        try {
            return getProxy().hasConfigAttr(SessionManager.getSession().getUser(), "belis.edit.Keytables");
        } catch (final Exception ex) {
            return false;
        }
    }

//    /**
//     * DOCUMENT ME!
//     *
//     * @return  DOCUMENT ME!
//     */
//    public boolean checkForAddToArbeitsauftrag() {
//        try {
//            return getProxy().hasConfigAttr(SessionManager.getSession().getUser(), "belis.addTo.Arbeitsauftrag");
//        } catch (final Exception ex) {
//            return false;
//        }
//    }
//
//    /**
//     * DOCUMENT ME!
//     *
//     * @return  DOCUMENT ME!
//     */
//    public boolean checkForAddToVeranlassung() {
//        try {
//            return getProxy().hasConfigAttr(SessionManager.getSession().getUser(), "belis.addTo.Veranlassung");
//        } catch (final Exception ex) {
//            return false;
//        }
//    }

    /**
     * DOCUMENT ME!
     *
     * @param   classid   DOCUMENT ME!
     * @param   objectid  DOCUMENT ME!
     * @param   domain    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MetaObject getMetaObject(final int classid, final int objectid, final String domain) {
        MetaObject mos = null;
        try {
            mos = getProxy().getMetaObject(SessionManager.getSession().getUser(), objectid, classid, domain);
        } catch (ConnectionException ex) {
            LOG.error("error retrieving metaobject by id", ex);
        }
        return mos;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  className  DOCUMENT ME!
     * @param  listener   DOCUMENT ME!
     */
    public void addListenerForKeyTableChange(final String className, final KeyTableListener listener) {
        Collection<KeyTableListener> listeners = keyTableListeners.get(className.toLowerCase());
        if (listeners == null) {
            listeners = new ArrayList<KeyTableListener>();
            keyTableListeners.put(className.toLowerCase(), listeners);
        }
        listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  className  DOCUMENT ME!
     * @param  listener   DOCUMENT ME!
     */
    public void removeListenerForKeyTableChange(final String className, final KeyTableListener listener) {
        final Collection<KeyTableListener> listeners = keyTableListeners.get(className.toLowerCase());
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                keyTableListeners.remove(className.toLowerCase());
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  className  DOCUMENT ME!
     */
    public void fireListenerForKeyTableChange(final String className) {
        final Collection<KeyTableListener> listeners = keyTableListeners.get(className.toLowerCase());

        if (listeners != null) {
            for (final KeyTableListener listener : listeners) {
                listener.keyTableChanged();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   className  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection refreshAll(String className) {
        className = className.toLowerCase();
        final MetaClass metaclass = CidsBroker.getInstance().getBelisMetaClass(className);
        if (metaclass == null) {
            return null;
        }
        final MetaObject[] mos = CidsBroker.getInstance()
                    .getBelisMetaObject("SELECT " + metaclass.getID() + ", " + metaclass.getTableName() + "."
                        + metaclass.getPrimaryKey() + " FROM " + metaclass.getTableName());
        final Collection<CidsBean> beans = new HashSet<CidsBean>();
        for (final MetaObject metaObject : mos) {
            beans.add(metaObject.getBean());
        }
        fireListenerForKeyTableChange(className);
        return (Collection)beans;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   strassenschluessel  DOCUMENT ME!
     * @param   kennziffer          DOCUMENT ME!
     * @param   laufendeNummer      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public TreeSet<BaseEntity> getObjectsByKey(final String strassenschluessel,
            final Integer kennziffer,
            final Integer laufendeNummer) throws ActionNotSuccessfulException {
        final Collection<TdtaStandortMastCustomBean> standorte = retrieveStandort(new StandortKey(
                    strassenschluessel,
                    kennziffer,
                    laufendeNummer));
        final Collection<SchaltstelleCustomBean> schaltstellen = retrieveSchaltstelle(
                strassenschluessel,
                laufendeNummer);
        final Collection<MauerlascheCustomBean> mauerlaschen = retrieveMauerlasche(strassenschluessel, laufendeNummer);
        // final TreeSet<BaseEntity> results = new TreeSet<BaseEntity>(new EntityComparator());
        final TreeSet<BaseEntity> results = new TreeSet<BaseEntity>(new ReverseComparator(new EntityComparator()));
        if (standorte != null) {
            results.addAll(standorte);
        }
        if (schaltstellen != null) {
            results.addAll(schaltstellen);
        }
        if (mauerlaschen != null) {
            results.addAll(mauerlaschen);
        }
        return results;
    }
    /**
     * ToDo is it a good idea to make a basic identity for generic id access or propertyChange support ??
     *
     * @param   key  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public Collection<TdtaStandortMastCustomBean> retrieveStandort(final StandortKey key)
            throws ActionNotSuccessfulException {
        try {
            final MetaClass metaclass = getMetaClass(TdtaStandortMastCustomBean.TABLE, BELIS_DOMAIN);

            if (key == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The search key is null");
                }
                throw new ActionNotSuccessfulException("The search key is null");
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Finde Standort: ");
                LOG.debug("Strassenschlüssel       : " + key.getStrassenschluessel().getPk());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Kennziffer: " + key.getKennziffer());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("laufende Nummer : " + key.getLaufendeNummer());
            }
            // ToDo what todo if one of the values is null --> extra flag useWildcard
            String strasse = null;
            if ((key.getStrassenschluessel() == null) || ((strasse = key.getStrassenschluessel().getPk()) == null)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("At least the strassenschluessel must be != null");
                }
                return null;
            }
            Integer kennziffer = null;
            if ((key.getKennziffer() == null) || ((kennziffer = key.getKennziffer().getKennziffer()) == null)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("kennziffer is null --> wildcard");
                }
            }
            final Integer lfdNummer;
            if ((lfdNummer = key.getLaufendeNummer()) == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("lfdNummer is null --> wildcard");
                }
            }
            if (LOG.isDebugEnabled()) {
                // ToDo optimise
                LOG.debug("Strasse: " + strasse);
            }
            final Collection<TdtaStandortMastCustomBean> result;
            if ((strasse != null) && (lfdNummer != null) && (kennziffer != null)) {
                result = (Collection<TdtaStandortMastCustomBean>)getBeanCollectionForQuery("SELECT " + metaclass
                                .getID() + ", " + metaclass.getTableName() + "." + metaclass.getPrimaryKey() + " "
                                + " FROM " + metaclass.getTableName() + " s WHERE s.strassenschluessel.pk = " + strasse
                                + " AND s.kennziffer.kennziffer = " + kennziffer + " AND s.laufendeNummer = "
                                + lfdNummer + "",
                        BELIS_DOMAIN);
            } else if ((strasse != null) && (lfdNummer != null)) {
                result = (Collection<TdtaStandortMastCustomBean>)getBeanCollectionForQuery("SELECT " + metaclass
                                .getID() + ", " + metaclass.getTableName() + "." + metaclass.getPrimaryKey() + " "
                                + " FROM " + metaclass.getTableName() + " s WHERE s.strassenschluessel.pk = " + strasse
                                + " AND s.laufendeNummer = " + lfdNummer + "",
                        BELIS_DOMAIN);
            } else if ((strasse != null) && (kennziffer != null)) {
                result = (Collection<TdtaStandortMastCustomBean>)getBeanCollectionForQuery("SELECT " + metaclass
                                .getID() + ", " + metaclass.getTableName() + "." + metaclass.getPrimaryKey() + " "
                                + " FROM " + metaclass.getTableName() + " s WHERE s.strassenschluessel.pk = " + strasse
                                + " AND s.kennziffer.kennziffer = " + kennziffer + "",
                        BELIS_DOMAIN);
            } else {
                result = (Collection<TdtaStandortMastCustomBean>)getBeanCollectionForQuery("SELECT " + metaclass
                                .getID() + ", " + metaclass.getTableName() + "." + metaclass.getPrimaryKey() + " "
                                + " FROM " + metaclass.getTableName() + " s WHERE s.strassenschluessel.pk = " + strasse
                                + "",
                        BELIS_DOMAIN);
            }
//            if()
////            List<Standort> maeste = (List<Standort>) em.createNamedQuery(
////                    "findStandort").setParameter("strassenschluessel",   key.getStrassenschluessel().getStrasse()).setParameter("kennziffer", "%").setParameter("laufendeNummer", "%").getResultList();
//                    List<Standort> maeste = (List<Standort>) em.createNamedQuery(
//                    "Standort.findStandortByStrassenschluessel").setParameter("strassenschluessel",   key.getStrassenschluessel().getStrasse()).getResultList();
            if ((result != null) && (!result.isEmpty())) {
                if (LOG.isDebugEnabled()) {
//                if (maeste. if (masize() > 1) {
//                    LOG.debug("Maeste count: " + maeste.size());
//                    throw new Exception("Multiple Maeste should only be one");
//                } else {
                    LOG.debug("Found Standort");
                }
                return new HashSet<TdtaStandortMastCustomBean>(result);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("No Standort found");
                }
                return null;
            }
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failure during Standort querying: " + key, ex);
            }
            throw new ActionNotSuccessfulException("Failure during Standort querying");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   query   DOCUMENT ME!
     * @param   domain  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection getBeanCollectionForQuery(final String query, final String domain) {
        final MetaObject[] mos = CidsBroker.getInstance().getMetaObject(query, domain);
        final Collection<CidsBean> beans = new HashSet<CidsBean>();
        for (final MetaObject metaObject : mos) {
            beans.add((CidsBean)metaObject.getBean());
        }
        return beans;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   key             DOCUMENT ME!
     * @param   laufendeNummer  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public Collection<SchaltstelleCustomBean> retrieveSchaltstelle(final String key, final Integer laufendeNummer)
            throws ActionNotSuccessfulException {
        try {
            final MetaClass metaclass = getMetaClass(SchaltstelleCustomBean.TABLE, BELIS_DOMAIN);
            if (key == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The search key is null");
                }
                throw new ActionNotSuccessfulException("The search key is null");
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Finde Schaltstelle: ");
                LOG.debug("Strassenschlüssel       : " + key);
            }
            // ToDo what todo if one of the values is null --> extra flag useWildcard
            if (key == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The strassenschluessel must be != null");
                }
                return null;
            }

            final Integer lfdNummer = null;
            if ((laufendeNummer) == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("lfdNummer is null --> wildcard");
                }
            }
            if (LOG.isDebugEnabled()) {
                // ToDo optimise
                LOG.debug("Strasse: " + key);
            }
            Collection<SchaltstelleCustomBean> result;
            if ((key != null) && (lfdNummer != null)) {
                result = (Collection<SchaltstelleCustomBean>)getBeanCollectionForQuery("SELECT " + metaclass.getID()
                                + ", " + metaclass.getTableName() + "." + metaclass.getPrimaryKey() + " " + " FROM "
                                + metaclass.getTableName() + " s WHERE s.strassenschluessel.pk = " + key
                                + " AND s.laufendeNummer = " + lfdNummer + "",
                        BELIS_DOMAIN);
            } else {
                result = (Collection<SchaltstelleCustomBean>)getBeanCollectionForQuery("SELECT " + metaclass.getID()
                                + ", " + metaclass.getTableName() + "." + metaclass.getPrimaryKey() + " " + " FROM "
                                + metaclass.getTableName() + " s WHERE s.strassenschluessel.pk = " + key + "",
                        BELIS_DOMAIN);
            }
            if ((result != null) && (!result.isEmpty())) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Found Schaltstelle");
                }
                return new HashSet<SchaltstelleCustomBean>(result);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("No Schaltstelle found");
                }
                return null;
            }
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failure during Schaltstelle querying: " + key, ex);
            }
            throw new ActionNotSuccessfulException("Failure during Schaltstelle querying");
        }
    }
    // ToDo Generic Method redundant code

    /**
     * DOCUMENT ME!
     *
     * @param   key             DOCUMENT ME!
     * @param   laufendeNummer  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public Collection<MauerlascheCustomBean> retrieveMauerlasche(final String key, final Integer laufendeNummer)
            throws ActionNotSuccessfulException {
        try {
            final MetaClass metaclass = getMetaClass(MauerlascheCustomBean.TABLE, BELIS_DOMAIN);
            if (key == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The search key is null");
                }
                throw new ActionNotSuccessfulException("The search key is null");
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Finde Mauerlasche: ");
                LOG.debug("Strassenschlüssel       : " + key);
            }
            // ToDo what todo if one of the values is null --> extra flag useWildcard
            final String strasse = null;
            if (key == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The strassenschluessel must be != null");
                }
                return null;
            }

            final Short lfdNummer = null;
            if ((laufendeNummer) == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("lfdNummer is null --> wildcard");
                }
            }
            if (LOG.isDebugEnabled()) {
                // ToDo optimise
                LOG.debug("Strasse: " + strasse);
            }
            Collection<MauerlascheCustomBean> result;
            if ((strasse != null) && (lfdNummer != null)) {
                result = (Collection<MauerlascheCustomBean>)getBeanCollectionForQuery("SELECT " + metaclass.getID()
                                + ", " + metaclass.getTableName() + "." + metaclass.getPrimaryKey() + " " + " FROM "
                                + metaclass.getTableName() + " m WHERE m.strassenschluessel.pk = " + strasse
                                + " AND m.laufendeNummer = " + lfdNummer + "",
                        BELIS_DOMAIN);
            } else {
                result = (Collection<MauerlascheCustomBean>)getBeanCollectionForQuery("SELECT " + metaclass.getID()
                                + ", " + metaclass.getTableName() + "." + metaclass.getPrimaryKey() + " " + " FROM "
                                + metaclass.getTableName() + " m WHERE m.strassenschluessel.pk = " + strasse + "",
                        BELIS_DOMAIN);
            }
            if ((result != null) && (!result.isEmpty())) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Found Mauerlasche");
                }
                return new HashSet<MauerlascheCustomBean>(result);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("No Mauerlasche found");
                }
                return null;
            }
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failure during Mauerlasche querying: " + key, ex);
            }
            throw new ActionNotSuccessfulException("Failure during Mauerlasche querying");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   objectsToSave  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public Collection<BaseEntity> saveObjects(final Collection<BaseEntity> objectsToSave)
            throws ActionNotSuccessfulException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("save objects");
        }

        final TreeSet<BaseEntity> savedEntities = new TreeSet(new ReverseComparator(new EntityComparator()));
        final ArrayList<BaseEntity> errornousEntities = new ArrayList<BaseEntity>();
        try {
            if (objectsToSave != null) {
                for (final BaseEntity curEntity : objectsToSave) {
                    try {
                        if (curEntity != null) {
                            if (curEntity.getId() == null) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("Entity Id is not set --> persisting entity (create).");
                                }
                                savedEntities.add((BaseEntity)curEntity.persist());
                            } else {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("Entity Id is set --> merge entity (update).");
                                }
                                savedEntities.add((BaseEntity)curEntity.persist());
                            }
                        }
                    } catch (Exception ex) {
                        LOG.error("Error while saving Entity: " + curEntity, ex);
                        errornousEntities.add(curEntity);
                    }
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("There are no Objects to save.");
                }
                throw new ActionNotSuccessfulException("There are no Objects to save.");
            }
        } catch (Exception ex) {
            LOG.error("Error while saving entities", ex);
            throw new ActionNotSuccessfulException("Error while saving entities", ex);
        }
        if (!errornousEntities.isEmpty()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("There were problems during saving the entities errorCount: " + errornousEntities.size());
            }
            throw new ActionNotSuccessfulException("There were problems during saving the entities", errornousEntities);
        }
        return savedEntities;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   search  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public Collection executeServerSearch(final CidsServerSearch search) throws Exception {
        return proxy.customServerSearch(proxy.getSession().getUser(), search);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   objectsToRefresh  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public Collection<BaseEntity> refreshObjects(final Collection<BaseEntity> objectsToRefresh)
            throws ActionNotSuccessfulException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("refresh objects");
        }
        final ArrayList<BaseEntity> errornousEntities = new ArrayList<BaseEntity>();
        final TreeSet refreshedObjects = new TreeSet(new ReverseComparator(new EntityComparator()));
        final HashMap<String, ArrayList> entityIDs = new HashMap();
        try {
            if ((objectsToRefresh != null) && (objectsToRefresh.size() > 0)) {
                for (final BaseEntity curEntity : objectsToRefresh) {
                    try {
                        final String className = curEntity.getMetaObject().getMetaClass().getName();
                        if (entityIDs.containsKey(className)) {
                            final ArrayList classIdList = entityIDs.get(className);
                            classIdList.add(curEntity.getId());
                        } else {
                            final ArrayList newClassIdList = new ArrayList();
                            newClassIdList.add(curEntity.getId());
                            entityIDs.put(className, newClassIdList);
                        }

                        for (final String curClassName : entityIDs.keySet()) {
                            final Integer[] ids = (Integer[])entityIDs.get(curClassName).toArray(new Integer[0]);
                            final String idIn;
                            if (ids.length == 0) {
                                idIn = "";
                            } else {
                                final StringBuilder sb = new StringBuilder();
                                sb.append(ids[0]);
                                for (int index = 1; index < ids.length; index++) {
                                    sb.append(", ");
                                    sb.append(ids[index]);
                                }
                                idIn = sb.toString();
                            }

                            final MetaClass curMetaClass = getMetaClass(curClassName, BELIS_DOMAIN);
                            final MetaObject[] mos = CidsBroker.getInstance()
                                        .getMetaObject(
                                            "SELECT "
                                            + curMetaClass.getID()
                                            + ", "
                                            + curMetaClass.getPrimaryKey()
                                            + " "
                                            + "FROM "
                                            + curMetaClass.getTableName()
                                            + " "
                                            + "WHERE id IN ("
                                            + idIn
                                            + ");",
                                            BELIS_DOMAIN);
                            final List curClassResults = new ArrayList(mos.length);
                            for (final MetaObject mo : mos) {
                                curClassResults.add(mo.getBean());
                            }
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("found: " + curClassResults);
                            }
                            refreshedObjects.addAll(curClassResults);
                        }
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Error while refreshing Entity: " + curEntity, ex);
                        }
                        errornousEntities.add(curEntity);
                    }
                }
                if (errornousEntities.isEmpty()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("refreshing of entities successful");
                    }
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("There were problems during refreshing the entities errorCount: "
                                    + errornousEntities.size());
                    }
                    throw new ActionNotSuccessfulException(
                        "There were problems during refreshing the entities",
                        errornousEntities);
                }
                return new HashSet(refreshedObjects);
            } else {
                return new HashSet();
            }
        } catch (Exception ex) {
            LOG.error("Error while refreshing entities", ex);
            throw new ActionNotSuccessfulException("Error while refreshing entities", ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   objectsToDelete  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public void deleteEntities(final Collection<BaseEntity> objectsToDelete) throws ActionNotSuccessfulException {
        try {
            if (objectsToDelete != null) {
                for (final BaseEntity objectToDelete : objectsToDelete) {
                    if (objectToDelete != null) {
                        objectToDelete.delete();
                        objectToDelete.persist();
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("deleting of entity successful");
                        }
                    }
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("deleting of all entities successful");
            }
        } catch (Exception ex) {
            LOG.error("Error while deleting entities", ex);
            throw new ActionNotSuccessfulException("Error while deleting entities", ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bb  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public TreeSet getObjectsByBoundingBox(final BoundingBox bb) throws ActionNotSuccessfulException {
        final TreeSet result = new TreeSet(new ReverseComparator(new EntityComparator()));

        final Collection<CidsBean> curClassResults = (Collection<CidsBean>)getBeanCollectionForQuery(
                "SELECT classid, objectid  FROM ("
                        + " SELECT 5 as classid, id as objectid, fk_geom FROM abzweigdose "
                        + " UNION SELECT 14, id, fk_geom FROM mauerlasche"
                        + " UNION SELECT 11, id, fk_geom FROM leitung"
                        + " UNION SELECT 15, id, fk_geom FROM schaltstelle"
                        + " UNION SELECT 29, id, fk_geom FROM tdta_standort_mast) AS geom_objects, geom"
                        + " WHERE geom.id = geom_objects.fk_geom AND envelope(ST_geometryfromtext('"
                        + bb.getGeometryFromTextLineString()
                        + "', -1)) && geom.geo_field",
                BELIS_DOMAIN);
        if (LOG.isDebugEnabled()) {
            LOG.debug("found: " + curClassResults);
        }
        result.addAll(curClassResults);
        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   objectsToLock  DOCUMENT ME!
     * @param   userString     DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     * @throws  LockAlreadyExistsException    DOCUMENT ME!
     */
    public SperreCustomBean lockEntities(final Collection<BaseEntity> objectsToLock, final String userString)
            throws ActionNotSuccessfulException, LockAlreadyExistsException {
        try {
            if (objectsToLock != null) {
                final Collection<SperreCustomBean> locks = checkIfLocked(objectsToLock);
                if ((locks != null) && !locks.isEmpty()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("A lock for the desired object is already existing");
                    }
                    // ToDo internationalise
                    throw new LockAlreadyExistsException(
                        "A lock for the desired object is already existing",
                        locks);
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("There is no Lock for the object");
                    }

                    final SperreCustomBean newLock = SperreCustomBean.createNew();
                    newLock.setTimestamp(new Date(new java.util.Date().getTime()));
                    newLock.setUserString(userString);
                    for (final BaseEntity objectToLock : objectsToLock) {
                        final SperreEntityCustomBean newLockEntity = SperreEntityCustomBean.createNew();
                        newLockEntity.setClassId(objectToLock.getMetaObject().getMetaClass().getId());
                        newLockEntity.setObjectId(objectToLock.getId());
                        newLock.getN_sperre_entities().add(newLockEntity);
                    }
                    return (SperreCustomBean)newLock.persist();
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The objectcollection to lock is null");
                }
                throw new ActionNotSuccessfulException("The objectcollection to lock is null");
            }
        } catch (ActionNotSuccessfulException e) {
            throw e;
        } catch (LockAlreadyExistsException e) {
            throw e;
        } catch (final Exception ex) {
            LOG.error("Exception while creating lock", ex);
            throw new ActionNotSuccessfulException("Exception while creating lock", ex);
        }
    }

    /**
     * /** * DOCUMENT ME! * * @param objectsToLock DOCUMENT ME! * @param userString DOCUMENT ME! * * @return DOCUMENT
     * ME! * * @throws ActionNotSuccessfulException DOCUMENT ME! * @throws LockAlreadyExistsException DOCUMENT ME!
     *
     * @param   stringArray  DOCUMENT ME!
     * @param   delimiter    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String implode(final String[] stringArray, final String delimiter) {
        if (stringArray.length == 0) {
            return "";
        } else {
            final StringBuilder sb = new StringBuilder();
            sb.append(stringArray[0]);
            for (int index = 1; index < stringArray.length; index++) {
                sb.append(delimiter);
                final String string = stringArray[index];
                if (string != null) {
                    sb.append(string);
                }
            }
            return sb.toString();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   objectToCheck  lockedObjects DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<SperreCustomBean> checkIfLocked(final Collection<BaseEntity> objectToCheck) {
        final Collection<SperreCustomBean> locks = new ArrayList<SperreCustomBean>();

        final Collection<String> whereList = new ArrayList<String>();
        for (final BaseEntity lockedObject : objectToCheck) {
            if (lockedObject != null) {
                if (lockedObject.getMetaObject().getStatus() == MetaObject.NEW) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Entity is not yet persisted. Therefore it is surely not locked");
                    }
                } else {
                    whereList.add("(class_id = " + lockedObject.getMetaObject().getMetaClass().getId()
                                + " AND object_id = " + lockedObject.getId() + ")");
                }
            } else {
                LOG.warn("Entity is null. could not check if its locked");
            }
        }

        if (whereList.isEmpty()) {
            return locks;
        }

        final String whereSnippet = implode(whereList.toArray(new String[0]), " OR ");
        final MetaClass mcSperre = getMetaClass(SperreCustomBean.TABLE, BELIS_DOMAIN);
        final MetaClass mcSperreEntity = getMetaClass(SperreEntityCustomBean.TABLE, BELIS_DOMAIN);
        final String query = "SELECT DISTINCT " + mcSperre.getID() + ", " + mcSperre.getTableName() + "."
                    + mcSperre.getPrimaryKey() + ", lock_timestamp" + " "
                    + "FROM " + mcSperre.getTableName() + ", " + mcSperreEntity.getTableName() + " "
                    + "WHERE sperre.id = fk_sperre AND " + whereSnippet + " "
                    + "ORDER BY lock_timestamp;";
        final MetaObject[] mos = getMetaObject(query, BELIS_DOMAIN);

        if (mos != null) {
            for (final MetaObject mo : mos) {
                final SperreCustomBean lock = (SperreCustomBean)mo.getBean();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("A lock for the desired object is already existing and is hold by: "
                                + lock.getUserString());
                }
                locks.add(lock);
            }
        }
        return locks;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   holdedLock  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public void unlock(final SperreCustomBean holdedLock) throws ActionNotSuccessfulException {
        try {
            if (holdedLock != null) {
                holdedLock.delete();
                holdedLock.persist();
            }
        } catch (final Exception ex) {
            LOG.error("Failure while releasing lock", ex);
            throw new ActionNotSuccessfulException("Failure while releasing lock", ex);
        }
    }
}
