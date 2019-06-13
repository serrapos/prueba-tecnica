package es.serrapos.pruebatecnica.model.dao;

import java.util.List;

import es.serrapos.pruebatecnica.exceptions.EntityNotFoundException;
import es.serrapos.pruebatecnica.model.entities.Course;

public interface CourseDao {
	
	public Course create(Course course);
    public Course update(Long id, Course course) throws EntityNotFoundException;
    public void delete(Long id) throws EntityNotFoundException;
    public List<Course> findAll();
    public List<Course> findAllActive();
    public Course findOne(Long id) throws EntityNotFoundException;
    public Course findOneByTitle(String title) throws EntityNotFoundException;
    public boolean existsByTitle(String title);
    public boolean existsByTitleAndDistintId(String title, Long id);

}
