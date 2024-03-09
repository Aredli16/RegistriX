package fr.aredli.registrix.notification.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@ToString
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(nullable = false)
	private String title;
	private String message;
	@Column(nullable = false)
	private String sender;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Receiver receiver;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status = Status.UNREAD;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public enum Receiver {
		USER, ADMIN, USER_AND_ADMIN
	}
	
	public enum Type {
		EMAIL, PUSH, EMAIL_AND_PUSH
	}
	
	public enum Status {
		READ, UNREAD
	}
}
