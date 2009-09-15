/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.converter;

import de.cismet.belis.todo.CustomTreeTableModel;
import de.cismet.belisEE.entity.Leuchte;
import de.cismet.belisEE.entity.Standort;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

/**
 *
 * @author spuhl
 */
public class SearchResultConverter extends Converter<Set, CustomTreeTableModel> {

    private final Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    @Override
    public CustomTreeTableModel convertForward(final Set searchResults) {
        log.debug("fowardConverter called");
        final DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode(null, true);
        for (Object curObject : searchResults) {
            if (curObject instanceof Standort) {
                final DefaultMutableTreeTableNode standortNode = new DefaultMutableTreeTableNode(curObject, true);
                Set<Leuchte> leuchten = ((Standort) curObject).getLeuchten();
                if (searchResults != null) {
                    if (leuchten != null) {
                        for (Leuchte curLeuchte : leuchten) {
                            final DefaultMutableTreeTableNode leuchteNode = new DefaultMutableTreeTableNode(curLeuchte, false);
                            standortNode.add(leuchteNode);
                        }
                        root.add(standortNode);
                    }
                }
            }
        }
        //return new CustomTreeTableModel(root);
        return null;
    }

    @Override
    public Set convertReverse(final CustomTreeTableModel searchResultsTreeTrableModel) {
        log.debug("convertReverse called");
        return searchResultsTreeTrableModel.getAllUserObjects();
    }
}