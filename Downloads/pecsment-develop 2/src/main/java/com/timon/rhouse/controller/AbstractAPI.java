package com.timon.rhouse.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class AbstractAPI  {

    Pageable firstPageWithFive = PageRequest.of(0, 5);


}
