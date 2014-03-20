package com.lkulig.retry.util.builder;

public abstract class AbstractBuildableBuilder<TYPE extends Buildable, BUILDER extends AbstractBuildableBuilder<TYPE, BUILDER>> {

    protected TYPE buildable;
    protected BUILDER builder;

    public AbstractBuildableBuilder() {
        buildable = createBuildable();
        builder = getBuilder();
    }

    public abstract TYPE build();

    protected abstract TYPE createBuildable();

    protected abstract BUILDER getBuilder();

}
