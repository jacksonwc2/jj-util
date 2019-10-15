package com.jj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class StringUtilTest {

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void ConstructorIsPrivateTest() throws Exception {
        Constructor<StringUtil> constructor = null;
        constructor = StringUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testIsVazio() {
        assertFalse(StringUtil.isVazio("Casa"));
        assertTrue(StringUtil.isVazio(null));
        assertTrue(StringUtil.isVazio(""));
    }

    @Test
    public void testRemoveCaracteresInvalidos() {
        StringBuilder a = new StringBuilder("Ca\"\'sa");
        StringBuilder b = new StringBuilder();
        assertEquals(StringUtil.removeCaracteresInvalidos(a).toString(), "Casa");
        assertEquals(StringUtil.removeCaracteresInvalidos(b).toString(), "");
    }

    @Test
    public void testRemoveEspacos() {
        assertTrue(StringUtil.removeEspacos(" ").equals(""));
        assertTrue(StringUtil.isNullOrEmpty(StringUtil.removeEspacos(null)));
        assertTrue(StringUtil.removeEspacos("Ca SA").equals("Ca SA"));
        assertTrue(StringUtil.removeEspacos(" CASA ").equals("CASA"));
    }

    @Test
    public void testTransformaMininusculo() {
        assertTrue(StringUtil.transformaMininusculo("").equals(""));
        assertTrue(StringUtil.transformaMininusculo(null).equals(""));
        assertTrue(StringUtil.transformaMininusculo("Ca SA").equals("ca sa"));
        assertTrue(StringUtil.transformaMininusculo("CASA ").equals("casa"));
    }

    @Test
    public void testContaCaracteres() {
        assertTrue(StringUtil.contaCaracteres("").equals(0));
        assertTrue(StringUtil.contaCaracteres(null).equals(0));
        assertTrue(StringUtil.contaCaracteres("Ca sa").equals(5));
        assertTrue(StringUtil.contaCaracteres("Casa ").equals(4));
    }

    @Test
    public void testTratarNulo() {
        assertTrue(StringUtil.tratarNulo("Valor").equals("Valor"));
        assertTrue(StringUtil.tratarNulo(null).equals(""));
        assertTrue(StringUtil.tratarNulo("").equals(""));
    }

    @Test
    public void testIsNumericList() {
        assertTrue(StringUtil.isNumericList("13,313,13"));
        assertFalse(StringUtil.isNumericList("dqq,dw2,231"));
        assertFalse(StringUtil.isNumericList("454"));
        assertFalse(StringUtil.isNumericList("454123dwdqw"));
    }

    @Test
    public void isNumericTest() {
        assertFalse(StringUtil.isNumeric("13,313,13"));
        assertTrue(StringUtil.isNumeric("-8949"));
        assertTrue(StringUtil.isNumeric("454"));
        assertFalse(StringUtil.isNumeric("454123dwdqw"));
    }

    @Test
    public void testListToString() {
        List<String> a = new ArrayList<>();
        a.add("c");
        a.add("b");
        a.add("a");
        List<String> b = new ArrayList<>();
        b.add("ACB");
        List<String> c = new ArrayList<>();
        c.add("54");
        c.add("32");
        assertEquals(StringUtil.listToString(a), "c, b, a");
        assertEquals(StringUtil.listToString(b), "ACB");
        assertEquals(StringUtil.listToString(c), "54, 32");
    }

    @Test
    public void testPrimeiraMaiscula() {
        assertTrue(StringUtil.primeiraMaiscula("casa").equals("Casa"));
        assertTrue(StringUtil.primeiraMaiscula("Casa").equals("Casa"));
        assertTrue(StringUtil.primeiraMaiscula("2Casa").equals("2Casa"));
    }

    @Test
    public void testPrimeiraMinuscula() {
        assertTrue(StringUtil.primeiraMinuscula("casa").equals("casa"));
        assertTrue(StringUtil.primeiraMinuscula("Casa").equals("casa"));
        assertTrue(StringUtil.primeiraMinuscula("2asa").equals("2asa"));
    }

    @Test
    public void testIsNullOrEmpty() {
        assertFalse(StringUtil.isNullOrEmpty("casa"));
        assertTrue(StringUtil.isNullOrEmpty(""));
        assertTrue(StringUtil.isNullOrEmpty(null));
    }

    @Test
    public void testTruncate() {
        assertTrue(StringUtil.truncate("casa", 5).equals("casa"));
        assertTrue(StringUtil.truncate("Casa", 3).equals("Cas"));
        assertTrue(StringUtil.truncate("2asa", 1).equals("2"));
        assertTrue(StringUtil.isNullOrEmpty(StringUtil.truncate(null, 1)));
    }

    @Test
    public void testToCaractersList() {
        List<String> a = new ArrayList<>();
        List<String> b = new ArrayList<>();
        a.add("a");
        a.add("b");
        a.add("c");
        a.add("d");
        a.add("2");
        b.add("");
        assertTrue(StringUtil.toCaractersList("abcd2").equals(a));
        assertTrue(StringUtil.toCaractersList("").equals(b));
    }

    @Test
    public void testToList() {
        List<String> a = new ArrayList<>();
        a.add("ABCD2");
        List<String> b = new ArrayList<>();
        b.add("");
        assertTrue(StringUtil.toList("[abcd2]").equals(a));
        assertTrue(StringUtil.toList("[]").equals(b));
    }

    @Test
    public void testListS1toList() {
        List<String> a = new ArrayList<>();
        a.add("ABCD2");
        List<String> b = new ArrayList<>();
        b.add("");
        assertTrue(StringUtil.listS1toList("<abcd<2>").equals(a));
        assertTrue(StringUtil.listS1toList("<><><").equals(b));
    }

    @Test
    public void testPadLeft() {
        assertTrue(StringUtil.padLeft("Casa", 9).equals("00000Casa"));
        assertTrue(StringUtil.padLeft("Casa", 3).equals("Casa"));
        assertTrue(StringUtil.padLeft("   ", 9).equals("000000   "));
        assertTrue(StringUtil.padLeft("", 0).equals(""));
    }

    @Test
    public void testPrimeiraLetraMaiusculo() {
        assertTrue(StringUtil.primeiraLetraMaiusculo("casa").equals("Casa"));
        assertTrue(StringUtil.primeiraLetraMaiusculo("CASA").equals("Casa"));
    }

    @Test
    public void testTrim() {
        assertTrue(StringUtil.trim(" casa ").equals("casa"));
        assertTrue(StringUtil.trim(" ").equals(""));
        assertTrue(StringUtil.trim(null).equals(""));
    }

    @Test
    public void testReplaceNaoNumeros() {
        assertTrue(StringUtil.replaceNaoNumeros("Ca12sa").equals("12"));
        assertTrue(StringUtil.replaceNaoNumeros("2 1").equals("21"));
        assertTrue(StringUtil.replaceNaoNumeros("").equals(""));
    }

    @Test
    public void testformataMoeda() {
        assertTrue(StringUtil.formataMoeda(BigDecimalUtil.CEM, 2, true).equals("R$ 100,00"));
        assertTrue(StringUtil.formataMoeda(BigDecimalUtil.CEM, 4, true).equals("R$ 100,0000"));
        assertTrue(StringUtil.formataMoeda(BigDecimalUtil.CEM, 2, false).equals("100,00"));
    }

    @Test
    public void testIsNumeric() {
        assertFalse(StringUtil.isNumeric(BigDecimal.class, "12321d"));
        assertTrue(StringUtil.isNumeric(BigDecimal.class, "1233"));
        assertFalse(StringUtil.isNumeric(BigDecimal.class, ""));
        assertFalse(StringUtil.isNumeric(BigDecimal.class, null));
    }

    @Test
    public void testFormataMoeda() {
        assertTrue(StringUtil.formataMoeda(BigDecimalUtil.CEM).equals("100,00"));
    }

    @Test
    public void testQuebrarArrayCodigos() {
        List<String> a = new ArrayList<>();
        a.add("caSa");
        a.add("Casa");
        assertTrue(StringUtil.quebrarArrayCodigos(String.class, "<caSa>,C<asa").equals(a));
    }
}
