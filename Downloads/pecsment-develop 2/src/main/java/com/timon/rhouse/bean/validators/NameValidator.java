package com.timon.rhouse.bean.validators;


import javax.faces.application.FacesMessage;
import javax.faces.validator.Validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("nameValidator")
public class NameValidator implements Validator {


    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        String name = o.toString();
        if(name.length()<2){
            FacesMessage msg = new FacesMessage("Invalid name","Name length min is 2");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        char[] nameletters=name.toCharArray();
        for(char c:nameletters){
            if(Character.isDigit(c)){
                FacesMessage msg = new FacesMessage("Invalid name","Name can't contain any numbers");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }


    }
}
