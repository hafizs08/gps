package com.example.gps.service;

import com.example.gps.entity.Vehicle;
import com.example.gps.exception.VehicleNotFoundException;
import com.example.gps.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository repository;

    public Vehicle createVehicle(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
    }

    public Vehicle updateVehicle(Long id, Vehicle vehicleDetails) {
        Vehicle vehicle = getVehicleById(id);
        vehicle.setPlateNumber(vehicleDetails.getPlateNumber());
        vehicle.setType(vehicleDetails.getType());
        vehicle.setName(vehicleDetails.getName());
        return repository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        repository.deleteById(id);
    }
}
