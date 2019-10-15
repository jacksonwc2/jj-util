package com.jj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class LongUtilTest {

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void ConstructorIsPrivateTest() throws Exception {
        Constructor<LongUtil> constructor = null;
        constructor = LongUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void isNullOrZeroNullTest() {
        assertTrue(LongUtil.isNullOrZero(null));
    }

    @Test
    public void isNullOrZeroZeroTest() {
        assertTrue(LongUtil.isNullOrZero(LongUtil.ZERO));
    }

    @Test
    public void isNullOrZeroNegativoTest() {
        assertFalse(LongUtil.isNullOrZero(-LongUtil.UM));
    }

    @Test
    public void isNullOrZeroPositivoTest() {
        assertFalse(LongUtil.isNullOrZero(LongUtil.UM));
    }

    @Test
    public void isNullTrueTest() {
        assertTrue(LongUtil.isNull(null));
    }

    @Test
    public void isNullFalseTest() {
        assertFalse(LongUtil.isNull(LongUtil.UM));
    }

    @Test
    public void isNullOrLessOrEqualsZeroNullTest() {
        assertTrue(LongUtil.isNullOrLessOrEqualsZero(null));
    }

    @Test
    public void isNullOrLessOrEqualsZeroLessTest() {
        assertTrue(LongUtil.isNullOrLessOrEqualsZero(-LongUtil.UM));
    }

    @Test
    public void isNullOrLessOrEqualsZeroZeroTest() {
        assertTrue(LongUtil.isNullOrLessOrEqualsZero(LongUtil.ZERO));
    }

    @Test
    public void isNullOrLessOrEqualsZeroFalseTest() {
        assertFalse(LongUtil.isNullOrLessOrEqualsZero(LongUtil.UM));
    }

    @Test
    public void isNumberFalseTest() {
        assertFalse(LongUtil.isNumber(""));
    }

    @Test
    public void isNumberNotNumberTest() {
        assertFalse(LongUtil.isNumber("teste"));
    }

    @Test
    public void isNumberTrueTest() {
        assertTrue(LongUtil.isNumber("2"));
    }

    @Test
    public void asLongTest() {
        List<String> lista = new ArrayList<>();
        lista.add("23");
        List<Long> listaReturn = new ArrayList<>();
        listaReturn.add(LongUtil.VINTE_TRES);

        assertEquals(LongUtil.asLong(lista), listaReturn);
    }

    @Test
    public void asLongVaziaNullTest() {
        List<String> lista = new ArrayList<>();
        lista.add("~~");

        assertEquals(LongUtil.asLong(lista), null);
    }

    @Test
    public void getNumberTest() {
        String string = "1ddd";

        assertEquals(LongUtil.getNumber(string), LongUtil.UM);
    }

    @Test
    public void getNumberNullTest() {

        assertEquals(LongUtil.getNumber(null), null);
    }

    @Test
    public void getNumberVazioTest() {

        assertEquals(LongUtil.getNumber("dasdas"), null);
    }

}
