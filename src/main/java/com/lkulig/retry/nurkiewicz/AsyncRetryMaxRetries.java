package com.lkulig.retry.nurkiewicz;

import com.blogspot.nurkiewicz.asyncretry.AsyncRetryExecutor;
import com.blogspot.nurkiewicz.asyncretry.RetryContext;
import com.blogspot.nurkiewicz.asyncretry.RetryExecutor;
import com.blogspot.nurkiewicz.asyncretry.function.RetryRunnable;
import com.lkulig.retry.service.BusinessService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AsyncRetryMaxRetries {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncRetryMaxRetries.class);
    private static BusinessService businessService = new BusinessService();
    private static final int THREAD_POOL_SIZE = 1;
    private static final int RETRY_COUNT = 5;

    public static void main(String args[]) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
        RetryExecutor executor = new AsyncRetryExecutor(scheduler).withMaxRetries(RETRY_COUNT);

        executor.doWithRetry(new RetryRunnable() {

            @Override
            public void run(RetryContext context) throws Exception {
                LOGGER.debug("Execution time: {}", DateTime.now().toLocalTime());
                businessService.businessOperation();
            }
        });

        System.out.println("DUPA");
    }
}
