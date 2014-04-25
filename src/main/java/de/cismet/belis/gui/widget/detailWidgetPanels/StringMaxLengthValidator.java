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
public class StringMaxLengthValidator extends Validator<String> {

    //~ Instance fields --------------------------------------------------------

    private int maxLength;
    private int maxStringLength = 250;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new StringMaxLengthValidator object.
     */
    public StringMaxLengthValidator() {
        this.maxLength = maxStringLength;
    }

    /**
     * Creates a new StringMaxLengthValidator object.
     *
     * @param  maxLength  DOCUMENT ME!
     */
    public StringMaxLengthValidator(final int maxLength) {
        this.maxLength = maxLength;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Validator.Result validate(final String value) {
        if ((value != null) && (value.length() > maxLength)) {
            return new Validator.Result("code", "Der Text darf nicht länger als " + maxLength + " Zeichen sein.");
        }
        return null;
    }
}
