package com.hglobal.demo.requestbody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserRequestBody {

	private Integer userId;
	private String emailID;
	private String password;
	private SaveUserProfileRequestBody saveUserProfileRequestBody;
}
