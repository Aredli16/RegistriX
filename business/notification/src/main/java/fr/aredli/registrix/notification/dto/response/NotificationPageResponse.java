package fr.aredli.registrix.notification.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotificationPageResponse {
	private int page;
	private int totalPages;
	private long totalElements;
	private List<NotificationResponse> notifications;
}
