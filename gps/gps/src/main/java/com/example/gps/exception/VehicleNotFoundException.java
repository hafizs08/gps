package com.example.gps.exception;

public class VehicleNotFoundException extends RuntimeException {

    private final Object[] args;

    public VehicleNotFoundException(Long vehicleId) {
        super("Vehicle not found with ID: " + vehicleId);
        this.args = new Object[]{vehicleId};
    }

    public Object[] getArgs() {
        return args;
    }
}