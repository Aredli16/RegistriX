package fr.aredli.registrix.notification.dto.response;

import fr.aredli.registrix.notification.entity.Notification.Receiver;
import fr.aredli.registrix.notification.entity.Notification.Status;
import fr.aredli.registrix.notification.entity.Notification.Type;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
	private String id;
	private String title;
	private String message;
	private String sender;
	private Receiver receiver;
	private Type type;
	private Status status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
