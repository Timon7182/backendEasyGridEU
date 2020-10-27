package com.timon.rhouse.service;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import javax.servlet.http.Part;

/**
 *
 * @author hantsy
 */
@FacesValidator
public class FileValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Part part = (Part) value;
        if(part.getSize()>1024){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "file is too large", "file is too large"));
        }
    }

}