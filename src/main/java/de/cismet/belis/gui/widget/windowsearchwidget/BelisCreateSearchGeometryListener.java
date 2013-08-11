/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.belis.gui.widget.windowsearchwidget;

import edu.umd.cs.piccolo.PNode;

import org.apache.log4j.Logger;

import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.AbstractCreateSearchGeometryListener;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class BelisCreateSearchGeometryListener extends AbstractCreateSearchGeometryListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(BelisCreateSearchGeometryListener.class);

    //~ Instance fields --------------------------------------------------------

    private final BelisWindowSearch ws;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MetaSearchCreateSearchGeometryListener object.
     *
     * @param  mc  DOCUMENT ME!
     * @param  ws  DOCUMENT ME!
     */
    public BelisCreateSearchGeometryListener(final MappingComponent mc, final BelisWindowSearch ws) {
        super(mc);

        this.ws = ws;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected boolean performSearch(final PureNewFeature searchFeature) {
        ws.searchWithThisGeometry(searchFeature.getGeometry());
        getMappingComponent().setInteractionMode(MappingComponent.SELECT);
        return true;
    }

    @Override
    protected PNode getPointerAnnotation() {
        return null;
    }
}
