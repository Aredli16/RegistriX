package fr.aredli.registrix.notification.controller;

import fr.aredli.registrix.common.ContainerTest;
import fr.aredli.registrix.notification.dto.response.NotificationPageResponse;
import fr.aredli.registrix.notification.dto.response.NotificationResponse;
import fr.aredli.registrix.notification.entity.Notification;
import fr.aredli.registrix.notification.entity.Notification.Status;
import fr.aredli.registrix.notification.repository.NotificationRepository;
import fr.aredli.registrix.notification.util.NotificationUtilTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class NotificationControllerTest extends ContainerTest {
	@Autowired
	private NotificationRepository notificationRepository;
	
	protected NotificationControllerTest() {
		super("notification");
	}
	
	@BeforeEach
	void setUp() {
		notificationRepository.deleteAll();
	}
	
	@Test
	void getAllNotifications() {
		Notification firstNotification = NotificationUtilTest.createNotification(notificationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		Notification secondNotification = NotificationUtilTest.createNotification(notificationRepository, KEYCLOAK_CONTAINER_USER_USERID);
		Notification thirdNotification = NotificationUtilTest.createNotification(notificationRepository, KEYCLOAK_CONTAINER_USER_USERID);
		
		ResponseEntity<NotificationPageResponse> response = getWithAdminAuth("/notification?page=0&size=10&sortBy=createdAt&sortDirection=asc", NotificationPageResponse.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(3, response.getBody().getTotalElements());
		assertEquals(firstNotification.getId(), response.getBody().getNotifications().getFirst().getId());
		assertEquals(secondNotification.getId(), response.getBody().getNotifications().get(1).getId());
		assertEquals(thirdNotification.getId(), response.getBody().getNotifications().getLast().getId());
	}
	
	@Test
	void getNotificationById() {
		Notification notification = NotificationUtilTest.createNotification(notificationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		
		ResponseEntity<NotificationResponse> response = getWithAdminAuth("/notification/" + notification.getId(), NotificationResponse.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(notification.getId(), response.getBody().getId());
		assertEquals(notification.getTitle(), response.getBody().getTitle());
		assertEquals(notification.getMessage(), response.getBody().getMessage());
		assertEquals(notification.getSender(), response.getBody().getSender());
		assertEquals(notification.getReceiver(), response.getBody().getReceiver());
		assertEquals(notification.getType(), response.getBody().getType());
		assertEquals(notification.getStatus(), response.getBody().getStatus());
		assertEquals(notification.getCreatedAt(), response.getBody().getCreatedAt());
		assertEquals(notification.getUpdatedAt(), response.getBody().getUpdatedAt());
	}
	
	@Test
	void readNotification() {
		Notification notification = NotificationUtilTest.createNotification(notificationRepository, KEYCLOAK_CONTAINER_ADMIN_USERID);
		
		ResponseEntity<NotificationResponse> response = postWithAdminAuth("/notification/read/" + notification.getId(), null, NotificationResponse.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(Status.READ, response.getBody().getStatus());
	}
}
