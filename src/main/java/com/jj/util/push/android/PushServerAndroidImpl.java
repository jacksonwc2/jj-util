package com.jj.util.push.android;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import com.jj.util.LongUtil;
import com.jj.util.StringUtil;
import com.jj.util.connect.rest.HttpRequestClient;
import com.jj.util.connect.rest.impl.HttpRequestClientImpl;
import com.jj.util.enumerador.HttpMethod;
import com.jj.util.exception.BusinessServerException;
import com.jj.util.push.PushMessage;
import com.jj.util.push.PushServer;

public class PushServerAndroidImpl implements PushServer {

    private static final String BARRAS = "://";

    private static final String FALHA_AO_ENVIAR_A_NOTIFICACAO = "Falha ao enviar a notificação!";

    private static final String PUSH_ERROR = "PUSH_ERROR";

    private String protocolo = "https";

    private static final String URL_SERVICE = "android.googleapis.com/gcm/send";

    private static final String API_KEY = "key=AIzaSyD99Dc7TZg_NlRS9uA0CF8BFh_IIdJdPb4";

    private HttpRequestClient httpRequestClient;

    Long noId = 0L;

    public PushServerAndroidImpl() {
        httpRequestClient = new HttpRequestClientImpl();

        String protocolo = System.getProperty("protocolo-servico");

        if (!StringUtil.isNullOrEmpty(protocolo)) {
            this.protocolo = protocolo;
        }
    }

    @Override
    public void sendMessage(String deviceToken, PushMessage pushMessage) throws BusinessServerException {

        if (noId == LongUtil.CINCO) {
            noId = LongUtil.ZERO;
        }

        Map<String, String> requestProperty = new HashMap<String, String>();
        requestProperty.put("Content-Type", "application/json");
        requestProperty.put("Authorization", API_KEY);

        PayloadAndroid payload = new PayloadAndroid();

        noId++;
        payload.addRegId(deviceToken);
        payload.createData(pushMessage.getTitle(), pushMessage.getMessage(), noId);

        Builder builder = httpRequestClient.clientBuilder(this.protocolo.concat(BARRAS).concat(URL_SERVICE), requestProperty);

        Response response = httpRequestClient.clientRequest(builder, payload, HttpMethod.POST);

        if (response.getStatus() == Response.Status.OK.getStatusCode() || response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            ResultNotifyAndroid result = response.readEntity(ResultNotifyAndroid.class);
            if (result.getFailure() > 0) {
                StringBuilder errors = new StringBuilder();
                for (ResultItem item : result.getResults()) {
                    if (errors.length() > 0) {
                        errors.append(",").append(item.getError());
                    } else {
                        errors.append(item.getError());
                    }
                }
                throw new BusinessServerException(PUSH_ERROR, errors.toString(), FALHA_AO_ENVIAR_A_NOTIFICACAO);
            }

        } else {
            throw new BusinessServerException(PUSH_ERROR, response.getStatusInfo().toString(), FALHA_AO_ENVIAR_A_NOTIFICACAO);
        }

    }
}
