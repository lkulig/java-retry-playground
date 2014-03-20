package com.lkulig.retry.util.spring;

import com.lkulig.retry.util.builder.AbstractBuildableBuilder;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.policy.CompositeRetryPolicy;

public class RetryTemplateBuilder extends AbstractBuildableBuilder<RetryTemplate, RetryTemplateBuilder> {

    public static RetryTemplateBuilder retryTemplate() {
        return new RetryTemplateBuilder();
    }

    public RetryTemplateBuilder withPolicies(RetryPolicy... policies) {
        CompositeRetryPolicy compositePolicy = new CompositeRetryPolicy();
        compositePolicy.setPolicies(policies);
        buildable.setRetryPolicy(compositePolicy);
        return this;
    }

    public RetryTemplateBuilder withPolicies(RetryPolicy retryPolicy, BackOffPolicy backOffPolicy) {
        buildable.setRetryPolicy(retryPolicy);
        buildable.setBackOffPolicy(backOffPolicy);
        return this;
    }

    public RetryTemplateBuilder withPolicies(BackOffPolicy backOffPolicy) {
        buildable.setBackOffPolicy(backOffPolicy);
        return this;
    }

    @Override
    public RetryTemplate build() {
        return buildable;
    }

    @Override
    protected RetryTemplate createBuildable() {
        return new RetryTemplate();
    }

    @Override
    protected RetryTemplateBuilder getBuilder() {
        return this;
    }
}
