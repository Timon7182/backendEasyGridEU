package com.timon.rhouse.controller;

import com.sun.mail.iap.Response;
import com.timon.rhouse.domain.Consumer;
import com.timon.rhouse.exception.ResourceNotFoundException;
import com.timon.rhouse.repository.ConsumerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.swing.text.html.HTML;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/consumer")
public class ConsumerAPI {

    private final static Logger LOG = LogManager.getLogger();

    @Autowired
    ConsumerRepository consumerRepository;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createConsumer(@RequestBody Consumer consumer) throws Exception {
        try{
            consumerRepository.save(consumer);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e){
            LOG.error(e + " data: " + consumer);
            throw new Exception(e);
        }

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Consumer> updateConsumer(@PathVariable("id") long id,@RequestBody Consumer _consumer){

        try{
            Consumer consumer =consumerRepository.getOne(id);
            if(consumer==null){
                throw new ResourceNotFoundException("Can't find consumer with id: " + id);
            }
            consumer.setEmail(_consumer.getEmail());
            consumer.setName(_consumer.getName());
            consumer.setSurname(_consumer.getSurname());
            consumer.setPassword(_consumer.getPassword());
            consumer.setUsername(_consumer.getUsername());

            return new ResponseEntity<>(consumerRepository.save(consumer),HttpStatus.OK);
        }catch (Exception e){

            LOG.error(e + " data " + _consumer );
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAll")
   public ResponseEntity<List<Consumer>> getAllConsumer(){

       try{
           List<Consumer> consumers = consumerRepository.findAll();

           if(consumers.isEmpty()){
               LOG.error("Consumer list is empty");
               return new ResponseEntity<>(consumers,HttpStatus.NO_CONTENT);
           }
           return new ResponseEntity<>(consumers,HttpStatus.OK);

       }catch (Exception e){
           LOG.error(  e);
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   @GetMapping("/getById/{id}")
   public ResponseEntity<Consumer> getConsumerById(@PathVariable("id")  long id){

            Consumer consumer = consumerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No Consumer found  with id - " + id));
            return new ResponseEntity<>(consumer, HttpStatus.OK);

   }

   @GetMapping("/getByEmail/{email}")
    public ResponseEntity<Consumer> getConsumerByEmail(@PathVariable("email") String email){

       Consumer consumer = consumerRepository.findByEmail(email);
       if(consumer==null){
           LOG.info("Consumer with email: " + email +  " not found ");
           throw new ResourceNotFoundException("No Consumer found  with Email - " + email);
       }
       return new ResponseEntity<>(consumer, HttpStatus.OK);
   }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<Consumer> getConsumerByUsername(@PathVariable("username") String username){

        Consumer consumer = consumerRepository.findByUsername(username);
        if(consumer==null){
            LOG.info("Consumer with username: " + username +  " not found ");
            throw new ResourceNotFoundException("No Consumer found  with Username - " + username);
        }
        return new ResponseEntity<>(consumer, HttpStatus.OK);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<List<Consumer>> getAllConsumerByName(@PathVariable("name") String name){

        List<Consumer> consumers = consumerRepository.findAllByName(name);
        if(consumers.isEmpty()){
            LOG.info("Consumers with name: " + name +  " not found ");
            throw new ResourceNotFoundException("No Consumer found  with Name - " + name);
        }
        return new ResponseEntity<>(consumers, HttpStatus.OK);
    }

    @GetMapping("/getBySurname/{surname}")
    public ResponseEntity<List<Consumer>> getAllConsumerBySurname(@PathVariable("surname") String surname){

        List<Consumer> consumers = consumerRepository.findAllBySurname(surname);
        if(consumers.isEmpty()){
            LOG.info("Consumers with name: " + surname +  " not found ");
            throw new ResourceNotFoundException("No Consumer found  with surname - " + surname);
        }
        return new ResponseEntity<>(consumers, HttpStatus.OK);
    }

    @GetMapping("/getByNameAndSurname/{name}/{surname}")
    public ResponseEntity<List<Consumer>> getAllConsumerByNameAndSurname(@PathVariable("name") String name,@PathVariable("surname") String surname){
        List<Consumer> consumers = consumerRepository.findAllByNameAndSurname(name,surname);
        if(consumers.isEmpty()){
            LOG.info("Consumers with name and surname: " + name + " " +surname +  " not found ");
            throw new ResourceNotFoundException("Consumers with name and surname: " + name + " " +surname +  " not found ");
        }
        return new ResponseEntity<>(consumers, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllConsumers() throws Exception {
        try {
            consumerRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            LOG.error(e + " Can't delete all consumers ");
            throw new Exception(e);
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> deleteConsumerById(@PathVariable("id") long id) throws Exception {

        if( id<=0) {
            LOG.error("Id <= 0 : " + id);
            throw new Exception("Id == 0");
        }
        try{
            consumerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            LOG.error(e);
            throw new Exception(e);
        }
    }

    @DeleteMapping("/deleteByUsername/{username}")
    public ResponseEntity<HttpStatus> deleteConsumerByUsername(@PathVariable("username") String username) throws Exception {

        if( username==null) {
            LOG.error("username ==null " + username);
            throw new Exception("username == null");
        }
        try{
            if(consumerRepository.findByUsername(username)==null){
                throw new ResourceNotFoundException("No Consumer found  with Username - " + username);
            }
            consumerRepository.deleteByUsername(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            LOG.error(e);
            throw new Exception(e);
        }
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public ResponseEntity<HttpStatus> deleteConsumerByEmail(@PathVariable("email") String email) throws Exception {

        if( email==null) {
            LOG.error("email ==null " + email);
            throw new Exception("email == null");
        }
        try{
            if(consumerRepository.findByEmail(email)==null){
                LOG.info("Consumer with email: " + email +  " not found ");
                throw new ResourceNotFoundException("No Consumer found  with Email - " + email);

            }
            consumerRepository.deleteByEmail(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            LOG.error(e);
            throw new Exception(e);
        }
    }

    @DeleteMapping("/deleteAllByName/{name}")
    public ResponseEntity<HttpStatus> deleteAllConsumerByName(@PathVariable("name") String name) throws Exception {

        if( name==null) {
            LOG.error("name ==null " + name);
            throw new Exception("name == null");
        }
        try{
            if(consumerRepository.findAllByName(name).isEmpty()){
                LOG.info("Consumers with name: " + name +  " not found ");
                throw new ResourceNotFoundException("No Consumer found  with Name - " + name);
            }
            consumerRepository.deleteAllByName(name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            LOG.error(e);
            throw new Exception(e);
        }
    }

    @DeleteMapping("/deleteAllBySurname/{surname}")
    public ResponseEntity<HttpStatus> deleteAllConsumerBySurname(@PathVariable("surname") String surname) throws Exception {

        if( surname==null) {
            LOG.error("surname ==null " + surname);
            throw new Exception("surname == null");
        }
        try{
            if(consumerRepository.findAllBySurname(surname).isEmpty()){
                LOG.info("Consumers with surname: " + surname +  " not found ");
                throw new ResourceNotFoundException("No Consumer found  with surname - " + surname);
            }
            consumerRepository.deleteAllBySurname(surname);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            LOG.error(e);
            throw new Exception(e);
        }
    }

    @DeleteMapping("/deleteAllByNameAndSurname/{name}/{surname}")
    public ResponseEntity<HttpStatus> deleteAllConsumerByNameAndSurname(@PathVariable("name") String name,@PathVariable("surname") String surname) throws Exception {

        if( surname==null||name==null) {
            LOG.error("surname == null || name ==null  " + surname + " " + name);
            throw new Exception("surname == null || name ==null ");
        }
        try{
            if(consumerRepository.findAllByNameAndSurname(name,surname).isEmpty()){
                LOG.info("Consumers with name and surname : " + name + " " + surname+  " not found ");
                throw new ResourceNotFoundException("Consumers with name and surname : " + name + " " + surname+  " not found ");
            }
            consumerRepository.deleteAllByNameAndSurname(name,surname);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            LOG.error(e);
            throw new Exception(e);
        }
    }
}
