package com.lkulig.retry.util.spring;

import com.lkulig.retry.builder.Buildable;
import java.util.Map;

public class SimpleRetryPolicy extends org.springframework.retry.policy.SimpleRetryPolicy implements Buildable {

    public SimpleRetryPolicy() {
        super();
    }

    public SimpleRetryPolicy(int maxAttempts, Map<Class<? extends Throwable>, Boolean> exceptions) {
        super(maxAttempts, exceptions);
    }
}
