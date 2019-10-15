package com.jj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ConversorMd5Test {

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void ConstructorIsPrivateTest() throws Exception {
        Constructor<ConversorMd5> constructor = null;
        constructor = ConversorMd5.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void detectarMD5Test() {
        assertTrue(ConversorMd5.detectarMD5("8277e0910d750195b448797616e091ad"));
    }

    @Test
    public void detectarMD5VazioTest() {
        assertFalse(ConversorMd5.detectarMD5(""));
    }

    @Test
    public void converterStringParaMd5Test() {
        assertEquals(ConversorMd5.converterStringParaMd5("d"), "8277e0910d750195b448797616e091ad");
    }

    @Test
    public void converterStringParaMd5VazioTest() {
        assertNull(ConversorMd5.converterStringParaMd5(""));
    }
}
