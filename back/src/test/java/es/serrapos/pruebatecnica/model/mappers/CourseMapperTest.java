package es.serrapos.pruebatecnica.model.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import es.serrapos.pruebatecnica.model.entities.Course;
import es.serrapos.pruebatecnica.model.entities.Teacher;
import es.serrapos.pruebatecnica.model.enums.CourseLevel;

@RunWith(SpringRunner.class)
@MybatisTest
public class CourseMapperTest {

	@Autowired
	private CourseMapper courseMapper;

	@Test
	public void fullProcess() {

		List<Course> allCourseInit = courseMapper.getAll();

		// Insert test
		Course course = inicializarCourseValido();
		course.setId(null);
		courseMapper.insert(course);
		assertNotNull(course.getId());

		// Get By Id test
		Course courseGet = courseMapper.getById(course.getId());
		assertEquals(course.getId(), courseGet.getId());

		// GetAll test
		List<Course> allCourseAfterInsert = courseMapper.getAll();
		assertEquals(allCourseInit.size() + 1, allCourseAfterInsert.size());

		// Update tets
		course.setTitle("Test Update");
		courseMapper.update(course);
		assertEquals("Test Update", course.getTitle());

		// Find by title test
		Course courseByTitle = courseMapper.getByTitle("Test Update");
		assertEquals("Test Update", courseByTitle.getTitle());

		// Title not found test
		Course courseNotFound = courseMapper.getByTitle("Courese Not Found");
		assertNull(courseNotFound);
		assertEquals("Test Update", courseByTitle.getTitle());
		
		// Exist by title test
		boolean existsByTitle = courseMapper.checkCourseExistsByTitle("Test Update");
		assertEquals(true, existsByTitle);
		
		boolean existsByTitleNotFound = courseMapper.checkCourseExistsByTitle("Courese Not Found");
		assertEquals(false, existsByTitleNotFound);
		
		// Filter by Active
		List<Course> coursesNoActive = courseMapper.getAllActive();
		List<Course> courseAfterFilter = coursesNoActive.stream().filter(c -> !c.getState())
				.collect(Collectors.toList());
		assertEquals(0, courseAfterFilter.size());

	}

	public static Course inicializarCourseValido() {
		try {
			Course course = new Course();
			Teacher teacher = new Teacher();
			teacher.setId(1l);
			course.setId(50L);
			course.setFileId(null);
			course.setLevel(CourseLevel.BASICO);
			course.setNumberOfHours(5);
			course.setState(true);
			course.setTeacher(teacher);
			course.setTitle("Curso Mockito");
			return course;
		} catch (Throwable e) {
			return null;
		}
	}

}
