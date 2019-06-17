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

import es.serrapos.pruebatecnica.model.entities.Teacher;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TeacherResourceTests {

	static final String ENDPOINT_TEACHER = "/api/v1/teacher";

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testOptions() {
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_TEACHER, HttpMethod.OPTIONS, null,
				new ParameterizedTypeReference<String>() {
				});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetAll() {
		ResponseEntity<List<Teacher>> response = restTemplate.exchange(ENDPOINT_TEACHER, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Teacher>>() {
				});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		// assertTrue(response.getBody().size() > 0);
	}

	@Test
	public void testGet() {
		ResponseEntity<Teacher> response = restTemplate.exchange(ENDPOINT_TEACHER + "/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Teacher>() {
				});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getId(), Long.valueOf(1));
	}

	@Test
	public void testCreate() {
		HttpEntity<Teacher> entity = new HttpEntity<>(mockTeacher());
		ResponseEntity<Teacher> response = restTemplate.exchange(ENDPOINT_TEACHER, HttpMethod.POST, entity,
				Teacher.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getId());
	}

	@Test
	public void testUpdate() {
		HttpEntity<Teacher> entity = new HttpEntity<>(mockTeacher());
		entity.getBody().setFirstName("Teacher UPDATED");
		ResponseEntity<Teacher> response = restTemplate.exchange(ENDPOINT_TEACHER + "/1", HttpMethod.PUT, entity,
				Teacher.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		assertEquals("Teacher UPDATED", response.getBody().getFirstName());
	}

	@Test
	public void testDelete() {

		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_TEACHER + "/4", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<String>() {
				});

		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		assertEquals("Teacher deleted", response.getBody());

		ResponseEntity<Teacher> responseGet = restTemplate.exchange(ENDPOINT_TEACHER + "/4", HttpMethod.GET, null,
				new ParameterizedTypeReference<Teacher>() {
				});
		assertTrue(responseGet.getStatusCode().equals(HttpStatus.NOT_FOUND));
	}

	private Teacher mockTeacher() {
		Teacher teacher = new Teacher();
		teacher.setFirstName("Nombre");
		teacher.setLastName("Apellidos");
		return teacher;
	}

}
