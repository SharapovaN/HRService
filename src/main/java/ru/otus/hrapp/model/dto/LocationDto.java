package ru.otus.hrapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private Long id;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;
}
