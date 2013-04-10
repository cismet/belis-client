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

import org.openide.util.Exceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

import de.cismet.belisEE.bean.interfaces.BelisServerRemote;

import de.cismet.belisEE.entity.GeomToEntityIndex;
import de.cismet.belisEE.entity.Lock;

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

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.BoundingBox;

import de.cismet.commons.server.entity.BaseEntity;

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
     * @param   className  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Collection getAll(final String className) {
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
        return (Collection)beans;
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
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    @Override
    public Collection<BaseEntity> refreshObjects(final Collection<BaseEntity> objectsToSave)
            throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    @Override
    public void deleteEntity(final BaseEntity objectsToDelete, final String userString)
            throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteEntities(final Collection<BaseEntity> objectsToDelete, final String userString)
            throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
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
            for (final GeomToEntityIndex currentIndex : geomToEntityIndices) {
                if (entityIDs.containsKey(Integer.parseInt(currentIndex.getEntityClassId()))) {
                    final ArrayList classIdList = entityIDs.get(Integer.parseInt(currentIndex.getEntityClassId()));
                    classIdList.add(currentIndex.getEntityID());
                } else {
                    final ArrayList newClassIdList = new ArrayList();
                    newClassIdList.add(currentIndex.getEntityID());
                    entityIDs.put(Integer.parseInt(currentIndex.getEntityClassId()), newClassIdList);
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
    public TdtaStandortMastCustomBean determineNextLaufendenummer(final TdtaStandortMastCustomBean standort,
            final Short minimalNumber) throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    @Override
    public Lock lockEntity(final Object objectToLock, final String userString) throws ActionNotSuccessfulException,
        LockAlreadyExistsException {
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    @Override
    public Collection<Lock> lockEntity(final Collection<Object> objectsToLock, final String userString)
            throws ActionNotSuccessfulException, LockAlreadyExistsException {
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    @Override
    public Lock isEntityLocked(final Object lockedObject) throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    @Override
    public Lock tryToLockEntity(final Object lockedObject, final String userString) throws ActionNotSuccessfulException,
        LockAlreadyExistsException {
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    @Override
    public void unlockEntity(final Object objectToUnlock) throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Object> unlockEntity(final Collection<Object> objectsToUnlock)
            throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    @Override
    public void unlockEntity(final Lock holdedLock) throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
