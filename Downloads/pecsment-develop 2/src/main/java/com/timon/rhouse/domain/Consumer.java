package com.timon.rhouse.domain;

import javax.persistence.Entity;

@Entity(name = Consumer.TBL_NAME)
public class Consumer extends AbstractClient {

    public static final String TBL_NAME="consumers";

    public Consumer(String username, String name,String surname, String email, String password,String roles) {
        super(username, name,surname, email, password,roles);
    }

    public Consumer() {
    }
}
