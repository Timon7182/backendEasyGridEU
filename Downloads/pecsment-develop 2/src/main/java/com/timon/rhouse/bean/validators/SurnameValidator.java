package com.timon.rhouse.bean.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("surnameValidator")
public class SurnameValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        String surname = o.toString();
        if(surname.length()<2){
            FacesMessage msg = new FacesMessage("Invalid surname","surname length min is 2");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        char[] nameletters=surname.toCharArray();
        for(char c:nameletters){
            if(Character.isDigit(c)){
                FacesMessage msg = new FacesMessage("Invalid surname","surname can't contain any numbers");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }
}
