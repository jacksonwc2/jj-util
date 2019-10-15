package com.jj.util.conversor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.jj.util.DateUtil;
import com.jj.util.conversor.dto.TesteEnderecoDTO;
import com.jj.util.conversor.dto.TestePerfilDTO;
import com.jj.util.conversor.dto.TesteUsuarioDTO;
import com.jj.util.conversor.model.Endereco;
import com.jj.util.conversor.model.Perfil;
import com.jj.util.conversor.model.Usuario;
import com.jj.util.exception.BusinessServerException;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class DtoConverterTest {

    private static final long ID = 1L;
    private static final String NOME = "Usuario";
    private static final String CIDADE = "Cidade";
    private static final String RUA = "Rua";
    private static final Date DATA_NASCIMENTO = DateUtil.parseDate("2016-01-01", DateUtil.FORMATO_YYYY_MM_DD);
    public static final String ADMINISTRADOR = "Administrador";

    // Teste necessário para testar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<DtoConverter> constructor = null;
        constructor = DtoConverter.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testConverterDTOParaModel() throws Exception {
        TesteUsuarioDTO usuarioDto = defineValoresDTO();

        Usuario usuario = DtoConverter.convert(usuarioDto, Usuario.class);

        // Objeto Principal
        assertEquals(usuario.getId(), usuarioDto.getId());
        // lista de objeto filho
        assertEquals(usuario.getEnderecos().size(), 1);
    }

    @Test
    public void testConverterOrigemNull() throws Exception {
        TesteUsuarioDTO usuarioDto = null;

        Usuario usuario = DtoConverter.convert(usuarioDto, Usuario.class);

        // Objeto de origem
        assertNull(usuario);
    }

    @Test
    public void testConverterModelParaDTO() throws Exception {
        Usuario usuario = defineValoresModel();

        TesteUsuarioDTO usuarioDto = DtoConverter.convert(usuario, TesteUsuarioDTO.class);

        assertEquals(usuarioDto.getId(), usuario.getId());
        assertEquals(usuarioDto.getEnderecos().size(), 1);
    }

    @Test
    public void testConverterModelsParaDTOs() throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(defineValoresModel());

        List<TesteUsuarioDTO> usuarioDTOs = DtoConverter.convert(usuarios, TesteUsuarioDTO.class);

        assertEquals(usuarioDTOs.get(0).getId(), usuarios.get(0).getId());
        assertEquals(usuarioDTOs.get(0).getEnderecos().size(), 1);
    }

    @Test
    public void testConverterDTOsParaModels() throws Exception {
        List<TesteUsuarioDTO> usuarioDTOs = new ArrayList<>();
        usuarioDTOs.add(defineValoresDTO());

        List<Usuario> usuarios = DtoConverter.convert(usuarioDTOs, Usuario.class);

        assertEquals(usuarios.get(0).getId(), usuarioDTOs.get(0).getId());
        assertEquals(usuarios.get(0).getEnderecos().size(), 1);
    }

    @Test
    public void testeCloneObjects() {

        TesteUsuarioDTO usuarioDTO = defineValoresDTO();

        TesteUsuarioDTO usuarioDTOClone = DtoConverter.clone(usuarioDTO);

        usuarioDTOClone.setNome("Usuario X");
        usuarioDTOClone.getEnderecos().get(0).setRua("Rua XYZ");
        usuarioDTOClone.getPerfil().setNivel("Cliente");

        // Ao alterar dados no objeto clone, não deve impactar na instancia do objeto origem. Portanto os valores como foram alterados devem permanecer diferentes.
        assertFalse(usuarioDTOClone.getPerfil().getNivel().equalsIgnoreCase(usuarioDTO.getPerfil().getNivel()));
        assertFalse(usuarioDTOClone.getNome().equalsIgnoreCase(usuarioDTO.getNome()));
        assertFalse(usuarioDTOClone.getEnderecos().get(0).getRua().equalsIgnoreCase(usuarioDTO.getEnderecos().get(0).getRua()));

    }

    private Usuario defineValoresModel() {
        Usuario usuario = new Usuario();
        usuario.setId(ID);
        usuario.setNome(NOME);
        usuario.setDataNascimento(DATA_NASCIMENTO);

        List<Endereco> enderecos = new ArrayList<>();
        Endereco endereco = new Endereco();
        endereco.setId(ID);
        endereco.setRua(RUA);
        endereco.setCidade(CIDADE);

        enderecos.add(endereco);
        usuario.setEnderecos(enderecos);

        Perfil perfil = new Perfil();
        perfil.setId(ID);
        perfil.setNivel(ADMINISTRADOR);
        usuario.setPerfil(perfil);

        return usuario;
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

    @Test
    public void toScriptUpdateTest() throws BusinessServerException {

        TesteUsuarioDTO usuarioDTO = new TesteUsuarioDTO();
        usuarioDTO.setId(ID);
        usuarioDTO.setNome(NOME);
        usuarioDTO.setDataNascimento(DATA_NASCIMENTO);

        String script = DtoConverter.toScript(usuarioDTO);

        String esperado = "UPDATE tb_usuario SET nome='Usuario',dt_nascimento='2016-01-01' WHERE id=1;";

        assertTrue(script.equals(esperado));

    }

    @Test
    public void toScriptInsertTest() throws BusinessServerException {

        TesteUsuarioDTO usuarioDTO = new TesteUsuarioDTO();
        usuarioDTO.setId(null);
        usuarioDTO.setNome(NOME);
        usuarioDTO.setDataNascimento(DATA_NASCIMENTO);

        String script = DtoConverter.toScript(usuarioDTO);

        String esperado = "INSERT INTO tb_usuario(id,nome,dt_nascimento) VALUES (nextval('gen_usuario'),'Usuario','2016-01-01');";

        assertTrue(script.equals(esperado));

    }
}
