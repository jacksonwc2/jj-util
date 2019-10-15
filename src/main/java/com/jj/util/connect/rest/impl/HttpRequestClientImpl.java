package com.jj.util.connect.rest.impl;

import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jj.util.IntegerUtil;
import com.jj.util.Json;
import com.jj.util.StringUtil;
import com.jj.util.connect.rest.HttpRequestClient;
import com.jj.util.enumerador.HttpMethod;
import com.jj.util.exception.BusinessServerException;
import com.jj.util.logger.LoggerUtil;
import com.jj.util.vo.RequestVO;

import antlr.StringUtils;

@SuppressWarnings(value = "all")
public class HttpRequestClientImpl implements HttpRequestClient {
    @Inject
    @LoggerUtil
    private transient Logger logger;

    private static final String ERRO_NAO_TRATADO_PELO_SERVIDOR = "Ocorreu um erro não tratado pelo servidor.";
    private static final String SERVIDOR_INACESSIVEL = "Não foi possível estabelecer conexão com servidor.";
    private static final Integer DEFAULT_TIMEOUT = IntegerUtil.CENTO_E_VINTE_MIL;

    private TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            return;
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            return;
        }
    } };

    @Override
    public <T> T request(RequestVO requestVO, Object requestObj, GenericType<T> returnType) throws BusinessServerException {

        return request(requestVO.getUriService(), requestVO.getTimeout(), requestVO.getHeaders(), requestVO.getHttpMethod(), requestObj, returnType);

    }

    @Override
    public <T> T request(RequestVO requestVO, GenericType<T> returnType) throws BusinessServerException {

        return request(requestVO.getUriService(), requestVO.getTimeout(), requestVO.getHeaders(), requestVO.getHttpMethod(), null, returnType);
    }

    @Override
    public <T> T request(RequestVO requestVO, Class<T> returnType) throws BusinessServerException {

        return request(requestVO.getUriService(), requestVO.getTimeout(), requestVO.getHeaders(), requestVO.getHttpMethod(), null, returnType);
    }

    @Override
    public void request(RequestVO requestVO, Object requestObj) throws BusinessServerException {

        request(requestVO.getUriService(), requestVO.getTimeout(), requestVO.getHeaders(), requestVO.getHttpMethod(), requestObj);
    }

    @Override
    public <T> T request(RequestVO requestVO, Object requestObj, Class<T> returnType) throws BusinessServerException {

        return request(requestVO.getUriService(), requestVO.getTimeout(), requestVO.getHeaders(), requestVO.getHttpMethod(), requestObj, returnType);
    }

    @Override
    public <T> T request(String urlServico, Integer timeout, HttpMethod method, Object requestObj, GenericType<T> returnType)
            throws BusinessServerException {

        return request(urlServico, timeout, null, method, requestObj, returnType);

    }

    @Override
    public <T> T request(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method, GenericType<T> returnType)
            throws BusinessServerException {

        return request(urlServico, timeout, requestProperty, method, null, returnType);
    }

    @Override
    public <T> T request(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method, Class<T> returnType)
            throws BusinessServerException {

        return request(urlServico, timeout, requestProperty, method, null, returnType);
    }

    @Override
    public void request(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method, Object requestObj)
            throws BusinessServerException {

        Builder builder = clientBuilder(urlServico, timeout, requestProperty);

        Response response = clientRequest(builder, requestObj, method);

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {

            throw this.tratarExcecao(response);
        }

    }

    @Override
    public <T> T request(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method, Object requestObj,
            GenericType<T> returnType) throws BusinessServerException {

        T retorno = null;

        Builder builder = clientBuilder(urlServico, timeout, requestProperty);

        Response response = clientRequest(builder, requestObj, method);

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {

            retorno = response.readEntity(returnType);

        } else {

            throw this.tratarExcecao(response);
        }

        return retorno;

    }

    @Override
    public <T> T request(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method, Object requestObj,
            Class<T> returnType) throws BusinessServerException {

        T retorno = null;

        Builder builder = clientBuilder(urlServico, timeout, requestProperty);

        Response response = clientRequest(builder, requestObj, method);

        if (response.getStatus() == Response.Status.OK.getStatusCode() || response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            retorno = response.readEntity(returnType);
        } else {
            BusinessServerException exceptionRestVO = this.tratarExcecao(response);

            throw new BusinessServerException(exceptionRestVO.getCodigo(), exceptionRestVO.getMensagemTecnica(), exceptionRestVO.getMensagem());
        }

        return retorno;
    }

    @Override
    public <T> Integer requestStatus(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method, Object requestObj,
            Class<T> returnType) throws BusinessServerException {

        Builder builder = clientBuilder(urlServico, timeout, requestProperty);

        Response response = clientRequest(builder, requestObj, method);

        if (response == null) {
            return Response.Status.BAD_REQUEST.getStatusCode();
        }

        return response.getStatus();
    }

    @Override
    public <T> T request(String urlServico, HttpMethod method, Object requestObj, GenericType<T> returnType) throws BusinessServerException {

        return request(urlServico, DEFAULT_TIMEOUT, null, method, requestObj, returnType);
    }

    @Override
    public <T> T request(String urlServico, Map<String, String> requestProperty, HttpMethod method, GenericType<T> returnType)
            throws BusinessServerException {
        return request(urlServico, DEFAULT_TIMEOUT, requestProperty, method, null, returnType);
    }

    @Override
    public <T> T request(String urlServico, Map<String, String> requestProperty, HttpMethod method, Class<T> returnType)
            throws BusinessServerException {
        return request(urlServico, DEFAULT_TIMEOUT, requestProperty, method, null, returnType);
    }

    @Override
    public void request(String urlServico, Map<String, String> requestProperty, HttpMethod method, Object requestObj) throws BusinessServerException {
        request(urlServico, DEFAULT_TIMEOUT, requestProperty, method, requestObj);

    }

    @Override
    public <T> T request(String urlServico, Map<String, String> requestProperty, HttpMethod method, Object requestObj, GenericType<T> returnType)
            throws BusinessServerException {

        return request(urlServico, DEFAULT_TIMEOUT, requestProperty, method, requestObj, returnType);
    }

    @Override
    public <T> T request(String urlServico, Map<String, String> requestProperty, HttpMethod method, Object requestObj, Class<T> returnType)
            throws BusinessServerException {
        return request(urlServico, DEFAULT_TIMEOUT, requestProperty, method, requestObj, returnType);
    }

    @Override
    public BusinessServerException tratarExcecao(Response response) {

        BusinessServerException businessServerException = null;

        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            businessServerException = new BusinessServerException(response.getStatusInfo().getReasonPhrase(),
                    "ACESSO NEGADO! VERIFIQUE AS CREDENCIAIS.", "ACESSO NEGADO! VERIFIQUE AS CREDENCIAIS.");
        } else {

            String retorno = response.readEntity(String.class);

            try {

                businessServerException = Json.getInstance().fromJson(BusinessServerException.class, retorno);

                if (businessServerException != null && StringUtil.isNullOrEmpty(businessServerException.getCodigo())) {

                    businessServerException = new BusinessServerException(response.getStatusInfo().getReasonPhrase(), retorno,
                            ERRO_NAO_TRATADO_PELO_SERVIDOR);
                }

            } catch (Exception e) {

                Boolean servidorInacessivel = response.getStatus() == Response.Status.NOT_FOUND.getStatusCode();

                String mensagem = servidorInacessivel ? SERVIDOR_INACESSIVEL : ERRO_NAO_TRATADO_PELO_SERVIDOR;

                businessServerException = new BusinessServerException(response.getStatusInfo().getReasonPhrase(), retorno, mensagem);
            }
        }

        return businessServerException;
    }

    @Override
    public Response clientRequest(Builder builder, Object requestObj, HttpMethod method) {
        Response response = null;
        switch (method) {
            case POST:
                response = postRequest(builder, requestObj);
                break;
            case PUT:
                response = putRequest(builder, requestObj);
                break;
            case GET:
                response = getRequest(builder);
                break;
            case DELETE:
                response = deleteRequest(builder, requestObj);
                break;

            default:
                break;
        }
        return response;
    }

    private Response deleteRequest(Builder builder, Object requestObj) {
        Response response;
        if (requestObj != null) {
            response = builder.build(HttpMethod.DELETE.toString(), Entity.entity(requestObj, MediaType.APPLICATION_JSON)).invoke();
        } else {
            response = builder.buildDelete().invoke();
        }
        return response;
    }

    private Response getRequest(Builder builder) {
        return builder.buildGet().invoke();
    }

    private Response putRequest(Builder builder, Object requestObj) {
        Response response;
        if (requestObj != null) {
            response = builder.buildPut(Entity.entity(requestObj, MediaType.APPLICATION_JSON)).invoke();
        } else {
            response = builder.build(HttpMethod.PUT.toString()).invoke();
        }
        return response;
    }

    private Response postRequest(Builder builder, Object requestObj) {
        Response response;
        if (requestObj != null) {
            response = builder.buildPost(Entity.entity(requestObj, MediaType.APPLICATION_JSON)).invoke();
        } else {
            response = builder.build(HttpMethod.POST.toString()).invoke();
        }
        return response;
    }

    @Override
    public Builder clientBuilder(String urlServico, Integer timeout, Map<String, String> requestProperty) {
        Builder builder = null;

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();

        configSSL(clientBuilder, urlServico);

        configTimeout(clientBuilder, timeout);

        Client client = clientBuilder.build();

        WebTarget target = client.target(urlServico);

        builder = target.request();

        if (requestProperty != null) {
            for (Entry<String, String> entry : requestProperty.entrySet()) {
                // tem que ignorar, pois ja vem add por padrão
                if (!HttpHeaders.CONTENT_LENGTH.equalsIgnoreCase(entry.getKey())) {
                    builder.header(entry.getKey(), entry.getValue());
                }
            }
        }

        if (requestProperty == null || !requestProperty.containsKey("Content-Type")) {
            builder.header("Content-Type", MediaType.APPLICATION_JSON);
        }
        if (requestProperty == null || !requestProperty.containsKey("Accept")) {
            builder.header("Accept", MediaType.APPLICATION_JSON);
        }
        if (requestProperty == null || !requestProperty.containsKey("Accept-Encoding")) {
            builder.acceptEncoding("UTF-8");
        }

        return builder;
    }

    @Override
    public Builder clientBuilder(String urlServico, Map<String, String> requestProperty) {
        return clientBuilder(urlServico, DEFAULT_TIMEOUT, requestProperty);
    }

    private void configSSL(ClientBuilder clientBuilder, String urlServico) {
        try {
            if (urlServico.toUpperCase().contains("HTTPS")) {

                SSLContext sslContext = null;

                sslContext = SSLContext.getInstance("SSL");

                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

                clientBuilder.sslContext(sslContext);

            }
        } catch (Exception e) {
            logger.fine(e.getMessage());
        }

    }

    private void configTimeout(ClientBuilder clientBuilder, Integer timeout) {
        clientBuilder.property("connectTimeout", timeout);
        clientBuilder.property("returnTimeout", timeout);
    }

}