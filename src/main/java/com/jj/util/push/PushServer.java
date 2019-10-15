package com.jj.util.push;

import com.jj.util.exception.BusinessServerException;

@FunctionalInterface
public interface PushServer {

    public void sendMessage(String deviceToken, PushMessage pushMessage) throws BusinessServerException;
}
