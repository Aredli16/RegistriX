package fr.aredli.registrix.registration.mapper;

import fr.aredli.registrix.registration.dto.request.RegistrationCreateRequest;
import fr.aredli.registrix.registration.dto.request.RegistrationUpdateRequest;
import fr.aredli.registrix.registration.dto.response.RegistrationResponse;
import fr.aredli.registrix.registration.entity.Registration;
import org.modelmapper.ModelMapper;

public abstract class RegistrationMapper {
	private static final ModelMapper mapper = new ModelMapper();
	
	public static RegistrationResponse mapEntityToDTO(Registration entity) {
		return mapper.map(entity, RegistrationResponse.class);
	}
	
	public static Registration mapDTOToEntity(RegistrationCreateRequest request) {
		return mapper.map(request, Registration.class);
	}
	
	public static void mapDTOToEntity(Registration registration, RegistrationUpdateRequest request) {
		mapper.map(request, registration);
	}
}
