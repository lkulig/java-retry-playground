package com.lkulig.retry.util;

import com.aida.commons.builder.AbstractBuildableBuilder;

public class FixedBackOffPolicyBuilder extends AbstractBuildableBuilder<FixedBackOffPolicy, FixedBackOffPolicyBuilder> {

    private FixedBackOffPolicyBuilder() {}

    public static FixedBackOffPolicyBuilder fixedBackOffPolicy() {
        return new FixedBackOffPolicyBuilder();
    }

    public FixedBackOffPolicyBuilder withDelay(long delay) {
        buildable.setBackOffPeriod(delay);
        return this;
    }

    @Override
    public FixedBackOffPolicy build() {
        return buildable;
    }

    @Override
    protected FixedBackOffPolicy createBuildable() {
        return new FixedBackOffPolicy();
    }

    @Override
    protected FixedBackOffPolicyBuilder getBuilder() {
        return this;
    }
}
