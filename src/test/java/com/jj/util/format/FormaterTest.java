package com.jj.util.format;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FormaterTest {

    @Test
    public void formatTest() {

        assertTrue(Formater.formatString("teste %s teste %s", "01", "02", "teste").equals("teste 01 teste 02"));

    }

}
