package fr.perform.utils.wrappers;


import fr.perform.service.dto.WorkoutDTO;
import fr.perform.service.dto.WorkoutGoalDTO;

import java.time.LocalDate;
import java.util.List;

public class WrapperWorkout {

    // Workout
    private Long id;
    private String name;
    private LocalDate date;
    private String comment;

    // WorkoutGoal
    private Long workoutGoalId;
    private String workoutGoalName;

    // Exercises
    private List<WrapperExercise> wrapperExerciseList;

    // User
    private Long userId;

    /*
     * Default empty contructor, use setters.
     */
    public WrapperWorkout() {

    }

    /**
     * Constructor with parameters, all attributes setted.
     *
     * @param workoutDTO          the workoutDTO
     * @param workoutGoalDTO      the workoutGoalDTO
     * @param wrapperExerciseList the list of wrapperExercise
     */
    public WrapperWorkout(WorkoutDTO workoutDTO, WorkoutGoalDTO workoutGoalDTO, List<WrapperExercise> wrapperExerciseList) {

        super();

        // Workout
        this.id = workoutDTO.getId();
        this.name = workoutDTO.getName();
        this.date = workoutDTO.getDate();
        this.comment = workoutDTO.getComment();

        // WorkoutGoal
        this.workoutGoalId = workoutGoalDTO.getId();
        this.workoutGoalName = workoutGoalDTO.getName();

        // Exercises
        this.wrapperExerciseList = wrapperExerciseList;

        // User
        this.userId = workoutDTO.getUserId();
    }

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

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

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

    public String getWorkoutGoalName() {
        return workoutGoalName;
    }

    public void setWorkoutGoalName(String workoutGoalName) {
        this.workoutGoalName = workoutGoalName;
    }

    public List<WrapperExercise> getWrapperExerciseList() {
        return wrapperExerciseList;
    }

    public void setWrapperExerciseList(List<WrapperExercise> wrapperExerciseList) {
        this.wrapperExerciseList = wrapperExerciseList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
