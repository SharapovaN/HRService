package ru.otus.hrapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.ActivityStatus;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

    private LocalDate startDate;
    private LocalDate endDate;
}
