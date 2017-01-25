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
package de.cismet.belis.gui.widget.detailWidgetPanels;

import org.jdesktop.beansbinding.Validator;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class NotNullValidator extends Validator {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(NotNullValidator.class);

    //~ Instance fields --------------------------------------------------------

    String elementname = "Element";

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new NotNullValidator object.
     */
    public NotNullValidator() {
    }

    /**
     * Creates a new NotNullValidator object.
     *
     * @param  elementname  DOCUMENT ME!
     */
    public NotNullValidator(final String elementname) {
        this.elementname = elementname;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Validator.Result validate(final Object value) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("NotNullValidatorcheck: " + value);
        }
        if (value != null) {
            return null;
        } else {
            return new Validator.Result("code", elementname + " muss gesetzt werden.");
        }
    }
}
