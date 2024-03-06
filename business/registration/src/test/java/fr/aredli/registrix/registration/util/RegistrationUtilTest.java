package fr.aredli.registrix.registration.util;

import com.github.javafaker.Faker;
import fr.aredli.registrix.registration.entity.Registration;
import fr.aredli.registrix.registration.repository.RegistrationRepository;

public abstract class RegistrationUtilTest {
	public static Registration createRegistration(RegistrationRepository registrationRepository, String userId) {
		Faker faker = new Faker();
		Registration registration = new Registration();
		
		registration.setLastName(faker.name().lastName());
		registration.setFirstName(faker.name().firstName());
		registration.setEmail(faker.internet().emailAddress());
		registration.setPhone(faker.phoneNumber().phoneNumber());
		registration.setStreetAddress(faker.address().streetAddress());
		registration.setZipCode(faker.address().zipCode());
		registration.setCity(faker.address().city());
		registration.setCountry(faker.address().country());
		registration.setCreatedBy(userId);
		
		return registrationRepository.save(registration);
	}
}
