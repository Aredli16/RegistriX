package fr.aredli.registrix.registration.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistrationResponse {
	private String id;
	private String lastName;
	private String firstName;
	private String email;
	private String phone;
	private String streetAddress;
	private String zipCode;
	private String city;
	private String country;
	private String createdBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
