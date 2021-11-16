package com.transaction.demo;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;

import org.springframework.cglib.beans.BeanCopier.Generator;

public class EncryptMethod {
	
	private SecretKey key;
	private int KEY_SIZE = 128;
    private Cipher encryptionCipher;
	
	public void init()  throws Exception{
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(KEY_SIZE);
		key = generator.generateKey();
	}
	
	public String encrypt(String message) throws Exception {
        byte[] messageInBytes = message.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }
	
	private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
	
}
