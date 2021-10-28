package com.ssurbar.survey.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class LinkUtil {
	public String makeUrl(String surveyId, String type) {
		MessageDigest md;
		StringBuilder sb = new StringBuilder();
		
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(surveyId.getBytes());
			
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
