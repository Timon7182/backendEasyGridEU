package com.timon.rhouse.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.javamoney.moneta.FastMoney;

import javax.money.Monetary;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = Landlord.TBL_NAME)
public class Landlord extends AbstractClient {

    public static final String TBL_NAME="landlords";

    public static final String FLD_MONEY="money";
    public static final String FLD_NUMBER="contactNumber";


    @OneToMany(mappedBy = Flat.FLD_LANDLORDS,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference(value = "landlord")
    List<Flat> flats;

    @Column(name = FLD_MONEY,nullable = false)
    FastMoney money;



    @Column(name = FLD_NUMBER,unique = true,nullable = true)
    @Size(min = 2,max =15,message = "Details can be 2-15")
    private String contactNumber;

    public Landlord() {
    }

    public Landlord(String username, String name,String surname, String email,String contactNumber, String password, BigDecimal money,String currencyUnit,String roles) {
        super(username, name,surname, email, password,roles);
        this.contactNumber=contactNumber;
        this.flats=new ArrayList<>();
        this.money=FastMoney.of(money, Monetary.getCurrency(currencyUnit));
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<Flat> getFlats() {
        return flats;
    }

    public void setFlats(List<Flat> flats) {
        this.flats = flats;
    }

    public FastMoney getMoney() {
        return money;
    }

    public void setMoney(FastMoney money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Landlord)) return false;
        if (!super.equals(o)) return false;
        Landlord landlord = (Landlord) o;
        return Objects.equals(flats, landlord.flats) &&
                Objects.equals(money, landlord.money) &&
                contactNumber.equals(landlord.contactNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), flats, money, contactNumber);
    }
}
