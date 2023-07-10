package ru.otus.hrapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class EmployeeProjectID implements Serializable {

    private int projectId;

    private int employeeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeProjectID)) return false;
        EmployeeProjectID that = (EmployeeProjectID) o;
        return projectId == that.projectId &&
                employeeId == that.employeeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, employeeId);
    }
}
