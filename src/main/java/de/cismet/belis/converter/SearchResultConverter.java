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
package de.cismet.belis.converter;

import org.apache.log4j.Logger;

import org.jdesktop.beansbinding.Converter;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

import java.util.Collection;
import java.util.Set;

import de.cismet.belis.todo.CustomTreeTableModel;

import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class SearchResultConverter extends Converter<Set, CustomTreeTableModel> {

    //~ Instance fields --------------------------------------------------------

    private final Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    //~ Methods ----------------------------------------------------------------

    @Override
    public CustomTreeTableModel convertForward(final Set searchResults) {
        if (log.isDebugEnabled()) {
            log.debug("fowardConverter called");
        }
        final DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode(null, true);
        for (final Object curObject : searchResults) {
            if (curObject instanceof TdtaStandortMastCustomBean) {
                final DefaultMutableTreeTableNode standortNode = new DefaultMutableTreeTableNode(curObject, true);
                final Collection<TdtaLeuchtenCustomBean> leuchten = ((TdtaStandortMastCustomBean)curObject)
                            .getLeuchten();
                if (searchResults != null) {
                    if (leuchten != null) {
                        for (final TdtaLeuchtenCustomBean curLeuchte : leuchten) {
                            final DefaultMutableTreeTableNode leuchteNode = new DefaultMutableTreeTableNode(
                                    curLeuchte,
                                    false);
                            standortNode.add(leuchteNode);
                        }
                        root.add(standortNode);
                    }
                }
            }
        }
        // return new CustomTreeTableModel(root);
        return null;
    }

    @Override
    public Set convertReverse(final CustomTreeTableModel searchResultsTreeTrableModel) {
        if (log.isDebugEnabled()) {
            log.debug("convertReverse called");
        }
        return searchResultsTreeTrableModel.getAllUserObjects();
    }
}
