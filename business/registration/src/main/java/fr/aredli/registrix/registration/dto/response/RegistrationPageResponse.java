package fr.aredli.registrix.registration.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegistrationPageResponse {
	private int page;
	private int totalPages;
	private long totalElements;
	private List<RegistrationResponse> registrations;
}
