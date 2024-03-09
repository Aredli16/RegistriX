package fr.aredli.registrix.registration.security;

import fr.aredli.registrix.common.ContainerTest;
import fr.aredli.registrix.common.exception.ErrorHandler;
import fr.aredli.registrix.registration.dto.request.RegistrationCreateRequest;
import fr.aredli.registrix.registration.dto.request.RegistrationUpdateRequest;
import fr.aredli.registrix.registration.dto.response.RegistrationResponse;
import fr.aredli.registrix.registration.entity.Registration;
import fr.aredli.registrix.registration.repository.RegistrationRepository;
import fr.aredli.registrix.registration.util.RegistrationUtilTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerUserAuthSecurityTest extends ContainerTest {
	@Autowired
	private RegistrationRepository registrationRepository;
	
	protected RegistrationControllerUserAuthSecurityTest() {
		super("registration");
	}
	
	@Test
	void getAll() {
		ResponseEntity<ErrorHandler> response = getWithUserAuth("/registration", ErrorHandler.class);
		
		assertEquals(403, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals("Access Denied", response.getBody().getMessage());
		assertEquals("Access denied.", response.getBody().getDetails());
		assertEquals(HttpStatus.FORBIDDEN, response.getBody().getStatus());
		assertEquals(403, response.getBody().getStatusCode());
	}
	
	@Test
	void getByIdNoOwnerRegistration() {
		Registration registration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		
		ResponseEntity<ErrorHandler> response = getWithUserAuth("/registration/" + registration.getId(), ErrorHandler.class);
		
		assertEquals(403, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals("Access Denied", response.getBody().getMessage());
		assertEquals("Access denied.", response.getBody().getDetails());
		assertEquals(HttpStatus.FORBIDDEN, response.getBody().getStatus());
		assertEquals(403, response.getBody().getStatusCode());
	}
	
	@Test
	void getByIdOwnerRegistration() {
		Registration registration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_USER_USERID);
		
		ResponseEntity<RegistrationResponse> response = getWithUserAuth("/registration/" + registration.getId(), RegistrationResponse.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
	}
	
	@Test
	void create() {
		ResponseEntity<RegistrationResponse> response = postWithUserAuth("/registration", new RegistrationCreateRequest(), RegistrationResponse.class);
		
		assertEquals(201, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(KEYCLOAK_CONTAINER_USER_USERID, response.getBody().getCreatedBy());
	}
	
	@Test
	void updateNoOwnerRegistration() {
		Registration registration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		
		ResponseEntity<ErrorHandler> response = putWithUserAuth("/registration/" + registration.getId(), new RegistrationUpdateRequest(), ErrorHandler.class);
		
		assertEquals(403, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals("Access Denied", response.getBody().getMessage());
		assertEquals("Access denied.", response.getBody().getDetails());
		assertEquals(HttpStatus.FORBIDDEN, response.getBody().getStatus());
		assertEquals(403, response.getBody().getStatusCode());
	}
	
	@Test
	void updateOwnerRegistration() {
		Registration registration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_USER_USERID);
		
		ResponseEntity<RegistrationResponse> response = putWithUserAuth("/registration/" + registration.getId(), new RegistrationUpdateRequest(), RegistrationResponse.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
	}
	
	@Test
	void deleteNoOwnerRegistration() {
		Registration registration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		
		ResponseEntity<ErrorHandler> response = deleteWithUserAuth("/registration/" + registration.getId(), ErrorHandler.class);
		
		assertEquals(403, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals("Access Denied", response.getBody().getMessage());
		assertEquals("Access denied.", response.getBody().getDetails());
		assertEquals(HttpStatus.FORBIDDEN, response.getBody().getStatus());
		assertEquals(403, response.getBody().getStatusCode());
	}
	
	@Test
	void deleteOwnerRegistration() {
		Registration registration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_USER_USERID);
		
		ResponseEntity<Void> response = deleteWithUserAuth("/registration/" + registration.getId(), Void.class);
		
		assertEquals(204, response.getStatusCode().value());
	}
}
