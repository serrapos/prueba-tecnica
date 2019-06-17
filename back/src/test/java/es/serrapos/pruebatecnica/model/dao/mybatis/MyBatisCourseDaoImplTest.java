package es.serrapos.pruebatecnica.model.dao.mybatis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import es.serrapos.pruebatecnica.exceptions.EntityNotFoundException;
import es.serrapos.pruebatecnica.model.entities.Course;
import es.serrapos.pruebatecnica.model.enums.CourseLevel;
import es.serrapos.pruebatecnica.model.mappers.CourseMapper;

@RunWith(MockitoJUnitRunner.class)
public class MyBatisCourseDaoImplTest {

	/**
	 * Class to test
	 */
	// @InjectMocks
	private static MyBatisCourseDaoImpl myBatisCourseDaoImpl;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBefore() throws Exception {
		myBatisCourseDaoImpl = inicializarMyBatisCourseDaoImpl();
	}

	/**	
	 */
	@Test
	public void whenCreatedACourse() {

		// Inicializacion de parametros de entrada
		Course courseInit = inicializarCourseValido();
		courseInit.setId(null);
		// Llamada al servicio
		Course result = myBatisCourseDaoImpl.create(courseInit);
		assertNotNull(result);
		// assertEquals(Long.valueOf(50l), result.getId());
	}

	@Test
	public void whenSearchACourseByTitle() {
		Course course = inicializarCourseValido();
		CourseMapper courseMapper = inicializarCourseMapper();
		try {
			// Mockito.when(courseMapper.getByTitle(course.getTitle())).thenReturn(course);
			Course result = myBatisCourseDaoImpl.findOneByTitle(course.getTitle());
			assertEquals(course.getTitle(), result.getTitle());
		} catch (EntityNotFoundException e) {
			// fail();
		}
	}


	/**
	 * Metodo que inicializa las variables del tipo MyBatisCourseDaoImpl
	 */
	public static MyBatisCourseDaoImpl inicializarMyBatisCourseDaoImpl() {
		try {
			CourseMapper courseMapper = inicializarCourseMapper();
			MyBatisCourseDaoImpl myBatisCourseDaoImpl = new MyBatisCourseDaoImpl(courseMapper);
			return myBatisCourseDaoImpl;
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * Metodo que inicializa las variables del tipo CourseMapper
	 */
	public static CourseMapper inicializarCourseMapper() {
		try {
			CourseMapper courseMapper = Mockito.mock(CourseMapper.class);
			return courseMapper;
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * Metodo que inicializa las variables del tipo Course
	 */
	public static Course inicializarCourseValido() {
		try {
			Course course = new Course();
			course.setId(50L);
			course.setFileId(1L);
			course.setLevel(CourseLevel.BASICO);
			course.setNumberOfHours(5);
			course.setState(true);
			course.setTeacher(null);
			course.setTitle("Curso Mockito");
			return course;
		} catch (Throwable e) {
			return null;
		}
	}

}

