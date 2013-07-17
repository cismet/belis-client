/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.beans.belis;

import java.io.File;

import java.net.URISyntaxException;
import java.net.URL;

import de.cismet.commons.server.entity.BaseEntity;

import de.cismet.tools.URLSplitter;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class DmsUrlCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DmsUrlCustomBean.class);

    public static final String TABLE = "dms_url";

    private static final String PROP__TYP = "typ";
    private static final String PROP__URL_ID = "url_id";
    private static final String PROP__DESCRIPTION = "description";
    private static final String PROP__NAME = "name";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__TYP,
            PROP__URL_ID,
            PROP__DESCRIPTION,
            PROP__NAME
        };

    //~ Instance fields --------------------------------------------------------

    private Integer typ;
    private UrlCustomBean url_id;
    private String description;
    private String name;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BauartCustomBean object.
     */
    public DmsUrlCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static DmsUrlCustomBean createNew() {
        return (DmsUrlCustomBean)createNew(TABLE);
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
    public Integer getTyp() {
        return typ;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  typ  DOCUMENT ME!
     */
    public void setTyp(final Integer typ) {
        final Integer old = this.typ;
        this.typ = typ;
        this.propertyChangeSupport.firePropertyChange(PROP__TYP, old, this.typ);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  name  DOCUMENT ME!
     */
    public void setName(final String name) {
        final String old = this.name;
        this.name = name;
        this.propertyChangeSupport.firePropertyChange(PROP__NAME, old, this.name);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public UrlCustomBean getUrl_id() {
        return url_id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  url_id  DOCUMENT ME!
     */
    public void setUrl_id(final UrlCustomBean url_id) {
        final UrlCustomBean old = this.url_id;
        this.url_id = url_id;
        this.propertyChangeSupport.firePropertyChange(PROP__URL_ID, old, this.url_id);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getDescription() {
        return description;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  description  DOCUMENT ME!
     */
    public void setDescription(final String description) {
        final String old = this.description;
        this.description = description;
        this.propertyChangeSupport.firePropertyChange(PROP__DESCRIPTION, old, this.description);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getBeschreibung() {
        return getDescription();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  beschreibung  DOCUMENT ME!
     */
    public void setBeschreibung(final String beschreibung) {
        setDescription(beschreibung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public UrlCustomBean getUrl() {
        return getUrl_id();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  url  DOCUMENT ME!
     */
    public void setUrl(final UrlCustomBean url) {
        setUrl_id(url);
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return System.identityHashCode(this);
        }
        return this.getId().hashCode();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public File toFile() {
        File candidate = null;
        if (getUrl() != null) {
            final URL u = getUrl().getURL();
            if (u != null) {
                try {
                    candidate = new File(u.toURI());
                } catch (URISyntaxException ex) {
                    candidate = new File(u.getPath());
                }
                if (candidate.isFile()) {
                    return candidate;
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof DmsUrlCustomBean) {
            final DmsUrlCustomBean anEntity = (DmsUrlCustomBean)other;
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
        return "de.cismet.belis.entity.DmsUrl[id=" + getId() + "]";
    }

    /**
     * DOCUMENT ME!
     *
     * @param   link         DOCUMENT ME!
     * @param   description  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  NullPointerException  DOCUMENT ME!
     */
    public static DmsUrlCustomBean createDmsURLFromLink(final String link, final String description) {
        if ((link == null) || (description == null)) {
            throw new NullPointerException();
        }
        final DmsUrlCustomBean dmsUrlEntity = DmsUrlCustomBean.createNew();
        final UrlCustomBean url = UrlCustomBean.createNew();
        final UrlBaseCustomBean base = UrlBaseCustomBean.createNew();
        final URLSplitter splitter = new URLSplitter(link);
        dmsUrlEntity.setBeschreibung(description);
        url.setUrlBase(base);
        dmsUrlEntity.setUrl(url);
        base.setPfad(splitter.getPath());
        base.setProtPrefix(splitter.getProt_prefix());
        base.setServer(splitter.getServer());
        url.setObjektname(splitter.getObject_name());
        return dmsUrlEntity;
    }
}
