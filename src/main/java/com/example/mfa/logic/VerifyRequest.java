package com.example.mfa.logic;

import lombok.Data;

@Data
public class VerifyRequest {
	// 維護使用者，使用者啟動實在account表格中填入
	private String userSecretKeyInPersistent;

	// 驗證時確認用
	private String verifyCode;
}
