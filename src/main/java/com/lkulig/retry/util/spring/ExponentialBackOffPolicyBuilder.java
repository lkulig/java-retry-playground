package com.lkulig.retry.util.spring;

import com.lkulig.retry.util.builder.AbstractBuildableBuilder;

public class ExponentialBackOffPolicyBuilder extends AbstractBuildableBuilder<ExponentialBackOffPolicy, ExponentialBackOffPolicyBuilder> {

    private ExponentialBackOffPolicyBuilder() {}

    public static ExponentialBackOffPolicyBuilder exponentialBackOffPolicy() {
        return new ExponentialBackOffPolicyBuilder();
    }

    @Override
    public ExponentialBackOffPolicy build() {
        return buildable;
    }

    @Override
    protected ExponentialBackOffPolicy createBuildable() {
        return new ExponentialBackOffPolicy();
    }

    @Override
    protected ExponentialBackOffPolicyBuilder getBuilder() {
        return this;
    }
}
