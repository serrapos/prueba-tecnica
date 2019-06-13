package es.serrapos.pruebatecnica.model.entities;

import java.io.Serializable;

import es.serrapos.pruebatecnica.model.enums.CourseLevel;
import lombok.Data;

/**
 * Entity for Course
 * @author Sergio Raposo Vargas
 * @version 1.0
 */
@Data
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of Course
     */
    private Long id;

    /**
     * Title of Course
     */
    private String title;

    /**
     * Level of Course
     */
    private CourseLevel level;

    /**
     * Número de horas del curso
     */
    private Integer numberOfHours;

    /**
     * Teacher Id of Course
     */
    private Teacher teacher;

    /**
     * State of Course
     */
    private Boolean state;
    
    /**
     * State of Course
     */
    private Long fileId;

}