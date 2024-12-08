package com.example.mfa.utils;

import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.example.mfa.utils.GoogleAuthenticatorUtil;
import com.google.zxing.WriterException;
@TestMethodOrder(OrderAnnotation.class)
class GoogleAuthenticatorUtilTest {
	private static String secretKey;
	@BeforeEach
	protected void setUp() throws Exception {
		if(secretKey==null) {
			secretKey = GoogleAuthenticatorUtil.createSecretKey();
		}
	}

	@AfterEach
	protected void tearDown() throws Exception {
	}

	
	@Order(1) 
	@Test
	public void testCreateGoogleAuthQRCodeData() throws WriterException, IOException {	 
		String email = "test@gmail.com";
		String companyName = "Awesome Company";
		String authQRCode = GoogleAuthenticatorUtil.createGoogleAuthQRCodeData(secretKey, email, companyName);
		String fileName ="QRCode-%s.png".formatted(secretKey);
		GoogleAuthenticatorUtil.createQRCode(authQRCode, fileName, 400, 400);
	}
	@Order(2) 
	@Test
	public void testVerify() {
		System.out.println(secretKey);
		
//		System.out.print("Please enter 2fA code here -> ");
//		Scanner scanner = new Scanner(System.in);
//		String code = scanner.nextLine();
//		
//		if (GoogleAuthenticatorUtil.verify(secretKey, code)) {
//			System.out.println("Logged in successfully");
//		} else {
//			System.out.println("Invalid 2FA Code");
//		}
	}
}
