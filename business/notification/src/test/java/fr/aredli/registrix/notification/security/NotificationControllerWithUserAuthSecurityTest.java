package fr.aredli.registrix.notification.security;

import fr.aredli.registrix.common.ContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class NotificationControllerWithUserAuthSecurityTest extends ContainerTest {
	protected NotificationControllerWithUserAuthSecurityTest() {
		super("notification");
	}
	
	@Test
	void getAllNotifications() {
		ResponseEntity<Void> response = getWithUserAuth("/notification", Void.class);
		
		assertEquals(403, response.getStatusCode().value());
	}
	
	@Test
	void getNotificationById() {
		ResponseEntity<Void> response = getWithUserAuth("/notification/1", Void.class);
		
		assertEquals(403, response.getStatusCode().value());
	}
	
	@Test
	void readNotification() {
		ResponseEntity<Void> response = postWithUserAuth("/notification/read/1", null, Void.class);
		
		assertEquals(403, response.getStatusCode().value());
	}
}
