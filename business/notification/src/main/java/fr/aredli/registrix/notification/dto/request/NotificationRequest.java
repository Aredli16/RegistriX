package fr.aredli.registrix.notification.dto.request;

import fr.aredli.registrix.notification.entity.Notification.Receiver;
import fr.aredli.registrix.notification.entity.Notification.Type;
import lombok.Data;

@Data
public class NotificationRequest {
	private String title;
	private String message;
	private Receiver receiver;
	private Type type;
}
