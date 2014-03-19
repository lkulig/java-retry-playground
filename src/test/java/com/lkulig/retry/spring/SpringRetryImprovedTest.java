package com.lkulig.retry.spring;

import static com.google.common.collect.Maps.newHashMap;
import static com.lkulig.retry.util.ExponentialBackOffPolicyBuilder.exponentialBackOffPolicy;
import static com.lkulig.retry.util.FixedBackOffPolicyBuilder.fixedBackOffPolicy;
import static com.lkulig.retry.util.RetryTemplateBuilder.retryTemplate;
import static com.lkulig.retry.util.SimpleRetryPolicyBuilder.simpleRetryPolicy;
import static com.lkulig.retry.util.SimpleRetryPolicyBuilder.simpleRetryPolicyWithRetryableExceptions;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.mockito.Mockito.doThrow;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.retry.support.RetryTemplate;
import com.lkulig.retry.AbstractUnitTest;

@RunWith(MockitoJUnitRunner.class)
public class SpringRetryImprovedTest extends AbstractUnitTest {

    private static final int NUMBER_OF_EXCEPTIONS_TO_BE_THROWN = 4;
    private static final int MAX_ATTEMPTS = 5;
    private static final int RETRY_DELAY = 100;

    @InjectMocks
    private SpringRetryImproved springRetry;

    @Test
    public void shouldRetryOperation5Times() throws Exception {
        // given
        throwExceptionTimes(NUMBER_OF_EXCEPTIONS_TO_BE_THROWN);
        RetryTemplate retryTemplate = retryTemplate()
            .withPolicies(
                simpleRetryPolicy()
                    .withMaxAttempts(MAX_ATTEMPTS)
                    .build())
            .build();

        // when
        springRetry.doRetry(retryTemplate);
    }

    @Test
    public void shouldRetryOperationWithFixedBackOff() throws Exception {
        // given
        throwExceptionTimes(NUMBER_OF_EXCEPTIONS_TO_BE_THROWN);
        RetryTemplate retryTemplate = retryTemplate()
            .withPolicies(
                simpleRetryPolicy()
                    .withMaxAttempts(MAX_ATTEMPTS)
                    .build(),
                fixedBackOffPolicy()
                    .withDelay(RETRY_DELAY)
                    .build())
            .build();

        // when
        springRetry.doRetry(retryTemplate);
    }

    @Test
    public void shouldRetryOperationWithExponentialBackOff() throws Exception {
        // given
        throwExceptionTimes(NUMBER_OF_EXCEPTIONS_TO_BE_THROWN);
        RetryTemplate retryTemplate = retryTemplate()
            .withPolicies(
                simpleRetryPolicy()
                    .withMaxAttempts(MAX_ATTEMPTS)
                    .build(),
                exponentialBackOffPolicy()
                    .build())
            .build();

        // when
        springRetry.doRetry(retryTemplate);
    }

    @Test
    public void shouldFailOnSpecificException() throws Exception {
        // given
        doThrow(new ArrayIndexOutOfBoundsException()).doThrow(new IllegalArgumentException()).doCallRealMethod().when(
            fabulousBusinessService).superbBusinessOperation();

        Map<Class<? extends Throwable>, Boolean> retryFor = newHashMap();
        retryFor.put(ArrayIndexOutOfBoundsException.class, TRUE);
        retryFor.put(IllegalArgumentException.class, TRUE);

        RetryTemplate retryTemplate = retryTemplate()
            .withPolicies(
                simpleRetryPolicyWithRetryableExceptions(MAX_ATTEMPTS, retryFor))
            .build();

        // when
        springRetry.doRetry(retryTemplate);
    }
}
