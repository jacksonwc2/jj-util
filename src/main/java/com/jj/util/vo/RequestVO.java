package com.jj.util.vo;

import java.util.Map;

import com.jj.util.IntegerUtil;
import com.jj.util.enumerador.HttpMethod;

public class RequestVO {
    
    private String uriService;
    
    private  Map<String, String> headers;
    
    private HttpMethod httpMethod;
    
    private Integer timeout = IntegerUtil.CENTO_E_VINTE_MIL;

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getUriService() {
        return uriService;
    }

    public void setUriService(String uriService) {
        this.uriService = uriService;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

}
