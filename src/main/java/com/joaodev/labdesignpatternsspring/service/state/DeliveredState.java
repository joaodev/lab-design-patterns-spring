package com.joaodev.labdesignpatternsspring.service.state;

public class DeliveredState extends BaseOrderState {

    @Override
    public String getStatusName() {
        return "DELIVERED";
    }
}
