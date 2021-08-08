package com.coinbase.orderbookservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coinbase.orderbookservice.bean.SubscribeMessage;
import com.coinbase.orderbookservice.service.WebsocketClientEndpoint.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinbaseOrderService {

    public static final String LEVEL_2 = "level2";
    private final WebsocketClientEndpoint clientEndPoint;
    private MessageHandler messageHandler;
    private final ObjectMapper objectMapper;

    @Autowired
    public CoinbaseOrderService(WebsocketClientEndpoint clientEndPoint, MessageHandler messageHandler) {
        this.clientEndPoint = clientEndPoint;
        this.messageHandler = messageHandler;
        this.objectMapper = new ObjectMapper();

    }

    public void orderBook(String productId) throws JsonProcessingException {
        this.clientEndPoint.addMessageHandler(messageHandler);
        clientEndPoint.sendMessage(objectMapper.writeValueAsString(new SubscribeMessage(new String[]{productId}, new String[]{LEVEL_2})));
    }

}
