package com.jj.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptoUtil {

    private static final String UTF_8 = "UTF-8";
    public static final String SECRET_KEY = "jj$#_clpfcc";
    public static final String ALGORITM = "Blowfish";

    private CryptoUtil() {
        // Garante que a classe não seja instanciada.
    }

    public static String criptografar(String conteudo) {
        try {

            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(UTF_8), ALGORITM);

            Cipher cipher = Cipher.getInstance(ALGORITM);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encrypted = cipher.doFinal(conteudo.getBytes(UTF_8));

            return bytesToHex(encrypted);

        } catch (Exception e) {
            return null;
        }
    }

    public static String descriptografar(String conteudo) {
        try {

            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(UTF_8), ALGORITM);

            Cipher cipher = Cipher.getInstance(ALGORITM);

            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decrypted = cipher.doFinal(hexToBytes(conteudo));

            return new String(decrypted);

        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }

    }

    public static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        } else {
            int len = data.length;
            String str = "";
            for (int i = 0; i < len; i++) {
                if ((data[i] & 0xFF) < 16)
                    str = str + "0" + java.lang.Integer.toHexString(data[i] & 0xFF);
                else
                    str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
            }
            return str.toUpperCase();
        }
    }

    public static String criptografarBase64(String conteudo) {
        try {

            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(UTF_8), ALGORITM);

            Cipher cipher = Cipher.getInstance(ALGORITM);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encrypted = cipher.doFinal(conteudo.getBytes(UTF_8));

            return Base64.encodeBase64String(encrypted);

        } catch (Exception e) {
            return null;
        }
    }

    public static String descriptografarBase64(String conteudo) {
        try {

            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(UTF_8), ALGORITM);

            Cipher cipher = Cipher.getInstance(ALGORITM);

            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] a = Base64.decodeBase64(conteudo);

            byte[] decrypted = cipher.doFinal(a);

            return new String(decrypted);
        } catch (Exception e) {
            return null;
        }
    }

}