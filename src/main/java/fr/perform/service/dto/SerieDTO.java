package fr.perform.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link fr.perform.domain.Serie} entity.
 */
public class SerieDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer number;

    @NotNull
    private Integer repsAchieved;

    @NotNull
    private Float weight;

    private Float percent1RM;

    private Integer restingTime;


    private Long exerciseId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getRepsAchieved() {
        return repsAchieved;
    }

    public void setRepsAchieved(Integer repsAchieved) {
        this.repsAchieved = repsAchieved;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getPercent1RM() {
        return percent1RM;
    }

    public void setPercent1RM(Float percent1RM) {
        this.percent1RM = percent1RM;
    }

    public Integer getRestingTime() {
        return restingTime;
    }

    public void setRestingTime(Integer restingTime) {
        this.restingTime = restingTime;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SerieDTO)) {
            return false;
        }

        return id != null && id.equals(((SerieDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SerieDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", repsAchieved=" + getRepsAchieved() +
            ", weight=" + getWeight() +
            ", percent1RM=" + getPercent1RM() +
            ", restingTime=" + getRestingTime() +
            ", exerciseId=" + getExerciseId() +
            "}";
    }
}
