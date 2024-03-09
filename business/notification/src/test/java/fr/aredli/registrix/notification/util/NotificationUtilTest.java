package fr.aredli.registrix.notification.util;

import fr.aredli.registrix.notification.entity.Notification;
import fr.aredli.registrix.notification.entity.Notification.Receiver;
import fr.aredli.registrix.notification.repository.NotificationRepository;

public abstract class NotificationUtilTest {
	public static Notification createNotification(NotificationRepository notificationRepository, String userId) {
		Notification notification = new Notification();
		
		notification.setTitle("Test title");
		notification.setMessage("Test content");
		notification.setSender(userId);
		notification.setReceiver(Receiver.USER);
		notification.setType(Notification.Type.EMAIL);
		
		return notificationRepository.save(notification);
	}
}
