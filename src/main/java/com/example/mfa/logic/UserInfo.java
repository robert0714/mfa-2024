package com.example.mfa.logic;

import lombok.Data;

@Data
public class UserInfo {
	private String name;
	private String issuer;
	
	//維護使用者，使用者啟動實在account表格中填入
	private String userSecretKeyInPersistent;
	 
}
