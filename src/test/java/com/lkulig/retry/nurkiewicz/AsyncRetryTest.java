package com.lkulig.retry.nurkiewicz;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.blogspot.nurkiewicz.asyncretry.backoff.FixedIntervalBackoff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.blogspot.nurkiewicz.asyncretry.AsyncRetryExecutor;
import com.blogspot.nurkiewicz.asyncretry.RetryContext;
import com.blogspot.nurkiewicz.asyncretry.RetryExecutor;
import com.blogspot.nurkiewicz.asyncretry.function.RetryRunnable;
import com.google.common.util.concurrent.ListenableFuture;
import com.lkulig.retry.AbstractUnitTest;

@RunWith(MockitoJUnitRunner.class)
public class AsyncRetryTest extends AbstractUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncRetryTest.class);

    private static final int NUMBER_OF_EXCEPTIONS_TO_BE_THROWN = 5;
    private static final int MAX_ATTEMPTS = 5;
    private static final int RETRY_DELAY = 100;

    @InjectMocks
    private AsyncRetry asyncRetry;

    @Test
    public void shouldRetryOperation5Times() throws Exception {
        throwExceptionTimes(NUMBER_OF_EXCEPTIONS_TO_BE_THROWN);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        RetryExecutor executor = new AsyncRetryExecutor(scheduler).withMaxRetries(MAX_ATTEMPTS);

        ListenableFuture<Void> voidListenableFuture = executor.doWithRetry(new RetryRunnable() {

            @Override
            public void run(RetryContext context) throws Exception {
                LOGGER.debug("Retry count: {}", context.getRetryCount());
                fabulousBusinessService.superbBusinessOperation();
            }
        });

        scheduler.shutdownNow();
    }

    @Test
    public void shouldRetryOperationWithFixedBackOff() throws Exception {
        throwExceptionTimes(NUMBER_OF_EXCEPTIONS_TO_BE_THROWN);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        RetryExecutor executor = new AsyncRetryExecutor(scheduler).withMaxRetries(MAX_ATTEMPTS).withBackoff(new FixedIntervalBackoff(RETRY_DELAY));

        ListenableFuture<Void> voidListenableFuture = executor.doWithRetry(new RetryRunnable() {

            @Override
            public void run(RetryContext context) throws Exception {
                LOGGER.debug("Retry count: {}", context.getRetryCount());
                fabulousBusinessService.superbBusinessOperation();
            }
        });

        scheduler.shutdownNow();
    }
}
