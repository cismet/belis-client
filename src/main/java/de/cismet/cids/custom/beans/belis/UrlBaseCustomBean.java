/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.beans.belis;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class UrlBaseCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(UrlBaseCustomBean.class);

    public static final String TABLE = "url_base";

    private static final String PROP__PROT_PREFIX = "prot_prefix";
    private static final String PROP__PATH = "path";
    private static final String PROP__SERVER = "server";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__PROT_PREFIX,
            PROP__PATH,
            PROP__SERVER
        };

    //~ Instance fields --------------------------------------------------------

    private String prot_prefix;
    private String path;
    private String server;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UrlBaseCustomBean object.
     */
    public UrlBaseCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static UrlBaseCustomBean createNew() {
        return (UrlBaseCustomBean)createNew(TABLE);
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
    public String getProt_prefix() {
        return prot_prefix;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  prot_prefix  DOCUMENT ME!
     */
    public void setProt_prefix(final String prot_prefix) {
        final String old = this.prot_prefix;
        this.prot_prefix = prot_prefix;
        this.propertyChangeSupport.firePropertyChange(PROP__PROT_PREFIX, old, this.prot_prefix);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getPath() {
        return path;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  path  DOCUMENT ME!
     */
    public void setPath(final String path) {
        final String old = this.path;
        this.path = path;
        this.propertyChangeSupport.firePropertyChange(PROP__PATH, old, this.path);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getServer() {
        return server;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  server  DOCUMENT ME!
     */
    public void setServer(final String server) {
        final String old = this.server;
        this.server = server;
        this.propertyChangeSupport.firePropertyChange(PROP__SERVER, old, this.server);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getProtPrefix() {
        return getProt_prefix();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  protPrefix  DOCUMENT ME!
     */
    public void setProtPrefix(final String protPrefix) {
        setProt_prefix(protPrefix);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getPfad() {
        return getPath();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pfad  DOCUMENT ME!
     */
    public void setPfad(final String pfad) {
        setPath(pfad);
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
        if (other instanceof UrlBaseCustomBean) {
            final UrlBaseCustomBean anEntity = (UrlBaseCustomBean)other;
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getCompleteURLBase() {
        return getProtPrefix() + getServer() + getPfad();
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.UrlBase[id=" + getId() + "]";
    }
}
