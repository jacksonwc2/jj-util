package com.jj.util.cors;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.jj.util.StringUtil;

@Provider
public class RESTCorsResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {
        String reqHead = requestCtx.getHeaderString("Access-Control-Request-Headers");
        if (StringUtil.isVazio(reqHead)) {
            reqHead = "accept, content-type, x-requested-with, sistema, sistemaversao, tokendns, browserinfo, ticketacesso, tokenserver, exibirTokenServer";
        }
        responseCtx.getHeaders().add("Access-Control-Allow-Headers", reqHead.concat(", origin, authorization"));
        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseCtx.getHeaders().add("Access-Control-Max-Age", "1209600");
    }
}