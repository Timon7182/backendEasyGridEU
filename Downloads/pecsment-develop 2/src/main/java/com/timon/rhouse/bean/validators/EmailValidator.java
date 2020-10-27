package com.timon.rhouse.bean.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {


    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        String email = value.toString();
        if (!isValid(email)) {
            FacesMessage msg = new FacesMessage("Invalid email","ivnalid email ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }



    boolean isValid(String email) {

        // Reqular expression pattern to validate the format submitted
        String validator = "^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+"
                + "(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})$";

        if (!email.matches(validator)) {
            return false;
        }

        // Split the user and the domain name
        String[] parts = email.split("@");

        boolean retval=true;
        // This is similar to nslookup –q=mx domain_name.com to query
        // the mail exchanger of the domain.
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put("java.naming.factory.initial",
                    "com.sun.jndi.dns.DnsContextFactory");
            DirContext context = new InitialDirContext(env);
            Attributes attributes =
                    context.getAttributes(parts[1], new String[]{"MX"});
            Attribute attribute = attributes.get("MX");
            if (attribute.size() == 0) {
                retval=false;
            }
            context.close();
            return retval;

        } catch (Exception exception) {
            return false;
        }
    }


}
