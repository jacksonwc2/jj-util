package com.jj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class MapValueTest {

    private MapValue<String, Long> table;

    @Before
    public void setUp() throws Exception {
        table = new MapValue<String, Long>();
    }

    @Test
    public void testePopularHashTable() {

        table.put("valor1", LongUtil.UM);
        table.put("valor2", LongUtil.DOIS);
        table.put("valor1", LongUtil.TRES);

        assertEquals(table.get(0).getKey(), "valor1");
        assertEquals(table.getValue("valor2"), LongUtil.DOIS);
    }

    @Test
    public void testePopularHashTableUniqueKey() {

        table.setUniqueKey(Boolean.TRUE);

        table.put("valor1", LongUtil.UM);
        table.put("valor2", LongUtil.DOIS);
        table.put("valor3", LongUtil.TRES);

        assertEquals(table.get(0).getKey(), "valor1");
        assertEquals(table.getValue("valor2"), LongUtil.DOIS);
    }

    @Test
    public void testePopularHashTableFalhaUniqueKey() {

        table.setUniqueKey(Boolean.TRUE);

        table.put("valor1", LongUtil.UM);
        table.put("valor1", LongUtil.DOIS);
        table.put("valor3", LongUtil.TRES);

        assertEquals(table.getValue("valor1"), LongUtil.UM);
        assertEquals(table.getItens().size(), 2);
    }

    @Test
    public void testeGetUniqueKeyTrue() {
        table.setUniqueKey(Boolean.TRUE);
        assertEquals(table.getUniqueKey(), Boolean.TRUE);
    }

    @Test
    public void testeGetUniqueKeyFalse() {
        assertEquals(table.getUniqueKey(), Boolean.FALSE);
    }

    @Test
    public void testeRemoveItem() {

        table.put("valor1", LongUtil.UM);
        table.put("valor2", LongUtil.DOIS);
        table.put("valor3", LongUtil.TRES);

        table.remove(0);
        assertEquals(table.getItens().size(), 2);
    }

    @Test
    public void testeSort() {

        table.put("valor2", LongUtil.DOIS);
        table.put("valor1", LongUtil.UM);
        table.put("valor3", LongUtil.TRES);

        table.sort();

        assertEquals(table.getItens().get(0).getKey(), "valor1");
        assertEquals(table.getItens().get(1).getKey(), "valor2");
    }

    @Test
    public void testeGetValueNull() {

        table.put("valor1", LongUtil.DOIS);
        table.put("valor2", LongUtil.UM);
        table.put("valor3", LongUtil.TRES);

        Long ret = table.getValue("teste");

        assertNull(ret);
    }

    @Test
    public void testeGetLastValue() {

        table.put("valor1", LongUtil.DOIS);
        table.put("valor2", LongUtil.UM);
        table.put("valor3", LongUtil.TRES);

        Long ret = table.getLastValue();

        assertEquals(ret, LongUtil.TRES);
    }

    @Test
    public void testeGetLastValueListaVazia() {
        Long ret = table.getLastValue();
        assertNull(ret);
    }
}