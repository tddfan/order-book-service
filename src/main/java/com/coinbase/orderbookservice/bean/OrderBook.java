package com.coinbase.orderbookservice.bean;

import java.math.BigDecimal;
import java.util.List;

public class OrderBook {

    private BoundedTreeMap<BigDecimal, BigDecimal> asks;
    private BoundedTreeMap<BigDecimal, BigDecimal> bids;


    public OrderBook(int orderSize, List<List<String>> asks, List<List<String>>  bids) {
        this.asks = new BoundedTreeMap(orderSize);
        initOrders(orderSize, asks, this.asks);
        this.bids = new BoundedTreeMap(orderSize, true);
        initOrders(orderSize, bids, this.bids);
    }

    private void initOrders(int orderSize, List<List<String>> asks, BoundedTreeMap<BigDecimal, BigDecimal> map) {
        asks.subList(0, orderSize(orderSize, asks))
                .forEach(entry -> map.put(new BigDecimal(entry.get(0)), new BigDecimal(entry.get(1))));
    }

    public boolean addAsk(List<String> newEntry) {
        return updateValue(newEntry, this.asks);
    }

    public boolean addBid(List<String> newEntry) {
        return updateValue(newEntry, this.bids);
    }

    private boolean updateValue(List<String> newEntry, BoundedTreeMap<BigDecimal, BigDecimal> map) {
        BigDecimal newValue = new BigDecimal(newEntry.get(1));
        if(BigDecimal.ZERO.compareTo(newValue) ==0) {
            return map.remove(new BigDecimal(newEntry.get(0)));
        }
        return map.put(new BigDecimal(newEntry.get(0)), newValue);
    }

    private int orderSize(int orderSize, List<List<String>> asks) {
        return asks.size() > orderSize ? orderSize: asks.size();
    }


    public void printOrder() {
        System.out.println("asks " + asks);
        System.out.println("bids " + bids);
    }

}
