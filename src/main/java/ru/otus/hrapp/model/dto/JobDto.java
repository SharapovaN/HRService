package ru.otus.hrapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {
    private Long id;
    private String title;
    private String description;
}
