package com.jj.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ByteUtilTest {
    private byte[] byteArray;

    @Before
    public void setUp() throws Exception {
        this.byteArray = "CheckGroupBox1    T   CheckGroupBox2  F".getBytes();
    }

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void ConstructorIsPrivateTest() throws Exception {
        Constructor<ByteUtil> constructor = null;
        constructor = ByteUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    // metodo que transforma a chave em um int[]
    private int[] getChave(String chave) {
        byte[] byteChave = chave.getBytes();
        int[] intArray = new int[byteChave.length];
        for (int i = 0; i < byteChave.length; i++) {
            intArray[i] = (byteChave[i] & 0xff);
        }
        return intArray;
    }

    @Test
    public void deveRetornarCheckGroupBox1() {
        assertArrayEquals(ByteUtil.getByteArray(byteArray, 0, 14), getChave("CheckGroupBox1"));
    }

    @Test
    public void deveRetornarCheckGroupBox2() {
        assertArrayEquals(ByteUtil.getByteArray(byteArray, 22, 14), getChave("CheckGroupBox2"));
    }

    @Test
    public void deveRetornarT() {
        assertArrayEquals(ByteUtil.getByteArray(byteArray, 18, 1), getChave("T"));
    }

    @Test
    public void deveRetornarF() {
        assertArrayEquals(ByteUtil.getByteArray(byteArray, byteArray.length - 1, 1), getChave("F"));
    }

    @Test
    public void deveRetornarVazio() {
        assertArrayEquals(ByteUtil.getByteArray(byteArray, 0, 0), getChave(""));
    }

    @Test
    public void testePosicaoValorNegativo() {
        assertArrayEquals(ByteUtil.getByteArray(byteArray, -1, 1), getChave(""));
    }

    @Test
    public void testeTamanhoValorNegativo() {
        assertArrayEquals(ByteUtil.getByteArray(byteArray, 1, -1), getChave(""));
    }

    @Test
    public void testeTamanhoEPosicaoValorNegativo() {
        assertArrayEquals(ByteUtil.getByteArray(byteArray, -1, -1), getChave(""));
    }

    @Test
    public void testeTamanhoEPosicaoZero() {
        assertArrayEquals(ByteUtil.getByteArray(byteArray, 0, 0), getChave(""));
    }

    @Test
    public void testeInicioMaiorQueArray() {
        assertArrayEquals(ByteUtil.getByteArray(byteArray, byteArray.length, 1), getChave(""));
    }

    @Test
    public void deveRetornarTodaString() {
        assertArrayEquals(ByteUtil.getByteArray(byteArray, 0, byteArray.length), getChave("CheckGroupBox1    T   CheckGroupBox2  F"));
    }

}
