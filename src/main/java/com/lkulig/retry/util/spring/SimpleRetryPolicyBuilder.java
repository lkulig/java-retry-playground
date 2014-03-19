package com.lkulig.retry.util.spring;

import com.lkulig.retry.builder.AbstractBuildableBuilder;
import java.util.Map;

public class SimpleRetryPolicyBuilder extends AbstractBuildableBuilder<SimpleRetryPolicy, SimpleRetryPolicyBuilder> {

    public static SimpleRetryPolicyBuilder simpleRetryPolicy() {
        return new SimpleRetryPolicyBuilder();
    }

    public static SimpleRetryPolicy simpleRetryPolicyWithRetryableExceptions(int maxAttempts,
                                                                             Map<Class<? extends Throwable>, Boolean> exception) {
        return new SimpleRetryPolicy(maxAttempts, exception);
    }

    public SimpleRetryPolicyBuilder withMaxAttempts(int maxAttempts) {
        buildable.setMaxAttempts(maxAttempts);
        return this;
    }

    @Override
    public SimpleRetryPolicy build() {
        return buildable;
    }

    @Override
    protected SimpleRetryPolicy createBuildable() {
        return new SimpleRetryPolicy();
    }

    @Override
    protected SimpleRetryPolicyBuilder getBuilder() {
        return this;
    }
}
