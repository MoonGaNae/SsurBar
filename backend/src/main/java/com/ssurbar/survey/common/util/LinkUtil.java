package com.ssurbar.survey.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class LinkUtil {
	public String makeUrl(String surveyId, String type) {
		MessageDigest md;
		StringBuilder sb = new StringBuilder();

		String str = surveyId+type;
		
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());
			
			byte[] bytes = md.digest();
			
			for (byte b : bytes) {
				sb.append(String.format("%02x", b));
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
