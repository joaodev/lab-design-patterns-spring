package com.joaodev.labdesignpatternsspring.service.shipping;

public abstract class ShippingDecorator implements ShippingStrategy {

    protected final ShippingStrategy wrapped;

    protected ShippingDecorator(ShippingStrategy wrapped) {
        this.wrapped = wrapped;
    }
}
