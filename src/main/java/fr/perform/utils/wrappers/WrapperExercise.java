package fr.perform.utils.wrappers;

import fr.perform.service.dto.ExerciseDTO;
import fr.perform.service.dto.ExerciseTypeDTO;
import fr.perform.service.dto.SerieDTO;

import java.util.List;

public class WrapperExercise {

    // Exercise
    private Long id;
    private String name;
    private Integer number;
    private String comment;

    // ExerciseType
    private Long exerciseTypeId;
    private String exerciseTypeName;
    private String description;

    // Series
    private List<SerieDTO> serieDTOList;

    /*
     * Default empty contructor, use setters.
     */
    public WrapperExercise() {

    }

    /**
     * Constructor with parameters, all attributes setted.
     *
     * @param exerciseDTO          the exerciseDTO
     * @param exerciseTypeDTO      the exerciseTypeDTO
     * @param serieDTOList the list of serieDTO
     */
    public WrapperExercise(ExerciseDTO exerciseDTO, ExerciseTypeDTO exerciseTypeDTO, List<SerieDTO> serieDTOList) {

        super();
        // Exercise
        this.id = exerciseDTO.getId();
        this.name = exerciseDTO.getName();
        this.number = exerciseDTO.getNumber();
        this.comment = exerciseDTO.getComment();

        // ExerciseType
        this.exerciseTypeId = exerciseTypeDTO.getId();
        this.exerciseTypeName = exerciseTypeDTO.getName();
        this.description = exerciseTypeDTO.getDescription();

        // Series
        this.serieDTOList = serieDTOList;
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

    public Long getExerciseTypeId() {
        return exerciseTypeId;
    }

    public void setExerciseTypeId(Long exerciseTypeId) {
        this.exerciseTypeId = exerciseTypeId;
    }

    public String getExerciseTypeName() {
        return exerciseTypeName;
    }

    public void setExerciseTypeName(String exerciseTypeName) {
        this.exerciseTypeName = exerciseTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SerieDTO> getSerieDTOList() {
        return serieDTOList;
    }

    public void setSerieDTOList(List<SerieDTO> serieDTOList) {
        this.serieDTOList = serieDTOList;
    }
}
