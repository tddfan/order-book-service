package com.coinbase.orderbookservice.bean;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SubscribeMessage {
    private String type;
    private String[] product_ids;
    private Channel[] channels;

    public SubscribeMessage(String[] product_ids, String[] channels) {
        this.type = "subscribe";
        this.product_ids = product_ids;
        this.channels = Stream.of(channels).map(c -> new Channel(c)).collect(toList()).toArray(new Channel[0]);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(String[] product_ids) {
        this.product_ids = product_ids;
    }

    public Channel[] getChannels() {
        return channels;
    }

    public void setChannels(Channel[] channels) {
        this.channels = channels;
    }
}

class Channel {
    private String name;

    public Channel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}