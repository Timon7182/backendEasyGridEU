package com.timon.rhouse.controller.specification;

import com.timon.rhouse.domain.Flat;
import org.javamoney.moneta.FastMoney;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FlatSpec {





    public static Specification<Flat> cityNameEquals(String city) {
        return (root, query, builder) ->
                city == null ?
                        builder.conjunction() :
                        builder.equal(root.get("city"), city);
    }

    public static Specification<Flat> addressFlatEquals(String address) {
        return (root, query, builder) ->
                address == null ?
                        builder.conjunction() :
                        builder.equal(root.get("address"), address);
    }

    public static Specification<Flat> priceBetween(FastMoney less ,FastMoney greater) {
        return (root, query, builder) ->
                less == null ?
                        builder.conjunction() :
                        builder.between(root.get("price"), less,greater);
    }
    public static Specification<Flat> utilitiesBetween(FastMoney less ,FastMoney greater) {
        return (root, query, builder) ->
                less == null && greater==null ?
                        builder.conjunction() :
                        builder.between(root.get("utilities"), less,greater);
    }

    public static Specification<Flat> rentOrBuy(Boolean rentOrBuy) {
        return (root, query, builder) ->
                rentOrBuy == null ?
                        builder.conjunction() :
                        builder.equal(root.get("rentORbuy"), rentOrBuy);
    }

    public static Specification<Flat> roomsBetween(Integer less ,Integer greater) {
        return (root, query, builder) ->
                less == null && greater ==null ?
                        builder.conjunction() :
                        builder.between(root.get("numberOfRooms"), less,greater);
    }

    public static Specification<Flat> isFurnished(Boolean furnished) {
        return (root, query, builder) ->
                furnished == null ?
                        builder.conjunction() :
                        builder.equal(root.get("furnished"), furnished);
    }

    public static Specification<Flat> isRenovated(Boolean renovated) {
        return (root, query, builder) ->
                renovated == null ?
                        builder.conjunction() :
                        builder.equal(root.get("renovated"), renovated);
    }

    public static Specification<Flat> isWifi(Boolean wifi) {
        return (root, query, builder) ->
                wifi == null ?
                        builder.conjunction() :
                        builder.equal(root.get("wifi"), wifi);
    }
    public static Specification<Flat> wifiSpeed(Float internetSpeed) {
        return (root, query, builder) ->
                internetSpeed == null ?
                        builder.conjunction() :
                        builder.greaterThan(root.get("internetSpeed"), internetSpeed);
    }

    public static Specification<Flat> availableFrom(LocalDate availableFrom) {
        return (root, query, builder) ->
                availableFrom == null ?
                        builder.conjunction() :
                        builder.greaterThan(root.get("availableFrom"), availableFrom);
    }
}
