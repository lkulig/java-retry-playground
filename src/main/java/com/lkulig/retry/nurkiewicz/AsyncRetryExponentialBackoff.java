package com.lkulig.retry.nurkiewicz;

import static org.joda.time.DateTime.now;
import com.blogspot.nurkiewicz.asyncretry.AsyncRetryExecutor;
import com.blogspot.nurkiewicz.asyncretry.RetryContext;
import com.blogspot.nurkiewicz.asyncretry.RetryExecutor;
import com.blogspot.nurkiewicz.asyncretry.function.RetryRunnable;
import com.lkulig.retry.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AsyncRetryExponentialBackoff {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncRetryExponentialBackoff.class);
    private static final int THREAD_POOL_SIZE = 1;
    private static final int RETRY_COUNT = 5;
    private static final int BACKOFF = 100;
    private static final int MULTIPLIER = 2;
    private static BusinessService businessService = new BusinessService();

    public static void main(String args[]) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
        RetryExecutor executor = new AsyncRetryExecutor(scheduler).withMaxRetries(RETRY_COUNT).withExponentialBackoff(
            BACKOFF, MULTIPLIER);

        executor.doWithRetry(new RetryRunnable() {

            @Override
            public void run(RetryContext context) throws Exception {
                LOGGER.debug("Start time: {}", now().toLocalTime());
                businessService.businessOperation();
            }
        });
    }
}
