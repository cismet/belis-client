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

import de.cismet.belisEEold.entity.Url;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class UrlCustomBean extends BaseEntity implements Url {

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private UrlBaseCustomBean urlBase;
    private String objektname;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UrlCustomBean object.
     */
    public UrlCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public URL getURL() {
        try {
            return new URL(urlBase.getCompleteURLBase() + objektname);
        } catch (MalformedURLException ex) {
            // TODO log
        }
        return null;
    }

    @Override
    public UrlBaseCustomBean getUrlBase() {
        return urlBase;
    }

    @Override
    public void setUrlBase(final UrlBaseCustomBean val) {
        this.urlBase = val;
    }

    @Override
    public String getObjektname() {
        return objektname;
    }

    @Override
    public void setObjektname(final String val) {
        this.objektname = val;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
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
