package com.timon.rhouse.bean;

import com.timon.rhouse.domain.File;
import com.timon.rhouse.domain.Flat;
import com.timon.rhouse.repository.FileRepository;
import com.timon.rhouse.repository.FlatRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.timon.rhouse.domain.Landlord;
import com.timon.rhouse.repository.LandlordRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Scope(value = "session")
@Component(value = "landlordJsfService")
@ELBeanName(value = "landlordJsfService")
public class LandlordBean {

    private final static Logger LOG = LogManager.getLogger();
    private final static Boolean DEBUG_TEMPORARY = false;

    @Autowired
    LandlordRepository landlordRepository;

    @Autowired
    FlatRepository flatRepository;
    private List<Flat> flatList;


//    String username, String name,String surname, String email,String contactNumber, String password, BigDecimal money,String currencyUnit,String roles

    private String username;
    private String name;
    private String surname;
    private String email;
    private String contactNumber;
    private String password;

    private Landlord landlord;

    public Landlord getLoggedUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object name = auth.getPrincipal();
        String username;
        if (name instanceof UserDetails) {
             username = ((UserDetails)name).getUsername();
        } else {
             username = name.toString();
        }
        Landlord landlord=landlordRepository.findByUsername(username);

       this.flatList= flatRepository.findAllByLandlord(landlord);
        return landlord;
    }



    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData(int pagenumber,int size) throws Exception {
        try{
            Pageable firstPageWithFive = PageRequest.of(pagenumber, size);

            flatList=flatRepository.findAll(firstPageWithFive).getContent();

        }catch (Exception e ){
            throw  new Exception(e);
        }
    }

    public String createNewLandlord(LandlordBean landlord){
        try{
            landlordRepository.save(new Landlord(landlord.getUsername(), landlord.getName(),landlord.getSurname(), landlord.getEmail(),landlord.getContactNumber(),landlord.getPassword(), new BigDecimal(0),"EUR","ROLE_USER"));
            return "/WelcomePage.jsf?faces-redirect=true";
        }catch (Exception e){
            LOG.error( "Can't create new Landlord " + e);
            return "/registrationLandlord.jsf?faces-redirect=true";
        }

    }
    public String getFirstLetterOfName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object name = auth.getPrincipal();
        String username;
        if (name instanceof UserDetails) {
            username = ((UserDetails)name).getUsername();
        } else {
            username = name.toString();
        }
        Landlord landlord=landlordRepository.findByUsername(username);


        return Character.toString(landlord.getName().charAt(0));
    }

    public String redirectToLandlordRegistration(){
        return "/registrationLandlord.jsf?faces-redirect=true";
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

    public List<Flat> getFlatList() {
        return flatList;
    }

    public void setFlatList(List<Flat> flatList) {
        this.flatList = flatList;
    }

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }
}
