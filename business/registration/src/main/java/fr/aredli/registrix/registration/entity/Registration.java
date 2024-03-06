package fr.aredli.registrix.registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class Registration {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String lastName;
	private String firstName;
	private String email;
	private String phone;
	private String streetAddress;
	private String zipCode;
	private String city;
	private String country;
	private String createdBy;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
