package com.hglobal.demo.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class Utility {

	public static String encryptPassword(String password) {
		return new Base64().encodeAsString(password.getBytes());
	}
	
	public static LocalDate covertDatetoDBFormat(String date, String format) {
		if (date == null) {
			return null;
		}
		if (format.equalsIgnoreCase(Constants.UI_TIME_FORMAT)) {
			return LocalDate.parse(date, DateTimeFormatter.ofPattern(Constants.UI_TIME_FORMAT));
		} else {
			return null;
		}
	}

}
