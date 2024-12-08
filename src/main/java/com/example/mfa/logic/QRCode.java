package com.example.mfa.logic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QRCode {
	private String authQRCode;
	private String qrCodeImgBase64;
}
