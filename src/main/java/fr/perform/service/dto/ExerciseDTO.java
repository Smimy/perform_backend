package fr.perform.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link fr.perform.domain.Exercise} entity.
 */
public class ExerciseDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer number;

    private String comment;


    private Long workoutId;

    private Long exerciseTypeId;
    
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public Long getExerciseTypeId() {
        return exerciseTypeId;
    }

    public void setExerciseTypeId(Long exerciseTypeId) {
        this.exerciseTypeId = exerciseTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExerciseDTO)) {
            return false;
        }

        return id != null && id.equals(((ExerciseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExerciseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", number=" + getNumber() +
            ", comment='" + getComment() + "'" +
            ", workoutId=" + getWorkoutId() +
            ", exerciseTypeId=" + getExerciseTypeId() +
            "}";
    }
}
