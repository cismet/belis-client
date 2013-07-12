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
package de.cismet.belis.gui.widget;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.NavigatorAttributeEditorGui;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class ExtendedNavigatorAttributeEditorGui extends NavigatorAttributeEditorGui {

    //~ Methods ----------------------------------------------------------------

    @Override
    protected void executeAfterSuccessfullSave(final CidsBean savedBean) {
        final String classname = savedBean.getMetaObject().getMetaClass().getTableName();
        CidsBroker.getInstance().fireListenerForBeanChange(classname);
    }
}
