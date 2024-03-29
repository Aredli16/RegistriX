package fr.aredli.registrix.common;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public abstract class ContainerTest {
	protected static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:16.2");
	protected static final KeycloakContainer KEYCLOAK_CONTAINER = new KeycloakContainer("quay.io/keycloak/keycloak:23.0");
	protected static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.0"));
	protected static final String KEYCLOAK_CONTAINER_ADMIN_USERID = "1b530987-9b16-4632-9865-cb3cc65fc387";
	protected static final String KEYCLOAK_CONTAINER_ADMIN_USERNAME = "admin";
	protected static final String KEYCLOAK_CONTAINER_ADMIN_PASSWORD = "admin";
	protected static final String KEYCLOAK_CONTAINER_ADMIN_EMAIL = "admin@admin.com";
	protected static final String KEYCLOAK_CONTAINER_USER_USERID = "9a287f3b-49b0-4965-b5f6-e9b437dfc243";
	protected static final String KEYCLOAK_CONTAINER_USER_USERNAME = "user";
	protected static final String KEYCLOAK_CONTAINER_USER_PASSWORD = "user";
	protected static final String KEYCLOAK_CONTAINER_USER_EMAIL = "user@user.com";
	
	static {
		POSTGRE_SQL_CONTAINER
				.withLogConsumer(new Slf4jLogConsumer(log))
				.withUsername("postgres")
				.withPassword("postgres")
				.start();
		
		KEYCLOAK_CONTAINER
				.withLogConsumer(new Slf4jLogConsumer(log))
				.withAdminUsername(KEYCLOAK_CONTAINER_ADMIN_USERNAME)
				.withAdminPassword(KEYCLOAK_CONTAINER_ADMIN_PASSWORD)
				.withRealmImportFile("keycloak/realm.json")
				.withReuse(true)
				.start();
		
		KAFKA_CONTAINER
				.withLogConsumer(new Slf4jLogConsumer(log))
				.withReuse(true)
				.start();
	}
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
		registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
		registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
		registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> KEYCLOAK_CONTAINER.getAuthServerUrl() + "/realms/test");
		registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
		registry.add("keycloak.server-url", KEYCLOAK_CONTAINER::getAuthServerUrl);
		registry.add("keycloak.realm", () -> "test");
	}
	
	protected String getAccessToken(String username, String password) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "password");
		formData.add("client_id", "api");
		formData.add("username", username);
		formData.add("password", password);
		
		ResponseEntity<String> response = restTemplate.postForEntity(KEYCLOAK_CONTAINER.getAuthServerUrl() + "/realms/test/protocol/openid-connect/token", formData, String.class);
		
		return new JacksonJsonParser().parseMap(response.getBody()).get("access_token").toString();
	}
	
	protected <T> ResponseEntity<T> getWithoutAuth(String url, Class<T> responseType) {
		return restTemplate.getForEntity(url, responseType);
	}
	
	protected <T> ResponseEntity<T> getWithUserAuth(String url, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(getAccessToken(KEYCLOAK_CONTAINER_USER_USERNAME, KEYCLOAK_CONTAINER_USER_PASSWORD));
		
		return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
	}
	
	protected <T> ResponseEntity<T> getWithAdminAuth(String url, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(getAccessToken(KEYCLOAK_CONTAINER_ADMIN_USERNAME, KEYCLOAK_CONTAINER_ADMIN_PASSWORD));
		
		return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
	}
	
	protected <T> ResponseEntity<T> postWithoutAuth(String url, Object request, Class<T> responseType) {
		return restTemplate.postForEntity(url, request, responseType);
	}
	
	protected <T> ResponseEntity<T> postWithUserAuth(String url, Object request, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(getAccessToken(KEYCLOAK_CONTAINER_USER_USERNAME, KEYCLOAK_CONTAINER_USER_PASSWORD));
		
		return restTemplate.postForEntity(url, new HttpEntity<>(request, headers), responseType);
	}
	
	protected <T> ResponseEntity<T> postWithAdminAuth(String url, Object request, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(getAccessToken(KEYCLOAK_CONTAINER_ADMIN_USERNAME, KEYCLOAK_CONTAINER_ADMIN_PASSWORD));
		
		return restTemplate.postForEntity(url, new HttpEntity<>(request, headers), responseType);
	}
	
	protected <T> ResponseEntity<T> putWithoutAuth(String url, Object request, Class<T> responseType) {
		return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request), responseType);
	}
	
	protected <T> ResponseEntity<T> putWithUserAuth(String url, Object request, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(getAccessToken(KEYCLOAK_CONTAINER_USER_USERNAME, KEYCLOAK_CONTAINER_USER_PASSWORD));
		
		return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), responseType);
	}
	
	protected <T> ResponseEntity<T> putWithAdminAuth(String url, Object request, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(getAccessToken(KEYCLOAK_CONTAINER_ADMIN_USERNAME, KEYCLOAK_CONTAINER_ADMIN_PASSWORD));
		
		return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), responseType);
	}
	
	protected <T> ResponseEntity<T> deleteWithoutAuth(String url, Class<T> responseType) {
		return restTemplate.exchange(url, HttpMethod.DELETE, null, responseType);
	}
	
	protected <T> ResponseEntity<T> deleteWithUserAuth(String url, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(getAccessToken(KEYCLOAK_CONTAINER_USER_USERNAME, KEYCLOAK_CONTAINER_USER_PASSWORD));
		
		return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), responseType);
	}
	
	protected <T> ResponseEntity<T> deleteWithAdminAuth(String url, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(getAccessToken(KEYCLOAK_CONTAINER_ADMIN_USERNAME, KEYCLOAK_CONTAINER_ADMIN_PASSWORD));
		
		return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), responseType);
	}
}
