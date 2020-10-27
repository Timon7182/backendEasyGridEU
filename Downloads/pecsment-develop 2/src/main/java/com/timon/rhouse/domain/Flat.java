package com.timon.rhouse.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.javamoney.moneta.FastMoney;

import javax.money.Monetary;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

@Entity(name = Flat.TBL_NAME)
public class Flat extends AbstractEntity<Long> {

    public static final String TBL_NAME="flats";


    public static final String FLD_LANDLORDS="landlord";
    public static final String FLD_TITLE="title";
    public static final String FLD_CITY="city";
    public static final String FLD_ADDRESS="address";
    public static final String FLD_PRICE="price";
    public static final String FLD_UTILITIES="utilities";
    public static final String FLD_TOTAL="total";
    public static final String FLD_DETAILS="details";
    public static final String FLD_RENTORBUY="rentORbuy";
    public static final String FLD_ROOMS="numberOfRooms";
    public static final String FLD_BEDS="numberOfBeds";
    public static final String FLD_WIFI="wifi";
    public static final String FLD_FURNISHED="furnished";
    public static final String FLD_RENOVATED="renovated";
    public static final String FLD_INTERNETSPEED="internetSpeed";
    public static final String FLD_AVAILABLEDATE="availableFrom";

    @Column(name = FLD_TITLE,unique = false,nullable = false)
    @NotNull(message = "username can't be null")
    @Size(min = 2,max =30,message = "Length of username can be 2-30")
    private String title;

    @Column(name = FLD_CITY,unique = false,nullable = false)
    @NotNull(message = "city of flat can't be null")
    @Size(min = 2,max =30,message = "Length of city can be 2-30")
    private String city;

    @Column(name = FLD_ADDRESS,unique = false,nullable = false)
    @NotNull(message = "address of flat can't be null")
    @Size(min = 2,max =30,message = "address can be 2-30")
    private String address;

    @Column(name = FLD_PRICE,unique = false,nullable = false)
    @NotNull(message = "price can't be null")
    private FastMoney price;

    @Column(name = FLD_UTILITIES,unique = false,nullable = false)
    @NotNull(message = "utilities can't be null")
    private FastMoney utilities;

    @Column(name = FLD_TOTAL,unique = false,nullable = false)
    @NotNull(message = "total can't be null")
    private FastMoney total;

    @Column(name = FLD_DETAILS,unique = false,nullable = false)
    @NotNull(message = "details  can't be null")
    @Size(min = 2,max =300,message = "Details can be 2-300")
    private String details;

    @Column(name = FLD_RENTORBUY,unique = false,nullable = false)
    @NotNull(message = "rent or buy  boolean can't be null")
    private Boolean rentORbuy;


    @OneToMany(mappedBy = File.FLD_FLAT,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<File> files;


    @ManyToOne(fetch = FetchType.LAZY,cascade = {
            CascadeType.MERGE
    })
    @JoinColumn(name=FLD_LANDLORDS)
    @JsonBackReference(value = "landlord")
    private Landlord landlord;

    @Column(name = FLD_ROOMS,unique = false,nullable = false)
    @NotNull(message = "rooms number can't be null")
    private int numberOfRooms;

    @Column(name = FLD_BEDS)
    private int numberOfBeds;

    @Column(name = FLD_WIFI,nullable = false)
    @NotNull(message = " wifi boolean can't be null")
    private boolean wifi;

    @Column(name = FLD_RENOVATED,nullable = false)
    @NotNull(message = "renovated boolean can't be null")
    private boolean renovated;

    @Column(name = FLD_FURNISHED,nullable = false)
    @NotNull(message = "furnished  boolean can't be null")
    private boolean furnished;

    @Column(name = FLD_INTERNETSPEED)
    private float internetSpeed;

    @Column(name = FLD_AVAILABLEDATE,nullable = true,columnDefinition = "DATE")
    @NotNull(message = "availableFrom can't be null")
    private LocalDate availableFrom;



    private String normalPrice;
    private String normalUtilities;

    public Flat() {
    }
    public Flat(String title, String city, String address, BigDecimal price,
                BigDecimal utilities, String details, int numberOfRooms ,int numberOfBeds ,
                boolean wifi,float internetSpeed,boolean renovated,
                boolean furnished,Boolean rentORbuy,String currencyUnit,Landlord landlord,LocalDate availableFrom ){

        this.internetSpeed=internetSpeed;
        this.availableFrom=availableFrom;
        this.numberOfBeds=numberOfBeds;
        this.numberOfRooms=numberOfRooms;
        this.wifi=wifi;
        this.renovated=renovated;
        this.furnished=furnished;
        this.title=title;
        this.city=city;
        this.address=address;
        this.price=FastMoney.of(price, Monetary.getCurrency(currencyUnit));
        this.utilities=FastMoney.of(utilities, Monetary.getCurrency(currencyUnit));
        this.total=this.price.add(this.utilities);
        this.details=details;
        this.rentORbuy=rentORbuy;
        this.landlord=landlord;
        files=new ArrayList<>();
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public float getInternetSpeed() {
        return internetSpeed;
    }

    public void setInternetSpeed(float internetSpeed) {
        this.internetSpeed = internetSpeed;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
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

    public FastMoney getTotal() {
        return total;
    }

    public void setTotal(FastMoney total) {
        this.total = total;
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

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }

    public String getNormalPrice() {
        Number value= this.getPrice().getNumber();
        String s = new DecimalFormat("0.####").format(Double.parseDouble(String.valueOf(value)));
        return s;
    }

    public void setNormalPrice(String normalPrice) {
        BigDecimal price = new BigDecimal(normalPrice);
        this.price=FastMoney.of(price, Monetary.getCurrency("EUR"));

        this.normalPrice=normalPrice;
    }

    public String getNormalUtilities() {
        Number value= this.getUtilities().getNumber();
        String s = new DecimalFormat("0.####").format(Double.parseDouble(String.valueOf(value)));
        return s;
    }

    public void setNormalUtilities(String normalUtilities) {
        BigDecimal price = new BigDecimal(normalUtilities);
        this.utilities=FastMoney.of(price, Monetary.getCurrency("EUR"));
        this.normalUtilities = normalUtilities;
    }

    public String getNormalTotal(){

        Number value= this.getTotal().getNumber();
        String s = new DecimalFormat("0.####").format(Double.parseDouble(String.valueOf(value)));
        return s;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flat)) return false;
        if (!super.equals(o)) return false;
        Flat flat = (Flat) o;
        return numberOfRooms == flat.numberOfRooms &&
                numberOfBeds == flat.numberOfBeds &&
                wifi == flat.wifi &&
                renovated == flat.renovated &&
                furnished == flat.furnished &&
                Float.compare(flat.internetSpeed, internetSpeed) == 0 &&
                title.equals(flat.title) &&
                city.equals(flat.city) &&
                address.equals(flat.address) &&
                price.equals(flat.price) &&
                utilities.equals(flat.utilities) &&
                total.equals(flat.total) &&
                details.equals(flat.details) &&
                rentORbuy.equals(flat.rentORbuy) &&
                landlord.equals(flat.landlord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, city, address, price, utilities, total, details, rentORbuy, landlord, numberOfRooms, numberOfBeds, wifi, renovated, furnished, internetSpeed, availableFrom);
    }
}
