package com.lkulig.retry.service;

import java.util.concurrent.atomic.AtomicInteger;

public class BusinessService {

    AtomicInteger counter = new AtomicInteger(1);

    public void businessOperation() {
        if (counter.get() <= 5) {
            counter.getAndIncrement();
            throw new NumberFormatException();
        }
        System.out.println("Business Operation Successful");

    }
}
