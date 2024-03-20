package fr.aredli.registrix.user.security;

import fr.aredli.registrix.common.ContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class KeycloakUserControllerNoAuthTest extends ContainerTest {
	@Test
	void getAllUsers() {
		ResponseEntity<Void> response = getWithoutAuth("/user", Void.class);
		
		assertEquals(401, response.getStatusCode().value());
	}
	
	@Test
	void getUserById() {
		ResponseEntity<Void> response = getWithoutAuth("/user/" + KEYCLOAK_CONTAINER_ADMIN_USERID, Void.class);
		
		assertEquals(401, response.getStatusCode().value());
	}
	
	@Test
	void getAllAdminUsers() {
		ResponseEntity<Void> response = getWithoutAuth("/user/admin", Void.class);
		
		assertEquals(401, response.getStatusCode().value());
	}
}
