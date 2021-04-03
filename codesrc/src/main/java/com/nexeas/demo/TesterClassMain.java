package com.nexeas.demo;

import java.security.NoSuchAlgorithmException;

import com.fitlers.utils.AuthenticationUtils;

public class TesterClassMain {

	public static void main(String[] args) throws NoSuchAlgorithmException {
      long time=1603563664959L;
      //System.out.println(Math.round((double)time/1000));
      String authenticationToken=AuthenticationUtils.generateMD5AuthenticationToken("48ffc00a-5047-4ad5-bc05-22714ac3d579", "1605792764143", "4f2d95e8945a2370edf74da47cd18773");
      System.out.println(authenticationToken);
      
        /*MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
        String token="48ffc00a50474ad5bc0522714ac3d5795ea122591d586740888a4f63d7022f8b";
        //String userIdUTF=new String(AuthenticationUtils.hexStringToByteArray(token),StandardCharsets.ISO_8859_1);
		byte[] authenticationTokenBytes=messageDigest.digest(token.getBytes());
		System.out.println(AuthenticationUtils.bytesToHex(authenticationTokenBytes));*/
	}

}
