package com.jj.util.connect.rest;

import java.util.Map;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.jj.util.enumerador.HttpMethod;
import com.jj.util.exception.BusinessServerException;
import com.jj.util.vo.RequestVO;

public interface HttpRequestClient {

    public abstract <T> T request(RequestVO requestVO, Object requestObj, GenericType<T> returnType) throws BusinessServerException;

    public abstract <T> T request(RequestVO requestVO, GenericType<T> returnType) throws BusinessServerException;

    public abstract <T> T request(RequestVO requestVO, Class<T> returnType) throws BusinessServerException;

    public abstract <T> T request(RequestVO requestVO, Object requestObj, Class<T> returnType) throws BusinessServerException;

    public abstract <T> T request(String urlServico, HttpMethod method, Object requestObj, GenericType<T> returnType) throws BusinessServerException;

    public abstract <T> T request(String urlServico, Map<String, String> requestProperty, HttpMethod method, GenericType<T> returnType)
            throws BusinessServerException;

    public abstract <T> T request(String urlServico, Map<String, String> requestProperty, HttpMethod method, Class<T> returnType)
            throws BusinessServerException;

    public abstract void request(String urlServico, Map<String, String> requestProperty, HttpMethod method, Object requestObj)
            throws BusinessServerException;

    public abstract <T> T request(String urlServico, Map<String, String> requestProperty, HttpMethod method, Object requestObj,
            GenericType<T> returnType) throws BusinessServerException;

    public abstract <T> T request(String urlServico, Map<String, String> requestProperty, HttpMethod method, Object requestObj, Class<T> returnType)
            throws BusinessServerException;

    public abstract <T> T request(String urlServico, Integer timeout, HttpMethod method, Object requestObj, GenericType<T> returnType)
            throws BusinessServerException;

    public abstract <T> T request(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method,
            GenericType<T> returnType) throws BusinessServerException;

    public abstract <T> T request(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method, Class<T> returnType)
            throws BusinessServerException;

    public abstract void request(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method, Object requestObj)
            throws BusinessServerException;

    public abstract <T> T request(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method, Object requestObj,
            GenericType<T> returnType) throws BusinessServerException;

    public abstract <T> T request(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method, Object requestObj,
            Class<T> returnType) throws BusinessServerException;

    public abstract void request(RequestVO requestVO, Object requestObj) throws BusinessServerException;

    public Builder clientBuilder(String urlServico, Integer timeout, Map<String, String> requestProperty);

    public Builder clientBuilder(String urlServico, Map<String, String> requestProperty);

    public Response clientRequest(Builder builder, Object requestObj, HttpMethod method);

    public BusinessServerException tratarExcecao(Response response);

    /**
     * Retorna o status do servico que ira acessar
     * 
     * @return
     * @throws BusinessServerException
     */
    public <T> Integer requestStatus(String urlServico, Integer timeout, Map<String, String> requestProperty, HttpMethod method, Object requestObj,
            Class<T> returnType) throws BusinessServerException;

}