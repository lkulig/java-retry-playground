package com.lkulig.retry.nurkiewicz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.blogspot.nurkiewicz.asyncretry.RetryContext;
import com.blogspot.nurkiewicz.asyncretry.RetryExecutor;
import com.blogspot.nurkiewicz.asyncretry.function.RetryRunnable;
import com.lkulig.retry.service.FabulousBusinessService;

@Component
public class AsyncRetry {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncRetry.class);

    @Autowired
    private FabulousBusinessService fabulousBusinessService;

    public void doRetry(RetryExecutor executor) {
        executor.doWithRetry(new RetryRunnable() {

            @Override
            public void run(RetryContext context) throws Exception {
                LOGGER.debug("Attempt nr: {}", context.getRetryCount());
                fabulousBusinessService.superbBusinessOperation();
            }
        });
    }
}
