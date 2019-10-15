package com.jj.util.websocket;

import java.util.List;

import com.jj.util.dto.WebSocketDTO;
import com.jj.util.exception.BusinessServerException;

public interface NotificacaoFacade {

    public void notificar(WebSocketDTO webSocketDTO) throws BusinessServerException;

    public List<Long> buscarUsuariosAutenticados(List<Long> usuarios, WebSocketDTO parametros) throws BusinessServerException;

}