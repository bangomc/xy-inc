package com.xyinc.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaEncoder {

	public static String criptografar(String senha) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(senha);
	}

}
