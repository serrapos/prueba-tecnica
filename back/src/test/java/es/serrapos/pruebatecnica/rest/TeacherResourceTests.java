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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import es.serrapos.pruebatecnica.model.entities.Teacher;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TeacherResourceTests {

	static final String ENDPOINT_COURSES = "/api/v1/teacher";

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
		ResponseEntity<List<Teacher>> response = restTemplate.exchange(ENDPOINT_COURSES, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Teacher>>() {
				});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		// assertTrue(response.getBody().size() > 0);
	}

	@Test
	public void testGet() {
		ResponseEntity<Teacher> response = restTemplate.exchange(ENDPOINT_COURSES + "/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Teacher>() {
				});
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getId(), Long.valueOf(1));
	}

}
