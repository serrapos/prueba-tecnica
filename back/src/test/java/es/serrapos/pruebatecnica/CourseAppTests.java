package es.serrapos.pruebatecnica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import es.serrapos.pruebatecnica.model.entities.Course;
import es.serrapos.pruebatecnica.model.entities.FileTopic;
import es.serrapos.pruebatecnica.model.entities.Teacher;
import es.serrapos.pruebatecnica.model.enums.CourseLevel;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CourseAppTests {

	static final String ENDPOINT_COURSES = "/api/v1/course";
	static final String ENDPOINT_TEACHER = "/api/v1/teacher";
	static final String ENDPOINT_FILES = "/api/v1/filetopics";

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testAllCourseApp() {

		// Creamos fichero
		FileTopic file = mockFile();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		httpHeaders.setContentDispositionFormData("file", file.getFilename());

		MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
		form.add("file", file.getContent());

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(form, httpHeaders);

		ResponseEntity<FileTopic> responseCreateFile = restTemplate.exchange(ENDPOINT_FILES + "/upload",
				HttpMethod.POST,
				requestEntity,
				new ParameterizedTypeReference<FileTopic>() {
				});
		assertTrue(responseCreateFile.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(responseCreateFile.getBody());
		assertNotNull(responseCreateFile.getBody().getId());

		// Descargamos fichero
		ResponseEntity<FileTopic> responseGetFile = restTemplate.exchange(
				ENDPOINT_COURSES + "/" + responseCreateFile.getBody().getId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<FileTopic>() {
				});
		assertTrue(responseGetFile.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(responseGetFile.getBody());
		assertEquals(responseCreateFile.getBody().getId(), responseGetFile.getBody().getId());

		// Obtenemos los profesores
		ResponseEntity<List<Teacher>> responseTeachers = restTemplate.exchange(ENDPOINT_TEACHER, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Teacher>>() {
				});
		assertTrue(responseTeachers.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(responseTeachers.getBody());

		// Creamos un curso
		HttpEntity<Course> entityCourse = new HttpEntity<>(mockCourse());
		entityCourse.getBody().setFileId(responseCreateFile.getBody().getId());
		ResponseEntity<Course> responseCreateCourse = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.POST,
				entityCourse, Course.class);
		assertTrue(responseCreateCourse.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(responseCreateCourse.getBody());
		assertNotNull(responseCreateCourse.getBody().getId());
		assertEquals(Boolean.TRUE, responseCreateCourse.getBody().getState());
		assertEquals(Long.valueOf(1), responseCreateCourse.getBody().getTeacher().getId());
		assertEquals(Integer.valueOf(6), responseCreateCourse.getBody().getNumberOfHours());
		assertEquals("Course 1 JUnit", responseCreateCourse.getBody().getTitle());
		assertEquals(Long.valueOf(1), responseCreateCourse.getBody().getTeacher().getId());
		assertEquals(responseCreateFile.getBody().getId(), responseCreateCourse.getBody().getFileId());

		// Recuperamos el curso
		ResponseEntity<Course> responseGetCourse = restTemplate.exchange(
				ENDPOINT_COURSES + "/" + responseCreateCourse.getBody().getId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<Course>() {
				});
		assertTrue(responseGetCourse.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(responseGetCourse.getBody());
		assertNotNull(responseGetCourse.getBody().getId());
		assertEquals(responseCreateCourse.getBody().getId(), responseGetCourse.getBody().getId());
		assertEquals(Boolean.TRUE, responseGetCourse.getBody().getState());
		assertEquals(Integer.valueOf(6), responseGetCourse.getBody().getNumberOfHours());
		assertEquals("Course 1 JUnit", responseGetCourse.getBody().getTitle());
		assertEquals(Long.valueOf(1), responseGetCourse.getBody().getTeacher().getId());
		assertEquals(responseCreateFile.getBody().getId(), responseGetCourse.getBody().getFileId());

		// Actualizamos el curso
		HttpEntity<Course> entityCourseUpdate = new HttpEntity<>(mockCourse());
		entityCourseUpdate.getBody().setTitle("Course UPDATED");
		entityCourseUpdate.getBody().setFileId(responseCreateFile.getBody().getId());
		ResponseEntity<Course> responseUpdateCourse = restTemplate.exchange(
				ENDPOINT_COURSES + "/" + responseCreateCourse.getBody().getId(), HttpMethod.PUT,
				entityCourseUpdate, Course.class);
		assertTrue(responseUpdateCourse.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(responseUpdateCourse.getBody());
		assertEquals(responseCreateCourse.getBody().getId(), responseUpdateCourse.getBody().getId());
		assertEquals(Boolean.TRUE, responseUpdateCourse.getBody().getState());
		assertEquals(Integer.valueOf(6), responseUpdateCourse.getBody().getNumberOfHours());
		assertEquals("Course UPDATED", responseUpdateCourse.getBody().getTitle());
		assertEquals(Long.valueOf(1), responseUpdateCourse.getBody().getTeacher().getId());
		assertEquals(responseCreateFile.getBody().getId(), responseUpdateCourse.getBody().getFileId());

		// Creamos curso auxiliar inactivo para validar activos y duplicate
		HttpEntity<Course> entityCourseAux = new HttpEntity<>(mockCourse());
		entityCourseAux.getBody().setTitle("Course Ya Existente");
		entityCourseAux.getBody().setState(Boolean.FALSE);
		ResponseEntity<Course> responseCreateCourseAux = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.POST,
				entityCourseAux, Course.class);
		assertTrue(responseCreateCourseAux.getStatusCode().equals(HttpStatus.OK));

		// Validamos Duplicate error en create curso
		HttpEntity<Course> entityCourseDuplicate = new HttpEntity<>(mockCourse());
		entityCourseDuplicate.getBody().setTitle("Course Ya Existente");
		entityCourseDuplicate.getBody().setState(Boolean.FALSE);
		ResponseEntity<String> responseCreateCourseDuplicate = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.POST,
				entityCourseDuplicate, new ParameterizedTypeReference<String>() {
				});
		assertTrue(responseCreateCourseDuplicate.getStatusCode().equals(HttpStatus.CONFLICT));

		// Validamos update curso duplicate
		HttpEntity<Course> entityCourseUpdateDuplicate = new HttpEntity<>(mockCourse());
		entityCourseUpdateDuplicate.getBody().setTitle("Course Ya Existente");
		ResponseEntity<String> responseUpdateCourseDuplicate = restTemplate.exchange(
				ENDPOINT_COURSES + "/" + responseCreateCourse.getBody().getId(), HttpMethod.PUT,
				entityCourseUpdateDuplicate,
				new ParameterizedTypeReference<String>() {
				});
		assertTrue(responseUpdateCourseDuplicate.getStatusCode().equals(HttpStatus.CONFLICT));

		// Validamos que no esta en listado active el aux
		ResponseEntity<List<Course>> responseGetActives = restTemplate.exchange(ENDPOINT_COURSES + "/active",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Course>>() {
				});
		assertTrue(responseGetActives.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(responseGetActives.getBody());
//		for (Course c : responseGetActives.getBody().listIterator()) {
//			assertEquals(true, c.getState());
//		}

		// Borramos curso
		ResponseEntity<String> responseDeleteCourse = restTemplate.exchange(
				ENDPOINT_COURSES + "/" + responseCreateCourse.getBody().getId(), HttpMethod.DELETE, null,
				new ParameterizedTypeReference<String>() {
				});

		assertTrue(responseDeleteCourse.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(responseDeleteCourse.getBody());
		assertEquals("Course deleted", responseDeleteCourse.getBody());

		ResponseEntity<Course> responseGetDeletedCourse = restTemplate.exchange(
				ENDPOINT_COURSES + "/" + responseCreateCourse.getBody().getId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<Course>() {
				});
		assertTrue(responseGetDeletedCourse.getStatusCode().equals(HttpStatus.NOT_FOUND));

		// Borramos fichero
		ResponseEntity<String> responseDeleteFile = restTemplate.exchange(
				ENDPOINT_FILES + "/" + responseCreateFile.getBody().getId(), HttpMethod.DELETE, null,
				new ParameterizedTypeReference<String>() {
				});

		assertTrue(responseDeleteFile.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(responseDeleteFile.getBody());
		assertEquals("File deleted", responseDeleteFile.getBody());

		ResponseEntity<Course> responseGetDeletedFile = restTemplate.exchange(
				ENDPOINT_FILES + "/" + responseCreateFile.getBody().getId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<Course>() {
				});
		assertTrue(responseGetDeletedFile.getStatusCode().equals(HttpStatus.NOT_FOUND));
	}

	private Course mockCourse() {
		Course course = new Course();
		course.setState(Boolean.TRUE);
		course.setNumberOfHours(6);
		course.setTitle("Course 1 JUnit");
		course.setLevel(CourseLevel.BASICO);
		course.setTeacher(new Teacher());
		course.getTeacher().setId(1L);
		return course;
	}

	private FileTopic mockFile() {
		FileTopic file = new FileTopic();
		file.setFilename("file-test.txt");
		file.setMediatype("text/plain");
		file.setContent("contenido del fichero test".getBytes());
		return file;
	}

}
