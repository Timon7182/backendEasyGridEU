package com.timon.rhouse.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.timon.rhouse.domain.File;
import com.timon.rhouse.domain.Flat;
import com.timon.rhouse.domain.Landlord;
import com.timon.rhouse.repository.FlatRepository;
import com.timon.rhouse.repository.LandlordRepository;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.javamoney.moneta.FastMoney;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.money.Monetary;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Scope(value = "session")
@Component(value = "flatJsfService")
@ELBeanName(value = "flatJsfService")
public class FlatBean {


    private long id;
    private String title;
    private String city;
    private String address;
    private FastMoney price;
    private FastMoney utilities;
    private String details;
    private Boolean rentORbuy;
    private List<File> files;
    private Landlord landlord;
    private Integer numberOfRooms;
    private Integer numberOfBeds;
    private boolean wifi;
    private boolean renovated;
    private boolean furnished;
    private Float internetSpeed;
    private LocalDate availableFrom;
    private BigDecimal utilitiesDecimal;
    private long landlordid;
    private BigDecimal priceDecimal;
    private BigDecimal empty;

    @Autowired
    LandlordRepository landlordRepository;

    @Autowired
    FlatRepository flatRepository;

    private List<Flat> listOfFlats;


    public void loadData(int pagenumber,int size) throws Exception {
        try{
            Pageable firstPageWithFive = PageRequest.of(pagenumber, size);

            listOfFlats=flatRepository.findAll(firstPageWithFive).getContent();


        }catch (Exception e ){
            throw  new Exception(e);
        }
    }

//    public StreamedContent getImage() throws IOException {
//        FacesContext context = FacesContext.getCurrentInstance();
//
//        // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
//        String studentId = context.getExternalContext().getRequestParameterMap().get("flat");
//        Flat student = flatRepository.findById(Long.valueOf(studentId)).get();
//        return new DefaultStreamedContent(new ByteArrayInputStream(student.getFiles().get(0).getData()));
//
//    }


    public String createFlat(FlatBean flatBean){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object name = auth.getPrincipal();
        String username;
        if (name instanceof UserDetails) {
            username = ((UserDetails)name).getUsername();
        } else {
            username = name.toString();
        }
        Landlord landlord=landlordRepository.findByUsername(username);

        Flat f1 = new Flat(flatBean.title, flatBean.city, flatBean.address, flatBean.priceDecimal,
                flatBean.utilitiesDecimal, flatBean.details,flatBean.numberOfRooms,flatBean.numberOfBeds,
                flatBean.wifi,flatBean.internetSpeed,flatBean.renovated,flatBean.furnished,flatBean.rentORbuy,"EUR",landlord,flatBean.availableFrom);


        flatRepository.save(f1);
        flatBean.setId(f1.getId());
        return "/filesave.jsf?faces-reidrect=true";

    }

    public String change(Long id){
        try {
            Flat flat = this.flatRepository.findById(id).get();

            Map<String, Object> sessionMap= FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMap.put("editRecord",flat);
            int d=0;
            return "/editFlat.jsf?faces-reidrect=true";


        }catch (Exception e){
            return null;
        }
    }

    public String save(BigDecimal price,BigDecimal utilities,Flat flat){
        if(price !=null){
            flat.setPrice(FastMoney.of(price, Monetary.getCurrency("EUR")));
        }
        if(utilities!=null){
            flat.setUtilities(FastMoney.of(utilities, Monetary.getCurrency("EUR")));
        }


        flatRepository.save(flat);
        this.priceDecimal= this.empty;
        this.utilitiesDecimal=this.empty;

        return "/profPage.jsf?faces-reidrect=true";


    }


    public Flat getFlatFromSession(){
        try{
            Map<String, Object> sessionMap= FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

            Flat flat= (Flat) sessionMap.get("editRecord");

            return flat;
        }catch (Exception e){

            System.out.println("dss");
            return null;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Flat> getListOfFlats() {
        return listOfFlats;
    }

    public void setListOfFlates(List<Flat> listOfFlates) {
        this.listOfFlats = listOfFlates;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FastMoney getPrice() {
        return price;
    }

    public void setPrice(FastMoney price) {
        this.price = price;
    }

    public FastMoney getUtilities() {
        return utilities;
    }

    public void setUtilities(FastMoney utilities) {
        this.utilities = utilities;
    }



    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean getRentORbuy() {
        return rentORbuy;
    }

    public void setRentORbuy(Boolean rentORbuy) {
        this.rentORbuy = rentORbuy;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public boolean isWifi() {
        return wifi;
    }


    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }


    public boolean isRenovated() {
        return renovated;
    }

    public void setRenovated(boolean renovated) {
        this.renovated = renovated;
    }

    public boolean isFurnished() {
        return furnished;
    }

    public void setFurnished(boolean furnished) {
        this.furnished = furnished;
    }

    public Float getInternetSpeed() {
        return internetSpeed;
    }

    public void setInternetSpeed(Float internetSpeed) {
        this.internetSpeed = internetSpeed;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }

    public BigDecimal getUtilitiesDecimal() {
        return utilitiesDecimal;
    }

    public void setUtilitiesDecimal(BigDecimal utilitiesDecimal) {
        this.utilitiesDecimal = utilitiesDecimal;
    }

    public long getLandlordid() {
        return landlordid;
    }

    public void setLandlordid(long landlordid) {
        this.landlordid = landlordid;
    }

    public BigDecimal getPriceDecimal() {
        return priceDecimal;
    }

    public void setPriceDecimal(BigDecimal priceDecimal) {
        this.priceDecimal = priceDecimal;
    }
}
