package fr.aredli.registrix.registration.controller;

import fr.aredli.registrix.registration.dto.request.RegistrationCreateRequest;
import fr.aredli.registrix.registration.dto.request.RegistrationUpdateRequest;
import fr.aredli.registrix.registration.dto.response.RegistrationPageResponse;
import fr.aredli.registrix.registration.dto.response.RegistrationResponse;
import fr.aredli.registrix.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
	private final RegistrationService registrationService;
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RegistrationPageResponse> getAll(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "20") int size,
			@RequestParam(required = false, defaultValue = "updatedAt") String sortBy,
			@RequestParam(required = false, defaultValue = "desc") String sortDirection
	) {
		return ResponseEntity.ok(registrationService.findAll(page, size, sortBy, sortDirection));
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @registrationService.isOwner(#id, authentication.name)")
	public ResponseEntity<RegistrationResponse> getById(@PathVariable String id) {
		return ResponseEntity.ok(registrationService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<RegistrationResponse> create(@RequestBody RegistrationCreateRequest registration) {
		return new ResponseEntity<>(registrationService.create(registration), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @registrationService.isOwner(#id, authentication.name)")
	public ResponseEntity<RegistrationResponse> update(@PathVariable String id, @RequestBody RegistrationUpdateRequest registration) {
		return ResponseEntity.ok(registrationService.update(id, registration));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @registrationService.isOwner(#id, authentication.name)")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		registrationService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
