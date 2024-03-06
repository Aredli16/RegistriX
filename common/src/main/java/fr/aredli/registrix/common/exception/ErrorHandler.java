package fr.aredli.registrix.common.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@Builder
public class ErrorHandler {
	private LocalDate timestamp;
	private String message;
	private String details;
	private HttpStatus status;
	private int statusCode;
}
