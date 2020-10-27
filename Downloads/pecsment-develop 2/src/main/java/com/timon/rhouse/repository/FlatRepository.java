package com.timon.rhouse.repository;

import com.timon.rhouse.domain.Flat;
import com.timon.rhouse.domain.Landlord;
import org.javamoney.moneta.FastMoney;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface FlatRepository extends JpaRepository<Flat, Long> {

    @Transactional
    Page<Flat> findAll(Pageable pageable);

    @Transactional
    Flat findByTitle(String title);

    @Transactional
    List<Flat> findAllByCity(String city);

    @Transactional
    List<Flat> findAllByAddress(String address);

    @Transactional
    List<Flat> findAllByRentORbuy(Boolean rentOrbuy);

    @Transactional
    List<Flat> findAllByLandlord(Landlord landlord);

    @Transactional
    List<Flat> findAllByPriceBetween(FastMoney less, FastMoney greater);

    @Transactional
    List<Flat> findAllByPriceLessThan(FastMoney less);

    @Transactional
    List<Flat> findAllByPriceGreaterThan(FastMoney greater);

    @Transactional
    List<Flat> findAllByUtilitiesBetween(FastMoney less,FastMoney greater);

    @Transactional
    List<Flat> findAllByUtilitiesGreaterThan(FastMoney greater);

    @Transactional
    List<Flat> findAllByUtilitiesLessThan(FastMoney less);


    @Transactional
    List<Flat> findAllByTotalBetween(FastMoney less,FastMoney greater);

    @Transactional
    List<Flat> findAllByTotalGreaterThan(FastMoney greater);

    @Transactional
    List<Flat> findAllByTotalLessThan(FastMoney less);

    @Transactional
    List<Flat> findAllByNumberOfBedsBetween(int less,int greater);

    @Transactional
    List<Flat> findAllByNumberOfRoomsBetween(int less,int greater);

    @Transactional
    List<Flat> findAllByWifi(boolean iswifi);

    @Transactional
    List<Flat> findAllByInternetSpeedGreaterThanEqual(float greater);

    @Transactional
    List<Flat> findAllByRenovated(boolean isrnenovated);

    @Transactional
    List<Flat> findAllByFurnished(boolean furnished);

    @Transactional
    List<Flat> findAll(Specification spec);


    @Transactional
    List<Flat> findAllByAvailableFromBetween(LocalDate before, LocalDate later);

    @Transactional
    List<Flat> findAllByAvailableFromGreaterThan(LocalDate greater);
}
