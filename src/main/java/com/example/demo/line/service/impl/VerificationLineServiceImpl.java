package com.example.demo.line.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.line.service.VerificationLineService;

@Service
public class VerificationLineServiceImpl implements VerificationLineService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean checkFromLine(String requestBody, String X_Line_Signature, String channelSecret) {
		SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
		Mac mac;
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(key);
			byte[] source = requestBody.getBytes("UTF-8");
			String signature = Base64.encodeBase64String(mac.doFinal(source));
			if (signature.equals(X_Line_Signature)) {
				return true;
			}
		} catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

}
