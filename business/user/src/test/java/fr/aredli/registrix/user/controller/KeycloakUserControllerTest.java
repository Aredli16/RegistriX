package fr.aredli.registrix.user.controller;

import fr.aredli.registrix.common.ContainerTest;
import fr.aredli.registrix.common.user.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class KeycloakUserControllerTest extends ContainerTest {
	@Test
	void getAllUsers() {
		ResponseEntity<UserResponse[]> response = getWithAdminAuth("/user", UserResponse[].class);
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().length);
		assertEquals(KEYCLOAK_CONTAINER_ADMIN_USERID, response.getBody()[0].getId());
		assertEquals(KEYCLOAK_CONTAINER_USER_USERID, response.getBody()[1].getId());
	}
	
	@Test
	void getUserById() {
		ResponseEntity<UserResponse> response = getWithAdminAuth("/user/" + KEYCLOAK_CONTAINER_ADMIN_USERID, UserResponse.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(KEYCLOAK_CONTAINER_ADMIN_USERID, response.getBody().getId());
		assertEquals(KEYCLOAK_CONTAINER_ADMIN_USERNAME, response.getBody().getUsername());
		assertEquals(KEYCLOAK_CONTAINER_ADMIN_EMAIL, response.getBody().getEmail());
	}
	
	@Test
	void getAllAdminUsers() {
		ResponseEntity<UserResponse[]> response = getWithAdminAuth("/user/admin", UserResponse[].class);
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(1, response.getBody().length);
		assertEquals(KEYCLOAK_CONTAINER_ADMIN_USERID, response.getBody()[0].getId());
	}
}
