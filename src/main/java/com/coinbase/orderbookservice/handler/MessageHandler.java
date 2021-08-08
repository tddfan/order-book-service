package com.coinbase.orderbookservice.handler;

import com.coinbase.orderbookservice.bean.OrderBook;
import com.coinbase.orderbookservice.service.WebsocketClientEndpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MessageHandler implements WebsocketClientEndpoint.MessageHandler {

    public static final String MESSAGE_TYPE = "type";
    public static final String SNAPSHOT = "snapshot";
    public static final String CHANGES = "changes";
    public static final String L_2_UPDATE = "l2update";
    public static final String BIDS = "bids";
    public static final String ASKS = "asks";
    public static final String SELL = "sell";
    public static final String BUY = "buy";

    private final ObjectMapper objectMapper;
    private OrderBook orderBook;

    public MessageHandler() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void handleMessage(String message) {
        Map messageMap = null;
        try {
            messageMap = objectMapper.readValue(message, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(messageMap.get(MESSAGE_TYPE).equals(SNAPSHOT)) {
            orderBook = new OrderBook(10, (List<List<String>> )messageMap.get(ASKS), (List<List<String>> )messageMap.get(BIDS));
        }
        if(messageMap.get(MESSAGE_TYPE).equals(L_2_UPDATE)) {
            handleUpdate(messageMap);
        }

    }

    private void handleUpdate(Map value) {
        List<List<String>> changes = (List<List<String>>)value.get(CHANGES);
        List<String> updates = changes.get(0);
        if(SELL.equals(updates.get(0))) {
            if(orderBook.addAsk(updates.subList(1, 3))) {
                orderBook.printOrder();
            }
        }
        if(BUY.equals(updates.get(0))) {
            if(orderBook.addBid(updates.subList(1,3))) {
                orderBook.printOrder();
            }
        }
    }


}
