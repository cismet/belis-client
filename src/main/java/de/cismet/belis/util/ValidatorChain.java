/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belis.util;

import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author spuhl
 */
public class ValidatorChain extends Validator{

    private Validator[] validators;

    public ValidatorChain(Validator ... validators) {
        this.validators = validators;
    }

    @Override
    public Result validate(Object value) {
        if(validators != null && validators.length >0){
            for(int i =0;i<validators.length;i++){
                final Result resultOfCurrentValidator = validators[i].validate(value);
                if(resultOfCurrentValidator != null){
                    return resultOfCurrentValidator;
                }
            }
            return null;
        } else {
            return null;
        }
    }

}
