package com.jj.util.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class CpfTest {

    private static final List<String> FORMATO_INVALIDO = Arrays.asList("00000000000", "11111111111", "22222222222", "33333333333", "44444444444",
            "55555555555", "66666666666", "77777777777", "88888888888", "99999999999");
    private static final List<String> FORMATO_INVALIDO_DOZE = Arrays.asList("000000000000", "111111111111", "222222222222", "333333333333",
            "444444444444", "555555555555", "666666666666", "777777777777", "888888888888", "999999999999");
    private static final String CPF_MASCARA = "262.487.381-30";
    private static final String CPF_SEM_MASCARA = "26248738130";
    private static final String CPF_INVALIDO = "00845612350";

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<Cpf> constructor = null;
        constructor = Cpf.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testFormatoInvalido() throws Exception {
        int numInvalido = 0;
        for (String valor : FORMATO_INVALIDO) {
            if (!Cpf.validar(valor)) {
                numInvalido++;
            }
        }
        assertEquals(numInvalido, FORMATO_INVALIDO.size());

    }

    @Test
    public void testFormatoInvalidoTamanhoDoze() throws Exception {
        int numInvalido = 0;
        for (String valor : FORMATO_INVALIDO_DOZE) {
            if (!Cpf.validar(valor)) {
                numInvalido++;
            }
        }
        assertEquals(numInvalido, FORMATO_INVALIDO.size());

    }

    @Test
    public void testCpfComMascara() throws Exception {
        assertTrue(Cpf.validar(CPF_MASCARA));

    }

    @Test
    public void testCpfSemMascara() throws Exception {
        assertTrue(Cpf.validar(CPF_SEM_MASCARA));

    }

    @Test
    public void testCpfInvalido() throws Exception {
        assertFalse(Cpf.validar(CPF_INVALIDO));

    }

    @Test
    public void testCpfPrimeiroDigitoVerificadorZero() throws Exception {
        assertTrue(Cpf.validar("172.640.160-05"));
    }

    @Test
    public void testCpfSegundoDigitoVerificadorZero() throws Exception {
        assertTrue(Cpf.validar("142.120.161-50"));
    }
}
