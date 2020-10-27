package com.timon.rhouse.security;


import com.timon.rhouse.domain.Consumer;
import com.timon.rhouse.domain.Landlord;
import com.timon.rhouse.repository.ConsumerRepository;
import com.timon.rhouse.repository.LandlordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplUserDetailsService  implements UserDetailsService {

    @Autowired
    LandlordRepository landlordRepository;

    @Autowired
    ConsumerRepository consumerRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            Consumer consumer = consumerRepository.findByUsername(s);
            Landlord landlord = landlordRepository.findByUsername(s);

            if (consumer == null && landlord ==null) {
                throw new UsernameNotFoundException("Could not find any user with name " + s);
            }
            if(consumer==null && landlord !=null){
                return new LandlordDetailsService(landlord);

            }else{
                return new ConsumerDetailsService(consumer);
            }

        }
}
