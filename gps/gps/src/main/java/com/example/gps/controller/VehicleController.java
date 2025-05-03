package com.example.gps.controller;

import com.example.gps.entity.Vehicle;
import com.example.gps.service.GPSLogService;
import com.example.gps.service.VehicleService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.gps.dto.response.ApiResponse;
import org.springframework.context.MessageSource;
import java.util.Locale;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService service;
    private final GPSLogService gpsLogService;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Vehicle> vehicles = service.getAllVehicles();
        String msg = messageSource.getMessage("application.success.vehicle.found", null, null);
        return ResponseEntity.ok(new ApiResponse<>(msg, vehicles));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Vehicle vehicle, Locale locale) {
        Vehicle created = service.createVehicle(vehicle);
        String msg = messageSource.getMessage("application.success.vehicle.created", null, locale);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(msg, created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, Locale locale) {
        Vehicle vehicle = service.getVehicleById(id);
        String msg = messageSource.getMessage("application.success.vehicle.found", null, locale);
        return ResponseEntity.ok(new ApiResponse<>(msg, vehicle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Vehicle vehicle, Locale locale) {
        Vehicle updated = service.updateVehicle(id, vehicle);
        String msg = messageSource.getMessage("application.success.vehicle.updated", null, locale);
        return ResponseEntity.ok(new ApiResponse<>(msg, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Locale locale) {
        service.deleteVehicle(id);
        String msg = messageSource.getMessage("application.success.vehicle.deleted", null, locale);
        return ResponseEntity.ok(new ApiResponse<>(msg, null));
    }

    @GetMapping("/{id}/last-location")
    public ResponseEntity<?> getLastLocation(@PathVariable Long id) {
        return ResponseEntity.ok(gpsLogService.getLastLocation(id));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<?> getHistory(
            @PathVariable Long id,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {
        return ResponseEntity.ok(gpsLogService.getHistory(id, from, to));
    }
}
