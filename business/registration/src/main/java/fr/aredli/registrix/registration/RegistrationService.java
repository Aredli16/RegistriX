package fr.aredli.registrix.registration;

import fr.aredli.registrix.registration.dto.request.RegistrationCreateRequest;
import fr.aredli.registrix.registration.dto.request.RegistrationUpdateRequest;
import fr.aredli.registrix.registration.dto.response.RegistrationPageResponse;
import fr.aredli.registrix.registration.dto.response.RegistrationResponse;
import fr.aredli.registrix.registration.entity.Registration;
import fr.aredli.registrix.registration.mapper.RegistrationMapper;
import fr.aredli.registrix.registration.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
	private final RegistrationRepository repository;
	
	public RegistrationPageResponse findAll(int page, int size, String sortBy, String sortDirection) {
		Page<Registration> registrations = repository.findAll(PageRequest.of(page, size).withSort(Sort.by(Direction.fromString(sortDirection), sortBy)));
		
		return RegistrationPageResponse
				.builder()
				.page(registrations.getNumber())
				.totalPages(registrations.getTotalPages())
				.totalElements(registrations.getTotalElements())
				.registrations(registrations.getContent().stream().map(RegistrationMapper::mapEntityToDTO).toList())
				.build();
	}
	
	public RegistrationResponse findById(String id) {
		return RegistrationMapper.mapEntityToDTO(repository.findById(id).orElseThrow());
	}
	
	public RegistrationResponse create(RegistrationCreateRequest request) {
		Registration registration = RegistrationMapper.mapDTOToEntity(request);
		
		registration.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		
		return RegistrationMapper.mapEntityToDTO(repository.save(registration));
	}
	
	public RegistrationResponse update(String id, RegistrationUpdateRequest request) {
		Registration registration = repository.findById(id).orElseThrow();
		
		RegistrationMapper.mapDTOToEntity(registration, request);
		
		return RegistrationMapper.mapEntityToDTO(repository.save(registration));
	}
	
	public void delete(String id) {
		repository.deleteById(id);
	}
	
	public boolean isOwner(String registrationId, String userId) {
		try {
			return findById(registrationId).getCreatedBy().equals(userId);
		} catch (Exception e) {
			return false;
		}
	}
}
