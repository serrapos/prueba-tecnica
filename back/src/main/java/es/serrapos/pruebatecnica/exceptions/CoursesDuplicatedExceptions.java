package es.serrapos.pruebatecnica.exceptions;

public class CoursesDuplicatedExceptions extends Exception{
	
	public CoursesDuplicatedExceptions() {
		super("Validation Error: There is already a course with that title");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
