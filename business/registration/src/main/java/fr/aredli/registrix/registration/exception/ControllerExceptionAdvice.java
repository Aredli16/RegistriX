package fr.aredli.registrix.registration.exception;

import fr.aredli.registrix.common.exception.ErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorHandler> handleNoSuchElementException(NoSuchElementException exception) {
		log.error(exception.getMessage(), exception);
		
		ErrorHandler errorHandler = ErrorHandler.builder()
				.timestamp(LocalDate.now())
				.message(exception.getMessage())
				.details("The requested resource was not found.")
				.status(HttpStatus.NOT_FOUND)
				.statusCode(HttpStatus.NOT_FOUND.value())
				.build();
		
		return new ResponseEntity<>(errorHandler, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorHandler> handleAccessDeniedException(AccessDeniedException exception) {
		log.error(exception.getMessage(), exception);
		
		ErrorHandler errorHandler = ErrorHandler.builder()
				.timestamp(LocalDate.now())
				.message(exception.getMessage())
				.details("Access denied.")
				.status(HttpStatus.FORBIDDEN)
				.statusCode(HttpStatus.FORBIDDEN.value())
				.build();
		
		return new ResponseEntity<>(errorHandler, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorHandler> handleException(Exception exception) {
		log.error(exception.getMessage(), exception);
		
		ErrorHandler errorHandler = ErrorHandler.builder()
				.timestamp(LocalDate.now())
				.message(exception.getMessage())
				.details("Internal server error.")
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.build();
		
		return new ResponseEntity<>(errorHandler, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
