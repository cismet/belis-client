/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.beans.belis2;

import java.io.File;

import java.net.URI;
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

    public static final String PROP__TYP = "typ";
    public static final String PROP__URL_ID = "url_id";
    public static final String PROP__DESCRIPTION = "description";
    public static final String PROP__NAME = "name";

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
        addPropertyNames(
            new String[] {
                PROP__TYP,
                PROP__URL_ID,
                PROP__DESCRIPTION,
                PROP__NAME
            });
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public URI toUri() {
        if (getUrl() != null) {
            final URL u = getUrl().getURL();
            if (u != null) {
                try {
                    return u.toURI();
                } catch (URISyntaxException e) {
                    LOG.error("URL syntax error.", e);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "DmsUrl[id=" + getId() + "]";
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
