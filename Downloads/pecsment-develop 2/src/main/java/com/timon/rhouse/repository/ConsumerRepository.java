package com.timon.rhouse.repository;

import com.timon.rhouse.domain.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer,Long> {

     Consumer findByEmail(String email);
     Consumer findByUsername(String username);
     List<Consumer> findAllByName(String name);
     List<Consumer> findAllBySurname(String surname);
     List<Consumer> findAllByNameAndSurname(String name,String surname);

     @Transactional
     void deleteByUsername(String username);

     @Transactional
     void deleteByEmail(String email);

     @Transactional
     void deleteAllByName(String name);

     @Transactional
     void deleteAllBySurname(String surname);

     @Transactional
     void deleteAllByNameAndSurname(String name,String surname);
}
