package com.jj.util;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ThreadUtilTest {

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void ConstructorIsPrivateTest() throws Exception {
        Constructor<ThreadUtil> constructor = null;
        constructor = ThreadUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
