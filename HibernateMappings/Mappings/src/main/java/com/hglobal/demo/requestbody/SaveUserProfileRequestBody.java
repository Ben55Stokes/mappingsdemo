package com.hglobal.demo.requestbody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserProfileRequestBody {

	private Integer userProfileId;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String gender;
}
