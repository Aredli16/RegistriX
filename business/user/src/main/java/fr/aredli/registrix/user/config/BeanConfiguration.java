package fr.aredli.registrix.user.config;

import org.keycloak.OAuth2Constants;
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
	@Value("${keycloak.admin-username}")
	private String keycloakAdminUsername;
	@Value("${keycloak.admin-password}")
	private String keycloakAdminPassword;
	
	@Bean
	public Keycloak keycloak() {
		return KeycloakBuilder.builder()
				.serverUrl(keycloakServerUrl)
				.realm(keycloakRealm)
				.grantType(OAuth2Constants.PASSWORD)
				.clientId(keycloakClientId)
				.username(keycloakAdminUsername)
				.password(keycloakAdminPassword)
				.build();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
