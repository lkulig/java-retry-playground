package com.lkulig.retry.spring;

import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import com.lkulig.retry.service.FabulousBusinessService;

@Component
public class SpringRetryImproved {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringRetryImproved.class);

    @Autowired
    private FabulousBusinessService businessService;
    private AtomicInteger counter = new AtomicInteger(1);

    public void doRetry(RetryTemplate retryTemplate) throws Exception {
        retryTemplate.execute(new RetryCallback<Void>() {

            @Override
            public Void doWithRetry(RetryContext context) throws Exception {
                LOGGER.debug("Attempt nr: {}", counter.getAndIncrement());
                businessService.superbBusinessOperation();
                return null;
            }
        });
    }
}
