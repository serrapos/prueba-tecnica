package es.serrapos.pruebatecnica.rest;

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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import es.serrapos.pruebatecnica.model.entities.Course;
import es.serrapos.pruebatecnica.model.entities.Teacher;
import es.serrapos.pruebatecnica.model.enums.CourseLevel;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CourseResourceTests {

	static final String ENDPOINT_COURSES = "/api/v1/course";

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testOptions() {
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.OPTIONS, null,
				new ParameterizedTypeReference<String>() {
				});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetAll() {
		ResponseEntity<List<Course>> response = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Course>>() {
				});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		// assertTrue(response.getBody().size() > 0);
	}

	@Test
	public void testGet() {
		ResponseEntity<Course> response = restTemplate.exchange(ENDPOINT_COURSES + "/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Course>() {
				});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getId(), Long.valueOf(1));
	}

	@Test
	public void testCreate() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		ResponseEntity<Course> response = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.POST, entity,
				Course.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getId());
		assertEquals(Boolean.TRUE, response.getBody().getState());
		assertEquals(Integer.valueOf(6), response.getBody().getNumberOfHours());
		assertEquals("Course 1 JUnit", response.getBody().getTitle());
		assertEquals(Long.valueOf(1), response.getBody().getTeacher().getId());
		assertEquals(null, response.getBody().getFileId());

	}

	@Test
	public void testCreateWithErrorTitle() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		entity.getBody().setTitle(null);
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.POST, entity,
				String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void testCreateWithErrorTeacher() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		entity.getBody().setTeacher(null);
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.POST, entity,
				String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void testCreateWithErrorLevel() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		entity.getBody().setLevel(null);
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.POST, entity,
				String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void testCreateWithErrorState() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		entity.getBody().setState(null);
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.POST, entity,
				String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void testCreateDuplicated() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		entity.getBody().setTitle("Course Duplicated");
		ResponseEntity<Course> response = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.POST, entity,
				Course.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getId());
		assertEquals(Boolean.TRUE, response.getBody().getState());
		assertEquals(Integer.valueOf(6), response.getBody().getNumberOfHours());
		assertEquals("Course Duplicated", response.getBody().getTitle());
		assertEquals(Long.valueOf(1), response.getBody().getTeacher().getId());
		assertEquals(null, response.getBody().getFileId());

		/*
		 * ResponseEntity<Course> responseDuplicated =
		 * restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.POST, entity,
		 * Course.class);
		 * assertTrue(responseDuplicated.getStatusCode().equals(HttpStatus.CONFLICT));
		 */
	}

	@Test
	public void testDelete() {

		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES + "/1", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<String>() {
				});

		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		assertEquals("Course deleted", response.getBody());

		ResponseEntity<Course> responseGet = restTemplate.exchange(ENDPOINT_COURSES + "/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Course>() {
				});
		assertTrue(responseGet.getStatusCode().equals(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testDeleteNotFound() {
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES + "/1000", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<String>() {
				});

		assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testUpdate() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		entity.getBody().setTitle("Course UPDATED");
		ResponseEntity<Course> response = restTemplate.exchange(ENDPOINT_COURSES + "/2", HttpMethod.PUT, entity,
				Course.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		assertEquals(Long.valueOf(2), response.getBody().getId());
		assertEquals(Boolean.TRUE, response.getBody().getState());
		assertEquals(Integer.valueOf(6), response.getBody().getNumberOfHours());
		assertEquals("Course UPDATED", response.getBody().getTitle());
		assertEquals(Long.valueOf(1), response.getBody().getTeacher().getId());
		assertEquals(null, response.getBody().getFileId());
	}

	@Test
	public void testUpdateNotFound() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		entity.getBody().setTitle("Course UPDATED");
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES + "/2000", HttpMethod.PUT, entity,
				String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testUpdateErrorTitle() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		entity.getBody().setTitle(null);
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES + "/2", HttpMethod.PUT, entity,
				String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void testUpdateErrorLevel() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		entity.getBody().setLevel(null);
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES + "/2", HttpMethod.PUT, entity,
				String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void testUpdateErrorState() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		entity.getBody().setState(null);
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES + "/2", HttpMethod.PUT, entity,
				String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void testUpdateErrorTeacher() {
		HttpEntity<Course> entity = new HttpEntity<>(mockCourse());
		entity.getBody().setTeacher(null);
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES + "/2", HttpMethod.PUT, entity,
				String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void testOption() {
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.OPTIONS, null,
				new ParameterizedTypeReference<String>() {
				});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
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

}

