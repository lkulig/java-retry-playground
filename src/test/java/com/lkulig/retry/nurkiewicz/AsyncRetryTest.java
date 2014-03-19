package com.lkulig.retry.nurkiewicz;

import static org.mockito.Mockito.doThrow;
import com.blogspot.nurkiewicz.asyncretry.AsyncRetryExecutor;
import com.blogspot.nurkiewicz.asyncretry.RetryContext;
import com.blogspot.nurkiewicz.asyncretry.RetryExecutor;
import com.blogspot.nurkiewicz.asyncretry.function.RetryRunnable;
import com.lkulig.retry.AbstractAsyncRetryUnitTest;
import org.junit.Test;

public class AsyncRetryTest extends AbstractAsyncRetryUnitTest {

    private static final int RETRY_COUNT = 5;
    private static final int NUMBER_OF_EXCEPTIONS_TO_BE_THROWN = 4;
    private static final int RETRY_DELAY = 100;

    @Test
    public void shouldRetryOperation5Times() throws Exception {
        final RetryExecutor executor = new AsyncRetryExecutor(schedulerMock).withMaxRetries(RETRY_COUNT);
        throwIllegalArgumentExceptionTimes(NUMBER_OF_EXCEPTIONS_TO_BE_THROWN);

        executor.doWithRetry(new RetryRunnable() {

            @Override
            public void run(RetryContext context) throws Exception {
                serviceMock.superbBusinessOperation();
            }
        });
    }

    @Test
    public void shouldRetryOperationWithFixedBackOff() throws Exception {
        final RetryExecutor executor = new AsyncRetryExecutor(schedulerMock).withMaxRetries(RETRY_COUNT)
            .withFixedBackoff(RETRY_DELAY);
        throwIllegalArgumentExceptionTimes(NUMBER_OF_EXCEPTIONS_TO_BE_THROWN);

        executor.doWithRetry(new RetryRunnable() {

            @Override
            public void run(RetryContext context) throws Exception {
                serviceMock.superbBusinessOperation();
            }
        });
    }

    @Test
    public void shouldRetryOperationWithExponentialBackOff() throws Exception {
        final RetryExecutor executor = new AsyncRetryExecutor(schedulerMock).withMaxRetries(RETRY_COUNT)
            .withExponentialBackoff(1000, 2);
        throwIllegalArgumentExceptionTimes(NUMBER_OF_EXCEPTIONS_TO_BE_THROWN);

        executor.doWithRetry(new RetryRunnable() {

            @Override
            public void run(RetryContext context) throws Exception {
                serviceMock.superbBusinessOperation();
            }
        });
    }

    @Test
    public void shouldFailOnSpecificException() throws Exception {
        final RetryExecutor executor = new AsyncRetryExecutor(schedulerMock).withMaxRetries(RETRY_COUNT).abortOn(
            IllegalArgumentException.class);

        doThrow(ArrayIndexOutOfBoundsException.class).doThrow(IllegalArgumentException.class).when(serviceMock)
            .superbBusinessOperation();

        executor.doWithRetry(new RetryRunnable() {

            @Override
            public void run(RetryContext context) throws Exception {
                serviceMock.superbBusinessOperation();
            }
        });
    }
}
