package com.jj.util;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class Base256UtilTest {

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void ConstructorIsPrivateTest() throws Exception {
        Constructor<Base256Util> constructor = null;
        constructor = Base256Util.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void byte0DeveRetornar0() {
        int[] byteArray = { 0 };
        assertTrue("Deve retornar 0", (Base256Util.toInteger(byteArray).equals(IntegerUtil.ZERO)));
    }

    @Test
    public void byte1DeveRetornar256() {
        int[] byteArray = { 1 };
        assertTrue("Deve retornar 256", (Base256Util.toInteger(byteArray).equals(256)));
    }

    @Test
    public void byte0e1DeveRetornar1() {
        int[] byteArray = { 0, 1 };
        assertTrue("Deve retornar 1", (Base256Util.toInteger(byteArray).equals(IntegerUtil.UM)));
    }

    @Test
    public void byte1e0DeveRetornar65536() {
        int[] byteArray = { 1, 0 };

        assertTrue("Deve retornar 65536", (Base256Util.toInteger(byteArray).equals(65536)));
    }

    @Test
    public void byte0e1e9DeveRetornar256() {
        int[] byteArray = { 0, 1, 9 };
        assertTrue("Deve retornar 265", (Base256Util.toInteger(byteArray).equals(256)));
    }

    @Test
    public void byte42DeveRetornar10752() {
        int[] byteArray = { 42 };
        assertTrue("Deve retornar 10752", (Base256Util.toInteger(byteArray).equals(10752)));
    }

}
