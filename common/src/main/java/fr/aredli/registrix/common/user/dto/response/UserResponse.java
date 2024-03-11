package fr.aredli.registrix.common.user.dto.response;

import lombok.Data;

@Data
public class UserResponse {
	protected String id;
	protected String username;
	protected String firstName;
	protected String lastName;
	protected String email;
}
