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
import Sirius.server.newuser.User;

import org.apache.commons.collections.comparators.ReverseComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import de.cismet.cids.custom.beans.belis.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.QuerschnittCustomBean;
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
        try {
            setProxy(SessionManager.getProxy());
            if (!SessionManager.isInitialized()) {
                SessionManager.init(getProxy());
                ClassCacheMultiple.setInstance(BELIS_DOMAIN);
            }
        } catch (Throwable e) {
            LOG.fatal("no connection to the cids server possible. too bad.", e);
        }
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
    private ConnectionProxy getProxy() {
        return proxy;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  proxy  DOCUMENT ME!
     */
    private void setProxy(final ConnectionProxy proxy) {
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
        } catch (Exception exception) {
            LOG.error("couldn't load metaclass for " + tablename, exception);
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
            final User user = SessionManager.getSession().getUser();
            final ConnectionProxy proxy = getProxy();
            mos = proxy.getMetaObjectByQuery(user, query, domain);
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
            final Short kennziffer,
            final Short laufendeNummer) throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    @Override
    public Collection<TdtaStandortMastCustomBean> retrieveStandort(final StandortKey key)
            throws ActionNotSuccessfulException {
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
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
//        final TreeSet result = new TreeSet(new ReverseComparator(new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
//        try {
//            //ToDo create namedNativeQuery for reusability
//            System.out.println("GeometryText: " + bb.getGeometryFromTextLineString());
//            getMetaObject(BELIS_DOMAIN, BELIS_DOMAIN);
//            List<GeomToEntityIndex> geomToEntityIndices = (List<GeomToEntityIndex>) em.createNativeQuery(
//                    //ToDo optimize
//                    //"SELECT id,geometry FROM GeomToEntityIndex WHERE envelope(geometryfromtext(?,-1)) && geometry", GeomToEntityIndex.class).setParameter(1, bb.getGeometryFromTextLineString()).getResultList();
//                    "SELECT geom_to_entity_index.id,geom_to_entity_index.entityclass,geom_to_entity_index.entityid,geom_to_entity_index.fk_geom FROM geom_to_entity_index,geom WHERE geom.id = geom_to_entity_index.fk_geom AND envelope(geometryfromtext(?,-1)) && geom.geo_field", GeomToEntityIndex.class).setParameter(1, bb.getGeometryFromTextLineString()).getResultList();
//            if (geomToEntityIndices != null && geomToEntityIndices.size() > 0) {
//                System.out.println("There are results. size: " + geomToEntityIndices.size());
//            } else {
//                System.out.println("There are no results. size: " + geomToEntityIndices.size());
//                return result;
//            }
//
//            System.out.println("Start searching for entities");
//            final HashMap<Class, ArrayList> entityIDs = new HashMap();
//            for (GeomToEntityIndex currentIndex : geomToEntityIndices) {
////                final Object foundedEntity = getObjectForIndex(currentIndex);
////                if (foundedEntity != null) {
////                    System.out.println("Adding Entity: " + foundedEntity + " to result");
////                    result.add(foundedEntity);
////                }
//                if (entityIDs.containsKey(currentIndex.getEntityClass())) {
//                    final ArrayList classIdList = entityIDs.get(currentIndex.getEntityClass());
//                    classIdList.add(currentIndex.getEntityID());
//                } else {
//                    final ArrayList newClassIdList = new ArrayList();
//                    newClassIdList.add(currentIndex.getEntityID());
//                    entityIDs.put(currentIndex.getEntityClass(), newClassIdList);
//                }
//            }
//
//            for (Class curClass : entityIDs.keySet()) {
//                System.out.println("Class to search: " + curClass.getSimpleName() + ", id: " + curClass + " ,entityIDs: " + entityIDs.get(curClass));
//                List curClassResults = em.createNamedQuery(
//                        curClass.getSimpleName() + ".refresh").setParameter("ids", entityIDs.get(curClass)).getResultList();
//                System.out.println("found: " + curClassResults);
//                addCollectionToSortedSet(result, curClassResults);
//                //result.addAll(curClassResults);
//            }
//            System.out.println("Entities in result set: " + result.size());
//            return result;
//        } catch (Exception ex) {
//            System.out.println("Failure during boundingBox querying: " + bb);
//            ex.printStackTrace();
//            throw new ActionNotSuccessfulException("Failure during boundingBox querying");
//        }
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  sortedSet   DOCUMENT ME!
     * @param  collection  DOCUMENT ME!
     */
    private static void addCollectionToSortedSet(final SortedSet sortedSet, final Collection collection) {
        if ((sortedSet != null) && (collection != null) && (collection.size() > 0)) {
            System.out.println("adding Collection: " + collection + "to sorted set: " + sortedSet);
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
