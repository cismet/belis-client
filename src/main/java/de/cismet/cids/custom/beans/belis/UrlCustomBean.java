/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.beans.belis;

import java.net.MalformedURLException;
import java.net.URL;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class UrlCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(UrlCustomBean.class);

    public static final String TABLE = "url";

    public static final String PROP__URL_BASE_ID = "url_base_id";
    public static final String PROP__OBJECT_NAME = "object_name";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__URL_BASE_ID, PROP__OBJECT_NAME };

    //~ Instance fields --------------------------------------------------------

    private UrlBaseCustomBean url_base_id;
    private String object_name;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UrlCustomBean object.
     */
    public UrlCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static UrlCustomBean createNew() {
        return (UrlCustomBean)createNew(TABLE);
    }

    @Override
    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public UrlBaseCustomBean getUrl_base_id() {
        return url_base_id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  url_base_id  DOCUMENT ME!
     */
    public void setUrl_base_id(final UrlBaseCustomBean url_base_id) {
        final UrlBaseCustomBean old = this.url_base_id;
        this.url_base_id = url_base_id;
        this.propertyChangeSupport.firePropertyChange(PROP__URL_BASE_ID, old, this.url_base_id);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getObject_name() {
        return object_name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  object_name  DOCUMENT ME!
     */
    public void setObject_name(final String object_name) {
        final String old = this.object_name;
        this.object_name = object_name;
        this.propertyChangeSupport.firePropertyChange(PROP__OBJECT_NAME, old, this.object_name);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public URL getURL() {
        try {
            return new URL(getUrlBase().getCompleteURLBase() + getObjektname());
        } catch (MalformedURLException ex) {
            // TODO log
        }
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public UrlBaseCustomBean getUrlBase() {
        return getUrl_base_id();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  urlBase  DOCUMENT ME!
     */
    public void setUrlBase(final UrlBaseCustomBean urlBase) {
        setUrl_base_id(urlBase);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getObjektname() {
        return getObject_name();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  objektname  DOCUMENT ME!
     */
    public void setObjektname(final String objektname) {
        setObject_name(objektname);
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
        if (other instanceof UrlCustomBean) {
            final UrlCustomBean anEntity = (UrlCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getId() == null) || (anEntity.getId() == null)) {
                return false;
            } else {
                return this.getId().equals(anEntity.getId());
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.Url[id=" + getId() + "]";
    }
}
