package com.example.gps.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleResponseDTO {
    private Long id;
    private String plateNumber;
    private String name;
    private String type;
}
