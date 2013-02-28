/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.beans.belis;

import de.cismet.belisEEold.entity.UrlBase;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class UrlBaseCustomBean extends BaseEntity implements UrlBase {

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private String protPrefix;
    private String server;
    private String pfad;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UrlBaseCustomBean object.
     */
    public UrlBaseCustomBean() {
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
    public String getServer() {
        return server;
    }

    @Override
    public void setServer(final String val) {
        this.server = val;
    }

    @Override
    public String getProtPrefix() {
        return protPrefix;
    }

    @Override
    public void setProtPrefix(final String val) {
        this.protPrefix = val;
    }

    @Override
    public String getPfad() {
        return pfad;
    }

    @Override
    public void setPfad(final String val) {
        this.pfad = val;
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
        return protPrefix + server + pfad;
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.UrlBase[id=" + getId() + "]";
    }
}
