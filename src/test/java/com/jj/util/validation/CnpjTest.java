package com.jj.util.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class CnpjTest {

    private static final String CNPJ_VALIDO = "04.366.171/0001-90";
    private static final String CNPJ_INVALIDO = "04.111.111/0001-90";
    private static final String CNPJ_COM_MASCARA = "81.624.457/0001-79";
    private static final String CNPJ_SEM_MASCARA = "81624457000179";

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<Cnpj> constructor = null;
        constructor = Cnpj.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testCnpjValido() throws Exception {
        assertTrue(Cnpj.validar(CNPJ_VALIDO));
    }

    @Test
    public void testCnpjInvalido() throws Exception {
        assertFalse(Cnpj.validar(CNPJ_INVALIDO));
    }

    @Test
    public void testCnpjMenosQuatorze() throws Exception {
        assertFalse(Cnpj.validar("04.111.111/0001-9"));
    }

    @Test
    public void testCnpjComMascara() throws Exception {
        assertTrue(Cnpj.validar(CNPJ_COM_MASCARA));
    }

    @Test
    public void testCnpjSemMascara() throws Exception {
        assertTrue(Cnpj.validar(CNPJ_SEM_MASCARA));
    }

    @Test
    public void testCnpjValidoPrimeiroDigitoVerificadorZero() throws Exception {
        assertTrue(Cnpj.validar("13.324.016/0001-00"));
    }

}
