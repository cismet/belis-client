/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * EntityDetailWidget.java
 *
 * Created on 23. März 2009, 11:27
 */
package de.cismet.commons2.architecture.widget;

import org.apache.log4j.Logger;

import org.jdom.Element;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.commons.architecture.broker.AdvancedPluginBroker;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */

//ToDo generic Entity Panel -- not yet implemented --> if a standardized detail panel occurs in another special application
//like lagis or verdis implement it. Maybe a default like the navigator editor would be cool.
public class EntityDetailWidget extends DefaultWidget {

    //~ Instance fields --------------------------------------------------------

    private final Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panMain;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form EntityDetailWidget.
     *
     * @param  broker  DOCUMENT ME!
     */
    public EntityDetailWidget(final BelisBroker broker) {
        super(broker);
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        panMain = new javax.swing.JPanel();

        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ObjectProperty.create(),
                panMain,
                org.jdesktop.beansbinding.BeanProperty.create("background"));
        bindingGroup.addBinding(binding);

        panMain.setLayout(new java.awt.BorderLayout());

        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                panMain,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                400,
                Short.MAX_VALUE));
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                panMain,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                300,
                Short.MAX_VALUE));

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public void masterConfigure(final Element parent) {
    }
}
