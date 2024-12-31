package org.example.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.thingsboard.server.common.data.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {
	private String id;
	private String tenantId;
	private String customerId;
	private String email;
	private String authority;
	private String firstName;
	private String lastName;
	private String phone;
	private LocalDateTime createdTime;

	public static UserResponse toUserResponse(User user) {
		return UserResponse.builder()
				.id(user.getId().toString())
				.tenantId(user.getTenantId().toString())
				.customerId(user.getCustomerId().toString())
				.email(user.getEmail())
				.authority(user.getAuthority().name())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.phone(user.getPhone())
//				.createdTime(LocalDateTime.ofEpochSecond(user.getCreatedTime(), 0, null))
				.build();
	}
}
