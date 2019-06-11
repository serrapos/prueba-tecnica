package es.serrapos.pruebatecnica.model.services.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import es.serrapos.pruebatecnica.model.entities.Course;
import es.serrapos.pruebatecnica.model.exceptions.EntityNotFoundException;
import es.serrapos.pruebatecnica.model.mappers.CourseMapper;
import es.serrapos.pruebatecnica.model.services.CourseService;

@Service
public class MyBatisCourseService implements CourseService{
	
	private CourseMapper courseMapper;
	 
    public MyBatisCourseService(final CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }
 
    @Override
    public Course create(Course course) {
        this.courseMapper.insert(course);
        return course;
    }
 
    @Override
    public Course update(Long id, Course course) throws EntityNotFoundException {
        Course courseBD = this.courseMapper.getById(id);
        if (courseBD == null) {
            throw new EntityNotFoundException("No existe el registro a modificar");
        }
        courseBD.setTitle(course.getTitle());
        courseBD.setLevel(course.getLevel());
        courseBD.setNumberOfHours(course.getNumberOfHours());
        courseBD.setTeacher(course.getTeacher());
        courseBD.setState(course.getState());
 
        this.courseMapper.update(courseBD);
        return courseBD;
    }
 
    @Override
    public void delete(Long id) throws EntityNotFoundException {
        if (this.courseMapper.getById(id) == null) {
            throw new EntityNotFoundException("No existe el registro a eliminar");
        }
        this.courseMapper.deleteById(id);
    }
 
    @Override
    public List findAll() {
        return this.courseMapper.getAll();
    }
 
    @Override
    public Course findOne(Long id) throws EntityNotFoundException {
        Course course = this.courseMapper.getById(id);
        if (course == null) {
            throw new EntityNotFoundException("No existe el registro en el sistema");
        }
        return course;
    }

}
