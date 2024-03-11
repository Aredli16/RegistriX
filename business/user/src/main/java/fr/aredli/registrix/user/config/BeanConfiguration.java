package fr.aredli.registrix.user.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
	@Value("${keycloak.server-url}")
	private String keycloakServerUrl;
	@Value("${keycloak.admin-realm}")
	private String keycloakRealm;
	@Value("${keycloak.client-id}")
	private String keycloakClientId;
	@Value("${keycloak.client-secret}")
	private String keycloakClientSecret;
	
	@Bean
	public Keycloak keycloak() {
		return KeycloakBuilder.builder()
				.serverUrl("http://keycloak:8000")
				.realm("master")
				.grantType("client_credentials")
				.clientId("admin-cli")
				.clientSecret("dbqgJPVZqt1FGUzhckZIB5hRHEJ2qyrJ")
				.build();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
