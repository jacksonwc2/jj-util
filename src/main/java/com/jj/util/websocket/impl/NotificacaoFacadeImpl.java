package com.jj.util.websocket.impl;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;

import com.jj.util.Json;
import com.jj.util.StringUtil;
import com.jj.util.connect.rest.HttpRequestClient;
import com.jj.util.dto.NotificacaoWebSocketDTO;
import com.jj.util.dto.UsuariosAutenticadosDTO;
import com.jj.util.dto.WebSocketDTO;
import com.jj.util.dto.WebSocketMensagemDTO;
import com.jj.util.enumerador.HttpMethod;
import com.jj.util.exception.BusinessServerException;
import com.jj.util.websocket.NotificacaoFacade;

public class NotificacaoFacadeImpl implements NotificacaoFacade {

    private static Logger logger = Logger.getLogger(NotificacaoFacadeImpl.class.getName());

    @Inject
    private HttpRequestClient httpRequestClient;

    @Override
    public void notificar(WebSocketDTO webSocketDTO) throws BusinessServerException {

        if (StringUtil.isNullOrEmpty(webSocketDTO.getVersaoFacade())) {
            logger.info("Versão Facade não encontrada para o domínio:" + webSocketDTO.getDominioSistema());
        } else {

            int chaveSessao = webSocketDTO.getCodigoUsuario().toString().concat(webSocketDTO.getDominioSistema()).hashCode();

            WebSocketMensagemDTO mensagem = new WebSocketMensagemDTO(webSocketDTO.getCodigoNotificacao(), webSocketDTO.getCodigoTipoNotificacao(),
                    webSocketDTO.getCodigoUsuario(), webSocketDTO.getMensagem(), webSocketDTO.getConteudo());

            NotificacaoWebSocketDTO notificacaoWebSocketDTO = new NotificacaoWebSocketDTO();
            notificacaoWebSocketDTO.setFlagVisivel(webSocketDTO.getFlagVisivel());
            notificacaoWebSocketDTO.setChaveSessao(chaveSessao);
            notificacaoWebSocketDTO.setMensagem(Json.getInstance().toJson(mensagem));

            httpRequestClient.request(montarUrlFacade(webSocketDTO, "notificar"), null, HttpMethod.POST, notificacaoWebSocketDTO, String.class);
        }

    }

    private String montarUrlFacade(WebSocketDTO webSocketDTO, String servico) {
        String urlFacadeCliente = webSocketDTO.getProtocolo().concat("://").concat(webSocketDTO.getDominioSistema());

        String facadeLocal = System.getProperty("facade-local");
        Boolean facadeProducao = StringUtil.isNullOrEmpty(facadeLocal) ? true : !Boolean.parseBoolean(facadeLocal);

        if (facadeProducao) {
            urlFacadeCliente += "/s1-fcd-" + webSocketDTO.getVersaoFacade() + "/bs/notificacaoWebSocket/" + servico;
        } else {
            urlFacadeCliente = "http://".concat(webSocketDTO.getDominioSistema());

            String portaServidor = System.getProperty("porta-servidor");
            urlFacadeCliente += ":".concat(StringUtil.isNullOrEmpty(portaServidor) ? "8080" : portaServidor)
                    .concat("/s1-fcd/bs/notificacaoWebSocket/" + servico);
        }
        return urlFacadeCliente;
    }

    @Override
    public List<Long> buscarUsuariosAutenticados(List<Long> usuarios, WebSocketDTO parametros) throws BusinessServerException {

        UsuariosAutenticadosDTO usuariosAutenticados = new UsuariosAutenticadosDTO();
        usuariosAutenticados.setCodigoUsuarios(usuarios);
        usuariosAutenticados.setDominioSistema(parametros.getDominioSistema());

        return httpRequestClient.request(montarUrlFacade(parametros, "buscarUsuariosAutenticados"), new HashMap<>(), HttpMethod.POST,
                usuariosAutenticados, new GenericType<List<Long>>() {

                });
    }

}
