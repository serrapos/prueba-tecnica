package es.serrapos.pruebatecnica.model.services;

import java.util.List;
import java.util.Optional;

import es.serrapos.pruebatecnica.model.entities.Course;
import es.serrapos.pruebatecnica.model.exceptions.EntityNotFoundException;

public interface CourseService {
	
	public Course create(Course course);
    public Course update(Long id, Course course) throws EntityNotFoundException;
    public void delete(Long id) throws EntityNotFoundException;
    public List<Course> findAll();
    public Course findOne(Long id) throws EntityNotFoundException;

}
