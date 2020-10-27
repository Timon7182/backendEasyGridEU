package com.timon.rhouse.controller;



import com.timon.rhouse.controller.specification.FlatSpec;
import com.timon.rhouse.domain.Flat;
import com.timon.rhouse.domain.Landlord;
import com.timon.rhouse.exception.ResourceNotFoundException;
import com.timon.rhouse.repository.FlatRepository;
import com.timon.rhouse.repository.LandlordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.FastMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/flat")
public class FlatAPI {

    @Autowired
    FlatRepository flatRepository;

    @Autowired
    LandlordRepository landlordRepository;
    private final static Logger LOG = LogManager.getLogger();


//    create
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createFlat(@RequestBody Flat flat) throws Exception {

        try{
            flatRepository.save(flat);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e){
            LOG.error(e + " data: " + flat);
            throw new Exception(e);
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Flat>> getAllFlat(){
        return new ResponseEntity<>(flatRepository.findAll(),HttpStatus.OK);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<Flat> getFlatById(@PathVariable("id") long id) throws Exception{

        Flat flat = flatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Flat found  with id - " + id));
        return new ResponseEntity<>(flat, HttpStatus.OK);

    }

    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<Flat> getFlatByTitle(@PathVariable("title") String title){


       Flat flat=flatRepository.findByTitle(title);
        if(flat==null){
            LOG.info("Flat with title: " + title +  " not found ");
            throw new ResourceNotFoundException("No Flat found  with Title - " + title);
        }
        return new ResponseEntity<>(flat, HttpStatus.OK);

    }

    @GetMapping("/getByCity/{city}")
    public ResponseEntity<List<Flat>> getAllFlatByCity(@PathVariable("city") String city){


        List<Flat> flat=flatRepository.findAllByCity(city);
        if(flat.isEmpty()){
            LOG.info("Flat with cities: " + city +  " not found ");
            throw new ResourceNotFoundException("No Flat found  with city - " + city);
        }
        return new ResponseEntity<>(flat, HttpStatus.OK);

    }

    @GetMapping("/getByAddress/{address}")
    public ResponseEntity<List<Flat>> getAllFlatByAddress(@PathVariable("address") String address){


        List<Flat> flat=flatRepository.findAllByAddress(address);
        if(flat.isEmpty()){
            LOG.info("Flat with address: " + address +  " not found ");
            throw new ResourceNotFoundException("No Flat found  with address - " + address);
        }
        return new ResponseEntity<>(flat, HttpStatus.OK);

    }

    @GetMapping("/getRentOrBuy/{rentorbuy}")
    public ResponseEntity<List<Flat>> getAllFlatByAddress(@PathVariable("rentorbuy") Boolean rentorbuy){


        List<Flat> flat=flatRepository.findAllByRentORbuy(rentorbuy);
        if(flat.isEmpty()){
            LOG.info("Flat with rent/buy: " + rentorbuy +  " not found ");
            throw new ResourceNotFoundException("No Flat found  for rent/buy - " + rentorbuy);
        }
        return new ResponseEntity<>(flat, HttpStatus.OK);

    }


    @GetMapping("/getByLandlord/{id}")
    public ResponseEntity<List<Flat>> getAllFlatByLandlord(@PathVariable("id") long id){



        Landlord landlord=landlordRepository.findById(id).get();
        if(landlord==null){
            LOG.info("Landlord with id: " + id +  " not found ");
            throw new ResourceNotFoundException("No Landlord found  with id - " + id);
        }
        List<Flat> flat=flatRepository.findAllByLandlord(landlord);
        if(flat.isEmpty()){
            LOG.info("Flat with landlord: " + landlord +  " not found ");
            throw new ResourceNotFoundException("No Flat found  with landlord - " + landlord);
        }
        return new ResponseEntity<>(flat, HttpStatus.OK);

    }

    @GetMapping("/getByPriceBetween/{less}/{big}/{currency}")
    public ResponseEntity<List<Flat>> getAllFlatByPriceRange(@PathVariable("less") BigDecimal less,@PathVariable("big") BigDecimal big,@PathVariable("currency") String currency){


        FastMoney f1=FastMoney.of(less, Monetary.getCurrency(currency));
        FastMoney f2=FastMoney.of(big, Monetary.getCurrency(currency));
        List<Flat> flat=flatRepository.findAllByPriceBetween(f1,f2);
        if(flat.isEmpty()){
            LOG.info("Flat in range " + less + " and "+ big +  " not found ");
            throw new ResourceNotFoundException("Flat in range " + less + " and "+ big +  " not found ");
        }
        return new ResponseEntity<>(flat, HttpStatus.OK);

    }

    @GetMapping("/getByPriceGreater/{greater}/{currency}")
    public ResponseEntity<List<Flat>> getAllFlatByGreaterPrice(@PathVariable("greater") BigDecimal greater,@PathVariable("currency") String currency){

        FastMoney f1=FastMoney.of(greater, Monetary.getCurrency(currency));
        List<Flat> flat=flatRepository.findAllByPriceLessThan(f1);
        if(flat.isEmpty()){
            LOG.info("Flat greater "+  greater +  " not found ");
            throw new ResourceNotFoundException("Flat greater than  " + greater+  " not found ");
        }
        return new ResponseEntity<>(flat, HttpStatus.OK);

    }

    @GetMapping("/getByPriceLess/{less}/{currency}")
    public ResponseEntity<List<Flat>> getAllFlatByLess(@PathVariable("less") BigDecimal less,@PathVariable String currency){

        FastMoney f1=FastMoney.of(less, Monetary.getCurrency(currency));
        List<Flat> flat=flatRepository.findAllByPriceLessThan(f1);
        if(flat.isEmpty()){
            LOG.info("Flat less than "+  less +  " not found ");
            throw new ResourceNotFoundException("Flat less than  " + less+  " not found ");
        }
        return new ResponseEntity<>(flat, HttpStatus.OK);

    }

    @GetMapping("/search/{timeFrom}")
    public ResponseEntity<List<Flat>> getAllAvailabeFlatsFrom(@PathVariable("timeFrom")String timeFrom) throws Exception {

        String[] t1=timeFrom.split("-");
        LocalDate dateofAvailable=LocalDate.of(Integer.parseInt(t1[0]),Integer.parseInt(t1[1]),Integer.parseInt(t1[2]));

        try{
            return new ResponseEntity<>(flatRepository.findAllByAvailableFromGreaterThan(dateofAvailable),HttpStatus.OK);

        }catch (Exception e){
            LOG.error(e + "  " + timeFrom + " " + dateofAvailable);
            throw new Exception(e + " " + timeFrom);
        }

    }

    @GetMapping("/advancedSearch")
    public ResponseEntity<List<Flat>> getAllFlatAdvanced(@RequestParam(required = false) String cityname,
                                               @RequestParam(required = false) BigDecimal priceLess,
                                               @RequestParam(required = false) BigDecimal priceGreater,
                                               @RequestParam(required = false) BigDecimal untilityLess,
                                               @RequestParam(required = false) BigDecimal untilityGreater,
                                               @RequestParam(required = false) Boolean rentORbuy,
                                               @RequestParam(required = false) Boolean furnished,
                                               @RequestParam(required = false) Boolean renovated,
                                               @RequestParam(required = false) Integer roomsLess,
                                               @RequestParam(required = false) Integer roomsGreater,
                                               @RequestParam(required = false) Boolean wifi,
                                               @RequestParam(required = false) Float internetSpeed,
                                               @RequestParam(required = false) String availableDate
                                               ){

        FastMoney priceLess1=null;
        FastMoney priceGreater1=null;
        FastMoney untilityLess1=null;
        FastMoney untilityGreater1=null;

        if(priceGreater!=null && priceLess!=null){
            priceLess1=FastMoney.of(priceLess, Monetary.getCurrency("EUR"));
            priceGreater1=FastMoney.of(priceGreater, Monetary.getCurrency("EUR"));
        }

        if(untilityGreater!=null && untilityLess!=null){
            untilityLess1=FastMoney.of(untilityLess, Monetary.getCurrency("EUR"));
            untilityGreater1=FastMoney.of(untilityGreater, Monetary.getCurrency("EUR"));


        }

        LocalDate availableFrom=null;

        if(availableDate!=null){
            String[] availables=availableDate.split("-");
            availableFrom=LocalDate.of(Integer.parseInt(availables[0]),Integer.parseInt(availables[1]),Integer.parseInt(availables[2]));
        }


        Specification spec1 = FlatSpec.cityNameEquals(cityname);
        Specification spec2 = FlatSpec.priceBetween(priceLess1,priceGreater1);
        Specification spec3 = FlatSpec.utilitiesBetween(untilityLess1,untilityGreater1);
        Specification spec4 = FlatSpec.rentOrBuy(rentORbuy);
        Specification spec5 = FlatSpec.isRenovated(renovated);
        Specification spec6 = FlatSpec.isFurnished(furnished);
        Specification spec7 = FlatSpec.roomsBetween(roomsLess,roomsGreater);
        Specification spec8 = FlatSpec.isWifi(wifi);
        Specification spec9 = FlatSpec.wifiSpeed(internetSpeed);
        Specification spec10 = FlatSpec.availableFrom(availableFrom);

        Specification spec = Specification.where(spec1).and(spec2).and(spec3).and(spec4).and(spec5).and(spec6).and(spec7).and(spec8).and(spec9).and(spec10);


        return new ResponseEntity<>(flatRepository.findAll(spec),HttpStatus.OK);


    }
    @GetMapping("/do")
    public void get(){
        Landlord l1=new Landlord("danik", "Daniyar","Serikov","dserikov07@gmail.com","+77029556305", "Daniyar123", new BigDecimal(1000),"EUR","ROLE_USER");

        Landlord l2=new Landlord("jo", "jo","jo","jo@gmail.com","+2121", "Daniyar123", new BigDecimal(1000),"EUR","ROLE_USER");

        LocalDate calendarDate = LocalDate.of(2020,06,30);


        Flat f1 = new Flat("MyHome", "Pecs", "Rokus 7a", new BigDecimal(100),
                new BigDecimal(20), "Nohthing",2,1,
                true,(float)1.4,true,true,true,"EUR",l1,calendarDate);


        Flat f2 = new Flat("Home2", "Pecs", "Rokus 7a", new BigDecimal(200),
                new BigDecimal(20), "Nohthing",2,1,
                true,(float)1.4,false,false,true,"EUR",l1,calendarDate);


        Flat f3 = new Flat("Home3", "Pecs", "Ak 4b", new BigDecimal(300),
                new BigDecimal(20), "Nohthing",2,1,
                true,(float)1.4,false,true,false,"EUR",l2,calendarDate);


        Flat f4 = new Flat("Home4", "Sopron", "Alkotman 17c", new BigDecimal(400),
                new BigDecimal(20), "Nohthing",2,1,
                false,0,true,false,false,"EUR",l2,calendarDate);

        landlordRepository.save(l1);
        landlordRepository.save(l2);
//        flatRepository.save(f1);
//        flatRepository.save(f2);
//        flatRepository.save(f3);
//        flatRepository.save(f4);
    }


    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllFlat() throws Exception{

        try{
            flatRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (Exception e){
            LOG.error(e +" Can't DELETE ALL");
            throw new Exception(e);

        }


    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> deleteFlatById(@PathVariable("id") Long id)throws Exception{
        if(id==null){
            throw new Exception("ID is NUll " +id);
        }
        try{
            flatRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            LOG.error(e);
            throw new Exception(e +  " " + id);
        }

    }



    @PutMapping("/update")
    public ResponseEntity<Flat> updateFlatData(@RequestBody Flat _flat,@RequestParam Long id) throws Exception{

        if(id==null){
            throw new Exception("Id is null");
        }
        try{
            Flat flat = flatRepository.findById(id)
                    .orElseThrow(()->new ResourceNotFoundException("Can't find flat with id " + id ));

          
            flat.setInternetSpeed(_flat.getInternetSpeed());
            flat.setAvailableFrom(_flat.getAvailableFrom());
            flat.setNumberOfBeds(_flat.getNumberOfBeds());
            flat.setNumberOfRooms(_flat.getNumberOfRooms());
            flat.setWifi(_flat.isWifi());
            flat.setRenovated(_flat.isRenovated());
            flat.setFurnished(_flat.isFurnished());
            flat.setTitle(_flat.getTitle());
            flat.setCity(_flat.getCity());
            flat.setAddress(_flat.getAddress());
            flat.setPrice(_flat.getPrice());
            flat.setUtilities(_flat.getUtilities());
            flat.setTotal(flat.getPrice().add(flat.getUtilities()));
            flat.setDetails(_flat.getDetails());
            flat.setRentORbuy(_flat.getRentORbuy());
            flat.setLandlord(_flat.getLandlord());

            flatRepository.save(flat);

            return new ResponseEntity<>(flat,HttpStatus.OK);

        }catch (Exception e){
            LOG.error("Can't update flat " + e);
            throw new Exception(e);
        }
    }
}
