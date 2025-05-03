package com.example.gps.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GPSLogResponseDTO {
    private Double latitude;
    private Double longitude;
    private Double speed;
    private LocalDateTime timestamp;
    private boolean speedViolation;
}
