package fr.perform.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link fr.perform.domain.Workout} entity.
 */
public class WorkoutDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate date;

    private String comment;


    private Long workoutGoalId;

    private Long userId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getWorkoutGoalId() {
        return workoutGoalId;
    }

    public void setWorkoutGoalId(Long workoutGoalId) {
        this.workoutGoalId = workoutGoalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkoutDTO)) {
            return false;
        }

        return id != null && id.equals(((WorkoutDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkoutDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", workoutGoalId=" + getWorkoutGoalId() +
            ", userId=" + getUserId() +
            "}";
    }
}
