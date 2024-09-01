package com.example.passwordmanager.utils;

import org.springframework.security.crypto.codec.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class AesCryptoUtil {
    private static final int IV_LENGTH  = 16;
    private static final String transformation = "AES/CBC/PKCS5Padding";
    private String secret_key;
    private byte[] salt;

    public AesCryptoUtil(String key,String salt){
        this.secret_key = key;
        this.salt = Base64.getDecoder().decode(salt);
    }

    private SecretKeySpec getKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        PBEKeySpec spec = new PBEKeySpec(secret_key.toCharArray(), this.salt, 10000, 256);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return new SecretKeySpec(keyFactory.generateSecret(spec).getEncoded(),"AES");
    }

    public static String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[IV_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private IvParameterSpec generateIv() {
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public String encrypt(String msg) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeySpecException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(transformation);
        IvParameterSpec iv = generateIv();
        cipher.init(cipher.ENCRYPT_MODE,getKey(),iv);
        byte[] encrypted = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(byteConca(iv.getIV(),encrypted));
    }

    public String decrypt(String encryptMsg) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, NoSuchProviderException {
        byte[] msgByte = Base64.getDecoder().decode(encryptMsg);
        IvParameterSpec iv = getIv(byteDetach(msgByte,0));
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(cipher.DECRYPT_MODE,getKey(),iv);
        byte[] decrypted = cipher.doFinal(byteDetach(msgByte,1));
        return new String(decrypted, "UTF-8");
    }

    private IvParameterSpec getIv(byte[] iv){
        return new IvParameterSpec(iv);
    }

    private byte[] byteDetach(byte[] encryptedMsgByte, int mode) throws IOException {
        if(mode != 0 && mode != 1){
            throw new RuntimeException("Invalid mode");
        }
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            switch (mode){
                case 0:
                    outputStream.write(encryptedMsgByte,0,IV_LENGTH);
                    break;
                case 1:
                    outputStream.write(encryptedMsgByte,IV_LENGTH,encryptedMsgByte.length-IV_LENGTH);
                    break;
            }
            return outputStream.toByteArray();
        }
    }

    private byte[] byteConca(byte[] iv, byte[] encryptedMsg) throws IOException {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(iv.length+encryptedMsg.length)) {
            outputStream.write(iv);
            outputStream.write(encryptedMsg);
            return outputStream.toByteArray();
        }
    }

}
