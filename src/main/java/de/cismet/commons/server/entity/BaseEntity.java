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
package de.cismet.commons.server.entity;

import Sirius.server.localserver.attribute.ObjectAttribute;
import Sirius.server.middleware.types.MetaObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public abstract class BaseEntity extends CidsBean {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BaseEntity.class);

    protected static final String PROP__ID = "id";
    private static int NEXT_NEW_ID = -1;

    //~ Instance fields --------------------------------------------------------

    private final Collection<String> propertyNames = new ArrayList<String>();

    private final Map<String, Object> backupProperties = new HashMap<String, Object>();
    private boolean editAllowed = false;

    private Integer id;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BaseEntity object.
     */
    public BaseEntity() {
        addPropertyName(PROP__ID);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static int getNextNewId() {
        return NEXT_NEW_ID--;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   tableName  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected static BaseEntity createNew(final String tableName) {
        try {
            final BaseEntity entity = (BaseEntity)CidsBean.createNewCidsBeanFromTableName(
                    CidsBroker.BELIS_DOMAIN,
                    tableName);
            final int nextId = getNextNewId();
            entity.setId(nextId);
            entity.getMetaObject().setID(nextId);
            entity.setEditAllowed(true);
            return entity;
        } catch (Exception ex) {
            LOG.error("error creating " + tableName + " bean", ex);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isEditAllowed() {
        return editAllowed;
    }

    /**
     * DOCUMENT ME!
     */
    public void init() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param  editAllowed  modifiable DOCUMENT ME!
     */
    public void setEditAllowed(final boolean editAllowed) {
        this.editAllowed = this.editAllowed || editAllowed;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getId() {
        return ((id == null) || (id < 0)) ? null : id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    public void setId(final Integer id) {
        final Integer old = this.id;
        this.id = id;
        this.propertyChangeSupport.firePropertyChange(PROP__ID, old, this.id);
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return System.identityHashCode(this);
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if ((other == null) || !(other instanceof BaseEntity)) {
            return false;
        }
        if (this == other) {
            return true;
        }

        final BaseEntity otherBE = (BaseEntity)other;
        if ((getId() == null) || (otherBE.getId() == null)) {
            return false;
        }

        if (otherBE.getMetaObject().getMetaClass().equals(getMetaObject().getMetaClass())) {
            return otherBE.getId().equals(getId());
        } else {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void storeBackup() {
        backupProperties.clear();
        for (final String property : getPropertyNames()) {
            final Object object = getProperty(property);
            if (object instanceof Collection) {
                final Collection collectionCopy = new ArrayList((Collection)object);
                for (final Object collectionObject : collectionCopy) {
                    if (collectionObject instanceof BaseEntity) {
                        ((BaseEntity)collectionObject).storeBackup();
                    }
                }
                backupProperties.put(property, collectionCopy);
            } else {
                if (object instanceof BaseEntity) {
                    ((BaseEntity)object).storeBackup();
                }
                backupProperties.put(property, object);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void loadBackup() {
        final MetaObject mo = getMetaObject();
        for (final String property : getPropertyNames()) {
            final ObjectAttribute oa = mo.getAttributeByFieldName(property);
            if ((oa != null) && oa.isChanged()) {
                final Object object = backupProperties.get(property);
                if (object instanceof Collection) {
                    final Collection collectionCopy = (Collection)object;
                    getBeanCollectionProperty(property).clear();
                    for (final Object collectionObject : collectionCopy) {
                        if (collectionObject instanceof BaseEntity) {
                            ((BaseEntity)collectionObject).loadBackup();
                            try {
                                getBeanCollectionProperty(property).add((BaseEntity)collectionObject);
                            } catch (Exception ex) {
                                LOG.error("error while setting collection copy", ex);
                            }
                        }
                    }
                } else {
                    if (object instanceof BaseEntity) {
                        ((BaseEntity)object).loadBackup();
                    }
                    try {
                        setProperty(property, object);
                    } catch (Exception ex) {
                        LOG.error("error while setting object copy", ex);
                    }
                }
            }
        }
    }

    @Override
    public String[] getPropertyNames() {
        return propertyNames.toArray(new String[0]);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  propertyName  DOCUMENT ME!
     */
    protected final void addPropertyName(final String propertyName) {
        this.propertyNames.add(propertyName);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  propertyNames  DOCUMENT ME!
     */
    protected final void addPropertyNames(final String[] propertyNames) {
        this.propertyNames.addAll(Arrays.asList(propertyNames));
    }
}
