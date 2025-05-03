package com.example.gps.service;

import com.example.gps.dto.request.GPSLogRequestDTO;
import com.example.gps.dto.response.GPSLogResponseDTO;
import com.example.gps.entity.GPSLog;
import com.example.gps.entity.Vehicle;
import com.example.gps.exception.VehicleNotFoundException;
import com.example.gps.repository.GPSLogRepository;
import com.example.gps.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GPSLogService {

    private final GPSLogRepository gpsLogRepository;
    private final VehicleRepository vehicleRepository;

    private static final double SPEED_LIMIT = 100.0;

    @Transactional
    public void saveGPSLog(GPSLogRequestDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(dto.getVehicleId()));

        GPSLog log = new GPSLog();
        log.setVehicle(vehicle);
        log.setLatitude(dto.getLatitude());
        log.setLongitude(dto.getLongitude());
        log.setSpeed(dto.getSpeed());
        log.setTimestamp(dto.getTimestamp());
        log.setSpeedViolation(dto.getSpeed() > SPEED_LIMIT);

        gpsLogRepository.save(log);
    }

    public GPSLogResponseDTO getLastLocation(Long vehicleId) {
        GPSLog log = gpsLogRepository.findTopByVehicleIdOrderByTimestampDesc(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));
        return toResponse(log);
    }

    public List<GPSLogResponseDTO> getHistory(Long vehicleId, LocalDateTime from, LocalDateTime to) {
        return gpsLogRepository.findByVehicleIdAndTimestampBetweenOrderByTimestampAsc(vehicleId, from, to)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private GPSLogResponseDTO toResponse(GPSLog log) {
        return new GPSLogResponseDTO(
                log.getLatitude(),
                log.getLongitude(),
                log.getSpeed(),
                log.getTimestamp(),
                log.isSpeedViolation()
        );
    }
}
