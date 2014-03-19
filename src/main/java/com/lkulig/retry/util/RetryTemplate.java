package com.lkulig.retry.util;

import org.springframework.retry.RetryCallback;
import com.aida.commons.builder.Buildable;

public class RetryTemplate extends org.springframework.retry.support.RetryTemplate implements Buildable {

    public <T> T executeOperation(RetryCallback<T> retryCallback) {
        try {
            return super.execute(retryCallback);
        } catch (Exception e) {
            throw new OperationRetryException(e.getCause());
        }
    }
}
