package fr.aredli.registrix.notification.repository;

import fr.aredli.registrix.notification.entity.Notification;
import fr.aredli.registrix.notification.entity.Notification.Receiver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
	Page<Notification> findAllByReceiverOrReceiver(Receiver admin, Receiver admin_and_user, Pageable pageable);
}
