package com.jj.util;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class JsonTest {
    private Json json;

    @Before
    public void criarInstancia() {
        json = Json.getInstance();
    }

    @Test
    public void testFromJson() throws Exception {
        json = Json.getInstance();

    }

    @Test
    public void testToJson() throws Exception {
        String teste = "teste";
        String jsonTeste = json.toJson(teste);
        assertTrue(jsonTeste.equals("\"teste\""));
    }
}
