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

import de.cismet.cismap.commons.features.SearchFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.MetaSearchFollowingCreateSearchGeometryListener;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class BelisCreateSearchGeometryListener extends MetaSearchFollowingCreateSearchGeometryListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(BelisCreateSearchGeometryListener.class);

    //~ Instance fields --------------------------------------------------------

    private final BelisWindowSearch ws;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MetaSearchCreateSearchGeometryListener object.
     *
     * @param  mc                 DOCUMENT ME!
     * @param  ws                 DOCUMENT ME!
     * @param  inputListenerName  DOCUMENT ME!
     */
    public BelisCreateSearchGeometryListener(final MappingComponent mc,
            final BelisWindowSearch ws,
            final String inputListenerName) {
        super(mc, inputListenerName);

        this.ws = ws;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected boolean performSearch(final SearchFeature searchFeature) {
        ws.searchWithThisGeometry(searchFeature.getGeometry());
        return true;
    }

    @Override
    protected PNode getPointerAnnotation() {
        return null;
    }
}
