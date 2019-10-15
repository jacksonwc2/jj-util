package com.jj.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class IntegerUtilTest {

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void ConstructorIsPrivateTest() throws Exception {
        Constructor<IntegerUtil> constructor = null;
        constructor = IntegerUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void isNullOrZeroTrueZeroTest() {
        assertTrue(IntegerUtil.isNullOrZero(IntegerUtil.ZERO));
    }

    @Test
    public void isNullOrZeroTrueNullTest() {
        assertTrue(IntegerUtil.isNullOrZero(null));
    }

    @Test
    public void isNullOrZeroFalseUmTest() {
        assertFalse(IntegerUtil.isNullOrZero(IntegerUtil.UM));
    }

    @Test
    public void isNullOrZeroFalseNegativoTest() {
        assertFalse(IntegerUtil.isNullOrZero(IntegerUtil.UM_NEGATIVO));
    }

    @Test
    public void isNullOrLessOrEqualsZeroNullTest() {
        assertTrue(IntegerUtil.isNullOrLessOrEqualsZero(null));
    }

    @Test
    public void isNullOrLessOrEqualsZeroLessTest() {
        assertTrue(IntegerUtil.isNullOrLessOrEqualsZero(-LongUtil.UM));
    }

    @Test
    public void isNullOrLessOrEqualsZeroZeroTest() {
        assertTrue(IntegerUtil.isNullOrLessOrEqualsZero(LongUtil.ZERO));
    }

    @Test
    public void isNullOrLessOrEqualsZeroFalseTest() {
        assertFalse(IntegerUtil.isNullOrLessOrEqualsZero(LongUtil.UM));
    }

}
