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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import de.cismet.belis.gui.widget.KeyTableListener;

import de.cismet.belis.server.search.HighestLfdNummerSearch;

import de.cismet.belisEE.bean.interfaces.BelisServerRemote;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.exception.LockAlreadyExistsException;

import de.cismet.belisEE.util.EntityComparator;
import de.cismet.belisEE.util.LeuchteComparator;
import de.cismet.belisEE.util.StandortKey;

import de.cismet.cids.custom.beans.belis.BauartCustomBean;
import de.cismet.cids.custom.beans.belis.GeomCustomBean;
import de.cismet.cids.custom.beans.belis.GeomToEntityIndexCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.QuerschnittCustomBean;
import de.cismet.cids.custom.beans.belis.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis.SperreCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyBezirkCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyEnergielieferantCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKlassifizierungCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyLeuchtentypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMastartCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMasttypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.BoundingBox;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.entity.GeoBaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class CidsBroker implements BelisServerRemote {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CidsBroker.class);
    public static final String BELIS_DOMAIN = "BELIS";
    private static CidsBroker brokerInstance = null;

    //~ Instance fields --------------------------------------------------------

    HashMap<String, Collection<CidsBean>> keyTableCollections = new HashMap<String, Collection<CidsBean>>();
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
     * @param  className  DOCUMENT ME!
     * @param  listener   DOCUMENT ME!
     */
    public void addListenerForKeyTableChange(final String className, final KeyTableListener listener) {
        Collection<KeyTableListener> listeners = keyTableListeners.get(className.toLowerCase());
        if (listeners == null) {
            listeners = new ArrayList<KeyTableListener>();
            keyTableListeners.put(className, listeners);
        }
        listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  classname  DOCUMENT ME!
     * @param  listener   DOCUMENT ME!
     */
    public void removeListenerForKeyTableChange(final String classname, final KeyTableListener listener) {
        final Collection<KeyTableListener> listeners = keyTableListeners.get(classname.toLowerCase());
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                keyTableListeners.remove(classname);
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
        keyTableCollections.put(className, beans);
        fireListenerForKeyTableChange(className);
        return (Collection)beans;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   className  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public Collection getAll(String className) throws ActionNotSuccessfulException {
        className = className.toLowerCase();
        if (keyTableCollections.containsKey(className)) {
            return keyTableCollections.get(className);
        } else {
            return refreshAll(className);
        }
    }

    @Override
    public Collection<TkeyStrassenschluesselCustomBean> getAllStrassenschluessel() throws ActionNotSuccessfulException {
        return getAll(TkeyStrassenschluesselCustomBean.TABLE);
    }

    @Override
    public Collection<TkeyEnergielieferantCustomBean> getAllEnergielieferanten() throws ActionNotSuccessfulException {
        return getAll(TkeyEnergielieferantCustomBean.TABLE);
    }

    @Override
    public Collection<TkeyKennzifferCustomBean> getAllKennziffer() throws ActionNotSuccessfulException {
        return getAll(TkeyKennzifferCustomBean.TABLE);
    }

    @Override
    public Collection<TkeyBezirkCustomBean> getAllStadtbezirke() throws ActionNotSuccessfulException {
        return getAll(TkeyBezirkCustomBean.TABLE);
    }

    @Override
    public Collection<TkeyMastartCustomBean> getAllMastarten() throws ActionNotSuccessfulException {
        return getAll(TkeyMastartCustomBean.TABLE);
    }

    @Override
    public Collection<TkeyMasttypCustomBean> getAllMasttypen() throws ActionNotSuccessfulException {
        return getAll(TkeyMasttypCustomBean.TABLE);
    }

    @Override
    public Collection<TkeyKlassifizierungCustomBean> getAllKlassifizierungen() throws ActionNotSuccessfulException {
        return getAll(TkeyKlassifizierungCustomBean.TABLE);
    }

    @Override
    public Collection<TkeyUnterhMastCustomBean> getAllUnterhaltMast() throws ActionNotSuccessfulException {
        return getAll(TkeyUnterhMastCustomBean.TABLE);
    }

    @Override
    public Collection<TkeyUnterhLeuchteCustomBean> getAllUnterhaltLeuchte() throws ActionNotSuccessfulException {
        return getAll(TkeyUnterhLeuchteCustomBean.TABLE);
    }

    @Override
    public Collection<MaterialLeitungCustomBean> getAllMaterialLeitung() throws ActionNotSuccessfulException {
        return getAll(MaterialLeitungCustomBean.TABLE);
    }

    @Override
    public Collection<LeitungstypCustomBean> getAllLeitungstypen() throws ActionNotSuccessfulException {
        return getAll(LeitungstypCustomBean.TABLE);
    }

    @Override
    public Collection<QuerschnittCustomBean> getAllQuerschnitte() throws ActionNotSuccessfulException {
        return getAll(QuerschnittCustomBean.TABLE);
    }

    @Override
    public Collection<MaterialMauerlascheCustomBean> getAllMaterialMauerlasche() throws ActionNotSuccessfulException {
        return getAll(MaterialMauerlascheCustomBean.TABLE);
    }

    @Override
    public Collection<BauartCustomBean> getAllBauarten() throws ActionNotSuccessfulException {
        return getAll(BauartCustomBean.TABLE);
    }

    @Override
    public Collection<TkeyLeuchtentypCustomBean> getAllLeuchtentypen() throws ActionNotSuccessfulException {
        return getAll(TkeyLeuchtentypCustomBean.TABLE);
    }

    @Override
    public Collection<TkeyDoppelkommandoCustomBean> getAllDoppelkommando() throws ActionNotSuccessfulException {
        return getAll(TkeyDoppelkommandoCustomBean.TABLE);
    }

    @Override
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
        final TreeSet<BaseEntity> results = new TreeSet<BaseEntity>(new ReverseComparator(
                    new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
        if (standorte != null) {
            addCollectionToSortedSet(results, standorte);
        }
        if (schaltstellen != null) {
            addCollectionToSortedSet(results, schaltstellen);
            // results.addAll(schaltstellen);
        }
        if (mauerlaschen != null) {
            addCollectionToSortedSet(results, mauerlaschen);
            // results.addAll(mauerlaschen);
        }
        return results;
    }

    // ToDo is it a good idea to make a basic identity for generic id access or propertyChange support ??
    @Override
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
    private Collection getBeanCollectionForQuery(final String query, final String domain) {
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

    @Override
    public Collection<BaseEntity> saveObjects(final Collection<BaseEntity> objectsToSave, final String userString)
            throws ActionNotSuccessfulException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("save objects");
        }
        final TreeSet<BaseEntity> savedEntities = new TreeSet(new ReverseComparator(
                    new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
        final ArrayList<BaseEntity> errornousEntities = new ArrayList<BaseEntity>();
        try {
            if (objectsToSave != null) {
                for (BaseEntity curEntity : objectsToSave) {
                    try {
                        if (curEntity != null) {
                            if (curEntity.getId() == null) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("Entity Id is not set --> persisting entity (create).");
                                }
                                if ((curEntity instanceof TdtaStandortMastCustomBean)
                                            && (((TdtaStandortMastCustomBean)curEntity).getLaufendeNummer() == null)) {
                                    if (LOG.isDebugEnabled()) {
                                        // ToDo maybe is already set
                                        LOG.debug("There is no laufende Nummer set, getting automaticly next one");
                                    }
                                    determineNextLaufendenummer((TdtaStandortMastCustomBean)curEntity);
                                } else {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("Laufende Nummer already set no need to determine next one");
                                    }
                                }
                                curEntity = (BaseEntity)curEntity.persist();
//                                if (curEntity instanceof Standort) {
//                                   final Set<Leuchte> leuchten =((Standort)curEntity).getLeuchten();
//                                   if(leuchten != null && leuchten.size() > 0){
//                                       LOG.debug("Standort has leuchten, setting backrefs from Leuchte to Standort");
//                                       for(Leuchte curLeuchte:leuchten){
//                                           curLeuchte.setStandort((Standort)curEntity);
//                                           em.persist(curLeuchte);
//                                       }
//                                   }
//                                }
                                if (curEntity instanceof GeoBaseEntity) {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("instance is GeoBaseEntity");
                                    }
                                    updateGeomIndex((GeoBaseEntity)curEntity, true);
                                }
                                savedEntities.add(curEntity);
                            } else {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("Entity Id is set --> merge entity (update).");
                                }
                                final BaseEntity refreshedEntity = (BaseEntity)curEntity.persist();
                                if (refreshedEntity instanceof GeoBaseEntity) {
                                    // ToDo
                                    // updateGeomIndex((GeoBaseEntity) curEntity, false);
                                    updateGeomIndex((GeoBaseEntity)refreshedEntity, false);
                                }
                                savedEntities.add(refreshedEntity);
                            }
                        } else {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Entity is null --> skipping Entity");
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
        if (errornousEntities.isEmpty()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Saving of entities successful");
            }
        } else {
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
     * @param   standort  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public TdtaStandortMastCustomBean determineNextLaufendenummer(final TdtaStandortMastCustomBean standort)
            throws ActionNotSuccessfulException {
        return determineNextLaufendenummer(standort, -1);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   standort       DOCUMENT ME!
     * @param   minimalNumber  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    @Override
    public TdtaStandortMastCustomBean determineNextLaufendenummer(final TdtaStandortMastCustomBean standort,
            final Integer minimalNumber) throws ActionNotSuccessfulException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("determine next laufendenummer");
        }
        if (standort != null) {
            // ToDo would be cooler to use the objects itself as parameter;
            String strassenschluessel = null;
            if ((standort.getStrassenschluessel() == null)
                        || ((strassenschluessel = standort.getStrassenschluessel().getPk()) == null)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("strassenschluessel must be != null");
                }
            }
            Integer kennziffer = null;
            if ((standort.getKennziffer() == null)
                        || ((kennziffer = standort.getKennziffer().getKennziffer()) == null)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("kennziffer must be != null");
                }
            }
            if ((kennziffer != null) && (strassenschluessel != null)) {
                try {
                    final List<Integer> highestNumbers = (List<Integer>)proxy.customServerSearch(proxy.getSession()
                                    .getUser(),
                            new HighestLfdNummerSearch(strassenschluessel, kennziffer));

                    final Integer highestNumber = (highestNumbers.isEmpty()) ? null : highestNumbers.get(0);
                    if ((highestNumber == null)) {
                        if (minimalNumber > -1) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("there is no highest laufende nummer using minimal: " + minimalNumber);
                            }
                            standort.setLaufendeNummer(minimalNumber);
                        } else {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("there is no highest laufende nummer and no minimalNumber using 0.");
                            }
                            standort.setLaufendeNummer(0);
                        }
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("the highest laufende nummer is: " + highestNumber);
                        }
                        if ((minimalNumber > -1) && (minimalNumber > highestNumber)) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Minimal " + minimalNumber
                                            + " is greater than highest number using minimal number");
                            }
                            standort.setLaufendeNummer(minimalNumber);
                        } else {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Minimal is -1 or smaller than highest number: " + minimalNumber);
                                LOG.debug("using highestnumber +1 ");
                            }
                            // ToDo best way to add Short ?
                            standort.setLaufendeNummer(highestNumber + ((short)1));
                        }
                    }
                    setLeuchtenPropertiesDependingOnStandort(standort);
                    return standort;
                } catch (Exception ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Error while querying entity", ex);
                    }
                    throw new ActionNotSuccessfulException("Error while querying highest laufendenummer", ex);
                }
            }
        }
        throw new ActionNotSuccessfulException(
            "Not possible to determine laufendenummer kennziffer and strassenschlüssel of standort must be set.");
    }

    /**
     * DOCUMENT ME!
     *
     * @param  standort  DOCUMENT ME!
     */
    private void setLeuchtenPropertiesDependingOnStandort(final TdtaStandortMastCustomBean standort) {
        if (standort != null) {
            final Collection<TdtaLeuchtenCustomBean> leuchten = standort.getLeuchten();
            if (leuchten != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Setting properties of Leuchte.");
                }
                if (standort.isStandortMast()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Standort is Mast.");
                    }
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Standort is no Mast.");
                    }
                }
                // ToDo check if there is only one Leuchte per no mast standort
                for (final TdtaLeuchtenCustomBean curLeuchte : leuchten) {
                    if (standort.isStandortMast()) {
                        curLeuchte.setStrassenschluessel(standort.getStrassenschluessel());
                        curLeuchte.setKennziffer(standort.getKennziffer());
                    }
                    curLeuchte.setLaufendeNummer(standort.getLaufendeNummer());
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("No leuchten to set properties.");
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   entity  DOCUMENT ME!
     * @param   isNew   DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    private void updateGeomIndex(final GeoBaseEntity entity, final boolean isNew) throws ActionNotSuccessfulException {
        try {
            if (isNew) {
                if (entity.getGeometrie() != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Persisted GeoBaseEntity has Geom object. Creating entry in index");
                    }
                    final GeomToEntityIndexCustomBean newIndex = GeomToEntityIndexCustomBean.createNew();
                    newIndex.setGeometry(entity.getGeometrie());
                    newIndex.setEntityClassId(entity.getMetaObject().getMetaClass().getId());
                    final Integer entityID = entity.getId();
                    if (entityID != null) {
                        newIndex.setEntityID(entityID);
                    } else {
                        throw new ActionNotSuccessfulException("Entity has no id, can't create geom index");
                    }
                    newIndex.persist();
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Persisted GeoBaseEntity is saved checking Geom index");
                }
                if (entity.getGeometrie() != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Geometry is available.");
                    }
                    final GeomToEntityIndexCustomBean indexBean = getGeomIndexById(entity.getGeometrie().getId());
                    if (indexBean != null) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("IndexAvailable.Updating Geometry");
                        }
                        indexBean.setGeometry(entity.getGeometrie());
                        indexBean.persist();
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Warning no index available. Creating Index");
                        }
                        updateGeomIndex(entity, true);
                    }
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Entity has no geometry set. Doing nothing");
                    }
                }
            }
        } catch (final Exception ex) {
            LOG.error("error while persist geom index", ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    private GeomToEntityIndexCustomBean getGeomIndexById(final Integer id) throws ActionNotSuccessfulException {
        if (id != null) {
            final MetaClass mcGeomToEntityIndex = getMetaClass(GeomToEntityIndexCustomBean.TABLE, BELIS_DOMAIN);
            final MetaClass mcGeom = getMetaClass(GeomCustomBean.TABLE, BELIS_DOMAIN);
            final MetaObject[] mos = CidsBroker.getInstance()
                        .getMetaObject("SELECT " + mcGeomToEntityIndex.getID() + ", "
                            + "gtei." + mcGeomToEntityIndex.getPrimaryKey() + " "
                            + "FROM "
                            + mcGeomToEntityIndex.getTableName() + " AS gtei, "
                            + mcGeom.getTableName() + " AS g "
                            + "WHERE gtei.fk_geom = g.id "
                            + "AND g.id = " + id + ";",
                            BELIS_DOMAIN);

            if ((mos != null) && (mos.length > 0)) {
                final GeomToEntityIndexCustomBean geomIndex = (GeomToEntityIndexCustomBean)mos[0].getBean();
                return geomIndex;
            }
        }
        return null;
    }

    @Override
    public Collection<BaseEntity> refreshObjects(final Collection<BaseEntity> objectsToRefresh)
            throws ActionNotSuccessfulException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("refresh objects");
        }
        final ArrayList<BaseEntity> errornousEntities = new ArrayList<BaseEntity>();
        final TreeSet refreshedObjects = new TreeSet(new ReverseComparator(
                    new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
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
                            addCollectionToSortedSet(refreshedObjects, curClassResults);
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

    @Override
    public void deleteEntity(final BaseEntity objectToDelete, final String userString)
            throws ActionNotSuccessfulException {
        if (objectToDelete != null) {
            try {
                if (objectToDelete instanceof TdtaStandortMastCustomBean) {
                    if (((TdtaStandortMastCustomBean)objectToDelete).getLeuchten() != null) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Leuchten des zu löschenden Standorts: "
                                        + ((TdtaStandortMastCustomBean)objectToDelete).getLeuchten());
                        }
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("leuchten null");
                        }
                    }
                }
                final BaseEntity updatedEntity = (BaseEntity)objectToDelete.persist();
                if (objectToDelete instanceof TdtaStandortMastCustomBean) {
                    if (((TdtaStandortMastCustomBean)objectToDelete).getLeuchten() != null) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Leuchten des zu löschenden Standorts: "
                                        + ((TdtaStandortMastCustomBean)objectToDelete).getLeuchten());
                        }
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("leuchten null");
                        }
                    }
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("UpdatedEntity: " + updatedEntity);
                }
                updatedEntity.delete();
                updatedEntity.persist();
            } catch (Exception ex) {
                LOG.error("Error while deleting entity", ex);
                throw new ActionNotSuccessfulException("Error while deleting entity", ex);
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Object to delete == null");
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleting of entity successful");
        }
    }

    @Override
    public void deleteEntities(final Collection<BaseEntity> objectsToDelete, final String userString)
            throws ActionNotSuccessfulException {
        if (objectsToDelete != null) {
            try {
                for (final BaseEntity curObject : objectsToDelete) {
                    deleteEntity(curObject, userString);
                }
            } catch (Exception ex) {
                LOG.error("Error while deleting entities", ex);
                throw new ActionNotSuccessfulException("Error while deleting entities", ex);
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Objects to delete == null");
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleting of all entities successful");
        }
    }

    @Override
    public TreeSet getObjectsByBoundingBox(final BoundingBox bb) throws ActionNotSuccessfulException {
        final TreeSet result = new TreeSet(new ReverseComparator(
                    new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
        try {
            final MetaClass metaclass = getMetaClass(GeomToEntityIndexCustomBean.TABLE, BELIS_DOMAIN);
            final Collection<GeomToEntityIndexCustomBean> geomToEntityIndices =
                (Collection<GeomToEntityIndexCustomBean>)getBeanCollectionForQuery("SELECT " + metaclass.getID() + ", "
                            + metaclass.getTableName() + "." + metaclass.getPrimaryKey() + " " + " FROM "
                            + metaclass.getTableName() + ", "
                            + " geom g WHERE g.id = " + metaclass.getTableName()
                            + ".fk_geom AND envelope(geometryfromtext('"
                            + bb.getGeometryFromTextLineString() + "', -1)) && g.geo_field",
                    BELIS_DOMAIN);
            if ((geomToEntityIndices == null) || (geomToEntityIndices.size() <= 0)) {
                return result;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Start searching for entities");
            }
            final HashMap<Integer, ArrayList> entityIDs = new HashMap();
            for (final GeomToEntityIndexCustomBean currentIndex : geomToEntityIndices) {
                if (entityIDs.containsKey(currentIndex.getEntityClassId())) {
                    final ArrayList classIdList = entityIDs.get(currentIndex.getEntityClassId());
                    classIdList.add(currentIndex.getEntityID());
                } else {
                    final ArrayList newClassIdList = new ArrayList();
                    newClassIdList.add(currentIndex.getEntityID());
                    entityIDs.put(currentIndex.getEntityClassId(), newClassIdList);
                }
            }

            for (final Integer curClassId : entityIDs.keySet()) {
                final MetaClass curMetaclass = getMetaClass(curClassId, BELIS_DOMAIN);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Class to search - id: " + curClassId + " ,entityIDs: " + entityIDs.get(curClassId));
                }
                final Collection<CidsBean> curClassResults = (Collection<CidsBean>)getBeanCollectionForQuery("SELECT "
                                + curMetaclass.getID() + ", " + curMetaclass.getTableName() + "."
                                + curMetaclass.getPrimaryKey() + " " + " FROM  " + curMetaclass.getTableName()
                                + " WHERE id IN "
                                + entityIDs.get(curClassId).toString().replace('[', '(').replace(']', ')') + "",
                        BELIS_DOMAIN);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("found: " + curClassResults);
                }
                addCollectionToSortedSet(result, curClassResults);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Entities in result set: " + result.size());
            }
            return result;
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failure during boundingBox querying: " + bb, ex);
            }
            throw new ActionNotSuccessfulException("Failure during boundingBox querying");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  sortedSet   DOCUMENT ME!
     * @param  collection  DOCUMENT ME!
     */
    private static void addCollectionToSortedSet(final SortedSet sortedSet, final Collection collection) {
        if ((sortedSet != null) && (collection != null) && (collection.size() > 0)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("adding Collection: " + collection + "to sorted set: " + sortedSet);
            }
            for (final Object curObject : collection) {
                sortedSet.add(curObject);
            }
        }
    }

    @Override
    public Object getObjectsByGeom(final GeomCustomBean geom) throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    @Override
    public boolean checkIfStandortExists(final TdtaStandortMastCustomBean standort)
            throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
        return false;
    }

    @Override
    public SperreCustomBean lockEntity(final BaseEntity objectToLock, final String userString)
            throws ActionNotSuccessfulException, LockAlreadyExistsException {
        try {
            if (objectToLock != null) {
                // datamodell refactoring 22.10.07
                final SperreCustomBean lock = isEntityLocked(objectToLock);
                if (lock != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("A lock for the desired object is already existing and is hold by: "
                                    + lock.getUserString());
                    }
                    // ToDo internationalise
                    throw new LockAlreadyExistsException("A lock for the desired object is already existing", lock);
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("There is no Lock for the object");
                    }
                    final SperreCustomBean newLock = SperreCustomBean.createNew();
                    newLock.setClassId(objectToLock.getMetaObject().getMetaClass().getId());
                    newLock.setTimestamp(new Date());
                    newLock.setUserString(userString);
                    newLock.setObjectId(objectToLock.getId());
                    return (SperreCustomBean)newLock.persist();
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The object to lock is null");
                }
                throw new ActionNotSuccessfulException("The object to lock is null");
            }
        } catch (final Exception ex) {
            LOG.error("Exception while creating lock", ex);
            throw new ActionNotSuccessfulException("Exception while creating lock", ex);
        }
    }

    @Override
    public Collection<SperreCustomBean> lockEntity(final Collection<BaseEntity> objectsToLock, final String userString)
            throws ActionNotSuccessfulException, LockAlreadyExistsException {
        final ArrayList<SperreCustomBean> lockedObjects = new ArrayList<SperreCustomBean>();
        final ArrayList<SperreCustomBean> exisitingLock = new ArrayList<SperreCustomBean>();

        if (objectsToLock != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Objects to lock count: " + objectsToLock.size());
            }
            for (final BaseEntity curObject : objectsToLock) {
                try {
                    lockedObjects.add(lockEntity(curObject, userString));
                } catch (ActionNotSuccessfulException ex) {
                    LOG.error("Error while locking one of the objects", ex);
                    if (!lockedObjects.isEmpty()) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Unlocking already locked objects");
                        }
                        try {
                            unlockEntity(lockedObjects);
                        } catch (ActionNotSuccessfulException ex2) {
                            LOG.error("Error while unlocking already locked objects", ex);
                            throw new ActionNotSuccessfulException(
                                "Tried to lock Objects --> faild. Tried to unlock the successful locked objects --> faild",
                                ex);
                        }
                    }
                    throw new ActionNotSuccessfulException("Error while locking one of the objects.", ex);
                } catch (LockAlreadyExistsException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("One of the Objects is already locked --> trying the rest of the objects");
                    }
                    exisitingLock.addAll(ex.getAlreadyExisingLocks());
                    continue;
                }
            }
            if (exisitingLock.isEmpty()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("locking of Objects was successful");
                }
                return new ArrayList<SperreCustomBean>(lockedObjects);
            } else {
                if (!lockedObjects.isEmpty()) {
                    try {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("unlocking of already locked objects (Cleanup)");
                        }
                        final Collection failedUnlocks = unlockEntity(lockedObjects);
                    } catch (ActionNotSuccessfulException ex2) {
                        LOG.error("Error while unlocking already locked objects", ex2);
                        throw new ActionNotSuccessfulException(
                            "Tried to lock Objects --> faild. Tried to unlock the successful locked objects --> faild",
                            ex2);
                    }
                }
                throw new LockAlreadyExistsException("Error some of the objects are already locked", exisitingLock);
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("The set of objects to lock is null");
            }
            throw new ActionNotSuccessfulException("The set of objects to lock is null");
        }
    }

    @Override
    public SperreCustomBean isEntityLocked(final BaseEntity lockedObject) {
        // datamodell refactoring 22.10.07
        if (lockedObject != null) {
            final Integer id = lockedObject.getId();
            if (id == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Entity is not yet persisted. Therefore it is surely not locked");
                }
                return null;
            }

            final MetaClass mcSperreBean = getMetaClass(SperreCustomBean.TABLE, BELIS_DOMAIN);

            final String query = "SELECT " + mcSperreBean.getID() + ", " + mcSperreBean.getTableName() + "."
                        + mcSperreBean.getPrimaryKey() + " "
                        + "FROM " + mcSperreBean.getTableName() + " "
                        + "WHERE "
                        + "object_id = " + id
                        + "AND class_id = " + lockedObject.getMetaObject().getMetaClass().getId()
                        + ";";
            final MetaObject[] mos = getMetaObject(query, BELIS_DOMAIN);

            if ((mos == null) || (mos.length <= 0)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("There is no lock for the given Object");
                }
                return null;
            }
            final SperreCustomBean lock = (SperreCustomBean)mos[0].getBean();
            if (LOG.isDebugEnabled()) {
                LOG.debug("A lock for the desired object is already existing and is hold by: " + lock.getUserString());
            }
            return lock;
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("The object to check is null");
            }
            return null;
        }
    }

    @Override
    public SperreCustomBean tryToLockEntity(final BaseEntity objectToLock, final String userString)
            throws ActionNotSuccessfulException, LockAlreadyExistsException {
        SperreCustomBean exisitingLock = null;
        if ((exisitingLock = isEntityLocked(objectToLock)) == null) {
            return lockEntity(objectToLock, userString);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Entity is locked");
            }
            throw new LockAlreadyExistsException("A lock for the desired object is already existing", exisitingLock);
        }
    }

    @Override
    public void unlockEntity(final SperreCustomBean holdedLock) throws ActionNotSuccessfulException {
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

    @Override
    public Collection<Object> unlockEntity(final Collection<SperreCustomBean> objectsToUnlock)
            throws ActionNotSuccessfulException {
        final ArrayList unsuccessfulUnlocking = new ArrayList();
        if (objectsToUnlock != null) {
            for (final BaseEntity curObject : objectsToUnlock) {
                try {
                    unlockEntity(curObject);
                } catch (ActionNotSuccessfulException ex) {
                    unsuccessfulUnlocking.add(curObject);
                }
            }
            return new HashSet<Object>(unsuccessfulUnlocking);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("The set of objects to unlock is null");
            }
            throw new ActionNotSuccessfulException("The set of objects to unlock is null");
        }
    }

    @Override
    public void unlockEntity(final BaseEntity objectToUnlock) throws ActionNotSuccessfulException {
        final SperreCustomBean entityLock = isEntityLocked(objectToUnlock);
        if (entityLock != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("There is an Lock for the given Entity");
            }
            unlockEntity(entityLock);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("There is no Lock for the given Entity");
            }
        }
    }
}
