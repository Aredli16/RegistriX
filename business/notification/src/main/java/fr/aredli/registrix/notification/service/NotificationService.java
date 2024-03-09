package fr.aredli.registrix.notification.service;

import fr.aredli.registrix.common.notification.RegistrationNotification;
import fr.aredli.registrix.notification.dto.response.NotificationPageResponse;
import fr.aredli.registrix.notification.dto.response.NotificationResponse;
import fr.aredli.registrix.notification.entity.Notification;
import fr.aredli.registrix.notification.entity.Notification.Receiver;
import fr.aredli.registrix.notification.entity.Notification.Status;
import fr.aredli.registrix.notification.entity.Notification.Type;
import fr.aredli.registrix.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final NotificationRepository notificationRepository;
	private final ModelMapper modelMapper;
	private final MailService mailService;
	
	public NotificationPageResponse findAll(int page, int size, String sortBy, String sortDirection) {
		Page<Notification> notifications = notificationRepository.findAll(PageRequest.of(page, size).withSort(Sort.by(Direction.fromString(sortDirection), sortBy)));
		
		return NotificationPageResponse
				.builder()
				.page(notifications.getNumber())
				.totalPages(notifications.getTotalPages())
				.totalElements(notifications.getTotalElements())
				.notifications(notifications.getContent().stream().map((element) -> modelMapper.map(element, NotificationResponse.class)).collect(Collectors.toList()))
				.build();
	}
	
	public NotificationResponse findById(String id) {
		return modelMapper.map(notificationRepository.findById(id).orElseThrow(), NotificationResponse.class);
	}
	
	public void create(RegistrationNotification request) {
		Notification notification = new Notification();
		
		switch (request.getType()) {
			case CREATED:
				notification.setTitle("Created Registration");
				notification.setMessage("A new registration has been created");
				break;
			case UPDATED:
				notification.setTitle("Updated Registration");
				notification.setMessage("A registration has been updated");
				break;
			case DELETED:
				notification.setTitle("Deleted Registration");
				notification.setMessage("A registration has been deleted");
				break;
		}
		
		notification.setSender(request.getRegistrationUserId());
		notification.setReceiver(Receiver.USER_AND_ADMIN);
		notification.setType(Type.EMAIL_AND_PUSH);
		
		mailService.sendMail("test@gmail.com", notification.getTitle(), notification.getMessage());
		
		notificationRepository.save(notification);
	}
	
	public NotificationResponse read(String id) {
		Notification notification = notificationRepository.findById(id).orElseThrow();
		
		notification.setStatus(Status.READ);
		
		return modelMapper.map(notificationRepository.save(modelMapper.map(notification, Notification.class)), NotificationResponse.class);
	}
}
