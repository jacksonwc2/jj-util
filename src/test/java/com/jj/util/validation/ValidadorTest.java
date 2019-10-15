package com.jj.util.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.jj.util.BigDecimalUtil;
import com.jj.util.IntegerUtil;
import com.jj.util.LongUtil;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ValidadorTest {

    private static final String CPF_MASCARA = "262.487.381-30";
    private static final String CPF_SEM_MASCARA = "26248738130";
    private static final String CPF_INVALIDO = "00845612350";
    private static final String CNPJ_VALIDO = "04.366.171/0001-90";
    private static final String CNPJ_INVALIDO = "04.111.111/0001-90";
    private static final String CNPJ_SEM_MASCARA = "81624457000179";

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<Validador> constructor = null;
        constructor = Validador.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void validaCpfValidoComMascaraTest() {
        assertTrue(Validador.validaCpf(CPF_MASCARA));
    }

    @Test
    public void validaCpfValidoSemMascaraTest() {
        assertTrue(Validador.validaCpf(CPF_SEM_MASCARA));
    }

    @Test
    public void validaCpfInvalidoTest() {
        assertFalse(Validador.validaCpf(CPF_INVALIDO));
    }

    @Test
    public void validaCnpjValidoComMascaraTest() {
        assertTrue(Validador.validaCnpj(CNPJ_VALIDO));
    }

    @Test
    public void validaCnpjValidoSemMascaraTest() {
        assertTrue(Validador.validaCnpj(CNPJ_SEM_MASCARA));
    }

    @Test
    public void validaCnpjInvalidoTest() {
        assertFalse(Validador.validaCnpj(CNPJ_INVALIDO));
    }

    @Test
    public void isNumberTrueStringTest() {
        assertTrue(Validador.isNumber("2"));
    }

    @Test
    public void isNumberTrueLongTest() {
        assertTrue(Validador.isNumber(LongUtil.UM));
    }

    @Test
    public void isNumberTrueBigdecimalTest() {
        assertTrue(Validador.isNumber(BigDecimalUtil.UM));
    }

    @Test
    public void isNumberTrueIntegerTest() {
        assertTrue(Validador.isNumber(IntegerUtil.UM));
    }

    @Test
    public void isNumberFalseStringTest() {
        assertFalse(Validador.isNumber("teste"));
    }

    @Test
    public void isNumberFalseDateTest() {
        Date data = new Date();
        assertFalse(Validador.isNumber(data));
    }

    @Test
    public void isNumberFalseBooleanTrueTest() {
        assertFalse(Validador.isNumber(Boolean.TRUE));
    }

    @Test
    public void isNumberFalseBooleanFalseTest() {
        assertFalse(Validador.isNumber(Boolean.FALSE));
    }

    @Test
    public void isNumberNullTest() {
        assertFalse(Validador.isNumber(null));
    }

    @Test
    public void isNumberEmptyStringTest() {
        assertFalse(Validador.isNumber(""));
    }

    @Test
    public void isNumberEmptyArrayTest() {
        ArrayList<String> array = new ArrayList<>();
        assertFalse(Validador.isNumber(array));
    }
}
