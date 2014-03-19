package com.lkulig.retry.util.spring;

import com.lkulig.retry.builder.AbstractBuildableBuilder;

public class TimeoutRetryPolicyBuilder extends AbstractBuildableBuilder<TimeoutRetryPolicy, TimeoutRetryPolicyBuilder> {

    public static TimeoutRetryPolicyBuilder timeoutRetryPolicy() {
        return new TimeoutRetryPolicyBuilder();
    }

    public TimeoutRetryPolicyBuilder withTimeout(long timeout) {
        buildable.setTimeout(timeout);
        return this;
    }

    @Override
    public TimeoutRetryPolicy build() {
        return buildable;
    }

    @Override
    protected TimeoutRetryPolicy createBuildable() {
        return new TimeoutRetryPolicy();
    }

    @Override
    protected TimeoutRetryPolicyBuilder getBuilder() {
        return this;
    }
}
