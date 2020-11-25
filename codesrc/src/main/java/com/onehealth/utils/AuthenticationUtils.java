package com.onehealth.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class AuthenticationUtils {

	public static String generateMD5AuthenticationToken(String userId, String requestTimeStamp, String secret) throws NoSuchAlgorithmException {
		String userIdHex=userId.replaceAll("-", "");
		//String userIdUTF=new String(hexStringToByteArray(userIdHex));
		//String secretUTF=new String(hexStringToByteArray(secret));
		String userIdUTF=new String(hexStringToByteArray(userIdHex),StandardCharsets.ISO_8859_1);
		String secretUTF=new String(hexStringToByteArray(secret),StandardCharsets.ISO_8859_1);
		//String authenticationTokenDigest=new String((userIdHex+requestTimeStamp+secret).getBytes(),StandardCharsets.ISO_8859_1);
	 	String authenticationTokenDigest=userIdHex+requestTimeStamp+secret;
	 	System.out.println("authenticationTokenDigest "+authenticationTokenDigest);
	 	MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
		byte[] authenticationTokenBytes=messageDigest.digest((authenticationTokenDigest).getBytes());
		String authenticationToken=bytesToHex(authenticationTokenBytes);
	 	return authenticationToken;
	}
	
	public static byte[] hexStringToByteArray(String hex) {
	    int l = hex.length();
	    byte[] data = new byte[l / 2];
	    for (int i = 0; i < l; i += 2) {
	    	//System.out.println((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
	        data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
	                + Character.digit(hex.charAt(i + 1), 16));
	    }
	    return data;
	}
	
	public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
