package com.timon.rhouse.controller;

import com.timon.rhouse.domain.Consumer;
import com.timon.rhouse.domain.Landlord;
import com.timon.rhouse.exception.ResourceNotFoundException;
import com.timon.rhouse.repository.LandlordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.FastMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/landlord")
public class LandlordAPI  extends AbstractAPI{


    @Autowired
    LandlordRepository landlordRepository;

    private final static Logger LOG = LogManager.getLogger();


    @PostMapping("/create")
    public ResponseEntity<Landlord> createLandlord(@RequestBody Landlord landlord) throws Exception{

        if(landlord==null){
            throw new Exception("Lanlord is null + " + landlord);
        }
        try{
            landlordRepository.save(landlord);
            return new ResponseEntity<>(landlord,HttpStatus.OK);
        }catch (Exception e){
            LOG.error(e + " Can't create new entity Landlord + " + landlord);
            throw new Exception(e + " " + landlord);
        }

    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Landlord>> getAllLanlords()throws Exception{
        try{
            Page<Landlord> landlords=landlordRepository.findAll(firstPageWithFive);
            if(landlords.isEmpty()){
                throw new Exception("List of landlords is empty");
            }
            return new ResponseEntity<>(landlords,HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
    @GetMapping("/getById/{id}")
    public  ResponseEntity<Landlord> getLandlordById(@PathVariable("id") Long id) throws Exception {
        if(id==null){
            LOG.error("Null id given "+ id);
            throw new Exception("Id is null");
        }
        try{

            Landlord landlord=landlordRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Can't find lanlord with id - " + id));
            return new ResponseEntity<Landlord>(landlord,HttpStatus.OK);

        }catch (Exception e){
            LOG.error(e + " id " +id);
            throw new Exception(e + " " + id);
        }
    }


    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<Landlord> getLandlordByEmail(@PathVariable("email") String email) throws Exception{
        if(email==null){
            LOG.error("email is null");
            throw new Exception("Email is null");
        }
        try{
            Landlord landlord =landlordRepository.findByEmail(email);
            if(landlord==null){
                throw new ResourceNotFoundException("Can't find landlord with email - "+ email);
            }
            return new ResponseEntity<>(landlord,HttpStatus.OK);
        }catch (Exception e){
            LOG.error(e+"email- "+ email);
            throw new Exception(e+ " email- " + email);
        }
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<Landlord> getLandlordByUsername(@PathVariable("username") String username) throws Exception{
        if(username==null){
            LOG.error("username is null");
            throw new Exception("username is null");
        }
        try{
            Landlord landlord =landlordRepository.findByUsername(username);
            if(landlord==null){
                throw new ResourceNotFoundException("Can't find landlord with username - "+ username);
            }
            return new ResponseEntity<>(landlord,HttpStatus.OK);
        }catch (Exception e){
            LOG.error(e+"username- "+ username);
            throw new Exception(e+ " username- " + username);
        }
    }

    @GetMapping("/getAllByName/{name}")
    public ResponseEntity<List<Landlord>> getLandlordByName(@PathVariable("name") String name) throws Exception{
        if(name==null){
            LOG.error("name is null");
            throw new Exception("name is null");
        }
        try{
            List<Landlord> landlord =landlordRepository.findAllByName(name);
            if(landlord.isEmpty()){
                throw new ResourceNotFoundException("Can't find landlords with name - "+ name);
            }
            return new ResponseEntity<>(landlord,HttpStatus.OK);
        }catch (Exception e){
            LOG.error(e+"name- "+ name);
            throw new Exception(e+ " name- " + name);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllLanlords()throws Exception{

        try{
            landlordRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            LOG.error("Can't delete- " + e);
            throw new Exception(e);
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> deleteLandlordById(@PathVariable("id" )Long id)throws Exception{

        if(id==null){
            LOG.error("Id is null");
            throw new Exception("Id is null");
        }
        try{
            landlordRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            LOG.error("Can't delete lanlord with id - "+id+" " + e);
            throw new Exception(e);
        }
    }


    @DeleteMapping("/deleteByEmail/{email}")
    public ResponseEntity<HttpStatus> deleteLanlordByEmail(@PathVariable("email" )String email)throws Exception{

        if(email==null){
            LOG.error("email is null");
            throw new Exception("email is null");
        }
        try{
            landlordRepository.deleteByEmail(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            LOG.error("Can't delete lanlord with email - "+email+" " + e);
            throw new Exception(e);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Landlord> updateConsumer(@PathVariable("id") long id, @RequestBody Landlord _landlord){

        try{
            Landlord landlord =landlordRepository.getOne(id);
            if(landlord==null){
                LOG.error("Can't find consumer with id: " + id);
                throw new ResourceNotFoundException("Can't find consumer with id: " + id);
            }

            landlord.setUsername(_landlord.getUsername());
            landlord.setName(_landlord.getName());
            landlord.setSurname(_landlord.getSurname());
            landlord.setPassword(_landlord.getPassword());
            landlord.setUsername(_landlord.getUsername());
            landlord.setContactNumber(_landlord.getContactNumber());
            landlord.setFlats(_landlord.getFlats());

            return new ResponseEntity<>(landlordRepository.save(landlord),HttpStatus.OK);
        }catch (Exception e){

            LOG.error(e + " data " + _landlord );
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/popupBalance")
    public ResponseEntity<Landlord> popUpBalanceOfLanlord(@RequestParam BigDecimal moneyToAdd,@RequestParam Long id)throws Exception{

        try{
            Landlord landlord= landlordRepository.findById(id).get();
            if(landlord==null){
                throw new ResourceNotFoundException("Can't find landlord with id - "+ id);
            }
            FastMoney fastMoney=FastMoney.of(moneyToAdd, Monetary.getCurrency("EUR"));
            landlord.setMoney(landlord.getMoney().add(fastMoney));

            landlordRepository.save(landlord);
            return new ResponseEntity<>(landlord,HttpStatus.OK);
        }catch (Exception e){
            LOG.error("Can't add money for lanlord with id " + id +" " + e);
            throw new Exception(e);
        }
    }

    @PutMapping("/subtractBalance")
    public ResponseEntity<Landlord> substractBalanceOfLanlord(@RequestParam BigDecimal moneyToSubtract,@RequestParam Long id)throws Exception{

        try{
            Landlord landlord= landlordRepository.findById(id).get();
            if(landlord==null){
                throw new ResourceNotFoundException("Can't find landlord with id - "+ id);
            }
            FastMoney fastMoney=FastMoney.of(moneyToSubtract, Monetary.getCurrency("EUR"));
            landlord.setMoney(landlord.getMoney().subtract(fastMoney));

            landlordRepository.save(landlord);
            return new ResponseEntity<>(landlord,HttpStatus.OK);
        }catch (Exception e){
            LOG.error("Can't moneyToSubtract money for lanlord with id " + id +" " + e);
            throw new Exception(e);
        }
    }


}
