package fr.aredli.registrix.registration.dto.request;

import lombok.Data;

@Data
public class RegistrationCreateRequest {
	private String lastName;
	private String firstName;
	private String email;
	private String phone;
	private String streetAddress;
	private String zipCode;
	private String city;
	private String country;
}
