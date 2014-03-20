package com.lkulig.retry;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doThrow;
import com.blogspot.nurkiewicz.asyncretry.AsyncRetryContext;
import com.blogspot.nurkiewicz.asyncretry.RetryContext;
import com.blogspot.nurkiewicz.asyncretry.policy.RetryPolicy;
import com.lkulig.retry.service.FabulousBusinessService;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AbstractAsyncRetryUnitTest {

    @Mock
    protected ScheduledExecutorService schedulerMock;
    @Mock
    protected FabulousBusinessService serviceMock;

    @Before
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
        setupMocks();
    }

    private void setupMocks() {
        given(schedulerMock.schedule(notNullRunnable(), anyLong(), eq(TimeUnit.MILLISECONDS))).willAnswer(
            new Answer<Object>() {

                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    ((Runnable) invocation.getArguments()[0]).run();
                    return null;
                }
            });
    }

    protected Runnable notNullRunnable() {
        return (Runnable) notNull();
    }

    protected RetryContext notNullRetryContext() {
        return (RetryContext) notNull();
    }

    protected TimeUnit millis() {
        return eq(TimeUnit.MILLISECONDS);
    }

    protected RetryContext anyRetry() {
        return retry(1);
    }

    protected RetryContext retry(int ret) {
        return new AsyncRetryContext(RetryPolicy.DEFAULT, ret, new Exception());
    }

    protected void throwIllegalArgumentExceptionTimes(int numberOfThrows) {
        int i = 1;
        Stubber stubber = doThrow(new NumberFormatException());
        do {
            stubber = stubber.doThrow(new NumberFormatException());
            i++;
        } while (i <= numberOfThrows);
        stubber.doCallRealMethod().when(serviceMock).superbBusinessOperation();
    }

}
