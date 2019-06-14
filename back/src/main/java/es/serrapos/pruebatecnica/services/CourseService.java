package es.serrapos.pruebatecnica.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.serrapos.pruebatecnica.exceptions.CoursesDuplicatedExceptions;
import es.serrapos.pruebatecnica.exceptions.EntityNotFoundException;
import es.serrapos.pruebatecnica.model.dao.CourseDao;
import es.serrapos.pruebatecnica.model.dao.FileTopicDao;
import es.serrapos.pruebatecnica.model.dao.TeacherDao;
import es.serrapos.pruebatecnica.model.entities.Course;
import es.serrapos.pruebatecnica.model.entities.FileTopic;
import es.serrapos.pruebatecnica.model.entities.Teacher;

/**
 * Management all logic of Course APP
 * @author sraposov
 *
 */
@Service
public class CourseService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
    
    private CourseDao courseDao;
    private TeacherDao teacherDao;
    private FileTopicDao fileTopicDao;
    
    /*COURSES*/
    public CourseService(CourseDao courseDao, TeacherDao teacherDao, FileTopicDao fileTopicDao) {
		log.debug("CourseService Constructor");
        this.courseDao = courseDao;
        this.teacherDao = teacherDao;
        this.fileTopicDao = fileTopicDao;
    }
    
    public Course createCourse(Course course) throws CoursesDuplicatedExceptions{
    	//Validation title not duplicate
    	if(this.courseDao.existsByTitle(course.getTitle())) {
    		throw new CoursesDuplicatedExceptions();
    	}
    	return this.courseDao.create(course);        
    }
 
    public Course updateCourse(Long id, Course course) throws EntityNotFoundException, CoursesDuplicatedExceptions {
    	//Validation title not duplicate
		if (this.courseDao.existsByTitleAndDistintId(course.getTitle(), id)) {
    		throw new CoursesDuplicatedExceptions();
    	}
        return this.courseDao.update(id, course);
    }
 
    public void deleteCourse(Long id) throws EntityNotFoundException {
        this.courseDao.delete(id);
    }
 
    public List<Course> findAllCourses() {
        return this.courseDao.findAll();
    }
 
    public Course findOneCourse(Long id) throws EntityNotFoundException {
        return this.courseDao.findOne(id);
    }
    
    public List<Course> findAllActiveCourses(){
    	return this.courseDao.findAllActive();
    }
    
    /*TEACHERS*/
    public Teacher createTeacher(Teacher teacher) {
        return this.teacherDao.create(teacher);
    }
 
    public Teacher updateTeacher(Long id, Teacher teacher) throws EntityNotFoundException {
        return this.teacherDao.update(id, teacher);
    }
 
    public void deleteTeacher(Long id) throws EntityNotFoundException {
        this.teacherDao.delete(id);
    }
 
    public List<Teacher> findAllTeacher() {
        return this.teacherDao.findAll();
    }
 
    public Teacher findOneTeacher(Long id) throws EntityNotFoundException {
        return this.teacherDao.findOne(id);
    }
    
    /*FILES*/
    public FileTopic createFile(FileTopic file) {
        return this.fileTopicDao.create(file);
    }
 
    public void deleteFile(Long id) throws EntityNotFoundException {
        this.fileTopicDao.delete(id);
    }
 
    public FileTopic findOneFile(Long id) throws EntityNotFoundException {
        return this.fileTopicDao.findOne(id);
    }

}
