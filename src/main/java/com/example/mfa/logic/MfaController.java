package com.example.mfa.logic;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mfa.utils.GoogleAuthenticatorUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "MfaController", description = "示範MFA")
@RestController
@RequestMapping("/calculate")
public class MfaController {
	@Operation(description = "產生使用者專屬的SecretKey資訊")
	@GetMapping("/userSecretKeyInPersistent") 
	public ResponseEntity<String> getSecretKeyInPersistent( ) {
		String userSecretKeyInPersistent = GoogleAuthenticatorUtil.createSecretKey();
	    return ResponseEntity.ok(userSecretKeyInPersistent);
	}
	
	
	@Operation(description = "取得Google Auth QRCode資訊")
	@PostMapping("/googleAuthQRCode") 
	public ResponseEntity<QRCode> getGoogleAuthQRCode( @RequestBody UserInfo currentUser) {
		String userSecretKeyInPersistent = currentUser.getUserSecretKeyInPersistent();
	    String authQRCode = GoogleAuthenticatorUtil.createGoogleAuthQRCodeData( userSecretKeyInPersistent , currentUser.getName(), currentUser.getIssuer());
	    QRCode result = QRCode.builder()
	    		.authQRCode(authQRCode)
	    		.qrCodeImgBase64(GoogleAuthenticatorUtil.createQRCode(authQRCode, 400, 400).get())
	    		.build();
	    return ResponseEntity.ok( result);
	}
	
	
	@Operation(description = "驗證Google Auth")
	@PostMapping("/googleAuthQRCode/{verifyCode}") 
	public ResponseEntity<Boolean> checkGoogleAuthQRCode(@RequestBody VerifyRequest currentUser) {
		String verifyCode = currentUser.getVerifyCode();
	    boolean checkGoogleAuthQRCode = false;
	   
	    if(StringUtils.isNotBlank(currentUser.getUserSecretKeyInPersistent())) {
	        checkGoogleAuthQRCode = GoogleAuthenticatorUtil.verify(currentUser.getUserSecretKeyInPersistent(), verifyCode);
	    }
	    return ResponseEntity.ok(checkGoogleAuthQRCode);
	}
}
