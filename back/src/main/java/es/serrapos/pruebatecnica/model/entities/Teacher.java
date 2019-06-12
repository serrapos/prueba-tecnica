package es.serrapos.pruebatecnica.model.entities;

import java.io.Serializable;

import lombok.Data;

/**
 * Entity Teacher
 * @author Sergio Raposo Vargas
 * @version 1.0
 */
@Data
public class Teacher implements Serializable{
	
	private static final long serialVersionUID = 1L;

    /**
     * ID of Course
     */
    private Long id;
    
    private String firstName;
    
    private String lastName;

}
