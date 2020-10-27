package com.timon.rhouse.bean.validators;


import com.timon.rhouse.domain.Consumer;
import com.timon.rhouse.domain.Landlord;
import com.timon.rhouse.repository.ConsumerRepository;
import com.timon.rhouse.repository.LandlordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@Component
@Scope("request")
public class UsernameValidator implements Validator {

    @Autowired
    LandlordRepository landlordRepository;

    @Autowired
    ConsumerRepository consumerRepository;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        String username = o.toString();
        if(username.length()<2){
            FacesMessage msg = new FacesMessage("Invalid username","Name length min is 2");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        Landlord landlord=landlordRepository.findByUsername(username);
        Consumer consumer=consumerRepository.findByUsername(username);


        if(landlord!=null || consumer !=null){
            FacesMessage msg = new FacesMessage("Invalid username","Username already taken ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }


    }
}
