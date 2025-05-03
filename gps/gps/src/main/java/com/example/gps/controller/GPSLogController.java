package com.example.gps.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gps.dto.request.GPSLogRequestDTO;
import com.example.gps.dto.response.GPSLogResponseDTO;
import com.example.gps.service.GPSLogService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import com.example.gps.dto.response.ApiResponse;
import org.springframework.context.MessageSource;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GPSLogController {

    private final GPSLogService gpsLogService;
    private final MessageSource messageSource;

    @PostMapping("/gps")
    public ResponseEntity<?> logGPS(@RequestBody @Valid GPSLogRequestDTO dto, Locale locale) {
        gpsLogService.saveGPSLog(dto);
        String msg = messageSource.getMessage("application.success.gps.saved", null, locale);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(msg, null));
    }
}
