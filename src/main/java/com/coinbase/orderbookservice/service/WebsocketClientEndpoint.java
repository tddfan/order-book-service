package com.coinbase.orderbookservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.net.URI;


@Component
@ClientEndpoint
public class WebsocketClientEndpoint {

    private Session userSession = null;
    private MessageHandler messageHandler;

    public WebsocketClientEndpoint(@Value("${coinbase.wss.api}") String coinBaseAPI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.setDefaultMaxBinaryMessageBufferSize(1024 * 1024);
            container.setDefaultMaxTextMessageBufferSize(1024 * 1024);
            container.connectToServer(this, new URI(coinBaseAPI));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }


    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }


    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    public  interface MessageHandler {
         void handleMessage(String message);
    }
}