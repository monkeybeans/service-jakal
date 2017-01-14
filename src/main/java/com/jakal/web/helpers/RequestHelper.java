package com.jakal.web.helpers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
	static public String scrambleIp(HttpServletRequest request) {
		try {
			byte[] ip = request.getRemoteAddr().getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD2");
			
			byte[] ipReduced = Arrays.copyOfRange(md.digest(ip), 0, 12); 
			return new String(ipReduced);
		} catch (NoSuchAlgorithmException e) {
			
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return null;
	}

}
