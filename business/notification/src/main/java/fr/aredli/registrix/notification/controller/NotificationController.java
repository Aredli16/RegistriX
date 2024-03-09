package fr.aredli.registrix.notification.controller;

import fr.aredli.registrix.common.notification.RegistrationNotification;
import fr.aredli.registrix.notification.dto.response.NotificationPageResponse;
import fr.aredli.registrix.notification.dto.response.NotificationResponse;
import fr.aredli.registrix.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;
	
	@GetMapping
	public ResponseEntity<NotificationPageResponse> getAllNotifications(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "20") int size,
			@RequestParam(required = false, defaultValue = "updatedAt") String sortBy,
			@RequestParam(required = false, defaultValue = "desc") String sortDirection
	) {
		return ResponseEntity.ok(notificationService.findAll(page, size, sortBy, sortDirection));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable String id) {
		return ResponseEntity.ok(notificationService.findById(id));
	}
	
	@PostMapping("/read/{id}")
	public ResponseEntity<NotificationResponse> readNotification(@PathVariable String id) {
		return ResponseEntity.ok(notificationService.read(id));
	}
	
	@KafkaListener(topics = "registration", groupId = "notification")
	public void listenRegistrationNotification(RegistrationNotification notification) {
		notificationService.create(notification);
	}
}
