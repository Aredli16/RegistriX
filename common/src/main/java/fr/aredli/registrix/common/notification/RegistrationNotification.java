package fr.aredli.registrix.common.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationNotification {
	private String registrationId;
	private String registrationUserId;
	private Type type;
	
	public enum Type {
		CREATED, UPDATED, DELETED
	}
}
