package fr.aredli.registrix.registration.security;

import fr.aredli.registrix.common.ContainerTest;
import fr.aredli.registrix.registration.dto.request.RegistrationCreateRequest;
import fr.aredli.registrix.registration.dto.request.RegistrationUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerNoAuthSecurityTest extends ContainerTest {
	protected RegistrationControllerNoAuthSecurityTest() {
		super("registration");
	}
	
	@Test
	void getAll() {
		ResponseEntity<Void> response = getWithoutAuth("/registration", Void.class);
		
		assertEquals(401, response.getStatusCode().value());
	}
	
	@Test
	void getById() {
		ResponseEntity<Void> response = getWithoutAuth("/registration/1", Void.class);
		
		assertEquals(401, response.getStatusCode().value());
	}
	
	@Test
	void create() {
		ResponseEntity<Void> response = postWithoutAuth("/registration", new RegistrationCreateRequest(), Void.class);
		
		assertEquals(401, response.getStatusCode().value());
	}
	
	@Test
	void update() {
		ResponseEntity<Void> response = putWithoutAuth("/registration/1", new RegistrationUpdateRequest(), Void.class);
		
		assertEquals(401, response.getStatusCode().value());
	}
	
	@Test
	void delete() {
		ResponseEntity<Void> response = deleteWithoutAuth("/registration/1", Void.class);
		
		assertEquals(401, response.getStatusCode().value());
	}
}
