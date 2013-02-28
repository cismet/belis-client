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

import de.cismet.belisEEold.entity.DmsUrl;

import de.cismet.commons.server.entity.BaseEntity;

import de.cismet.tools.URLSplitter;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class DmsUrlCustomBean extends BaseEntity implements DmsUrl {

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private Integer typ;
    private String name;
    private String beschreibung;
    private UrlCustomBean url;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DmsUrlCustomBean object.
     */
    public DmsUrlCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer val) {
        this.id = val;
    }

    @Override
    public Integer getTyp() {
        return typ;
    }

    @Override
    public void setTyp(final Integer val) {
        this.typ = val;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String val) {
        this.name = val;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public void setBeschreibung(final String val) {
        this.beschreibung = val;
    }

    @Override
    public UrlCustomBean getUrl() {
        return url;
    }

    @Override
    public void setUrl(final UrlCustomBean val) {
        this.url = val;
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        return this.getId().hashCode();
    }

    @Override
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
        final DmsUrlCustomBean dmsUrlEntity = new DmsUrlCustomBean();
        final UrlCustomBean url = new UrlCustomBean();
        final UrlBaseCustomBean base = new UrlBaseCustomBean();
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
