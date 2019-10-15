package com.jj.util.criptografia;

import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class BlowFish {
    private static Logger logger = Logger.getLogger(BlowFish.class.getName());
    private static final String BLOWFISH = "Blowfish";
    public static final String CHAVE_CRIPTOGRAFIA_RESET = "jj_4@5#Ka#&67";

    private BlowFish() {
    }

    public static String decrypt(byte[] hash, byte[] chave) {
        logger.info("==>Executando método decrypt.");
        try {
            SecretKeySpec key = new SecretKeySpec(chave, BLOWFISH);

            Cipher cipher = Cipher.getInstance(BLOWFISH);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(hash);

            return new String(decrypted);
        } catch (Exception e) {
            logger.info("==>Erro ao executar criptografia usando BlowFish: " + e.getMessage());
            return null;
        }
    }

}