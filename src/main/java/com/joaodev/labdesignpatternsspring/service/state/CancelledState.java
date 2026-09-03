package com.joaodev.labdesignpatternsspring.service.state;

public class CancelledState extends BaseOrderState {

    @Override
    public String getStatusName() {
        return "CANCELLED";
    }
}
