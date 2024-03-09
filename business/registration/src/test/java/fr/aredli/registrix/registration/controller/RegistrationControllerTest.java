package fr.aredli.registrix.registration.controller;

import com.github.javafaker.Faker;
import fr.aredli.registrix.common.ContainerTest;
import fr.aredli.registrix.common.exception.ErrorHandler;
import fr.aredli.registrix.registration.dto.request.RegistrationCreateRequest;
import fr.aredli.registrix.registration.dto.request.RegistrationUpdateRequest;
import fr.aredli.registrix.registration.dto.response.RegistrationPageResponse;
import fr.aredli.registrix.registration.dto.response.RegistrationResponse;
import fr.aredli.registrix.registration.entity.Registration;
import fr.aredli.registrix.registration.repository.RegistrationRepository;
import fr.aredli.registrix.registration.util.RegistrationUtilTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationControllerTest extends ContainerTest {
	@Autowired
	private RegistrationRepository registrationRepository;
	
	protected RegistrationControllerTest() {
		super("registration");
	}
	
	@AfterEach
	void tearDown() {
		registrationRepository.deleteAll();
	}
	
	@Test
	void getAll() {
		Registration firstRegistration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		Registration secondRegistration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		Registration thirdRegistration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		
		ResponseEntity<RegistrationPageResponse> pageResponse = getWithAdminAuth("/registration?page=0&size=10&sortBy=createdAt&sortDirection=asc", RegistrationPageResponse.class);
		
		assertEquals(200, pageResponse.getStatusCode().value());
		assertNotNull(pageResponse.getBody());
		assertEquals(3, pageResponse.getBody().getTotalElements());
		assertEquals(1, pageResponse.getBody().getTotalPages());
		assertNotNull(pageResponse.getBody().getRegistrations());
		assertEquals(3, pageResponse.getBody().getRegistrations().size());
		assertEquals(firstRegistration.getId(), pageResponse.getBody().getRegistrations().getFirst().getId());
		assertEquals(secondRegistration.getId(), pageResponse.getBody().getRegistrations().get(1).getId());
		assertEquals(thirdRegistration.getId(), pageResponse.getBody().getRegistrations().getLast().getId());
	}
	
	@Test
	void getById() {
		Registration registration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		
		ResponseEntity<RegistrationResponse> registrationResponse = getWithAdminAuth("/registration/" + registration.getId(), RegistrationResponse.class);
		
		assertEquals(200, registrationResponse.getStatusCode().value());
		assertNotNull(registrationResponse.getBody());
		assertEquals(registration.getId(), registrationResponse.getBody().getId());
		assertEquals(registration.getLastName(), registrationResponse.getBody().getLastName());
		assertEquals(registration.getFirstName(), registrationResponse.getBody().getFirstName());
		assertEquals(registration.getEmail(), registrationResponse.getBody().getEmail());
		assertEquals(registration.getPhone(), registrationResponse.getBody().getPhone());
		assertEquals(registration.getStreetAddress(), registrationResponse.getBody().getStreetAddress());
		assertEquals(registration.getZipCode(), registrationResponse.getBody().getZipCode());
		assertEquals(registration.getCity(), registrationResponse.getBody().getCity());
		assertEquals(registration.getCountry(), registrationResponse.getBody().getCountry());
		assertEquals(registration.getCreatedBy(), registrationResponse.getBody().getCreatedBy());
		assertEquals(registration.getCreatedAt(), registrationResponse.getBody().getCreatedAt());
		assertEquals(registration.getUpdatedAt(), registrationResponse.getBody().getUpdatedAt());
	}
	
	@Test
	void getByIdNotFound() {
		ResponseEntity<ErrorHandler> response = getWithAdminAuth("/registration/1", ErrorHandler.class);
		
		assertEquals(404, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getTimestamp());
		assertEquals("No value present", response.getBody().getMessage());
		assertEquals("The requested resource was not found.", response.getBody().getDetails());
		assertEquals(404, response.getBody().getStatusCode());
		assertEquals("NOT_FOUND", response.getBody().getStatus().name());
	}
	
	@Test
	void create() {
		RegistrationCreateRequest registrationCreateRequest = new RegistrationCreateRequest();
		Faker faker = new Faker();
		
		registrationCreateRequest.setLastName(faker.name().lastName());
		registrationCreateRequest.setFirstName(faker.name().firstName());
		registrationCreateRequest.setEmail(faker.internet().emailAddress());
		registrationCreateRequest.setPhone(faker.phoneNumber().phoneNumber());
		registrationCreateRequest.setStreetAddress(faker.address().streetAddress());
		registrationCreateRequest.setZipCode(faker.address().zipCode());
		registrationCreateRequest.setCity(faker.address().city());
		registrationCreateRequest.setCountry(faker.address().country());
		
		ResponseEntity<RegistrationResponse> registrationResponse = postWithAdminAuth("/registration", registrationCreateRequest, RegistrationResponse.class);
		
		assertEquals(201, registrationResponse.getStatusCode().value());
		assertNotNull(registrationResponse.getBody());
		assertNotNull(registrationResponse.getBody().getId());
		assertEquals(registrationCreateRequest.getLastName(), registrationResponse.getBody().getLastName());
		assertEquals(registrationCreateRequest.getFirstName(), registrationResponse.getBody().getFirstName());
		assertEquals(registrationCreateRequest.getEmail(), registrationResponse.getBody().getEmail());
		assertEquals(registrationCreateRequest.getPhone(), registrationResponse.getBody().getPhone());
		assertEquals(registrationCreateRequest.getStreetAddress(), registrationResponse.getBody().getStreetAddress());
		assertEquals(registrationCreateRequest.getZipCode(), registrationResponse.getBody().getZipCode());
		assertEquals(registrationCreateRequest.getCity(), registrationResponse.getBody().getCity());
		assertEquals(registrationCreateRequest.getCountry(), registrationResponse.getBody().getCountry());
		assertEquals(KEYCLOAK_CONTAINER_ADMIN_USERID, registrationResponse.getBody().getCreatedBy());
		assertNotNull(registrationResponse.getBody().getCreatedAt());
		assertNotNull(registrationResponse.getBody().getUpdatedAt());
		
		Registration registration = registrationRepository.findById(registrationResponse.getBody().getId()).orElseThrow();
		
		assertEquals(registrationCreateRequest.getLastName(), registration.getLastName());
		assertEquals(registrationCreateRequest.getFirstName(), registration.getFirstName());
		assertEquals(registrationCreateRequest.getEmail(), registration.getEmail());
		assertEquals(registrationCreateRequest.getPhone(), registration.getPhone());
		assertEquals(registrationCreateRequest.getStreetAddress(), registration.getStreetAddress());
		assertEquals(registrationCreateRequest.getZipCode(), registration.getZipCode());
		assertEquals(registrationCreateRequest.getCity(), registration.getCity());
		assertEquals(registrationCreateRequest.getCountry(), registration.getCountry());
		assertEquals(registrationResponse.getBody().getCreatedBy(), registration.getCreatedBy());
		assertEquals(registrationResponse.getBody().getCreatedAt(), registration.getCreatedAt());
		assertEquals(registrationResponse.getBody().getUpdatedAt(), registration.getUpdatedAt());
	}
	
	@Test
	void update() {
		Registration registration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		RegistrationUpdateRequest registrationUpdateRequest = new RegistrationUpdateRequest();
		Faker faker = new Faker();
		
		registrationUpdateRequest.setLastName(faker.name().lastName());
		registrationUpdateRequest.setFirstName(faker.name().firstName());
		registrationUpdateRequest.setEmail(faker.internet().emailAddress());
		registrationUpdateRequest.setPhone(faker.phoneNumber().phoneNumber());
		registrationUpdateRequest.setStreetAddress(faker.address().streetAddress());
		registrationUpdateRequest.setZipCode(faker.address().zipCode());
		registrationUpdateRequest.setCity(faker.address().city());
		registrationUpdateRequest.setCountry(faker.address().country());
		
		ResponseEntity<RegistrationResponse> registrationResponse = putWithAdminAuth("/registration/" + registration.getId(), registrationUpdateRequest, RegistrationResponse.class);
		
		assertEquals(200, registrationResponse.getStatusCode().value());
		assertNotNull(registrationResponse.getBody());
		assertEquals(registration.getId(), registrationResponse.getBody().getId());
		assertEquals(registrationUpdateRequest.getLastName(), registrationResponse.getBody().getLastName());
		assertEquals(registrationUpdateRequest.getFirstName(), registrationResponse.getBody().getFirstName());
		assertEquals(registrationUpdateRequest.getEmail(), registrationResponse.getBody().getEmail());
		assertEquals(registrationUpdateRequest.getPhone(), registrationResponse.getBody().getPhone());
		assertEquals(registrationUpdateRequest.getStreetAddress(), registrationResponse.getBody().getStreetAddress());
		assertEquals(registrationUpdateRequest.getZipCode(), registrationResponse.getBody().getZipCode());
		assertEquals(registrationUpdateRequest.getCity(), registrationResponse.getBody().getCity());
		assertEquals(registrationUpdateRequest.getCountry(), registrationResponse.getBody().getCountry());
		assertEquals(registration.getCreatedBy(), registrationResponse.getBody().getCreatedBy());
		assertEquals(registration.getCreatedAt(), registrationResponse.getBody().getCreatedAt());
		assertNotNull(registrationResponse.getBody().getUpdatedAt());
		
		Registration updatedRegistration = registrationRepository.findById(registrationResponse.getBody().getId()).orElseThrow();
		
		assertEquals(registrationUpdateRequest.getLastName(), updatedRegistration.getLastName());
		assertEquals(registrationUpdateRequest.getFirstName(), updatedRegistration.getFirstName());
		assertEquals(registrationUpdateRequest.getEmail(), updatedRegistration.getEmail());
		assertEquals(registrationUpdateRequest.getPhone(), updatedRegistration.getPhone());
		assertEquals(registrationUpdateRequest.getStreetAddress(), updatedRegistration.getStreetAddress());
		assertEquals(registrationUpdateRequest.getZipCode(), updatedRegistration.getZipCode());
		assertEquals(registrationUpdateRequest.getCity(), updatedRegistration.getCity());
		assertEquals(registrationUpdateRequest.getCountry(), updatedRegistration.getCountry());
		assertEquals(registration.getCreatedBy(), updatedRegistration.getCreatedBy());
		assertEquals(registration.getCreatedAt(), updatedRegistration.getCreatedAt());
		assertEquals(registrationResponse.getBody().getUpdatedAt(), updatedRegistration.getUpdatedAt());
	}
	
	@Test
	void delete() {
		Registration registration = RegistrationUtilTest.createRegistration(registrationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		
		ResponseEntity<Void> registrationResponse = deleteWithAdminAuth("/registration/" + registration.getId(), Void.class);
		
		assertEquals(204, registrationResponse.getStatusCode().value());
		assertEquals(0, registrationRepository.count());
	}
}
