package com.jj.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ItemMapTest {

    ItemMap<Long, String> map = new ItemMap<Long, String>(LongUtil.UM, "teste");

    @Test
    public void setKeyTest() {
        map.setKey(LongUtil.UM);
        assertEquals(map.getKey(), LongUtil.UM);
    }

    @Test
    public void setValueTest() {
        map.setValue("teste");
        assertEquals(map.getValue(), "teste");
    }
}
