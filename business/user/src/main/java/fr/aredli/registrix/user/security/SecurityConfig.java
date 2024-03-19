package fr.aredli.registrix.user.security;

import fr.aredli.registrix.common.security.KeycloakSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		
		http.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers("/actuator/health").permitAll();
			authorize.anyRequest().hasRole("ADMIN");
		});
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(KeycloakSecurityConfig.jwtAuthenticationConverter())));
		
		return http.build();
	}
}
