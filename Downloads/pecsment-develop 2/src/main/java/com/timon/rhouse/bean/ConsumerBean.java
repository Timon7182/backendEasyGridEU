package com.timon.rhouse.bean;

import com.timon.rhouse.domain.Consumer;
import com.timon.rhouse.repository.ConsumerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "consumerJsfService")
@ELBeanName(value = "consumerJsfService")
public class ConsumerBean {

    private final static Logger LOG = LogManager.getLogger();
    private final static Boolean DEBUG_TEMPORARY = false;
    @Autowired
    ConsumerRepository consumerRepository;



    private String username;
    private String name;
    private String surname;
    private String email;
    private String contactNumber;
    private String password;
//    public Consumer(String username, String name,String surname, String email, String password,String roles) {

    public String createNewConsumer(ConsumerBean consumer){
       try{
           consumerRepository.save(new Consumer(consumer.getUsername(),consumer.getName(),consumer.getSurname(),consumer.getEmail(),consumer.getPassword(),"ROLE_USER"));
           return "/WelcomePage.jsf?faces-redirect=true";
       }
       catch (Exception e){
           LOG.error("Can't create Consumer user" + e);
           return "/registration.jsf?faces-redirect=true";
       }
    }


    public String redirectToConsumerRegistration(){
        return "/registrationConsumer.jsf?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //    String username, String name,String surname, String email, String password,String roles

}
