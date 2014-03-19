package com.lkulig.retry.spring;

import com.lkulig.retry.service.FabulousBusinessService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicInteger;

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
                LOGGER.debug("Attempt nr: {}, start time: {}", counter.getAndIncrement(), DateTime.now().toLocalTime());
                businessService.superbBusinessOperation();
                return null;
            }
        });
    }
}
