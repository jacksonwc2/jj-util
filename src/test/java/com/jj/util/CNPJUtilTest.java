package com.jj.util;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class CNPJUtilTest {

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void ConstructorIsPrivateTest() throws Exception {
        Constructor<CNPJUtil> constructor = null;
        constructor = CNPJUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testFormatar() {
        assertTrue(CNPJUtil.formatar("23274641000130").equals("23.274.641/0001-30"));
        assertTrue(CNPJUtil.formatar("23.274.641/0001-30").equals("23.274.641/0001-30"));
        assertTrue(CNPJUtil.formatar("12345678912345").equals("12.345.678/9123-45"));
        assertTrue(CNPJUtil.formatar("1234567891").equals("00.001.234/5678-91"));
    }

}
