package com.jj.util;

import static com.jj.util.typesafe.STesteEnderecoDTO.testeEnderecoDTO;
import static com.jj.util.typesafe.STesteUsuarioDTO.testeUsuarioDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.jj.util.conversor.dto.TesteCompareToDTO;
import com.jj.util.conversor.dto.TesteDTO;
import com.jj.util.conversor.dto.TesteEnderecoDTO;
import com.jj.util.conversor.dto.TesteUsuarioDTO;
import com.jj.util.enumerador.ConvertTo;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ListUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void sucessoAdquirirListaObjeto() {
        List<TesteEnderecoDTO> enderecos = new ArrayList<TesteEnderecoDTO>();

        TesteEnderecoDTO endereco1 = new TesteEnderecoDTO();
        endereco1.setRua("Rua 1");

        TesteEnderecoDTO endereco2 = new TesteEnderecoDTO();
        endereco2.setRua("Rua 2");
        enderecos.add(endereco1);
        enderecos.add(endereco2);

        TesteUsuarioDTO usuario = new TesteUsuarioDTO();
        usuario.setEnderecos(enderecos);

        List<String> listaRuas = ListUtil.from(usuario).getListOf(testeUsuarioDTO.enderecos.rua);
        assertEquals(listaRuas.size(), 2);
        assertTrue(listaRuas.get(0).equals("Rua 1"));
        assertTrue(listaRuas.get(1).equals("Rua 2"));
    }

    @Test
    public void sucessoAdquirirListaObjetoNula() {

        TesteUsuarioDTO usuario = new TesteUsuarioDTO();
        usuario.setEnderecos(null);

        List<String> listaRuas = ListUtil.from(usuario).getListOf(testeUsuarioDTO.enderecos.rua);

        assertNull(listaRuas);
        assertTrue(ListUtil.from(usuario.getEnderecos()).isNullOrEmpty());

    }

    @Test
    public void sucessoAdquirirLista() {
        List<TesteEnderecoDTO> enderecos = new ArrayList<TesteEnderecoDTO>();

        TesteEnderecoDTO endereco1 = new TesteEnderecoDTO();
        endereco1.setRua("Rua 1");

        TesteEnderecoDTO endereco2 = new TesteEnderecoDTO();
        endereco2.setRua("Rua 2");
        enderecos.add(endereco1);
        enderecos.add(endereco2);

        List<String> listaRuas = ListUtil.from(enderecos).getListOf(testeEnderecoDTO.rua);

        assertEquals(listaRuas.size(), 2);
        assertTrue(listaRuas.get(0).equals("Rua 1"));
        assertTrue(listaRuas.get(1).equals("Rua 2"));
    }

    @Test
    public void sucessoAdquirirListaComAtributosNulos() {
        List<TesteEnderecoDTO> enderecos = new ArrayList<TesteEnderecoDTO>();

        TesteEnderecoDTO endereco1 = new TesteEnderecoDTO();
        endereco1.setRua("Rua 1");

        TesteEnderecoDTO endereco2 = new TesteEnderecoDTO();
        endereco2.setRua(null);
        enderecos.add(endereco1);
        enderecos.add(endereco2);

        List<String> listaRuas = ListUtil.from(enderecos).getListOf(testeEnderecoDTO.rua);

        assertEquals(listaRuas.size(), 2);
        assertTrue(listaRuas.get(0).equals("Rua 1"));
        assertNull(listaRuas.get(1));
    }

    @Test
    public void testeSucessoOrdenarLista() {
        List<TesteDTO> valores = new ArrayList<TesteDTO>();

        TesteDTO valor1 = new TesteDTO();
        valor1.setId(1L);

        TesteDTO valor2 = new TesteDTO();
        valor2.setId(2L);

        TesteDTO valor3 = new TesteDTO();
        valor3.setId(3L);

        TesteDTO valor4 = new TesteDTO();
        valor4.setId(4L);

        valores.add(valor4);
        valores.add(valor1);
        valores.add(valor2);
        valores.add(valor3);

        ListUtil.from(valores).sortBy("id");

        assertEquals(valores.get(0).getId(), 1L);
        assertEquals(valores.get(1).getId(), 2L);
        assertEquals(valores.get(2).getId(), 3L);
        assertEquals(valores.get(3).getId(), 4L);

    }

    @Test
    public void testeFalhaOrdenarLista() {
        // Quando houver falha a ordem da lista deve permanecer a mesma
        // Neste exemplo foi passado um atributo que nao existe na classe.

        List<TesteDTO> valores = new ArrayList<TesteDTO>();

        TesteDTO valor1 = new TesteDTO();
        valor1.setId(1L);

        TesteDTO valor2 = new TesteDTO();
        valor2.setId(2L);

        TesteDTO valor3 = new TesteDTO();
        valor3.setId(3L);

        TesteDTO valor4 = new TesteDTO();
        valor4.setId(4L);

        valores.add(valor4);
        valores.add(valor1);
        valores.add(valor2);
        valores.add(valor3);

        ListUtil.from(valores).sortBy("nome");

        assertEquals(valores.get(0).getId(), 4L);
        assertEquals(valores.get(1).getId(), 1L);
        assertEquals(valores.get(2).getId(), 2L);
        assertEquals(valores.get(3).getId(), 3L);
    }

    @Test
    public void testeCompareToEConversao() {
        // Este teste deverá ordenar a lista pelo dia da semana, observe que a classe TesteCompareToDTO, possui
        // uma anotação sobre o método getInstance().
        // @ConvertTo(tipo="dayOfWeekNumber",format=DateUtil.FORMATO_YYYY_MM_DD)
        List<TesteCompareToDTO> listaCompare = new ArrayList<TesteCompareToDTO>();

        TesteCompareToDTO compare1 = new TesteCompareToDTO();
        // Sexta-Feira
        compare1.setIdentificador("2000-12-15");

        TesteCompareToDTO compare2 = new TesteCompareToDTO();
        // Quart-Feira
        compare2.setIdentificador("2000-12-13");

        TesteCompareToDTO compare3 = new TesteCompareToDTO();
        // Segunda-Feira
        compare3.setIdentificador("2000-12-11");

        listaCompare.add(compare1);
        listaCompare.add(compare2);
        listaCompare.add(compare3);

        ListUtil.from(listaCompare).sortBy("identificador", Boolean.TRUE, ConvertTo.DAY_OF_WEEK_NUMBER, DateUtil.FORMATO_YYYY_MM_DD);

        assertEquals(listaCompare.get(0).getIdentificador(), "2000-12-11");
        assertEquals(listaCompare.get(1).getIdentificador(), "2000-12-13");
        assertEquals(listaCompare.get(2).getIdentificador(), "2000-12-15");
    }

    @Test
    public void listaNula() {
        List<TesteCompareToDTO> listaCompare = null;

        assertTrue(ListUtil.from(listaCompare).isNullOrEmpty());
        assertTrue(ListUtil.isNullOrEmpty(listaCompare));

    }

    @Test
    public void sizeEquals() {

        List<Long> longs = new ArrayList<Long>();
        assertFalse(ListUtil.sizeEquals(longs, IntegerUtil.ZERO));
        assertFalse(ListUtil.sizeEquals(longs, IntegerUtil.ZERO));
        assertFalse(ListUtil.sizeEquals(null, IntegerUtil.ZERO));

        longs.add(LongUtil.UM);
        assertTrue(ListUtil.sizeEquals(longs, IntegerUtil.UM));
    }

    @Test
    public void sizeMore() {

        List<Long> longs = new ArrayList<Long>();
        assertFalse(ListUtil.sizeMore(longs, IntegerUtil.ZERO));
        longs.add(LongUtil.UM);
        assertTrue(ListUtil.sizeMore(longs, IntegerUtil.ZERO));
    }

    @Test
    public void sizeMoreEquals() {

        List<Long> longs = new ArrayList<Long>();
        longs.add(LongUtil.UM);
        assertTrue(ListUtil.sizeMoreEquals(longs, IntegerUtil.ZERO));
        longs.add(LongUtil.DOIS);
        assertTrue(ListUtil.sizeMoreEquals(longs, IntegerUtil.UM));
        assertFalse(ListUtil.sizeMoreEquals(null, IntegerUtil.UM));
    }

    @Test
    public void sizeLessTest() {

        List<Long> longs = new ArrayList<Long>();
        longs.add(LongUtil.UM);
        assertFalse(ListUtil.sizeLess(longs, IntegerUtil.DOIS));
    }

    @Test
    public void sizeLess() {

        List<Long> longs = new ArrayList<Long>();
        longs.add(LongUtil.UM);
        longs.add(LongUtil.DOIS);
        assertTrue(ListUtil.sizeLess(longs, IntegerUtil.UM));
        assertFalse(ListUtil.sizeLess(null, IntegerUtil.ZERO));
    }

    @Test
    public void isNullOrEmptyOrMoreNullOrEmptyTest() {

        List<Long> longs = new ArrayList<Long>();
        assertTrue(ListUtil.isNullOrEmptyOrMore(longs, IntegerUtil.ZERO));
        assertTrue(ListUtil.isNullOrEmptyOrMore(null, IntegerUtil.ZERO));
        longs.add(LongUtil.UM);
        longs.add(LongUtil.DOIS);
        assertTrue(ListUtil.isNullOrEmptyOrMore(longs, IntegerUtil.UM));
    }

    @Test
    public void isNullOrEmptyOrMoreMoreTest() {

        List<Long> longs = new ArrayList<Long>();
        longs.add(LongUtil.UM);
        assertFalse(ListUtil.isNullOrEmptyOrMore(longs, IntegerUtil.UM));
    }

    @Test
    public void eqTrueTest() {
        List<Long> lista1 = new ArrayList<Long>();
        lista1.add(LongUtil.UM);
        List<Long> lista2 = new ArrayList<Long>();
        lista2.add(LongUtil.UM);

        assertTrue(ListUtil.eq(lista1, lista2));
    }

    @Test
    public void eqFalseTest() {
        List<Long> lista1 = new ArrayList<Long>();
        List<Long> lista2 = new ArrayList<Long>();
        lista2.add(LongUtil.UM);

        assertFalse(ListUtil.eq(lista1, lista2));
    }

}
