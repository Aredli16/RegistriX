package fr.aredli.registrix.user.service;

import fr.aredli.registrix.common.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {
	private final Keycloak keycloak;
	private final ModelMapper modelMapper;
	@Value("${keycloak.realm}")
	private String realm;
	
	public List<UserResponse> findAllUsers() {
		return keycloak.realm(realm).users().list().stream()
				.map(userRepresentation -> modelMapper.map(userRepresentation, UserResponse.class))
				.toList();
	}
	
	public UserResponse findUserById(String id) {
		return modelMapper.map(keycloak.realm(realm).users().get(id).toRepresentation(), UserResponse.class);
	}
	
	public List<UserResponse> findAllAdminUsers() {
		return keycloak.realm(realm).roles().get("admin").getUserMembers().stream()
				.map(userRepresentation -> modelMapper.map(userRepresentation, UserResponse.class))
				.toList();
	}
}
