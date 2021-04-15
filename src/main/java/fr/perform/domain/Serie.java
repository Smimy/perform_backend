package fr.perform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Serie.
 */
@Entity
@Table(name = "serie")
public class Serie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @NotNull
    @Column(name = "reps_achieved", nullable = false)
    private Integer repsAchieved;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Float weight;

    @Column(name = "percent_1_rm")
    private Float percent1RM;

    @Column(name = "resting_time")
    private Integer restingTime;

    @ManyToOne(optional = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnoreProperties(value = "series", allowSetters = true)
    private Exercise exercise;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Serie number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getRepsAchieved() {
        return repsAchieved;
    }

    public Serie repsAchieved(Integer repsAchieved) {
        this.repsAchieved = repsAchieved;
        return this;
    }

    public void setRepsAchieved(Integer repsAchieved) {
        this.repsAchieved = repsAchieved;
    }

    public Float getWeight() {
        return weight;
    }

    public Serie weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getPercent1RM() {
        return percent1RM;
    }

    public Serie percent1RM(Float percent1RM) {
        this.percent1RM = percent1RM;
        return this;
    }

    public void setPercent1RM(Float percent1RM) {
        this.percent1RM = percent1RM;
    }

    public Integer getRestingTime() {
        return restingTime;
    }

    public Serie restingTime(Integer restingTime) {
        this.restingTime = restingTime;
        return this;
    }

    public void setRestingTime(Integer restingTime) {
        this.restingTime = restingTime;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public Serie exercise(Exercise exercise) {
        this.exercise = exercise;
        return this;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Serie)) {
            return false;
        }
        return id != null && id.equals(((Serie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Serie{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", repsAchieved=" + getRepsAchieved() +
            ", weight=" + getWeight() +
            ", percent1RM=" + getPercent1RM() +
            ", restingTime=" + getRestingTime() +
            "}";
    }
}
