package fr.aredli.registrix.user.controller;

import fr.aredli.registrix.common.user.dto.response.UserResponse;
import fr.aredli.registrix.user.service.KeycloakUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class KeycloakUserController {
	private final KeycloakUserService keycloakUserService;
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return ResponseEntity.ok(keycloakUserService.findAllUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
		return ResponseEntity.ok(keycloakUserService.findUserById(id));
	}
	
	@GetMapping("/admin")
	public ResponseEntity<List<UserResponse>> getAllAdminUsers() {
		return ResponseEntity.ok(keycloakUserService.findAllAdminUsers());
	}
}
