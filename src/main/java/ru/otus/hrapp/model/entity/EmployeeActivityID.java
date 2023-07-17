package ru.otus.hrapp.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeActivityID implements Serializable {

    private Long employeeId;

    private Long activityId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeActivityID)) return false;
        EmployeeActivityID that = (EmployeeActivityID) o;
        return employeeId == that.employeeId &&
                activityId == that.activityId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, activityId);
    }
}
