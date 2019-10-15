package com.jj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class BigDecimalUtilTest {

    @Test
    public void igualVerdade() {

        assertTrue(BigDecimalUtil.igual(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    public void igualFalso() {

        assertFalse(BigDecimalUtil.igual(BigDecimal.ZERO, BigDecimal.ONE));
    }

    @Test
    public void diferenteVerdade() {

        assertTrue(BigDecimalUtil.diferente(BigDecimal.ZERO, BigDecimal.ONE));
    }

    @Test
    public void diferenteFalso() {

        assertFalse(BigDecimalUtil.diferente(BigDecimal.ZERO, BigDecimal.ZERO));
    }

    @Test
    public void maiorOuIgualVerdade() {
        assertTrue(BigDecimalUtil.maiorOuIgual(BigDecimal.TEN, BigDecimal.ONE));
    }

    @Test
    public void maiorOuIgualFalso() {
        assertFalse(BigDecimalUtil.maiorOuIgual(BigDecimal.ONE, BigDecimal.TEN));
    }

    @Test
    public void maiorOuIgualTrueIgualTest() {
        assertTrue(BigDecimalUtil.maiorOuIgual(BigDecimal.TEN, BigDecimal.TEN));
    }

    @Test
    public void menorOuIgualVerdade() {
        assertTrue(BigDecimalUtil.menorOuIgual(BigDecimal.ZERO, BigDecimal.ONE));
    }

    @Test
    public void menorOuIgualTrueIgualTest() {
        assertTrue(BigDecimalUtil.menorOuIgual(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    public void menorOuIgualFalse() {
        assertFalse(BigDecimalUtil.menorOuIgual(BigDecimal.TEN, BigDecimal.ONE));
    }

    @Test
    public void maiorVerdade() {
        assertTrue(BigDecimalUtil.maior(BigDecimal.TEN, BigDecimal.ONE));
    }

    @Test
    public void maiorFalse() {
        assertFalse(BigDecimalUtil.maior(BigDecimal.ZERO, BigDecimal.ONE));
    }

    @Test
    public void menorVerdade() {
        assertTrue(BigDecimalUtil.menor(BigDecimal.ZERO, BigDecimal.ONE));
    }

    @Test
    public void menorFalse() {
        assertFalse(BigDecimalUtil.menor(BigDecimal.TEN, BigDecimal.ONE));
    }

    @Test
    public void divide() {
        assertTrue(BigDecimalUtil.igual(BigDecimalUtil.divide(new BigDecimal(LongUtil.CINCO), new BigDecimal(LongUtil.QUINZE)),
                new BigDecimal(0.33, java.math.MathContext.DECIMAL64).setScale(2)));
    }

    @Test
    public void divide2() {
        assertTrue(BigDecimalUtil.igual(BigDecimalUtil.divide(new BigDecimal(LongUtil.SETE), new BigDecimal(LongUtil.TRES)),
                new BigDecimal(2.33, java.math.MathContext.DECIMAL64).setScale(2)));
    }

    @Test
    public void divide3() {
        assertTrue(BigDecimalUtil.igual(BigDecimalUtil.divide(new BigDecimal(LongUtil.TREZE), new BigDecimal(LongUtil.SETE)),
                new BigDecimal(1.86, java.math.MathContext.DECIMAL64).setScale(2)));
    }

    @Test
    public void divide4() {
        assertFalse(BigDecimalUtil.igual(BigDecimalUtil.divide(BigDecimalUtil.ZERO, BigDecimalUtil.DOIS),
                new BigDecimal(1.86, java.math.MathContext.DECIMAL64).setScale(2)));
    }

    @Test
    public void deveDividirSemArredondar() {
        assertTrue(BigDecimalUtil.igual(
                BigDecimalUtil.divide(new BigDecimal(10904.56), new BigDecimal(100434.58), IntegerUtil.SEIS)
                        .multiply(new BigDecimal(100), java.math.MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_EVEN),
                new BigDecimal(10.86).setScale(2, RoundingMode.HALF_EVEN)));
    }

    @Test
    public void maiorQueZeroTrueMaiorTest() {
        assertTrue(BigDecimalUtil.maiorQueZero(BigDecimalUtil.UM));
    }

    @Test
    public void maiorQueZeroFalseNullTest() {
        assertFalse(BigDecimalUtil.maiorQueZero(null));
    }

    @Test
    public void isNullOrZeroTrueNullTest() {
        assertTrue(BigDecimalUtil.isNullOrZero(null));
    }

    @Test
    public void isNullOrZeroTrueZeroTest() {
        assertTrue(BigDecimalUtil.isNullOrZero(BigDecimalUtil.ZERO));
    }

    @Test
    public void isNullOrZeroFalsePositivoTest() {
        assertFalse(BigDecimalUtil.isNullOrZero(BigDecimalUtil.CINCO));
    }

    @Test
    public void isNullOrZeroFalseNegativoTest() {
        assertFalse(BigDecimalUtil.isNullOrZero(BigDecimalUtil.CENTO_E_TRINTA_NEGATIVO));
    }

    @Test
    public void roundS1Test() {
        assertEquals(BigDecimalUtil.roundS1(BigDecimalUtil.DOIS), BigDecimalUtil.DOIS);
    }

    @Test
    public void betweenTrueTest() {
        assertTrue(BigDecimalUtil.valor(BigDecimalUtil.CEM).between(BigDecimalUtil.ZERO, BigDecimalUtil.CENTO_E_VINTE));
    }

    @Test
    public void betweenTrueIgualValor2Test() {
        assertTrue(BigDecimalUtil.valor(BigDecimalUtil.CEM).between(BigDecimalUtil.ZERO, BigDecimalUtil.CEM));
    }

    @Test
    public void betweenTrueIgualValor1Test() {
        assertTrue(BigDecimalUtil.valor(BigDecimalUtil.DOIS).between(BigDecimalUtil.DOIS, BigDecimalUtil.CEM));
    }

    @Test
    public void betweenFalseMaiorTest() {
        assertFalse(BigDecimalUtil.valor(BigDecimalUtil.CINCO).between(BigDecimalUtil.ZERO, BigDecimalUtil.DOIS));
    }

    @Test
    public void betweenFalseMenorTest() {
        assertFalse(BigDecimalUtil.valor(BigDecimalUtil.UM).between(BigDecimalUtil.DOIS, BigDecimalUtil.TRINTA));
    }

    @Test
    public void setScaleTest() {
        assertEquals(BigDecimalUtil.setScale(BigDecimalUtil.DOIS), BigDecimalUtil.DOIS.setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    public void valueOfStringTest() {
        assertEquals(BigDecimalUtil.valueOf("2"), BigDecimalUtil.DOIS.setScale(1));
    }

    @Test
    public void valueOfStringVaziaTest() {
        assertNull(BigDecimalUtil.valueOf(""));
    }

    @Test
    public void valueOfDoubleTest() {
        assertEquals(BigDecimalUtil.valueOf(DoubleUtil.CEM), BigDecimalUtil.CEM.setScale(2));
    }

    @Test
    public void divideLongBigDecimalTest() {
        assertEquals(BigDecimalUtil.divide(LongUtil.CINCO, BigDecimalUtil.CINCO), BigDecimalUtil.UM.setScale(2));
    }

    @Test
    public void divideBigDecimalLongTest() {
        assertEquals(BigDecimalUtil.divide(BigDecimalUtil.CINCO, LongUtil.CINCO), BigDecimalUtil.UM.setScale(2));
    }

    @Test
    public void divideDownTest() {
        assertEquals(BigDecimalUtil.divideDown(BigDecimalUtil.DOIS, BigDecimalUtil.UM), BigDecimalUtil.DOIS.setScale(2));
    }

    @Test
    public void menorQueZeroTrueTest() {
        assertTrue(BigDecimalUtil.menorQueZero(BigDecimalUtil.CENTO_E_TRINTA_NEGATIVO));
    }

    @Test
    public void menorQueZeroFalseNullTest() {
        assertFalse(BigDecimalUtil.menorQueZero(null));
    }

    @Test
    public void menorQueZeroFalsePositivoTest() {
        assertFalse(BigDecimalUtil.menorQueZero(BigDecimalUtil.DOIS));
    }

    @Test
    public void menorQueZeroFalseZeroTest() {
        assertFalse(BigDecimalUtil.menorQueZero(BigDecimalUtil.ZERO));
    }

    @Test
    public void percentageEscalaTest() {
        assertEquals(BigDecimalUtil.percentage(BigDecimalUtil.UM, BigDecimalUtil.UM, 1), BigDecimalUtil.ZERO.setScale(1));
    }

    @Test
    public void percentagePctZeroTest() {
        assertEquals(BigDecimalUtil.percentage(BigDecimalUtil.UM, BigDecimalUtil.ZERO, 1), BigDecimalUtil.ZERO);
    }

    @Test
    public void percentageTest() {
        assertEquals(BigDecimalUtil.percentage(BigDecimalUtil.VINTE, BigDecimalUtil.DEZ), BigDecimalUtil.DOIS.setScale(2));
    }

    @Test
    public void valorEntreTrueMaiorInicioTest() {
        assertTrue(BigDecimalUtil.valorEntre(BigDecimalUtil.CINCO, BigDecimalUtil.UM, BigDecimalUtil.QUARENTA));
    }

    @Test
    public void valorEntreTrueIgualInicioTest() {
        assertTrue(BigDecimalUtil.valorEntre(BigDecimalUtil.UM, BigDecimalUtil.UM, BigDecimalUtil.QUARENTA));
    }

    @Test
    public void valorEntreTrueMenorFimTest() {
        assertTrue(BigDecimalUtil.valorEntre(BigDecimalUtil.TRINTA, BigDecimalUtil.UM, BigDecimalUtil.QUARENTA));
    }

    @Test
    public void valorEntreTrueIgualFimTest() {
        assertTrue(BigDecimalUtil.valorEntre(BigDecimalUtil.QUARENTA, BigDecimalUtil.UM, BigDecimalUtil.QUARENTA));
    }

    @Test
    public void valorEntreFalseValorInicioMenorTest() {
        assertFalse(BigDecimalUtil.valorEntre(BigDecimalUtil.DOIS, BigDecimalUtil.CINCO, BigDecimalUtil.DEZ));
    }

    @Test
    public void valorEntreFalseValorFimMaiorTest() {
        assertFalse(BigDecimalUtil.valorEntre(BigDecimalUtil.VINTE, BigDecimalUtil.CINCO, BigDecimalUtil.DEZ));
    }

    @Test
    public void menosPositivoTest() {
        assertEquals(BigDecimalUtil.menos(BigDecimalUtil.DEZ, BigDecimalUtil.CINCO), BigDecimalUtil.CINCO);
    }

    @Test
    public void menosNegativoTest() {
        assertEquals(BigDecimalUtil.menos(BigDecimalUtil.CINCO, BigDecimalUtil.DEZ), BigDecimalUtil.CINCO.negate());
    }

    @Test
    public void menosZeroTest() {
        assertEquals(BigDecimalUtil.menos(BigDecimalUtil.CINCO, BigDecimalUtil.CINCO), BigDecimalUtil.ZERO);
    }

    @Test
    public void menosPositivoEscalaTest() {
        assertEquals(BigDecimalUtil.menos(BigDecimalUtil.DEZ, BigDecimalUtil.CINCO, 2), BigDecimalUtil.CINCO);
    }

    @Test
    public void menosNegativoEscalaTest() {
        assertEquals(BigDecimalUtil.menos(BigDecimalUtil.CINCO, BigDecimalUtil.DEZ, 2), BigDecimalUtil.CINCO.negate());
    }

    @Test
    public void menosIgualEscalaTest() {
        assertEquals(BigDecimalUtil.menos(BigDecimalUtil.DOIS, BigDecimalUtil.DOIS, 2), BigDecimalUtil.ZERO);
    }
}
