/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belis.todo;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

/**
 *
 * @author spuhl
 */
public class CustomMutableTreeTableNode extends DefaultMutableTreeTableNode{

    public CustomMutableTreeTableNode(Object arg0, boolean arg1) {
        super(arg0, arg1);
    }

    public CustomMutableTreeTableNode(Object arg0) {
        super(arg0);
    }

    public CustomMutableTreeTableNode() {
    }
    
//    public void removeAllChildren(){
//        children.clear();
//    }

}
