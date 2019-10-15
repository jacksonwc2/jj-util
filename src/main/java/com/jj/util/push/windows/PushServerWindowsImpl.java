package com.jj.util.push.windows;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.jj.util.DoubleUtil;
import com.jj.util.StringUtil;
import com.jj.util.connect.rest.impl.HttpRequestClientImpl;
import com.jj.util.exception.BusinessServerException;
import com.jj.util.push.PushMessage;
import com.jj.util.push.PushServer;

public class PushServerWindowsImpl implements PushServer {

    private static Logger logger = Logger.getLogger(PushServerWindowsImpl.class.getName());

    private static final String BARRAS = "://";

    private static final String FALHA_AO_ENVIAR_A_NOTIFICACAO = "Falha ao enviar a notificação!";

    private static final String PUSH_ERROR = "PUSH_ERROR";

    private String protocolo = "https";

    private static final String URL_AUTH = "login.live.com/accesstoken.srf";

    private Long startToken;

    private AuthTokenWindows auth;

    public PushServerWindowsImpl() throws Exception {

        String protocoloServico = System.getProperty("protocolo-servico");

        if (!StringUtil.isNullOrEmpty(protocoloServico)) {
            this.protocolo = protocoloServico;
        }

        auth = getNewToken();
    }

    @Override
    public void sendMessage(String deviceToken, PushMessage pushMessage) throws BusinessServerException {

        try {

            checkValidToken();

            Map<String, String> requestProperty = new HashMap<String, String>();
            requestProperty.put("Content-Type", "text/xml");
            requestProperty.put("X-WindowsPhone-Target", "toast");
            requestProperty.put("X-NotificationClass", "2");
            requestProperty.put("Authorization", auth.getAccess_token());

            Builder builder = new HttpRequestClientImpl().clientBuilder(deviceToken, requestProperty);

            PayloadWindows payload = new PayloadWindows(pushMessage.getTitle(), pushMessage.getMessage());

            Response response = builder.buildPost(Entity.entity(payload.build(), MediaType.TEXT_XML)).invoke();

            if (response.getStatus() != HttpStatus.SC_OK) {
                throw new BusinessServerException(PUSH_ERROR, response.getStatusInfo().toString(), FALHA_AO_ENVIAR_A_NOTIFICACAO);

            }

        } catch (Exception e) {
            throw new BusinessServerException(PUSH_ERROR, e.toString(), FALHA_AO_ENVIAR_A_NOTIFICACAO);
        }

    }

    private void checkValidToken() throws Exception {
        Long startValidation = System.currentTimeMillis();

        Long result = startValidation - startToken;

        Double seconds = result.doubleValue() / DoubleUtil.MIL;

        if (seconds > auth.getExpires_in().doubleValue()) {
            auth = getNewToken();
        }

    }

    private AuthTokenWindows getNewToken() throws Exception {

        Form form = new Form();
        form.param("grant_type", "client_credentials");
        form.param("client_id", "ms-app://s-1-15-2-2186497897-2461695567-992682061-1422976000-869217862-511803208-3648029744");
        form.param("client_secret", "YVMfYFxvNBr3TWJgG/I2xv4eAW/1mAzo");
        form.param("scope", "notify.windows.com");

        Map<String, String> requestProperty = new HashMap<String, String>();
        requestProperty.put("Content-Type", "application/x-www-form-urlencoded");

        Builder builder = new HttpRequestClientImpl().clientBuilder(this.protocolo.concat(BARRAS).concat(URL_AUTH), requestProperty);

        Response response = builder.buildPost(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED)).invoke();

        startToken = System.currentTimeMillis();

        logger.info("Token Windows Start:" + startToken);

        return response.readEntity(AuthTokenWindows.class);

    }
}
