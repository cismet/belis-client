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

import de.cismet.belis.broker.CidsBroker;

import de.cismet.cids.custom.beans.belis2.BauartCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import static de.cismet.cids.custom.beans.belis2.BauartCustomBean.TABLE;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class BaseEntity extends CidsBean {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BaseEntity.class);

    protected static final String PROP__ID = "id";
    private static int NEXT_NEW_ID = -1;

    //~ Instance fields --------------------------------------------------------

    private Integer id;

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
            return entity;
        } catch (Exception ex) {
            LOG.error("error creating " + TABLE + " bean", ex);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getId() {
        return (id < 0) ? null : id;
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getKeyString() {
        return "";
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getCompareCriteriaString() {
        return getKeyString();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getHumanReadablePosition() {
        return "";
    }
}
