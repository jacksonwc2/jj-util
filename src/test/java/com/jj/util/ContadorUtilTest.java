package com.jj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ContadorUtilTest {
    ContadorUtil a;

    @Before
    public void setUp() {
        a = new ContadorUtil(LongUtil.ZERO, LongUtil.ZERO);

    }

    @Test
    public void testNext() {
        a = new ContadorUtil();
        for (int i = 0; i < 10; i++) {
            a.next();
        }
        assertNotNull(a);
        assertEquals(a.getValue(), LongUtil.DEZ);
    }

    @Test
    public void testNextDois() {
        a = new ContadorUtil(LongUtil.ZERO, LongUtil.DOIS);
        for (int i = 0; i < 10; i++) {
            a.next();
        }
        assertNotNull(a);
        assertEquals(a.getValue(), LongUtil.VINTE);
    }

    @Test
    public void testBack() {
        a = new ContadorUtil();
        a.setValorInicial(LongUtil.DEZ);
        for (int i = 0; i < 10; i++) {
            a.back();
        }
        assertNotNull(a);
        assertEquals(a.getValue(), LongUtil.ZERO);
    }

    @Test
    public void testBackDois() {
        a = new ContadorUtil(LongUtil.VINTE, LongUtil.DOIS);
        for (int i = 0; i < 10; i++) {
            a.back();
        }
        assertNotNull(a);
        assertEquals(a.getValue(), LongUtil.ZERO);
    }

}
