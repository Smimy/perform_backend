package fr.perform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Exercise.
 */
@Entity
@Table(name = "exercise")
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(optional = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnoreProperties(value = "exercises", allowSetters = true)
    private Workout workout;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "exercises", allowSetters = true)
    private ExerciseType exerciseType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Exercise name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public Exercise number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public Exercise comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Workout getWorkout() {
        return workout;
    }

    public Exercise workout(Workout workout) {
        this.workout = workout;
        return this;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public Exercise exerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
        return this;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exercise)) {
            return false;
        }
        return id != null && id.equals(((Exercise) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", number=" + getNumber() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
