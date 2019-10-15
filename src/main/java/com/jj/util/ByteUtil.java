package com.jj.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteUtil {

    private ByteUtil() {
        // Garante que a classe não seja instanciada.
    }

    public static int[] getByteArray(byte[] byteArray, Integer posicao, Integer tamanho) {
        int[] retorno;

        if (((posicao + tamanho) <= byteArray.length) && (posicao >= 0) && (tamanho > 0)) {
            retorno = new int[tamanho];

            int posicaoFinal = posicao + tamanho;
            int i = 0;

            for (int j = posicao; j < posicaoFinal; j++) {
                retorno[i] = (byteArray[j] & 0xff);
                i++;
            }
        } else {
            retorno = new int[0];
        }
        return retorno;
    }

    // toByteArray and toObject are taken from: http://tinyurl.com/69h8l7x
    public static byte[] toByteArray(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return bytes;
    }

    public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return obj;
    }

    public static String toString(byte[] bytes) {
        return new String(bytes);
    }
}