package com.lkulig.retry.util;

import java.util.Map;
import com.aida.commons.builder.Buildable;

public class SimpleRetryPolicy extends org.springframework.retry.policy.SimpleRetryPolicy implements Buildable {

    public SimpleRetryPolicy() {
        super();
    }

    public SimpleRetryPolicy(int maxAttempts, Map<Class<? extends Throwable>, Boolean> exceptions) {
        super(maxAttempts, exceptions);
    }
}
