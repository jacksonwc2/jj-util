package com.jj.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.jj.util.BeanCompare;
import com.jj.util.conversor.dto.TesteEnderecoDTO;
import com.jj.util.conversor.dto.TestePerfilDTO;
import com.jj.util.conversor.dto.TesteUsuarioDTO;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class BeanCompareTest {

    private static final long ID = 1L;
    private static final String NOME = "Usuario";
    private static final String CIDADE = "Cidade";
    private static final String RUA = "Rua";
    private static final Date DATA_NASCIMENTO = new Date();
    public static final String ADMINISTRADOR = "Administrador";

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void ConstructorIsPrivateTest() throws Exception {
        Constructor<BeanCompare> constructor = null;
        constructor = BeanCompare.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testEquals() {

        TesteEnderecoDTO endereco1 = new TesteEnderecoDTO();
        endereco1.setRua("Rua 1");

        TesteEnderecoDTO endereco2 = new TesteEnderecoDTO();
        endereco2.setRua("Rua 1");

        assertFalse(endereco1.equals(endereco2));

        assertTrue(BeanCompare.eq(endereco1, endereco2));

    }

    @Test
    public void testBigDecimalEquals() {

        BigDecimal valor1 = new BigDecimal(2L);
        BigDecimal valor2 = new BigDecimal(2L);

        assertTrue(BeanCompare.eq(valor1, valor2));

    }

    @Test
    public void testObjetosNulos() {

        TesteEnderecoDTO endereco1 = null;

        TesteEnderecoDTO endereco2 = null;

        assertTrue(BeanCompare.eq(endereco1, endereco2));

    }

    @Test
    public void testObjetoNulo() {

        TesteEnderecoDTO endereco1 = null;

        TesteEnderecoDTO endereco2 = new TesteEnderecoDTO();
        endereco2.setRua("Rua 1");

        assertFalse(BeanCompare.eq(endereco1, endereco2));

    }

    @Test
    public void testTipoNull() {

        TesteEnderecoDTO endereco2 = new TesteEnderecoDTO();
        endereco2.setRua("Rua 1");

        assertFalse(BeanCompare.eq(null, endereco2));

    }

    @Test
    public void testTipoAtributo() {

        assertTrue(BeanCompare.eq(1L, 1L));

    }

    @Test
    public void testObjetosComplexosSucesso() {

        TesteUsuarioDTO obj1 = defineValoresDTO();

        TesteUsuarioDTO obj2 = defineValoresDTO();

        assertTrue(BeanCompare.eq(obj1, obj2));

    }

    @Test
    public void testObjetosComplexosFalha() {

        TesteUsuarioDTO obj1 = defineValoresDTO();

        TesteUsuarioDTO obj2 = defineValoresDTO();

        obj2.getEnderecos().get(0).setCidade("OUTRA");

        assertFalse(BeanCompare.eq(obj1, obj2));

    }

    private TesteUsuarioDTO defineValoresDTO() {

        TesteUsuarioDTO usuarioDTO = new TesteUsuarioDTO();
        usuarioDTO.setId(ID);
        usuarioDTO.setNome(NOME);
        usuarioDTO.setDataNascimento(DATA_NASCIMENTO);

        List<TesteEnderecoDTO> enderecoDTOs = new ArrayList<>();
        TesteEnderecoDTO enderecoDTO = new TesteEnderecoDTO();
        enderecoDTO.setRua(RUA);
        enderecoDTO.setCidade(CIDADE);

        enderecoDTOs.add(enderecoDTO);
        usuarioDTO.setEnderecos(enderecoDTOs);

        TestePerfilDTO perfilDTO = new TestePerfilDTO();
        perfilDTO.setNivel(ADMINISTRADOR);
        usuarioDTO.setPerfil(perfilDTO);

        return usuarioDTO;
    }

}
