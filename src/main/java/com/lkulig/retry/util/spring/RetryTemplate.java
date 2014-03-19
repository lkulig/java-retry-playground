package com.lkulig.retry.util.spring;

import com.lkulig.retry.builder.Buildable;
import com.lkulig.retry.util.OperationRetryException;
import org.springframework.retry.RetryCallback;

public class RetryTemplate extends org.springframework.retry.support.RetryTemplate implements Buildable {

    public <T> T executeOperation(RetryCallback<T> retryCallback) {
        try {
            return super.execute(retryCallback);
        } catch (Exception e) {
            throw new OperationRetryException(e.getCause());
        }
    }
}
