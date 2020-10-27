package com.timon.rhouse.repository;

import com.timon.rhouse.domain.Landlord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord,Long> {

    @Transactional
    Page<Landlord> findAll(Pageable pageable);

    @Transactional
    Landlord findByUsername(String username);

    @Transactional
    List<Landlord> findAllByName(String name);

    @Transactional
    Landlord findByEmail(String email);

    @Transactional
    Landlord deleteByEmail(String email);


}
