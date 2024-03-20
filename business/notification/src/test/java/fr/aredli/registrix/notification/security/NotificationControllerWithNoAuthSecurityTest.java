package fr.aredli.registrix.notification.security;

import fr.aredli.registrix.common.ContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class NotificationControllerWithNoAuthSecurityTest extends ContainerTest {
	@Test
	void getAllNotifications() {
		ResponseEntity<Void> response = getWithoutAuth("/notification", Void.class);
		
		assertEquals(401, response.getStatusCode().value());
	}
	
	@Test
	void getNotificationById() {
		ResponseEntity<Void> response = getWithoutAuth("/notification/1", Void.class);
		
		assertEquals(401, response.getStatusCode().value());
	}
	
	@Test
	void readNotification() {
		ResponseEntity<Void> response = postWithoutAuth("/notification/read/1", null, Void.class);
		
		assertEquals(401, response.getStatusCode().value());
	}
}
