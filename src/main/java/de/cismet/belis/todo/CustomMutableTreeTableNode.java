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
package de.cismet.belis.todo;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class CustomMutableTreeTableNode extends DefaultMutableTreeTableNode {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CustomMutableTreeTableNode object.
     */
    public CustomMutableTreeTableNode() {
    }

    /**
     * Creates a new CustomMutableTreeTableNode object.
     *
     * @param  arg0  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode(final Object arg0) {
        super(arg0);
    }

    /**
     * Creates a new CustomMutableTreeTableNode object.
     *
     * @param  arg0  DOCUMENT ME!
     * @param  arg1  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode(final Object arg0, final boolean arg1) {
        super(arg0, arg1);
    }

//    public void removeAllChildren(){
//        children.clear();
//    }

}
