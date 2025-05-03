package com.example.gps.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.gps.entity.GPSLog;

@Repository
public interface GPSLogRepository extends JpaRepository<GPSLog, Long> {
    Optional<GPSLog> findTopByVehicleIdOrderByTimestampDesc(Long vehicleId);
    List<GPSLog> findByVehicleIdAndTimestampBetweenOrderByTimestampAsc(Long vehicleId, LocalDateTime from, LocalDateTime to);
}

